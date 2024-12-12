package com.innoveworkshop.gametest

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.innoveworkshop.gametest.assets.EvilWife
import com.innoveworkshop.gametest.assets.Explosive
import com.innoveworkshop.gametest.assets.HealthBar
import com.innoveworkshop.gametest.assets.Man
import com.innoveworkshop.gametest.engine.Circle
import com.innoveworkshop.gametest.engine.GameObject
import com.innoveworkshop.gametest.engine.GameSurface
import com.innoveworkshop.gametest.engine.Rectangle
import com.innoveworkshop.gametest.engine.Vector

class MainActivity : AppCompatActivity() {
    protected var gameSurface: GameSurface? = null
    protected var upButton: Button? = null
    protected var downButton: Button? = null
    protected var leftButton: Button? = null
    protected var rightButton: Button? = null

    protected var game: Game? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameSurface = findViewById<View>(R.id.gameSurface) as GameSurface
        game = Game()
        gameSurface!!.setRootGameObject(game)

        setupControls()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupControls() {

        upButton = findViewById<View>(R.id.up_button) as Button
        upButton!!.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    game!!.man?.jump() // Trigger jump immediately on button press
                    true
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // Optionally handle any actions on release (e.g., stop jump)
                    true
                }
                else -> false
            }
        }

        downButton = findViewById<View>(R.id.down_button) as Button
        downButton!!.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (gameSurface != null && game!!.man != null) {
                        val man = game!!.man!!
                        val speedX = if (man.lookingLeft) -14f else 14f
                        Log.d("Explosive", "Man lookingLeft: ${man.lookingLeft}, throwSpeed: $speedX")

                        val explosive = Explosive(
                            Vector(man.position.x, man.position.y - man.height / 2),
                            14f,
                            -10f,
                            speedX,
                            Color.rgb(255, 0, 0)
                        )
                        gameSurface!!.addGameObject(explosive)
                    }
                    true
                }
                else -> false
            }
        }

        leftButton = findViewById<View>(R.id.left_button) as Button
        leftButton!!.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    game!!.man?.startWalkingLeft()
                    game!!.man!!.lookingLeft = true
                    true
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    game!!.man?.stopWalkingLeft()
                    true
                }
                else -> false
            }
        }

        rightButton = findViewById<View>(R.id.right_button) as Button
        rightButton!!.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    game!!.man?.startWalkingRight()
                    game!!.man!!.lookingLeft = false
                    true
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    game!!.man?.stopWalkingRight()
                    true
                }
                else -> false
            }
        }
    }

    inner class Game : GameObject() {
        var man: Man? = null
        var nightmare: EvilWife? = null

        override fun onStart(surface: GameSurface?) {
            super.onStart(surface)

            val surface_width = surface!!.width.toFloat()
            val surface_height = surface.height.toFloat()

            val roomLeft = 100f
            val roomTop = 100f
            val roomWidth = surface_width - 200f
            val roomHeight = surface_height - 300f



            //BACKGROUND
            val bg = Rectangle(
                    Vector(0f, 0f),
                    surface_width*2, surface_height*2,
                    Color.rgb(100, 50, 20)
            )


            //BABY
            val baby = Rectangle(
                    Vector(surface_width-200, surface_height-300),
                    60f, 60f,
                    Color.rgb(210, 160, 130)
            )
            //PILLOW
            val pillow = Rectangle(
                    Vector(surface_width-180, surface_height-290),
                    80f, 20f,
                    Color.rgb(120, 120, 140)
            )
            //MATTRESS
            val mattress = Rectangle(
                    Vector(surface_width-320, surface_height-260),
                    400f, 50f,
                    Color.rgb(150, 150, 160)
            )
            //BLANKET
            val blanket1 = Rectangle(
                    Vector(surface_width-380, surface_height-270),
                    300f, 70f,
                    Color.rgb(60, 80, 110)
            )
            val blanket2 = Rectangle(
                    Vector(surface_width-265, surface_height-290),
                    70f, 60f,
                    Color.rgb(60, 80, 110)
            )
            //BLANKET VISUALS
            val square1 = Rectangle(
                    Vector(surface_width-265, surface_height-290),
                    50f, 50f,
                    Color.rgb(60, 100, 60)
            )
            val center1 = Rectangle(
                    Vector(surface_width-265, surface_height-290),
                    30f, 30f,
                    Color.rgb(60, 80, 110)
            )
            val square2 = Rectangle(
                    Vector(surface_width-360, surface_height-260),
                    50f, 50f,
                    Color.rgb(60, 100, 60)
            )
            val center2 = Rectangle(
                    Vector(surface_width-360, surface_height-260),
                    30f, 30f,
                    Color.rgb(60, 80, 110)
            )
            val square3 = Rectangle(
                    Vector(surface_width-500, surface_height-270),
                    50f, 50f,
                    Color.rgb(60, 100, 60)
            )
            val center3 = Rectangle(
                    Vector(surface_width-500, surface_height-270),
                    30f, 30f,
                    Color.rgb(60, 80, 110)
            )
            //BABY BED
            val bed1 = Rectangle(
                    Vector(surface_width-120, surface_height-300),
                    30f, 200f,
                    Color.rgb(180, 180, 190)
            )
            val bed2 = Rectangle(
                    Vector(surface_width-200, surface_height-300),
                    10f, 150f,
                    Color.rgb(180, 180, 190)
            )
            val bed3 = Rectangle(
                    Vector(surface_width-280, surface_height-300),
                    10f, 150f,
                    Color.rgb(180, 180, 190)
            )
            val bed4 = Rectangle(
                    Vector(surface_width-360, surface_height-300),
                    10f, 150f,
                    Color.rgb(180, 180, 190)
            )
            val bed5 = Rectangle(
                    Vector(surface_width-440, surface_height-300),
                    10f, 150f,
                    Color.rgb(180, 180, 190)
            )
            val bed6 = Rectangle(
                    Vector(surface_width-520, surface_height-300),
                    30f, 200f,
                    Color.rgb(180, 180, 190)
            )
            val bed7 = Rectangle(
                    Vector(surface_width-320, surface_height-370),
                    400f, 20f,
                    Color.rgb(180, 180, 190)
            )
            val bed8 = Rectangle(
                    Vector(surface_width-320, surface_height-230),
                    400f, 20f,
                    Color.rgb(180, 180, 190)
            )


            //CHARACTERS
            man = Man(
                Vector((surface_width / 3)*2, (surface_height / 3)),
                120f, 120f, 0f,
                Color.rgb(40, 40, 80)
            )

            nightmare = EvilWife(
                Vector((surface_width / 3), (surface_height / 3)),
                120f, 120f,
                0f, 0f,
                Color.rgb(0, 0, 0)
            )


            //ROOM WALLS
            val wall1 = Rectangle(
                    Vector(surface_width / 2f, roomTop / 2),
                    surface_width, roomTop,
                    Color.rgb(70, 20, 10)
            )
            val wall2 = Rectangle(
                    Vector(surface_width / 2f, roomTop + roomHeight + (surface_height - (roomTop + roomHeight)) / 2),
                    surface_width, surface_height - (roomTop + roomHeight),
                    Color.rgb(70, 20, 10)
            )
            val wall3 = Rectangle(
                    Vector(roomLeft / 2, surface_height / 2f),
                    roomLeft, surface_height,
                    Color.rgb(70, 20, 10)
            )
            val wall4 = Rectangle(
                    Vector(roomLeft + roomWidth + (surface_width - (roomLeft + roomWidth)) / 2, surface_height / 2f),
                    surface_width - (roomLeft + roomWidth), surface_height,
                    Color.rgb(70, 20, 10)
            )

            //HEALTH BAR
            val healthbar_bg = Rectangle(
                    Vector(surface_width/2, 50f),
                    2000f, 30f,
                    Color.argb(200, 0, 0, 0)
            )
            val healthbar = HealthBar(
                    nightmare!!,
                    Vector(surface_width/2, 50f),
                    nightmare!!.health, 30f,
                    Color.rgb(130, 0, 20)
            )

            surface.addGameObject(bg)

            surface.addGameObject(baby)

            surface.addGameObject(pillow)

            surface.addGameObject(mattress)

            surface.addGameObject(blanket1)
            surface.addGameObject(blanket2)

            surface.addGameObject(square1)
            surface.addGameObject(center1)
            surface.addGameObject(square2)
            surface.addGameObject(center2)
            surface.addGameObject(square3)
            surface.addGameObject(center3)

            surface.addGameObject(bed1)
            surface.addGameObject(bed2)
            surface.addGameObject(bed3)
            surface.addGameObject(bed4)
            surface.addGameObject(bed5)
            surface.addGameObject(bed6)
            surface.addGameObject(bed7)
            surface.addGameObject(bed8)

            surface.addGameObject(man!!)
            surface.addGameObject(nightmare!!)

            surface.addGameObject(wall1)
            surface.addGameObject(wall2)
            surface.addGameObject(wall3)
            surface.addGameObject(wall4)

            surface.addGameObject(healthbar_bg)
            surface.addGameObject(healthbar)

            healthbar.onHealthZero = {
                bg.setColor(Color.rgb(45,45,45))

                pillow.setColor(Color.rgb(107, 107, 107))
                mattress.setColor(Color.rgb(137, 137, 137))
                bed1.setColor(Color.rgb(156, 156, 156))
                bed2.setColor(Color.rgb(156, 156, 156))
                bed3.setColor(Color.rgb(156, 156, 156))
                bed4.setColor(Color.rgb(156, 156, 156))
                bed5.setColor(Color.rgb(156, 156, 156))
                bed6.setColor(Color.rgb(156, 156, 156))
                bed7.setColor(Color.rgb(156, 156, 156))
                bed8.setColor(Color.rgb(156, 156, 156))

                man!!.setColor(Color.rgb(30, 20, 120))
                nightmare!!.setColor(Color.rgb(130, 0, 20))

                wall1.setColor(Color.rgb(25, 25, 25))
                wall2.setColor(Color.rgb(25, 25, 25))
                wall3.setColor(Color.rgb(25, 25, 25))
                wall4.setColor(Color.rgb(25, 25, 25))

                healthbar_bg.setColor(Color.argb(0, 0, 0, 0))
                healthbar.setColor(Color.argb(0, 0, 0, 0))
            }
        }
    }
}