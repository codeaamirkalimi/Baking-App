package com.example.aamirkalimi.bakingappmedia.utils;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;

import com.example.aamirkalimi.bakingappmedia.R;
import com.example.aamirkalimi.bakingappmedia.fragment.StepDetailsFragment;

public final class FragmentUtils {

    private FragmentUtils() {

    }

    public static void addDetailsFragment(FragmentActivity fragmentActivity, Bundle bundle) {
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        StepDetailsFragment stepDetailsFragment = (StepDetailsFragment) fragmentManager
                .findFragmentByTag(StepDetailsFragment.class.getSimpleName());
        if (stepDetailsFragment != null) {
            return;
        }

        fragmentManager.beginTransaction()
                .add(
                        R.id.container_step_details,
                        StepDetailsFragment.newInstance(bundle),
                        StepDetailsFragment.class.getSimpleName()
                )
                .commit();
    }


    public static void replaceDetailsFragment(FragmentActivity fragmentActivity, Bundle bundle) {
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(
                        R.id.container_step_details,
                        StepDetailsFragment.newInstance(bundle),
                        StepDetailsFragment.class.getSimpleName()
                )
                .commit();
    }
}
