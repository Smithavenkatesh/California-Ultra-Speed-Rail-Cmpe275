package edu.sjsu.cmpe275.project.trainMgmt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import edu.sjsu.cmpe275.project.trainMgmt.dao.ITrainDAO;
import edu.sjsu.cmpe275.project.trainMgmt.model.SearchedTrainResult;
import edu.sjsu.cmpe275.project.trainMgmt.model.Train;
import edu.sjsu.cmpe275.project.trainMgmt.util.SearchCriteria;
import edu.sjsu.cmpe275.project.trainMgmt.util.SearchUtils;

import javax.transaction.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Transactional
@Service
@RestController
@RequestMapping("/CUSR275")
public class TrainController {
	
		@Autowired
		private ITrainDAO trainDao;
		
		public static List<String> expressStops = Arrays.asList("A","F","K","P","U","Z");
		public static String resultType = "";
		// Default Constructor
	    public TrainController() {
			super();
		}

		@GetMapping("/train")
		@ResponseBody
		public ResponseEntity findAll(
			@RequestParam(value= "trainType", required = true) String trainType,
    			@RequestParam(value= "From", required = true) String From,
    			@RequestParam(value= "To", required = true) String To,
    			@RequestParam(value= "trainDeptTime", required = false)String trainDeptTime,
    			@RequestParam(value= "Date", required = false) String date,
    			@RequestParam(value= "noOfPassengers", required = false) String noOfPassengers,
    			@RequestParam(value= "exactTime", required = false) String exactTime,
    			@RequestParam(value= "roundTrip", required = false) String roundTrip,
    			@RequestParam(value= "numOfConnections", required = false) String numOfConnectionsString,
    			@RequestParam(value= "returnDate", required = false) String returnDate,
    			@RequestParam(value= "returnTime", required = false) String returnTime) throws ParseException{
			
			
			
			
	        List<SearchCriteria> params = new ArrayList<SearchCriteria>();
	        List<Train> trainList = new ArrayList<Train>();
	        List<SearchedTrainResult> resultList = new ArrayList<SearchedTrainResult>();
	        List<List<SearchedTrainResult>> finalConnectionsList= new ArrayList<List<SearchedTrainResult>>();
	        int numOfConnections = Integer.parseInt(numOfConnectionsString);
	         
			finalConnectionsList = findAllSearchCriteria(trainDeptTime,trainType,From,To,date,noOfPassengers,exactTime,roundTrip);
			ModelMap map = new ModelMap();
			ModelMap onwardMap = new ModelMap();
			onwardMap = buildJson(finalConnectionsList,numOfConnections,trainType);
			
			map.addAttribute("onwardJourney",onwardMap);
			
			if(roundTrip.equals("yes"))
			{
				List<List<SearchedTrainResult>> finalConnectionsListReturn= new ArrayList<List<SearchedTrainResult>>();
				finalConnectionsListReturn = findAllSearchCriteria(returnTime,trainType,To,From,date,noOfPassengers,exactTime,roundTrip);
				ModelMap returnMap = new ModelMap();
				returnMap = buildJson(finalConnectionsListReturn,numOfConnections,trainType);
				
				map.addAttribute("returnJourney",returnMap);
				
			}
	          
		        //return ResponseEntity.ok().body(resultList);
			 
			 return ResponseEntity.ok().body(map);
	    }
		
