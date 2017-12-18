package edu.sjsu.cmpe275.project.trainMgmt.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.project.trainMgmt.repository.ReservationRepository;
import edu.sjsu.cmpe275.project.trainMgmt.model.Reservation;
//import com.springproject.reservation.user;


@Service
@RestController
@RequestMapping("/v1")
public class ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;
	
	public ReservationService(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
		}
	
	// Retrieve all rows from table and populate list with objects
	public List<Object> getAllReservations() {
		
		ArrayList<Object> reservations = new ArrayList<Object>();
		reservationRepository.findAll().forEach(reservations::add);
		
		return reservations;
	}
	
	public List<Reservation> findAll() {
		return reservationRepository.findAll();
	}
public Reservation find(Long id){
		
		return reservationRepository.findOne(id);
	}
	


	// Retrieves one row from table based on given id
//	public Reservation getReservation(String id) {
//		return reservationRepository.findOne(id);
//	
//	}
	
	// Inserts row into table 
	public void addReservation(Reservation Reservation) {
		reservationRepository.save(Reservation);
	}
	
	
	public List<Reservation> findReservationByuid(Long uid){
		return reservationRepository.retrieveUsersByUid(uid);
	}
	
	
	
//	// Removes row from table
//	public void deleteReservation(String id) {
//		reservationRepository.delete(id);
//	}
	
}
