package com.apex.codeassesment.ui.location

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.apex.codeassesment.R
import com.apex.codeassesment.databinding.ActivityLocationBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices


// TODO (Optional Bonus 8 points): Calculate distance between 2 coordinates using phone's location
class LocationActivity : AppCompatActivity() {

    private var binding: ActivityLocationBinding? = null

    private val locationPermissionRequestCode = 123
    private var locationProvider: FusedLocationProviderClient? = null
    private var phoneLattitude: Double = 0.0
    private var phoneLongitude: Double = 0.0
    private var latitudeRandomUser = 0.0
    private var longitudeRandomUser = 0.0
    private var distanceHelper: DistanceHelper = DistanceHelper()

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            phoneLattitude = p0.locations.get(0).latitude
            phoneLongitude = p0.locations.get(0).longitude
            setLocationData()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        latitudeRandomUser = intent.getStringExtra("user-latitude-key")?.toDouble() ?: 0.0
        longitudeRandomUser = intent.getStringExtra("user-longitude-key")?.toDouble() ?: 0.0


        checkLocationPermission()

        binding?.locationRandomUser?.text = getString(
            R.string.location_random_user,
            latitudeRandomUser.toString(),
            longitudeRandomUser.toString()
        )

        binding?.locationCalculateButton?.setOnClickListener {
            calculateDistance()
        }
    }

    private fun calculateDistance() {
        val distanceInM = distanceHelper.distance(
            latitudeRandomUser,
            longitudeRandomUser,
            phoneLattitude,
            phoneLongitude,
            'M'
        )
        val distanceInK = distanceHelper.distance(
            latitudeRandomUser,
            longitudeRandomUser,
            phoneLattitude,
            phoneLongitude,
            'K'
        )
        binding?.locationDistance?.text = (getString(
            R.string.calculated_distance,
            distanceInK.toString(),
            distanceInM.toString()
        ))
    }

    fun setLocationData() {
        binding?.locationPhone?.text =
            getString(R.string.location_phone, phoneLattitude.toString(), phoneLongitude.toString())
    }

    private fun startLocationUpdates() {
        if (locationProvider == null) {
            locationProvider = LocationServices.getFusedLocationProviderClient(this)
        }

        val locationRequest = LocationRequest.create().apply {
            interval = 5000 // 10 seconds, adjust as needed
            fastestInterval = 2000 // 5 seconds, adjust as needed
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        try {
            locationProvider?.requestLocationUpdates(
                locationRequest, locationCallback, Looper.getMainLooper()
            )
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    private fun stopLocationUpdates() {
        locationProvider?.removeLocationUpdates(locationCallback)
    }


    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                locationPermissionRequestCode
            )
        } else {
            // Permission has been granted, start location updates
            startLocationUpdates()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionRequestCode && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Permission has been granted, start location updates
            startLocationUpdates()
        } else {
            Toast.makeText(this, "Permission Not Granted", Toast.LENGTH_LONG).show()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        stopLocationUpdates()
        binding = null
    }


}