		public ModelMap buildJson(List<List<SearchedTrainResult>> finalConnectionsList, int numOfConnections, String trainType) throws ParseException
		{
			 ModelMap map = new ModelMap();
			 map.addAttribute("resultType",resultType);
			 ModelMap finalMap = new ModelMap();
			 //System.out.println(trainType);
			 //map.addAttribute("Connections", finalConnectionsList);
			 
			 if(resultType.equals("combined"))
			 {
				for(int i=0;i<finalConnectionsList.size();i++)
	    		{
					if(i<=9)
					{
						ModelMap map2 = new ModelMap();
						String deptTime = "",arrTime = "";
						int currNumOfConn = finalConnectionsList.get(i).size()-1;
						if(numOfConnections>=currNumOfConn)
						{
							map2.addAttribute("numOfConnections",finalConnectionsList.get(i).size()-1);
							map2.addAttribute("trainID","Multiple");
							deptTime = finalConnectionsList.get(i).get(0).getDeptTime();
							map2.addAttribute("deptTime",deptTime);
							if(finalConnectionsList.get(i).size()<3)
							{
								arrTime = finalConnectionsList.get(i).get(1).getArrTime();
								map2.addAttribute("arrTime",arrTime);
							}
							else
							{
								arrTime = finalConnectionsList.get(i).get(2).getArrTime();
								map2.addAttribute("arrTime",arrTime);
							}
							map2.addAttribute("date",finalConnectionsList.get(i).get(0).getDate());
							map2.addAttribute("from",finalConnectionsList.get(i).get(0).getFrom());
							
							if(finalConnectionsList.get(i).size()<3)
							{
								map2.addAttribute("to",finalConnectionsList.get(i).get(1).getTo());
								map2.addAttribute("rate",finalConnectionsList.get(i).get(0).getRate() + finalConnectionsList.get(i).get(1).getRate() + 1);
								
							}
							else
							{
								map2.addAttribute("to",finalConnectionsList.get(i).get(2).getTo());
								map2.addAttribute("rate",finalConnectionsList.get(i).get(0).getRate() + finalConnectionsList.get(i).get(1).getRate()+ finalConnectionsList.get(i).get(2).getRate() + 1);
								
							}
							int duration = getDifferenceInTime(deptTime, arrTime);
							map2.addAttribute("totalDuration",duration);
							map2.addAttribute("noOfPassengers",finalConnectionsList.get(i).get(0).getNoOfPassengers());
							map2.addAttribute("trainType","combined"); 
							map2.addAttribute("connections",finalConnectionsList.get(i));
							
							//System.out.println(i);
							finalMap.addAttribute(Integer.toString(i),map2);
						}
					}
					else
					{
						if(!trainType.equals("Express"))
       	    		{
					 	 finalMap.addAttribute("regular",finalConnectionsList.get(i));
       	    		}
					}
	    		}
				
				map.addAttribute("results",finalMap);
			 }
			 else
			 { 
				for(int i=0;i<finalConnectionsList.size();i++)
				    {
						map.addAttribute("results",finalConnectionsList.get(i));
					}
			 }
			 
			 return map;
			
		}
		
		
		public List<List<SearchedTrainResult>> findAllSearchCriteria(String trainDeptTime,
				String trainType,
				String From,
				String To,
				String date,
				String noOfPassengers,
				String exactTime,
				String roundTrip
				) throws ParseException{
			
			List<SearchCriteria> finalParams = new ArrayList<SearchCriteria>();
			Character fromPlace = null,toPlace = null;
			String trainTypeValue="";
			String givenFromPlaceDeptTime="", direction="";
			List<SearchedTrainResult> resultList = new ArrayList<SearchedTrainResult>();
			Character previousExpressStation = null;
			Character nextExpressStation = null;
			List<List<SearchedTrainResult>> connectionsList= new ArrayList<List<SearchedTrainResult>>();
			
			String temp = ((String) From).toUpperCase();
			fromPlace = (Character) temp.charAt(0);
			
			String temp2 = ((String) To).toUpperCase();
		 	toPlace = (Character) temp2.charAt(0);
		 	
		 	givenFromPlaceDeptTime = trainDeptTime;
		 	
		 	trainTypeValue = trainType;
			
			 	           
	        if(fromPlace != null && toPlace != null)
				{
	        	   resultType = "notcombined";
	        	   switch(trainTypeValue) {
			        	    
	        	   
	        	   			case "Regular":
		       	    		//User selected either express or regular so give only that
			       	    		direction = findDirection(fromPlace,toPlace);
			       	    		finalParams.clear();
			       	    		resultList = findTrainDetailsByType(direction,toPlace,fromPlace,trainTypeValue,givenFromPlaceDeptTime,finalParams,resultList,Character.toString(fromPlace),Character.toString(toPlace),date, noOfPassengers,10);
			       	    		connectionsList.add(resultList);
			       	    		break;
			        	    default:
			        	    	//Can be only express or only regular or both based on origin and destination
			        	    	
			        	    	//Both start and end are express stations. So the results should contain individual express and trains without combinations
			        	    	if(expressStops.contains(From) && expressStops.contains(To))
			        	    	{
			        	    		direction = findDirection(fromPlace,toPlace);
			        	    		//find only express and add to result
			        	    		resultList = findTrainDetailsByType(direction,toPlace,fromPlace,"Express",givenFromPlaceDeptTime,finalParams,resultList,Character.toString(fromPlace),Character.toString(toPlace),date, noOfPassengers,10);
			        	    		if(!trainTypeValue.equals("Express"))
			        	    		{
				        	    		finalParams.clear();
				        	    		//find only regular and add to result
				        	    		resultList = findTrainDetailsByType(direction,toPlace,fromPlace,"Regular",givenFromPlaceDeptTime,finalParams,resultList,Character.toString(fromPlace),Character.toString(toPlace),date, noOfPassengers,10);
				        	    		
			        	    		}
			        	    		connectionsList.add(resultList);
			        	    	}
			        	    	
			        	    	//only one station is express.
			        	    	else if(expressStops.contains(From) || expressStops.contains(To))
			        	    	{
			        	    		resultType = "combined";
			        	    		Boolean ReverseCovered = false;
			        	    		//starting station is express. 
			        	    		//find last Express station before destination.
			        	    		if(expressStops.contains(From))
			        	    		{
			        	    			direction = findDirection(fromPlace,toPlace);
			        	    			previousExpressStation = findPreviousExpressStation(toPlace,direction);
			        	    			if(previousExpressStation==fromPlace)
			        	    			{
			        	    				previousExpressStation = findNextExpressStation(toPlace,direction);
			        	    				ReverseCovered = true;
			        	    			}
			        	    			resultList = findTrainDetailsByType(direction,previousExpressStation,fromPlace,"Express",givenFromPlaceDeptTime,finalParams,resultList,Character.toString(fromPlace),Character.toString(previousExpressStation),date, noOfPassengers,8);
				        	    		finalParams.clear();
				        	    		for(int i=0;i<resultList.size();i++)
				        	    		{
				        	    			List<SearchedTrainResult> individualConnections = new ArrayList<SearchedTrainResult>();
				        	    			List<SearchedTrainResult> resultList2 = new ArrayList<SearchedTrainResult>();
				        	    			direction = findDirection(previousExpressStation,toPlace);
				        	    			resultList2 = findTrainDetailsByType(direction,toPlace,previousExpressStation,"Regular",resultList.get(i).getArrTime(),finalParams,resultList2,Character.toString(previousExpressStation),Character.toString(toPlace),date, noOfPassengers,1);
				        	    			finalParams.clear();
				        	    			individualConnections.add(resultList.get(i));
				        	    			individualConnections.add(resultList2.get(0));
				        	    			connectionsList.add(individualConnections);
				        	    		}
				        	    		
				        	    		if(!ReverseCovered)
				        	    		{
				        	    			ReverseCovered = false;
				        	    			direction = findDirection(fromPlace,toPlace);
				        	    			previousExpressStation = findNextExpressStation(toPlace,direction);
				        	    			direction = findDirection(fromPlace,previousExpressStation);
				        	    			resultList.clear();
				        	    			resultList = findTrainDetailsByType(direction,previousExpressStation,fromPlace,"Express",givenFromPlaceDeptTime,finalParams,resultList,Character.toString(fromPlace),Character.toString(previousExpressStation),date, noOfPassengers,2);
					        	    		finalParams.clear();
					        	    		for(int i=0;i<resultList.size();i++)
					        	    		{
					        	    			List<SearchedTrainResult> individualConnections = new ArrayList<SearchedTrainResult>();
					        	    			List<SearchedTrainResult> resultList2 = new ArrayList<SearchedTrainResult>();
					        	    			direction = findDirection(previousExpressStation,toPlace);
					        	    			resultList2.clear();
					        	    			resultList2 = findTrainDetailsByType(direction,toPlace,previousExpressStation,"Regular",resultList.get(i).getArrTime(),finalParams,resultList2,Character.toString(previousExpressStation),Character.toString(toPlace),date, noOfPassengers,1);
					        	    			finalParams.clear();
					        	    			individualConnections.add(resultList.get(i));
					        	    			individualConnections.add(resultList2.get(0));
					        	    			connectionsList.add(individualConnections);
					        	    		}
				        	    		}
			        	    		}
			        	    		//Ending station is express. 
			        	    		//find Next Express station after start.
			        	    		else
			        	    		{
			        	    			direction = findDirection(fromPlace,toPlace);
			        	    			nextExpressStation = findNextExpressStation(fromPlace,direction);
			        	    			direction = findDirection(fromPlace,nextExpressStation);
			        	    			resultList = findTrainDetailsByType(direction,nextExpressStation,fromPlace,"Regular",givenFromPlaceDeptTime,finalParams,resultList,Character.toString(fromPlace),Character.toString(nextExpressStation),date, noOfPassengers,10);
				        	    		finalParams.clear();
				        	    		for(int i=0;i<resultList.size();i++)
				        	    		{
				        	    			List<SearchedTrainResult> individualConnections = new ArrayList<SearchedTrainResult>();
				        	    			List<SearchedTrainResult> resultList2 = new ArrayList<SearchedTrainResult>();
				        	    			direction = findDirection(nextExpressStation,toPlace);
				        	    			resultList2 = findTrainDetailsByType(direction,toPlace,nextExpressStation,"Express",resultList.get(i).getArrTime(),finalParams,resultList2,Character.toString(nextExpressStation),Character.toString(toPlace),date, noOfPassengers,1);
				        	    			finalParams.clear();
				        	    			individualConnections.add(resultList.get(i));
				        	    			individualConnections.add(resultList2.get(0));
				        	    			connectionsList.add(individualConnections);
				        	    		}
				        	    		
			        	    		}
			        	    		
			        	    		if(!trainTypeValue.equals("Express"))
			        	    		{
				        	    		resultType = "not combined";
				        	    		resultList.clear();
				        	    		direction = findDirection(fromPlace,toPlace);
				        	    		resultList = findTrainDetailsByType(direction,toPlace,fromPlace,"Regular",givenFromPlaceDeptTime,finalParams,resultList,Character.toString(fromPlace),Character.toString(toPlace),date, noOfPassengers,10);
				        	    		resultType = "combined";
				        	    		connectionsList.add(resultList);
			        	    		}
			        	    		
			        	    	}
			        	    	//Both the stations are not express stations
			        	    	else
			        	    	{
			        	    		resultType = "not combined";
			        	    		//Atleast one express train in between
			        	    		if(isExpressStationInBetween(fromPlace,toPlace))
			        	    		{
			        	    			direction = findDirection(fromPlace,toPlace);
			        	    			previousExpressStation = findPreviousExpressStation(toPlace,direction);
			        	    			nextExpressStation = findNextExpressStation(fromPlace,direction);
			        	    			resultType = "combined";
		        	    				direction = findDirection(fromPlace,nextExpressStation);
		        	    				finalParams.clear();
			        	    			resultList = findTrainDetailsByType(direction,nextExpressStation,fromPlace,"Regular",givenFromPlaceDeptTime,finalParams,resultList,Character.toString(fromPlace),Character.toString(nextExpressStation),date, noOfPassengers,10);
				        	    		
			        	    			//take regular, express and again regular
			        	    			if(previousExpressStation == nextExpressStation)
			        	    			{
			        	    				previousExpressStation = findNextExpressStation(toPlace,direction);
			        	    			}
			        	    			for(int i=0;i<resultList.size();i++)
					        	    	{
					        	    			List<SearchedTrainResult> individualConnections = new ArrayList<SearchedTrainResult>();
					        	    			List<SearchedTrainResult> resultList2 = new ArrayList<SearchedTrainResult>();
					        	    			List<SearchedTrainResult> resultList3= new ArrayList<SearchedTrainResult>();
					        	    			finalParams.clear();
					        	    			direction = findDirection(nextExpressStation,previousExpressStation);
					        	    			resultList2 = findTrainDetailsByType(direction,previousExpressStation,nextExpressStation,"Express",resultList.get(i).getArrTime(),finalParams,resultList2,Character.toString(nextExpressStation),Character.toString(previousExpressStation),date, noOfPassengers,1);
					        	    			finalParams.clear();
					        	    			direction = findDirection(previousExpressStation,toPlace);
					        	    			resultList3 = findTrainDetailsByType(direction,toPlace,previousExpressStation,"Regular",resultList2.get(0).getArrTime(),finalParams,resultList3,Character.toString(previousExpressStation),Character.toString(toPlace),date, noOfPassengers,1);
					        	    			individualConnections.add(resultList.get(i));
					        	    			individualConnections.add(resultList2.get(0));
					        	    			individualConnections.add(resultList3.get(0));
					        	    			connectionsList.add(individualConnections);
					        	    	}
			        	    			
			        	    			
			        	    		}
			        	    		
			        	    		if(!trainTypeValue.equals("Express"))
			        	    		{
				        	    		direction = findDirection(fromPlace,toPlace);
			        	    			resultList.clear();
			        	    			finalParams.clear();
				        	    		resultList = findTrainDetailsByType(direction,toPlace,fromPlace,"Regular",givenFromPlaceDeptTime,finalParams,resultList,Character.toString(fromPlace),Character.toString(toPlace),date, noOfPassengers,10);
				        	    		connectionsList.add(resultList);
				        	    	}
			        	    		
			        	    	}
			        	    	
			        		   
			        	    
						}
				}
	           return connectionsList;
		}
		
