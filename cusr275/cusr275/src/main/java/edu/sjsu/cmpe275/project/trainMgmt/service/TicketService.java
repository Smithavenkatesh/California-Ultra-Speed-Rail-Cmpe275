package edu.sjsu.cmpe275.project.trainMgmt.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.project.trainMgmt.repository.TicketRepository;
import edu.sjsu.cmpe275.project.trainMgmt.model.Ticket;
//import com.springproject.reservation.user;


@Service
@RestController
@RequestMapping("/v1")
public class TicketService {

	@Autowired
	private TicketRepository ticketRepository;
	
	public TicketService(TicketRepository ticketRepository) {
		this.ticketRepository = ticketRepository;
		}
	
	// Retrieve all rows from table and populate list with objects
	public List<Object> getAllTickets() {
		
		ArrayList<Object> tickets = new ArrayList<Object>();
		ticketRepository.findAll().forEach(tickets::add);
		
		return tickets;
	}
	
	public List<Ticket> findAll() {
		return ticketRepository.findAll();
	}
    public Ticket find(Long id){
		
		return ticktRepository.findOne(id);
	}
	


	// Retrieves one row from table based on given id
//	public Reservation getReservation(String id) {
//		return reservationRepository.findOne(id);
//	
//	}
	
	// Inserts row into table 
	public void addTicket(Ticket Ticket) {
		ticketRepository.save(Ticket);
	}
	
	
	public List<Ticket> findReservationByuid(Long uid){
		return ticketRepository.retrieveUsersByUid(uid);
	}
	
	
	
//	// Removes row from table
//	public void deleteReservation(String id) {
//		reservationRepository.delete(id);
//	}
	
}
