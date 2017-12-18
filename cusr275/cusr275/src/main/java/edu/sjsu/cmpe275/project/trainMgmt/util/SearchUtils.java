package edu.sjsu.cmpe275.project.trainMgmt.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;




public class SearchUtils {
	
	public static int totalDurationInMinutes = 0, totalDurationFromStartAndFP = 0,totalNoOfStnsToTravel=0; 
	public static Character fromPlace = null,toPlace = null;
	public static String givenTrainTypeValue = "", trainTypeValue="";
	public static List<SearchCriteria> findAllSearchCriteria(List<SearchCriteria> params) throws ParseException{
		List<SearchCriteria> finalParams = new ArrayList<SearchCriteria>();
		String givenFromPlaceDeptTime="", direction="", finalStartDeptTime="";
		Character givenFromPlace = null, givenToPlace = null;
		int noOfStnsFromStartAndFP = 0, noOfStopsFromStartAndFP = 0, totalNoOfStopsToTravel=0;
		
			
		
		for (final SearchCriteria param : params) 
		 {
			 if (param.getKey().equals("From"))
				{
					String temp = ((String) param.getValue()).toUpperCase();
					givenFromPlace = (Character) temp.charAt(0);
				}
			 else if (param.getKey().equals("To"))
				{
				 	String temp = ((String) param.getValue()).toUpperCase();
				 	givenToPlace = (Character) temp.charAt(0);
				}
			 else if (param.getKey().equals("trainDeptTime")) 
			 	{
				 	givenFromPlaceDeptTime = (String)param.getValue();	 
			 	}
			 else if (param.getKey().equals("trainType")) 
				 givenTrainTypeValue = (String) param.getValue();
				 
				// Have to implement no of Passengers, roundtrip, exact time still
			 else
				 finalParams.add(new SearchCriteria(param.getKey(),param.getOperation(),param.getValue())); 
		 }
		 
		 
           System.out.println(givenFromPlace);
           System.out.println(givenToPlace);
           
	           //Find if fromPlace, to Place is an Express Stop and if given train type is any or express, then we can choose express
           if(findIfExpressStop(givenFromPlace) && findIfExpressStop(givenToPlace) && (givenTrainTypeValue.equals("Express") || givenTrainTypeValue.equals("Any")))
           {
           		fromPlace = givenFromPlace;
           		toPlace = givenToPlace;
        	   		trainTypeValue = "Express";
           		finalParams.add(new SearchCriteria("trainType","=",trainTypeValue));
           }
        
         // Split the train journey by express and regular
           else
           {
               //if givenFromPlace is express stop and givenToPlace (i.e the final destination) is less than 5 stations then take regular
        	   		if(findIfExpressStop(givenFromPlace))
        	   		{
        	   		 if (Math.abs((int)givenFromPlace - (int)givenToPlace) < 5 )
        	  			{
        	  				fromPlace = givenFromPlace;
        	          		toPlace = givenToPlace;
        	  				trainTypeValue = "Regular";
        	          		finalParams.add(new SearchCriteria("trainType","=",trainTypeValue));
        	  			}
        	   		}
           		
           }

           
           
           System.out.println(trainTypeValue);
           if(fromPlace != null && toPlace != null)
			{
	            direction = findDirection(fromPlace,toPlace);
	            
	            if(direction.equals("SB")) {
	            		totalNoOfStnsToTravel = noOfStations(toPlace,fromPlace);
		            totalNoOfStopsToTravel = noOfStops(totalNoOfStnsToTravel, trainTypeValue) - 1;
		            totalDurationInMinutes =  duration(totalNoOfStnsToTravel,totalNoOfStopsToTravel);
	            		noOfStnsFromStartAndFP = noOfStations(fromPlace,'A');
	            		finalParams.add(new SearchCriteria("trainID","=","SB"));
	            }
	            
	            if(direction.equals("NB")) {
	            		totalNoOfStnsToTravel = noOfStations(fromPlace,toPlace);
		            totalNoOfStopsToTravel = noOfStops(totalNoOfStnsToTravel, trainTypeValue) - 1;
		            totalDurationInMinutes =  duration(totalNoOfStnsToTravel,totalNoOfStopsToTravel);
	            		noOfStnsFromStartAndFP = noOfStations('Z',fromPlace);
	            		finalParams.add(new SearchCriteria("trainID","=","NB"));
	            }
	            
	            noOfStopsFromStartAndFP = noOfStops(noOfStnsFromStartAndFP, trainTypeValue);
       			totalDurationFromStartAndFP = duration(noOfStnsFromStartAndFP,noOfStopsFromStartAndFP);
       			finalStartDeptTime = getCorrectTime(givenFromPlaceDeptTime,totalDurationFromStartAndFP,false);
       			finalParams.add(new SearchCriteria("trainStartTime",">",finalStartDeptTime));
       			
			}
	       
		  return finalParams;
		}

	
	//Time helper function to get correct time (Departure time
    public static String getCorrectTime(String givenFromPlaceDeptTime, int TimeCalcInMinutes, boolean fromTrainController) throws ParseException {
    		DateFormat sdf = new SimpleDateFormat("HH:mm");
        Date date = sdf.parse(givenFromPlaceDeptTime);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        if(fromTrainController) {
        		cal.add(Calendar.MINUTE, TimeCalcInMinutes);
        		date = cal.getTime();
        }
        else {
        	cal.add(Calendar.MINUTE, -TimeCalcInMinutes);
    		date = cal.getTime();
        }
        
        String strDateFormat = "HH:mm";//HH --> means 24 hour format
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate= dateFormat.format(date);
        return formattedDate;
       
    }
    
