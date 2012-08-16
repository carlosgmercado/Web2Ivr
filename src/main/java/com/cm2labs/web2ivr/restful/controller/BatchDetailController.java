package com.cm2labs.web2ivr.restful.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cm2labs.web2ivr.domain.BatchDetail;
import com.cm2labs.web2ivr.domain.BatchDetails;
import com.cm2labs.web2ivr.service.BatchDetailService;


@Controller
@RequestMapping(value="/batchdetail")
public class BatchDetailController {

		final Logger logger = LoggerFactory.getLogger(BatchDetailController.class);
		
		@Autowired
		private BatchDetailService batchDetailService;
		
		@RequestMapping(value="batch/{id}", method = RequestMethod.GET)
		@ResponseBody
		public BatchDetails  findByBatchId(@PathVariable Long id) {
			return new BatchDetails(batchDetailService.findByBatchId(id));
			
		}
		
		@RequestMapping(value="/{phone}", method = RequestMethod.GET)
		@ResponseBody
		public BatchDetail  findByPhone(@PathVariable String phone) {
			return batchDetailService.findByPhone(phone);
			
		}
		
		
		@RequestMapping(value="/{id}", method = RequestMethod.PUT)
		@ResponseBody
		public void update(@RequestBody BatchDetail batchdetail, @PathVariable Long id) {
			logger.info("Uptating BatchDetail for phone:"+batchdetail.getPhone());
			batchDetailService.update(batchdetail);
			logger.info("Batch detail updated successfully");
			
		}
		
		
}
