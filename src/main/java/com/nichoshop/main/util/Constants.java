package com.nichoshop.main.util;

/**
 * Created by Nursultan on 08/16/22.
 */

public class Constants {

    public static int cacheTimeout = 5;

    public static Integer categoryDepth = 4;
    public static String tokenSecret = "NICHOSHOP2022NUR"; // Token secret key
    public static Integer tokenExpire = 60 * 60 * 1; // Token expire hour : 1
    public static Integer randomNameCheckIteration = 10;
    public static Integer signupExpire = 7200000;
    public static Integer itemPhotoSize = 3 * 1024 * 1024;
    public static Long cancelBidBlock = 1000 * 60 * 60L; // 1 hour

    public static class sucExpire {
        public static Long adminTemp = 1000 * 60 * 10L;// 10 minutes
        public static Long account = 1000 * 60 * 60 * 24L;// 24 hours
        public static Long addPhone = 1000 * 60 * 60 * 24L;// 24 hours
        public static Long tempPass = 1000 * 60 * 60 * 24L;// 24 hours
        public static Long forgotPass = 1000 * 60 * 60 * 24L;// 24 hours
        public static Long emailConfirm = 1000 * 60 * 60 * 24L;// 24 hours
    }

    public static class sucType {
        public static Integer forgotEmail = 0;
        public static Integer forgotText = 1;
        public static Integer tempEmail = 2;
        public static Integer tempText = 3;
        public static Integer addPhone = 4;
        public static Integer adminTemp = 5;
        public static Integer account = 6;
        public static Integer adminChangePhone = 7;
        public static Integer adminChangeEmail = 8;
    }

    public static class AccountType {
        public static Integer personal = 0;
        public static Integer business = 1;
        public static Integer system = 2;
    }

    public static class Condition {
        public static String New = "New";
        public static String used = "Used";
        public static String new_other = "New other";
        public static String new_other_see_details = "New other (see details)";
        public static String new_open_box = " New-open box";
        public static String new_with_tags = "New with tags";
        public static String new_without_tags = "New without tags";
        public static String new_with_box = "New with box";
        public static String new_without_box = "New without box";
        public static String new_with_defects = "New with defects";
        public static String brand_new = "Brand New";
        public static String like_new = "Like New";
        public static String open_box = "Open box";
        public static String pre_owned = "Pre-owned";
        public static String very_good = "Very Good";
        public static String good = "Good";
        public static String accessible = "Accessible";
        public static String certified_pre_owned = "Certified pre-owned";
        public static String certified_refurbished = "Certified refurbished";
        public static String seller_refurbished = "Seller refurbished";
        public static String remanufactured = "Remanufactured";
        public static String retread = "Retread";
        public static String for_parts_or_not_working = "For parts or not working";
        public static String damaged = "Damaged";
    }

    public static class MessageType {
        public static Integer general = 0;
        public static Integer reportBuyer = 1;
        public static Integer reportSeller = 2;
        public static Integer cancelOrder = 3;
        public static Integer refundOrder = 4;
        public static Integer contactBuyer = 5;
        public static Integer cancelItem = 6;
        public static Integer reviseFeedBack = 7;
        public static Integer contactSeller = 8;
        public static Integer contactUser = 9;
    }

    public static class MessageTopic {
        public static String contactBuyer = "Seller wants to contact you.";
        public static String contactSeller = "Buyer wants to contact you.";
        public static String contactUser = "I would like to contatct you";
    }

    public static class BankAccountType {
        public static Integer IBAN = 0;
        public static Integer GB = 1;
        public static Integer US = 2;
        public static Integer CA = 3;
        public static Integer OTHER = 4;
    }

    public static class RoleType {
        public static String admin = "admin";
        public static String cs = "customer support";

    }
}
