package com.nichoshop.main.request;

import lombok.Data;

@Data
public class ReportRequest {
    private Long itemId;
    private Long reportType;
    private Long specificType;
    private Long detailType;
    private String content;
}
