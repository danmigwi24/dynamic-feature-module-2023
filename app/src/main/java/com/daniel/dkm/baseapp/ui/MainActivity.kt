package com.daniel.dkm.baseapp.ui

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.daniel.dkm.baseapp.TopApp
import com.daniel.dkm.baseapp.databinding.ActivitySplashBinding
import com.daniel.dkm.baseapp.ui.main.MainViewModel
import com.daniel.dkm.common.abstractions.BaseActivity
import javax.inject.Inject


class MainActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding

    private var mApp: TopApp? = null
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val mViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //
        mApp = application as TopApp
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = mViewModel.getData()

        Log.i("SplashActivity", "=> $data")

        //
       /* if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }*/
    }



}