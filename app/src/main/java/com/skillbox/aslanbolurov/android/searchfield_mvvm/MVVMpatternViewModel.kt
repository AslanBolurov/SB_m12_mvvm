package com.skillbox.aslanbolurov.android.searchfield_mvvm

import android.util.Log
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.aslanbolurov.android.searchfield_mvvm.databinding.FragmentMvvmPatternBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import androidx.fragment.app.FragmentManager
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

private const val TAG = "MVVMpatternViewModel"

class MVVMpatternViewModel : ViewModel() {

    private val _state:MutableStateFlow<State> =
                    MutableStateFlow<State>(State.Success)
    val state= _state.asStateFlow()

    private val _error:Channel<String> = Channel<String>()
    val error: Flow<String> =_error.receiveAsFlow()

    fun onSignInClick(textSearch: String) {
        viewModelScope.launch {
            if (textSearch.length <=3){
                _state.value=State.Error("text for searching is too short")
                _error.send("text for searching is too short")
            }else
                _state.value= State.Loading
                delay(5_000)
                _state.value=State.Success
        }
    }
}