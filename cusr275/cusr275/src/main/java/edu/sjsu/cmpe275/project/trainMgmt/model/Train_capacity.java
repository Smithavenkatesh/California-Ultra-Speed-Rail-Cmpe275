package edu.sjsu.cmpe275.project.trainMgmt.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;

//Model class
@Entity
@Table(name="Train_capacity")
public class Train_capacity {

	@EmbeddedId
	private Train_capacityID train_capacityId;
	
	@Column(name="capacity", nullable = false)
	private Long capacity;
	
	@Column(name="cancelled", nullable = false)
    private Boolean cancelled;
	
	/**
	 * Default Constructor
	 */
	public Train_capacity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Parameterized Constructor
	 * @param trainID
	 * @param capacity
	 * @param date
	*/
	public Train_capacity(Train_capacityID train_capacityId,Long capacity, Boolean cancelled) {
		super();
		this.train_capacityId = train_capacityId;
		this.capacity = capacity;
		this.cancelled = cancelled;
	}
	
	public Train_capacityID getTraincapacity() {
		return train_capacityId;
	}
	public void setTraincapacity(Train_capacityID train_capacityId) {
		this.train_capacityId = train_capacityId;
	}
	
    public Long getCapacity() {
		return capacity;
	}
	public void setCapacity(Long capacity) {
		this.capacity = capacity;
	}
	
	public Boolean getCancelled() {
		return cancelled;
	}
	public void setCancelled(Boolean cancelled) {
		this.cancelled = cancelled;
	}
	
}