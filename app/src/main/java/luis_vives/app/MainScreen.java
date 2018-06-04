package luis_vives.app;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import luis_vives.app.view.fragmentExamModels;
import luis_vives.app.view.main_fragment;

public class MainScreen extends AppCompatActivity {

    private Toolbar appbar;
    private NavigationView navView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        initializeMenu();
        ShowMainFragment();
    }

    private void ShowMainFragment(){
        Fragment fragment = null;
        fragment = new main_fragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
        drawerLayout.closeDrawers();
    }

    private void initializeMenu(){
        appbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(appbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.shift_menu_right);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navView = (NavigationView) findViewById(R.id.navview);
        navView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                boolean fragmentTransaction = false;
                Fragment fragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.menu_seccion_1:
                        fragment = new fragmentExamModels();
                        fragmentTransaction = true;
                        break;
                }
                if(fragmentTransaction) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, fragment)
                            .commit();
                    menuItem.setChecked(true);
                    getSupportActionBar().setTitle(menuItem.getTitle());
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
