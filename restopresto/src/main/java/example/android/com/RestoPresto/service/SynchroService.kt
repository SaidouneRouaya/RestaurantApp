package example.android.com.RestoPresto.service

import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import example.android.com.RestoPresto.R
import example.android.com.RestoPresto.RestaurantAdapter
import example.android.com.RestoPresto.database.AppDatabase
import example.android.com.RestoPresto.entities.Restaurant
import example.android.com.RestoPresto.nbCmdFragment
import example.android.com.RestoPresto.singleton.RoomService
import kotlinx.android.synthetic.main.fragment_main.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by start on 24/06/2018.
 */
class SynchroService: JobService() {
    var job: JobParameters? = null
    private var mDb: AppDatabase? = RoomService.appDatabase
    var restaurantsRemote :List<Restaurant>? = null
    var restaurantsLocal :List<Restaurant>? = null
    companion object {
        fun newInstance(id_plat:Int,id_restaurant:Int): nbCmdFragment {
            val frag = nbCmdFragment()
            val args = Bundle()
            args.putInt("id_plat", id_plat)
            args.putInt("id_restaurant",id_restaurant)
            frag.setArguments(args)
            return frag
        }
    }
    override fun onStartJob(job: JobParameters?): Boolean {
        this.job=job
        //toast("synchro")
        System.out.print("je passe dans la notif")
        val call = RetrofitService.endpoint.getRestaurants()
        call.enqueue(object : Callback<List<Restaurant>> {
            override fun onResponse(call: Call<List<Restaurant>>?, response: Response<List<Restaurant>>?) {
                //act.progressBar1.visibility = View.GONE
                if (response?.isSuccessful!!) {
                    //act.toast("response successful 1")
                    restaurantsRemote = response?.body()
                    // act.progressBar1.visibility = View.GONE
                    restaurantsLocal = mDb!!.getRestaurantDao().getRestaurants()
                    if (!restaurantsRemote!!.equals(restaurantsLocal))
                    {
                        sendNotification(this@SynchroService,"Un nouveau restaurant ajouté consultez la liste pour le voir!")
                        RoomService.appDatabase.getRestaurantDao().addRestaurants(restaurantsRemote!!.get(restaurantsRemote!!.size-1))
                    }
                    // save restaurants in SQLite DB

                } else {
                    //act.toast("Une erreur s'est produite1")
                }
            }

            override fun onFailure(call: Call<List<Restaurant>>?, t: Throwable?) {
                // act.progressBar1.visibility = View.GONE
                //act.toast("Une erreur s'est produite2")
            }


        })
        return true
    }

    override fun onStopJob(job: JobParameters?): Boolean {
        return false
    }
    private fun sendNotification(ctx: Context, message: String) {

        val defaultSoundUri =
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(ctx,"ssss")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Nouveau resto")
                .setContentText(message)
                .setAutoCancel(false)
                .setSound(defaultSoundUri)

        val notificationManager =
                ctx.getSystemService(Context.NOTIFICATION_SERVICE) as
                        NotificationManager

        notificationManager.notify(0, notificationBuilder.build())

    }

}