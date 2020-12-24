package no3ratii.mohammad.dev.app.notebook.base.helper

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import androidx.annotation.RequiresApi
import no3ratii.mohammad.dev.app.notebook.base.G
import kotlin.math.hypot

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
//initialize value for start anim
data class CirculeRevealHelper(
    var view: View,
    var startcolor: Int,
    var defaultColor: Int
) {
    // by defult this value is view / 2 for show to use anim start in cener
    private var x = view.measuredWidth / 2

    // by defult this value is view / 2 for show to use anim start in cener
    private var y = view.measuredHeight / 2

    // defult value for radius is 0 becose this value is for start radius circle
    private var startRadius = 0

    // anim speed
    private var duration: Long = 0

    // can chenge circle position by this fun
    fun position(x: Int, y: Int): CirculeRevealHelper {
        this.x = x
        this.y = y
        return this
    }

    fun radius(startRadius: Int): CirculeRevealHelper {
        this.startRadius = startRadius
        return this
    }

    fun duration(duration: Long): CirculeRevealHelper {
        this.duration = duration
        return this
    }

    //start animation
    fun init() {
        view.setBackgroundColor(G.resources.getColor(startcolor))
        val endRadius =
            hypot(view.width.toDouble(), view.height.toDouble()).toInt()
        val anim: Animator = ViewAnimationUtils.createCircularReveal(
            view,
            x,
            y,
            startRadius.toFloat(),
            endRadius.toFloat()
        )
        anim.addListener(
            object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    view.setBackgroundColor(G.resources.getColor(defaultColor))
                }
            })
        if (this.duration > 0) {
            anim.duration = duration
        }
        anim.start()
    }
}