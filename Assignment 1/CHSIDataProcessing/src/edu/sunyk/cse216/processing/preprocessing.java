/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sunyk.cse216.processing;

import com.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author techj
 */
public class preprocessing {

    HashMap<String, List> csvData = new HashMap();
    HashMap<String, List> dsData = new HashMap();
    CSVReader LCD;
    CSVReader DS;

    public preprocessing(String LCDPath, String DSPath) throws FileNotFoundException, IOException {
        LCD = new CSVReader(new FileReader(LCDPath));
        DS = new CSVReader(new FileReader(DSPath));
        //parsing DS below

        List<String[]> DSAll = DS.readAll();
        ArrayList<Object>[] dsPairs = new ArrayList[DSAll.get(0).length];
        for (int i = 0; i < dsPairs.length; i++) {//init arraylist array
            dsPairs[i] = new ArrayList<Object>();
        }
        for (String[] s : DSAll) {
            for (int i = 0; i < s.length; i++) {
                dsPairs[i].add(s[i]);
            }
        }
        //convert into hashmap
        for (ArrayList a : dsPairs) {
            dsData.put((String) a.get(0), new ArrayList(a.subList(1, a.size())));
        }
        //parsing LCD below

        List<String[]> LCDAll = LCD.readAll();
        String[] LCDFirstLine = LCDAll.get(0); //column titles
        boolean[] include = new boolean[LCDFirstLine.length]; //whether to include column or not
        for (int i = 0; i < include.length; i++) {
            if (((ArrayList) dsData.get("COLUMN_NAME")).contains(LCDFirstLine[i])) {
                include[i] = true;
            }
        }
        ArrayList<Object>[] tempPairs = new ArrayList[LCDFirstLine.length]; //store temporary column name-value pairs
        for (int i = 0; i < tempPairs.length; i++) {//init arraylist array
            tempPairs[i] = new ArrayList<>();
        }
        for (String[] s : LCDAll) {
            for (int i = 0; i < s.length; i++) {
                if (include[i]) {
                    tempPairs[i].add(s[i]);
                }
            }
        }
        for (ArrayList a : tempPairs) {
            for (int i = 1; i < a.size(); i++) { //skip first value since that's the column name
                if (((ArrayList) dsData.get("DATA_TYPE")).get(((ArrayList) dsData.get("COLUMN_NAME")).indexOf(a.get(0))).equals("Integer")) {
                    if (Integer.parseInt((String) a.get(i)) >= 0) { //only allow valid values (that is, >=0)
                        a.set(i, Integer.parseInt((String) a.get(i)));
                    } else {
                        //not sure what to do otherwise, just put 0
                        a.set(i, 0);
                    }
                }
            }
        }
        //inefficient (multiple iterations over tempPairs)- fix?
        for (ArrayList a : tempPairs) {
            if (a.size() > 0) {
                csvData.put((String) a.get(0), new ArrayList(a.subList(1, a.size())));
            }
        }
    }

    public ArrayList parameters() {
        ArrayList temp = new ArrayList();
        for(int i = 0; i < dsData.get("COLUMN_NAME").size(); i++){
            if(dsData.get("IS_PERCENT_DATA").get(i).equals("Y")){
                temp.add(dsData.get("COLUMN_NAME").get(i));
            }
        }
        return temp;
    }

    public HashMap[] load() {
        return new HashMap[]{csvData, dsData};
    }

    public static void main(String[] args) throws IOException {
        preprocessing test = new preprocessing("C:/Users/techj/OneDrive/GitHub/CSE216/CSE216/Assignment 1/LEADINGCAUSESOFDEATH.csv", "C:/Users/techj/OneDrive/GitHub/CSE216/CSE216/Assignment 1/Dataset_info.csv");
        HashMap[] testing = test.load();
        Iterator iterator = testing[0].keySet().iterator();

        while (iterator.hasNext()) {
            String key = iterator.next().toString();
            String value = testing[0].get(key).toString();

            System.out.println(key + " " + value);
        }
    }
}
