package com.example.silagemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.silagemanager.Database.DBAdapter;
import com.example.silagemanager.Ensiroma.EnsiromaFragment;
import com.example.silagemanager.Metaforeas.MetaforeasFragment;
import com.example.silagemanager.Paragogos.ParagogosFragment;
import com.example.silagemanager.databinding.ActivityMenuBinding;

public class MenuActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        //load Homepage
        HomeFragment homeFragment = new HomeFragment();
        this.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_menu, homeFragment).commit();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        //connect database
        DBAdapter dbAdapter = new DBAdapter(this);

        //Start AppService
        Intent intent = new Intent(this, AppService.class);
        startService(intent);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //-----------------------Menu Call---------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.menu_home){
            HomeFragment homeFragment = new HomeFragment();
            this.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_menu, homeFragment).commit();
        }else if(id == R.id.menu_paragogos) {
            ParagogosFragment paragogosFragment = new ParagogosFragment();
            this.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_menu, paragogosFragment).commit();

        }else if (id==R.id.menu_metaforeas){
            MetaforeasFragment metaforeasFragment = new MetaforeasFragment();
            this.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_menu, metaforeasFragment).commit();

        }else if(id==R.id.menu_ensiroma){
            EnsiromaFragment ensiromaFragment = new EnsiromaFragment();
            this.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_menu, ensiromaFragment).commit();
        }

        return super.onOptionsItemSelected(item);
    }
}