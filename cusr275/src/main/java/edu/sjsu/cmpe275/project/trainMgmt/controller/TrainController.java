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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


@Transactional
@Service
@RestController
@RequestMapping("/CUSR275")
public class TrainController {
	
		@Autowired
		private ITrainDAO trainDao;
		
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
    			@RequestParam(value= "roundTrip", required = false) String roundTrip) throws ParseException{
   
	        List<SearchCriteria> params = new ArrayList<SearchCriteria>();
	        List<Train> trainList = new ArrayList<Train>();
	        List<SearchedTrainResult> resultList = new ArrayList<SearchedTrainResult>();
	        
	        
			params.add(new SearchCriteria("trainType","=",trainType));
			params.add(new SearchCriteria("From","=",From));
			params.add(new SearchCriteria("To","=",To));
			if(trainDeptTime != null)
				params.add(new SearchCriteria("trainDeptTime","=",trainDeptTime));
			
	        
	        List<SearchCriteria> finalParams = SearchUtils.findAllSearchCriteria(params);
	        trainList = trainDao.searchTrain(finalParams);
	        int lenOfListTrains = (10<trainList.size())?10:trainList.size();
	        
	        for(int i=0;i<lenOfListTrains;i++)
	        {
	        		SearchedTrainResult searchedTrain = new SearchedTrainResult();
	        		String trainDeptTimeFromPlace = SearchUtils.getCorrectTime(trainList.get(i).getTrainStartTime(),SearchUtils.totalDurationFromStartAndFP,true);
	        		String trainArrTimeToPlace = SearchUtils.getCorrectTime(trainDeptTimeFromPlace,SearchUtils.totalDurationInMinutes,true);
	        		int noOfStn = SearchUtils.totalNoOfStnsToTravel;
	        		
	        		searchedTrain.setTrainId(trainList.get(i).getTrainID());
	        		searchedTrain.setDeptTime(trainDeptTimeFromPlace);
	        		searchedTrain.setArrTime(trainArrTimeToPlace);
	        		searchedTrain.setFrom(From);
	        		searchedTrain.setTo(To );
	        		searchedTrain.setDate(date);
	        		searchedTrain.setTrainType(SearchUtils.trainTypeValue);
	        		searchedTrain.setTotalDuration(SearchUtils.totalDurationInMinutes);
	        		searchedTrain.setNoOfPassengers(noOfPassengers);
	        		searchedTrain.setRate(SearchUtils.findRate(noOfStn,trainType));
	        		resultList.add(searchedTrain);
	        		
	        }
	        return ResponseEntity.ok().body(resultList);
	    }
		
	}
	    
	    


	    
	    

