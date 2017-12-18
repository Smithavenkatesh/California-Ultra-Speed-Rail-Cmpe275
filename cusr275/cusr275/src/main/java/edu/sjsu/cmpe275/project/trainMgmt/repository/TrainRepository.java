package edu.sjsu.cmpe275.project.trainMgmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import edu.sjsu.cmpe275.project.trainMgmt.model.Train;

//public interface TrainRepository extends JpaRepository<Train,Long>, JpaSpecificationExecutor<Train>  {
//
//}


public interface TrainRepository extends JpaRepository<Train,Long>  {

}
