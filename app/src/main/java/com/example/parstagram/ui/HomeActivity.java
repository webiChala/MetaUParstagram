

package com.example.parstagram.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.parstagram.Fragments.ComposeFragment;
import com.example.parstagram.Fragments.PostsFragment;
import com.example.parstagram.Fragments.ProfileFragment;
import com.example.parstagram.R;
import com.example.parstagram.databinding.ActivityHomeBinding;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    public static final String TAG = "HomeActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());

        // layout of activity is stored in a special property called root
        View view = binding.getRoot();
        setContentView(view);

        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragmentToShow = null;
                switch (item.getItemId()) {
                    case R.id.action_home:
                        fragmentToShow = new PostsFragment();
                        break;
                    case R.id.action_post:
                        fragmentToShow = new ComposeFragment();
                        break;
                    case R.id.action_profile:
                        fragmentToShow = new ProfileFragment();
                        break;
                    default:
                        break;
                }

                if (fragmentToShow != null) {

                    getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, fragmentToShow).commit();

                }

                return true;
            }


        });

        binding.bottomNavigation.setSelectedItemId(R.id.action_home);


//        binding.btnLogOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //Log user out
//                ParseUser.logOut();
//                ParseUser currentUser = ParseUser.getCurrentUser();
//
//                Intent i = new Intent(HomeActivity.this, LoginActivity.class);
//                startActivity(i);
//                finish();
//            }
//        });


    }

}