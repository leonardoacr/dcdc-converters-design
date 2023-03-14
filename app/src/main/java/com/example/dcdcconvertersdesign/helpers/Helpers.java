package com.example.dcdcconvertersdesign.helpers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dcdcconvertersdesign.R;

import java.util.Locale;

public class Helpers {
    public static void setMainActionBar(Activity activity){
        ActionBar actionBar = ((AppCompatActivity) activity).getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.app_icon);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#293239")));

        SpannableString s = new SpannableString("DCDC Converters Design");
        s.setSpan(new TypefaceSpan("montserrat_regular"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        s.setSpan(new AbsoluteSizeSpan(20, true), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        actionBar.setTitle(s);
    }
    public static void setMinActionBar(Activity activity) {
        ActionBar actionBar = ((AppCompatActivity) activity).getSupportActionBar();
        assert actionBar != null;
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#293239")));

        SpannableString s = new SpannableString("DCDC Converters Design");
        s.setSpan(new TypefaceSpan("montserrat_regular"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        s.setSpan(new AbsoluteSizeSpan(20, true), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public static void unsetActionBar(Activity activity) {
        ActionBar actionBar = ((AppCompatActivity) activity).getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static String formatValue(double value, String unit) {
        String[] units = {"n", "μ", "m", "", "k", "M"};
        int exponent = (int) Math.floor(Math.log10(Math.abs(value)));
        int index = (exponent + 9) / 3;
        if (index < 0) {
            index = 0;
        } else if (index >= units.length) {
            index = units.length - 1;
        }
        double scaledValue = value / Math.pow(10, index * 3 - 9);
        return stringFormat(scaledValue) + " " + units[index] + unit;
    }

    public static String stringFormat(double input) {
        return String.format(Locale.US, "%.2f", input);
    }
}
