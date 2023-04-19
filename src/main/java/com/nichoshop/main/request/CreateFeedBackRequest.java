package com.nichoshop.main.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CreateFeedBackRequest {
    private int serler;
    private int rating;
    private int itemAsDescribed;
    private int communication;
    private int shippingTime;
    private int shippingCharges;
    
            // sellId: Int, 
            // rating: Int, 
            // itemAsDescribed: Int, 
            // communication: Int, 
            // shippingTime: Int, 
            // shippingCharges: Int, 
            // message: Option[String] = None
    
}
