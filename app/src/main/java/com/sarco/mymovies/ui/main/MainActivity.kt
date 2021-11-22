package com.sarco.mymovies.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.sarco.mymovies.R
import com.sarco.mymovies.databinding.ActivityMainBinding
import com.sarco.mymovies.model.Movie
import com.sarco.mymovies.model.MovieDBClient
import com.sarco.mymovies.model.MoviesDatabase
import com.sarco.mymovies.ui.detail.DetailActivity
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class MainActivity : AppCompatActivity() {
    companion object{
        private const val DEFAULT_REGION = "US"
    }

    private var moviesAdapter = MoviesAdapter(emptyList()) { navigateTo(it)}
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()){ isGranted ->
            doRequestPopularMovies(isGranted)
    }

    private fun getRegionFromLocation(location: Location?): String {
        if(location == null){
            return DEFAULT_REGION
        }
        val geocoder = Geocoder(this)
        val result = geocoder.getFromLocation(
            location.latitude,
            location.longitude,
            1
        )

        return result.firstOrNull()?.countryCode ?: DEFAULT_REGION
    }


    private fun doRequestPopularMovies(isLocationGranted: Boolean) {
        lifecycleScope.launch {
            val region = getRegion(isLocationGranted)
            val listPopularMovies =
                MovieDBClient.service.listPopularMovies(getString(R.string.api_key), region)
            moviesAdapter.movies = listPopularMovies.results
            moviesAdapter.notifyDataSetChanged()
        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun getRegion(isLocationGranted: Boolean): String = suspendCancellableCoroutine{ continuaton ->
        if(isLocationGranted){
            fusedLocationProviderClient.lastLocation.addOnCompleteListener {
                continuaton.resume(getRegionFromLocation(it.result))

            }
        } else {
            continuaton.resume(DEFAULT_REGION)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val moviesDatabase = MoviesDatabase.getInstance(application)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)


        binding.recycler.adapter = moviesAdapter


        requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_COARSE_LOCATION)


    }

    private fun navigateTo(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_MOVIE, movie)

        startActivity(intent)
    }
}