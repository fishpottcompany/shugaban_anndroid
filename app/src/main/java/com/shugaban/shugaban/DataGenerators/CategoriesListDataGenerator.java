package com.shugaban.shugaban.DataGenerators;

import com.shugaban.shugaban.Models.CategoryModel;
import com.shugaban.shugaban.Models.MovieModel;

import java.util.ArrayList;
import java.util.List;

public class CategoriesListDataGenerator {
    // DECLARING THE DATA ARRAY LIST
    static List<CategoryModel> allData = new ArrayList<>();

    // SETTING/RESETTING ALL SUGGESTED LINKUPS DATA
    public static void setAllDatasAfresh(List<CategoryModel> newAllData) {
        CategoriesListDataGenerator.allData = newAllData;
    }

    // ADDING ONE DATA TO ARRAY LIST
    public static boolean addOneData(CategoryModel model) {
        return allData.add(model);
    }

    // GETTING ALL DATA AS ARRAY LIST
    public static List<CategoryModel> getAllData() {
        return allData;
    }

    // ADDING ONE DATA TO A DESIRED POSITION IN ARRAY LIST
    public static void addOneDataToDesiredPosition(int i, CategoryModel model){
        allData.add(i, model);
    }
}
