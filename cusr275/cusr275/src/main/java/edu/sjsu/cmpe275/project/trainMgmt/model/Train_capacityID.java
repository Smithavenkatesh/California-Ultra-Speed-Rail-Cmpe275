package edu.sjsu.cmpe275.project.trainMgmt.model;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
public class Train_capacityID implements Serializable{
   
   @Column(name = "tid")
   private String tid;
   
   @Column(name = "date")
   private String date;
   
   public Train_capacityID() {
		super();
		// TODO Auto-generated constructor stub
   }
   
   public Train_capacityID(String tid, String date) {
	   super();
	   this.tid = tid;
	   this.date = date;
   }
   
   public String getTid() {
		return tid;
   }
  
   public void setTid(String tid) {
		this.tid = tid;
   }
  
   public String getDate() {
		return date;
   }
   
   public void setDate(String date) {
		this.date = date;
   }
   
   
}