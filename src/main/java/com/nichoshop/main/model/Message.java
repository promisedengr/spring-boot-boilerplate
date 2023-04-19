package com.nichoshop.main.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import com.nichoshop.main.util.converter.StringListConverter;

/**
 * Created by Nursultan 08/11/22.
 */
@Data
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    private String fromName = "";

    private Long fromId;

    private Long toId;
    private String subject;
    private String content;

    private Long itemId = 0L;
    private String itemTitle = "";

    @Convert(converter = StringListConverter.class)
    @Column
    private List<String> attached = new ArrayList<String>();
    @Column(name = "reply_msg_id", nullable = true)
    private Long replyMsgId = null;
    @Column(name = "order_id", nullable = true)
    private Long orderId = null;

    private Boolean msgRead = false;

    private Long folderId = 0L;

    private Boolean priority = false;

    private Boolean flag = false;
    private String reason = "";

    private int messageType = 0;
    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt = new Date();

}
