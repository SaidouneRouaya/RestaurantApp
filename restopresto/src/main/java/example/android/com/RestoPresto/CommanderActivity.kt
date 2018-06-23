package example.android.com.RestoPresto

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import example.android.com.RestoPresto.database.AppDatabase
import example.android.com.RestoPresto.entities.Commande
import example.android.com.RestoPresto.entities.Ligne_commande
import example.android.com.RestoPresto.entities.Plat
import example.android.com.RestoPresto.service.RetrofitService
import example.android.com.RestoPresto.singleton.RoomService
import kotlinx.android.synthetic.main.activity_commander.*
import kotlinx.android.synthetic.main.plat_commande_layout.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommanderActivity : AppCompatActivity(){
    private var mDb: AppDatabase? = RoomService.appDatabase
    var total: Double= 0.0
    var id_user = 1
    var id_restaurant = 1
    var preferences: SharedPreferences? = null
    var commandes: List<Commande>? = null
    var ligne_commande : List<Ligne_commande> = mutableListOf()
    //var liste_commandes : Map<Ligne_commande,Plat>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commander)
        val ab = getSupportActionBar()
        //ab?.setDisplayHomeAsUpEnabled(true)
        preferences = getSharedPreferences("projetMobile", Context.MODE_PRIVATE)
        // Récupération des variables passées en argument
        id_restaurant =intent.getIntExtra("id_resto",1)
        id_user = preferences!!.getInt("id_user",1)
        val listView= findViewById<ListView>(R.id.list_plats_cmd)

        // Création de l'adapter pour la liste
        var listPlats = loadData()
        if (ligne_commande.isEmpty())
            System.out.println("c ca")
        System.out.println("c ca2")
        val platCmdAdapter = PlatCommandeAdapter(this, ligne_commande,listPlats)
        System.out.println("c ca3")
        listView.adapter = platCmdAdapter
        for (p in listPlats)
        {
            //  total += (listPlats[i].prix*listPlats[i].nbCmd.toInt())
            //à enlever
            total += p.key.nombre * p.value.prix
        }
        listView.setOnItemClickListener({adapterView, view, i, l ->
            val newFragment:nbCmdFragment = nbCmdFragment.newInstance(ligne_commande[i]!!.id_plat,id_restaurant)
            newFragment.show(fragmentManager,"dialog")
        })
        prix_total_cmd.setText(total.toString())
        valider.setOnClickListener({
            if (Util().isNetworkAvailable(this)) {
                if (commandes!!.isNotEmpty() && ligne_commande.isNotEmpty())
                    openDialog()
                else
                    toast("Veuillez sélectionner des plats d'abord !")
            }
            else {
                toast("Veuillez vous connecter !")
            }
        })


    }
    fun openDialog() {
        var dialogCmd = ConfirmCmdDialogFragment.newInstance(commandes!!.get(0).id_cmd)
        dialogCmd.show(supportFragmentManager,"dialog")
    }
    fun loadData():  MutableMap<Ligne_commande,Plat> {
        commandes = mDb!!.getCommandeDao().getCommandesByUserByRestaurant(id_user,id_restaurant)
        var plat : Plat
        var liste_commandes = mutableMapOf<Ligne_commande,Plat>()
        System.out.println("je passe avant commandes.notEmpty "+id_restaurant)
        if (commandes!!.isNotEmpty())
        {
            ligne_commande = mDb!!.getLigneCommandeDao().getLigne_commandesByCommande(commandes!!.get(0).id_cmd)
            System.out.println("je passe ds commandes.notEmpty")
            if (ligne_commande!!.isNotEmpty())
            {
                System.out.println("je passe dans ligne_commandes.notEmpty")
                for( lc in ligne_commande)
                {
                    plat = mDb!!.getPlatDao().getPlatById(lc.id_plat)
                    liste_commandes.put(lc,plat)
                }

            }
        }
        else
        {
            System.out.println("je passe commande est empty")
            ligne_commande = mutableListOf()
            liste_commandes = mutableMapOf()
        }
        return liste_commandes
    }




}
