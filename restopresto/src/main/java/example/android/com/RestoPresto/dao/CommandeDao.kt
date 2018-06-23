package example.android.com.RestoPresto.dao

import android.arch.persistence.room.*
import example.android.com.RestoPresto.entities.Commande

/**
 * Created by start on 18/06/2018.
 */
@Dao
interface CommandeDao {
    @Query("select * from commande")
    fun getCommandes():List<Commande>

    @Query("select * from commande p natural join restaurant r where r.id_restaurant=:id_restaurant")
    fun getCommandesByRestaurant(id_restaurant:Int):List<Commande>

    @Query("select * from commande p natural join user u where u.id_user=:id_user")
    fun getCommandesByUser(id_user:Int):List<Commande>

    @Query("select * from commande  where id_cmd=:id_cmd")
    fun getCommandesById(id_cmd:Int):Commande

    @Query("select * from commande where id_user=:id_user and id_restaurant=:id_restaurant and termine='0'")
    fun getCommandesByUserByRestaurant(id_user:Int,id_restaurant:Int):List<Commande>

    @Insert
    fun addCommandes(vararg Commande: Commande)

    @Update
    fun updateCommande(Commande: Commande)

    @Delete
    fun deleteCommande(Commande: Commande)

    @Delete
    fun deleteAllCommandes(Commandes:List<Commande>)
}