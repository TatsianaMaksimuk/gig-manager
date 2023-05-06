package com.gigmanager.models.request;

import com.gigmanager.models.enums.ItemStatus;
import lombok.Data;

@Data
public class ItemUpsertRequest {
    private String name;
    private String description;
    private ItemStatus status;
    private String stage;
    private boolean isFinished;
}
