package com.cm2labs.web2ivr.service.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.cm2labs.web2ivr.domain.BatchDetail;
import com.cm2labs.web2ivr.service.BatchDetailService;


@Service("jpaBatchDetailService")
@Repository
@Transactional

public class BatchDetailServiceImpl implements BatchDetailService{

	private Log log = LogFactory.getLog(BatchDetailServiceImpl.class);
	
	
	@PersistenceContext
	private EntityManager em;
	
	
	
	@Transactional(readOnly=true)
	public List<BatchDetail> getAllNumbers() {
		List<BatchDetail> batchDetails = em.createNamedQuery("BatchDetail.getAllNumbers", BatchDetail.class).getResultList();
		return batchDetails;
	}
	@Transactional(readOnly=true)
	public List<BatchDetail> findByBatchId(long batchId) {
		TypedQuery<BatchDetail> query = em.createNamedQuery("BatchDetail.findByBatchId", BatchDetail.class);
		query.setParameter("batchid", batchId);
		return query.getResultList();
	}
	
	@Transactional(readOnly=true)
	public BatchDetail findByPhone(String phone) {
		TypedQuery<BatchDetail> query = em.createNamedQuery("BatchDetail.findByPhone", BatchDetail.class);
		query.setParameter("phone", phone);
		return query.getSingleResult();
	}
	
	
	
	public void update(BatchDetail tr) {
		if (tr.getId() == null) { // Insert BatchDetail
			log.info("Inserting new BatchDetail");
			em.persist(tr);
		} else {                       // Update BatchDetail
			em.merge(tr);
			log.info("Updating existing BatchDetail");
		}
		log.info("BatchDetail saved with id: " + tr.getId());	
	}

}
