package cvrce.cvrce.com.cvrcecanteen;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class ViewPageAdapter extends FragmentPagerAdapter {
    public ViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        if (i == 0)
        {
            fragment = new Lunch();
        }
        else if (i == 1)
        {
            fragment = new Dinner();
        }

        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "Lunch";
        }
        else if (position == 1)
        {
            title = "Dinner";
        }

        return title;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
