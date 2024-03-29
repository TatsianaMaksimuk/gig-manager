package com.gigmanager.controllers;


import com.gigmanager.models.Job;
import com.gigmanager.models.enums.JobStatus;
import com.gigmanager.models.request.JobUpsertRequest;
import com.gigmanager.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class JobsController {

    private final JobService jobService;

    //GetAllJobs
    @GetMapping("jobs/")
    public ResponseEntity<?> getAllJobs(HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        List<Job> jobs = jobService.readAllJobs(username); //username validation, checking user through customer
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    //GetJobById
    @GetMapping("jobs/{id}")
    public ResponseEntity<?> getJobById(@PathVariable Long id, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        Job requestedJob = jobService.findJobById(id, username);
        if (requestedJob == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(requestedJob, HttpStatus.OK);
    }


    //GetJobsByCustomerId
    @GetMapping("customers/{id}/jobs")
    public ResponseEntity<?> getAllJobsByCustomer(@PathVariable Long id, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        List<Job> jobs = jobService.findJobsByCustomerId(id, username);
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }


    //GetJobsByStatus
    @GetMapping("jobs/filter")
    public ResponseEntity<?> getJobsByStatus(@RequestParam String status, HttpServletRequest request) {
        JobStatus jobStatus = JobStatus.valueOf(status);
        String username = request.getUserPrincipal().getName();
        List<Job> jobs = jobService.filterJobsByStatus(jobStatus, username);
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    //CreateJob
    @PostMapping("customers/{id}/")
    public ResponseEntity<?> createJob(@RequestBody JobUpsertRequest jobUpsertRequest, @PathVariable Long id, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        Job newJob = jobService.createNewJob(jobUpsertRequest, id, username);
        return new ResponseEntity<>(newJob, HttpStatus.CREATED);
    }


    //UpdateJob - don't set customer
    @PostMapping("customers/{customerId}/jobs/{jobId}")
    public ResponseEntity<?> updateJob(@RequestBody JobUpsertRequest jobUpsertRequest, @PathVariable Long customerId, @PathVariable Long jobId, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        Job updatedJob = jobService.updateJob(jobUpsertRequest, customerId, jobId, username);
        if (updatedJob == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedJob, HttpStatus.OK);
    }

    //DeleteJob

    @DeleteMapping("jobs/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable Long id, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        jobService.deleteJob(id, username);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
