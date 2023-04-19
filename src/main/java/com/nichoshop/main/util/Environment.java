package com.nichoshop.main.util;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Created by Nursultan on 08/17/22.
 */
public class Environment {

        // public static Config config = ConfigFactory.load("application.properties");
        public static Config config = ConfigFactory.load("application.conf");

        public static Boolean isProduction = config.getString("environment.type") == "production";

        public static Boolean skipAuth = config.getBoolean("skipAuth");

        public static String adminId = isProduction ? config.getString("adminId.stable")
                        : config.getString("adminId.test");

        public static class recaptcha {
                public static String uri = isProduction ? config.getString("recaptcha.stable.uri")
                                : config.getString("recaptcha.test.uri");

                public static String secret = isProduction ? config.getString("recaptcha.stable.secret")
                                : config.getString("recaptcha.test.secret");
        }

        public static Boolean skipCaptcha = isProduction ? config.getBoolean("recaptcha.stable.skip")
                        : config.getBoolean("recaptcha.test.skip");

        public static String host = config.getString("nichoshop.host");

        public static String apiHost = config.getString("nichoshop.api-host");

        public static String hostWithRootPath = config.getString("nichoshop.protocol") + "://" + apiHost
                        + config.getString("nichoshop.root-path");

        public static class twilio {
                public static String accountSid = isProduction ? config.getString("twilio.stable.accountSid")
                                : config.getString("twilio.test.accountSid");

                public static String authToken = isProduction ? config.getString("twilio.stable.authToken")
                                : config.getString("twilio.test.authToken");

                public static String from = isProduction ? config.getString("twilio.stable.from")
                                : config.getString("twilio.test.from");
        }

        public static class duo {
                public static String clientId = isProduction ? config.getString("duo.stable.client-id")
                                : config.getString("duo.test.client-id");

                public static String clientSecret = isProduction ? config.getString("duo.stable.client-secret")
                                : config.getString("duo.test.client-secret");

                public static String apiHost = isProduction ? config.getString("duo.stable.api-host")
                                : config.getString("duo.test.api-host");

                public static String redirectUri = isProduction ? config.getString("duo.stable.redirect-uri")
                                : config.getString("duo.test.redirect-uri");

                public static String redirectSUCUri = isProduction ? config.getString("duo.stable.redirect-suc-uri")
                                : config.getString("duo.test.redirect-suc-uri");

        }

}
