package com.oodi.godsendapp.activity;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.vivchar.viewpagerindicator.ViewPagerIndicator;
import com.oodi.godsend.R;
import com.oodi.godsendapp.fragment.profile.MedicalRecordsFragment;
import com.oodi.godsendapp.fragment.walkthrough.WalkthroughFourFragment;
import com.oodi.godsendapp.fragment.walkthrough.WalkthroughOneFragment;
import com.oodi.godsendapp.fragment.walkthrough.WalkthroughThreeFragment;
import com.oodi.godsendapp.fragment.walkthrough.WalkthroughTwoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalkthroughActivity extends AppCompatActivity {

    Activity mContext;
    public static ViewPager mViewpager ;
    @BindView(R.id.view_pager_indicator)
    ViewPagerIndicator mIndicator;
    public static ImageView mImgBack;
    public static TextView mTxtSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough);
        mContext = this ;
        ButterKnife.bind(mContext);

        mImgBack = findViewById(R.id.imgBack);
        mTxtSkip = findViewById(R.id.txtSkip);
        mViewpager = findViewById(R.id.viewpager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new WalkthroughOneFragment(), "");
        adapter.addFragment(new WalkthroughTwoFragment(), "");
        adapter.addFragment(new WalkthroughThreeFragment(), "");
        adapter.addFragment(new WalkthroughFourFragment(), "");
        mViewpager.setAdapter(adapter);

        mViewpager.setOffscreenPageLimit(3);

        mIndicator.setupWithViewPager(mViewpager);
        //mIndicator.addOnPageChangeListener(mOnPageChangeListener);

    }

    class ViewPagerAdapter extends android.support.v4.app.FragmentStatePagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

}
