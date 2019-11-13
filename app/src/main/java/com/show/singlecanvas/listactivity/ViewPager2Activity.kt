package com.show.singlecanvas.listactivity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.show.singlecanvas.R
import com.show.singlecanvas.SingleCanvasApplication
import com.show.singlecanvas.adapter.ViewPageAdapter
import kotlinx.android.synthetic.main.activity_view_pager2_acticity.*

class ViewPager2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager2_acticity)
        val viewPageAdapter = ViewPageAdapter(this)
        viewPageAdapter.numOfItemsInViewPage =
            (application as SingleCanvasApplication).getNumberOfObject()

        view_pager2.adapter = viewPageAdapter
        view_pager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Toast.makeText(applicationContext, "Page Selected: $position", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                viewPageAdapter.setScrollType(ViewPageAdapter.RenderState.Rendering.StartRendering)
            }
        })

    }
}
