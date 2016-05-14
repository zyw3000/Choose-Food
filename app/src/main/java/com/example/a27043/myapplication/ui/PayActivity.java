package com.example.a27043.myapplication.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.a27043.myapplication.R;
import com.example.a27043.myapplication.service.OrderService;
import com.example.a27043.myapplication.service.OrderService.OrderDto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 27043 on 2016-05-14.
 */
public class PayActivity extends BasicActivity{

    static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    EditText orderCodeEdt;
    Button queryBtn;
    Button payBtn;
    Button cancelBtn;
    TextView sumTxv;
    ListView orderedLtv;

    TextView orderCodeTxv;
    TextView tableCodeTxv;
    TextView waiterCodeTxv;
    TextView orderTimeTxv;
    TextView customersTxv;
    TextView descriptionTxv;

    List<Map<String, Object>>orderedList = new ArrayList<Map<String, Object>>();
    String[] orderedLtvKeys = new String[] {"no", "name", "description", "num", "price"};
    int[] orderedLtvIds = new int[] {R.id.noTxv, R.id.nameTxv, R.id.descriptionTxv, R.id.nameTxv, R.id.priceTxv};

    int orderId;

    OrderService orderService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay);

        orderService = new OrderService();

        orderCodeEdt = (EditText) findViewById(R.id.orderCodeEdt);
        queryBtn = (Button) findViewById(R.id.queryBtn);
        payBtn = (Button) findViewById(R.id.payBtn);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        sumTxv = (TextView) findViewById(R.id.sumTxv);
        orderedLtv = (ListView) findViewById(R.id.orderedLtv);
        orderCodeTxv = (TextView) findViewById(R.id.orderCodeTxv);
        tableCodeTxv = (TextView) findViewById(R.id.tableCodeTxv);
        waiterCodeTxv = (TextView) findViewById(R.id.waiterCodeTxv);
        orderTimeTxv = (TextView) findViewById(R.id.orderTimeTxv);
        customersTxv = (TextView) findViewById(R.id.customersTxv);
        descriptionTxv = (TextView) findViewById(R.id.descriptionTxv);

        initOrderedLtv();

        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayActivity.this.orderId = 0;
                String orderCode = orderCodeEdt.getText().toString();
                clearDisplay();
                if(orderCode.length() == 0) {
                    showMessageDialog("请输入订单编号",
                            R.drawable.warning, null);
                    return;
                }
                OrderDto dto = null;
                dto = orderService.getOrder(orderCode);
                if(dto == null) {
                    showMessageDialog("未查找到订单： " + orderCode,
                            R.drawable.warning, null);
                    return;
                }
                orderCodeTxv.setText("订单编号： " + dto.order.code);
                tableCodeTxv.setText(" 餐桌ID： " + dto.order.tableId + "");
                waiterCodeTxv.setText("服务员ID： " + dto.order.waiterId + "");
                orderTimeTxv.setText("  时间： " + sdf.format(dto.order.orderTime));
                customersTxv.setText("顾客数量: " + dto.order.customers);
                descriptionTxv.setText(dto.order.description == null ? "" : (" 备注： " + dto.order.description));
                orderedList.addAll(dto.orderedList);
                SimpleAdapter sa = (SimpleAdapter) orderedLtv.getAdapter();
                sa.notifyDataSetChanged();
                orderedLtv.setVisibility(View.VISIBLE);
                if(dto.order.status == 1) {
                    sumTxv.setText("此订单已结算，合计： ¥ "+dto.sum);
                    PayActivity.this.orderId = -1;
                }else {
                    sumTxv.setText("合计： ¥ " + dto.sum);
                    PayActivity.this.orderId = dto.order.id;
                }
            }
        });

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderId == 0) {
                    showMessageDialog("请选择订单", R.drawable.warning, null);
                    return;
                }
                if(orderId == -1) {
                    showMessageDialog("此订单已结算", R.drawable.warning, null);
                    return;
                }
                orderService.pay(orderId);
                orderId = -1;
                showMessageDialog("结账完成", R.drawable.info,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayActivity.this.finish();
            }
        });
    }

    void clearDisplay() {
        orderCodeEdt.setText(null);
        orderCodeTxv.setText(null);
        tableCodeTxv.setText(null);
        waiterCodeTxv.setText(null);
        orderTimeTxv.setText(null);
        customersTxv.setText(null);
        descriptionTxv.setText(null);
        sumTxv.setText(null);

        orderedList.clear();
        SimpleAdapter sa = (SimpleAdapter) orderedLtv.getAdapter();
        sa.notifyDataSetChanged();
        orderedLtv.setVisibility(View.GONE);
    }

    void initOrderedLtv() {
        SimpleAdapter sa = new SimpleAdapter(this, orderedList,
                R.layout.ordered, orderedLtvKeys, orderedLtvIds) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                CheckBox checkCkb = (CheckBox) view.findViewById(R.id.checkCkb);
                checkCkb.setVisibility(View.GONE);
                return view;
            }
        };
        orderedLtv.setAdapter(sa);
    }

    @Override
    protected String getName() {
        return "结账";
    }
}
