package com.nichoshop.main.request;

import java.util.List;

import lombok.Data;

@Data
public class DeleteIdsRequest {
    private List<Long> ids;
    private String reason;
}
