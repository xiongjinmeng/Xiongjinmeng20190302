package bawei.com.xiongjinmeng20190302.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * @作者 熊金梦
 * @时间 2019/3/2 0002 9:05
 * @
 */
public class MyAdapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> list;

    public MyAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.list=fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
