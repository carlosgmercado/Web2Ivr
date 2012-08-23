package com.cm2labs.web2ivr.utils;


import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.joda.time.DateTime;
import java.lang.Thread;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.web.client.RestTemplate;

import com.cm2labs.web2ivr.domain.BatchDetail;
import com.cm2labs.web2ivr.domain.BatchDetails;
import com.voxeo.tropo.Tropo;


public class RestfulClient {
	private static final String URL_GET_WORKING_BATCHDETAIL = "http://localhost:8080/Web2Ivr/restful/batchdetail/batch/{id}";
	private static final String URL_GET_BATCHDETAIL_PHONE = "http://localhost:8080/Web2Ivr/restful/batchdetail/{phone}";
	private static final String URL_TO_UPDATE_BATCHDETAIL = "http://localhost:8080/Web2Ivr/restful/batchdetail/{id}";
	private static Tropo tropo;
	
	public static void main(String[] args) {
		
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:restful-client-app-context.xml");
		ctx.refresh();
		tropo = new Tropo();
		BatchDetails batchdetails;
		
		RestTemplate restTemplate = ctx.getBean("restTemplate",RestTemplate.class);
		
		batchdetails = restTemplate.getForObject(URL_GET_WORKING_BATCHDETAIL, BatchDetails.class, 5);
		int i=1;
		for (BatchDetail batchdetail: batchdetails.getBatchdetails()) {
			
			if (i%200 == 0){
				try {
					System.out.println("Running total called :"+i);
				Thread.sleep(100000);
				}
				catch (Exception e){
					
					
				}
			}
			
			//batchdetail1 = restTemplate.getForObject(URL_GET_BATCHDETAIL_PHONE, BatchDetail.class, batchdetail1.getPhone());
			call(batchdetail);
			batchdetail.setStatus("CALLED");
			batchdetail.setTime_stamp(new DateTime());
			restTemplate.put(URL_TO_UPDATE_BATCHDETAIL, batchdetail, batchdetail.getId());
			System.out.println("Running total called :"+i);
			
			i++;
		}
		
		//listBatchDetail(batchdetails);
		i = i - 1;
System.out.println("Finished with :"+i+" called");
	}
	private static void listBatchDetail(BatchDetails batchdetails) {
		System.out.println("");
		System.out.println("Listing Batch Details:");
		for (BatchDetail batchdetail: batchdetails.getBatchdetails()) {
			System.out.println(batchdetail);		
			System.out.println();	
		}
	}
	
	private static void call(BatchDetail batchdetail){
		String token = "1346b5c74535014e9c6b168876c4e192f9e91631e456a5acd942921e3fe201fdd7cae0293f7620dd50184a35";
		Map<String, String> params = new HashMap<String, String>();
		params.put("numberToDial", batchdetail.phone2Tropo());
		tropo.launchSession(token, params);
		
		
	}
	
	
	
	

}
