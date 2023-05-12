package com.gigmanager.repositories;

import com.gigmanager.models.Job;
import com.gigmanager.models.enums.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    //AllJobs
    List<Job> findAllByCustomer_ApiUser_username(String username);

    //query all jobs by customer id
    List<Job> findAllByCustomer_ApiUser_usernameAndCustomer_id(Long id, String username);

    List<Job> findAllByCustomer_ApiUser_usernameAndStatus(JobStatus status, String username);
}
