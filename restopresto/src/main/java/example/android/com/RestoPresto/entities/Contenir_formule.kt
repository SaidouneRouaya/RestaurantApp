package example.android.com.RestoPresto.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey


/**
 * Created by start on 19/06/2018.
 */
@Entity(tableName = "contenir_formule",
        primaryKeys = arrayOf( "id_formule", "id_plat"),
        foreignKeys = arrayOf(
            ForeignKey(entity = Formule::class,
                    parentColumns = arrayOf("id_formule"),
            childColumns = arrayOf("id_formule")),
            ForeignKey(entity = Plat::class,
                    parentColumns = arrayOf("id_plat"),
            childColumns = arrayOf("id_plat"))
        ))
class Contenir_formule(val id_formule: Int, val id_plat: Int)