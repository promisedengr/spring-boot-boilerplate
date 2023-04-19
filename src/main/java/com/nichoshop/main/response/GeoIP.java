package com.nichoshop.main.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class GeoIP {
    public GeoIP(String ip, String country) {
        setIpAddress(ip);
        setCountry(country);
    }

    private String ipAddress;
    private String country;
}