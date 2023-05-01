package com.gigmanager.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.gigmanager.models.enums.ItemStatus;
import com.gigmanager.models.enums.ItemType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private ItemType type;

    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    private String stage;
    private boolean isFinished;

    @ManyToOne
    @JoinColumn(name = "job_id", referencedColumnName = "id")
    @JsonIncludeProperties({"name", "status"})
    private Job job;
}
