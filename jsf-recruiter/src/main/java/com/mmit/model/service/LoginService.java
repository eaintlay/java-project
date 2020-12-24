package com.mmit.model.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.mmit.model.entity.Recruiter;


@Stateless
public class LoginService {
	
	@PersistenceContext(name = "jsf-recruiter")
	private EntityManager em;

	public Recruiter check(String email, String password) throws EntityNotFoundException{
		TypedQuery<Recruiter> query = em.createNamedQuery("Recruiter.login",Recruiter.class );
		query.setParameter("email" , email);
		query.setParameter("password" , password);
		
		Recruiter result_rec = query.getSingleResult();
		System.out.println(result_rec.getName());
		return query.getSingleResult();
		
		
	}
}
