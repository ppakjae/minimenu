package com.example.minimenu;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class FinalActivity extends AppCompatActivity {

    ListView list_receipt;
    MenuOrderedAdapter adapter;

    ArrayList<String> MenuName = new ArrayList<>();
    ArrayList<String> MenuPrice = new ArrayList<>();
    ArrayList<String> MenuCount = new ArrayList<>();
    ArrayList<String> nMenuPrice = new ArrayList<>();

    ArrayList <MenuOrderedItem> OrderedMenus = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        list_receipt = (ListView)findViewById(R.id.list_receipt);

        Intent intent = new Intent(this.getIntent());

        MenuName = intent.getStringArrayListExtra("MenuName");
        MenuPrice = intent.getStringArrayListExtra("MenuPrice");
        MenuCount = intent.getStringArrayListExtra("MenuCount");

        adapter = new MenuOrderedAdapter();
        adapter.readContact();
        list_receipt.setAdapter(adapter);

    }

    class MenuOrderedAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return OrderedMenus.size();
        }

        @Override
        public Object getItem(int position) {
            return OrderedMenus.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MenuOrderedView view = new MenuOrderedView(getApplicationContext());

            MenuOrderedItem item = OrderedMenus.get(position);
            view.setTvMenu(item.getMenu());
            view.setTvPrice(item.getPrice());
            view.setTvCount_Cart(item.getCount());
            return view;
        }

        public void addSelectedMenu(MenuOrderedItem view){
            OrderedMenus.add(view);
        }

        public void readContact() {
            for (int i=0; i<MenuName.size() ; i++) {
                if (Integer.parseInt(MenuCount.get(i)) != 0) {
                    addSelectedMenu(new MenuOrderedItem(MenuName.get(i), MenuPrice.get(i), MenuCount.get(i)));
                }
            }
        }

    }

    class MenuOrderedView extends LinearLayout {

        TextView tvOrderedMenu;
        TextView tvOrderedPrice;
        TextView tvOrderedCount;

        public MenuOrderedView(Context context) {
            super(context);

            init(context);
        }

        public MenuOrderedView(Context context, AttributeSet attrs) {
            super(context, attrs);

            init(context);
        }

        public void init(Context context) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.menuselect_receipt,this,true);

            tvOrderedMenu = (TextView) findViewById(R.id.tvOrderedMenu);
            tvOrderedPrice = (TextView) findViewById(R.id.tvOrderedPrice);
            tvOrderedCount = (TextView) findViewById(R.id.tvOrderedCount);

        }

        public void setTvMenu(String menu) {
            tvOrderedMenu.setText(menu);
        }

        public void setTvPrice(String price) {
            tvOrderedPrice.setText(price);
        }

        public void setTvCount_Cart(String count) {
            tvOrderedCount.setText(count);
        }
    }


}
