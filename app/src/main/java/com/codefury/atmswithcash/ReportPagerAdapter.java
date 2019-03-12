package com.codefury.atmswithcash;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by codefury on 11/14/16.
 */

public class ReportPagerAdapter extends FragmentPagerAdapter {
    int mNumOfTabs;
    String title;

    public ReportPagerAdapter(FragmentManager fm, int NumOfTabs, String title) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.title = title;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ReportATMFragment tab1 = new ReportATMFragment();
                Bundle bundle = new Bundle();
                bundle.putString("TITLE", title);
                tab1.setArguments(bundle);
                return tab1;
            case 1:
                HistoryFragment tab2 = new HistoryFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
