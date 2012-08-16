package com.cm2labs.web2ivr.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.lang.StringBuffer;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.hibernate.annotations.Type;



@Entity
@Table(name = "BatchDetail")
@NamedQueries({
	@NamedQuery(name="BatchDetail.getAllNumbers",
			    query="select b from BatchDetail b"), 
	@NamedQuery(name="BatchDetail.findByBatchId", 
			    query="select distinct b from BatchDetail b where b.batchid = :batchid"),
	
	@NamedQuery(name="BatchDetail.findByPhone", 
   				query="select distinct b from BatchDetail b where b.phone = :phone")
})
@SqlResultSetMapping(
		name="batchDetailResult",
		entities=@EntityResult(entityClass=BatchDetail.class)
)
public class BatchDetail implements Serializable {
	
	private Long id;
	private Long batchid;
	private String phone;
	private String status;
	private DateTime time_stamp;
	
	
	

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idBatchDetails")
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id){
		this.id = id;
		
	}
	
	@Column(name = "fk_batchid")
	public Long getBatchid() {
		return batchid;
	}


	public void setBatchid(Long batchid) {
		this.batchid = batchid;
	}

	@Column(name = "phone")
	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	
	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "time_stamp")
	@Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
	public DateTime getTime_stamp() {
		return time_stamp;
	}


	public void setTime_stamp(DateTime time_stamp) {
		this.time_stamp = time_stamp;
	}

	public String phone2Tropo() {
		return "+1"+ phone.replace("-", "");
	}	
	
	public void setTropo2Phone(String phone) {
		phone=phone.replace("+1","");
		phone= new StringBuffer(phone).insert(3, "-").toString();
		phone= new StringBuffer(phone).insert(7, "-").toString();
		setPhone(phone);
	}
	@Override
	
	public String toString() {
		return "BatchDetail [id=" + id + ", batchid=" + batchid + ", phone="
				+ phone + ", tropo version ="+phone2Tropo()+", status=" + status + ", time_stamp=" + DateTimeFormat.forPattern("YYYY-MM-dd hh:mm:ss").print(time_stamp)
				+ "]";
	}

	

	
	
	
	
	

}
