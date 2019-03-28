package edu.sunyk.cse216.interaction;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import edu.sunyk.cse216.processing.preprocessing;
import edu.sunyk.cse216.statistics.computestats;


/**
 *
 * @author techj
 */
public class userinterface {
    public static final Map<String, String> STATE_MAP;
    static {
        STATE_MAP = new HashMap<String, String>();
        STATE_MAP.put("ALL", "ALL");
        STATE_MAP.put("AL", "Alabama");
        STATE_MAP.put("AK", "Alaska");
        STATE_MAP.put("AB", "Alberta");
        STATE_MAP.put("AZ", "Arizona");
        STATE_MAP.put("AR", "Arkansas");
        STATE_MAP.put("BC", "British Columbia");
        STATE_MAP.put("CA", "California");
        STATE_MAP.put("CO", "Colorado");
        STATE_MAP.put("CT", "Connecticut");
        STATE_MAP.put("DE", "Delaware");
        STATE_MAP.put("DC", "District Of Columbia");
        STATE_MAP.put("FL", "Florida");
        STATE_MAP.put("GA", "Georgia");
        STATE_MAP.put("GU", "Guam");
        STATE_MAP.put("HI", "Hawaii");
        STATE_MAP.put("ID", "Idaho");
        STATE_MAP.put("IL", "Illinois");
        STATE_MAP.put("IN", "Indiana");
        STATE_MAP.put("IA", "Iowa");
        STATE_MAP.put("KS", "Kansas");
        STATE_MAP.put("KY", "Kentucky");
        STATE_MAP.put("LA", "Louisiana");
        STATE_MAP.put("ME", "Maine");
        STATE_MAP.put("MB", "Manitoba");
        STATE_MAP.put("MD", "Maryland");
        STATE_MAP.put("MA", "Massachusetts");
        STATE_MAP.put("MI", "Michigan");
        STATE_MAP.put("MN", "Minnesota");
        STATE_MAP.put("MS", "Mississippi");
        STATE_MAP.put("MO", "Missouri");
        STATE_MAP.put("MT", "Montana");
        STATE_MAP.put("NE", "Nebraska");
        STATE_MAP.put("NV", "Nevada");
        STATE_MAP.put("NB", "New Brunswick");
        STATE_MAP.put("NH", "New Hampshire");
        STATE_MAP.put("NJ", "New Jersey");
        STATE_MAP.put("NM", "New Mexico");
        STATE_MAP.put("NY", "New York");
        STATE_MAP.put("NF", "Newfoundland");
        STATE_MAP.put("NC", "North Carolina");
        STATE_MAP.put("ND", "North Dakota");
        STATE_MAP.put("NT", "Northwest Territories");
        STATE_MAP.put("NS", "Nova Scotia");
        STATE_MAP.put("NU", "Nunavut");
        STATE_MAP.put("OH", "Ohio");
        STATE_MAP.put("OK", "Oklahoma");
        STATE_MAP.put("ON", "Ontario");
        STATE_MAP.put("OR", "Oregon");
        STATE_MAP.put("PA", "Pennsylvania");
        STATE_MAP.put("PE", "Prince Edward Island");
        STATE_MAP.put("PR", "Puerto Rico");
        STATE_MAP.put("QC", "Quebec");
        STATE_MAP.put("RI", "Rhode Island");
        STATE_MAP.put("SK", "Saskatchewan");
        STATE_MAP.put("SC", "South Carolina");
        STATE_MAP.put("SD", "South Dakota");
        STATE_MAP.put("TN", "Tennessee");
        STATE_MAP.put("TX", "Texas");
        STATE_MAP.put("UT", "Utah");
        STATE_MAP.put("VT", "Vermont");
        STATE_MAP.put("VI", "Virgin Islands");
        STATE_MAP.put("VA", "Virginia");
        STATE_MAP.put("WA", "Washington");
        STATE_MAP.put("WV", "West Virginia");
        STATE_MAP.put("WI", "Wisconsin");
        STATE_MAP.put("WY", "Wyoming");
        STATE_MAP.put("YT", "Yukon Territory");
    }
    public static void main(String[] args) throws IOException{
        String state = "Select Calculation";
        String stat = ""; //which statistic
        String calc = ""; //what will be calculated
        preprocessing csv = new preprocessing(args[0], args[1]);
        HashMap[] csvData = csv.load();
        computestats compute = new computestats(csvData);
        Scanner input = new Scanner(System.in);
        while(state.equals("Select Calculation")){
            int param = 0;
            System.out.println("Welcome! Select one of the following statistical parameters:\n" +
"1.	Mean\n" +
"2.	Median\n" +
"3.	Mode\n" +
"4.	Standard Deviation \n" +
"5.	Variance\n" +
"6.	Exit");
            System.out.println("User selects: ");
            try{
                param = input.nextInt();
                if(param < 1 || param > 6){
                    System.out.println("Invalid input!");
                }
                else{
                    switch (param) {
                        case 1:
                            calc = "mean value";
                            state = "Select Parameter";
                            break;
                        case 2:
                            calc = "median value";
                            state = "Select Parameter";
                            break;
                        case 3:
                            calc = "mode value";
                            state = "Select Parameter";
                            break;
                        case 4:
                            calc = "standard deviation";
                            state = "Select Parameter";
                            break;
                        case 5:
                            calc = "variance";
                            state = "Select Parameter";
                            break;
                        case 6:
                            System.exit(0);
                    }
                }
            }
            catch(Exception e){
                System.out.println("Invalid input!");
                input.next();
            }
            
        }
        while(state.equals("Select Parameter")){
            int param = 0;
            System.out.println("Select parameters of interest:");
            ArrayList parameters = csv.parameters();
            for(int i = 0; i < parameters.size(); i++){
                System.out.println((i+1)+". "+parameters.get(i));
            }
            System.out.println("User selects: ");
            try{
                param = input.nextInt();
                if(param < 1 || param > parameters.size()){
                    
                    System.out.println("Invalid input!");
                }
                else{
                    stat = (String) parameters.get(param - 1);
                    state = "Select State";
                }
            }
            catch(Exception e){
                System.out.println("Invalid input!");
                input.next();
            }
            
        }
        while(state.equals("Select State")){
            int param = 0;
            ArrayList<String> statesArray = compute.getStates();
            Collections.sort(statesArray);
            System.out.println("Select state code:");
            for(int i = 0; i < statesArray.size(); i++){
                System.out.println((i+1) + ". " + statesArray.get(i));
            }
            System.out.println("User selects: ");
            try{
                param = input.nextInt();
                if(param < 1 || param > statesArray.size()){
                    System.out.println("Invalid input!");
                }
                else{
                    double tempComp = 0;
                    switch(calc){
                        case "mean value":
                            double[] meanArray = compute.mean(stat, statesArray.get(param - 1));
                            double mean;
                            if(meanArray[1] > 0){
                                mean = meanArray[0]/meanArray[1];
                            }
                            else{
                                mean = 0;
                            }
                            tempComp = mean;
                            break;
                        case "median value":
                            tempComp = compute.median(stat, statesArray.get(param - 1));
                            break;
                        case "mode value":
                            tempComp = compute.mode(stat, statesArray.get(param - 1));
                            break;
                        case "standard deviation":
                            tempComp = compute.stdDev(stat, statesArray.get(param - 1));
                            break;
                        case "variance":
                            tempComp = compute.variance(stat, statesArray.get(param - 1));
                            break;

                    }
                    System.out.println("Output: The "+calc+" of the parameter "+stat+" for the state of "+userinterface.STATE_MAP.get(statesArray.get(param - 1))+" is "+tempComp+".");
                    System.exit(0);
                }
            }
            catch(Exception e){
                System.out.println("Invalid input!:"+e);
                input.next();
            }
            
        }
    }
}
