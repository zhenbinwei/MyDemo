package com.example.weizhenbin.mydemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.weizhenbin.mydemo.base.BaseActivity;
import com.example.weizhenbin.mydemo.widget.CommonToolbar;
import com.weizhenbin.show.R;

import java.util.HashMap;


public class MainActivity extends BaseActivity {
    private static int TYPE_NEWS=1;
    private static int TYPE_GANHUO=0;
    private static int TYPE_MUSIC=2;
    NavigationView nvMenu;
    DrawerLayout dlContent;
    Toolbar toolbar;
    FrameLayout flContent;
    FragmentManager fragmentManager;
    HashMap<Integer,Fragment> currentfragmentHashMap;
    MenuItem menuItem;
    Fragment currentFragment;
    HashMap<Integer,HashMap<Integer,Fragment>> allFragment=new HashMap<>();
    private int currentType=TYPE_GANHUO;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!this.isTaskRoot()) {
                //判断该Activity是不是任务空间的源Activity，“非”也就是说是被系统重新实例化出来 //如果你就放在launcher Activity中话，这里可以直接return了
                Intent mainIntent = getIntent();
                String action = mainIntent.getAction();
                if (mainIntent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)) {
                    finish();
                    return;//finish()之后该活动会继续执行后面的代码，你可以logCat验证，加return避免可能的exception
                }
            }
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        toolbar=CommonToolbar.getToolBar(this);
        toolbar.setTitle("干货");
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        nvMenu = (NavigationView) findViewById(R.id.nv_menu);
        dlContent = (DrawerLayout) findViewById(R.id.dl_content);
        flContent= (FrameLayout) findViewById(R.id.fl_content);
        setSupportActionBar(toolbar);
        fragmentManager=getSupportFragmentManager();
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
                switch (id){
                    case R.id.ganhuo:
                        if(currentType==TYPE_GANHUO){
                            break;
                        }
                        currentType=TYPE_GANHUO;
                        invalidateOptionsMenu();
                        item.setChecked(true);
                        toolbar.setTitle(item.getTitle());
                        break;
                    case R.id.news:
                        if(currentType==TYPE_NEWS){
                            break;
                        }
                        currentType=TYPE_NEWS;
                        invalidateOptionsMenu();
                        item.setChecked(true);
                        toolbar.setTitle(item.getTitle());
                        break;
                    case R.id.music:
                        break;
                }
                return false;
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(menuItem==item){
                    return false;
                }
                if(menuItem!=null){
                    menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
                }
                 item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                 menuItem=item;
                if(!currentfragmentHashMap.containsKey(item.getItemId())){
                    if(currentType==TYPE_GANHUO) {
                        currentfragmentHashMap.put(item.getItemId(), GanhuoFragment.createFragment(item.getTitle().toString()));
                    }else if(currentType==TYPE_NEWS) {
                        currentfragmentHashMap.put(item.getItemId(), NewsFragment.createFragment(item.getItemId()));
                    }
                }
                Fragment fragment=currentfragmentHashMap.get(item.getItemId());
                if(fragment!=null){
                    if(fragment==currentFragment){
                       return false;
                    }else if (currentFragment!=null&&currentFragment.isVisible()){
                        fragmentManager.beginTransaction().hide(currentFragment).commit();
                    }
                    if(!fragment.isAdded()){
                        fragmentManager.beginTransaction().add(R.id.fl_content,fragment).commit();
                    }else if(fragment.isHidden()){
                        fragmentManager.beginTransaction().show(fragment).commit();
                    }
                }else {
                    fragment= GanhuoFragment.createFragment(item.getTitle().toString());
                    if(currentFragment!=null&&currentFragment.isVisible()){
                        fragmentManager.beginTransaction().hide(fragment).commit();
                    }
                    fragmentManager.beginTransaction().add(R.id.fl_content,fragment).commit();
                }
                currentFragment=fragment;
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (currentType==TYPE_GANHUO){
            getMenuInflater().inflate(R.menu.ganhuo_menu, menu);
        }else if(currentType==TYPE_NEWS){
            getMenuInflater().inflate(R.menu.news_menu, menu);
        }
        currentfragmentHashMap=getFragmentHashMapByType(currentType);
          if(toolbar.getMenu()!=null&&toolbar.getMenu().size()>0){
              menuItem = toolbar.getMenu().getItem(0);
          }
          if(currentFragment!=null&&currentFragment.isVisible()) {
              fragmentManager.beginTransaction().hide(currentFragment).commit();
          }
        if (currentType==TYPE_GANHUO){
            currentFragment= GanhuoFragment.createFragment(menuItem.getTitle().toString());
        }else if(currentType==TYPE_NEWS){
            currentFragment=NewsFragment.createFragment(menuItem.getItemId());
        }
       if (currentFragment!=null&&!currentFragment.isAdded()) {
           currentfragmentHashMap.put(menuItem.getItemId(), currentFragment);
           fragmentManager.beginTransaction().add(R.id.fl_content, currentFragment).commit();
       }
        return true;
    }



    private HashMap<Integer,Fragment> getFragmentHashMapByType(int type){
        HashMap<Integer,Fragment> fragmentHashMap;
        if(allFragment!=null){
            fragmentHashMap=allFragment.get(type);
            if(fragmentHashMap!=null){
                return fragmentHashMap;
            }else {
                fragmentHashMap=new HashMap<>();
                return fragmentHashMap;
            }
        }else {
            allFragment=new HashMap<>();
            fragmentHashMap=new HashMap<>();
            allFragment.put(type,fragmentHashMap);
            return fragmentHashMap;
        }
    }
}
