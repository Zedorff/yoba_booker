package com.zedorff.yobabooker.ui.views

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.ArrayMap
import android.util.AttributeSet
import android.view.View
import android.view.animation.BounceInterpolator
import com.zedorff.yobabooker.R
import com.zedorff.yobabooker.app.extensions.*
import com.zedorff.yobabooker.app.utils.ColorGenerator
import com.zedorff.yobabooker.model.db.embeded.FullTransaction
import org.jetbrains.anko.dip
import org.jetbrains.anko.sp
import java.util.*

class ColumnBarGraph(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    companion object {
        private const val VALUES_COUNT = 8
        private const val VALUE_ROUND_TO = 50.0
    }

    private var transactionsMap: Map<Int, Float> = ArrayMap()
    private var category: String = ""
    private var daysDescriptionString: String = ""
    private var valuesDescriptionString: String = ""

    private var linePaint = Paint()
    private var graphPaint = Paint()
    private var textPaint = Paint()
    private var daysPaint = Paint()
    private var valuesPaint = Paint()
    private var barPaint = Paint()

    private var graphBounds = RectF()
    private var graphNameBounds = RectF()
    private var valuesBounds = RectF()
    private var valuesDescriptionBounds = RectF()
    private var daysBounds = RectF()
    private var daysDescriptionBounds = RectF()
    private var barBounds = RectF()

    private var graphAnimator = ObjectAnimator.ofFloat(0f, 1f)

    private var graphInterpolation: Float = 0f

    private var viewWidth: Int = 0
    private var viewHeight: Int = 0

    private var graphAxisOffset: Float = 0f
    private var valuesTextSize: Float = 0f
    private var daysCount: Int = 0

    private var dayWidth: Float = 0f
    private var valueHeight: Float = 0f

    private var dayCurrentX: Float = 0f
    private var valueCurrentY: Float = 0f

    private var valueMultiplier: Int = 0

    private var daysDescriptionStringHeight: Float = 0f
    private var daysDescriptionStringWidth: Float = 0f

    private var valuesDescriptionStringHeight: Float = 0f
    private var valuesDescriptionStringWidth: Float = 0f

    private var graphNameStringHeight: Float = 0f
    private var graphNameStringWidth: Float = 0f

    private var valuesCoordinates: FloatArray = FloatArray(VALUES_COUNT)
    private var daysCoordinates: FloatArray

    private var valueTextBound = Rect()

    init {
        setWillNotDraw(false)
        daysDescriptionString = resources.getString(R.string.description_days_of_month)
        valuesDescriptionString = resources.getString(R.string.description_outcome_transactions)

        graphAxisOffset = dip(4).toFloat()
        valuesTextSize = sp(6).toFloat()
        daysCount = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH) + 1 //Due to start from 0
        daysCoordinates = FloatArray(daysCount)

        initPaint()
        initAnimator()
        post {
            initRect()
            initSize()
            startAnimator()
        }
    }

    private fun initPaint() {
        linePaint.apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            strokeWidth = 0.5f
            color = Color.GRAY
        }

        graphPaint.apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = dip(1).toFloat()
            color = Color.BLACK
        }

        textPaint.apply {
            isAntiAlias = true
            color = getColor(R.color.pie_chart_fragment_big_text_color)
            textSize = sp(18).toFloat()
            typeface = Typeface.SANS_SERIF
        }

        daysPaint.apply {
            isAntiAlias = true
            color = getColor(R.color.pie_chart_fragment_small_text_color)
            textAlign = Paint.Align.CENTER
            textSize = sp(5).toFloat()
            typeface = Typeface.SANS_SERIF
        }

        valuesPaint.apply {
            isAntiAlias = true
            color = getColor(R.color.pie_chart_fragment_small_text_color)
            textAlign = Paint.Align.RIGHT
            textSize = valuesTextSize
            typeface = Typeface.SANS_SERIF
        }

        barPaint.apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            strokeWidth = 0.5f
            strokeCap = Paint.Cap.ROUND
            color = Color.RED
        }
    }

    private fun initAnimator() {
        graphAnimator.duration = 500
        graphAnimator.interpolator = BounceInterpolator()
        graphAnimator.addUpdateListener {
            graphInterpolation = it.animatedValue as Float
            invalidate()
        }
    }

    private fun startAnimator() {
        graphAnimator.cancel()
        graphAnimator.start()
    }

    private fun initRect() {
        viewWidth = width
        viewHeight = height

        graphNameBounds.top = 0f
        graphNameBounds.bottom = viewHeight * 0.2f
        graphNameBounds.left = 0f
        graphNameBounds.right = viewWidth.toFloat()

        valuesDescriptionBounds.top = graphNameBounds.bottom
        valuesDescriptionBounds.bottom = viewHeight * 0.7f
        valuesDescriptionBounds.left = 0f
        valuesDescriptionBounds.right = viewWidth * 0.1f

        valuesBounds.top = valuesDescriptionBounds.top
        valuesBounds.bottom = valuesDescriptionBounds.bottom
        valuesBounds.left = valuesDescriptionBounds.right
        valuesBounds.right = viewWidth * 0.15f

        graphBounds.top = valuesDescriptionBounds.top
        graphBounds.bottom = valuesDescriptionBounds.bottom
        graphBounds.left = valuesBounds.right
        graphBounds.right = viewWidth * 0.9f

        daysBounds.top = graphBounds.bottom
        daysBounds.bottom = graphBounds.bottom + viewHeight * 0.15f
        daysBounds.left = graphBounds.left
        daysBounds.right = graphBounds.right

        daysDescriptionBounds.top = daysBounds.bottom
        daysDescriptionBounds.bottom = viewHeight.toFloat()
        daysDescriptionBounds.left = graphBounds.left
        daysDescriptionBounds.right = graphBounds.right

        barBounds.bottom = graphBounds.bottom
    }

    private fun initSize() {
        dayWidth = daysBounds.width() / (daysCount - 1) //Due to counting from 1
        valueHeight = valuesBounds.height() / (VALUES_COUNT - 1) //Due to counting from 1

        val bounds = Rect()
        textPaint.getTextBounds(daysDescriptionString, 0, daysDescriptionString.length, bounds)
        daysDescriptionStringHeight = bounds.height().toFloat()
        daysDescriptionStringWidth = bounds.width().toFloat()

        textPaint.getTextBounds(valuesDescriptionString, 0, valuesDescriptionString.length, bounds)
        valuesDescriptionStringHeight = bounds.height().toFloat()
        valuesDescriptionStringWidth = bounds.width().toFloat()
    }

    fun setTransactions(list: List<FullTransaction>) {
        if (!list.isEmpty()) {
            category = list[0].category.name
            barPaint.color = list[0].category.color
        }

        transactionsMap = list.map { fullTransaction ->
            fullTransaction.transaction
        }.groupBy { transaction ->
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = transaction.date
            return@groupBy calendar.get(Calendar.DAY_OF_MONTH)
        }.mapValues { mapEntry ->
            mapEntry.value.sumBy { transaction ->
                transaction.value.negate()
            }
        }

        transactionsMap.values.max()?.let {
            valueMultiplier = roundTransactionValue(it / VALUES_COUNT)
        }
        startAnimator()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        setMeasuredDimension(width, width / 2)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        with(canvas) {
            drawGraphName(this)
            drawGraphAxis(this)
            drawValues(this)
            drawValuesDescription(this)
            drawDays(this)
            drawDaysDescription(this)
            drawGraphBars(this)
        }
    }

    private fun drawGraphName(canvas: Canvas) {
        var bounds = Rect()
        textPaint.getTextBounds(category, 0, category.length, bounds)
        graphNameStringHeight = bounds.height().toFloat()
        graphNameStringWidth = bounds.width().toFloat()
        canvas.drawText(category,
                graphNameBounds.halfWidth() - graphNameStringWidth.half(),
                graphNameBounds.halfHeight(),
                textPaint)
    }

    private fun drawGraphAxis(canvas: Canvas) {
        canvas.drawLine(graphBounds.left, graphBounds.bottom, graphBounds.right, graphBounds.bottom, linePaint) //X Axis
        canvas.drawLine(graphBounds.left, graphBounds.bottom, graphBounds.left, graphBounds.top, linePaint) //Y Axis
    }

    private fun drawValues(canvas: Canvas) {
        for (index in 0 until VALUES_COUNT) {
            valueCurrentY = valuesBounds.bottom - index * valueHeight
            valuesPaint.getTextBounds((valueMultiplier * index).toString(), 0, 1, valueTextBound)
            canvas.drawLine(
                    valuesBounds.right - graphAxisOffset,
                    valueCurrentY,
                    graphBounds.right,
                    valueCurrentY,
                    linePaint
            )
            valuesPaint.textSize = valuesTextSize * graphInterpolation
            canvas.drawText(
                    (valueMultiplier * index).toString(),
                    valuesBounds.right - graphAxisOffset * 2,
                    valueCurrentY + valueTextBound.halfHeight(),
                    valuesPaint
            )
            valuesCoordinates[index] = valueCurrentY
        }
    }

    private fun drawValuesDescription(canvas: Canvas) {
        canvas.save()
        canvas.rotate(-90f, valuesDescriptionBounds.centerX(), valuesDescriptionBounds.centerY())
        canvas.drawText(valuesDescriptionString,
                valuesDescriptionBounds.left + valuesDescriptionBounds.halfWidth() - valuesDescriptionStringWidth.half(),
                valuesDescriptionBounds.top + valuesDescriptionBounds.halfHeight() + valuesDescriptionStringHeight.half(),
                textPaint)
        canvas.restore()
    }

    private fun drawDays(canvas: Canvas) {
        for (index in 0 until daysCount) {
            dayCurrentX = daysBounds.left + (index * dayWidth)
            canvas.drawLine(
                    dayCurrentX, daysBounds.top,
                    dayCurrentX, daysBounds.top + graphAxisOffset,
                    linePaint
            )
            canvas.drawText(index.toString(),
                    dayCurrentX,
                    daysBounds.top + graphAxisOffset * 3,
                    daysPaint)
            daysCoordinates[index] = dayCurrentX
        }
    }

    private fun drawDaysDescription(canvas: Canvas) {
        canvas.drawText(daysDescriptionString,
                daysDescriptionBounds.left + daysDescriptionBounds.halfWidth() - daysDescriptionStringWidth / 2f,
                daysDescriptionBounds.top + daysDescriptionBounds.halfHeight() - daysDescriptionStringHeight / 2f,
                textPaint)
    }

    private fun drawGraphBars(canvas: Canvas) {
        transactionsMap.forEach {
            barBounds.top = barBounds.bottom - (valuesBounds.height() / (valueMultiplier * (VALUES_COUNT - 1)) * it.value * graphInterpolation)
            barBounds.left = daysCoordinates[it.key] - dayWidth / 4f
            barBounds.right = barBounds.left + dayWidth.half()
            canvas.drawRect(barBounds, barPaint)
            canvas.drawText(it.value.toInt().toString(),
                    barBounds.left + barBounds.halfWidth(),
                    barBounds.top - barBounds.halfWidth(),
                    daysPaint
            )
        }
    }

    private fun roundTransactionValue(value: Float): Int {
        return (Math.ceil(value / VALUE_ROUND_TO) * VALUE_ROUND_TO).toInt()
    }
}