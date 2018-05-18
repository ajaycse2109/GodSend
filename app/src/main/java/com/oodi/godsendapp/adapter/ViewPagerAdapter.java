package com.oodi.godsendapp.adapter;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.oodi.godsendapp.fragment.AmbulanceReplaceFragment;
import com.oodi.godsendapp.fragment.appointments.AppointmentsReplaceFragment;
import com.oodi.godsendapp.fragment.hospital.HospitalReplaceFragment;
import com.oodi.godsendapp.fragment.profile.ProfileReplaceFragment;
import com.oodi.godsendapp.fragment.records.RecordReplaceFragment;

/**
 * Created by pc on 5/19/17.
 */

public class ViewPagerAdapter extends android.support.v4.app.FragmentStatePagerAdapter {

    private final Resources resources;


    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

    /**
     * Create pager adapter
     *
     * @param resources
     * @param fm
     */
    public ViewPagerAdapter(final Resources resources, FragmentManager fm) {
        super(fm);
        this.resources = resources;
    }

    @Override
    public Fragment getItem(int position) {
        final Fragment result;
        switch (position) {
            case 0:
                result = new HospitalReplaceFragment();
                break;
            case 1:
                result = new AppointmentsReplaceFragment();
                break;
            case 2:
                result = new AmbulanceReplaceFragment();
                break;
            case 3:
                result = new RecordReplaceFragment();
                break;
            case 4:
                result = new ProfileReplaceFragment();
                break;
            default:
                result = null;
                break;
        }

        return result;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        switch (position) {
            case 0:
                return "Hospital";
            case 1:
                return "Appointments";
            case 2:
                return "Ambulance";
            case 3:
                return "Record";
            case 4:
                return "Profile";
            default:
                return null;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }
}