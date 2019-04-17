package ie.wit.foodapp.Menus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import ie.wit.foodapp.R;
import ie.wit.foodapp.activities.Add;
import ie.wit.foodapp.activities.Base;
import ie.wit.foodapp.activities.Foods;
import ie.wit.foodapp.activities.ImagesActivity;
import ie.wit.foodapp.activities.MainImageActivity;
import ie.wit.foodapp.main.MapsActivity;

public class MainActivity extends Base {

    int[] images = {R.drawable.food, R.drawable.camera_lg,R.drawable.maps};

    String[] version = { "Menu text", "Menu Images","Maps"};

    String[] versionNumber = {"Add food without an image","Add food with an image","Maps"};

    ListView lView;

    ie.wit.foodapp.Menus.ListAdapter lAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);




        lView = (ListView) findViewById(R.id.androidList);

        lAdapter = new ie.wit.foodapp.Menus.ListAdapter(MainActivity.this, version, versionNumber, images);

        lView.setAdapter(lAdapter);

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(MainActivity.this, version[i]+" "+versionNumber[i], Toast.LENGTH_SHORT).show();

                if (i==0)
                {

                    Intent intent = new Intent (view.getContext(), Foods.class);
                    startActivity(intent);
                }
                else if (i==1)
                {
                    Intent intent = new Intent (view.getContext(), ImagesActivity.class);
                    startActivity(intent);
                }

                else if (i==2)
                {
                    Intent intent = new Intent (view.getContext(), MapsActivity.class);
                    startActivity(intent);
                }


            }
        });

    }


}
