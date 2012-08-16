package com.cm2labs.web2ivr.domain;

import java.io.Serializable;
import java.util.List;
public class BatchDetails implements Serializable {

		private List<BatchDetail> batchdetails;
		public BatchDetails(){
			
		}
		
		public BatchDetails(List<BatchDetail> batchdetails){
			this.batchdetails = batchdetails;
		}

		
		public List<BatchDetail> getBatchdetails() {
			return batchdetails;
		}

		public void setBatchdetails(List<BatchDetail> batchdetails) {
			this.batchdetails = batchdetails;
		}

}		
