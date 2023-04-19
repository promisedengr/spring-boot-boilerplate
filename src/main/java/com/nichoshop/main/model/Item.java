package com.nichoshop.main.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import com.nichoshop.main.util.converter.StringListConverter;

@Data
@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(name = "seller_id")
    private Long sellerId;
    @Column(name = "cat_id")
    private int categoryId;
    @Column
    private String title;
    @Column(name = "cond_id")
    private int condId;
    @Column(name = "cond_desc")
    private String condDesc;
    // @ElementCollection @Column(nullable = true) private List<String> images = new
    // ArrayList<String>();
    @Convert(converter = StringListConverter.class)
    @Column
    private List<String> images = new ArrayList<String>();

    @Column
    private String image;
    @Column(name = "item_desc", nullable = true)
    private String itemDesc;
    @Column(name = "listing_format")
    private int listingFormat;
    @Column(name = "price_type")
    private int priceType; // 1: Fixed Price, 2: Auction, 3: Classified ads
    @Column(name = "now_price")
    private int nowPrice;
    @Column
    private int currency;
    @Column
    private int quantity;
    @Column
    private int duration;
    @Column(name = "start_price")
    private int startPrice;
    @Column(name = "reserve_price")
    private int reservePrice;
    @Column
    private int state = 0; // 0: ACTIVE, 1: SOLD / OUT OF STOCK, 2: UNSOLD, 3: ARCHIVED
    @Column
    private int status;
    @Column(name = "is_multi")
    private Boolean isMulti = false;
    @Column
    private String nsln;
    @Column(name = "domestic_service")
    private int domesticService = 1;
    @Column(name = "domestic_service_cost")
    private int domesticServiceCost = 0;
    @Column(name = "another_service", nullable = true)
    private int anotherService;
    @Column(name = "another_service_cost", nullable = true)
    private int anotherServiceCost;
    @Column(name = "local_collect", nullable = true)
    private Boolean localCollect;
    @Column(name = "international_service", nullable = true)
    private int internationalService;
    @Column(name = "international_service_cost", nullable = true)
    private int internationalServiceCost;
    @Column(name = "dispatch_time")
    private int dispatchTime;
    @Column(name = "item_country")
    private String itemCountry;
    @Column(name = "item_city")
    private String itemCity;
    @Column(name = "return_days")
    private int returnDays;
    @Column
    private int returns = 0;
    @Column(name = "return_accept")
    private Boolean returnAccept = true;
    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt = new Date();
}
