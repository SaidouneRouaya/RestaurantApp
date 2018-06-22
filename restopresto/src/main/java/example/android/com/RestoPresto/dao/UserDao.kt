package example.android.com.RestoPresto.dao

import android.arch.persistence.room.*
import example.android.com.RestoPresto.entities.User

/**
 * Created by start on 18/06/2018.
 */
@Dao
interface UserDao {
    @Query("select * from user")
    fun getUsers():List<User>

    @Query("select * from user where mail=:email ")
    fun getUsersByEmail(email:String):List<User>

    @Insert
    fun addUsers(vararg User: User)

    @Update
    fun updateUser(User: User)

    @Delete
    fun deleteUser(User: User)

    @Delete
    fun deleteAllUsers(Users:List<User>)
}