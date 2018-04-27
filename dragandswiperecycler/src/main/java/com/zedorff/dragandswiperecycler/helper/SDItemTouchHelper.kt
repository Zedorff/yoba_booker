package com.zedorff.dragandswiperecycler.helper

import android.support.v7.widget.helper.ItemTouchHelper

class SDItemTouchHelper(callback: SDHelperListener): ItemTouchHelper(SDItemTouchCallback(callback))