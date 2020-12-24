
INSERT INTO Recruiter (email, name, password, phone, role) VALUES ('admin@gmail.com', 'admin', 'admin123', '0943276678','Admin');
INSERT INTO Recruiter (email, name, password, phone, role) VALUES ('test@gmail.com', 'test', 'test123', '0935754257','Staff');

INSERT INTO Township  (name) VALUES ('Yangon');
INSERT INTO Township  (name) VALUES ('Mandalay');
INSERT INTO Township  (name) VALUES ('Pyin Oo Lwin');

INSERT INTO AvailabilityType(description)VALUES('immediately');
INSERT INTO AvailabilityType(description)VALUES('1 week');
INSERT INTO AvailabilityType(description)VALUES('2 weeks');
INSERT INTO AvailabilityType(description)VALUES('1 or 2 months');
INSERT INTO AvailabilityType(description)VALUES('above 2 months');


INSERT INTO CandidateJoborderStatus  (`id`,`short_description`) VALUES ('100', 'Added');
INSERT INTO CandidateJoborderStatus  (`id`,`short_description`) VALUES ('200', 'Interview Request');
INSERT INTO CandidateJoborderStatus  (`id`,`short_description`) VALUES ('300', 'Interview Accept');
INSERT INTO CandidateJoborderStatus  (`id`,`short_description`) VALUES ('400', 'Interview Reject');
INSERT INTO CandidateJoborderStatus  (`id`,`short_description`) VALUES ('500', 'First Interview');
INSERT INTO CandidateJoborderStatus  (`id`,`short_description`) VALUES ('600', 'Second Interview');
INSERT INTO CandidateJoborderStatus  (`id`,`short_description`) VALUES ('700', 'Third Interview');
INSERT INTO CandidateJoborderStatus  (`id`,`short_description`) VALUES ('800', 'Offered');
INSERT INTO CandidateJoborderStatus  (`id`,`short_description`) VALUES ('900', 'Placed');
INSERT INTO CandidateJoborderStatus  (`id`,`short_description`) VALUES ('1000', 'Candidate Declined');

INSERT INTO `Company` (`id`, `address`, `email`, `entry_date`, `ishot`, `modifyDate`, `name`, `ownerName`, `phone1`, `phone2`, `remark`, `website`, `entryBy`, `modifyBy`, `township_id`) VALUES (NULL, 'Hlaing,Than Lan', 'imc@gmail.com', '2020-12-21', 'yes', NULL, 'IMC', 'CEO', '0950765676', '0943199682', 'JP & IT', 'www.imc.com.mm', '1', NULL, '1');

INSERT INTO `Candidate` (`id`, `cv_form`, `email`, `entry_date`, `expectedSalary`, `is_active`, `key_skills`, `modify_date`, `name`, `phone`, `availiability_id`, `entryBy`, `modifyBy`, `township_id`) VALUES (NULL, 'mycv.pdf', 'koko@gmail.com', '2020-12-22', '180000', 'yes', 'PHP, Python, Java, Japan', NULL, 'Ko Ko', '0996275324', '1', '1', NULL, '1');

INSERT INTO `JobOrder` (`id`, `basic_salary`, `entry_date`, `is_active`, `job_description`, `job_position`, `modify_date`, `total_employee`, `company`, `entryBy`, `job_location`, `modifyBy`) VALUES (NULL, '200000', '2020-12-21', 'yes', 'This position is entitled to perform the following responsibilities:\r\n\r\nSupport junior developer’s productivity improvement by knowledge sharing\r\n\r\nDevelop assign tasks by project manager/ team leader\r\n\r\nEasily recognizes system deficiencies and implements effective solutions\r\n\r\nCommunicates and enforces coding standards\r\n\r\nHelp in solving issues of junior developer\r\n\r\nConsistently delivers high-quality services to project manager/ team leader\r\n\r\nWrite daily report and send to project manager', 'Web Developer', NULL, '2', '1', '1', '1', NULL);

INSERT INTO InterviewType  (description) VALUES ('Personal');
INSERT INTO InterviewType  (description) VALUES ('Call');
INSERT INTO InterviewType  (description) VALUES ('Online');

DROP TRIGGER IF EXISTS change_pipeline_status_history; 
CREATE TRIGGER change_pipeline_status_history AFTER UPDATE ON candidate_job_order FOR EACH ROW BEGIN IF NEW.candidatejoborderstatus_id != OLD.candidatejoborderstatus_id THEN INSERT INTO Pipelinehistory(piplelineId,fromstatus,tostatus,actionUserId,actionDate) VALUES(NEW.id,OLD.candidatejoborderstatus_id,NEW.candidatejoborderstatus_id,NEW.modifyBy,NEW.modify_date); END IF; END



