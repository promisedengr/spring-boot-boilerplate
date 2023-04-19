package com.nichoshop.main.util;

import org.apache.commons.mail.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 * Created by Nursultan on 08/16/22.
 */

public class EmailerBase {

    @Autowired
    private static Environment env;

    public static void send(String to, String subject, String message) throws EmailException {

        // Thread.currentThread().setContextClassLoader( getClass().getClassLoader() );
        // String config = env.getProperty("smtp");
        // log.info(config.root().render())
        String fromAddress = env.getProperty("smtp.from.address");
        String fromName = env.getProperty("smtp.from.name");

        Email mail = new SimpleEmail().setMsg(message);

        // val mail = new HtmlEmail()

        mail.addTo(to);

        mail.setStartTLSEnabled(Boolean.parseBoolean(env.getProperty("smtp.tls")));
        mail.setSSLOnConnect(Boolean.parseBoolean(env.getProperty("smtp.ssl")));
        mail.setHostName(env.getProperty("smtp.host"));

        mail.setSmtpPort(Integer.parseInt(env.getProperty("smtp.port")));
        mail.setAuthenticator(
                new DefaultAuthenticator(env.getProperty("smtp.login"), env.getProperty("smtp.password")));

        mail.setFrom(fromAddress, fromName);
        mail.setSubject(subject);

        mail.send();
    }
}
