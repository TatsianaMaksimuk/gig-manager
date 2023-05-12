package com.gigmanager.service;

import com.gigmanager.models.Customer;
import com.gigmanager.models.Job;
import com.gigmanager.models.enums.JobStatus;
import com.gigmanager.models.request.JobUpsertRequest;
import com.gigmanager.repositories.CustomerRepository;
import com.gigmanager.repositories.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;
    private final CustomerRepository customerRepository;

    public List<Job> readAllJobs(String username) {
        return jobRepository.findAllByCustomer_ApiUser_username(username);
    }

    public Job findJobById(Long id, String username) {
        Job job = jobRepository.findById(id).orElse(null);
        if (job == null || !job.getCustomer().getApiUser().getUsername().equals(username)) {
            return null;
        }
        return job;
    }

    //GetJobsByCustomerId
    public List<Job> findJobsByCustomerId(Long id, String username) {
        return jobRepository.findAllByCustomer_ApiUser_usernameAndCustomer_id(id, username);
    }

    //GetJobsByStatus
    public List<Job> filerJobsByStatus(JobStatus status, String username) {
        return jobRepository.findAllByCustomer_ApiUser_usernameAndStatus(status, username);
    }

    public Job createNewJob(JobUpsertRequest jobUpsertRequest, Long id, String username) {
        Customer customer = customerRepository.getById(id);
        if (!customer.getApiUser().getUsername().equals(username)) {
            return null;
        }
        Job newJob = new Job();
        newJob.setCustomer(customer);
        newJob.setName(jobUpsertRequest.getName());
        newJob.setDescription(jobUpsertRequest.getDescription());
        newJob.setStatus(JobStatus.TO_DO);
        return newJob;
    }

    public Job updateJob(JobUpsertRequest jobUpsertRequest, Long customerId, Long jobId, String username) {
        Job job = findJobById(jobId, username);
        if (!job.getCustomer().getId().equals(customerId)
                || !job.getCustomer().getApiUser().getUsername().equals(username)) {
            return null; // validation
        }
        job.setName(jobUpsertRequest.getName());
        job.setDescription(jobUpsertRequest.getDescription());
        job.setStatus(jobUpsertRequest.getStatus());
        job.setFinished(jobUpsertRequest.isFinished());
        job.setPayed(jobUpsertRequest.isPayed());
        jobRepository.save(job);
        return job;
    }

    public void deleteJob(Long id, String username){
        Job job = findJobById(id, username);

        if (job != null) {
            jobRepository.deleteById(id);
        }
    }
}
