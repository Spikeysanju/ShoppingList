package com.spikeysanju98gmail.shoppinglist;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.spikeysanju98gmail.shoppinglist.fragments.ItemCatalogFragment;
import com.spikeysanju98gmail.shoppinglist.fragments.PopularFragment;
import com.spikeysanju98gmail.shoppinglist.fragments.RecentsFragment;

public class ChooseItemActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private android.support.v7.widget.Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_item);

        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);


        tabLayout = (TabLayout)findViewById(R.id.tablayout_id);
        viewPager = (ViewPager)findViewById(R.id.viewpager_id);
        viewPager.setOffscreenPageLimit(3);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        setSupportActionBar(mToolbar);





        //Fragments Here



        adapter.AddFragment(new RecentsFragment(),"Recent");
        adapter.AddFragment(new PopularFragment(),"Popular");
        adapter.AddFragment(new ItemCatalogFragment(),"Item Catalog");


        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0);
        tabLayout.getTabAt(1);
        tabLayout.getTabAt(2);




        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);



    }
}
