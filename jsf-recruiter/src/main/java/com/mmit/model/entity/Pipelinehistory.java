package com.mmit.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

/**
 * Entity implementation class for Entity: Pipelinehistory
 *
 */
@Entity
@NamedQuery(name = "Pipelinehistory.findAll", query = "SELECT plh FROM Pipelinehistory plh ORDER BY plh.actionDate DESC")

public class Pipelinehistory implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "piplelineId", referencedColumnName = "id")
	private JobPipeline piplelineId;
	@ManyToOne
	@JoinColumn(name = "actionUserId", referencedColumnName = "id")
	private Recruiter actionUserId;
	@ManyToOne
	@JoinColumn(name = "fromstatus", referencedColumnName = "id")
	private CandidateJoborderStatus fromstatus;
	@ManyToOne
	@JoinColumn(name = "tostatus", referencedColumnName = "id")
	private CandidateJoborderStatus tostatus;
	@CreationTimestamp
	private LocalDateTime actionDate;

	private static final long serialVersionUID = 1L;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public JobPipeline getPiplelineId() {
		return piplelineId;
	}

	public void setPiplelineId(JobPipeline piplelineId) {
		this.piplelineId = piplelineId;
	}

	public Recruiter getActionUserId() {
		return actionUserId;
	}

	public void setActionUserId(Recruiter actionUserId) {
		this.actionUserId = actionUserId;
	}

	public CandidateJoborderStatus getFromstatus() {
		return fromstatus;
	}

	public void setFromstatus(CandidateJoborderStatus fromstatus) {
		this.fromstatus = fromstatus;
	}

	public CandidateJoborderStatus getTostatus() {
		return tostatus;
	}

	public void setTostatus(CandidateJoborderStatus tostatus) {
		this.tostatus = tostatus;
	}

	public LocalDateTime getActionDate() {
		return actionDate;
	}

	public void setActionDate(LocalDateTime actionDate) {
		this.actionDate = actionDate;
	}

	public Pipelinehistory() {
		super();
	}
	

}
