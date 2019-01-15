package cvrce.cvrce.com.cvrcecanteen;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.github.ag.floatingactionmenu.OptionsFabLayout;
import com.squareup.picasso.Picasso;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import io.saeid.fabloading.LoadingView;
import nl.dionsegijn.steppertouch.OnStepCallback;
import nl.dionsegijn.steppertouch.StepperTouch;

public class Dinner extends Fragment {

    ArrayList<String> product;
    ArrayList<String> image ;
    ArrayList<String> type ;
    ArrayList<Integer> price;
    ArrayList<String> description ;
    Bundle cart = new Bundle();
    HashSet<String> orderProduct = new HashSet<>();
    HashMap<String, Integer> orderQuantity = new HashMap<>();
    HashMap<String, Integer> orderPrice = new HashMap<>();
    Button btn_cart;
    ListView listDinner;
    MyAdapterOne adaper;
    LoadingView loadingView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dinner_fragment_layout, container, false);
        product = new ArrayList<>();
        image = new ArrayList<>();
        type = new ArrayList<>();
        price = new ArrayList<>();
        description = new ArrayList<>();

        btn_cart = view.findViewById(R.id.goToCartBtn);
        btn_cart.setEnabled(false);
        listDinner = view.findViewById(R.id.list_dinner);
        loadingView = view.findViewById(R.id.loading_view);

        loadingView.addAnimation(Color.CYAN, R.drawable.food_1, LoadingView.FROM_LEFT);
        loadingView.addAnimation(Color.GRAY, R.drawable.restaurant, LoadingView.FROM_TOP);
        loadingView.addAnimation(Color.MAGENTA, R.drawable.food_2, LoadingView.FROM_RIGHT);
        loadingView.addAnimation(Color.BLUE, R.drawable.food_3, LoadingView.FROM_BOTTOM);
        loadingView.startAnimation();
        loadingView.setVisibility(View.VISIBLE);
        Log.d("timeItem","OnCreate");
        Button goToCart = view.findViewById(R.id.goToCartBtn);

        goToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),CartActivity.class);
//                cart.putSerializable("price", orderPrice);
//                cart.putSerializable("quantity", orderQuantity);
//                i.putExtra("Cart",cart);
//                Log.d("cartdebug", orderQuantity.toString());
                i.putExtra("quantity", orderQuantity);
                i.putExtra("price", orderPrice);
                i.putExtra("product", orderProduct);
                startActivity(i);
            }
        });

        new Dinner.FetchData().execute();

        adaper = new MyAdapterOne(product,image, type, price, description, getContext());
        listDinner.setAdapter(adaper);
        return view;
    }


    class FetchData extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {
            if (Looper.myLooper() == null)
            {
                Looper.prepare();
            }
            Log.d("mytag", "Fetch data background");
            StringBuilder sb = new StringBuilder("");
            try {

                BufferedReader reader = InternetData.readData(InternetData.getHTTPConnected(getString(R.string.url)+"fetch_todays_meal.php?dinner=1", true, true, "GET"));
                String s = "";
                Log.d("mytag", "Fetch data after inputstream");
                while ((s = reader.readLine()) != null) {
                    String arr[] = s.split(",");
                    product.add(arr[0]);
                    price.add(Integer.parseInt(arr[3]));
                    type.add(arr[4]);
                    image.add(arr[5]);
                    description.add(arr[6]);

                }
                Log.d("mytag","Items");
                for (int i = 0; i < product.size(); i++) {
                    Log.d("mytag",product.get(i) + " " + " " + price.get(i) + " " + image.get(i) + " " + type.get(i) + " " + description.get(i));
                }


            } catch (IOException e) {
                Toast.makeText(getActivity().getApplicationContext(), "Failed to connect to the College Network", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

            return sb;
        }

        @Override
        protected void onPostExecute(Object o) {
            loadingView.setVisibility(View.INVISIBLE);

            if(product.size() > 0) {
                Log.d("mytag", "post execute" + product.get(0));
                adaper.notifyDataSetChanged();
            }
            else {
                ImageView imageView = getView().findViewById(R.id.image_sorry);
                imageView.setVisibility(View.VISIBLE);
                Button button = getView().findViewById(R.id.goToCartBtn);
                button.setEnabled(false);
            }
            super.onPostExecute(o);
        }
    }


    //MyAdapter Class

    class MyAdapterOne extends BaseAdapter {
        ArrayList<String> product;
        ArrayList<String> image ;
        ArrayList<String> type ;
        ArrayList<Integer> price;
        ArrayList<String> description;
        private Context context;

        public MyAdapterOne(ArrayList<String> product, ArrayList<String> image, ArrayList<String> type, ArrayList<Integer> price, ArrayList<String> description, Context context) {
            this.product = product;
            this.image = image;
            this.type = type;
            this.price = price;
            this.description = description;
            this.context = context;
        }

        @Override
        public int getCount() {
            return product.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {

            Log.d("mytag", "getView");
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.list_item_layout,null);

            ImageView product_image = (ImageView) view.findViewById(R.id.item_image);
            Picasso.get().load(image.get(i)).into(product_image);

            TextView product_name = (TextView) view.findViewById(R.id.item_text_name);
            product_name.setText(product.get(i));

            TextView product_price = (TextView) view.findViewById(R.id.item_text_price);
            product_price.setText(price.get(i)+" ");

            TextView product_desc = (TextView) view.findViewById(R.id.item_text_desc);
            product_desc.setText(description.get(i));

            int[] product_type_image_list = {R.drawable.vegetarian_symbol,R.drawable.nveg};

            ImageView product_type_image = (ImageView) view.findViewById(R.id.item_type_image);
            product_type_image.setImageResource(product_type_image_list[Integer.parseInt(type.get(i))]);

            StepperTouch stepperTouch = (StepperTouch) view.findViewById(R.id.item_quantity);
            stepperTouch.stepper.setMin(0);
            stepperTouch.stepper.setMax(8);
            stepperTouch.enableSideTap(true);
            stepperTouch.stepper.addStepCallback(new OnStepCallback() {
                @Override
                public void onStep(int value, boolean positive) {
                    if(orderQuantity.containsKey(product.get(i))){
                        if(value==0){
                            orderQuantity.remove(product.get(i));
                        }else
                        orderQuantity.put(product.get(i), value);

                    }
                    else{
                        if(value==0){
                        orderPrice.remove(product.get(i));
                        orderQuantity.remove(product.get(i));
                        orderProduct.remove(product.get(i));
                        }
                        orderPrice.put(product.get(i), price.get(i));
                        orderQuantity.put(product.get(i), value);
                        orderProduct.add(product.get(i));
                    }
                    if(orderQuantity.isEmpty())
                        btn_cart.setEnabled(false);
                    else
                        btn_cart.setEnabled(true);
                }
            });


            return view;
        }
    }



}
