package islamkhsh.com.healthinpocket.ui.activity.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import islamkhsh.com.healthinpocket.R;
import islamkhsh.com.healthinpocket.ui.activity.base.BaseActivity;
import islamkhsh.com.healthinpocket.ui.activity.login.LoginActivity;
import islamkhsh.com.healthinpocket.ui.adapter.ViewPagerAdapter;
import islamkhsh.com.healthinpocket.ui.fragment.check_up.CheckUpFragment;
import islamkhsh.com.healthinpocket.ui.fragment.history.HistoryFragment;

public class MainActivity extends BaseActivity {

    @BindView(R.id.drawer_layout)
    public DrawerLayout drawerLayout;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.navigation_view)
    public NavigationView navigationView;
    @BindView(R.id.view_pager)
    public ViewPager viewPager;
    @BindView(R.id.tabs)
    public TabLayout tabLayout;
    private MainViewModel mainViewModel;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private View headerView;
    private TextView userName, userMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupToolbarAndDrawer();
        setupViewPager();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        mainViewModel.getCurrentUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(@Nullable FirebaseUser firebaseUser) {
                if (firebaseUser == null)
                    sendToLogScreen();
                else {
                    userName.setText(firebaseUser.getDisplayName());
                    userMail.setText(firebaseUser.getEmail());
                }

            }
        });
    }


    @Override
    public void setupViewModel() {
        //super.setupViewModel();
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setupViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),
                CheckUpFragment.newInstance(getResources().getString(R.string.check_up)),
                HistoryFragment.newInstance(getResources().getString(R.string.history)));
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        navigationView.setCheckedItem(R.id.check_up_fragment);
                        break;
                    case 1:
                        navigationView.setCheckedItem(R.id.history_fragment);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public void setupToolbarAndDrawer() {
        //Set a Toolbar to replace the ActionBar
        setSupportActionBar(toolbar);

        //hide app title
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //drawerToggle create a Hamburger icon (three lines)
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        // Tie DrawerLayout events to the ActionBarToggle
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        setupNavigationContent();
        setupNavigationHeader();

    }

    public void setupNavigationHeader() {
        this.headerView = navigationView.getHeaderView(0);
        this.userName = headerView.findViewById(R.id.user_name);
        this.userMail = headerView.findViewById(R.id.user_email);
    }

    private void setupNavigationContent() {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }

                });
    }


    public void selectDrawerItem(MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.check_up_fragment:
                viewPager.setCurrentItem(0);
                menuItem.setChecked(true);
                break;
            case R.id.history_fragment:
                viewPager.setCurrentItem(1);
                menuItem.setChecked(true);
                break;
            case R.id.account_settings:
                menuItem.setChecked(false);
                break;
            case R.id.log_out:
                mainViewModel.setCurrentUser(null);
                break;
            default:

        }

        // Close the navigation drawer
        drawerLayout.closeDrawers();
    }

    private void sendToLogScreen() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

}
