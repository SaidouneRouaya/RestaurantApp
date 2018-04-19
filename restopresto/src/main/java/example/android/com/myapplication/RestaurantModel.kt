package example.android.com.myapplication

import android.arch.lifecycle.ViewModel
import example.android.com.myapplication.entity.Restaurant

/**
 * Created by start on 01/04/2018.
 */
class RestaurantModel: ViewModel() {
    var restaurant:Restaurant = Restaurant()
}