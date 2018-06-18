package example.android.com.RestoPresto.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import example.android.com.RestoPresto.entities.Restaurant

/**
 * Created by start on 18/06/2018.
 */
@Entity(tableName = "formule",foreignKeys = arrayOf(ForeignKey(entity = Restaurant::class,
        parentColumns = arrayOf("id_restaurant"),
        childColumns = arrayOf("id_restaurant"),
        onDelete = ForeignKey.CASCADE)))
data class Formule (
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name="id_formule")
        var id_formule:Int,
        @ColumnInfo(name = "type")
        var type :String,
        @ColumnInfo(name = "id_restaurant")
        var id_restaurant:Int
)