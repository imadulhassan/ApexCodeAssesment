package com.apex.codeassesment.ui.details

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.apex.codeassesment.R
import com.apex.codeassesment.data.model.Coordinates
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.databinding.ActivityDetailsBinding
import com.apex.codeassesment.ui.location.LocationActivity
import com.sample.extn.loadImage
import com.sample.extn.navigateToScreen


// TODO (3 points): Convert to Kotlin
// TODO (3 points): Remove bugs or crashes if any
// TODO (1 point): Add content description to images
// TODO (2 points): Add tests
class DetailsActivity : AppCompatActivity() {

     var binding: ActivityDetailsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra("saved-user-key", User::class.java)
        } else {
            intent?.getParcelableExtra<User>("saved-user-key")
        }

        user?.let { setData(it) }
    }

     fun setData(user: User) {
        // TODO (1 point): Use Glide to load images
        user.picture?.large?.let { binding?.detailsImage?.loadImage(it) }
        val name = user.name
        binding?.detailsName?.text = (getString(R.string.details_name, name?.first, name?.last))
        binding?.detailsEmail?.text = (getString(R.string.details_email, user.gender))
        binding?.detailsAge?.text = user.dob?.age?.toString() ?: ""
        val coordinates = user.location?.coordinates
        binding?.detailsLocation?.text = getString(
            R.string.details_location, coordinates?.latitude, coordinates?.longitude
        )
        binding?.detailsLocationButton?.setOnClickListener {
            coordinates?.let { navigateLocation(it) }
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


    private fun navigateLocation(coordinates: Coordinates) {
        navigateToScreen(LocationActivity::class.java) {
            putString(
                "user-latitude-key", coordinates.latitude
            )
            putString("user-longitude-key", coordinates.longitude)
        }
    }

}