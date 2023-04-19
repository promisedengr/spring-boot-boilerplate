package com.nichoshop.main.request;

import lombok.Data;

@Data
public class AdminNoteCreateRequest {
    private String subject;
    private String desc;
    private int csId;
}
