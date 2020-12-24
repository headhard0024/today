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
data class CirculeRevealHelper(
    var view: View,
    var startcolor: Int,
    var defaultColor: Int
) {
    private var x = view.measuredWidth / 2
    private var y = view.measuredHeight / 2
    private var startRadius = 0
    private var duration: Long = 0

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

    fun init(): CirculeRevealHelper {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.setBackgroundColor(G.resources.getColor(startcolor, null))
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
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            view.setBackgroundColor(G.resources.getColor(defaultColor, null))
                        }
                    }
                })
            if (this.duration > 0) {
                anim.duration = duration
            }
            anim.start()
        }
        return this
    }
}