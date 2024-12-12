package com.innoveworkshop.gametest.assets

import com.innoveworkshop.gametest.engine.Rectangle
import com.innoveworkshop.gametest.engine.Vector

class EvilWife(
    position: Vector?,
    width: Float,
    height: Float,
    dropRate: Float,
    velocityX: Float,
    color: Int
) : Rectangle(position, width, height, color) {

    var health: Float = 2000f

    var dropRate: Float = 0f
    var velocityX: Float = 0f
    var gravity: Float = 1.5f
    private var bounciness: Float = 0.5f // from 0 to 1 (higher unrealistically)
    private var friction: Float = 0.9f

    init {
        this.dropRate = dropRate
        this.velocityX = velocityX
    }

    override fun onFixedUpdate() {
        super.onFixedUpdate()

        dropRate += gravity
        position.x += velocityX
        position.y += dropRate

        if (isFloored) {
            position.y = gameSurface!!.height - height / 2 - 200
            velocityX = velocityX * friction
            dropRate = dropRate * (-bounciness)
        }

        if (hitLeftWall()) {
            position.x = 100f + height / 2
            velocityX = velocityX * (-bounciness)
        }

        if (hitRightWall()) {
            position.x = gameSurface!!.width - width / 2 - 100
            velocityX = velocityX * (-bounciness)
        }

        if (hitCeiling()) {
            position.y = 100f + height / 2
            dropRate = dropRate * (-bounciness)
        }
    }
}