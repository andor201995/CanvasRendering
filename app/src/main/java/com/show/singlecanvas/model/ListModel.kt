package com.show.singlecanvas.model

import android.app.Activity

data class ListModel(val listName: String, val activityClass: Class<out Activity>)