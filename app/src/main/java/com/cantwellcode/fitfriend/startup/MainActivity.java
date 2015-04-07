package com.cantwellcode.fitfriend.startup;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.cantwellcode.fitfriend.connect.GoalsActivity;
import com.cantwellcode.fitfriend.connect.SettingsActivity;
import com.cantwellcode.fitfriend.friends.FriendsActivity;
import com.cantwellcode.fitfriend.nutrition.NutritionFavoritesView;
import com.cantwellcode.fitfriend.R;
import com.cantwellcode.fitfriend.connect.ForumFragment;
import com.cantwellcode.fitfriend.exercise.log.WorkoutLog;
import com.cantwellcode.fitfriend.nutrition.NutritionLog;
import com.cantwellcode.fitfriend.plan.Plan;
import com.cantwellcode.fitfriend.purchases.PurchasesActivity;
import com.cantwellcode.fitfriend.utils.SocialEvent;
import com.cantwellcode.fitfriend.utils.Statics;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean firstOpen = sp.getBoolean("firstOpen", true);
        if (firstOpen) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(getLayoutInflater().inflate(R.layout.fragment_initial_help, null));
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
            sp.edit().putBoolean("firstOpen", false).commit();
        }

        // Query for Friends
        SocialEvent.getCurrentFriendshipsQuery().findInBackground(new FindCallback<ParseObject>() {
            // query for friendships
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {
                    final List<ParseUser> friends = new ArrayList<ParseUser>();
                    // get each friend out the friendship objects
                    for (ParseObject friendship : parseObjects) {
                        ParseUser friend = SocialEvent.getFriendFromFriendship(friendship);
                        friends.add(friend);
                    }
                    // unpin the old friends
                    ParseObject.unpinAllInBackground(Statics.PIN_FRIENDS, new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            // pin the new friends
                            ParseObject.pinAllInBackground(Statics.PIN_FRIENDS, friends);
                        }
                    });
                } else {

                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        switch (position) {
            case 0:
                Fragment workoutFragment = WorkoutLog.newInstance();
                FragmentManager fm1 = getSupportFragmentManager();
                fm1.beginTransaction().replace(R.id.container, workoutFragment).commit();
                break;
            case 1:
                Fragment nutritionFragment = NutritionLog.newInstance();
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.container, nutritionFragment).commit();
                break;
            case 2:
                Fragment planFragment = Plan.newInstance();
                FragmentManager fm2 = getSupportFragmentManager();
                fm2.beginTransaction().replace(R.id.container, planFragment).commit();
                break;
            case 3:
                Fragment forumFragment = ForumFragment.newInstance();
                FragmentManager fm3 = getSupportFragmentManager();
                fm3.beginTransaction().replace(R.id.container, forumFragment).commit();
                break;
            case 4:
                Intent i0 = new Intent(this, SettingsActivity.class);
                startActivity(i0);
                break;
            case 5:
                Intent i1 = new Intent(this, GoalsActivity.class);
                startActivity(i1);
                break;
            case 6:
                Intent i2 = new Intent(this, NutritionFavoritesView.class);
                startActivity(i2);
                break;
            case 7:
                Intent i3 = new Intent(this, FriendsActivity.class);
                startActivity(i3);
                break;
            case 8:
                Intent i4 = new Intent(this, PurchasesActivity.class);
                startActivity(i4);
                break;
        }
    }
}
