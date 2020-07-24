package com.nullit.rtg.business.domain.state

interface DataStateChangeListener {
    fun onDataStateChange(dataState: DataState<*>?)

    fun expandAppBar()

    fun hideSoftKeyBoard()
}