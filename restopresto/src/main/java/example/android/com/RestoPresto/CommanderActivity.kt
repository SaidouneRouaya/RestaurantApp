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
import example.android.com.RestoPresto.singleton.RoomService
import kotlinx.android.synthetic.main.activity_commander.*
import kotlinx.android.synthetic.main.plat_commande_layout.*
import org.jetbrains.anko.toast

class CommanderActivity : AppCompatActivity(){
    private var mDb: AppDatabase? = RoomService.appDatabase
    var cmdPlatTab = arrayOf<String>()
    var cmdPrixTab = arrayOf<String>()
    var cmdNbTab = arrayOf<String>()
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
        ab?.setDisplayHomeAsUpEnabled(true)
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
        prix_total_cmd.setText(total.toString())
        valider.setOnClickListener({
            openDialog()
        })

    }
    fun openDialog() {
        var dialogCmd = ConfirmCmdDialogFragment()
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
        /*cmdPlatTab = resources.getStringArray(R.array.plat_cmd)
        cmdPrixTab=resources.getStringArray(R.array.prix_cmd)
        cmdNbTab = resources.getStringArray(R.array.nb_cmd)
        val list = mutableListOf<Plat>()
        for (i in 0..cmdPlatTab.size - 1) {
            //list.add(Plat(nom = cmdPlatTab[i], prix = cmdPrixTab[i], nbCmd = cmdNbTab[i]))
            list.add(Plat(nom = cmdPlatTab[i], prix = 123.056))
        }*/
        return liste_commandes
    }


}