		public List<SearchedTrainResult> findTrainDetailsByType(String direction,Character toPlace,Character fromPlace,String trainTypeValue,String givenFromPlaceDeptTime
				,List<SearchCriteria> finalParams,List<SearchedTrainResult> resultList,String From, String To, String date, String noOfPassengers, int resLength) throws ParseException{
				int totalDurationInMinutes = 0, totalDurationFromStartAndFP = 0,totalNoOfStnsToTravel=0; 
				String finalStartDeptTime="";
				int noOfStnsFromStartAndFP = 0, noOfStopsFromStartAndFP = 0, totalNoOfStopsToTravel=0;
				 
				finalParams.add(new SearchCriteria("trainType","=",trainTypeValue));
				//System.out.println(trainTypeValue);
				if(direction.equals("SB"))
				{
		         		totalNoOfStnsToTravel = noOfStations(toPlace,fromPlace);
		         	    totalNoOfStopsToTravel = noOfStops(totalNoOfStnsToTravel, trainTypeValue);
				        totalDurationInMinutes =  duration(totalNoOfStnsToTravel,totalNoOfStopsToTravel);
				        noOfStnsFromStartAndFP = noOfStations(fromPlace,'A');
		         		finalParams.add(new SearchCriteria("trainID","=","SB"));
		        }
		         
		        if(direction.equals("NB")) 
		        {
		         		totalNoOfStnsToTravel = noOfStations(fromPlace,toPlace);
				        totalNoOfStopsToTravel = noOfStops(totalNoOfStnsToTravel, trainTypeValue);
				        totalDurationInMinutes =  duration(totalNoOfStnsToTravel,totalNoOfStopsToTravel);
		         		noOfStnsFromStartAndFP = noOfStations('Z',fromPlace);
		         		finalParams.add(new SearchCriteria("trainID","=","NB"));
		         }
		         
		        noOfStopsFromStartAndFP = noOfStops(noOfStnsFromStartAndFP, trainTypeValue);
		        totalDurationFromStartAndFP = duration(noOfStnsFromStartAndFP,noOfStopsFromStartAndFP);
				finalStartDeptTime = getCorrectTime(givenFromPlaceDeptTime,totalDurationFromStartAndFP,false);
		 		finalParams.add(new SearchCriteria("trainStartTime",">",finalStartDeptTime));
		 		//System.out.println(finalStartDeptTime);
		 		resultList = addToResultSet(finalParams,resultList,totalDurationFromStartAndFP,totalDurationInMinutes,totalNoOfStnsToTravel,From,To,date, noOfPassengers,resLength);
		 		return resultList;
		}
		
