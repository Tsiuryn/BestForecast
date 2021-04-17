package com.ts.alex.bestforecast.ui.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ts.alex.bestforecast.R
import com.ts.alex.bestforecast.databinding.FragmentStartBinding
import com.ts.alex.bestforecast.ui.registration.RegistrationFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.coroutines.flow.collect


class StartFragment : Fragment() {

    private val viewModel: StartViewModel by viewModel()
    private lateinit var binding: FragmentStartBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_start, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        navController = Navigation.findNavController(requireView())
        observe()
        onBackPress()
    }

    private fun observe() {
        lifecycleScope.launchWhenStarted {
            viewModel.startSignUp.collect {
                val action = StartFragmentDirections.actionStartFragmentToRegistrationFragment(true)
                navController.navigate(action)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.startSignIn.collect {
                val action = StartFragmentDirections.actionStartFragmentToRegistrationFragment(false)
                navController.navigate(action)
            }
        }
    }

    private fun onBackPress() {
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finishAffinity()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)
    }

}