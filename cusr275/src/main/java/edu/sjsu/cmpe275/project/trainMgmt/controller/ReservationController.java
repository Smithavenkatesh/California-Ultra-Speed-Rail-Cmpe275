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
	
	// ------------ Retrieve all reservations ------------
	@RequestMapping(value = "/reservations", method = RequestMethod.GET)
	public List getAllReservations() {
		
		return reservationService.getAllReservations();
		
	}
	
//	// ------------ Retrieve a reservation ------------
//	@RequestMapping(value = "/reservations/{id}", method = RequestMethod.GET)
//	public Reservation getReservation(@PathVariable String id) {
//		return reservationService.getReservation(id);
//	}
	
	// ------------ Create a reservation ------------
	@PostMapping("/reservations")
	@ResponseBody
	public ResponseEntity createReservation(@RequestBody Reservation Reservation)   	      
    {
		 // Reservation addreservation = new Reservation(overall_from_station,overall_to_station,round_trip,number_of_tickets,number_of_passengers,overall_rate,names,type,date,time,overall_start_time,overall_end_time,UID);
		 
		ReservationRepository.save(Reservation);
		System.out.println(Reservation.getNames());
		  return new ResponseEntity(HttpStatus.OK);
	    		
    }
    
	@PostMapping("/cancel/{id}")
	
    public ResponseEntity<Reservation> updateid(HttpServletResponse response,@PathVariable(value = "id") Long tid) {
		Reservation cancelid = reservationService.find(tid);
		
		
        if((Long)tid == null || cancelid == null) {
        	System.out.println("not found");
        	return ResponseEntity.notFound().build();
        	
        }
        	
        	cancelid.setCancelled(true);
    		ReservationRepository.save(cancelid);
    	    return new ResponseEntity(HttpStatus.OK);        
    }
	
	
@GetMapping("/GetReservationsByuid/{id}")
	
    public ModelAndView userdetails(HttpServletResponse response,@PathVariable(value = "id") Long uid) {
		
		ArrayList<Reservation> user = new ArrayList<>();
		user = (ArrayList<Reservation>) reservationService.findReservationByuid(uid);
		ModelAndView modelAndView;
		 ModelMap map = new ModelMap();
        if(user != null && !user.isEmpty() ) {
        	
        	System.out.println("found");	
        	 map.addAttribute("Reservations", user);
    		 
    		 modelAndView = new ModelAndView(new MappingJackson2JsonView(),map);
    		 return modelAndView;
        	
        }
      

        map.addAttribute("Message", "not found");
		 
		 modelAndView = new ModelAndView(new MappingJackson2JsonView(),map);
		 return modelAndView;        
    }
	
	
}
