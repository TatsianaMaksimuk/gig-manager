package com.gigmanager.controllers;

import com.gigmanager.models.Job;
import com.gigmanager.repositories.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/jobs")
@RequiredArgsConstructor
public class JobsController {

    private final JobRepository jobRepository;
    //GetAllJobs
    @GetMapping("/")
    public ResponseEntity<?> getAllJobs(HttpServletRequest request){
      String username = request.getUserPrincipal().getName();
        List<Job> jobs = jobRepository.findAllByCustomer_ApiUser_username(username);
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    //GetJobsByCustomerId
    @GetMapping("/customers/{id}")
    public ResponseEntity<?> getAllJobsByCustomer (@PathVariable Long id,  HttpServletRequest request){
        String username = request.getUserPrincipal().getName();
        List<Job> jobs = jobRepository.findAllByCustomer_ApiUser_usernameAndCustomer_id(username, id);
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    //GetJobById
    @GetMapping("/{id}")
    public ResponseEntity<?> getJobById(@PathVariable Long id, HttpServletRequest request){
        String username = request.getUserPrincipal().getName();
        Job requestedJob =  jobRepository.findById(id).orElse(null);
        if (requestedJob==null|| !requestedJob.getCustomer().getApiUser().getUsername().equals(username)){
            return null;
        }
        return new ResponseEntity<>(requestedJob, HttpStatus.OK);
    }


    //CreateJob
    //UpdateJob
    //DeleteJob
}
