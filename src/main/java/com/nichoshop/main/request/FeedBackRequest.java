package com.nichoshop.main.request;

import lombok.Data;

@Data
public class FeedBackRequest {

    private Long id;
    private Long itemId;
    private Long userId;
    private String content;
    private int rate;
}
