package cvrce.cvrce.com.cvrcecanteen;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class CartActivity extends AppCompatActivity {

    ArrayList<String> product;
    ArrayList<Integer> quantity;
    ArrayList<Integer> price;
    ArrayList<Integer> amount;
    int cost=0;

    ListView listCart;
    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Bundle cart = new Bundle();
        cart = getIntent().getExtras();

        product = new ArrayList<>();
        quantity = new ArrayList<>();
        price = new ArrayList<>();
        amount = new ArrayList<>();
        listCart = findViewById(R.id.cart_list);

        product = (ArrayList<String>) cart.get("product");
        quantity = (ArrayList<Integer>) cart.get("quantity");
        price = (ArrayList<Integer>) cart.get("price");

        cartAdapter = new CartAdapter(product, quantity, price, amount, cost, getApplicationContext());
        listCart.setAdapter(cartAdapter);

    }

    private class CartAdapter extends BaseAdapter {
        ArrayList<String> product;
        ArrayList<Integer> price;
        ArrayList<Integer> quantity;
        ArrayList<Integer> amount;
        private Context context;
        int cost;

        public CartAdapter(ArrayList<String> product, ArrayList<Integer> price, ArrayList<Integer> quantity, ArrayList<Integer> amount, int cost, Context context) {
            this.product = product;
            this.price = price;
            this.quantity = quantity;
            this.amount = amount;
            this.cost = cost;
            this.context = context;
        }

        @Override
        public int getCount() {
            return product.size();
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

            TextView product_name = (TextView) convertView.findViewById(R.id.cart_item_name);
            product_name.setText(product.get(position));

            TextView product_quantity = (TextView) convertView.findViewById(R.id.cart_item_quantity);
            product_quantity.setText(quantity.get(position)+" ");

            TextView product_amount = (TextView) convertView.findViewById(R.id.cart_item_amount);
            product_amount.setText((quantity.get(position)*price.get(position))+" ");

            cost+=(quantity.get(position)*price.get(position));

            return convertView;
        }
    }
}
