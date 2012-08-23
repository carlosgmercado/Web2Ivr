package com.cm2labs.web2ivr.utils;
import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.regex.*;

import org.joda.time.DateTime;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.web.client.RestTemplate;

import com.cm2labs.web2ivr.domain.BatchDetail;
import com.cm2labs.web2ivr.domain.BatchDetails;

public class ReadWithScanner {
	
	private static final String URL_GET_WORKING_BATCHDETAIL = "http://localhost:8080/Web2Ivr/restful/batchdetail/batch/{id}";
	private static final String URL_GET_BATCHDETAIL_PHONE = "http://localhost:8080/Web2Ivr/restful/batchdetail/{phone}";
	private static final String URL_TO_UPDATE_BATCHDETAIL = "http://localhost:8080/Web2Ivr/restful/batchdetail/{id}";
	private static final String EXP_PHONE_STATUS = ": \\[(\\w+),(\\+\\d{11})]";
	private static final String EXP_PHONE_CDR = ".*Submitting CDR.*Called\":\"(\\+\\d{11}).*\"Duration\":\"(\\d+)";
	private static  File fFile;

	private static  RestTemplate restTemplate;

  public static void main(String... aArgs) throws FileNotFoundException {
    ReadWithScanner parser = new ReadWithScanner("C:\\2012-08-21-2000.txt");
    GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
 	ctx.load("classpath:restful-client-app-context.xml");
 	ctx.refresh();
 	restTemplate = ctx.getBean("restTemplate",RestTemplate.class);
   
    Scanner scanner = new Scanner(new FileReader(fFile));
    try {
      //first use a Scanner to get each line
      while ( scanner.hasNextLine() ){
        processLine( scanner.nextLine() );
      }
    }
    finally {
    	System.out.println("Done");
      scanner.close();
    }
 
    
  }
  

  public ReadWithScanner(String aFileName){
    fFile = new File(aFileName);  
  }
  
  /** Template method that calls {@link #processLine(String)}.  */
  public static final void processLineByLine() throws FileNotFoundException {
    //Note that FileReader is used, not File, since File is not Closeable
    
  }
  

  protected static void processLine(String aLine){
    //use a second Scanner to parse the content of each line 
	  matchPart(aLine,EXP_PHONE_STATUS);
	  matchTime(aLine,EXP_PHONE_CDR);
	  
 
  }
  
  
  private static void matchPart( String aText, String patternToMatch ){

	
	  Pattern pattern = Pattern.compile(patternToMatch);
	  Matcher matcher = pattern.matcher(aText);
	  
	  BatchDetail batchdetail;
	  while (matcher.find()) {
		 
		  
			batchdetail = restTemplate.getForObject(URL_GET_BATCHDETAIL_PHONE, BatchDetail.class, tropo2Phone(matcher.group(2)));			
			batchdetail.setStatus(matcher.group(1));
			restTemplate.put(URL_TO_UPDATE_BATCHDETAIL, batchdetail, batchdetail.getId());
			//System.out.println(batchdetail);
	  }

  }
  
  private static void matchTime( String aText, String patternToMatch ){

		
	  Pattern pattern = Pattern.compile(patternToMatch);
	  Matcher matcher = pattern.matcher(aText);
	  
	  BatchDetail batchdetail;
	  while (matcher.find()) {
		 
		  	if (!matcher.group(1).equals("+17877058826")){
				batchdetail = restTemplate.getForObject(URL_GET_BATCHDETAIL_PHONE, BatchDetail.class, tropo2Phone(matcher.group(1)));			
				batchdetail.setTotalCallTime(Long.valueOf(matcher.group(2)));
				restTemplate.put(URL_TO_UPDATE_BATCHDETAIL, batchdetail, batchdetail.getId());
				//System.out.println(batchdetail);
		  	}
	  }

  }
  
  
  public static String tropo2Phone(String phone) {
		phone=phone.replace("+1","");
		phone= new StringBuffer(phone).insert(3, "-").toString();
		phone= new StringBuffer(phone).insert(7, "-").toString();
		return phone;
	}
}