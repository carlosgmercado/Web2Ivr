package com.cm2labs.web2ivr.service;
import java.util.List;

import com.cm2labs.web2ivr.domain.BatchDetail;

public interface BatchDetailService {
	
	List<BatchDetail> getAllNumbers();
	
	List<BatchDetail> findByBatchId(long batchId);
	
	BatchDetail findByPhone (String phone);
	
	void update(BatchDetail bdr);
	

}