		public List<SearchedTrainResult> addToResultSet(List<SearchCriteria> finalParams,List<SearchedTrainResult> resultList,int totalDurationFromStartAndFP,int totalDurationInMinutes,
				int totalNoOfStnsToTravel, String From, String To, String date, String noOfPassengers,int resLength) throws ParseException{      
	           
	           	List<Train> trainList = new ArrayList<Train>();
	           	trainList = trainDao.searchTrain(finalParams);
	           	//System.out.println(trainList.size());
		        int lenOfListTrains = (resLength<trainList.size())?resLength:trainList.size();
		        
		        for(int i=0;i<lenOfListTrains;i++)
		        {
		        		SearchedTrainResult searchedTrain = new SearchedTrainResult();
		        		String trainDeptTimeFromPlace = getCorrectTime(trainList.get(i).getTrainStartTime(),totalDurationFromStartAndFP,true);
		        		String trainArrTimeToPlace = getCorrectTime(trainDeptTimeFromPlace,totalDurationInMinutes,true);
		        		int noOfStn = totalNoOfStnsToTravel;
		        		
		        		searchedTrain.setTrainId(trainList.get(i).getTrainID());
		        		searchedTrain.setDeptTime(trainDeptTimeFromPlace);
		        		searchedTrain.setArrTime(trainArrTimeToPlace);
		        		searchedTrain.setFrom(From);
		        		searchedTrain.setTo(To );
		        		searchedTrain.setDate(date);
		        		searchedTrain.setTrainType(trainList.get(i).getTrainType());
		        		searchedTrain.setTotalDuration(totalDurationInMinutes);
		        		searchedTrain.setNoOfPassengers(noOfPassengers);
		        		searchedTrain.setRate(findRate(noOfStn,trainList.get(i).getTrainType()));
		        		resultList.add(searchedTrain);
		        }   
			  return resultList;
			}
		
