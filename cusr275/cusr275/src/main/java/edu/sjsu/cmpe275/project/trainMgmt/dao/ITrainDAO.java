package edu.sjsu.cmpe275.project.trainMgmt.dao;

import java.util.List;

import edu.sjsu.cmpe275.project.trainMgmt.model.Train;
import edu.sjsu.cmpe275.project.trainMgmt.util.SearchCriteria;

public interface ITrainDAO {
	List<Train> searchTrain(List<SearchCriteria> params);
	
	void save(Train entity);

}
