package com.nichoshop.main.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CreatemessageRequest {
    Long toId;
    String topic;
    String content;
    Long itemId;
    String itemTitle;
    MultipartFile[] files;
}
