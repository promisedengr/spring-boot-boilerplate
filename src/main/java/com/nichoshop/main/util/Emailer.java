package com.nichoshop.main.util;

import org.apache.commons.mail.EmailException;

import com.nichoshop.main.model.PasswordReset;
import com.nichoshop.main.model.User;

public class Emailer {
    public static String hostWithRootPath = "https://api.nichoshop.com/api/v1";

    public static void sendEmailConfirmation(User user, String code) throws EmailException {
        // TODO
        String message = "Hello" + user.getFname() + " " + user.getLname() +

                "This email address was used to register on www.nichoshop.com" + "\n" +
                "To confirm this address, please, click on link:" + "\n" +
                "If you did not register on $host, ignore this message." + "\n" +
                hostWithRootPath + "/signup/confirm?code=" + code + "\n" +

                "With best regards," + "\n" +
                "NichoShop Team";

        EmailerBase.send(user.getEmail(), "Nichoshop Account Confirmation", message);
    }

    public static void sendPasswordReset(User user, PasswordReset passwordReset) throws EmailException {
        String message = "Hello" + user.getFname() + " " + user.getLname() + "\n" +

                "password reset was requested for this email." + "\n" +
                "If you did not ask for that, please, ignore this message. Otherwise" + "\n" +
                "use link below to reset your password." + "\n" +
                hostWithRootPath + "/reset?code=" + passwordReset.getHash() + "\n" +
                "With best regards" + "\n" +
                "NichoShop Team";

        EmailerBase.send(user.getEmail(), "nichoshop.com reset password", message);
    }

    public static void sendSUCConfirmation(User user, String suc) throws EmailException {
        String message = "Hello " + user.getFname() + user.getLname() +

                "If you forgot password, please use this code." + "\n" +
                "It can be used once and never again." + "\n" +
                "SUC = " + suc + "\n" +
                "With best regards," + "\n" +
                "NichoShop Team";

        EmailerBase.send(user.getEmail(), "nichoshop.com suc confirmation", message);
    }

    public static void sendUsernameChange(User user, String prevUsername) throws EmailException {
        String message = "Hello " + user.getFname() + user.getLname() + "\n" +

                "Your username is renamed from " + prevUsername + " to " + user.getUsername() + "\n" +
                "With best regards,\n" +
                "NichoShop Team";
        EmailerBase.send(user.getEmail(), "nichoshop.com reset username", message);
    }

    public static void sendPasswordChange(User user, String password) throws EmailException {
        String message = "Hello " + user.getFname() + user.getLname() + "\n" +
                "Your password was changed to " + password + "\n" +
                "Please remember this password." + "\n" +
                "With best regards," + "\n" +
                "NichoShop Team";
        EmailerBase.send(user.getEmail(), "nichoshop.com reset username", message);
    }

}
