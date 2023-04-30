package com.gigmanager.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.gigmanager.models.enums.JobStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "job")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private JobStatus status;
    private boolean isFinished;
    private boolean isPayed;


    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @JsonIncludeProperties("name")
    private Customer customer;
}
