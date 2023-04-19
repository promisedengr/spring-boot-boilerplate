package com.nichoshop.main.model;

import lombok.Data;
import java.util.Date;
import javax.persistence.*;
import org.springframework.data.annotation.CreatedDate;

@Data
@Entity
@Table(name = "item_delete_logs")
public class ItemDeleteLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column private Long id;
    @Column private String reason;
    @Column private Long itemId;
    @Column private Long sellerId;
    @Column private String itemTitle;
    @Column(name = "created_at", nullable = false, updatable = false) @Temporal(TemporalType.TIMESTAMP) @CreatedDate private Date createdAt = new Date();

}
