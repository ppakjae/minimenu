package com.example.minimenu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MenuCartActivity extends AppCompatActivity {

    ImageView imgBack_Cart;

    ListView listSelect_Cart;
    MenuSelectedAdapter adapter;

    TextView tvAllPrice_Cart;
    TextView tvAllCount_Cart;

    ArrayList<String> MenuName = new ArrayList<>();
    ArrayList<String> MenuPrice = new ArrayList<>();
    ArrayList<String> MenuCount = new ArrayList<>();
    ArrayList<String> nMenuPrice = new ArrayList<>();

    ArrayList<MenuSelectedItem> SelectedMenus = new ArrayList<MenuSelectedItem>();

    int AllPrice = 0;
    int CountSum = 0;
    String strAllPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cart);

        tvAllPrice_Cart = (TextView) findViewById(R.id.tvAllPrice_Cart);
        tvAllCount_Cart = (TextView) findViewById(R.id.tvAllCount_Cart);

        Intent intent = new Intent(this.getIntent());

        MenuName = intent.getStringArrayListExtra("MenuName");
        MenuPrice = intent.getStringArrayListExtra("MenuPrice");
        MenuCount = intent.getStringArrayListExtra("MenuCount");

        SumPrice();

        tvAllPrice_Cart.setText(new StringBuffer(strAllPrice).reverse().toString() + "원");

        for (int i =0 ;i<MenuCount.size() ; i++)
            CountSum += Integer.parseInt(MenuCount.get(i));
        tvAllCount_Cart.setText(String.valueOf(CountSum));

        imgBack_Cart = (ImageView) findViewById(R.id.imgBack_Cart);

        listSelect_Cart = (ListView) findViewById(R.id.listSelect_Cart);
        adapter = new MenuSelectedAdapter();
        adapter.readContact();
        listSelect_Cart.setAdapter(adapter);

        setListViewHeightBasedOnChildren(listSelect_Cart, 0);

        imgBack_Cart.setOnClickListener(Click);
    }

    class MenuSelectedAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return SelectedMenus.size();
        }

        @Override
        public Object getItem(int position) {
            return SelectedMenus.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MenuSelectedView view = new MenuSelectedView(getApplicationContext());

            MenuSelectedItem item = SelectedMenus.get(position);
            view.setTvMenu(item.getMenu());
            view.setTvPrice(item.getPrice());
            view.setTvCount_Cart(item.getCount());
            return view;
        }

        public void addSelectedMenu(MenuSelectedItem view){
            SelectedMenus.add(view);
        }

        public void readContact() {
            for (int i=0; i<MenuName.size() ; i++) {
                addSelectedMenu(new MenuSelectedItem(MenuName.get(i), MenuPrice.get(i), MenuCount.get(i)));
            }
        }

        public void DeleteMenu(int position) {
            SelectedMenus.remove(position);
        }
    }

    View.OnClickListener Click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.imgBack_Cart:
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    startActivity(intent);
            }
        }
    };

    public void SumPrice() {
        int Number = 0;
        for (int i =0; i<MenuPrice.size(); i++) {
            Number = Integer.parseInt(MenuCount.get(i));
            char Temp[] = MenuPrice.get(i).toCharArray();
            String a = "";
            for (int j=0 ; j<Temp.length ; j++) {
                if (Temp[j] != ',') {
                    a += Temp[j];
                }
            }
            nMenuPrice.add(a);
            AllPrice += Integer.parseInt(nMenuPrice.get(i)) * Number;
        }

        String a = String.valueOf(AllPrice);
        char Temp[] = a.toCharArray();
        int count = -1;
        strAllPrice = "";
        for (int i=Temp.length-1; i>=0 ; i--) {
            count++;
            if (count == 3) {
                strAllPrice += ",";
                count=-1;
                i += 1;
            }
            else {
                strAllPrice += Temp[i];
            }
        }
    }

    public void setListViewHeightBasedOnChildren(ListView listView, int c) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            if (c == 0)
                totalHeight += listItem.getMeasuredHeight() - 305;
            else
                totalHeight += listItem.getMeasuredHeight() - 12;
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    class MenuSelectedView extends LinearLayout {

        TextView tvSelectedMenu;
        TextView tvSelectedPrice;
        TextView tvCount_Cart;
        TextView tvDelete;

        public MenuSelectedView(Context context) {
            super(context);

            init(context);
        }

        public MenuSelectedView(Context context, AttributeSet attrs) {
            super(context, attrs);

            init(context);
        }

        public void init(Context context) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.menuselect,this,true);

            tvSelectedMenu = (TextView) findViewById(R.id.tvSelectedMenu);
            tvSelectedPrice = (TextView) findViewById(R.id.tvSelectedPrice);
            tvCount_Cart = (TextView) findViewById(R.id.tvCount_Cart);
            tvDelete = (TextView) findViewById(R.id.tvDelete);

            tvDelete.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    int Count =0;
                    for (int i =0; i<MenuName.size() ; i++) {
                        if (MenuName.get(i) == tvSelectedMenu.getText().toString()) {
                            Count = Integer.parseInt(MenuCount.get(i));
                            Count--;
                            CountSum--;
                            MenuCount.set(i, String.valueOf(Count));
                            tvCount_Cart.setText(String.valueOf(Count));
                            tvAllCount_Cart.setText(String.valueOf(CountSum));
                            SumPrice();
                            tvAllPrice_Cart.setText(new StringBuffer(strAllPrice).reverse().toString() + "원");
                            SelectedMenus.set(i, new MenuSelectedItem(tvSelectedMenu.getText().toString(), tvSelectedPrice.getText().toString(), tvCount_Cart.getText().toString()));

                            if (Count == 0) {
                                MenuName.remove(i);
                                MenuPrice.remove(i);
                                MenuCount.remove(i);
                                SelectedMenus.remove(i);
                                setListViewHeightBasedOnChildren(listSelect_Cart, 1);
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            });
        }

        public void setTvMenu(String menu) {
            tvSelectedMenu.setText(menu);
        }

        public void setTvPrice(String price) {
            tvSelectedPrice.setText(price);
        }

        public void setTvCount_Cart(String count) {
            tvCount_Cart.setText(count);
        }
    }

}
