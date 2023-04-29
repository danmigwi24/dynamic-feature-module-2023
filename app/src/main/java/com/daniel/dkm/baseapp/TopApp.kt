package com.daniel.dkm.baseapp

import android.util.Log
import androidx.work.*
import com.daniel.dkm.baseapp.di.AppComponent
import com.daniel.dkm.baseapp.di.BaseModuleInjector
import com.daniel.dkm.baseapp.di.DaggerAppComponent
import com.daniel.dkm.baseapp.di.helpers.features.FeatureModule
import com.daniel.dkm.common.workmanger.SyncDataWorkerFactory
import com.daniel.dkm.common.abstractions.BaseApplication
import com.daniel.dkm.common.utils.CrashReportingTree
import com.daniel.dkm.common.workmanger.SYNC_DATA_WORK_NAME
import com.daniel.dkm.common.workmanger.SyncDataWorker
import com.daniel.dkm.common.workmanger.TAG_SYNC_DATA
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TopApp : BaseApplication(), HasAndroidInjector, Configuration.Provider {

    // ActivityInjector / FragmentInjector used in the main module
    @Inject
    lateinit var dispatchingInjector: DispatchingAndroidInjector<Any>

    // List of ActivityInjector / FragmentInjector used in each Feature module
    private val moduleInjectors = mutableListOf<DispatchingAndroidInjector<Any>>()

    // Set for determining whether the Injector of the Feature module has been generated
    private val injectedModules = mutableSetOf<FeatureModule>()

    // Used from AppComponent and Component of each Feature module
    val appComponent by lazy {
        DaggerAppComponent.builder().create(this) as AppComponent
    }

    // AndroidInjector <Activity> that actually injects
    private val androidInjector = AndroidInjector<Any> { instance ->
        // If true is returned by maybeInject, Inject is successful

        // Main module
        if (dispatchingInjector.maybeInject(instance)) {
            return@AndroidInjector
        }

        // Each Feature module
        moduleInjectors.forEach { injector ->
            if (injector.maybeInject(instance)) {
                return@AndroidInjector
            }
        }
        throw IllegalStateException("Injector not found for $instance")
    }

    override fun onCreate() {
        super.onCreate()

        appComponent.inject(this)

        setUpConfigration()
    }

    private fun setUpConfigration() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }

        applicationScope.launch {
            delayedInitForWorkManagerPushToServer()
        }
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    // Add Injector for Feature module
    // Called just before the Feature module is used after installation
    fun addModuleInjector(module: FeatureModule) {
        if (injectedModules.contains(module)) {
            // Do nothing if added
            return
        }

        // Generate Injector for Feature module
        val clazz = Class.forName(module.injectorName)
        val moduleInjector = clazz.newInstance() as BaseModuleInjector
        // Inject Dispatching Android Injector of Injector of Feature module
        moduleInjector.inject(this)

        // Add to list
        moduleInjectors.add(moduleInjector.activityInjector())

        injectedModules.add(module)
    }

    // ----------------------------------WORK MANAGER---------------------------------------------------

    /** *********************START**********************************
     * WORK MANAGER CONFIGARATION
     */

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    @Inject
    lateinit var workerFactory: SyncDataWorkerFactory

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(Log.INFO)
            .build()

    private fun delayedInitForWorkManagerPushToServer() {
        // Create Network constraint
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        Timber.e("=============1============== ${Calendar.getInstance().get(Calendar.SECOND)}")
        val periodicSyncDataWork =
            PeriodicWorkRequest.Builder(
                SyncDataWorker::class.java,
                30,
                TimeUnit.MINUTES
            )
                .addTag(TAG_SYNC_DATA)
                .setConstraints(constraints) // setting a backoff on case the work needs to retry
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                .build()
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            SYNC_DATA_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP, // Existing Periodic Work policy
            periodicSyncDataWork // work request
        )
    }

    /********************************** END ***************************************/
}
