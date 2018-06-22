package example.android.com.RestoPresto

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import example.android.com.RestoPresto.database.AppDatabase
import example.android.com.RestoPresto.entities.Plat
import example.android.com.RestoPresto.entities.Reservation
import example.android.com.RestoPresto.service.RetrofitService
import example.android.com.RestoPresto.singleton.RoomService
import kotlinx.android.synthetic.main.activity_reserver.*
import org.jetbrains.anko.makeCall
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class ReserverActivity : AppCompatActivity() {

    var cal = Calendar.getInstance()
    var textview_time: TextView? = null
    var bouton_time:Button? = null
    var textview_date:TextView? = null
    var bouton_date:Button? = null
    var numberpicker:NumberPicker? = null
    var preferences: SharedPreferences? = null
    var MY_PERMISSIONS_REQUEST_CALL_PHONE:Int = 0
    var id_restaurant = 0
    private var mDb: AppDatabase? = RoomService.appDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserver)
        val ab = getSupportActionBar()
        ab?.setDisplayHomeAsUpEnabled(true)
        preferences = getSharedPreferences("projetMobile", Context.MODE_PRIVATE)

        //Récupération des références du layout et personnalisation
        textview_date = this.date_textview
        bouton_date = this.button_pick_date
        textview_time= this.time_textview
        bouton_time=this.button_pick_time
        numberpicker= this.numberPicker
        textview_date!!.text= "--/--/----"
        textview_time!!.text="--:--"
        numberPicker.minValue = 1
        numberPicker.maxValue = 10
       // nom_prenom.editText!!.text = preferences!!.getString("nom")
        nom_resto.text= intent.getStringExtra("nom")

        //Initialisation de la variable
        id_restaurant = intent.getIntExtra("pos",0)

        //Création OnDate Listener
        val dateSetListener = object : DatePickerDialog.OnDateSetListener{
            override fun onDateSet(view: DatePicker?, year: Int, monthOfyear: Int, dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfyear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        val timeSetListener = object : TimePickerDialog.OnTimeSetListener{
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                cal.set(Calendar.HOUR_OF_DAY,hourOfDay)
                cal.set(Calendar.MINUTE,minute)
                updateTimeInView()
            }
        }

        // Quand on veut ajouter la date en cliquant sur le bouton changer la date
        bouton_date!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                DatePickerDialog(this@ReserverActivity, dateSetListener,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show()
            }
        })

        bouton_time!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                TimePickerDialog(this@ReserverActivity, timeSetListener,
                        cal.get(Calendar.HOUR_OF_DAY),
                        cal.get(Calendar.MINUTE), true).show()
            }
        })

        valider.setOnClickListener({
            if (nom_prenom.equals("") || date_textview.text.equals("--/--/----") || time_textview.text.equals("--:--")) {
                toast("Veuillez remplir tous les champs")

            }
            else
            {
                if (isNetworkAvailable())
                {
                    val reservation = Reservation(date =date_textview.text.toString(),heure =time_textview.text.toString(),
                            nb_personne = numberPicker.value, id_restaurant = id_restaurant+1, id_user = preferences!!.getInt("id_user",1),
                            id_reservation = 0)
                    System.out.println(reservation.date+"  "+reservation.nb_personne)
//                    mDb!!.getReservationDao().addReservations(reservation)
                    val call3 = RetrofitService.endpoint.addReservation(reservation)
                    call3.enqueue(object:Callback<String>
                    {
                        override fun onFailure(call: Call<String>?, t: Throwable?) {
                            toast("failure"+t!!.message.toString())
                        }

                        override fun onResponse(call: Call<String>?, response: Response<String>?) {
                            if(response?.isSuccessful!!)
                                toast("Votre réservation a bien été prise en compte !")
                            else
                                toast("nonsuccess"+response.message())

                        }

                    })
                    this.finish()

                }
                else {
                    toast("Veuillez vous connecter !")
                }


            }
        })
        appel.setOnClickListener({
           /* if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                                Manifest.permission.CALL_PHONE)) {

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(this,
                            arrayOf(Manifest.permission.READ_CONTACTS),
                            MY_PERMISSIONS_REQUEST_CALL_PHONE)

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                // Permission has already been granted
            }*/
            makeCall("0549528008")
        })


        numberPicker.wrapSelectorWheel = false
    }

    //Vérifier si le wifi est activé
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        return (networkInfo != null && networkInfo?.type == ConnectivityManager.TYPE_WIFI)
    }

    private fun updateDateInView(){
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.FRANCE)
        textview_date!!.text = sdf.format(cal.time)
    }
    private fun updateTimeInView(){
        val myFormat = "HH:mm"
        val stf = SimpleDateFormat(myFormat, Locale.FRANCE)
        textview_time!!.text=stf.format(cal.time)
    }
    /*override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_CALL_PHONE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                    makeCall("0549528008")
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return
            }

        // Add other 'when' lines to check for other
        // permissions this app might request.

            else -> {
                // Ignore all other requests.
            }
        }
    }*/

}
