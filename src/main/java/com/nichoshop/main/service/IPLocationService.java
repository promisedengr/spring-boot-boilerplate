package com.nichoshop.main.service;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import com.nichoshop.main.response.GeoIP;

public class IPLocationService {
    private DatabaseReader dbReader;

    public IPLocationService() throws IOException {
        File database = new File("./GeoLite2-Country.mmdb");
        dbReader = new DatabaseReader.Builder(database).build();
    }

    public GeoIP getLocation(String ip)
            throws IOException, GeoIp2Exception {
        InetAddress ipAddress = InetAddress.getByName(ip);
        CountryResponse response = dbReader.country(ipAddress);

        String countryName = response.getCountry().getName();
        return new GeoIP(ip, countryName);
    }
}