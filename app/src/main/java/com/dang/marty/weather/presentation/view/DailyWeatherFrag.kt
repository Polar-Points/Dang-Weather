package com.dang.marty.weather.presentation.view

import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.dang.marty.weather.utils.Constants
import com.dang.marty.weather.R
import com.dang.marty.weather.data.repository.LocationDataRepositoryImpl
import com.dang.marty.weather.data.repository.WeatherRepositoryImpl
import com.dang.marty.weather.data.webservice.OpenWeatherApiService
import com.dang.marty.weather.presentation.presenter.DailyWeatherPresenter
import com.dang.marty.weather.presentation.presenter.DailyWeatherPresenterImpl
import com.dang.marty.weather.presentation.presenter.DailyWeatherView
import com.dang.marty.weather.databinding.FragmentDailyWeatherBinding
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class DailyWeatherFrag : Fragment(), DailyWeatherView, SeekBar.OnSeekBarChangeListener  {

    @Inject lateinit var presenter: DailyWeatherPresenter
    private lateinit var binding: FragmentDailyWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.setView(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDailyWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (ActivityCompat.checkSelfPermission(requireContext(), Constants.locationPermissionsArray[0]) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(), Constants.locationPermissionsArray[1]) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission()
        } else {
            presenter.viewInitialized()
        }
        binding.seekbar.setOnSeekBarChangeListener(this)
    }

    override fun displayWeatherValues(location: String, temperature: String, humidity: String, precipitation: String, windSpeed: String) {
        binding.homeFragLocation.text = location
        binding.homeFragTemperatureTextView.text = temperature
        binding.homeFragHumidity.text = humidity
        binding.homeFragPrecipitationChance.text = precipitation
        binding.homeFragWindSpeed.text = windSpeed
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == Constants.locationCode){
            // Checking whether user granted the permission or not.
            if (grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                presenter.viewInitialized()
            }
            else {
                requestLocationPermission()
            }
        }
    }

    // handling case if user denies permission access
    private fun requestLocationPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                Constants.locationPermissionsArray[0]) &&
            ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                Constants.locationPermissionsArray[1])){

            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.permission_dialog_title))
                .setMessage(getString(R.string.location_permission_explanation))
                .setPositiveButton(getString(R.string.ok)) { _, _ ->
                    requestPermissions(Constants.locationPermissionsArray, Constants.locationCode)
                }
                .setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.dismiss() }
                .create().show()
        } else {
            requestPermissions(Constants.locationPermissionsArray, Constants.locationCode)
        }
    }

    // SeekBar
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        presenter.processScrollInput(progress)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

    override fun onStopTrackingTouch(seekBar: SeekBar?) {}

}