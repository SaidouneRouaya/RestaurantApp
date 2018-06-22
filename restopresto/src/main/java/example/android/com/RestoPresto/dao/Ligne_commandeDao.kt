package example.android.com.RestoPresto.dao

import android.arch.persistence.room.*
import example.android.com.RestoPresto.entities.Ligne_commande

/**
 * Created by start on 18/06/2018.
 */
@Dao
interface Ligne_commandeDao {
    @Query("select * from ligne_commande")
    fun getLigne_commandes():List<Ligne_commande>

    @Query("select * from ligne_commande p natural join commande c where c.id_cmd=:id_cmd")
    fun getLigne_commandesByCommande(id_cmd:Int):List<Ligne_commande>

    @Query("select * from ligne_commande l where id_plat=:id_plat and id_cmd=:id_cmd")
    fun getLigne_commandeByCommandeByPlat(id_cmd: Int,id_plat:Int): List<Ligne_commande>

    @Insert
    fun addLigne_commandes(vararg Ligne_commande: Ligne_commande)

    @Update
    fun updateLigne_commande(Ligne_commande: Ligne_commande)

    @Delete
    fun deleteLigne_commande(Ligne_commande: Ligne_commande)

    @Delete
    fun deleteAllLigne_commandes(Ligne_commandes:List<Ligne_commande>)
}