package ie.wit.foodapp.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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


public class Foods extends Base {

    //initialize
    EditText editTextName, editTextShop, editTextNumber ,editTextFavourite;
    Button buttonAddUser;
    ListView listViewUsers;
    //a list to store all the Food from firebase database
    List<Food> foods;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods);
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
                        Intent intent1 = new Intent(Foods.this, Foods.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_add:
                        Intent intent2 = new Intent(Foods.this, Add.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_maps:
                        Intent intent3 = new Intent(Foods.this, MapsActivity.class);
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


        // list item click listener
        listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Food Food = foods.get(i);
                CallUpdateAndDeleteDialog(Food.getUserid(), Food.getUsername(), Food.getShop(), Food.getPrice());


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
                FoodList UserAdapter = new FoodList(Foods.this, foods);
                //attaching adapter to the listview
                listViewUsers.setAdapter(UserAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void CallUpdateAndDeleteDialog(final String userid, String username, final String shop, String price) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);
        //Access Dialog views
        final EditText updateTextname = (EditText) dialogView.findViewById(R.id.updateTextname);
        final EditText updateTextshop = (EditText) dialogView.findViewById(R.id.updateTextshop);
        final EditText updateTextprice = (EditText) dialogView.findViewById(R.id.updateTextprice);

        updateTextname.setText(username);
        updateTextshop.setText(shop);
        updateTextprice.setText(price);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateUser);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteUser);
        //username for set dialog title
        dialogBuilder.setTitle(username);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        // Click listener for Update data
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = updateTextname.getText().toString().trim();
                String shop = updateTextshop.getText().toString().trim();
                String price = updateTextprice.getText().toString().trim();
                //checking if the value is provided or not Here, you can Add More Validation as you required

                if (!TextUtils.isEmpty(name)) {
                    if (!TextUtils.isEmpty(shop)) {
                        if (!TextUtils.isEmpty(price)) {
                            //Method for update data
                            updateUser(userid, name, shop, price);
                            b.dismiss();
                        }
                    }
                }

            }
        });

        // Click listener for Delete data
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Method for delete data
                deleteUser(userid);
                b.dismiss();
            }
        });
    }

    private boolean updateUser(String id, String name, String shop, String mobilenumber) {
        //getting the specified Food reference
        DatabaseReference UpdateReference = FirebaseDatabase.getInstance().getReference("Foods").child(id);
        Food Food = new Food(id, name, shop, mobilenumber);
        //update  Food  to firebase
        UpdateReference.setValue(Food);
        Toast.makeText(getApplicationContext(), "Food Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean deleteUser(String id) {
        //getting the specified Food reference
        DatabaseReference DeleteReference = FirebaseDatabase.getInstance().getReference("Foods").child(id);
        //removing Food
        DeleteReference.removeValue();
        Toast.makeText(getApplicationContext(), "Food Deleted", Toast.LENGTH_LONG).show();
        return true;
    }



}