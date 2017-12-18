package edu.sjsu.cmpe275.project.trainMgmt.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import edu.sjsu.cmpe275.project.trainMgmt.model.Train;
import edu.sjsu.cmpe275.project.trainMgmt.util.SearchCriteria;
import edu.sjsu.cmpe275.project.trainMgmt.util.SearchUtils;

@Repository
public class TrainDAO implements ITrainDAO {
	
	@PersistenceContext
    private EntityManager entityManager;
	
	@Override
    public List<Train> searchTrain(final List<SearchCriteria> params) {
		
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Train> query = builder.createQuery(Train.class);
        final Root r = query.from(Train.class);

        Predicate predicate = builder.conjunction();
        
        for (final SearchCriteria param : params) {
            if (param.getOperation().equalsIgnoreCase(">")) {
                predicate = builder.and(predicate, builder.greaterThanOrEqualTo(r.get(param.getKey()), param.getValue().toString()));
            } else if (param.getOperation().equalsIgnoreCase("<")) {
                predicate = builder.and(predicate, builder.lessThan(r.get(param.getKey()), param.getValue().toString()));
            } else if (param.getOperation().equalsIgnoreCase("=")) {
                if (r.get(param.getKey()).getJavaType() == String.class) {
                    predicate = builder.and(predicate, builder.like(r.get(param.getKey()), "%" + param.getValue() + "%"));
                } else {
                    predicate = builder.and(predicate, builder.equal(r.get(param.getKey()), param.getValue()));
                }
            }
        }
        query.where(predicate);
       
        List<Train> result = entityManager.createQuery(query).getResultList();
        return result;
    }


	@Override
	public void save(Train entity) {
		entityManager.persist(entity);	
	}
}
