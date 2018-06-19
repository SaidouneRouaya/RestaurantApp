package example.android.com.RestoPresto

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import kotlinx.android.synthetic.main.infos_resto.*
import com.google.android.gms.maps.model.MarkerOptions
import example.android.com.RestoPresto.entities.Restaurant
import kotlinx.android.synthetic.main.activity_infos.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.jetbrains.anko.makeCall
import org.jetbrains.anko.toast
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.view.Menu
import android.view.MenuItem
import org.jetbrains.anko.intentFor


class InfosActivity : AppCompatActivity(), OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {
    private var mapView: MapView? = null
    private var gmap: GoogleMap? = null
    private val MAP_VIEW_BUNDLE_KEY = R.string.bundle_key.toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infos)
        setSupportActionBar(toolbar)
        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY)
        }

        mapView = findViewById(R.id.map_view) as MapView
        mapView?.onCreate(mapViewBundle)
        mapView?.getMapAsync(this)
        val util = Util()
        val url  = "fb://page/218641444910278"
        val i = intent.getIntExtra("pos",0)
        val imagesTab = arrayOf(R.drawable.ledey, R.drawable.lallamina, R.drawable.eldjenina, R.drawable.lapalmeraie, R.drawable.eldjazair)
        val nomsTab = resources.getStringArray(R.array.restos)
        val adresseTab = resources.getStringArray(R.array.adresses)
        val noteTab = resources.getStringArray(R.array.notes)
        val emailTab = resources.getStringArray(R.array.emails)
        val numeroTab = resources.getStringArray(R.array.numeros)
        val descriptions = resources.getStringArray(R.array.descriptions)
        val fbTab = resources.getStringArray(R.array.facebooks)
        val twTab = resources.getStringArray(R.array.twitters)
        val resto = Restaurant(nom = nomsTab[i], lien = imagesTab[i].toString(), adresse = adresseTab[i], n_tel = numeroTab[i], note = noteTab[i],email = emailTab[i],description = descriptions[i])
        util.displayInfos(this,resto)
        numero_resto.setOnClickListener({
            makeCall(numero_resto.text.toString())
        })
        facebook.setOnClickListener({
            try {
                util.openFacebookPage(this, url)
                toast("Vous etes redirigés vers Facebook")
            }
            catch (e : Exception)
            {
                util.browseUrl(this,"https://www.facebook.com/search/top/?q=bristol&ref=eyJzaWQiOiIwLjUzNjE2MjY1Mjk4ODc3NDQiLCJxcyI6IkpUVkNKVEl5WW5KcGMzUnZiQ1V5TWlVMVJBIiwiZ3YiOiJiZWUwOWY5M2ZhNzMyY2ZhNTlhMWNiNmQ5ZjQ1MGQzODkyNDI0ZTQ5IiwiZW50X2lkcyI6W10sImJzaWQiOiJiYmI5NDBhMmQ5YmMwZDljNDEzMjlmNWJhNTJmN2NmMiJ9")
            }

        })
        twitter.setOnClickListener({
            try {
                util.openFacebookPage(this, "https://twitter.com/palmeraieresort?lang=fr")
                toast("Vous etes redirigés vers Twitter")
            }
            catch (e : Exception)
            {
                util.browseUrl(this,"https://www.facebook.com/search/top/?q=bristol&ref=eyJzaWQiOiIwLjUzNjE2MjY1Mjk4ODc3NDQiLCJxcyI6IkpUVkNKVEl5WW5KcGMzUnZiQ1V5TWlVMVJBIiwiZ3YiOiJiZWUwOWY5M2ZhNzMyY2ZhNTlhMWNiNmQ5ZjQ1MGQzODkyNDI0ZTQ5IiwiZW50X2lkcyI6W10sImJzaWQiOiJiYmI5NDBhMmQ5YmMwZDljNDEzMjlmNWJhNTJmN2NmMiJ9")
            }
        })
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)

        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
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
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val ft = supportFragmentManager.beginTransaction()
        val i = intent.getIntExtra("pos",0)
        val nom = intent.getStringExtra("nom")
        when (item.itemId) {
            R.id.accueil -> { startActivity(intentFor<MainActivity> ()) }

            R.id.menu_jour -> { startActivity(intentFor<MenuDesMenusActivity> ("nom" to nom, "pos" to i)) }

            R.id.infos -> { startActivity(intentFor<InfosActivity> ("pos" to i)) }

            R.id.commander-> { startActivity(intentFor<CommanderActivity> ("pos" to i)) }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
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
        uiSettings?.isMyLocationButtonEnabled = true
        uiSettings?.isMapToolbarEnabled = true
        val latitudesTab = resources.getStringArray(R.array.latitudes)
        val longitudesTab = resources.getStringArray(R.array.longitudes)
        val ny = LatLng(latitudesTab[i].toDouble(), longitudesTab[i].toDouble())
        val markerOptions = MarkerOptions()
        markerOptions.position(ny)
        gmap?.addMarker(markerOptions)
        gmap?.moveCamera(CameraUpdateFactory.newLatLng(ny))
    }
}
