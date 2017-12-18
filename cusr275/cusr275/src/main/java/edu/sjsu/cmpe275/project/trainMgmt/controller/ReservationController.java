package edu.sjsu.cmpe275.project.trainMgmt.controller;

import java.util.ArrayList;
//import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import edu.sjsu.cmpe275.project.trainMgmt.repository.ReservationRepository;
import edu.sjsu.cmpe275.project.trainMgmt.model.Reservation;
import edu.sjsu.cmpe275.project.trainMgmt.service.ReservationService;
import edu.sjsu.cmpe275.project.trainMgmt.repository.TicketRepository;
import edu.sjsu.cmpe275.project.trainMgmt.model.Ticket;
import edu.sjsu.cmpe275.project.trainMgmt.service.TicketService;

//import javax.servlet.http.HttpServletResponse;
//import javax.transaction.Transactional;
//import javax.validation.Valid;

@RestController
@RequestMapping("/v1")
public class ReservationController {
	
	@Autowired
    private ReservationRepository ReservationRepository;
	
    @Autowired
	private ReservationService reservationService;
    
    @Autowired
    private TicketRepository TicketRepository;
	
    @Autowired
	private TicketService ticketService;
    
    // ------------ Retrieve all reservations ------------
	@RequestMapping(value = "/reservations", method = RequestMethod.GET)
	public List getAllReservations() {
		return reservationService.getAllReservations();
	}
	
	// ------------ Retrieve a reservation ------------
	@RequestMapping(value = "/reservations/{id}", method = RequestMethod.GET)
	public Reservation getReservation(@PathVariable String id) {
		return reservationService.getReservation(id);
	}
	
	// ------------ Create a reservation ------------
	@PostMapping("/reservations")
	@ResponseBody
	public ResponseEntity createReservation(@Valid
			@RequestParam(value= "trainids", required = true) String trainids,
			@RequestParam(value= "paths", required = true) String paths,
			@RequestParam(value= "traintimes", required = true) String traintimes,
			@RequestParam(value= "types", required = true) String types,
			@RequestBody Reservation Reservation)   	      
    {
		System.out.println("trainids: " +trainids);
		String[] items = trainids.split(",");
	    List<String> itemList = Arrays.asList(items);
	    System.out.println(itemList);
	    
	    
	    //for(String item: itemList){
	    	
	    //}
	    
		System.out.println("paths: " +paths);
		String[] items1 = paths.split(",");
		for(String item: items1) {
			String[] items2 = item.split("-");
			List<String> itemList1 = new ArrayList<String>();
		    for(String token: items2){
		        itemList1.add(token);
		    }
		    String from_station = itemList1[0];
		    String to_station = itemList1[1];
		    
		    Ticket t = new Ticket();
		    t.setFrom_station(String from_station);
		    t.setTo_station(String to_station);
		    
		    
		    //TicketRepository.save(t);
		    //return itemList1;
		    System.out.println(itemList1);
		}
		//String[] items2 = items1[0].split("-");
	    
	    
		System.out.println("traintimes: " +traintimes);
		String[] items3 = traintimes.split(",");
		for(String item: items3) {
			String[] items4 = item.split("-");
			List<String> itemList2 = new ArrayList<String>();
		    for(String token: items4){
		        itemList2.add(token);
		    }
		    //return itemList1;
		    System.out.println(itemList2);
		}
		
	    System.out.println("types: " +types);
		String[] items5 = types.split(",");
	    List<String> itemList3 = Arrays.asList(items5);
	    System.out.println(itemList3);
	    
		ReservationRepository.save(Reservation);
		
	    System.out.println(Reservation.getNames());
		return new ResponseEntity(HttpStatus.OK);
	    		
    }
	
	//------------ Delete a reservation ------------
	@RequestMapping(value = "/reservations/{id}", method = RequestMethod.DELETE)
	public void deleteReservation(@PathVariable String id) {
		reservationService.deleteReservation(id);
	}
}