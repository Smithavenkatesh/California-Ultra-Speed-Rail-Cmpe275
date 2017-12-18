package edu.sjsu.cmpe275.project.trainMgmt.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

//Model class
@Entity
@Table(name="reservation")
public class Reservation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="tid", nullable = false)
 	private long tid;
	
	@Column(name="uid")
    private Long uid;
	
	@Column(name="type")
    private String type;
	
	@Column(name="date")
    private String date;
	
	@Column(name="time")
    private String time;
	
	@Column(name="overall_rate")
    private String overall_rate;
	
	@Column(name="overall_start_time")
    private String overall_start_time;
	
	@Column(name="overall_end_time")
    private String overall_end_time;
	
	@Column(name="round_trip")
    private String round_trip;
	
 	@Column(name="overall_from_station")
 	private String overall_from_station;
 	
 	@Column(name="overall_to_station")
 	private String overall_to_station;
 	
 	@Column(name="number_of_tickets")
    private String number_of_tickets;
 	
 	@Column(name="number_of_passengers")
    private Long number_of_passengers;
 	
 	@Column(name="names")
    private String names;
 	
 	@Column(name="cancelled")
    private Boolean cancelled;
 	
 	@Column(name="isexact")
    private Boolean isexact;
 	
 	
 	@Access(AccessType.PROPERTY)
	@OneToMany(targetEntity=Ticket.class, mappedBy = "reservation",cascade = CascadeType.ALL,
    fetch = FetchType.EAGER)
 	private List<Ticket> tickets;
 	
 	/**
	 * Default Constructor
	 */
	public Reservation() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Parameterized Constructor
	 * @param from_station
	 * @param to_station
	 * @param price
	*/
	public Reservation(String overall_from_station, String overall_to_station, String overall_rate, String number_of_tickets, Long number_of_passengers,String names, String type, String date, String time, String overall_start_time, String overall_end_time, String round_trip, Long uid, Boolean cancelled, Boolean isexact) {
		super();
		this.overall_from_station = overall_from_station;
		this.overall_to_station = overall_to_station;
		this.overall_rate = overall_rate;
		this.number_of_tickets = number_of_tickets;
		this.number_of_passengers = number_of_passengers;
		this.names = names;
		this.type = type;
		this.date = date;
		this.time = time;
	    this.overall_start_time = overall_start_time;
		this.overall_end_time = overall_end_time;
		this.round_trip = round_trip;
		this.uid = uid;
		this.cancelled = cancelled;
		this.isexact = isexact;
		
	}
	
	public Long getUid() {
		return uid;
	}
	
	public void setUid(Long uid) {
		this.uid = uid;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
    public String getOverall_from_station() {
		return overall_from_station;
	}
	
	public void setOverall_from_station(String overall_from_station) {
		this.overall_from_station = overall_from_station;
	}
	
	public String getOverall_to_station() {
		return overall_to_station;
	}
	
	public void setOverall_to_station(String overall_to_station) {
		this.overall_to_station = overall_to_station;
	}
	
	public String getOverall_rate() {
		return overall_rate;
	}
	public void setOverall_rate(String overall_rate) {
		this.overall_rate = overall_rate;
	}
	
	public Long getNumber_of_passengers() {
		return number_of_passengers;
	}
	public void setNumber_of_passengers(Long number_of_passengers) {
		this.number_of_passengers = number_of_passengers;
	}
	
	public String getNumber_of_tickets() {
		return number_of_tickets;
	}
	public void setNumber_of_tickets(String number_of_tickets) {
		this.number_of_tickets = number_of_tickets;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getOverall_start_time() {
		return overall_start_time;
	}
	public void setOverall_start_time(String overall_start_time) {
		this.overall_start_time = overall_start_time;
	}
	
	public String getOverall_end_time() {
		return overall_end_time;
	}
	public void setOverall_end_time(String overall_end_time) {
		this.overall_end_time = overall_end_time;
	}
	
	public String getRound_trip() {
		return round_trip;
	}
	public void setRound_trip(String round_trip) {
		this.round_trip = round_trip;
	}
	
	public String getNames() {
		return names;
	}
	public void setNames(String names) {
		this.names = names;
	}
	
	public Boolean getCancelled() {
		return cancelled;
	}
	public void setCancelled(Boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	public Boolean getIsexact() {
		return isexact;
	}
	public void setIsexact(Boolean isexact) {
		this.isexact = isexact;
	}
	
	public List<Ticket> getTickets() {
		return tickets;
	}
	
	public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
 	
	
	//@Override
	//public String toString() {
	//	return "reservation [TID=" + tid + ", overall_from_station=" + overall_from_station + ", overall_to_station=" + overall_to_station + ", overall_rate=" + overall_rate
	//			+ ", number_of_tickets=" + number_of_tickets + ", number_of_passengers=" + number_of_passengers + ", uid=" + uid + ", type=" + type + ", date=" + date + ", time=" + time + ", overall_start_time=" + overall_start_time + ", overall_end_time=" + overall_end_time + ", round_trip=" + round_trip + ", cancelled=" + cancelled + ", isexact=" + isexact + ", trainids=" + trainids + ", paths=" + paths + "traintimes=" + traintimes + "]";
	//}

}
