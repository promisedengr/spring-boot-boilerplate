package com.nichoshop.main.util;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CaptchaClient {
    Logger logger = LoggerFactory.getLogger(CaptchaClient.class);

    public static Boolean checkCaptchaV2(String userResponse) throws IOException {
        HttpClient httpclient = HttpClients.createDefault();

        HttpPost post = new HttpPost(Environment.recaptcha.uri);

        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("secret", Environment.recaptcha.secret));
        params.add(new BasicNameValuePair("response", userResponse));

        post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        HttpResponse response = httpclient.execute(post);
        HttpEntity entity = response.getEntity();

        String res = "";

        if (Optional.of(entity).isPresent()) {

            InputStream instream = entity.getContent();
            try {

                res = IOUtils.toString(instream);

                EntityUtils.consumeQuietly(entity);
                instream.close();

                return res.contains("true");

            } catch (RuntimeException err) {
                return false;
            }
        } else
            return false;

    }
}
