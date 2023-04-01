package com.gigmanager.repositories;

import com.gigmanager.models.Job;
import com.gigmanager.models.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    //query all jobs by customer id
    List<Job> findAllByCustomer_id(Long id);
    List<Job> findAllByStatus(JobStatus status);
}
