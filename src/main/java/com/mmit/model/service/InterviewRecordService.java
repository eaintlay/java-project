package com.mmit.model.service;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.mmit.model.entity.InterviewRecord;
import com.mmit.model.entity.JobPipeline;

@Stateless
public class InterviewRecordService {
	
	@PersistenceContext(name = "jsf-recruiter")
	private EntityManager em;

	

	public InterviewRecord findById(int id) {
		return em.find(InterviewRecord.class, id);
	}



	public List<InterviewRecord> findAll() {
		TypedQuery<InterviewRecord> query = em.createNamedQuery("InterviewRecord.findAll", InterviewRecord.class);
		return query.getResultList();
	}



	public void insertIntoInterViewRecord(InterviewRecord interviewRecord) {
		em.persist(interviewRecord);
		
	}



	public List<InterviewRecord> findExistOrNot(JobPipeline edit_jobpipeline) {
		TypedQuery<InterviewRecord> query = em.createNamedQuery("InterviewRecord.findExistOrNot", InterviewRecord.class);
		query.setParameter("eidtcandidateJobOrderId", edit_jobpipeline.getId());
		return query.getResultList();
	}



	public void updateRecord(InterviewRecord interviewRecord) {
		em.merge(interviewRecord);
		
	}

}
