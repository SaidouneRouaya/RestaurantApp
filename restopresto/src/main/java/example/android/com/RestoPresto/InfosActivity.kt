package example.android.com.RestoPresto

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import kotlinx.android.synthetic.main.activity_infos.*
import com.google.android.gms.maps.model.MarkerOptions
import example.android.com.RestoPresto.entities.Restaurant
import org.jetbrains.anko.makeCall


class InfosActivity : AppCompatActivity(), OnMapReadyCallback {
    private var mapView: MapView? = null
    private var gmap: GoogleMap? = null
    private val MAP_VIEW_BUNDLE_KEY = R.string.bundle_key.toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infos)
        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY)
        }

        mapView = findViewById(R.id.map_view) as MapView
        mapView?.onCreate(mapViewBundle)
        mapView?.getMapAsync(this)
        val util = Util()
        val i = intent.getIntExtra("pos",0)
        val imagesTab = arrayOf(R.drawable.ledey, R.drawable.lallamina, R.drawable.eldjenina, R.drawable.lapalmeraie, R.drawable.eldjazair)
        val nomsTab = resources.getStringArray(R.array.restos)
        val adresseTab = resources.getStringArray(R.array.adresses)
        val noteTab = resources.getStringArray(R.array.notes)
        val emailTab = resources.getStringArray(R.array.emails)
        val numeroTab = resources.getStringArray(R.array.numeros)
        val resto = Restaurant(nom = nomsTab[i], image = imagesTab[i], adresse = adresseTab[i], numero = numeroTab[i], note = noteTab[i],email = emailTab[i])
        util.displayInfos(this,resto)
        numero_resto.setOnClickListener({
            makeCall(numero_resto.text.toString())
        }

        )
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        var mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY)
        if (mapViewBundle == null) {
            mapViewBundle = Bundle()
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle)
        }

        mapView?.onSaveInstanceState(mapViewBundle)
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onPause() {
        mapView?.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mapView?.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val ab = getSupportActionBar()
        ab?.setDisplayHomeAsUpEnabled(true)
        gmap = googleMap
        gmap?.setMinZoomPreference(12.00f)
        gmap?.setIndoorEnabled(true)
        val i = intent.getIntExtra("pos",0)
        val uiSettings = gmap?.getUiSettings()
        uiSettings?.setIndoorLevelPickerEnabled(true)
        uiSettings?.setMyLocationButtonEnabled(true)
        uiSettings?.setMapToolbarEnabled(true)
        uiSettings?.setCompassEnabled(true)
        uiSettings?.setZoomControlsEnabled(true)
        val latitudesTab = resources.getStringArray(R.array.latitudes)
        val longitudesTab = resources.getStringArray(R.array.longitudes)
        val ny = LatLng(latitudesTab[i].toDouble(), longitudesTab[i].toDouble())
        val markerOptions = MarkerOptions()
        markerOptions.position(ny)
        gmap?.addMarker(markerOptions)
        gmap?.moveCamera(CameraUpdateFactory.newLatLng(ny))
    }
}
