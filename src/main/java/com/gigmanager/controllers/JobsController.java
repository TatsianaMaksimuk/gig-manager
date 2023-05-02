package com.gigmanager.controllers;

import com.gigmanager.models.Customer;
import com.gigmanager.models.Job;
import com.gigmanager.models.enums.JobStatus;
import com.gigmanager.models.request.JobUpsertRequest;
import com.gigmanager.repositories.CustomerRepository;
import com.gigmanager.repositories.JobRepository;
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

    private final JobRepository jobRepository;
    private final CustomerRepository customerRepository;
    //GetAllJobs
    @GetMapping("jobs/")
    public ResponseEntity<?> getAllJobs(HttpServletRequest request){
      String username = request.getUserPrincipal().getName();
        List<Job> jobs = jobRepository.findAllByCustomer_ApiUser_username(username); //username validation, checking user through customer
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    //GetJobById
    @GetMapping("jobs/{id}")
    public ResponseEntity<?> getJobById(@PathVariable Long id, HttpServletRequest request){
        String username = request.getUserPrincipal().getName();
        Job requestedJob =  jobRepository.findById(id).orElse(null);
        if (requestedJob==null|| !requestedJob.getCustomer().getApiUser().getUsername().equals(username)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(requestedJob, HttpStatus.OK);
    }


    //GetJobsByCustomerId
    @GetMapping("customers/{id}/jobs")
    public ResponseEntity<?> getAllJobsByCustomer (@PathVariable Long id,  HttpServletRequest request){
        String username = request.getUserPrincipal().getName();
        List<Job> jobs = jobRepository.findAllByCustomer_ApiUser_usernameAndCustomer_id(username, id);
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }


    //GetJobsByStatus
    @GetMapping("jobs/filter")
    public ResponseEntity<?> getJobsByStatus(@RequestParam JobStatus status, HttpServletRequest request){
        String username = request.getUserPrincipal().getName();
        List<Job> jobs = jobRepository.findAllByCustomer_ApiUser_usernameAndStatus(username, status);
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    //CreateJob
    @PostMapping("customers/{id}/")
    public ResponseEntity<?> createJob (@RequestBody JobUpsertRequest jobUpsertRequest, Long id, HttpServletRequest request){
        String username = request.getUserPrincipal().getName();
        Customer jobCustomer = customerRepository.getById(id);
        if (!jobCustomer.getApiUser().getUsername().equals(username)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Job newJob = new Job();
        newJob.setCustomer(jobCustomer);
        newJob.setName(jobUpsertRequest.getName());
        newJob.setDescription(jobUpsertRequest.getDescription());
        newJob.setStatus(JobStatus.TO_DO);
//        newJob.setFinished(jobUpsertRequest.isFinished()); - false from start
//        newJob.setPayed(jobUpsertRequest.isPayed());

        return new ResponseEntity<>(newJob, HttpStatus.CREATED);
    }


    //UpdateJob - don't set customer
    @PostMapping("customers/{customerId}/jobs/{jobId}")
    public ResponseEntity<?> updateJob (@RequestBody JobUpsertRequest jobUpsertRequest, Long customerId, Long jobId, HttpServletRequest request){
        String username = request.getUserPrincipal().getName();
        Job job = jobRepository.getById(customerId);
        if (!job.getCustomer().getId().equals(customerId)
         || !job.getCustomer().getApiUser().getUsername().equals(username)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // validation
        }
        job.setName(jobUpsertRequest.getName());
        job.setDescription(jobUpsertRequest.getDescription());
        job.setStatus(jobUpsertRequest.getStatus());
        job.setFinished(jobUpsertRequest.isFinished());
        job.setPayed(jobUpsertRequest.isPayed());


        return new ResponseEntity<>(job, HttpStatus.OK);
    }

    //DeleteJob

    @DeleteMapping("jobs/{id}")
    public ResponseEntity<?> deleteJob (@PathVariable Long id, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        Job requestedJob = jobRepository.findById(id).orElse(null);
        if (requestedJob == null || !requestedJob.getCustomer().getApiUser().getUsername().equals(username)) {
            return null;
        }
        jobRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
