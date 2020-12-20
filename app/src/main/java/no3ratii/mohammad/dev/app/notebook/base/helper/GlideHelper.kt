package no3ratii.mohammad.dev.app.notebook.base.helper

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

data class GlideHelper(var imageView: ImageView, var url: String, var placeholder: Int){

    fun bilde() : GlideHelper{
        Glide.with(imageView)
            .load(url)
            .apply(RequestOptions.placeholderOf(placeholder))
            .circleCrop()
            .into(imageView)
        return this
    }

    fun bildeNoClicle() : GlideHelper{
        Glide.with(imageView)
            .load(url)
            .apply(RequestOptions.placeholderOf(placeholder))
            .into(imageView)
        return this
    }
}