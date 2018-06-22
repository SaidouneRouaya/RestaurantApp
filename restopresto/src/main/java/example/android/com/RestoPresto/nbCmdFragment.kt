package example.android.com.RestoPresto


import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.content.DialogInterface
import android.content.SharedPreferences
import android.widget.*
import com.firebase.jobdispatcher.*
import example.android.com.RestoPresto.database.AppDatabase
import example.android.com.RestoPresto.entities.Commande
import example.android.com.RestoPresto.entities.Ligne_commande
import example.android.com.RestoPresto.service.CommandeJobService
import example.android.com.RestoPresto.singleton.RoomService
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class nbCmdFragment : DialogFragment(), NumberPicker.OnValueChangeListener {
    override fun onValueChange(picker: NumberPicker?, oldVal: Int, newVal: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    private var mDb: AppDatabase? = RoomService.appDatabase
    var preferences: SharedPreferences? = null
    private var  nombrecmd:NumberPicker? = null
    private var id_plat:Int=0
    private var id_restaurant:Int=0
    private var id_user:Int=0

    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)
    val stf = SimpleDateFormat("hh:mm", Locale.FRANCE)
    companion object {
    fun newInstance(id_plat:Int,id_restaurant:Int): nbCmdFragment {
        val frag = nbCmdFragment()
        val args = Bundle()
        args.putInt("id_plat", id_plat)
        args.putInt("id_restaurant",id_restaurant)
                frag.setArguments(args)
        return frag
    }
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity.layoutInflater.inflate(R.layout.fragment_nb_cmd, null)
        val args=arguments
        var lignecmd: List<Ligne_commande>
        preferences = activity.getSharedPreferences("projetMobile", Context.MODE_PRIVATE)
        id_plat=args.getInt("id_plat",1)
        id_restaurant=args.getInt("id_restaurant",1)
        id_user=preferences!!.getInt("id_user",1)
        var commandes =  mDb!!.getCommandeDao().getCommandesByUserByRestaurant(preferences!!.getInt("id_user",1),id_restaurant)
        nombrecmd= view.findViewById<NumberPicker>(R.id.nombreCmd)
        nombrecmd!!.minValue = 0
        nombrecmd!!.maxValue = 10
        if (commandes.isNotEmpty()) {
            lignecmd = mDb!!.getLigneCommandeDao().getLigne_commandeByCommandeByPlat(commandes.get(0).id_cmd, id_plat)
            if (lignecmd.isNotEmpty()) {
                nombrecmd!!.value = lignecmd.get(0).nombre
            }
        }
        val builder = AlertDialog.Builder(activity)
        builder.setPositiveButton(R.string.valider, object : DialogInterface.OnClickListener {
            override  fun onClick(dialog: DialogInterface, id: Int) {
                if (commandes.isEmpty())
                {
                    mDb!!.getCommandeDao().addCommandes(Commande(0,sdf.format(Date()),stf.format(Date()),id_restaurant,id_user,0))
                    commandes = mDb!!.getCommandeDao().getCommandesByUserByRestaurant(id_user,id_restaurant)
                    System.out.println("c : "+commandes.get(0).id_cmd+"  "+id_plat)
                    mDb!!.getLigneCommandeDao().addLigne_commandes(Ligne_commande(0,nombrecmd!!.value,commandes.get(0).id_cmd,id_plat))
                }
                else
                {
                    lignecmd = mDb!!.getLigneCommandeDao().getLigne_commandeByCommandeByPlat(commandes.get(0).id_cmd, id_plat)
                    if (lignecmd.isNotEmpty()) {
                        lignecmd.get(0).nombre = nombrecmd!!.value
                        mDb!!.getLigneCommandeDao().updateLigne_commande(lignecmd.get(0))
                    }
                    else
                    {
                        System.out.println("c : "+commandes.get(0).id_cmd+"  "+id_plat)
                        mDb!!.getLigneCommandeDao().addLigne_commandes(Ligne_commande(0,nombrecmd!!.value,commandes.get(0).id_cmd,id_plat))
                    }
                }
                }
                })
                .setNegativeButton(R.string.annuler, object : DialogInterface.OnClickListener {
                    override  fun onClick(dialog: DialogInterface, id: Int) {
                    dismiss()
                    }
                })
        return builder.setTitle("Nombre de plats").setView(view).create()
    }
    fun lancerJob()
    {
        val dispatcher = FirebaseJobDispatcher(GooglePlayDriver(activity))
        val args = Bundle()
        args.putInt("id_plat", id_plat)
        args.putInt("id_restaurant",id_restaurant)
        args.putInt("id_user", id_user)
        val myJob = dispatcher?.newJobBuilder()
                .setService(CommandeJobService::class.java)
                .setRecurring(false) // Exécuter une fois
                .setTag("lll")
                .setExtras(args)
                .setLifetime(Lifetime.FOREVER) // durée de vie
                .setTrigger(Trigger.executionWindow(3600, 3630)) // temps de lancement
                .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR) // stratégie de relance
                .setConstraints(Constraint.ON_UNMETERED_NETWORK).build() // mode WiFi uniquement
        dispatcher?.mustSchedule(myJob)
    }
}

// Required empty public constructor