		public int getDifferenceInTime(String startTime, String endTime) throws ParseException{
			DateFormat sdf = new SimpleDateFormat("HH:mm");
	        Date date = sdf.parse(startTime);
	        Date date2 = sdf.parse(endTime);
	        int diff = (int) (date2.getTime()-date.getTime())/60000;
	        return diff;	
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
		    	int noOfStops=0;
		    	if(trainTypeValue.equals("Express"))
		    		noOfStops =  (int) Math.ceil((double)noOfStns/5)-1;
		    	if(trainTypeValue.equals("Regular"))
		    		noOfStops = noOfStns-1;
		    	noOfStops = noOfStops<=0? 0 : noOfStops;
		    	return noOfStops;
	    }
	    
	    public boolean isExpressStationInBetween(Character From,Character To)
	    {
	    	List<Character> expressStopsChars = Arrays.asList('A','F','K','P','U','Z');
	    	int fromstation = (int) From - 64;
	    	int tostation = (int) To - 64;
	    	
	    	if(((int) Math.ceil((double)From/5)) == ((int) Math.ceil((double)To/5)))
	    	{
	    		return false;
	    	}
	    	return true;
	    }
	    
	    public Character findPreviousExpressStation(Character To, String direction)
	    {
	    	List<Character> expressStopsChars = Arrays.asList('A','F','K','P','U','Z');
	    	int station  = (int) To - 64;
	    	int prevExpStation = 0;
	    	if(direction=="SB")
	    	{
	    		prevExpStation = ((int) Math.ceil((double)station/5))-1;
	    	}
	    	else
	    	{
	    		prevExpStation = ((int) Math.ceil((double)station/5));
	    	}
	    	return expressStopsChars.get(prevExpStation);
	    }
	    
