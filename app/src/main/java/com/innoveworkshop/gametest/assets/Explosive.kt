package com.innoveworkshop.gametest.assets

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.innoveworkshop.gametest.engine.Circle
import com.innoveworkshop.gametest.engine.Vector

class Explosive(
    position: Vector?,
    radius: Float,
    dropRate: Float,
    val throwSpeed: Float,
    color: Int
) : Circle(position, radius, color) {

    var dropRate: Float = 0f
    var gravity: Float = 1.5f
    private var lifeSpan: Float = 30f
    private var hasExploded: Boolean = false
    private val maxDistance = 450f
    private val intensityEvil = 1f

    init {
        this.dropRate = dropRate
    }

    override fun onFixedUpdate() {
        if (hasExploded) return

        super.onFixedUpdate()

        lifeSpan -= 1f

        if (!isFloored) {
            dropRate += gravity
            position.y += dropRate
        }

        if (isFloored) {
            position.y = gameSurface!!.height - radius - 200
            dropRate = 0f
        }

        if (!hitLeftWall() && !hitRightWall()) {
            position.x += throwSpeed
        }

        if (hitCeiling()) {
            dropRate = 0f
        }

        if (lifeSpan <= 0f) {
            explode()
        }
    }

    private fun explode() {
        if (hasExploded) return
        hasExploded = true

        for (gameObject in gameSurface!!.gameObjects) {
            if (gameObject is EvilWife) {
                applyImpulseWife(gameObject)
            }
        }

        gameSurface!!.addDelayedRemoval(this)
    }

    private fun applyImpulseWife(evilWife: EvilWife) {
        val evilPosAway = Vector(evilWife.position.x - position.x, evilWife.position.y - position.y)
        val distance = Math.sqrt((evilPosAway.x*evilPosAway.x + evilPosAway.y*evilPosAway.y).toDouble()).toFloat()
        val direction = Vector(evilPosAway.x / distance, evilPosAway.y / distance)

        if (distance <= maxDistance) {
            val impulse = Vector(
                intensityEvil* (maxDistance - distance) * direction.x,
                intensityEvil * (maxDistance - distance) * direction.y
            )

            evilWife.velocityX += impulse.x
            evilWife.dropRate += impulse.y

            if (distance > 300f)
                evilWife.health -= 50
            if (distance <= 300f && distance > 69f)
                evilWife.health -= 100
            if (distance <= 69f && distance > 0f)
                evilWife.health -= 200
        }
    }

    fun drawExplosionEffect(canvas: Canvas) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.argb(100, 255, 0, 0) // Semi-transparent red
        paint.style = Paint.Style.STROKE
        canvas.drawCircle(position.x, position.y, maxDistance, paint)
        canvas.drawCircle(position.x, position.y, maxDistance-1, paint)
        canvas.drawCircle(position.x, position.y, maxDistance-2, paint)
        canvas.drawCircle(position.x, position.y, maxDistance-3, paint)
    }
}