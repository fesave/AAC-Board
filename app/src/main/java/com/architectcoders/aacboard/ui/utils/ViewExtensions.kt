package com.architectcoders.aacboard.ui.utils

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ViewAnimator
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.aacboard.databinding.FragmentListDashboardsBinding
import com.architectcoders.aacboard.databinding.FragmentMainDashboardBinding
import com.architectcoders.aacboard.databinding.FragmentSearchPictogramsBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlin.properties.Delegates

inline fun <VH : RecyclerView.ViewHolder, T> RecyclerView.Adapter<VH>.basicDiffUtil(
    initialValue: List<T>,
    crossinline areItemsTheSame: (T, T) -> Boolean = { old, new -> old == new },
    crossinline areContentsTheSame: (T, T) -> Boolean = { old, new -> old == new },
) =
    Delegates.observable(initialValue) { _, old, new ->
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                areItemsTheSame(old[oldItemPosition], new[newItemPosition])

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                areContentsTheSame(old[oldItemPosition], new[newItemPosition])

            override fun getOldListSize(): Int = old.size

            override fun getNewListSize(): Int = new.size
        }).dispatchUpdatesTo(this@basicDiffUtil)
    }

fun ImageView.loadUrl(url: String) {
    Glide.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}

fun ImageView.tint(context: Context?, colorId: Int) {
    context?.let { ctx -> setColorFilter(ctx.getColor(colorId)) }
}

const val ALPHA_0_PERCENT = 1.0F
const val ALPHA_50_PERCENT = 0.5F

fun View.setViewEnabled(allowedInteractions: Boolean) {
    this.alpha = if (allowedInteractions) ALPHA_0_PERCENT else ALPHA_50_PERCENT
    this.isEnabled = allowedInteractions
}

fun ViewAnimator.show(view: View) {
    displayedChild = indexOfChild(view)
}

fun FragmentMainDashboardBinding.showView(view: View) {
    viewAnimator.show(view)
}

fun FragmentListDashboardsBinding.showView(view: View) {
    viewAnimator.show(view)
}

fun FragmentSearchPictogramsBinding.showView(view: View) {
    viewAnimator.show(view)
}

@Suppress("DEPRECATION")
fun View.getScreenSize(): Pair<Int, Int> {
    val screenWidth: Int
    val screenHeight: Int

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics =
            (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).currentWindowMetrics
        val insets =
            windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())

        screenWidth = windowMetrics.bounds.width() - insets.left - insets.right
        screenHeight = windowMetrics.bounds.height() - insets.top - insets.bottom
    } else {
        val displayMetrics = DisplayMetrics()
        (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager)
            .defaultDisplay
            .getRealMetrics(displayMetrics)
        screenWidth = displayMetrics.widthPixels
        screenHeight = displayMetrics.heightPixels
    }

    return Pair(screenWidth, screenHeight)
}

fun View.getScreenWidth(): Int = getScreenSize().first