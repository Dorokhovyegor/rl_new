package com.nullit.rtg.ui.state

interface DataStateChangeListener {
    fun onDataStateChange(dataState: DataState<*>?)

    fun expandAppBar()

    fun hideSoftKeyBoard()
}