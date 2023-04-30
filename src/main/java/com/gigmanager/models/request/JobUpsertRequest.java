package com.gigmanager.models.request;

import com.gigmanager.models.enums.JobStatus;
import lombok.Data;

@Data
public class JobUpsertRequest {
    private String name;
    private String description;
    private JobStatus status;
    private boolean isFinished;
    private boolean isPayed;

}
