package com.mmit.model.service;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.mmit.model.bean.LoginBean;
import com.mmit.model.entity.Recruiter;

@Stateless
public class RecruiterService {
	
	@PersistenceContext(name = "jsf-recruiter")
	private EntityManager em;
	
	@Inject
	private LoginBean loginbean;
	
	public List<Recruiter> findAll() {
		TypedQuery<Recruiter> query = em.createNamedQuery("Recruiter.findAll", Recruiter.class);
		return query.getResultList();
	}

	public Recruiter findById(int id) {
		return em.find(Recruiter.class, id);
	}

	public boolean save(Recruiter recruiter) throws SQLException{
		try {
			
			if (recruiter.getId() == 0)  {
				em.persist(recruiter);
			}
			else {
				em.merge(recruiter);
				em.flush();
				if (recruiter.getId() == loginbean.getLoginUser().getId()) {
					loginbean.setLoginUser(recruiter);
				}	
			}
			
			return true;
			
		} catch (Exception e) {
			return false;
		}

			
	}

	public void delete(int rid) {
		Recruiter r = em.find(Recruiter.class, rid);
		em.remove(r);
		
	}

	public Long getCountUser() {
		TypedQuery<Long> query = em.createQuery("SELECT COUNT(r) FROM Recruiter r",Long.class);
		return query.getSingleResult();
	}
	
	
		
	

}
