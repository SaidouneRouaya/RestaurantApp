package example.android.com.RestoPresto

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import example.android.com.RestoPresto.entities.plat
import kotlinx.android.synthetic.main.activity_commander.*
import kotlinx.android.synthetic.main.plat_commande_layout.*
import org.jetbrains.anko.toast

class CommanderActivity : AppCompatActivity(){

    var cmdPlatTab = arrayOf<String>()
    var cmdPrixTab = arrayOf<String>()
    var cmdNbTab = arrayOf<String>()
    var total: Double= 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commander)
        val ab = getSupportActionBar()
        ab?.setDisplayHomeAsUpEnabled(true)
        // Création de l'adapter pour la liste
        var listPlats = loadData()
        val platCmdAdapter = PlatCommandeAdapter(this, listPlats)
        list_plats_cmd.adapter = platCmdAdapter
        for (i in 0..listPlats.size-1)
        {
            total += (listPlats[i].prix.toDouble()*listPlats[i].nbCmd.toInt())
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
    fun loadData(): List<plat> {
        cmdPlatTab = resources.getStringArray(R.array.plat_cmd)
        cmdPrixTab=resources.getStringArray(R.array.prix_cmd)
        cmdNbTab = resources.getStringArray(R.array.nb_cmd)
        val list = mutableListOf<plat>()
        for (i in 0..cmdPlatTab.size - 1) {
            list.add(plat(nom = cmdPlatTab[i], prix = cmdPrixTab[i], nbCmd = cmdNbTab[i]))

        }
        return list
    }

}
