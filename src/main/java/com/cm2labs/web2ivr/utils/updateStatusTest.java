package com.cm2labs.web2ivr.utils;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.joda.time.DateTime;

import org.springframework.context.support.GenericXmlApplicationContext;
import com.cm2labs.web2ivr.domain.*;
import com.cm2labs.web2ivr.service.BatchDetailService;

public class updateStatusTest {
	public static void main(String[] args) {
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:datasource-tx-jpa.xml");
		ctx.refresh();
		BatchDetailService batchDetailService = ctx.getBean("jpaBatchDetailService", BatchDetailService.class);
		//List<BatchDetail> batchdetails = batchDetailService.findByBatchId(1L);
		
		List<BatchDetail> batchdetails = batchDetailService.getAllNumbers();
		int i =0;
		
		
		for (BatchDetail batchdetail: batchdetails){
			
			/*if (i%3 == 0){
				batchdetail.setStatus("ERROR");
				
			}
			else if (i%7 == 0) {
				batchdetail.setStatus("LATER");
				
			}*/
			
			batchdetail.setStatus("READY");
			batchdetail.setTime_stamp(new DateTime());
			batchDetailService.update(batchdetail);
			i++;
		}
		
		listBatchDetail(batchdetails);
		
		//
		
	}
	private static void listBatchDetail(List<BatchDetail> batchdetails) {
		System.out.println("");
		System.out.println("Listing Batch Details:");
		for (BatchDetail batchdetail: batchdetails) {
			System.out.println(batchdetail);		
			System.out.println();	
		}
	}
	private static void updateBatchDetailtoError(BatchDetail batchdetail){
		batchdetail.setStatus("ERROR");
		
	}
	
	
}
