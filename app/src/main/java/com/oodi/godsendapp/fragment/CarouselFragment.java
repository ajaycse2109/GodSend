package com.oodi.godsendapp.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.oodi.godsend.R;
import com.oodi.godsendapp.activity.AmbulanceActivity;
import com.oodi.godsendapp.adapter.ViewPagerAdapter;
import com.oodi.godsendapp.util.OnBackPressListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarouselFragment extends Fragment {

    Activity mContext ;
    View view;
    public ViewPagerAdapter adapter;

    public ViewPager pager;

    public TabLayout tabLayout;

    ImageView imgAmbulance ;

    public CarouselFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_carousel, container, false);
        mContext = getActivity() ;
        tabLayout = (TabLayout)view.findViewById(R.id.tabs);
        pager = (ViewPager)view.findViewById(R.id.vp_pages);
        tabLayout.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                pager.setCurrentItem(0);

            }
        });
        imgAmbulance = view.findViewById(R.id.imgAmbulance);
      //  setSelectedTab();
        imgAmbulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext , AmbulanceActivity.class);
                startActivity(intent);
            }
        });

        adapter = new ViewPagerAdapter(getResources(), getChildFragmentManager());
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        pager.setOffscreenPageLimit(4);

        setupTabIcons();

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 0) {
                    ImageView tabOne = (ImageView) tabLayout.getTabAt(0).getCustomView();
                    //tabOne.setTextColor(mContext.getResources().getColor(R.color.bg));
                    //tabOne.setText("Profile");
                    tabOne.setImageResource(R.drawable.hospital_active);
                    tabLayout.getTabAt(0).setCustomView(tabOne);
                    //position = 0;
                } else {
                    ImageView tabOne = (ImageView) tabLayout.getTabAt(0).getCustomView();
                    //tabOne.setTextColor(Color.rgb(108, 108, 108));
                    //tabOne.setText("Profile");
                    tabOne.setImageResource(R.drawable.hospital_inactive);
                    tabLayout.getTabAt(0).setCustomView(tabOne);
                }

                if (tab.getPosition() == 1) {
                    ImageView tabTwo = (ImageView) tabLayout.getTabAt(1).getCustomView();
                    //tabTwo.setTextColor(mContext.getResources().getColor(R.color.bg));//(TextView) LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab, null);
                    //tabTwo.setText("Team");
                    tabTwo.setImageResource(R.drawable.appointment_active);
                    tabLayout.getTabAt(1).setCustomView(tabTwo);

                } else {
                    ImageView tabTwo = (ImageView) tabLayout.getTabAt(1).getCustomView(); //(TextView) LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab, null);
                    //tabTwo.setText("Team");
                    //tabTwo.setTextColor(Color.rgb(108, 108, 108));
                    tabTwo.setImageResource(R.drawable.appointment_inactive);
                    tabLayout.getTabAt(1).setCustomView(tabTwo);
                }

                if (tab.getPosition() == 2) {
                    ImageView tabThree = (ImageView) tabLayout.getTabAt(2).getCustomView();
                    //tabThree.setTextColor(mContext.getResources().getColor(R.color.bg));
                    //tabThree.setText("Leave");
                    //tabThree.setImageResource(R.drawable.ambulance_inactive);
                    tabLayout.getTabAt(2).setCustomView(tabThree);

                } else {
                    ImageView tabThree = (ImageView) tabLayout.getTabAt(2).getCustomView();
                    //tabThree.setTextColor(Color.rgb(108, 108, 108));
                    //tabThree.setText("Leave");
                    //tabThree.setImageResource(R.drawable.ambulance_inactive);
                    tabLayout.getTabAt(2).setCustomView(tabThree);
                }

                if (tab.getPosition() == 3) {
                    ImageView tabFour = (ImageView) tabLayout.getTabAt(3).getCustomView();
                    //tabFour.setTextColor(mContext.getResources().getColor(R.color.bg));
                    //tabFour.setText("Advances");
                    tabFour.setImageResource(R.drawable.records_active);
                    tabLayout.getTabAt(3).setCustomView(tabFour);
                } else {
                    ImageView tabFour = (ImageView) tabLayout.getTabAt(3).getCustomView();
                    //tabFour.setTextColor(Color.rgb(108, 108, 108));
                    //tabFour.setText("Advances");
                    tabFour.setImageResource(R.drawable.records_inactive);
                    tabLayout.getTabAt(3).setCustomView(tabFour);

                }

                if (tab.getPosition() == 4) {
                    ImageView tabFive = (ImageView) tabLayout.getTabAt(4).getCustomView();
                    //tabFive.setTextColor(mContext.getResources().getColor(R.color.bg));
                    //tabFive.setText("Updates");
                    tabFive.setImageResource(R.drawable.profile_active);
                    tabLayout.getTabAt(4).setCustomView(tabFive);
                } else {
                    ImageView tabFive = (ImageView) tabLayout.getTabAt(4).getCustomView();
                    //tabFive.setTextColor(Color.rgb(108, 108, 108));
                    //tabFive.setText("Updates");
                    tabFive.setImageResource(R.drawable.profile_inactive);
                    tabLayout.getTabAt(4).setCustomView(tabFive);

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }


    public void setupTabIcons() {

        ImageView tabOne = (ImageView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        //tabOne.setText("Profile");
        tabOne.setImageResource(R.drawable.hospital_active);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        ImageView tabTwo = (ImageView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        //tabTwo.setText("Team");
        tabTwo.setImageResource(R.drawable.appointment_inactive);
        tabLayout.getTabAt(1).setCustomView(tabTwo);
        //tabTwo.setTextColor(Color.rgb(180, 180, 180));

        ImageView tabThree = (ImageView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        //tabThree.setText("Leave");
        //tabThree.setImageResource(R.drawable.ambulance_inactive);
        tabLayout.getTabAt(2).setCustomView(tabThree);
        //tabThree.setTextColor(Color.rgb(180, 180, 180));

        ImageView tabFour = (ImageView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        //tabFour.setText("Advances");
        tabFour.setImageResource(R.drawable.records_inactive);
        tabLayout.getTabAt(3).setCustomView(tabFour);
        //tabFour.setTextColor(Color.rgb(180, 180, 180));

        ImageView tabFive = (ImageView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        //tabFive.setText("Updates");
        tabFive.setImageResource(R.drawable.profile_inactive);
        tabLayout.getTabAt(4).setCustomView(tabFive);
        //tabFive.setTextColor(Color.rgb(180, 180, 180));

    }

    public boolean onBackPressed() {
        // currently visible tab Fragment
        OnBackPressListener currentFragment = (OnBackPressListener) adapter.getRegisteredFragment(pager.getCurrentItem());

        if (currentFragment != null) {
            // lets see if the currentFragment or any of its childFragment can handle onBackPressed
            return currentFragment.onBackPressed();
        }

        // this Fragment couldn't handle the onBackPressed call
        return false;
    }

}
