package org.yellowtree.moviebrowse.util

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import org.yellowtree.moviebrowse.R

object UtilBindingAdapter {

    @JvmStatic
    @BindingAdapter("loadImage")
    fun loadImage(imageView: ImageView, url: String?) {
        url?.let {
            Glide.with(imageView.context).load(it).placeholder(R.drawable.transparent_bg)
                .transition(DrawableTransitionOptions.withCrossFade(1000)).into(imageView)

        }

    }

    @JvmStatic
    @BindingAdapter("display")
    fun show(view : View, display : Boolean) {
        view.visibility = if (display) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("clip")
    fun clipOutline(view : View, clipped: Boolean) {
        view.clipToOutline = clipped
    }

}