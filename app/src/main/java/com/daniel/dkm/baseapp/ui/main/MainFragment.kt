package com.daniel.dkm.baseapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.daniel.dkm.baseapp.TopApp
import com.daniel.dkm.baseapp.databinding.FragmentMainBinding
import com.daniel.dkm.baseapp.di.helpers.activities.ActivityHelperKt
import com.daniel.dkm.baseapp.di.helpers.activities.AddressableActivity
import com.daniel.dkm.baseapp.di.helpers.features.FeatureModule
import com.daniel.dkm.baseapp.di.helpers.features.Modules
import com.daniel.dkm.common.abstractions.BaseDaggerFragment
import com.daniel.dkm.common.theme.DFM_JC_TEMPLATE
import com.daniel.dkm.common.theme.NunitosansBold
import com.daniel.dkm.common.utils.toasty
import com.google.android.play.core.splitcompat.SplitCompat
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import javax.inject.Inject

class MainFragment : BaseDaggerFragment() {

    private lateinit var binding: FragmentMainBinding

    companion object {
        fun newInstance() = MainFragment()
    }

    private var mApp: TopApp? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val mViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }

    private val module by lazy {
        Modules.FeatureTourism.INSTANCE
    }

    private val splitInstallManager: SplitInstallManager by lazy {
        SplitInstallManagerFactory.create(requireActivity())
    }

    private val listener = SplitInstallStateUpdatedListener { state ->
        when (state.status()) {
            SplitInstallSessionStatus.DOWNLOADING -> {
                setStatus("DOWNLOADING")
            }
            SplitInstallSessionStatus.INSTALLING -> {
                setStatus("INSTALLING")
            }
            SplitInstallSessionStatus.INSTALLED -> {

                // Enable module immediately
                activity?.let { SplitCompat.install(it) }

                setStatus("${module.name} already installed\nPress start to continue ..")
                //
                binding.btnHomeModule.visibility = View.VISIBLE
                binding.btnHomeModule.setOnClickListener {
                    showFeatureModule(module)
                }
                binding.btnHiltModule.setOnClickListener {
                    showFeatureModule(Modules.FeatureHiltModule.INSTANCE)
                }
            }
            SplitInstallSessionStatus.FAILED -> {
                setStatus("FAILED")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //
        mApp = activity?.application as TopApp
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        //
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeView.apply {
            // Dispose the Composition when viewLifecycleOwner is destroyed
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
            )
            setContent {
                // In Compose world
                DFM_JC_TEMPLATE() {
                    ExplainWhyThisApp()
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val data = mViewModel.getData()

        Log.i("MainFragment", "=> $data")

        if (splitInstallManager.installedModules.contains(module.toString())) {
            showFeatureModule(module)
//            setStatus("${module.name} already installed\nPress start to continue ..")
//            //
//            binding.btnHomeModule.visibility = View.VISIBLE
//            binding.btnHomeModule.setOnClickListener{
//                showFeatureModule(module)
//            }
            return
        }

        val request = SplitInstallRequest
            .newBuilder()
            .addModule(module.name)
            .build()

        splitInstallManager.startInstall(request)
        setStatus("Start install for ${module.name}")
    }

    override fun onResume() {
        super.onResume()
        splitInstallManager.registerListener(listener)
    }

    override fun onPause() {
        splitInstallManager.unregisterListener(listener)
        super.onPause()
    }

    private fun setStatus(label: String) {
        // binding.status.text = label
        toasty(label)
    }

    /**
     *
     */
    private fun showFeatureModule(module: FeatureModule) {
        try {
            // Inject
            mApp!!.addModuleInjector(module)
            //

            this.startActivity(
                ActivityHelperKt.intentTo(
                    requireActivity(),
                    module as AddressableActivity
                )
            )
            // finish();
        } catch (e: Exception) {
            e.message?.let { Log.d("MainFragment", it) }
        }
    }
}


@Composable
fun ExplainWhyThisApp() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Text(
            text = "Learning Jetpack Compose",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = NunitosansBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            textDecoration = TextDecoration.Underline,
            color = Color(0xFF5FFF3F)
        )
        Text(
            text = "Modules In this App are:",
            modifier = Modifier.fillMaxWidth(),
            fontFamily = NunitosansBold,
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        val listStrings = listOf<String>(
            "Multiple View Type Recycler view",
            "Printer SDK with Bluetooth",
            "Custom Dialogs",
            "Kotlin Flow",
            "-----------------",
            "JC",
            "-----------------",
            "Navigation in jetpack Compose",
            "Onboarding in jetpack compose",
            "Splash Screen in jetpack compose",
            "Finance Screen UI",
            "Task App Management UI",
            "Fetching List of data "
        )
        listStrings.forEach {
            Text(
                text = it,
                fontFamily = NunitosansBold,
                fontWeight = FontWeight.Normal,
            )
        }
    }
}