package example.android.com.RestoPresto.service

import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import example.android.com.RestoPresto.R
import example.android.com.RestoPresto.database.AppDatabase
import example.android.com.RestoPresto.nbCmdFragment
import example.android.com.RestoPresto.service.RetrofitService
import example.android.com.RestoPresto.singleton.RoomService
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
/**
 * Created by start on 21/05/2018.
 */
class CommandeJobService : JobService() {
    var job:JobParameters? = null
    private var mDb: AppDatabase? = RoomService.appDatabase
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
        sendNotification(this,"start_service")
        toast("service commencé")
        this.job=job
        val commandes= mDb!!.getCommandeDao().getCommandesByUserByRestaurant(job!!.extras!!.getInt("id_user"),job!!.extras!!.getInt("id_restaurant"))
        if (commandes.isNotEmpty())
        {
            mDb!!.getCommandeDao().deleteCommande(commandes.get(0))
            sendNotification(this@CommandeJobService,"Votre commande a été supprimée !")
        }
        System.out.print("je passe dans la notif")

        toast("supprimé")
        /*val  teams: List<Team> = RoomService.appDatabase.getTeamDao().getTeams()
        val team = teams.get(teams.size-1)
        val s = RetrofitService.endpoint.addTeam(team)
        s.enqueue(object:Callback<String>
        {
            override fun onFailure(call: Call<String>?, t: Throwable?){
                sendNotification(this@CommandeJobService,"probleme")
            jobFinished(job!!,true)

            }

            override fun onResponse(call: Call<String>?, response: Response<String>?) {
                if(response?.isSuccessful!!){
                    sendNotification(this@CommandeJobService,"fait")
                    toast("Fait !")
                jobFinished(job!!,false)}
                else {
                    sendNotification(this@CommandeJobService,"erreur")
                    jobFinished(job!!, true)
                }

            }
        })*/
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
                .setContentTitle("Commande supprimée2")
                .setContentText(message)
                .setAutoCancel(false)
                .setSound(defaultSoundUri)

        val notificationManager =
                ctx.getSystemService(Context.NOTIFICATION_SERVICE) as
                        NotificationManager

        notificationManager.notify(0, notificationBuilder.build())

    }

}