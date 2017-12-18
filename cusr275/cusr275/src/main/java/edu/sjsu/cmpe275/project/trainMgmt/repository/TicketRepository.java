package edu.sjsu.cmpe275.project.trainMgmt.repository;

//import org.springframework.data.jpa.repository.Query;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.sjsu.cmpe275.project.trainMgmt.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
	
}
