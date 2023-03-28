package com.gigmanager.models.request;

import lombok.Data;

@Data
public class CustomerUpsertRequest {
    private String name;
    private String email;
}
