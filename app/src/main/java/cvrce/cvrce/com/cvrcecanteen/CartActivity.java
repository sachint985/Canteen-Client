package cvrce.cvrce.com.cvrcecanteen;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static java.security.AccessController.getContext;

public class CartActivity extends AppCompatActivity {


    HashMap<String, Integer> quantity;
    HashMap<String, Integer> price;
    HashSet<String> product;
    ArrayList<Integer> amount;
    int cost=0;

    ListView listCart;
    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //Bundle cart = getIntent().getExtras();

        Log.d("cartdebug", "Inside Cart Activity");
        //Log.d("cartdebug", cart.toString());

        quantity = new HashMap<>();
        price = new HashMap<>();
        amount = new ArrayList<>();
        listCart = findViewById(R.id.cart_list);


        product = (HashSet<String>) getIntent().getSerializableExtra("product");
        quantity = (HashMap<String, Integer>) getIntent().getSerializableExtra("quantity");
        price = (HashMap<String, Integer>) getIntent().getSerializableExtra("price");

        Log.d("cartdebug", quantity.toString());
        Log.d("cartdebug", price.toString());
        Log.d("cartdebug", product.toString());

        cartAdapter = new CartAdapter(product, quantity, price, amount, cost, getApplicationContext());
        listCart.setAdapter(cartAdapter);

    }

    private class CartAdapter extends BaseAdapter {

        HashSet<String> product = new HashSet<>();
        HashMap<String, Integer> price;
        HashMap<String, Integer> quantity;
        ArrayList<Integer> amount;
        private Context context;
        int cost;
        Object [] p = new Object[]{};


        public CartAdapter(HashSet<String> product,HashMap<String, Integer> price, HashMap<String, Integer> quantity, ArrayList<Integer> amount, int cost, Context context) {
            this.product = product;
            this.price = price;
            this.quantity = quantity;
            this.amount = amount;
            this.cost = cost;
            this.context = context;

            this.p = product.toArray();

            //Log.d("cartdebug", p.toString());


        }

        //Object[] p = product.toArray();

        @Override
        public int getCount() {
            return price.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.cart_item_list_layout, null);

            //Log.d("cartdebug", p.toString());

            TextView product_name = (TextView) convertView.findViewById(R.id.cart_item_name);
            product_name.setText(p[position].toString());

            TextView product_quantity = (TextView) convertView.findViewById(R.id.cart_item_quantity);
            product_quantity.setText(quantity.get(p[position]).toString());

            TextView product_amount = (TextView) convertView.findViewById(R.id.cart_item_amount);
            product_amount.setText(((quantity.get(p[position]))*(price.get(p[position])))+" ");

            cost+=(quantity.get(p[position])*price.get(p[position]));

            return convertView;
        }
    }
}
