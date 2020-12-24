package com.mmit.model.bean;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;

import com.mmit.model.entity.Candidate;
import com.mmit.model.service.CandidateService;

import java.io.File;
import java.io.Serializable;

@Named
@ViewScoped
public class CandidateBean implements Serializable {
	
	private static final long serialVersionUID = 1L;


	private Candidate candidate;
	
	
	private List<Candidate> candidateList;
	
	private Part cv_form;
	@EJB
	private CandidateService service;
	
	
	@PostConstruct
	private void initialize() {
		String canid = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("candidateid");
		if(canid != null) {
			candidate = service.findById(Integer.parseInt(canid));
			if(candidate.getIs_active().equals("yes")) {
				candidate.setIs_active("true");
			}else {
				candidate.setIs_active("false");
			}
			
		}else {
			candidate = new Candidate();
		}
		
		candidateList = service.findAll();
		
	}
	
//	public void uploadFile() {
//		System.out.println("This is upload file operation");
//		// Upload File
//		String candidateId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("candidateid");
//		if(candidateId == null) {
//		try {
//				String uploadFileName = cv_form.getSubmittedFileName();
//				String oldName = uploadFileName.substring(0,uploadFileName.lastIndexOf("."));
//				String newName = oldName + System.currentTimeMillis();
//				uploadFileName = uploadFileName.replace(oldName, newName);
//				
//				/* String.valueOf(LocalDateTime.now() */
//				ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
//				
//				String dirPath = context.getRealPath("") + File.separator + "CV_Uploads";
//				File rootDir = new File(dirPath);
//				if(!rootDir.exists())
//					rootDir.mkdirs();
//				
//				cv_form.write(rootDir + File.separator + uploadFileName);
//				candidate.setCv_form(uploadFileName);
//		} catch (Exception e) {
//					FacesMessage message = new FacesMessage(e.getMessage());
//					FacesContext.getCurrentInstance().addMessage(null, message);
//					return;
//				}
//		}
//	}
	
	public String removeCandidate(int cid) {
		service.delete(cid);
		return "/views/candidates?faces-redirect=true";
		
	}
	
	public String saveCandidate() {
		// cv form upload
		if (cv_form == null && candidate.getId() == 0) {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage("* CV form is required.");
			context.addMessage("candidateform:cvform", msg);
			return null;
		}
		try {
			if (cv_form != null) {
				String uploadFileName = cv_form.getSubmittedFileName();
				String oldName = uploadFileName.substring(0,uploadFileName.lastIndexOf("."));
				String newName = oldName + System.currentTimeMillis();
				uploadFileName = uploadFileName.replace(oldName, newName);
				
				/* String.valueOf(LocalDateTime.now() */
				ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
				
				String dirPath = context.getRealPath("") + File.separator + "CV_Uploads";
				File rootDir = new File(dirPath);
				if(!rootDir.exists())
					rootDir.mkdirs();
				
				cv_form.write(rootDir + File.separator + uploadFileName);
				// remove old cv form
				if (candidate.getId() != 0) {
					File file = new File(dirPath+File.separator+candidate.getCv_form());
					file.delete();
				}
				candidate.setCv_form(uploadFileName);
			}
		
			if(candidate.getIs_active().equals("true")) {
				candidate.setIs_active("yes");
			}else {
				candidate.setIs_active("no");
			}
		service.save(candidate);
		} catch (Exception e) {
			
			FacesMessage msg = new FacesMessage(e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}
		
		return "/views/candidates?faces-redirect=true";
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public List<Candidate> getCandidateList() {
		return candidateList;
	}

	public void setCandidateList(List<Candidate> candidateList) {
		this.candidateList = candidateList;
	}

	public Part getCv_form() {
		return cv_form;
	}

	public void setCv_form(Part cv_form) {
		this.cv_form = cv_form;
	}
	
	

	

	
}
