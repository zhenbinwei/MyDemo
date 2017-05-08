package com.example.weizhenbin.mydemo.ui;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.weizhenbin.mydemo.base.BaseActivity;
import com.example.weizhenbin.mydemo.common.ApplicationManage;
import com.weizhenbin.show.R;


public class MainActivity extends BaseActivity {


    private WelfareFragment welfareFragment;
    private NewFragment newFragment;
    private MeFragment meFragment;
    private DemosFragment demosFragment;
    private long exitTime = 0;
    private final static int ADD = 0;
    private final static int SHOW = 1;
    private final static int HIDE = 2;
    private FrameLayout content;
    BottomNavigationView navigationView;
    private void assignViews() {
        content = (FrameLayout) findViewById(R.id.content);
        navigationView= (BottomNavigationView) findViewById(R.id.bng_bottom_bar);
    }

    @Override
    protected void initView() {
        /*if(!this.isTaskRoot()) { //判断该Activity是不是任务空间的源Activity，“非”也就是说是被系统重新实例化出来
            //如果你就放在launcher Activity中话，这里可以直接return了
            Intent mainIntent=getIntent();
            String action=mainIntent.getAction();
            if(mainIntent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)) {
                finish();
                return;//finishActivity()之后该活动会继续执行后面的代码，你可以logCat验证，加return避免可能的exception
            }
        }*/
        setContentView(R.layout.activity_main);
        assignViews();
        selectTab(0);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initEvent() {
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.d("MainActivity", "item.getTitle():" + item.getTitle());
                switch (item.getItemId()){
                    case R.id.item_char:
                        selectTab(0);
                        break;
                    case R.id.item_main:
                        selectTab(1);
                        break;
                    case R.id.item_demos:
                        selectTab(2);
                        break;
                    case R.id.item_me:
                        selectTab(3);
                        break;
                }
                return true;
            }
        });
    }


    private void selectTab(int index) {
        hideAllFragment();
        switch (index) {
            case 0:
                if (newFragment == null) {
                    newFragment = new NewFragment();
                    fragmentOperation(newFragment, ADD);
                    fragmentOperation(newFragment, SHOW);
                } else {
                    fragmentOperation(newFragment, SHOW);
                }
                break;

            case 1:
                if (welfareFragment == null) {
                    welfareFragment = new WelfareFragment();
                    fragmentOperation(welfareFragment, ADD);
                    fragmentOperation(welfareFragment, SHOW);
                } else {
                    fragmentOperation(welfareFragment, SHOW);
                }
                break;
            case 2:
                if (demosFragment == null) {
                    demosFragment = new DemosFragment();
                    fragmentOperation(demosFragment, ADD);
                    fragmentOperation(demosFragment, SHOW);
                } else {
                    fragmentOperation(demosFragment, SHOW);
                }
                break;
            case 3:
                if (meFragment == null) {
                    meFragment = new MeFragment();
                    fragmentOperation(meFragment, ADD);
                    fragmentOperation(meFragment, SHOW);
                } else {
                    fragmentOperation(meFragment, SHOW);
                }
                break;

        }
    }

    @Override
    public void onBackPressed() {
        //确认退出程序
        if (exitTime == 0) {
            exitTime = System.currentTimeMillis();
            Toast.makeText(this, "双击退出", Toast.LENGTH_SHORT).show();
        } else if (System.currentTimeMillis() - exitTime < 1000) {
            //退出程序
            ApplicationManage.getInstance().exitApp();
        } else {
            exitTime = 0;
        }
    }

    private void fragmentOperation(Fragment fragment, int operationType) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (fragment != null) {
            switch (operationType) {
                case ADD:
                    transaction.add(R.id.content, fragment);
                    break;
                case HIDE:
                    transaction.hide(fragment);
                    break;
                case SHOW:
                    transaction.show(fragment);
                    break;
            }

        }
        transaction.commit();
    }

    private void hideAllFragment() {
            fragmentOperation(newFragment, HIDE);
            fragmentOperation(welfareFragment, HIDE);
            fragmentOperation(meFragment, HIDE);
            fragmentOperation(demosFragment, HIDE);
    }


}
