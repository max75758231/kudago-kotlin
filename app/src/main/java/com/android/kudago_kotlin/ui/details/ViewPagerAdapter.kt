package com.android.kudago_kotlin.ui.details

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.android.kudago_kotlin.R
import com.bumptech.glide.Glide

class ViewPagerAdapter(
    private val context: Context,
    private val imageUrls: List<String>
) : PagerAdapter() {

    override fun getCount(): Int {
        return imageUrls.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(context)
        Glide.with(context).load(imageUrls[position])
            .centerCrop()
            .placeholder(R.drawable.ic_image_placeholder)
            .into(imageView)
        container.addView(imageView)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }
}
