package com.example.a27043.myapplication.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.a27043.myapplication.R;
import com.example.a27043.myapplication.entity.Food;
import com.example.a27043.myapplication.entity.FoodType;
import com.example.a27043.myapplication.entity.Order;
import com.example.a27043.myapplication.entity.OrderDetail;
import com.example.a27043.myapplication.entity.Table;
import com.example.a27043.myapplication.entity.User;
import com.example.a27043.myapplication.service.App;
import com.example.a27043.myapplication.service.FoodService;
import com.example.a27043.myapplication.service.OrderService;
import com.example.a27043.myapplication.service.TableService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 27043 on 2016-05-12.
 */
public class OrderActivity extends BasicActivity {

    Spinner tableSpn;
    EditText customersEdt;
    EditText descriptionEdt;
    TextView sumTxv;
    Button addFoodBtn;
    Button orderBtn;
    Button cancelBtn;
    ListView orderedLtv;
    List<Map<String, Object>>orderedList = new ArrayList<Map<String, Object>>();
    String[] orderedLtvKeys = new String[] {"no", "name", "description", "num", "price"};
    int[] orderedLtvIds = new int[] {R.id.noTxv, R.id.nameTxv, R.id.descriptionTxv, R.id.numTxv, R.id.priceTxv};

    Spinner foodSpn;
    EditText numEdt;
    EditText foodDescriptionTxt;

