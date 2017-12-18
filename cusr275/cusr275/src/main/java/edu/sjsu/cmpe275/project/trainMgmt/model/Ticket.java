package edu.sjsu.cmpe275.project.trainMgmt.model;

import java.io.Serializable;

import javax.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Model class
@Entity
@Table(name="ticket")
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ticketid", nullable = false)
 	private long ticketid;
	
 	@Column(name="from_station", nullable = false)
 	private String from_station;
 	
 	@Column(name="to_station", nullable = false)
 	private String to_station;
 	
 	@Column(name="trainid", nullable = false)
 	private String trainid;
 	
 	@Column(name="date", nullable = false)
 	private String date;
 	
 	@Column(name="start_time", nullable = false)
 	private String start_time;
 	
 	@Column(name="end_time", nullable = false)
    private String end_time;
 	
 	@Column(name="type", nullable = false)
    private String type;
	
 	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transactionid", nullable = false)
	private Reservation reservation;
	
    public Reservation getReservation() {
		return reservation;
	}
	
	public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
	/**
	 * Default Constructor
	 */
	public Ticket() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Parameterized Constructor
	 * @param from_station
	 * @param to_station
	 * @param price
	*/
	public Ticket(Long ticketid,String from_station, String to_station, String trainid, String date, String start_time,String end_time,String type) {
		super();
		this.ticketid = ticketid;
		this.from_station = from_station;
		this.to_station = to_station;
		this.trainid = trainid;
		this.date = date;
		this.start_time = start_time;
		this.end_time = end_time;
		this.type = type;
		
	}
	
	public Long getTicketid() {
		return ticketid;
	}
	
	public void setTicketid(Long ticketid) {
		this.ticketid = ticketid;
	}
	
	public String getFrom_station() {
		return from_station;
	}
	public void setFrom_station(String from_station) {
		this.from_station = from_station;
	}
	public String getTo_station() {
		return to_station;
	}
	public void setTo_station(String to_station) {
		this.to_station = to_station;
	}
	
	public String getTrainid() {
		return trainid;
	}
	public void setTrainid(String trainid) {
		this.trainid = trainid;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "ticket [ticketid=" + ticketid + ", from_station=" + from_station + ", to_station=" + to_station + ", trainid=" + trainid + ", date=" + date
				+ ", start_time=" + start_time + ", end_time=" + end_time + ", type=" + type + "]";
	}

}
