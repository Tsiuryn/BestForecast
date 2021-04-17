package com.ts.alex.bestforecast.ui.forecast.main_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ts.alex.bestforecast.R
import com.ts.alex.bestforecast.databinding.FragmentForecastMainBinding
import com.ts.alex.bestforecast.databinding.FragmentRegistrationBinding
import com.ts.alex.bestforecast.ui.registration.RegistrationFragmentDirections
import com.ts.alex.bestforecast.ui.registration.RegistrationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ForecastMainFragment : Fragment() {
    private val viewModel by viewModel<ForecastMainViewModel>()
    private lateinit var binding: FragmentForecastMainBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forecast_main, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        navController  = Navigation.findNavController(requireView())
        onBackPress()
    }

    private fun onBackPress() {
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val action = RegistrationFragmentDirections.actionRegistrationFragmentToStartFragment()
                navController.navigate(action)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)
    }

}