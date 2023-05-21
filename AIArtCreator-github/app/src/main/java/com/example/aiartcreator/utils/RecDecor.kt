package com.example.aiartcreator.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecDecor(var bottom: Int = 15, var top: Int = 15,var left: Int = 15, var right: Int = 15 ): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(bottom,top,left,right)
    }
}