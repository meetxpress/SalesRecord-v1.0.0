import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import java.math.BigDecimal

class GetLocation: AppCompatActivity() {
    var LocLat: BigDecimal = 0.toBigDecimal()
    var LocLong: BigDecimal = 0.toBigDecimal()

    private var hasGps = false
    private var hasNetwork = false
    private var locationGps: Location? = null

    private lateinit var locationManager: LocationManager
    @SuppressLint("MissingPermission")
    //getting location in Lang-Long
    fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (hasGps) {
            Log.d("CodeAndroidLocation", "hasGps")
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1F, object :
                LocationListener {
                override fun onLocationChanged(location: Location?) {
                    if (location != null) {
                        runOnUiThread {
                            LocLat = locationGps!!.latitude.toBigDecimal()
                            LocLong = locationGps!!.longitude.toBigDecimal()
                            Log.v("loc","Done")
                        }

                        //val builder = AlertDialog.Builder(this@PunchAttendance)
                        //builder.setMessage("\nLatitude : "+textView31.text.toString()+"\nLongitude : "+textView32.text.toString())
                        //builder.show()
                    }
                }
                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                    runOnUiThread {
                        LocLat = locationGps!!.latitude.toBigDecimal()
                        LocLong= locationGps!!.longitude.toBigDecimal()
                        Log.v("loc","Done")

                        LocLat=locationGps!!.latitude.toBigDecimal()
                        LocLong=locationGps!!.longitude.toBigDecimal()
                    }
                }
                override fun onProviderEnabled(provider: String?) {}
                override fun onProviderDisabled(provider: String?) {}
            })

            val localGpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (localGpsLocation != null){
                locationGps = localGpsLocation
            }
        } else {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
    }
}