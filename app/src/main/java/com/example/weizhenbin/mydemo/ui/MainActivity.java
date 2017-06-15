package com.example.weizhenbin.mydemo.ui;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.weizhenbin.mydemo.base.BaseActivity;
import com.example.weizhenbin.mydemo.widget.CommonToolbar;
import com.weizhenbin.show.R;


public class MainActivity extends BaseActivity {
    NavigationView nvMenu;
    DrawerLayout dlContent;
    Toolbar toolbar;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        toolbar=CommonToolbar.getToolBar(this);
        toolbar.setTitle("福利");
        toolbar.setNavigationIcon(R.drawable.ic_list_white_36dp);
        nvMenu = (NavigationView) findViewById(R.id.nv_menu);
        dlContent = (DrawerLayout) findViewById(R.id.dl_content);
        setSupportActionBar(toolbar);

    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlContent.openDrawer(Gravity.LEFT);
            }
        });
        nvMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                dlContent.closeDrawers();
                int id=item.getItemId();
                if(id==R.id.welfare||id==R.id.news||id==R.id.music){
                    item.setChecked(true);
                    toolbar.setTitle(item.getTitle());
                }

                return false;
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.d("MainActivity", "item.getTitle():" + item.getTitle());
                if(menuItem!=null){
                    menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
                }
                 item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                 menuItem=item;
                return false;
            }
        });
    }
    MenuItem menuItem;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ganhuo_menu,menu);
        menuItem=toolbar.getMenu().getItem(0);
        return true;
    }

}
