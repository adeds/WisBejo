package adeyds.noes.wisbejo;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import adeyds.noes.wisbejo.fragment.AccountFragment;
import adeyds.noes.wisbejo.fragment.GaleriFragment;
import adeyds.noes.wisbejo.fragment.HomeFragment;
import adeyds.noes.wisbejo.fragment.InfoFragment;
import adeyds.noes.wisbejo.util.CustomTypefaceSpan;

import static adeyds.noes.wisbejo.util.AppVar.FROM_GALER;
import static adeyds.noes.wisbejo.util.AppVar.GALER_EXTRA;
import static adeyds.noes.wisbejo.util.AppVar.GALER_KEY;
import static adeyds.noes.wisbejo.util.AppVar.NOT_FROM_GALER;
import static adeyds.noes.wisbejo.util.AppVar.TAG_AKUN;
import static adeyds.noes.wisbejo.util.AppVar.TAG_GALERI;
import static adeyds.noes.wisbejo.util.AppVar.TAG_HOME;
import static adeyds.noes.wisbejo.util.AppVar.TAG_INFO;
import static adeyds.noes.wisbejo.util.AppVar.TO_DETAIL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AccountFragment accountFragment;
    private GaleriFragment galeriFragment;
    private HomeFragment homeFragment;
    private InfoFragment infoFragment;
    private boolean doubleBackToExitPressedOnce ;
    Fragment fragment ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        doubleBackToExitPressedOnce = false;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu m = navigationView.getMenu();
        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu!=null && subMenu.size() >0 ) {
                for (int j=0; j <subMenu.size();j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }
        navigationView.setNavigationItemSelectedListener(this);

        fragment = null;
        setFragment(0, TAG_HOME);


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity();
                } else {
                    ActivityCompat.finishAffinity(this);
                }
                System.exit(0);

            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Klik 2 kali untuk keluar", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);


        }
    }
    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/specific.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        //mNewTitle.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, mNewTitle.length(), 0); Use this if you want to center the items
        mi.setTitle(mNewTitle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "NOTHING", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            setFragment(0, TAG_HOME);
        } else if (id == R.id.nav_gallery) {
            setFragment(1, TAG_GALERI);
        } else if (id == R.id.nav_info) {
            setFragment(2, TAG_INFO);
        } else if (id == R.id.nav_akun) {
            setFragment(3, TAG_AKUN);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setFragment(int i, String tagg) {
        FragmentManager fragmentManager;

        fragmentManager = getSupportFragmentManager();
        homeFragment = (HomeFragment) fragmentManager.findFragmentByTag(TAG_HOME);
        galeriFragment = (GaleriFragment) fragmentManager.findFragmentByTag(TAG_GALERI);
        infoFragment = (InfoFragment) fragmentManager.findFragmentByTag(TAG_INFO);
        accountFragment = (AccountFragment) fragmentManager.findFragmentByTag(TAG_AKUN);
        if (i == 0 && homeFragment == null) {

            homeFragment = new HomeFragment();
            fragment = homeFragment;
        } else if (i == 1 ) {
            Bundle arguments = new Bundle();
            arguments.putString( FROM_GALER ,"NOTHING");
            galeriFragment = new GaleriFragment();
            galeriFragment.setArguments(arguments);
            fragment = galeriFragment;
        } else if (i == 2 && infoFragment == null) {

            infoFragment = new InfoFragment();
            fragment = infoFragment;
        } else if (i == 3 && accountFragment == null) {

            accountFragment = new AccountFragment();
            fragment = accountFragment;
        }else if (i == 1 && galeriFragment != null) {
           // galeriFragment = new GaleriFragment();
          //  setFragment(1, TAG_GALERI);
            Log.e("fromHere", galeriFragment.isDetached()+"");
        }

        fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment, tagg).commit();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("comeback", requestCode+ " "+ resultCode);
        if (requestCode==TO_DETAIL && resultCode!=NOT_FROM_GALER){
            Log.e("FROM DETAIL ON MAIN", "HERE");
            FragmentManager fragmentManager;

            Bundle arguments = new Bundle();
            arguments.putString( FROM_GALER ,GALER_EXTRA);
            arguments.putString( GALER_KEY , String.valueOf(resultCode));


            fragmentManager = getSupportFragmentManager();
            galeriFragment = (GaleriFragment) fragmentManager.findFragmentByTag(TAG_GALERI);
            galeriFragment = new GaleriFragment();
            galeriFragment.setArguments(arguments);
            fragment = galeriFragment;
            fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment, TAG_GALERI).commit();

        }
    }
}
