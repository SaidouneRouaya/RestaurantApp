package example.android.com.entities

import android.arch.persistence.room.*

/**
 * Created by start on 18/06/2018.
 */
@Entity(tableName="restaurant")
data class Restaurant (
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name ="id_restaurant")
        var id_restaurant:Int,
        @ColumnInfo(name ="nom")
        var nom : String,
        @ColumnInfo(name ="n_tel")
        var n_tel : String,
        @ColumnInfo(name ="adresse")
        var adresse : String,
        @ColumnInfo(name ="email")
        var email : String,
        @ColumnInfo(name ="description")
        var description : String,
        @ColumnInfo(name ="twitter")
        var twitter : String,
        @ColumnInfo(name ="facebook")
        var facebook : String,
        @ColumnInfo(name ="lien")
        var lien : String,
        @ColumnInfo(name ="latitude")
        var latitude : Double,
        @ColumnInfo(name ="longitude")
        var longitude : Double
)