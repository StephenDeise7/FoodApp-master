package ie.wit.foodapp.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

import ie.wit.foodapp.R;
import ie.wit.foodapp.main.MapsActivity;
import ie.wit.foodapp.models.Food;
import ie.wit.foodapp.adapters.FoodList;


public class Add extends Base {

    //initialize
    EditText editTextName, editTextShop, editTextNumber;

    Button buttonAddUser;
    ListView listViewUsers;
    //a list to store all the Food from firebase database
    List<Food> foods;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // method for find ids of views
        findViews();

        // to maintian click listner of views
        initListner();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_foods:
                        Intent intent1 = new Intent(Add.this, Foods.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_add:
                        Intent intent2 = new Intent(Add.this, Add.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_maps:
                        Intent intent3 = new Intent(Add.this, MapsActivity.class);
                        startActivity(intent3);
                        break;

                }
                return true;
            }
        });


    }


    private void findViews() {
        //getRefrance for user table
        databaseReference = FirebaseDatabase.getInstance().getReference("Foods");

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextShop = (EditText) findViewById(R.id.editTextshop);
        editTextNumber = (EditText) findViewById(R.id.editTextNumber);
        listViewUsers = (ListView) findViewById(R.id.listViewUsers);
        buttonAddUser = (Button) findViewById(R.id.buttonAddUser);
        //list for store objects of user
        foods = new ArrayList<>();
    }

    private void initListner() {
        //adding an onclicklistener to button
        buttonAddUser.setOnClickListener(new View.OnClickListener()      {
            @Override
            public void onClick(View view) {
                //calling the method addUser()
                //the method is defined below
                //this method is actually performing the write operation
                addUser();

            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clearing the previous Food list
                foods.clear();
                //getting all nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting Food from firebase console
                    Food Food = postSnapshot.getValue(Food.class);
                    //adding Food to the list
                    foods.add(Food);
                }
                //creating Userlist adapter
                FoodList UserAdapter = new FoodList(Add.this, foods);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }




    private void addUser() {

        //getting the values to save
        String name = editTextName.getText().toString().trim();
        String Shop = editTextShop.getText().toString().trim();
        String price = editTextNumber.getText().toString().trim();



        //checking if the value is provided or not Here, you can Add More Validation as you required

        if (!TextUtils.isEmpty(name)) {
            if (!TextUtils.isEmpty(Shop)) {
                if (!TextUtils.isEmpty(price)) {

                    //it will create a unique id and we will use it as the Primary Key for our Food
                    String id = databaseReference.push().getKey();
                    //creating an Food Object
                    Food Food = new Food(id, name, Shop, price);
                    //Saving the Food
                    databaseReference.child(id).setValue(Food);

                    editTextName.setText("");
                    editTextNumber.setText("");
                    editTextShop.setText("");
                    Toast.makeText(this, "Food added", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Add.this, Foods.class));
                } else {
                    Toast.makeText(this, "Please enter a price", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Please enter a Restuarant", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
        }

    }
}