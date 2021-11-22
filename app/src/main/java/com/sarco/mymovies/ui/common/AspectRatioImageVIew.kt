package com.sarco.mymovies.ui.common

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.sarco.mymovies.R

class AspectRatioImageVIew @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatImageView(context, attrs) {

    var ratio: Float = 1f

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioImageVIew)
       ratio = a.getFloat(R.styleable.AspectRatioImageVIew_ratio, 1f)
        a.recycle()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var width = measuredWidth
        var heigth = measuredHeight

        if(width == 0 && heigth == 0){
            return
        }

        if(width > 0){
            heigth = (width * ratio).toInt()
        } else if (heigth > 0) {
            width = (heigth / ratio).toInt()

        }
        setMeasuredDimension(width, heigth)
    }
}