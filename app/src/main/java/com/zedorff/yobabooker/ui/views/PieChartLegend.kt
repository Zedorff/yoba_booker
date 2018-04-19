package com.zedorff.yobabooker.ui.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.zedorff.yobabooker.R
import org.jetbrains.anko.dip
import org.jetbrains.anko.sp

class PieChartLegend(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var categories: List<String> = emptyList()

    private var legendMarkerBounds = RectF()

    private var categoryColors: IntArray = resources.getIntArray(R.array.rainbow)

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
        legendMarkerSize = dip(8).toFloat()
        legendTextStartOffset = dip(4).toFloat()
        legendLastX = legendMarkerSize

        initPaint()
        initRect()
    }

    fun setCategories(categories: List<String>) {
        this.categories = categories
        requestLayout()
    }

    private fun initPaint() {
        legendTextPaint.apply {
            isAntiAlias = true
            color = Color.BLACK
            textSize = sp(10).toFloat()
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

    private fun initRect() {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        var lastX = legendMarkerSize
        var textLines = 1
        for (index in 0 until categories.size) {
            if (lastX + legendMarkerSize + legendTextStartOffset + legendTextPaint.measureText(categories[index]) > width - legendMarkerSize) {
                textLines++
                lastX = legendMarkerSize
            }
            lastX = (lastX + legendMarkerSize) + legendTextPaint.measureText(categories[index]) + legendMarkerSize * 2
        }

        setMeasuredDimension(width, (legendMarkerSize + (legendMarkerSize + (textLines * legendMarkerSize) + legendMarkerSize)).toInt())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (categories.isEmpty()) return

        canvas.drawPaint(linePaint)

        for (index in 0 until categories.size) {
            drawLegend(canvas, index)
        }

        legendLastX = legendMarkerSize
        legendTextLines = 1f
    }

    private fun drawLegend(canvas: Canvas, index: Int) {
        legendMarkerPaint.color = categoryColors[index]

        if (legendLastX + legendMarkerSize + legendTextStartOffset + legendTextPaint.measureText(categories[index]) > width - legendMarkerSize) {
            legendTextLines++
            legendLastX = legendMarkerSize
        }

        legendMarkerBounds.top = legendTextLines * legendMarkerSize + (legendTextLines - 1) * legendMarkerSize
        legendMarkerBounds.bottom = legendMarkerBounds.top + legendMarkerSize
        legendMarkerBounds.left = legendLastX
        legendMarkerBounds.right = legendMarkerBounds.left + legendMarkerSize

        createLegendMarker(legendMarkerPath, legendMarkerBounds)
        createLegendInfo(legendMarkerTextPath, legendMarkerBounds, legendTextPaint.measureText(categories[index]))
        canvas.drawPath(legendMarkerPath, legendMarkerPaint)
        canvas.drawTextOnPath(categories[index], legendMarkerTextPath, 0f, 0f, legendTextPaint)

        legendLastX = legendMarkerBounds.right + legendTextPaint.measureText(categories[index]) + legendMarkerSize * 2
    }

    private fun createLegendMarker(path: Path, rect: RectF) {
        path.reset()
        path.addRect(rect, Path.Direction.CW)
    }

    private fun createLegendInfo(path: Path, markerRect: RectF, width: Float) {
        path.reset()
        path.moveTo(markerRect.right + legendTextStartOffset, markerRect.bottom)
        path.lineTo(markerRect.right + legendTextStartOffset + width, markerRect.bottom)
    }
}