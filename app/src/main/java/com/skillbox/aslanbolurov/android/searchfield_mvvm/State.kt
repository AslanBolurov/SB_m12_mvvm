package com.skillbox.aslanbolurov.android.searchfield_mvvm

sealed class State{
    object Loading:State()
    object Success:State()
    data class Error(val msg:String):State()
}
