package com.atharok.btremote.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.atharok.btremote.R
import com.atharok.btremote.common.utils.arePermissionsGranted
import com.atharok.btremote.common.utils.bluetoothConnectPermissions
import com.atharok.btremote.databinding.ActivityMainBinding
import com.atharok.btremote.presentation.viewmodel.AppScopeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val appScopeViewModel: AppScopeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        if (!appScopeViewModel.isBluetoothSupported) {
            navController.navigate(R.id.bluetoothNotSupportFragment)
        } else if (!arePermissionsGranted(this, bluetoothConnectPermissions)) {
            navController.navigate(R.id.bluetoothPermissionsFragment)
        } else if (!appScopeViewModel.isBluetoothEnabled) {
            navController.navigate(R.id.bluetoothActivationFragment)
        } else {
            navController.navigate(R.id.deviceSelectionFragment)
        }
    }
}