package com.example.thedevelopmentbuild.vergerss;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;


public class ViewPagerAdapter extends FragmentPagerAdapter {

    /*List that hold the fragments & tabTitles the will correspond to in tabbed navigation*/
    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<String> tabTiles = new ArrayList<>();


    /*Adds fragments to ViewPageAdapter*/
    public void addFragments(Fragment fragments, String tabTiles){
        this.fragments.add(fragments);
        this.tabTiles.add(tabTiles);
    }

    //Constructor
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    //Reference to item (Fragment) stored in fragments collection
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    /*Number of available/stored fragments*/
    @Override
    public int getCount() {
        return fragments.size();
    }

    /*Title of Page in ViewPager*/
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTiles.get(position);
    }
}
