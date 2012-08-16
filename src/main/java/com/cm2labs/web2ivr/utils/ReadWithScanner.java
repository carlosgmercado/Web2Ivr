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
	private static  File fFile;

	private static  RestTemplate restTemplate;

  public static void main(String... aArgs) throws FileNotFoundException {
    ReadWithScanner parser = new ReadWithScanner("C:\\2012-08-16-0000.txt");
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
	  matchParts(aLine);
 
  }
  
  
  private static void matchParts( String aText ){
	
	  Pattern pattern = Pattern.compile("(?:\\[(\\w(\\w)*),(\\+\\d{11})\\])");
	  Matcher matcher = pattern.matcher(aText );
	  BatchDetail batchdetail;
	  while (matcher.find()) {
		 
			
			batchdetail = restTemplate.getForObject(URL_GET_BATCHDETAIL_PHONE, BatchDetail.class, tropo2Phone(matcher.group(3)));			
			batchdetail.setStatus(matcher.group(1));
			restTemplate.put(URL_TO_UPDATE_BATCHDETAIL, batchdetail, batchdetail.getId());
			System.out.println(batchdetail);
	  }

  }
  
  public static String tropo2Phone(String phone) {
		phone=phone.replace("+1","");
		phone= new StringBuffer(phone).insert(3, "-").toString();
		phone= new StringBuffer(phone).insert(7, "-").toString();
		return phone;
	}
}