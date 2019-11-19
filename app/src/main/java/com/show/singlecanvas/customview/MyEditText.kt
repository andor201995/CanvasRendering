package com.show.singlecanvas.customview

import android.content.Context
import android.util.Log
import android.widget.EditText

class MyEditText(context: Context) : EditText(context) {
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.i("MyEditText", "attaching to window")
    }
}