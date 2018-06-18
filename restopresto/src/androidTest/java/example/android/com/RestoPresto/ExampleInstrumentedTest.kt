package example.android.com.RestoPresto

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import example.android.com.RestoPresto.database.AppDatabase
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert
import org.junit.Before


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
   /* @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("example.android.com.myapplication", appContext.packageName)
    }*/

    var appDataBase: AppDatabase?= null

    @Before
    fun initDB() {
        appDataBase =
                Room.inMemoryDatabaseBuilder(InstrumentationRegistry
                        .getContext(), AppDatabase::class.java).build()
    }

    @After
    fun closeDb(){
        appDataBase?.close()
    }

    @Test
    fun getRestaurants() {
        val restaurants= appDataBase?.getRestaurantDao()?.getRestaurants()
        for (restau in restaurants!!)
        {
            println("restau : "+restau.nom)
        }
    }
/*
    @Test
    fun insertAndGetTeam() {
        val team1 = Team(teamName ="Liverpool",continent = "Europe")
        appDataBase?.getTeamDao()?.addTeam(team1)
        val team2 = appDataBase?.getTeamDao()?.getTeamByName("Liverpool")
        Assert.assertEquals(team1,team2)
    }


    @Test
    fun InsertAndGetPlayer() {
        val team = Team(teamName = "Liverpool",continent = "Europa")
        var player1 = Player(firstName ="Mohamed",lastName = "Salah",age = 27,height = 1.76,teamId = 1)
        var player2 = Player(firstName ="Andrew",lastName = "Andrew",age = 24,height = 1.74,teamId = 1)
        appDataBase?.getTeamDao()?.addTeam(team)
        appDataBase?.getPlayerDao()?.addPlayer(player1,player2)
        val players = appDataBase?.getPlayerDao()?.getPlayers()
        Assert.assertArrayEquals(mutableListOf(player1,player2).toTypedArray(),players?.toTypedArray())
    }

*/

}
