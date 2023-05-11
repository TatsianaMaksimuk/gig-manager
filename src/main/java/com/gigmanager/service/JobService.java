package com.gigmanager.service;

import com.gigmanager.models.Job;
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
        return jobRepository.findAllByCustomer_ApiUser_usernameAndCustomer_id(username, id);
    }
}