	    public Character findNextExpressStation(Character From, String direction)
	    {
	    	List<Character> expressStopsChars = Arrays.asList('A','F','K','P','U','Z');
	    	int station  = (int) From - 64;
	    	int nextExpStation = 0;
	    	if(direction=="SB")
	    	{
	    		nextExpStation = ((int) Math.ceil((double)station/5));
	    	}
	    	else
	    	{
	    		nextExpStation = ((int) Math.ceil((double)station/5))-1;
	    	}
	    	return expressStopsChars.get(nextExpStation);
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
	    				rate = (long) (2);//1 is the transaction fee
	    			else if(trainTypeValue.equals("Regular"))
	    				rate = (long) (1);//1 is the transaction fee
	    			}	
	    		else {
	    			noOfStns = ((int) Math.ceil((double)noOfStns/5));
	    			if(trainTypeValue.equals("Express")) 
	    				rate = (long) ((noOfStns*2));//1 is the transaction fee
	    			else if(trainTypeValue.equals("Regular"))
	    				rate = (long) ((noOfStns*1));//1 is the transaction fee
	    		}
	    		rate = resultType.equals("combined")? rate : rate+1;
	    		return rate;
	    	}
	    
	    //Find Express Stops or not
	    public static boolean findIfExpressStop(Character PlaceA) {
	    		if((int)PlaceA % 5 == 0)
				return true;
		
			return false;
	    }

	}

		
	
	    
	    

