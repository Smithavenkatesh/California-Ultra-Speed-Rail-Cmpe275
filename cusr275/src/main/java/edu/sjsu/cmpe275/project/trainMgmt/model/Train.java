package edu.sjsu.cmpe275.project.trainMgmt.model;

import java.io.Serializable;

import javax.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@Entity
@Table(name = "train")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(allowGetters = true)
public class Train implements Serializable {
		
		@Id
		@GeneratedValue(strategy=GenerationType.TABLE)		
	 	@Column(name="trainID", nullable = false, unique = true)
	 	private String trainID;
	 	
	 	@Column(name="trainType")
	 	private String trainType;
	 	
	 	@Column(name="trainStartTime")
	 	private String trainStartTime;
	 	
	 	@Column(name="trainFareForFiveStn")
		private String trainFareForFiveStn;
	 	
	 	@Column(name="trainEndTime")
	 	private String trainEndTime;

		/**
		 * Default Constructor
		 */

		public Train() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		
		/**
		 * Parameterized Constructor
		 * @param trainType
		 * @param trainStartTime
		 * @param trainEndTime
		 */
		public Train(String trainType, String trainStartTime, String trainEndTime) {
			super();
			this.trainType = trainType;
			this.trainStartTime = trainStartTime;
			this.trainEndTime = trainEndTime;
		}
		
		
		public String getTrainID() {
			return trainID;
		}


		public String getTrainType() {
			return trainType;
		}


		public String getTrainStartTime() {
			return trainStartTime;
		}


		public String getTrainFareForFiveStn() {
			return trainFareForFiveStn;
		}
		
		public String getTrainEndTime() {
			return trainEndTime;
		}
		
		@Override
		public String toString() {
			return "Train [trainID=" + trainID + ", trainType=" + trainType + ", trainStartTime=" + trainStartTime
					+ ", trainFareForFiveStn=" + trainFareForFiveStn + ", trainEndTime=" + trainEndTime + "]";
		}

}

