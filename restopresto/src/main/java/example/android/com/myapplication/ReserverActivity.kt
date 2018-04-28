package example.android.com.myapplication

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_reserver.*
import org.jetbrains.anko.makeCall
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*

class ReserverActivity : AppCompatActivity() {

    var cal = Calendar.getInstance()
    var textview_time: TextView? = null
    var bouton_time:Button? = null
    var textview_date:TextView? = null
    var bouton_date:Button? = null
    var numberpicker:NumberPicker? = null
    var MY_PERMISSIONS_REQUEST_CALL_PHONE:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserver)
        val ab = getSupportActionBar()
        ab?.setDisplayHomeAsUpEnabled(true)
        //Récupération des références du layout 11201201
        textview_date = this.date_textview
        bouton_date = this.button_pick_date
        textview_time= this.time_textview
        bouton_time=this.button_pick_time
        numberpicker= this.numberPicker

        textview_date!!.text= "--/--/----"
        textview_time!!.text="--:--"
        numberPicker.minValue = 1
        numberPicker.maxValue = 10

        nom_resto.text= intent.getStringExtra("nom")

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
                toast("Votre réservation a bien été prise en compte !")
                this.finish()
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
