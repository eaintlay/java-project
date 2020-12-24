package com.mmit.model.service;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.mmit.model.bean.LoginBean;
import com.mmit.model.entity.Candidate;

@Stateless
public class CandidateService {
	
	@PersistenceContext(name = "jsf-recruiter")
	private EntityManager em;
	
	
	@Inject
	private LoginBean loginbean;

	public List<Candidate> findAll() {
		TypedQuery<Candidate> query = em.createNamedQuery("Candidate.findAll", Candidate.class);
		return query.getResultList();
	}

	public Candidate findById(int id) {
		return em.find(Candidate.class, id);
	}

	public void save(Candidate candidate) {	
		if (candidate.getId() == 0) {
			candidate.setEntry_date(LocalDate.now());
			candidate.setEntryBy(loginbean.getLoginUser());
			em.persist(candidate);
		}else {
			candidate.setModify_date(LocalDate.now());
			candidate.setModifyBy(loginbean.getLoginUser());
			em.merge(candidate);
		}
	}
	
	public void delete(int cid) {
		
		Candidate can = em.find(Candidate.class, cid);
		em.remove(can);
	}

	public List<Candidate> getUndeployedCandidates(int joborderid) {
		TypedQuery<Candidate> query = em.createNamedQuery("Candidate.getUndeployedCandidates", Candidate.class);
		query.setParameter("joborderid", joborderid);
		return query.getResultList();
	}

	

}
