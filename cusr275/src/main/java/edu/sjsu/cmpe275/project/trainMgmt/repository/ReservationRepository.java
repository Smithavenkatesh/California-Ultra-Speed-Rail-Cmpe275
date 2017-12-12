package edu.sjsu.cmpe275.project.trainMgmt.repository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;


import org.springframework.data.jpa.repository.JpaRepository;

import edu.sjsu.cmpe275.project.trainMgmt.model.Reservation;
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
	

	@Query(value="select * from csur275.reservation where uid=?",nativeQuery=true)
	public List<Reservation> retrieveUsersByUid(Long uid);
}
