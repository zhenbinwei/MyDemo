package com.example.weizhenbin.mydemo.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.weizhenbin.mydemo.base.BaseActivity;
import com.example.weizhenbin.mydemo.presenter.MusicService;
import com.example.weizhenbin.mydemo.presenter.MusicServiceControl;
import com.example.weizhenbin.mydemo.widget.CommonToolbar;
import com.weizhenbin.show.R;

import static com.example.weizhenbin.mydemo.presenter.MusicServiceControl.getServiceControl;


public class MainActivity extends BaseActivity implements MusicServiceControl.OnMusicChangeListener{
    private static int TYPE_NEWS=1;
    private static int TYPE_GANHUO=0;
    private static int TYPE_MUSIC=2;
    NavigationView nvMenu;
    DrawerLayout dlContent;
    Toolbar toolbar;
    FrameLayout flContent;
    FragmentManager fragmentManager;
    SparseArray<Fragment> currentfragmentHashMap;
    MenuItem menuItem;
    Fragment currentFragment;
    SparseArray<SparseArray<Fragment>> allFragment=new SparseArray<>();
    private int currentType=TYPE_GANHUO;
    private View nvHeadView;
    private TextView tvSongname;
    private ImageView ivPrevious,ivPlay,ivNext;
    LinearLayout llMusic;
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
        nvHeadView=nvMenu.getHeaderView(0);
        ivPrevious= (ImageView) nvHeadView.findViewById(R.id.iv_previous);
        ivPlay= (ImageView) nvHeadView.findViewById(R.id.iv_play);
        ivNext= (ImageView) nvHeadView.findViewById(R.id.iv_next);
        tvSongname= (TextView) nvHeadView.findViewById(R.id.tv_songname);
        llMusic= (LinearLayout) nvHeadView.findViewById(R.id.ll_music);
        MusicServiceControl.getServiceControl().addListener(this);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            dlContent.setFitsSystemWindows(true);
            dlContent.setClipToPadding(false);
        }
    }

    @Override
    protected void initData() {

    }

    private TranslateAnimation moveToViewLocation() {
        TranslateAnimation transformation=new TranslateAnimation( Animation.RELATIVE_TO_SELF,-1,Animation.RELATIVE_TO_SELF,0,
                Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0);
        transformation.setDuration(500);
        return transformation;
    }
    @Override
    protected void initEvent() {
        dlContent.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                if(getServiceControl().getMusicInfo()!=null) {
                    if(llMusic.getVisibility()==View.GONE){
                        llMusic.startAnimation(moveToViewLocation());
                        llMusic.setVisibility(View.VISIBLE);
                    }
                }else {
                    llMusic.setVisibility(View.GONE);
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerStateChanged( int newState) {

            }
        });
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
                        if(currentType==TYPE_MUSIC){
                            break;
                        }
                        currentType=TYPE_MUSIC;
                        invalidateOptionsMenu();
                        item.setChecked(true);
                        toolbar.setTitle(item.getTitle());
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
                if(currentfragmentHashMap.get(item.getItemId())==null){
                    if(currentType==TYPE_GANHUO) {
                        currentfragmentHashMap.put(item.getItemId(), GanhuoFragment.createFragment(item.getTitle().toString()));
                    }else if(currentType==TYPE_NEWS) {
                        currentfragmentHashMap.put(item.getItemId(), NewsFragment.createFragment(item.getTitle().toString()));
                    }else if(currentType==TYPE_MUSIC){
                        currentfragmentHashMap.put(item.getItemId(), MusicFragment.createFragment(item.getTitle().toString()));
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
                }
                currentFragment=fragment;
                return false;
            }
        });
        ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MusicServiceControl.getServiceControl().getStatus()
                        == MusicService.STATUS_ISPLAYING){
                    MusicServiceControl.getServiceControl().pause();
                    ivPlay.setImageResource(R.drawable.ic_play_arrow_white_36dp);
                }else {
                    MusicServiceControl.getServiceControl().reStart();
                    ivPlay.setImageResource(R.drawable.ic_pause_white_36dp);
                }
            }
        });
        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicServiceControl.getServiceControl().next();
                if(MusicServiceControl.getServiceControl().getMusicInfo()!=null) {
                    tvSongname.setText(MusicServiceControl.getServiceControl().getMusicInfo().getSongname());
                }

            }
        });
        ivPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicServiceControl.getServiceControl().previous();
                if(MusicServiceControl.getServiceControl().getMusicInfo()!=null) {
                    tvSongname.setText(MusicServiceControl.getServiceControl().getMusicInfo().getSongname());
                }
            }
        });
        tvSongname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicDetailActivity.startActivity(MainActivity.this);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (currentType==TYPE_GANHUO){
            getMenuInflater().inflate(R.menu.ganhuo_menu, menu);
        }else if(currentType==TYPE_NEWS){
            getMenuInflater().inflate(R.menu.news_menu, menu);
        }else if(currentType==TYPE_MUSIC){
            getMenuInflater().inflate(R.menu.music_menu, menu);
        }
        currentfragmentHashMap=getFragmentHashMapByType(currentType);
          if(toolbar.getMenu()!=null&&toolbar.getMenu().size()>0){
              menuItem = toolbar.getMenu().getItem(0);
          }
          if(currentFragment!=null&&currentFragment.isVisible()) {
              fragmentManager.beginTransaction().hide(currentFragment).commit();
          }
        currentFragment=currentfragmentHashMap.get(menuItem.getItemId());
        if(currentFragment==null) {
            if (currentType == TYPE_GANHUO) {
                currentFragment = GanhuoFragment.createFragment(menuItem.getTitle().toString());
            } else if (currentType == TYPE_NEWS) {
                currentFragment = NewsFragment.createFragment(menuItem.getTitle().toString());
            }else if(currentType==TYPE_MUSIC){
                currentFragment=MusicFragment.createFragment(menuItem.getTitle().toString());
            }
        }
       if (currentFragment!=null) {
           if(!currentFragment.isAdded()) {
               currentfragmentHashMap.put(menuItem.getItemId(), currentFragment);
               fragmentManager.beginTransaction().add(R.id.fl_content, currentFragment).commit();
           }else {
               fragmentManager.beginTransaction().show(currentFragment).commit();
           }
       }
        return true;
    }



    private SparseArray<Fragment> getFragmentHashMapByType(int type){
        SparseArray<Fragment> fragmentHashMap;
        if(allFragment!=null){
            fragmentHashMap=allFragment.get(type);
            if(fragmentHashMap!=null){
                return fragmentHashMap;
            }else {
                fragmentHashMap=new SparseArray<>();
                allFragment.put(type,fragmentHashMap);
                return fragmentHashMap;
            }
        }else {
            allFragment=new SparseArray<>();
            fragmentHashMap=new SparseArray<>();
            allFragment.put(type,fragmentHashMap);
            return fragmentHashMap;
        }
    }

    @Override
    public void onMusicChange() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(getServiceControl().getMusicInfo()!=null) {
                    if(llMusic.getVisibility()==View.GONE){
                        llMusic.startAnimation(moveToViewLocation());
                        llMusic.setVisibility(View.VISIBLE);
                    }
                    tvSongname.setText( MusicServiceControl.getServiceControl().getMusicInfo().getSongname());
                    if(MusicServiceControl.getServiceControl().getStatus()==MusicService.STATUS_ISPLAYING){
                        ivPlay.setImageResource(R.drawable.ic_pause_white_36dp);
                    }else {
                        ivPlay.setImageResource(R.drawable.ic_play_arrow_white_36dp);
                    }
                }else {
                    llMusic.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MusicServiceControl.getServiceControl().removeListener(this);
    }
}
