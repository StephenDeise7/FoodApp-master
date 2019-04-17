package ie.wit.foodapp.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

import ie.wit.foodapp.R;
import ie.wit.foodapp.models.Food;

public class FoodList extends ArrayAdapter<Food> {
    private Activity context;
    //list of users
    List<Food> foods;

    public FoodList(Activity context, List<Food> foods) {
        super(context, R.layout.layout_food_list, foods);
        this.context = context;
        this.foods = foods;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_food_list, null, true);
        //initialize
        TextView textViewName = (TextView) listViewItem.findViewById
                (R.id.textViewName);
        TextView textviewshop = (TextView) listViewItem.findViewById
                (R.id.textviewshop);
        TextView textviewnumber = (TextView) listViewItem.findViewById
                (R.id.textviewnumber);


        Food Food = foods.get(position);

        textViewName.setText(Food.getUsername());

        textviewshop.setText(Food.getShop());

        textviewnumber.setText(Food.getPrice());

        return listViewItem;
    }
}