package com.ekenya.rnd.hiltmodule.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class HomeViewModel @Inject constructor() : ViewModel() {
    private val mText: MutableLiveData<String?> = MutableLiveData()

    init {
        mText.value = "This is home fragment"
    }

    val text: LiveData<String?>
        get() = mText
}