    TableService tableService;
    FoodService foodService;
    OrderService orderService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);
        tableService = new TableService();
        foodService = new FoodService();
        orderService = new OrderService();

        addFoodBtn = (Button) findViewById(R.id.addFoodBtn);
        orderBtn = (Button) findViewById(R.id.orderBtn);
        cancelBtn = (Button) findViewById(R.id.cancelBtn1);
        customersEdt = (EditText) findViewById(R.id.customersEdt);
        descriptionEdt = (EditText) findViewById(R.id.descriptionEdt);
        sumTxv = (TextView) findViewById(R.id.sumTxv);
        tableSpn = (Spinner) findViewById(R.id.tableSpn);
        orderedLtv = (ListView) findViewById(R.id.orderLtv);

        initTableSpn();
        initOrderedLtv();

        LayoutInflater li = LayoutInflater.from(this);
        View foodView = li.inflate(R.layout.food, null);
        foodSpn = (Spinner) foodView.findViewById(R.id.foodSpn);
        numEdt = (EditText) foodView.findViewById(R.id.numEdt);
        foodDescriptionTxt = (EditText) foodView.findViewById(R.id.descriptionEdt1);
        GridView foodTypeGdv = (GridView) foodView.findViewById(R.id.foodTypeGdv);
        initFoodTypeGv(foodTypeGdv);

        numEdt.setText("1");
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("选择酒菜");
        dialogBuilder.setIcon(R.drawable.coffee);
        dialogBuilder.setView(foodView);
        dialogBuilder.setCancelable(true);
        dialogBuilder.setPositiveButton("确定",
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Food food = (Food) foodSpn.getSelectedItem();
                        int num = 1;
                        String n = numEdt.getText().toString();
                        if(n.length() > 0)
                            num = Integer.parseInt(n);
                        String description = foodDescriptionTxt.getText().toString();
                        Map<String, Object>line = new HashMap<String, Object>();
                        line.put("foodId",food.id);
                        line.put("no", orderedList.size() + 1);
                        line.put("name", food.name);
                        line.put("description", description);
                        line.put("num", num);
                        line.put("price", food.price);
                        line.put("checked", true);
                        orderedList.add(line);
                        ((SimpleAdapter) orderedLtv.getAdapter()).notifyDataSetChanged();
                        orderedLtv.setVisibility(View.VISIBLE);
                        refreshSum(num*food.price);
                        foodDescriptionTxt.setText(null);
                    }
                });
        dialogBuilder.setNegativeButton("取消", null);

        final AlertDialog dialog = dialogBuilder.create();
        addFoodBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                numEdt.setText("1");
                dialog.show();
            }
        });

        orderBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int customers = 0;
                String c = customersEdt.getText().toString();
                if(c.length()>0)
                    customers = Integer.parseInt(c);
                if(customers <= 0) {
                    showMessageDialog("请输入顾客数量", R.drawable.warning, null);
                    return;
                }
                int sum = Integer.parseInt(sumTxv.getText().toString());
                if(sum < 0){
                    showMessageDialog("尚未选择任何餐品", R.drawable.warning, null);
                    return;
                }

                Table table = (Table) tableSpn.getSelectedItem();

                Order order = new Order();
                User waiter = ((App)getApplication()).user;
                order.tableId = table.id;
                order.waiterId = waiter.id;
                order.customers = Integer.parseInt(customersEdt.getText().toString());
                order.description = descriptionEdt.getText().toString();
                order.orderDateils = new ArrayList<OrderDetail>();

                for (Map<String, Object> line : orderedList) {
                    boolean checked = (Boolean) line.get("checked");
                    if(!checked) continue;
                    OrderDetail od = new OrderDetail();
                    od.foodId = (Integer) line.get("foodId");
                    od.num = (Integer) line.get("num");
                    od.description = (String) line.get("description");
                    order.orderDateils.add(od);
                }

                orderService.addOrder(order);
                finish();
            }
        });

        cancelBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderActivity.this.finish();
            }
        });
    }

    void initTableSpn() {
        List<Table> tables = tableService.getTables();
        tableSpn.setAdapter(new ArrayAdapter<Table>(this, android.R.layout.simple_spinner_item, tables));
    }

    void initFoodSpn(int foodTypeId) {
        List<Food> foods = foodService.getFoods(foodTypeId);
        foodSpn.setAdapter(new ArrayAdapter<Food>(this, android.R.layout.simple_spinner_item, foods));
    }

    void initOrderedLtv() {
        SimpleAdapter sa = new SimpleAdapter(this, orderedList,
                R.layout.ordered, orderedLtvKeys, orderedLtvIds) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final int lineNo = position;
                View view = super.getView(position, convertView, parent);
                CheckBox checkCkb = (CheckBox) view.findViewById(R.id.checkCkb);
                checkCkb.setOnCheckedChangeListener(
                        new CheckBox.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                @SuppressWarnings("unchecked")
                                Map<String, Object> line
                                    = (Map<String,Object>) getItem(lineNo);
                                int num = (Integer) line.get("num");
                                int price = (Integer) line.get("price");
                                if(isChecked)
                                    refreshSum(num * price);
                                else
                                    refreshSum(-num * price);
                                line.put("checked", isChecked);
                                }
                            });
                        return view;
                }
            };
        orderedLtv.setAdapter(sa);
    }

    void initFoodTypeGv(final GridView foodTypeGdv) {
        final List<FoodType> foodTypes = foodService.getFoodTypes();
        final List<RadioButton> rbs = new ArrayList<RadioButton>();

        for (FoodType ft : foodTypes) {
            RadioButton rb = new RadioButton(OrderActivity.this);
            rb.setText(ft.name);
            rb.setTag(ft.id);
            rb.setOnCheckedChangeListener(
                    new RadioButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(!isChecked)
                                return;
                            for (RadioButton b : rbs) {
                                if (b != buttonView)
                                    b.setChecked(false);
                            }
                            int foodTypeId = Integer.parseInt(buttonView.getTag().toString());
                            initFoodSpn(foodTypeId);
                        }
                    });
            rbs.add(rb);
        }

        rbs.get(0).setChecked(true);
        initFoodSpn(0);

        foodTypeGdv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return foodTypes.size();
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
                return rbs.get(position);
            }
        });
    }

    void refreshSum(int newAmount) {
        int sum = Integer.parseInt(sumTxv.getText().toString());
        sum += newAmount;
        sumTxv.setText(sum + "");
    }

    @Override
    protected void showActivity(Context context, Class<? extends Context> contextClass) {
        super.showActivity(context, contextClass);
    }


    @Override
    protected String getName() {
        return "点餐";
    }
}
