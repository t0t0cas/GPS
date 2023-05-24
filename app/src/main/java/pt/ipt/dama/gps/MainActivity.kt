package pt.ipt.dama.gps

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity(), LocationListener {

    private lateinit var locationManager: LocationManager
    private lateinit var textView: TextView
    private lateinit var button: Button
    private var locationPermissionCode=2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button=findViewById(R.id.button)
        button.setOnClickListener{
            getlocation()
        }
    }

    /**
     * read the user's location
     */
    private fun getlocation() {
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        //see if user add permission to use the GPS
        if (
            (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionCode
            )
        }
        //reads the FPS position
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5f, this)
    }

    /**
     * this method will receive the GPS position
     * and write it to the textView
     */
    override fun onLocationChanged(location: Location) {
        textView = findViewById(R.id.textView)
        textView.text="Latitude: \n ${location.latitude} \n \nLongitude: \n ${location.longitude}"
    }

    /**
     * inform user for the result of permission
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == locationPermissionCode){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //the user allowed the app to use the GPS
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else{
                //the user don't allowed the app to use the GPS
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
