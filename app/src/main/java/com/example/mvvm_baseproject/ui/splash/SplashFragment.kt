package com.example.mvvm_baseproject.ui.splash

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.mvvm_baseproject.R
import com.example.mvvm_baseproject.databinding.FragmentSplashBinding
import com.example.mvvm_baseproject.navigationComponent.AppNavigation
import com.example.mvvm_baseproject.ui.base.BaseFragment
import com.example.mvvm_baseproject.utils.setTextCompute
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

    @Inject
    lateinit var appNavigation: AppNavigation

    private val viewModel: SplashViewModel by viewModels()

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        binding.btnToLogin.setOnClickListener {
            appNavigation.openSplashToLoginScreen()
        }

        binding.btnToHome.setOnClickListener {
            viewModel.testMergeObservable()
            //viewModel.runCoroutine()
        }
    }
}