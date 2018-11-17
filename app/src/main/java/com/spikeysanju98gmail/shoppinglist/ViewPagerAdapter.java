package com.spikeysanju98gmail.shoppinglist;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SpikeySanju on 11/02/18.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> FirstFragment = new ArrayList<>();

    private final List<String> FirstTitles = new ArrayList<>();


    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FirstFragment.get(position);

    }

    @Override
    public int getCount() {
        return FirstTitles.size();

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return FirstTitles.get(position);

    }

    public void AddFragment (Fragment fragment, String title){
        FirstFragment.add(fragment);
        FirstTitles.add(title);

    }
}
