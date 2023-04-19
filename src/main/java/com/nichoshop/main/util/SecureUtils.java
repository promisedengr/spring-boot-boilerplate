package com.nichoshop.main.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.*;

public class SecureUtils {

    public static int TOKEN_LENGTH = 45;

    public static String TOKEN_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_.-";
    public static SecureRandom secureRandom = new SecureRandom();
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static String toHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    private static String generateToken(int tokenLength) {
        int l = TOKEN_CHARS.length();

        return loop("", tokenLength, l);
    }

    private static String loop(String acc, int i, int l) {
        if (i == 0)
            return acc;
        else
            return loop(acc + TOKEN_CHARS.charAt(secureRandom.nextInt(l)), i - 1, l);
    }

    private static String md5(String s) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        return toHex(MessageDigest.getInstance("MD5").digest(s.getBytes("UTF-8")));
    }

    private static String sha(String s) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return toHex(MessageDigest.getInstance("SHA-256").digest(s.getBytes("UTF-8")));
    }

    public static String generateMD5Token(String tokenprefix)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return md5(tokenprefix + System.nanoTime() + generateToken(TOKEN_LENGTH));
    }

    public static String generateSHAToken(String tokenprefix)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return sha(tokenprefix + System.nanoTime() + generateToken(TOKEN_LENGTH));
    }

    public static String generateSMSCode() {
        Integer result = 100000 + secureRandom.nextInt(900000);
        return result.toString();
    }

    public static String generateJWTToken(String tokenSecret, Integer tokenExpire, Map<String, Object> claims)
            throws UnsupportedEncodingException {
        JwtBuilder jwt = Jwts.builder()
                // .setId(UUID.randomUUID.toString())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(tokenExpire))) // only available for 24hours
                .signWith(SignatureAlgorithm.HS512, tokenSecret.getBytes("UTF-8"));

        claims.forEach((key, value) -> {
            jwt.claim(key, value);
        });
        return jwt.compact();
    }

    public static Map<String, Object> verifyJWTToken(String tokenSecret, String token)
            throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException,
            IllegalArgumentException, UnsupportedEncodingException {

        Claims claims = Jwts.parser()
                .setSigningKey(tokenSecret.getBytes("UTF-8"))
                .parseClaimsJws(token) // we can trust this JWT
                .getBody();
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("id", claims.get("id"));
        hm.put("username", claims.get("id"));
        hm.put("email", claims.get("id"));

        return hm;
    }

    public static String generateSUC(List<String> usedSUC) {
        List<Integer> sucList = IntStream.rangeClosed(100000, 999999)
                .boxed().collect(Collectors.toList());

        usedSUC.forEach(s -> {

            int idx = sucList.indexOf(Integer.parseInt(s));
            if (idx > 0)
                sucList.remove(idx);
        });
        return String.valueOf(sucList.get(secureRandom.nextInt(sucList.size())));

    }

    public static String generateUsername(String fullname, String countryCode) {
        /**
         * First 6 letters of the last name / family + dot + the first 4 letters of the
         * first name
         * + 5 random numbers + the symbols of their country, for example
         * "abcdef.ghij13254gb".
         * If the first name is shorter than 4 letters, in this case less than 4 letters
         * of the name will be used
         */
        String[] names = fullname.split(" ");
        String fname = names[0];
        String lname = names[1];
        if (fname == null || lname == null)
            return "";

        String randomNum = Integer.toString(10000 + secureRandom.nextInt(90000));
        lname = lname.length() > 5 ? lname.substring(0, 6) : lname;
        fname = fname.length() > 3 ? fname.substring(0, 4) : fname;
        String username = lname + "." + fname + randomNum + countryCode.toLowerCase();
        return username;
    }

    public static String loop_order(String acc, Integer i) {
        if (i == 0)
            return acc;
        else
            return loop_order("0" + acc, i);
    }

    public static String generateOrderId(Integer serialNum) {
        /**
         * Order ID - the identification number of this order will be displayed.
         * The system will generate a unique identification number for each order.
         * The order number is composed of 14 digits, the first 8 digits are the serial
         * number of the order for that day (starting from 00000001),
         * then a dash and the next 6 digits, which are DD MM YY i.e. the day, month and
         * year when this order was placed, for example 00000001-010121.
         */
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("DDMMYY");

        String dateString = ft.format(dNow).toString();

        String serialString = serialNum.toString();

        if (serialString.length() > 7)
            serialString = serialString.substring(0, 8);
        else {
            Integer num = 8 - serialString.length();
            loop_order(serialString, num);
        }
        String orderId = serialString + dateString;
        return orderId;
    }

    public static Boolean isValidPhoneNumber(String phone) {
        // (0/91): number starts with (0/91)
        // [7-9]: starting of the number may contain a digit between 0 to 9
        // [0-9]: then contains digits 0 to 9
        Pattern ptrn = Pattern.compile("^\\+?\\d{6,18}$");
        // the matcher() method creates a matcher that will match the given input
        // against this pattern
        Matcher matche = ptrn.matcher(phone);
        // returns a boolean value
        return (matche.find() && matche.group().equals(phone));
    }

    public static String getIpFromRequest(HttpServletRequest request) {

        String ip = request.getHeader("X-FORWARDED-FOR");
        if (ip == null) {
            return request.getRemoteAddr();
        } else
            return ip;
    }

}
