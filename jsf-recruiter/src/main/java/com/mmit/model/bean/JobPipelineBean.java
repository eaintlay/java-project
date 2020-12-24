package com.mmit.model.bean;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mmit.model.entity.Candidate;
import com.mmit.model.entity.JobOrder;
import com.mmit.model.entity.JobPipeline;
import com.mmit.model.service.CandidateJobOrderStatusService;
import com.mmit.model.service.CandidateService;
import com.mmit.model.service.JobOrderService;
import com.mmit.model.service.JobPipelineService;

import java.io.Serializable;

@Named
@ViewScoped
public class JobPipelineBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Map<Integer, Boolean> checkedCandidates = new HashMap<Integer, Boolean>();	
	private List<Candidate> candidate_jobPipelineList;
	private JobPipeline jobpipeline;
	
	private JobPipeline edit_jobpipeline;
	private JobOrder joborder;
	
	private List<JobPipeline> jobPipelineList;
	private List<JobPipeline> joborder_jobPipelineList;

	
	
	private List<JobPipeline> jobPipelinePrcessActiveJobOrderList;
	private List<JobPipeline> jobPipelineRecentHireList;
	
	
	@EJB
	private JobPipelineService service;
	
	@EJB
	private CandidateService candidateService;
	
	@EJB
	private JobOrderService jobOrderService;
	
	@EJB
	private CandidateJobOrderStatusService candidateJobOrderStatusService;
	
	@Inject
	private LoginBean loginbean;
	
	@Inject
	private InterviewRecordBean interviewRecordBean;
	
	@PostConstruct
	private void initialize() {
		String jobid = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("joborderid");
		String canid = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("candidateid");
		if(jobid != null) {
			if(canid != null) {
				jobpipeline = service.findById(Integer.parseInt(canid));
				jobPipelineList = service.findByCandidate(jobpipeline.getId());
			}else {
				 candidate_jobPipelineList = candidateService.getUndeployedCandidates(Integer.parseInt(jobid));
				 for (Candidate each_candidate : candidate_jobPipelineList) 
				 { 
					 checkedCandidates.put(each_candidate.getId(), false);
					
					 }
				 for (Entry<Integer, Boolean> entry : checkedCandidates.entrySet()) {
					 System.out.println("key: "+ entry.getKey() +"value:" +entry.getValue() );
				 }
			}
			joborder = jobOrderService.findById(Integer.parseInt(jobid));
			joborder_jobPipelineList = service.findByJobOrder(Integer.parseInt(jobid));
		}else {
			if(canid != null) {
				
				jobpipeline = service.findById(Integer.parseInt(canid));
				if(jobpipeline != null)
					jobPipelineList = service.findByCandidate(jobpipeline.getId());
			}else {
				jobPipelinePrcessActiveJobOrderList = service.findInActiveJobOrder();
				jobPipelineRecentHireList = service.jobPipelineRecentHire();
			}
			
		}
		
		
	}
	
	public void findById(int jpid) {
		System.out.println("ID: "+ jpid);
		edit_jobpipeline = service.findById(jpid);
	}
	
	
	public String removeJobPipeline(int jpid) {
		service.delete(jpid);
		return "/views/joborder-detail?faces-redirect=true&joborderid=" + joborder.getId();
		
	}
	
	public String updateJobPipline() {
		int status = edit_jobpipeline.getCandidatejoborderstatus().getId();
		if(status == 500 || status == 600 || status == 700) {
			interviewRecordBean.insertInterviewRecord(edit_jobpipeline);
		}
		service.update(edit_jobpipeline);
		 return "/views/joborder-detail?faces-redirect=true&joborderid=" + joborder.getId();	
	}

	public String saveJobPipline() {
        for (Entry<Integer, Boolean> can : checkedCandidates.entrySet()) {
                if(can.getValue() == true) {
                	Candidate candidate = candidateService.findById( can.getKey());
                	JobPipeline jobPipeline = new JobPipeline();
                	jobPipeline.setJoborder(joborder);
                	jobPipeline.setCandidate(candidate);
                	jobPipeline.setCandidatejoborderstatus(candidateJobOrderStatusService.findById(100));
                	jobPipeline.setEntry_date(LocalDateTime.now());
                	jobPipeline.setEntryBy(loginbean.getLoginUser());
                	service.save(jobPipeline);
                }
            
        }
        return "/views/joborder-detail?faces-redirect=true&joborderid=" + joborder.getId();
		
	}


	public JobPipeline getJobpipeline() {
		return jobpipeline;
	}


	public void setJobpipeline(JobPipeline jobpipeline) {
		this.jobpipeline = jobpipeline;
	}


	public JobOrder getJoborder() {
		return joborder;
	}


	public void setJoborder(JobOrder joborder) {
		this.joborder = joborder;
	}



	public List<JobPipeline> getJobPipelineList() {
		return jobPipelineList;
	}


	public void setJobPipelineList(List<JobPipeline> jobPipelineList) {
		this.jobPipelineList = jobPipelineList;
	}


	public List<JobPipeline> getJoborder_jobPipelineList() {
		return joborder_jobPipelineList;
	}


	public void setJoborder_jobPipelineList(List<JobPipeline> joborder_jobPipelineList) {
		this.joborder_jobPipelineList = joborder_jobPipelineList;
	}


	public List<Candidate> getCandidate_jobPipelineList() {
		return candidate_jobPipelineList;
	}


	public void setCandidate_jobPipelineList(List<Candidate> candidate_jobPipelineList) {
		this.candidate_jobPipelineList = candidate_jobPipelineList;
	}


	public Map<Integer, Boolean> getCheckedCandidates() {
		return checkedCandidates;
	}


	public void setCheckedCandidates(Map<Integer, Boolean> checkedCandidates) {
		this.checkedCandidates = checkedCandidates;
	}

	public JobPipeline getEdit_jobpipeline() {

		return edit_jobpipeline;
	}

	public void setEdit_jobpipeline(JobPipeline edit_jobpipeline) {
		this.edit_jobpipeline = edit_jobpipeline;
	}

	public List<JobPipeline> getJobPipelinePrcessActiveJobOrderList() {
		return jobPipelinePrcessActiveJobOrderList;
	}

	public void setJobPipelinePrcessActiveJobOrderList(List<JobPipeline> jobPipelinePrcessActiveJobOrderList) {
		this.jobPipelinePrcessActiveJobOrderList = jobPipelinePrcessActiveJobOrderList;
	}

	public List<JobPipeline> getJobPipelineRecentHireList() {
		return jobPipelineRecentHireList;
	}

	public void setJobPipelineRecentHireList(List<JobPipeline> jobPipelineRecentHireList) {
		this.jobPipelineRecentHireList = jobPipelineRecentHireList;
	}

	
	

	
	


	


	
	
	
	
	
}
