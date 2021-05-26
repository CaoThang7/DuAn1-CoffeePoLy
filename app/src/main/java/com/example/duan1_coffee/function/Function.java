package com.example.duan1_coffee.function;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;


public class Function {
    // HÀM LOAD FRAGMENT
    public static boolean loadFragment(Fragment fragment, int id, View view) {
        FragmentActivity activity = (FragmentActivity) view.getContext();
        FragmentManager manager = activity.getSupportFragmentManager();
        if (fragment != null) {
            manager.beginTransaction()
                    .replace(id, fragment)
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        return false;
    }

}
