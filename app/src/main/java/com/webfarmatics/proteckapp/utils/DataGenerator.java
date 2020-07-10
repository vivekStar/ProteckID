package com.webfarmatics.proteckapp.utils;

import android.content.Context;

import com.webfarmatics.proteckapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataGenerator {

    public static ArrayList<String> getSamsungModelData(Context ctx) {
        ArrayList<String> items = new ArrayList<>();
        String name_arr[] = ctx.getResources().getStringArray(R.array.strings_samsung);
        for (String s : name_arr) items.add(s);
        Collections.shuffle(items);
        return items;
    }

    public static ArrayList<String> getNokiaModelData(Context ctx) {
        ArrayList<String> items = new ArrayList<>();
        String name_arr[] = ctx.getResources().getStringArray(R.array.strings_nokia);
        for (String s : name_arr) items.add(s);
        Collections.shuffle(items);
        return items;
    }


    public static ArrayList<String> getMotrolaModelData(Context ctx) {
        ArrayList<String> items = new ArrayList<>();
        String name_arr[] = ctx.getResources().getStringArray(R.array.strings_motorola);
        for (String s : name_arr) items.add(s);
        Collections.shuffle(items);
        return items;
    }

    public static ArrayList<String> getSonyModelData(Context ctx) {
        ArrayList<String> items = new ArrayList<>();
        String name_arr[] = ctx.getResources().getStringArray(R.array.strings_sony);
        for (String s : name_arr) items.add(s);
        Collections.shuffle(items);
        return items;
    }


    public static ArrayList<String> getHuaweiModelData(Context ctx) {
        ArrayList<String> items = new ArrayList<>();
        String name_arr[] = ctx.getResources().getStringArray(R.array.strings_huawei);
        for (String s : name_arr) items.add(s);
        Collections.shuffle(items);
        return items;
    }


    public static ArrayList<String> getAppleModelData(Context ctx) {
        ArrayList<String> items = new ArrayList<>();
        String name_arr[] = ctx.getResources().getStringArray(R.array.strings_apple);
        for (String s : name_arr) items.add(s);
        Collections.shuffle(items);
        return items;
    }




    public static ArrayList<String> getBrandData(Context ctx) {
        ArrayList<String> items = new ArrayList<>();
        String name_arr[] = ctx.getResources().getStringArray(R.array.strings_brands);
        for (String s : name_arr) items.add(s);
//        Collections.shuffle(items);
        return items;
    }


    public static ArrayList<String> getStateList(Context ctx) {
        ArrayList<String> items = new ArrayList<>();
        String name_arr[] = ctx.getResources().getStringArray(R.array.states);
        for (String s : name_arr) items.add(s);
//        Collections.shuffle(items);
        return items;
    }

}
