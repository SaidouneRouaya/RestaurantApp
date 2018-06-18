package example.android.com.RestoPresto

import android.app.Application
import example.android.com.RestoPresto.singleton.RoomService

/**
 * Created by start on 23/04/2018.
 */
class App: Application(){
    override fun onCreate() {
        super.onCreate()
        RoomService.context = applicationContext
    }
}