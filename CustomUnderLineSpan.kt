package com.example.amsdemo.view

import android.graphics.Canvas
import android.graphics.Paint
import android.text.style.ReplacementSpan
import androidx.annotation.ColorInt
import com.example.amsdemo.MyApplication
import com.example.amsdemo.R

class CustomUnderLineSpan(
    private val textSize: Float,
    @ColorInt private val textColor: Int,
    lineWidth: Float? = null,
    @ColorInt lineColor: Int? = null,
) : ReplacementSpan() {

    private val lineWidth: Float =
        lineWidth ?: MyApplication.appContext.resources.getDimensionPixelSize(R.dimen.common_ui_1_dp).toFloat()
    private val lineColor: Int = lineColor ?: textColor

    override fun getSize(paint: Paint, text: CharSequence?, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
        fm ?: return 0
        text ?: return 0

        paint.textSize = textSize

        with(paint.fontMetricsInt) {
            fm.ascent = ascent
            fm.descent = descent + lineWidth.toInt()
            fm.bottom = fm.descent
            fm.leading = leading
            fm.top = top
        }

        return paint.measureText(text.toString(), start, end).toInt()
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence?,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        text ?: return

        paint.apply {
            isAntiAlias = true
            color = textColor
            textSize = this@CustomUnderLineSpan.textSize
        }
        canvas.drawText(text.toString(), start, end, x, y.toFloat(), paint)

        val startY = bottom - lineWidth
        val stopX = x + paint.measureText(text.toString())

        paint.apply {
            color = lineColor
            style = Paint.Style.STROKE
            strokeWidth = lineWidth
        }
        canvas.drawLine(x, startY, stopX, startY, paint)
    }
}
