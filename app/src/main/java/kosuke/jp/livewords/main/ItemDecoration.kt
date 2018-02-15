package kosuke.jp.livewords.main

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by kosuke on 2/8/18.
 */

class ItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private val attr = intArrayOf(android.R.attr.listDivider)
    private var divider: Drawable

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        drawVertical(c, parent)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.set(0, 0, 0, divider.intrinsicHeight)
    }

    private fun drawVertical(c: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + divider.intrinsicHeight
            divider.bounds = Rect(left, top, right, bottom)
            divider.draw(c)
        }
    }

    init {
        val ta = context.obtainStyledAttributes(attr)
        divider = ta.getDrawable(0)
        ta.recycle()
    }
}
