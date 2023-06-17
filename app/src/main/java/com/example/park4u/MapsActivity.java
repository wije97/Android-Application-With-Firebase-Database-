package com.example.park4u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {

    private GoogleMap mMap;
    private int i=0;
    TextView slotCount;
    Handler handlerR = new Handler();
    Runnable refresh;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    private FirebaseAuth Auth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        slotCount = findViewById(R.id.A);

        set();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        /*-------Hooks------*/
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        Menu menu = navigationView.getMenu();

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        final MarkerOptions markerOpt = new MarkerOptions();




        // Add a marker in Sydney and move the camera
        final LatLng Sloat = new LatLng(6.957914, 79.877109);
        LatLng Sloat2 = new LatLng(6.953606, 79.881975);
        LatLng Sloat3 = new LatLng(6.954565, 79.880564);

        refresh = new Runnable() {
            public void run() {
                //mMap.addMarker(new MarkerOptions().position(Sloat).snippet("Available: "+slotCount.getText().toString()).title("Park A"));
                markerOpt.position(new LatLng(6.957914, 79.877109))
                        .title("Park A")
                        .snippet("Available: "+slotCount.getText().toString());

                //Set Custom InfoWindow Adapter
                InfoWindowData adapter = new InfoWindowData(MapsActivity.this);
                mMap.setInfoWindowAdapter(adapter);

                mMap.addMarker(markerOpt).showInfoWindow();

                handlerR.postDelayed(refresh, 3000);
            }
        };
        handlerR.post(refresh);

        mMap.addMarker(new MarkerOptions().position(Sloat2).title("Park B").snippet("Available: 6"));
        mMap.addMarker(new MarkerOptions().position(Sloat3).title("Park C").snippet("Available: 6"));

        LatLng Sloat4 = new LatLng(6.877492, 79.859533);
        LatLng Sloat5 = new LatLng(6.879969, 79.869112);
        LatLng Sloat6 = new LatLng(6.867733,  79.862772);

        mMap.addMarker(new MarkerOptions().position(Sloat4).title("Park A").snippet("Available: 6"));
        mMap.addMarker(new MarkerOptions().position(Sloat5).title("Park B").snippet("Available: 6"));
        mMap.addMarker(new MarkerOptions().position(Sloat6).title("Park C").snippet("Available: 6"));

        LatLng Sloat7 = new LatLng(6.887175, 79.876895);
        LatLng Sloat8 = new LatLng(6.879189, 79.876656);
        LatLng Sloat9 = new LatLng(6.873045, 79.883008);

        mMap.addMarker(new MarkerOptions().position(Sloat7).title("Park A").snippet("Available: 6"));
        mMap.addMarker(new MarkerOptions().position(Sloat8).title("Park B").snippet("Available: 6"));
        mMap.addMarker(new MarkerOptions().position(Sloat9).title("Park C").snippet("Available: 6"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Sloat,18.0f));
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {

            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_place:
                Intent intent = new Intent(MapsActivity.this, OurPlaces.class);
                startActivity(intent);
                break;

            case R.id.nav_share:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_logout:
                Intent intent2 = new Intent(MapsActivity.this,Login.class);
                startActivity(intent2);
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void set(){

        final String title = null;

        //Slot count
        final String text ="6 5 5 4 3 4 3 2 1 0 2 2 4 5 3 2 1 0";
        final String[] words = text.toString().split(" ");
        final Handler handler = new Handler();

        handler.post(new Runnable(){
            @Override
            public void run() {
                slotCount.setText(""+words[i]);
                i++;
                if(i < words.length) {
                    //time
                    handler.postDelayed(this, 6000);
                }
            }
        });
    }

}