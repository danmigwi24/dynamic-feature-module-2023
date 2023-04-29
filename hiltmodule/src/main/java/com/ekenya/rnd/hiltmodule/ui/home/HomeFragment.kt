package com.ekenya.rnd.hiltmodule.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.runtime.snapshots.Snapshot.Companion.observe
import androidx.lifecycle.ViewModelProvider
import com.daniel.dkm.common.abstractions.BaseDaggerFragment
import com.ekenya.rnd.hiltmodule.databinding.FragmentHomeBinding
import javax.inject.Inject

class HomeFragment : BaseDaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(requireActivity(), viewModelFactory).get<HomeViewModel>(
            HomeViewModel::class.java
        )
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        val textView: TextView = binding!!.textHome
        homeViewModel.text.observe(getViewLifecycleOwner()) { s -> textView.setText(s) }
        return root
    }

}