package edu.sjsu.cmpe275.project.trainMgmt.model;

public class SearchedTrainResult {
	
	String trainId;
	String deptTime;
	String arrTime;
	String date;
	String from;
	String to;
	String noOfPassengers;
	long rate;
	String trainType;
	int totalDuration;



	public SearchedTrainResult() {
		super();
		// TODO Auto-generated constructor stub
	}


	public SearchedTrainResult(String trainId, String deptTime, String arrTime, String date, String from, String to,
			String noOfPassengers, long rate, String trainType, int totalDuration) {
		super();
		this.trainId = trainId;
		this.deptTime = deptTime;
		this.arrTime = arrTime;
		this.date = date;
		this.from = from;
		this.to = to;
		this.noOfPassengers = noOfPassengers;
		this.rate = rate;
		this.trainType = trainType;
		this.totalDuration = totalDuration;
	}


	public int getTotalDuration() {
		return totalDuration;
	}


	public void setTotalDuration(int totalDurationInMinutes) {
		this.totalDuration = totalDurationInMinutes;
	}


	public String getTrainId() {
		return trainId;
	}


	public void setTrainId(String trainId) {
		this.trainId = trainId;
	}


	public String getDeptTime() {
		return deptTime;
	}


	public void setDeptTime(String deptTime) {
		this.deptTime = deptTime;
	}


	public String getArrTime() {
		return arrTime;
	}


	public void setArrTime(String arrTime) {
		this.arrTime = arrTime;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getFrom() {
		return from;
	}


	public void setFrom(String from) {
		this.from = from;
	}


	public String getTo() {
		return to;
	}


	public void setTo(String to) {
		this.to = to;
	}


	public String getNoOfPassengers() {
		return noOfPassengers;
	}


	public void setNoOfPassengers(String noOfPassengers) {
		this.noOfPassengers = noOfPassengers;
	}


	public long getRate() {
		return rate;
	}


	public void setRate(long rate) {
		this.rate = rate;
	}


	public String getTrainType() {
		return trainType;
	}


	public void setTrainType(String trainType) {
		this.trainType = trainType;
	}
	
	

}
