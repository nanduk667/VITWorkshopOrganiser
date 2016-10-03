package com.example.intodarkness.vitworkshoporganisers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView = null;
    Toolbar toolbar = null;
    public  static String tagInfo;
    public static String schoolNameVar;
    public static String workshopNameVar;
    public int i;
    public String stud;
    ListView regStudents;
    public static ArrayList <String> rollNo = new ArrayList<>();
    public static final String FIREBASE_URL = "https://vitworkshop.firebaseio.com/";
    Firebase myFirebaseRef;
    Firebase newURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);

        //Set the fragment initially
        FeaturedWorkshops featuredWorkshops = new FeaturedWorkshops();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, featuredWorkshops);
        fragmentTransaction.commit();

       toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
       // displayView(R.id.nav_featured);
    }
    public void extractDetails(View view) {

        tagInfo = view.getTag().toString();
        if (tagInfo.contains("SITE")) {

            schoolNameVar = "SITE";
            i=1;

        }
        else if(tagInfo.contains("SELECT"))
        {
            schoolNameVar = "SELECT";
            i=1;

        }
        else if(tagInfo.contains("SAS"))
        {
            schoolNameVar = "SAS";
            i=1;
        }
        if(checkNetwork())// if internet
        {
            workshopNameVar = tagInfo.substring(tagInfo.indexOf(" "));
            getFromFirebase();
        }
        else
        {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("No Internet connectivity, Please try again later!")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
    public void getFromFirebase()
    {

        myFirebaseRef = new Firebase(FIREBASE_URL);
        newURL = myFirebaseRef.child(schoolNameVar).child(workshopNameVar);
        stud = "student" + i++;
        newURL.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getUpdates(dataSnapshot);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        startActivity(new Intent(this, RegisteredStudents.class));
    }


    public void getUpdates(DataSnapshot dataSnapshot)
    {
        rollNo.clear();
        for(DataSnapshot snapshot : dataSnapshot.getChildren())
        {
            DataFromFirebase dataFromFirebase = snapshot.getValue(DataFromFirebase.class);
            System.out.println(dataFromFirebase.getRollNo());


          rollNo.add(dataFromFirebase.getRollNo());



            if(rollNo.size()>0)
            {   /*Intent intent = new Intent(MainActivity.this,RegisteredStudents.class);
                MainActivity.this.startActivity(intent);*/
                ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,rollNo);
                regStudents = RegisteredStudents.listView;
                regStudents.setAdapter(arrayAdapter);
            }
            else
            {
                Toast.makeText(MainActivity.this,"Ivide onumila kanikan",Toast.LENGTH_SHORT).show();
            }



        }
    }
    public boolean checkNetwork() {
        try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = p1.waitFor(); //the exit value of the native process being waited on.
            System.out.println(returnVal);
            System.out.println(p1);
            boolean reachable = (returnVal == 0);
            return reachable; // returns true if internet is available
        } catch (Exception e) {

            System.out.print(e);
        }
        return false;
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



   /* @Override
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
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        if (id == R.id.nav_featured) {
            FeaturedWorkshops featuredWorkshops = new FeaturedWorkshops();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, featuredWorkshops);
            fragmentTransaction.commit();
        } else  if (id == R.id.nav_cs) {
            ComputerScienceFragment computerScienceFragment = new ComputerScienceFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, computerScienceFragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_electronics) {
            ElectronicsFragment electronicsFragment = new ElectronicsFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, electronicsFragment);
            fragmentTransaction.commit();
        }/* else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
