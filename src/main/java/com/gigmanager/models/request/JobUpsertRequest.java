package com.gigmanager.models.request;

import com.gigmanager.models.Customer;
import lombok.Data;

@Data
public class JobUpsertRequest {
    private String name;
    private String description;
    private boolean isFinished;
    private boolean isPayed;
    private Customer customer;
}
