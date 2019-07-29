package com.kode.formscreator;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import static com.kode.formscreatorlib.Utils.FormsUtils.LOG;

public class Utils {
    private Activity mContext;

    public Utils(Activity mContext) {
        this.mContext = mContext;
    }


    /**
     * Add Fragment to an Activity
     *
     * @param fragment        FRAGMENT to be added
     * @param fragmentManager FRAGMENT_MANAGER to commit the changes and do the actual replacement
     **/
    public static void addFragment(Fragment fragment, android.support.v4.app.FragmentManager fragmentManager) {
        String backStackName = fragment.getClass().getName();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStackName, 0);
        if (!fragmentPopped) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.container, fragment, backStackName).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            transaction.addToBackStack(backStackName);
            transaction.commit();
        }
    }


    /**
     * Replace the current Fragment With new Fragment With no Animation
     **/
    public static void replaceFragment(Fragment newFragment, android.support.v4.app.FragmentManager fragmentManager) {
        try {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.container, newFragment).setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
            transaction.addToBackStack(newFragment.getClass().getName());
            transaction.commitAllowingStateLoss();
        } catch (Exception e) {
            LOG("Error chamging fragment " + e.getMessage());
        }
    }


    /**
     * Replace the current Fragment With new Fragment With no Animation
     **/
    public static void replaceCustomFragment(Fragment newFragment, android.support.v4.app.FragmentManager fragmentManager) {
        try {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.container, newFragment);
            transaction.addToBackStack(newFragment.getClass().getName());
            transaction.commit();
        } catch (Exception e) {
            LOG("Error chamging fragment " + e.getMessage());
        }
    }

    public static void replaceFragmentWithId(Fragment newFragment, int id, android.support.v4.app.FragmentManager fragmentManager) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(id, newFragment).setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
        transaction.addToBackStack(newFragment.getClass().getName());
        transaction.commitAllowingStateLoss();
        // replaceFragmentFromRight(newFragment, fragmentManager);
    }


    public static void replaceFragmentFromRight(Fragment newFragment, android.support.v4.app.FragmentManager fragmentManager) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
      //  transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