 // To check south bound or north bound. If fromPlace is less than toPlace then its SB
    public static String findDirection(Character fromPlace,Character toPlace)
    {
    		String result="";
        	 if(fromPlace != null && toPlace != null)
			 {
				 if ((int)fromPlace < ((int)toPlace))
					 result = "SB";
				 else
					 result = "NB";			 
			 }
        	 return result;
    }
    
    // Find number of stations between two places
    public static int noOfStations(Character PlaceA,Character PlaceB) {
	    	int noOfStns = ((int)PlaceA - (int)PlaceB);
	    	return noOfStns; 
    }
    
 // Find number of stops between two places
    public static int noOfStops(int noOfStns, String trainTypeValue) {
	    	int noOfStops=-1;
	    	if(trainTypeValue.equals("Express"))
	    		noOfStops =  noOfStns/5;
	    	if(trainTypeValue.equals("Regular"))
	    		noOfStops = noOfStns;
	    	
	    	return noOfStops;
    }
    
    // Find time duration between two places
    public static int duration(int noOfStns, int noOfStops) {
	    	int duration = (noOfStns*5) + (noOfStops*3);
	    	return duration;
    }
    
    // Calculate the rate to travel
    public static long findRate(int noOfStns, String trainTypeValue)
    {
    		long rate = 0;
    		if(noOfStns <= 5) {
    			if(trainTypeValue.equals("Express")) 
    				rate = (long) (2+1);//1 is the transaction fee
    			else if(trainTypeValue.equals("Regular"))
    				rate = (long) (1+1);//1 is the transaction fee
    			}	
    		else {
    			noOfStns = (noOfStns/5)+1;
    			if(trainTypeValue.equals("Express")) 
    				rate = (long) ((noOfStns*2)+1);//1 is the transaction fee
    			else if(trainTypeValue.equals("Regular"))
    				rate = (long) ((noOfStns*1)+1);//1 is the transaction fee
    		}
    		return rate;
    	}
    
    //Find Express Stops or not
    public static boolean findIfExpressStop(Character PlaceA) {
    		if((int)PlaceA % 5 == 0)
			return true;
	
		return false;
    }

}
