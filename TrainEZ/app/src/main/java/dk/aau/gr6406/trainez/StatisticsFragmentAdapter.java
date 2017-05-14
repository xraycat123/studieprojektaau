package dk.aau.gr6406.trainez;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by marti on 4/23/2017.
 */

public class StatisticsFragmentAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public StatisticsFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new WeightStatFragment();
        } else {
            return new ExerciseStatFragment();
        }
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return 2;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return mContext.getString(R.string.weightTabInfo);
            case 1:
                return mContext.getString(R.string.ExerciseTabInfo);
            default:
                return null;
        }
    }

}