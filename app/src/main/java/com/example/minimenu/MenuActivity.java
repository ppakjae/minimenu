package com.example.minimenu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
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

public class MenuActivity extends AppCompatActivity {

    ImageView imgBack;

    LinearLayout linCart;

    TextView tvStore;
    TextView tvPhoneNumber;
    TextView tvTime;
    TextView tvPosition;

    ListView listMenu;

    MenuAdapter adapter;

    ArrayList<MenuItem> Menus = new ArrayList<MenuItem>();

    int ReceiveActivity =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        imgBack = (ImageView) findViewById(R.id.imgBack);
        linCart = (LinearLayout) findViewById(R.id.linCart);

        tvStore = (TextView) findViewById(R.id.tvStore);
        tvPhoneNumber = (TextView) findViewById(R.id.tvPhoneNumber);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvPosition = (TextView) findViewById(R.id.tvPosition);

        listMenu = (ListView) findViewById(R.id.listMenu);
        adapter = new MenuAdapter();
        adapter.readContact();
        listMenu.setAdapter(adapter);

        setListViewHeightBasedOnChildren(listMenu);

        Intent intent = new Intent(this.getIntent());
        String Check = "";
        Check = intent.getStringExtra("Check");
        if (Check != null) {
            Log.d("aaa", "Check = NotNull");
            MoreCart();
        }
        else {
            Log.d("aaa", "Check = NULL");
        }

        imgBack.setOnClickListener(Click);
        linCart.setOnClickListener(Click);
    }

    private void MoreCart() {

        ArrayList<String> MenuName = new ArrayList<>();
        ArrayList<String> MenuPrice = new ArrayList<>();
        ArrayList<String> MenuCount = new ArrayList<>();

        Intent intent = new Intent(this.getIntent());

        MenuName = intent.getStringArrayListExtra("MenuName");
        MenuPrice = intent.getStringArrayListExtra("MenuPrice");
        MenuCount = intent.getStringArrayListExtra("MenuCount");

        for (int i =0; i<adapter.getCount() ; i++) {
            for (int j=0 ; j<MenuName.size() ; j++) {
                if (MenuName.get(j).equals(Menus.get(i).getMenu())) {
                    Menus.set(i, new MenuItem(MenuName.get(j), MenuPrice.get(j), MenuCount.get(j)));
                    Log.d("Count", Menus.get(i).getCount());
                }
            }
        }
        ReceiveActivity = 1;
        adapter.notifyDataSetChanged();
    }

    class MenuAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return Menus.size();
        }

        @Override
        public Object getItem(int position) {
            return Menus.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MenuView view = new MenuView(getApplicationContext());

            MenuItem item = Menus.get(position);
            view.setTvMenu(item.getMenu());
            view.setTvPrice(item.getPrice());
            view.setTvCount(item.getCount());
            return view;
        }

        public void addMenu(MenuItem view){
            Menus.add(view);
        }

        public void readContact() {
            addMenu(new MenuItem("aaa","20,000", "0"));
            addMenu(new MenuItem("ccc","50,000", "0"));
            addMenu(new MenuItem("bbb", "70,000", "0"));
            addMenu(new MenuItem("bbb", "70,000", "0"));
        }
    }

    View.OnClickListener Click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.imgBack:
                    break;

                case R.id.linCart:
                    ArrayList<String> MenuName = new ArrayList<>();
                    ArrayList<String> MenuPrice = new ArrayList<>();
                    ArrayList<String> MenuCount = new ArrayList<>();

                    for (int i =0; i<adapter.getCount() ; i++) {
                        if (Menus.get(i).getCount() != "0") {
                            MenuName.add(Menus.get(i).getMenu());
                            MenuPrice.add(Menus.get(i).getPrice());
                            MenuCount.add(Menus.get(i).getCount());
                        }
                    }

                    Intent intent = new Intent(getApplicationContext(), MenuCartActivity.class);

                    intent.putStringArrayListExtra("MenuName", MenuName);
                    intent.putStringArrayListExtra("MenuPrice", MenuPrice);
                    intent.putStringArrayListExtra("MenuCount", MenuCount);

                    startActivity(intent);
                    break;
            }
        }
    };

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < listAdapter.getCount() ; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight() - 275;
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    class MenuView extends LinearLayout {

        TextView tvMenu;
        TextView tvPrice;
        TextView tvCount;

        ImageView imgAdd;
        ImageView imgSub;

        int Count;
        String ViewMenuName;
        String ViewMenuPrice;

        public MenuView(Context context) {
            super(context);

            init(context);
        }

        public MenuView(Context context, AttributeSet attrs) {
            super(context, attrs);

            init(context);
        }

        public void init(Context context) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.menuitem,this,true);

            Count = 0;

            imgAdd = (ImageView) findViewById(R.id.imgAdd);
            imgSub = (ImageView) findViewById(R.id.imgSub);
            tvCount = (TextView) findViewById(R.id.tvCount);

            if (ReceiveActivity == 1) {
                for (int i = 0; i < adapter.getCount(); i++) {
                    MenuItem item = (MenuItem) adapter.getItem(i);
                    int count = Integer.parseInt(item.getCount());
                    Log.d("menu의 개수 인덱스", String.valueOf(i));
                    if (count != 0) {
                        imgAdd.setImageResource(R.drawable.plus);
                        imgSub.setImageResource(R.drawable.minus);
                        tvCount.setTextColor(Color.parseColor("#FF4B00"));
                    }
                }
                ReceiveActivity = 0;
            }

            imgAdd.setOnClickListener(Click);
            imgSub.setOnClickListener(Click);

            tvMenu = findViewById(R.id.tvMenu);
            tvPrice = findViewById(R.id.tvPrice);
        }

        public void setTvMenu(String menu) {
            tvMenu.setText(menu);
        }

        public void setTvPrice(String price) {
            tvPrice.setText(price);
        }

        public void setTvCount(String count) {
            tvCount.setText(count);
        }

        OnClickListener Click = new OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId()) {
                    case R.id.imgAdd:
                        Count++;
                        ViewMenuName = tvMenu.getText().toString();
                        ViewMenuPrice = tvPrice.getText().toString();
                        tvCount.setText(String.valueOf(Count));
                        tvCount.setTextColor(Color.parseColor("#FF4B00"));
                        imgAdd.setImageResource(R.drawable.plus);
                        imgSub.setImageResource(R.drawable.minus);
                        break;

                    case R.id.imgSub:
                        Count--;
                        ViewMenuName = tvMenu.getText().toString();
                        ViewMenuPrice = tvPrice.getText().toString();
                        tvCount.setText(String.valueOf(Count));
                        if (Count == 0) {
                            tvCount.setTextColor(Color.parseColor("#E3E3E3"));
                            imgSub.setImageResource(R.drawable.minus_g);
                            imgAdd.setImageResource(R.drawable.plus_g);
                        }
                        else if (Count <0) {
                            tvCount.setText("0");
                            Count =0;
                        }
                        break;
                }
                getCount();
            }
        };

        public void getCount() {
            for (int i =0; i<adapter.getCount() ; i++) {
                if (Menus.get(i).getMenu() == ViewMenuName) {
                    Menus.set(i, new MenuItem(ViewMenuName, ViewMenuPrice, String.valueOf(Count)));
                }
            }
        }
    }
}
