package com.oodi.godsendapp.fragment;

import android.support.v4.app.Fragment;

import com.oodi.godsendapp.util.BackPressImpl;
import com.oodi.godsendapp.util.OnBackPressListener;


/**
 * Created by pc on 5/19/17.
 */

public class RootFragment extends Fragment implements OnBackPressListener {

    @Override
    public boolean onBackPressed() {
        return new BackPressImpl(this).onBackPressed();
    }
}