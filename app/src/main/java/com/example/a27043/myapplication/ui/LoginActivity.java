package com.example.a27043.myapplication.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.a27043.myapplication.R;
import com.example.a27043.myapplication.entity.User;
import com.example.a27043.myapplication.service.App;
import com.example.a27043.myapplication.service.UserService;

public class LoginActivity extends BasicActivity {
    EditText codeEdt;
    EditText passwordEdt;
    Button loginBtn;
    Button cancelBtn;
    Button phoneButton;
    Button webButton;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        String title = getString(R.string.app_name) + " " + getName();
        setTitle(title);

        userService = new UserService();

        codeEdt = (EditText) findViewById(R.id.codeEdt);
        passwordEdt = (EditText) findViewById(R.id.passwordEdt);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        webButton = (Button) findViewById(R.id.webBtn);
        phoneButton = (Button) findViewById(R.id.phoneBtn);

        loginBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v){
                String code = codeEdt.getText().toString();
                String password = passwordEdt.getText().toString();
                if(code.length() == 0 ||password.length() == 0){
                    showMessageDialog("请输入登录名和密码",R.drawable.warning,null);
                    return;
                }
                User user = userService.login(code, password);
                if(user == null){
                    showMessageDialog("登录名或密码错误",R.drawable.warning,null);
                    return;
                }
                showMessageDialog("登陆成功",R.drawable.info,null);
                App app = (App) getApplication();
                app.user = user;
                showActivity(LoginActivity.this, MainActivity.class);
            }
        });

        cancelBtn.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                LoginActivity.this.finish();
            }
        });
        phoneButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:17858528230"));
                startActivity(intent);
            }
        });
        webButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.baidu.com"));
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.add_item:
                Toast.makeText(this, "You clicked Add",Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                finish();
                break;
            default:
        }
        return true;
    }

    @Override
    protected String getName() {
        return "登录";
    }



}
