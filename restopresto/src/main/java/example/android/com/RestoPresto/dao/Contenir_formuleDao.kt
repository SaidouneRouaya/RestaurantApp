package example.android.com.RestoPresto.dao

import android.arch.persistence.room.*
import example.android.com.RestoPresto.entities.Contenir_formule
import example.android.com.RestoPresto.entities.Ligne_commande
import example.android.com.RestoPresto.entities.Plat

/**
 * Created by start on 18/06/2018.
 */
@Dao
interface Contenir_formuleDao {
    @Query("select * from contenir_formule")
    fun getContenir_formule():List<Contenir_formule>

    @Query("select * from plat natural join contenir_formule where contenir_formule.id_plat = plat.id_plat " +
            "and contenir_formule.id_formule = :id_formule and plat.type=:typePlat")
    fun getPlatsByformule(id_formule:Int, typePlat:String):List<Plat>

    @Insert
    fun addContenir_formules(vararg Contenir_formule: Contenir_formule)

    @Update
    fun updateContenir_formule(Contenir_formule: Contenir_formule)

    @Delete
    fun deleteContenir_formule(Contenir_formule: Contenir_formule)

    @Delete
    fun deleteAllContenir_formules(Contenir_formules:List<Contenir_formule>)
}