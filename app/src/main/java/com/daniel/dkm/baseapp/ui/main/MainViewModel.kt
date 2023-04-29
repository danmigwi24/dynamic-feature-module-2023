package com.daniel.dkm.baseapp.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.daniel.dkm.common.data.repository.SampleRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val sampleRepository: SampleRepository,
    private val app: Application
) : AndroidViewModel(app) {

    fun getData(): String {
        return sampleRepository.getData()
    }
}