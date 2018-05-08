package com.zedorff.yobabooker.ui.views

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.Paint.Cap
import android.graphics.Paint.Join
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.zedorff.yobabooker.R
import com.zedorff.yobabooker.app.extensions.getColor
import com.zedorff.yobabooker.app.extensions.sumBy
import com.zedorff.yobabooker.model.db.embeded.FullTransaction
import com.zedorff.yobabooker.model.db.entities.CategoryEntity
import org.jetbrains.anko.dip
import org.jetbrains.anko.sp


class PieChartView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    companion object {
        const val LEFT: Int = 1
        const val RIGHT: Int = 2
    }

    private var categories: MutableList<CategoryEntity> = mutableListOf()
    private var categoriesSum: MutableList<Float> = mutableListOf()

    private var viewWidth: Int = 0
    private var viewHeight: Int = 0

    private var angleInterpolation: Float = 0F
    private var selectionInterpolation: Float = 0F
    private var unSelectionInterpolation: Float = 0F
    private var infoInterpolation: Float = 0F

    private var transactionTotal: Float = 0f

    private var selectionAnimator: ValueAnimator = ObjectAnimator.ofFloat(0f, 1f)
    private var unSelectionAnimator: ValueAnimator = ObjectAnimator.ofFloat(1f, 0f)
    private var angleAnimator: ValueAnimator = ObjectAnimator.ofFloat(0f, 1f)
    private var infoAppearingAnimator: ValueAnimator = ObjectAnimator.ofFloat(0f, 1f)
    private var animatorSet: AnimatorSet = AnimatorSet()
    private var selectionAnimatorSet: AnimatorSet = AnimatorSet()

    //onDraw variables
    private val pieChartPaint = Paint()
    private val linePaint = Paint()
    private val textPaint = Paint()
    private val semiTransparentPaint = Paint()

    private var pieChartBounds = RectF()
    private var halfRadiusBounds = RectF()
    private var decorationBounds = RectF()
    private var textUnderlineBounds = RectF()

    private var selectionMatrix = Matrix()

    private var slicePath = Path()
    private var sliceDecorationPath = Path()
    private var sliceInfoPath = Path()
    private var sliceInfoTextPath = Path()
    private var sliceInfoPathMeasure = PathMeasure()

    private var centerX: Float = 0f
    private var centerY: Float = 0f

    private var startAngle: Float = 0f
    private var sweepAngle: Float = 0f
    private var sliceCenterAngle: Float = 0f
    private var categoryPercents: Float = 0f
    private var circleDegrees: Float = 359.9F //Workaround for passing through 360 degree arcTo issue

    private var infoTextUnderlineLength: Float = 0f
    private var radius: Float = 0f
    private var clickedSlice: Int = -1
    private var previousClickedSlice: Int = -1

    private lateinit var percents: String
    private var percentsWidth: Float = 0f

    private var listener: OnPieChartClickListener? = null

    init {
        setWillNotDraw(false)

        post {
            initRect()
        }

        initPaint()
        initAnimator()
    }

    fun setTransactions(transactions: List<FullTransaction>) {
        clearValues()

        val transactionsGroup = transactions.groupBy { it.category }
        transactionsGroup.forEach {
            categories.add(it.key)
            categoriesSum.add(it.value.sumBy { it.transaction.value })
        }
        transactionTotal = transactions.map { it.transaction }.sumBy { it.value }

        startAnimation()
    }

    fun setOnSliceClickListener(listener: OnPieChartClickListener) {
        this.listener = listener
    }

    private fun clearValues() {
        categories.clear()
        categoriesSum.clear()
        previousClickedSlice = -1
        clickedSlice = -1
    }

    private fun initRect() {
        viewWidth = width
        viewHeight = height

        radius = viewWidth / 5f

        infoTextUnderlineLength = radius / 2f

        pieChartBounds.left = viewWidth / 2f - radius
        pieChartBounds.top = viewHeight / 2f - radius + dip(8)
        pieChartBounds.right = viewWidth / 2f + radius
        pieChartBounds.bottom = pieChartBounds.top + radius * 2

        centerX = pieChartBounds.centerX()
        centerY = pieChartBounds.centerY()

        halfRadiusBounds.left = centerX - radius / 2
        halfRadiusBounds.top = centerY - radius / 2
        halfRadiusBounds.right = centerX + radius / 2
        halfRadiusBounds.bottom = centerY + radius / 2

        decorationBounds.left = halfRadiusBounds.left - radius / 2 * 0.15f
        decorationBounds.top = halfRadiusBounds.top - radius / 2 * 0.15f
        decorationBounds.right = halfRadiusBounds.right + radius / 2 * 0.15f
        decorationBounds.bottom = halfRadiusBounds.bottom + radius / 2 * 0.15f
    }

    private fun initPaint() {
        pieChartPaint.apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            strokeWidth = 0.5f
            strokeCap = Cap.ROUND
        }

        linePaint.apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeJoin = Join.ROUND
            strokeCap = Cap.ROUND
            strokeWidth = dip(1).toFloat()
            color = Color.GRAY
        }

        textPaint.apply {
            isAntiAlias = true
            color = getColor(R.color.pie_chart_fragment_big_text_color)
            textSize = sp(16).toFloat()
            typeface = Typeface.SANS_SERIF
        }

        semiTransparentPaint.apply {
            isAntiAlias = true
            color = getColor(R.color.pie_chart_inner_circle_color)
            style = Paint.Style.FILL
        }
    }

    private fun initAnimator() {
        infoAppearingAnimator.duration = 500
        infoAppearingAnimator.addUpdateListener {
            infoInterpolation = it.animatedValue as Float
            invalidate()
        }

        angleAnimator.duration = 500
        angleAnimator.addUpdateListener {
            angleInterpolation = it.animatedValue as Float
            invalidate()
        }

        selectionAnimator.duration = 500
        selectionAnimator.addUpdateListener {
            selectionInterpolation = it.animatedValue as Float
            invalidate()
        }

        unSelectionAnimator.duration = 500
        unSelectionAnimator.addUpdateListener {
            unSelectionInterpolation = it.animatedValue as Float
        }


        animatorSet.playSequentially(angleAnimator, infoAppearingAnimator)
        animatorSet.startDelay = 300
        animatorSet.interpolator = AccelerateDecelerateInterpolator()

        selectionAnimatorSet.playTogether(selectionAnimator, unSelectionAnimator)
        selectionAnimatorSet.startDelay = 100
        selectionAnimatorSet.interpolator = AccelerateDecelerateInterpolator()
    }

    private fun startAnimation() {
        animatorSet.cancel()
        animatorSet.start()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val availableWidth = MeasureSpec.getSize(widthMeasureSpec)
        val radius = availableWidth / 5F
        setMeasuredDimension(availableWidth, (radius * 3).toInt())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (categories.isEmpty()) return

        for (index in 0 until categories.size) {
            drawPieChart(canvas, index)
        }

        startAngle = 0f
        sweepAngle = 0f
        sliceCenterAngle = 0f
        categoryPercents = 0f
    }

    private fun drawPieChart(canvas: Canvas, index: Int) {
        categoryPercents = categoriesSum[index] / transactionTotal
        sweepAngle = if (transactionTotal == 0f) 0f else (circleDegrees * categoryPercents)
        sliceCenterAngle = Math.round(startAngle + (sweepAngle / 2f)).toFloat()

        pieChartPaint.color = categories[index].color

        percents = "${Math.round(categoryPercents * 100f)}%"
        percentsWidth = textPaint.measureText(percents)

        createPieChartSlice(slicePath, pieChartBounds, halfRadiusBounds, startAngle, sweepAngle, angleInterpolation)
        createPieChartSliceDecoration(sliceDecorationPath, decorationBounds, halfRadiusBounds, startAngle, sweepAngle, angleInterpolation)
        createSliceInfo(sliceInfoPath, maybeFixAngle(sliceCenterAngle), sliceInfoPathMeasure, infoInterpolation)
        createPieChartSliceText(sliceInfoPath, sliceInfoTextPath, sliceInfoPathMeasure, selectionMatrix, maybeFixAngle(sliceCenterAngle), textUnderlineBounds)

        if (index == clickedSlice) {
            selectSlice(slicePath, startAngle, sweepAngle, selectionMatrix, selectionInterpolation, radius / 15f)
            selectSlice(sliceDecorationPath, startAngle, sweepAngle, selectionMatrix, selectionInterpolation, radius / 15f)
            selectSlice(sliceInfoPath, startAngle, sweepAngle, selectionMatrix, selectionInterpolation, radius / 15f)
            selectSlice(sliceInfoTextPath, startAngle, sweepAngle, selectionMatrix, selectionInterpolation, radius / 15f)
        }

        if (index == previousClickedSlice) {
            selectSlice(slicePath, startAngle, sweepAngle, selectionMatrix, unSelectionInterpolation, radius / 15f)
            selectSlice(sliceDecorationPath, startAngle, sweepAngle, selectionMatrix, unSelectionInterpolation, radius / 15f)
            selectSlice(sliceInfoPath, startAngle, sweepAngle, selectionMatrix, unSelectionInterpolation, radius / 15f)
            selectSlice(sliceInfoTextPath, startAngle, sweepAngle, selectionMatrix, unSelectionInterpolation, radius / 15f)
        }

        canvas.drawPath(slicePath, pieChartPaint)
        canvas.drawPath(sliceDecorationPath, semiTransparentPaint)
        canvas.drawPath(sliceInfoPath, linePaint)
        canvas.drawTextOnPath(percents, sliceInfoTextPath, getTextOffset(sliceCenterAngle, textUnderlineBounds, percentsWidth), -16f, textPaint)

        startAngle += sweepAngle
    }

    private fun createPieChartSlice(path: Path, outerRect: RectF, innerRect: RectF, startAngle: Float, sweepAngle: Float, interpolation: Float) {
        path.reset()
        path.arcTo(outerRect, startAngle * interpolation, sweepAngle * interpolation)
        path.lineTo(circumferenceX(innerRect.width(), startAngle + sweepAngle, interpolation),
                circumferenceY(innerRect.width(), startAngle + sweepAngle, interpolation))
        path.arcTo(innerRect, (startAngle + sweepAngle) * interpolation, -sweepAngle * interpolation)
        path.lineTo(circumferenceX(innerRect.width(), startAngle, interpolation),
                circumferenceY(innerRect.width(), startAngle, interpolation))
    }

    private fun createSliceInfo(path: Path, angle: Float, measure: PathMeasure, interpolation: Float) {
        path.reset()
        path.moveTo(circumferenceX((radius / 5) * 4, angle, 1f),
                circumferenceY((radius / 5) * 4, angle, 1f))
        path.lineTo(circumferenceX(radius * 1.25f, angle, 1f),
                circumferenceY(radius * 1.25f, angle, 1f))
        path.lineTo(circumferenceX(radius * 1.25f, angle, 1f) + if (getAngleSide(angle) == RIGHT) infoTextUnderlineLength else (-infoTextUnderlineLength),
                circumferenceY(radius * 1.25f, angle, 1f))

        measure.setPath(path, false)
        path.reset()
        measure.getSegment(0f, measure.length * interpolation, path, true)
    }

    private fun createPieChartSliceDecoration(path: Path, outerRect: RectF, innerRect: RectF, startAngle: Float, sweepAngle: Float, interpolation: Float) {
        path.reset()
        path.arcTo(outerRect, startAngle * interpolation, sweepAngle * interpolation)
        path.lineTo(circumferenceX(outerRect.width() / 2, startAngle + sweepAngle, interpolation),
                circumferenceY(outerRect.width() / 2, startAngle + sweepAngle, interpolation))
        path.arcTo(innerRect, (startAngle + sweepAngle) * interpolation, -sweepAngle * interpolation)
        path.lineTo(circumferenceX(outerRect.width() / 2, startAngle, interpolation),
                circumferenceY(outerRect.width() / 2, startAngle, interpolation))
    }

    private fun createPieChartSliceText(fromPath: Path, toPath: Path, measure: PathMeasure, matrix: Matrix, angle: Float, bounds: RectF) {
        toPath.reset()
        measure.setPath(fromPath, false)
        measure.getSegment(infoTextUnderlineLength, measure.length, toPath, true)
        if (getAngleSide(angle) == LEFT) {
            toPath.computeBounds(bounds, true)
            matrix.reset()
            matrix.postRotate(180f, bounds.centerX(), bounds.centerY())
            toPath.transform(matrix)
        }
    }

    private fun maybeFixAngle(angle: Float): Float {
        return if (angle == 90f || angle == 180f || angle == 270f || angle == 360f || angle == 0f) {
            angle + 1
        } else angle
    }

    private fun getAngleSide(angle: Float): Int =
            when (angle) {
                in 1.0..89.0 -> RIGHT
                in 271.0..359.0 -> RIGHT
                in 91.0..269.0 -> LEFT
                else -> getAngleSide(maybeFixAngle(angle))
            }

    private fun getTextOffset(angle: Float, bounds: RectF, width: Float) =
            when (getAngleSide(angle)) {
                RIGHT -> bounds.width() - width
                LEFT -> 0f
                else -> 0f
            }

    private fun selectSlice(path: Path, startAngle: Float, sweepAngle: Float, matrix: Matrix, interpolation: Float, translationLength: Float) {
        matrix.reset()
        matrix.setTranslate(translationX(startAngle, sweepAngle, interpolation, translationLength),
                translationY(startAngle, sweepAngle, interpolation, translationLength))
        path.transform(matrix)
    }

    private fun circumferenceX(r: Float, angle: Float, interpolation: Float): Float {
        return centerX + r * Math.cos(Math.toRadians((angle * interpolation).toDouble())).toFloat()
    }

    private fun circumferenceY(r: Float, angle: Float, interpolation: Float): Float {
        return centerY + r * Math.sin(Math.toRadians((angle * interpolation).toDouble())).toFloat()
    }

    private fun translationX(startAngle: Float, sweepAngle: Float, interpolation: Float, translationLength: Float): Float {
        return (circumferenceX(translationLength, startAngle + (sweepAngle / 2), 1f) - centerX) * interpolation
    }

    private fun translationY(startAngle: Float, sweepAngle: Float, interpolation: Float, translationLength: Float): Float {
        return (circumferenceY(translationLength, startAngle + (sweepAngle / 2), 1f) - centerY) * interpolation
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val xPos = event.x - centerX
            val yPos = event.y - centerY
            var startAngle = 0f
            var sweepAngle: Float
            var categoryPercents: Float
            var clickAngle = Math.toDegrees(Math.atan2(yPos.toDouble(), xPos.toDouble()))

            if (clickAngle < 0) clickAngle += 360

            for (index in 0 until categories.size) {
                categoryPercents = categoriesSum[index] / transactionTotal
                sweepAngle = if (transactionTotal == 0f) 0f else (360f * categoryPercents)

                if (pieChartBounds.contains(event.x, event.y)
                        && clickAngle > startAngle
                        && clickAngle < startAngle + sweepAngle) {
                    if (clickedSlice != index) {
                        previousClickedSlice = clickedSlice
                        clickedSlice = index
                        if (categories.size > 1) {
                            selectionAnimatorSet.cancel()
                            selectionAnimatorSet.start()
                        }
                        listener?.let {
                            it.onSliceClick(categories[index].id.toInt())
                        }
                    }
                    break
                }
                startAngle += sweepAngle
            }
        }
        return false
    }

    interface OnPieChartClickListener {
        fun onSliceClick(categoryId: Int)
    }
}