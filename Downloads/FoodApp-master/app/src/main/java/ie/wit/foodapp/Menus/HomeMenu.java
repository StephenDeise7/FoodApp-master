package ie.wit.foodapp.Menus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import ie.wit.foodapp.R;
import ie.wit.foodapp.activities.*;

public class HomeMenu extends Base
{

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homemenu);



        firebaseAuth = FirebaseAuth.getInstance();

        //if getCurrentUser does not returns null
        if(firebaseAuth.getCurrentUser() != null){
            //that means user is already logged in
            //so close this activity
            finish();
            startActivity(new Intent(HomeMenu.this, MainActivity.class));

            //and open main activity

        }
    }
    public void go2Register (View view){
        Intent intent = new Intent(this, MainLoginActivity.class);
        startActivity(intent);
    }
    public void go2Login (View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
