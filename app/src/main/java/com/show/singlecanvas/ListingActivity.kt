package com.show.singlecanvas

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.text.InputType
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.show.singlecanvas.adapter.ListingAdapter
import com.show.singlecanvas.listactivity.*
import com.show.singlecanvas.model.ListModel
import kotlinx.android.synthetic.main.activity_listing.*


class ListingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listing)
        val listingRecyclerView = listingRecyclerView as RecyclerView
        val listingAdapter = ListingAdapter(applicationContext, getMapOfContent())
        listingRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this@ListingActivity,
                LinearLayoutManager.VERTICAL
            )
        )
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        listingRecyclerView.layoutManager = linearLayoutManager
        listingRecyclerView.adapter = listingAdapter
        setSupportActionBar(my_toolbar)

    }

    private fun getMapOfContent(): HashMap<Int, ListModel> {
        return hashMapOf(
            0 to ListModel("Multiple Views", MultipleViewActivity::class.java),
            1 to ListModel("Single View Canvas", SingleViewCanvasActivity::class.java),
            2 to ListModel("Surface View", SurfaceViewActivity::class.java),
            3 to ListModel("Surface View Thread", SurfaceViewThreadActivity::class.java),
            4 to ListModel("Recycler View Thumbnail", SurfaceViewRecyclerActivity::class.java),
            5 to ListModel("Rendering Gif on Surface View", GifRenderingActivity::class.java)
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            openDialog()
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun openDialog() {

        val alertDialog = AlertDialog.Builder(this).create()

        // Set Custom Title
        val title = TextView(this)
        // Title Properties
        title.text = getString(R.string.set_no_of_objects)
        title.setPadding(10, 10, 10, 10)   // Set Position
        title.gravity = Gravity.CENTER
        title.setTextColor(Color.BLACK)
        title.textSize = 20f
        alertDialog.setCustomTitle(title)

        // Set Message
        val msg = EditText(this)
        msg.inputType = InputType.TYPE_CLASS_NUMBER
        // Message Properties
        msg.setText(
            (application as SingleCanvasApplication).getNumberOfObject().toString(),
            TextView.BufferType.EDITABLE
        )
        msg.gravity = Gravity.CENTER_HORIZONTAL
        msg.setTextColor(Color.BLACK)
        alertDialog.setView(msg)

        // Set Button
        // you can more buttons
        alertDialog.setButton(
            AlertDialog.BUTTON_NEUTRAL, "OK"
        ) { _, _ ->
            val numberOfObjects = msg.text.toString().toInt()
            (application as SingleCanvasApplication).setNumberOfObject(if (numberOfObjects > 0) numberOfObjects else 1000)
        }

        alertDialog.setButton(
            AlertDialog.BUTTON_NEGATIVE, "CANCEL"
        ) { _, _ ->

        }

        Dialog(applicationContext)
        alertDialog.show()

        // Set Properties for OK Button
        val okBT = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL)
        val neutralBtnLP = okBT.layoutParams as LinearLayout.LayoutParams
        neutralBtnLP.gravity = Gravity.FILL_HORIZONTAL
        okBT.setPadding(50, 10, 10, 10)   // Set Position
        okBT.setTextColor(Color.BLUE)
        okBT.layoutParams = neutralBtnLP

        val cancelBT = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
        val negBtnLP = okBT.layoutParams as LinearLayout.LayoutParams
        negBtnLP.gravity = Gravity.FILL_HORIZONTAL
        cancelBT.setTextColor(Color.RED)
        cancelBT.layoutParams = negBtnLP
    }
}
