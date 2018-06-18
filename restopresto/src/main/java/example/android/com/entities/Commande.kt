package example.android.com.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

/**
 * Created by start on 18/06/2018.
 */
@Entity(tableName = "commande",foreignKeys = arrayOf(ForeignKey(entity = Restaurant::class,
        parentColumns = arrayOf("id_restaurant"),
        childColumns = arrayOf("id_restaurant"),
        onDelete = ForeignKey.CASCADE), ForeignKey(entity = User::class,
        parentColumns = arrayOf("id_user"),
        childColumns = arrayOf("id_user"),
        onDelete = ForeignKey.CASCADE)))
data class Commande(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name="id_cmd")
        var id_cmd:Int,
        @ColumnInfo(name = "date")
        var date:String,
        @ColumnInfo(name = "heure")
        var heure:String,
        @ColumnInfo(name="id_restaurant")
        var id_restaurant:Int,
        @ColumnInfo(name ="id_user")
        var id_user:Int

)