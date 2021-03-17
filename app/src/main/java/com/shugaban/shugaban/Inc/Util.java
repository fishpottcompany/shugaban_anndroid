package com.shugaban.shugaban.Inc;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.shugaban.shugaban.R;

public class Util {

    public static Boolean ALLOW_LOGGING = true;
    public static String LINK_PROTOCOL = "http://";
    //public static String LINK_DOMAIN = "pjdigitalpool.fishpott.com";
    public static String LINK_DOMAIN = "144.202.76.74";

    // API LINKS
    //public static String LINK_SIGNUP = LINK_PROTOCOL + LINK_DOMAIN + "/api/v1/member/regis";
    public static String LINK_SIGNUP = LINK_PROTOCOL + LINK_DOMAIN + "/api/v1/member/register/";
    public static String LINK_LOGIN = LINK_PROTOCOL + LINK_DOMAIN + "/api/v1/member/login";
    public static String LINK_SEND_RESET_CODE = LINK_PROTOCOL + LINK_DOMAIN + "/api/v1/member/forgot";
    public static String LINK_RESET_PASSWORD = LINK_PROTOCOL + LINK_DOMAIN + "/api/v1/member/reset";

    //SHARES PREFERENCES
    public static String SHARED_PREF_KEY_USER_TOKEN = "SHARED_PREF_KEY_USER_TOKEN";
    public static String SHARED_PREF_KEY_USER_ID = "SHARED_PREF_KEY_USER_ID";
    public static String SHARED_PREF_KEY_USER_FIRST_NAME = "SHARED_PREF_KEY_USER_FIRST_NAME";
    public static String SHARED_PREF_KEY_USER_LAST_NAME = "SHARED_PREF_KEY_USER_LAST_NAME";
    public static String SHARED_PREF_KEY_USER_COUNTRY = "SHARED_PREF_KEY_USER_COUNTRY";
    public static String SHARED_PREF_KEY_USER_PHONE = "SHARED_PREF_KEY_USER_PHONE";
    public static String SHARED_PREF_KEY_USER_EMAIL= "SHARED_PREF_KEY_USER_EMAIL";
    public static String SHARED_PREF_KEY_USER_FLAGGED= "SHARED_PREF_KEY_USER_FLAGGED";

    //MOVIE SHARED PREFERENCES
    public static String SHARED_PREF_KEY_MOVIE_NAME = "SHARED_PREF_KEY_MOVIE_NAME";
    public static String SHARED_PREF_KEY_MOVIE_YEAR = "SHARED_PREF_KEY_MOVIE_YEAR";
    public static String SHARED_PREF_KEY_MOVIE_CATEGORY = "SHARED_PREF_KEY_MOVIE_CATEGORY";
    public static String SHARED_PREF_KEY_MOVIE_DURATION = "SHARED_PREF_KEY_MOVIE_DURATION";
    public static String SHARED_PREF_KEY_MOVIE_DESCRIPTION = "SHARED_PREF_KEY_MOVIE_DESCRIPTION";
    public static String SHARED_PREF_KEY_MOVIE_PLOT = "SHARED_PREF_KEY_MOVIE_PLOT";
    public static String SHARED_PREF_KEY_MOVIE_PRICE_NAIRA = "SHARED_PREF_KEY_MOVIE_PRICE_NAIRA";
    public static String SHARED_PREF_KEY_MOVIE_PRICE_DOLLAR = "SHARED_PREF_KEY_MOVIE_PRICE_DOLLAR";

    //MENU ITEMS
    public static int MOVIES_FRAMENT = 0;
    public static int SEARCH_FRAMENT = 1;
    public static int LIVE_FRAMENT = 2;
    public static int CINEMA_FRAMENT = 3;
    public static int PURCHASED_FRAMENT = 4;

    public static void show_log_in_console(String title, String description){
        if(ALLOW_LOGGING){
            Log.e(title, description);
        }
    }

    // OPENING A FRAGMENT
    public static void open_fragment(FragmentManager fragmentManager, int fragmentContainerId, Fragment newFragment, String fragmentName, int includeAnimation){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(includeAnimation == 1){
            transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_right);
        } else if (includeAnimation == 2){
            transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (includeAnimation == 3){
            transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down,R.anim.slide_in_down, R.anim.slide_out_up);
        }
        transaction.addToBackStack(fragmentName);
        transaction.add(fragmentContainerId, newFragment, fragmentName).commit();
    }
    // GET SHARED PREFERENCE STRING
    public static String getSharedPreferenceString(Context context, String key) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        context = null;
        return preferences.getString(key, "");

    }

    // EDIT SHARED PREFERENCE STRING
    public static void setSharedPreferenceString(Context context, String key, String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
        context = null;
    }

    // GET SHARED PREFERENCE INT
    public static int getSharedPreferenceInt(Context context, String key) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        context = null;
        return preferences.getInt(key, 0);

    }

    // SET SHARED PREFERENCE INT
    public static void setSharedPreferenceInt(Context context, String key, int value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
        context = null;
    }


    // GET SHARED PREFERENCE INT
    public static int getSharedPreferenceFloat(Context context, String key) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        context = null;
        return preferences.getInt(key, 0);

    }

    // SET SHARED PREFERENCE INT
    public static void setSharedPreferenceFloat(Context context, String key, float value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(key, value);
        editor.apply();
        context = null;
    }


    // GET SHARED PREFERENCE BOOLEAN
    public static boolean getSharedPreferenceBoolean(Context context, String key) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        context = null;
        return preferences.getBoolean(key, false);

    }

    // SET SHARED PREFERENCE BOOLEAN
    public static void setSharedPreferenceBoolean(Context context, String key, boolean value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        context = null;
        editor.apply();
    }

    // GET SHARED PREFERENCE STRING
    public static ArrayList<String> getSharedPreferenceStringSet(Context context, String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> set = preferences.getStringSet(key, null);
        if(set == null){
            return null;
        }
        ArrayList<String> array_list=new ArrayList<String>(set);
        context = null;
        return array_list;
    }

    // EDIT SHARED PREFERENCE STRING
    public static void setSharedPreferenceStringSet(Context context, String key, ArrayList<String> array_list) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        Set<String> set = new HashSet<String>();
        set.addAll(array_list);
        editor.putStringSet(key, set);
        editor.apply();
        context = null;
    }


    // CLEAR ALL SHARED PREFERENCE
    public static void deleteAllDataInSharedPreference(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        context = null;
    }

    public static void loadImageView(Context context, String url, ImageView imageView, final ProgressBar progressBar){

        if(context != null && imageView != null){
            Util.show_log_in_console("loadImageView", "url: " + url);
            Glide.with(context)
                    .load(url)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            if(progressBar != null){
                                progressBar.setVisibility(View.GONE);
                            }
                            Util.show_log_in_console("loadImageView", "onLoadFailed: " + e.toString());
                            return false;
                        }
                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            if(progressBar != null){
                                progressBar.setVisibility(View.GONE);
                            }
                            Util.show_log_in_console("loadImageView", "onResourceReady");
                            return false;
                        }
                    })
                    .into(imageView);
            Util.show_log_in_console("loadImageView", "COMPLETED");
        }
    }


}
