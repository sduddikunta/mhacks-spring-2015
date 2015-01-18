package org.mhacks.zss.rest;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Bill {

	public enum Status {
		PENDING, CANCELLED, COMPLETED
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private boolean recurring;
	private Status status;
	private String paymentDate;
	private String upcomingPaymentDate;
	private long paymentAmount;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isRecurring() {
		return recurring;
	}

	public void setRecurring(boolean recurring) {
		this.recurring = recurring;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getUpcomingPaymentDate() {
		return upcomingPaymentDate;
	}

	public void setUpcomingPaymentDate(String upcomingPaymentDate) {
		this.upcomingPaymentDate = upcomingPaymentDate;
	}

	public long getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(long paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

}
