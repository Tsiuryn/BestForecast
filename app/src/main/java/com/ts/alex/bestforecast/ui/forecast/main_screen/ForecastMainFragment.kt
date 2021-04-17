package com.ts.alex.bestforecast.ui.forecast.main_screen

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.ts.alex.bestforecast.R
import com.ts.alex.bestforecast.databinding.FragmentForecastMainBinding
import com.ts.alex.bestforecast.ui.forecast.settings.SettingsFragment
import com.ts.alex.bestforecast.utils.GPSPermission
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForecastMainFragment : Fragment() {
    private val viewModel by viewModel<ForecastMainViewModel>()
    private lateinit var binding: FragmentForecastMainBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_forecast_main,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        navController = Navigation.findNavController(requireView())
        observe()
        GPSPermission(requireActivity()).checkPermission {
            viewModel.getWeatherByCurrentLocation()
        }
        binding.vSettings.setOnClickListener {
            SettingsFragment().show(requireActivity().supportFragmentManager, SettingsFragment::class.java.canonicalName)
        }

        viewModel.getUser()
        onBackPress()


    }

    private fun observe() {
        lifecycleScope.launchWhenStarted {
            viewModel.weatherByLocation.collect {
                setUpForecast(
                    city = it.name,
                    deg = it.main.temp.toInt(),
                    location = "lon: ${it.coord.lon} lat: ${it.coord.lat}",
                    idIcon = it.weather[0].icon,
                    speed = it.wind.speed
                )

            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.weatherByCity.collect {
                setUpForecast(
                    city = it.name,
                    deg = it.main.temp.toInt(),
                    location = "lon: ${it.coord.lon} lat: ${it.coord.lat}",
                    idIcon = it.weather[0].icon,
                    speed = it.wind.speed
                )
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.showException.collect {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()

            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.progress.collect { visible ->
                binding.vProgress.isVisible = visible
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.error.collect { error ->
                Toast.makeText(requireContext(), "${error.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUpForecast(city: String, deg: Int, location: String, idIcon: String, speed: Int){
        val url = getString(R.string.icon_url, idIcon)
        Glide
            .with(this)
            .load(url)
            .into(binding.vImage)
        binding.vCity.text = city
        binding.vDeg.text = "$deg \u2103"
        binding.vLocation.text = location
        val text = "Speed of the wind:"
        binding.vWind.text =  "$text $speed m/s"
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