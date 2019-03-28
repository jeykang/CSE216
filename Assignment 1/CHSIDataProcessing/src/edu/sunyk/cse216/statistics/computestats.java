/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sunyk.cse216.statistics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import edu.sunyk.cse216.processing.preprocessing;

/**
 *
 * @author techj
 */
public class computestats {
    HashMap csvData;
    HashMap dsData;
    Set<String> states;
    ArrayList<String> stateRd;
    ArrayList<String> statesArray;
    HashMap stateStats;
    public computestats(HashMap[] processed){
        csvData = processed[0];
        dsData = processed[1];
        states = new HashSet((ArrayList) csvData.get("CHSI_State_Abbr"));
        stateRd = (ArrayList) csvData.get("CHSI_State_Abbr");
        statesArray = new ArrayList(states);
        statesArray.add("ALL");
    }
    public ArrayList<String> getStates(){
        return statesArray;
    }
    public double[] mean(String statName, String stateCode){
        double total = 0;
        int count = 0;
        if(!stateCode.equals("ALL")){
            for(int i = 0; i < stateRd.size(); i++){
                if(stateRd.get(i).equals(stateCode)){
                    total += (Integer) ((ArrayList) csvData.get(statName)).get(i);
                    if((Integer) ((ArrayList) csvData.get(statName)).get(i) > 0){ //ignore invalid values
                        count++;
                    }
                }
            }
        }
        else{
            for(String s: statesArray){
                if(!s.equals("ALL")){
                    double[] temp = mean(statName, s);
                    total += temp[0];
                    count += temp[1];
                }
            }
        }
        return new double[]{total, count};
    }
    public int median(String statName, String stateCode){
        ArrayList allStats = new ArrayList();
        if(!stateCode.equals("ALL")){
            for(int i = 0; i < stateRd.size(); i++){
                if(stateRd.get(i).equals(stateCode)){
                    if((Integer) ((ArrayList) csvData.get(statName)).get(i) > 0){ //ignore invalid values
                        allStats.add((Integer) ((ArrayList) csvData.get(statName)).get(i));
                    }
                }
            }
        }
        else{
            for(String s: statesArray){
                if(!s.equals("ALL")){
                    for(int i = 0; i < stateRd.size(); i++){
                        if(stateRd.get(i).equals(s)){
                            if((Integer) ((ArrayList) csvData.get(statName)).get(i) > 0){ //ignore invalid values
                                allStats.add((Integer) ((ArrayList) csvData.get(statName)).get(i));
                            }
                        }
                    }
                }
            }
        }
        Collections.sort(allStats);
        if(allStats.size() > 0){
            return (int) allStats.get(allStats.size()/2);   
        }
        return 0;
    }
    public int mode(String statName, String stateCode){
        ArrayList allStats = new ArrayList();
        if(!stateCode.equals("ALL")){
            for(int i = 0; i < stateRd.size(); i++){
                if(stateRd.get(i).equals(stateCode)){
                    if((Integer) ((ArrayList) csvData.get(statName)).get(i) > 0){ //ignore invalid values
                        allStats.add((Integer) ((ArrayList) csvData.get(statName)).get(i));
                    }
                }
            }
        }
        else{
            for(String s: statesArray){
                if(!s.equals("ALL")){
                    for(int i = 0; i < stateRd.size(); i++){
                        if(stateRd.get(i).equals(s)){
                            if((Integer) ((ArrayList) csvData.get(statName)).get(i) > 0){ //ignore invalid values
                                allStats.add((Integer) ((ArrayList) csvData.get(statName)).get(i));
                            }
                        }
                    }
                }
            }
        }
        //same as median up until there
        //now iterate over allstats to find most common
        int freq = 0;
        int mode = 0;
        for(Object o: allStats){
            if(Collections.frequency(allStats, o) > freq){
                mode = (int) o;
                freq = Collections.frequency(allStats, o);
            }
        }
        return mode;
    }
    public double stdDev(String statName, String stateCode){
        return Math.sqrt(variance(statName, stateCode));
    }
    public double variance(String statName, String stateCode){
        double sum = 0;
        double[] meanArray = mean(statName, stateCode);
        double mean;
        if(meanArray[1] > 0){
            mean = meanArray[0]/meanArray[1]; 
        }
        else{
            mean = 0;
        }
        int count = 0;
        if(!stateCode.equals("ALL")){
            for(int i = 0; i < stateRd.size(); i++){
                if(stateRd.get(i).equals(stateCode)){
                    if((Integer) ((ArrayList) csvData.get(statName)).get(i) > 0){ //ignore invalid values
                        count++;
                        sum += Math.pow((Integer) ((ArrayList) csvData.get(statName)).get(i) - mean, 2);
                    }
                }
            }
        }
        else{
            for(String s: statesArray){
                if(!s.equals("ALL")){
                    for(int i = 0; i < stateRd.size(); i++){
                        if(stateRd.get(i).equals(s)){
                            if((Integer) ((ArrayList) csvData.get(statName)).get(i) > 0){ //ignore invalid values
                                count++;
                                sum += Math.pow((Integer) ((ArrayList) csvData.get(statName)).get(i) - mean, 2);
                            }
                        }
                    }
                }
            }
        }
        if(count > 0){
            sum /= count;
        }
        return sum;
    }
    public static void main(String[] args) throws IOException{
        preprocessing test = new preprocessing("C:/Users/techj/OneDrive/GitHub/CSE216/CSE216/Assignment 1/LEADINGCAUSESOFDEATH.csv", "C:/Users/techj/OneDrive/GitHub/CSE216/CSE216/Assignment 1/Dataset_info.csv");
        HashMap[] testing = test.load();
        computestats testcomp = new computestats(testing);
        System.out.println(testcomp.stdDev("A_Wh_Comp", "FL"));
    }
}
