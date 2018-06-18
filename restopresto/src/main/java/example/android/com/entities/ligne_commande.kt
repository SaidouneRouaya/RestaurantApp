package example.android.com.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

/**
 * Created by start on 18/06/2018.
 */
@Entity(tableName = "ligne_ccommande",foreignKeys = arrayOf(ForeignKey(entity = Commande::class,
        parentColumns = arrayOf("id_cmd"),
        childColumns = arrayOf("id_cmd"),
        onDelete = ForeignKey.CASCADE), ForeignKey(entity = Plat::class,
        parentColumns = arrayOf("id_plat"),
        childColumns = arrayOf("id_plat"),
        onDelete = ForeignKey.CASCADE)))
data class ligne_commande(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name="id_ligne")
        var id_ligne:Int,
        @ColumnInfo(name = "nombre")
        var nombre:Int,
        @ColumnInfo(name = "id_cmd")
        var id_cmd :Int,
        @ColumnInfo(name="id_plat")
        var id_plat:Int
)