package com.gigmanager.repositories;

import com.gigmanager.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    //query all jobs by customer id
    List<Job> filterAllByCustomer_id(Long id);
}
