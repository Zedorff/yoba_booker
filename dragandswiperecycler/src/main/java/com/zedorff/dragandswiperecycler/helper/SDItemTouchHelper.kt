package com.zedorff.dragandswiperecycler.helper

import androidx.recyclerview.widget.ItemTouchHelper

class SDItemTouchHelper(callback: SDHelperListener, touchCallback: SDItemTouchCallback = SDItemTouchCallback(callback)): ItemTouchHelper(touchCallback)