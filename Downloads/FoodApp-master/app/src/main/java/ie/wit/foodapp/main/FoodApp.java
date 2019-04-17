package ie.wit.foodapp.main;

import android.app.Application;
import android.util.Log;




public class FoodApp extends Application
{

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.v("foodapp", "Food App Started");
    }
}