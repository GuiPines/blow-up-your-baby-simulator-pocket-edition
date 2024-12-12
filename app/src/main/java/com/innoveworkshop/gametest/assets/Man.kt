package com.innoveworkshop.gametest.assets

import android.util.Log
import com.innoveworkshop.gametest.engine.Rectangle
import com.innoveworkshop.gametest.engine.Vector

class Man(
    position: Vector?,
    width: Float,
    height: Float,
    dropRate: Float,
    color: Int
) : Rectangle(position, width, height, color) {

    var dropRate: Float = 0f
    var gravity: Float = 1.5f
    private var isWalkingRight: Boolean = false
    private var isWalkingLeft: Boolean = false
    var isJumping: Boolean = false
    var lookingLeft: Boolean = true

    init {
        this.dropRate = dropRate
    }

    fun startWalkingLeft() {
        if (!hitLeftWall())
            isWalkingLeft = true
        else
            isWalkingLeft = false

        lookingLeft = true
        Log.d("Man", "Start walking left: lookingLeft = $lookingLeft")
    }
    fun stopWalkingLeft() {
        isWalkingLeft = false
        lookingLeft = true
    }

    fun startWalkingRight() {
        if (!hitRightWall())
            isWalkingRight = true
        else
            isWalkingRight = false

        lookingLeft = false
        Log.d("Man", "Start walking left: lookingLeft = $lookingLeft")
    }
    fun stopWalkingRight() {
        isWalkingRight = false
        lookingLeft = false
    }

    fun jump() {
        dropRate = -20f
        isJumping = true
    }

    override fun onFixedUpdate() {
        super.onFixedUpdate()

        if (!isFloored || isJumping) {
            dropRate += gravity
            position.y += dropRate

            if (dropRate > 0) {
                isJumping = false
            }
        }

        if (isFloored) {
            position.y = gameSurface!!.height - height / 2 - 200
            dropRate = 0f
        }

        if (isWalkingLeft && !hitLeftWall()) {
            position.x -= 15
        }

        if (isWalkingRight && !hitRightWall()) {
            position.x += 15
        }

        if (hitCeiling()) {
            dropRate = 0f
        }
    }
}