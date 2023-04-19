package com.nichoshop.main.util;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.SmsFactory;
import com.twilio.sdk.resource.instance.Sms;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.nichoshop.main.util.Environment.*;

/**
 * Created by Nursultan on 08/17/22.
 */

public class Twilio {
    private Logger log = LoggerFactory.getLogger(getClass());
    private TwilioRestClient client = new TwilioRestClient(twilio.accountSid, twilio.authToken);

    BasicNameValuePair from = new BasicNameValuePair("From", twilio.from);

    public void sendSMSToNumber(String number, String code) {

        BasicNameValuePair to = new BasicNameValuePair("To", "+" + number); // 44 7743114997, 17745389164
        BasicNameValuePair body = new BasicNameValuePair("Body", "You code is: " + code);
        log.info(" ${number}>>>>>>>>>> will sms with code " + code);

        SmsFactory smsFactory = client.getAccount().getSmsFactory();

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(to);
        params.add(from);
        params.add(body);
        try {
            Sms sms = smsFactory.create(params);
            log.info("sms sid: " + sms.getSid());
        } catch (TwilioRestException e) {
            log.info("SMS not sended:" + e.getMessage());
        }

    }
}
