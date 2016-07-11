package net.weirun.moduleproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;

import net.weirun.gallerylibrary.widget.GalleryPagerView;
import net.weirun.moduleproject.utils.TimerUtil;
import net.weirun.moduleproject.version.VersionUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<String> galleryPaths = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        galleryPaths.add("http://img5.imgtn.bdimg.com/it/u=2340349138,2028497687&fm=21&gp=0.jpg");
        galleryPaths.add("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1467865811&di=8ebb2dfecfcdd5edae59b037eee3601e&src=http://g.hiphotos.baidu.com/zhidao/pic/item/4034970a304e251f3ba0d299a186c9177f3e537f.jpg");
        galleryPaths.add("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1467865811&di=55eb478c7703ed9821aad8c66afd3cb2&src=http://e.hiphotos.baidu.com/zhidao/pic/item/b219ebc4b74543a9ee2c5fc11c178a82b9011483.jpg");
        galleryPaths.add("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1467865811&di=8ebb2dfecfcdd5edae59b037eee3601e&src=http://g.hiphotos.baidu.com/zhidao/pic/item/4034970a304e251f3ba0d299a186c9177f3e537f.jpg");
        galleryPaths.add("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1467865811&di=55eb478c7703ed9821aad8c66afd3cb2&src=http://e.hiphotos.baidu.com/zhidao/pic/item/b219ebc4b74543a9ee2c5fc11c178a82b9011483.jpg");
        galleryPaths.add("http://img3.imgtn.bdimg.com/it/u=332767035,3916081752&fm=21&gp=0.jpg");
        GalleryPagerView pagerView = (GalleryPagerView) findViewById(R.id.nav_gallery);
        pagerView.setGallery(galleryPaths);

        VersionUtils versionUtils = new VersionUtils();
        versionUtils.checkVersion(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        TextView textView = (TextView) findViewById(R.id.code);
        TimerUtil util = new TimerUtil();
        util.setListener(new TimerUtil.OnTimeOutListener() {
            @Override
            public void onTimeOut(int time, boolean isStop) {

            }
        });
        util.startTimer(60);
        util.startTimer(60, textView, "获取验证码", "秒后重新获取");

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

    public void clickBT(View view) {
        Intent intent = new Intent();
        intent.setClass(this, Main2Activity.class);
        startActivity(intent);
    }


}
