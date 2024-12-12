package com.innoveworkshop.gametest.assets

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.Log
import com.innoveworkshop.gametest.engine.Rectangle
import com.innoveworkshop.gametest.engine.Vector

class HealthBar(
    val target: EvilWife,
    position: Vector?,
    width: Float,
    height: Float,
    color: Int
) : Rectangle(position, width, height, color) {

    var onHealthZero: (() -> Unit)? = null

    private var isDepleted = false
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val text = "You killed her."

    init {
        textPaint.color = Color.rgb(130, 0, 20)
        textPaint.textSize = 60f
        textPaint.typeface = Typeface.DEFAULT_BOLD
    }

    override fun onFixedUpdate() {
        super.onFixedUpdate()

        this.width = target.health

        if (target.health <= 0) {
            onHealthZero?.invoke()
            isDepleted = true
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (isDepleted) {
            canvas!!.drawText(text, position.x, position.y+20f, textPaint)
        }
    }
}