package com.nichoshop.main.request;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CreateItemRequest {
    private int id;
    private int sellerId;
    private int catId;
    private String title;
    private int condId;
    private String condDesc;
    private List<String> images;
    private String image;
    private String itemDesc;
    private int listingFormat = 0;
    private int nowPrice = 0;
    private int currency;
    private int quantity = 0;
    private int duration = 0;
    private int startPrice = 0;
    private int reservePrice = 0;
    private int state = 0;
    private int status = 0;
    private Boolean isMultivariation = false;
    private int domesticService;
    private int domesticServiceCost;
    private int anotherService;
    private int anotherServiceCost;
    private Boolean localCollect = false;
    private int internationalService;
    private int internationalServiceCost;
    private int dispatchTime;
    private String itemCountry;
    private String itemCity;
    private int returnDays;
    private int returns;
    private Boolean returnAccept;
    // private Object postInfo;
    // postInfo: PostInfo

}
