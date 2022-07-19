package com.enderx.jobsearcher.core.utils

import android.graphics.drawable.Drawable
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.annotation.AnimRes
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.load
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.transform.CircleCropTransformation
import com.google.android.material.appbar.MaterialToolbar

@BindingAdapter("visible")
fun View.visible(visible: Boolean) {
    isVisible = visible
}

@BindingAdapter("animationId", "animationStart", requireAll = true)
fun View.animation(@AnimRes animId: Int, animStart: Boolean) {
    if (!animStart) {
        clearAnimation()
    } else if (animId != 0) {
        startAnimation(AnimationUtils.loadAnimation(context, animId))
    }
}

@BindingAdapter(
    "loadImage",
    "placeHolderRes",
    "placeHolderDrawable",
    "circleCrop",
    "onSuccessListener",
    requireAll = false
)
fun ImageView.loadImage(
    url: String?,
    @DrawableRes placeholderRes: Int?,
    placeholderDrawable: Drawable?,
    circleCrop: Boolean,
    onSuccessListener: ((request: ImageRequest, result: SuccessResult) -> Unit)?
) {
    url?.let {
        load(url) {
            crossfade(true)
            placeholderRes?.let {
                placeholder(it)
            }
            placeholderDrawable?.let {
                placeholder(it)
            }
            if (circleCrop) {
                transformations(CircleCropTransformation())
            }
            onSuccessListener?.let {
                listener(onSuccess = it)
            }
        }
    }
}

@BindingAdapter("navigationListener")
fun MaterialToolbar.navigationListener(listener: View.OnClickListener?) {
    listener?.let {
        setNavigationOnClickListener(it)
    }
}

@BindingAdapter("navigationIcon")
fun MaterialToolbar.navigationIcon(@DrawableRes resId: Int) {
    if (resId != 0) {
        setNavigationIcon(resId)
    }
}

@BindingAdapter("showNavigationIcon")
fun MaterialToolbar.showNavigationIcon(shouldShow: Boolean) {
    if (!shouldShow) {
        navigationIcon = null
    }
}