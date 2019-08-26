package com.kode.formscreator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static com.kode.formscreator.Utils.addFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addFragment(new FormFragment(), getSupportFragmentManager());
    }


    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() == 1)
            finish();
        else
            getSupportFragmentManager().popBackStack();
    }
}
