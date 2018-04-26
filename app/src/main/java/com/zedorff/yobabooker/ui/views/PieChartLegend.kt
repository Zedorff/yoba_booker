package com.zedorff.yobabooker.ui.views

import android.content.Context
import android.graphics.*
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import com.zedorff.yobabooker.R
import com.zedorff.yobabooker.app.extensions.getColor
import com.zedorff.yobabooker.app.utils.ColorGenerator
import com.zedorff.yobabooker.model.db.entities.CategoryEntity
import org.jetbrains.anko.dip
import org.jetbrains.anko.sp

class PieChartLegend(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var categories: List<CategoryEntity> = emptyList()

    private var legendMarkerBounds = RectF()

    private var legendMarkerPath = Path()
    private var legendMarkerTextPath = Path()

    private val legendMarkerPaint = Paint()
    private val legendTextPaint = Paint()
    private val linePaint = Paint()

    private var legendLastX: Float = 0f

    private var legendMarkerSize: Float = 0f
    private var legendTextStartOffset: Float = 0f
    private var legendTextLines: Float = 1f

    init {
        setWillNotDraw(false)
        legendMarkerSize = dip(8).toFloat()
        legendTextStartOffset = dip(4).toFloat()
        legendLastX = legendMarkerSize

        initPaint()
    }

    fun setCategories(categories: List<CategoryEntity>) {
        this.categories = categories
        requestLayout()
    }

    private fun initPaint() {
        legendTextPaint.apply {
            isAntiAlias = true
            color = getColor(R.color.pie_chart_fragment_small_text_color)
            textSize = sp(10).toFloat()
            typeface = Typeface.SANS_SERIF
        }

        linePaint.apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            strokeWidth = dip(1).toFloat()
            color = Color.GRAY
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        var lastX = legendMarkerSize
        var textLines = 1
        for (index in 0 until categories.size) {
            if (lastX + legendMarkerSize + legendTextStartOffset + legendTextPaint.measureText(categories[index].name) > width - legendMarkerSize) {
                textLines++
                lastX = legendMarkerSize
            }
            lastX = (lastX + legendMarkerSize) + legendTextPaint.measureText(categories[index].name) + legendMarkerSize * 2
        }

        setMeasuredDimension(width, ((legendMarkerSize * 2 * textLines) + legendMarkerSize).toInt())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (categories.isEmpty()) return

        for (index in 0 until categories.size) {
            drawLegend(canvas, index)
        }

        legendLastX = legendMarkerSize
        legendTextLines = 1f
    }

    private fun drawLegend(canvas: Canvas, index: Int) {
        legendMarkerPaint.color = categories[index].color

        if (legendLastX + legendMarkerSize + legendTextStartOffset + legendTextPaint.measureText(categories[index].name) > width - legendMarkerSize) {
            legendTextLines++
            legendLastX = legendMarkerSize
        }

        legendMarkerBounds.top = legendTextLines * legendMarkerSize + (legendTextLines - 1) * legendMarkerSize
        legendMarkerBounds.bottom = legendMarkerBounds.top + legendMarkerSize
        legendMarkerBounds.left = legendLastX
        legendMarkerBounds.right = legendMarkerBounds.left + legendMarkerSize

        createLegendMarker(legendMarkerPath, legendMarkerBounds)
        createLegendInfo(legendMarkerTextPath, legendMarkerBounds, legendTextPaint.measureText(categories[index].name))
        canvas.drawPath(legendMarkerPath, legendMarkerPaint)
        canvas.drawTextOnPath(categories[index].name, legendMarkerTextPath, 0f, 0f, legendTextPaint)

        legendLastX = legendMarkerBounds.right + legendTextPaint.measureText(categories[index].name) + legendMarkerSize * 2
    }

    private fun createLegendMarker(path: Path, rect: RectF) {
        path.reset()
        path.addRoundRect(rect, rect.width() / 4f, rect.height() / 4f,  Path.Direction.CW)
    }

    private fun createLegendInfo(path: Path, markerRect: RectF, width: Float) {
        path.reset()
        path.moveTo(markerRect.right + legendTextStartOffset, markerRect.bottom)
        path.lineTo(markerRect.right + legendTextStartOffset + width, markerRect.bottom)
    }
}