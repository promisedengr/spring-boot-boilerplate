package com.nichoshop.main.model;

import javax.persistence.*;

@Entity
@Table(name = "bank_accounts")
public class BankAccount {

    @Id @Column private Long id;
    @Column private int userId;
    @Column private int accountType;
    @Column private String country;
    @Column private String street;
    @Column private String apartment;
    @Column private String city;
    @Column private String state;
    @Column private String zip;
    @Column private String holdername;
    @Column(nullable = true) private String iban;
    @Column(nullable = true) private String bic;
    @Column(name = "sort_code", nullable = true) private String sortCode;
    @Column(name = "digit_routing_number", nullable = true) private String digitRoutingNumber;
    @Column(name = "branch_code", nullable = true) private String branchCode;
    @Column(name = "insituation_num", nullable = true) private String inSituationNum;
    @Column(name = "bank_name", nullable = true) private String bankName;
    @Column(name = "account_num", nullable = true) private String accountNum;
    @Column private String currency = "usd";

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getAccountType() {
        return accountType;
    }
    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getApartment() {
        return apartment;
    }
    public void setApartment(String apartment) {
        this.apartment = apartment;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getZip() {
        return zip;
    }
    public void setZip(String zip) {
        this.zip = zip;
    }
    public String getHoldername() {
        return holdername;
    }
    public void setHoldername(String holdername) {
        this.holdername = holdername;
    }
    public String getIban() {
        return iban;
    }
    public void setIban(String iban) {
        this.iban = iban;
    }
    public String getBic() {
        return bic;
    }
    public void setBic(String bic) {
        this.bic = bic;
    }
    public String getSortCode() {
        return sortCode;
    }
    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }
    public String getDigitRoutingNumber() {
        return digitRoutingNumber;
    }
    public void setDigitRoutingNumber(String digitRoutingNumber) {
        this.digitRoutingNumber = digitRoutingNumber;
    }
    public String getBranchCode() {
        return branchCode;
    }
    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }
    public String getInSituationNum() {
        return inSituationNum;
    }
    public void setInSituationNum(String inSituationNum) {
        this.inSituationNum = inSituationNum;
    }
    public String getBankName() {
        return bankName;
    }
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    public String getAccountNum() {
        return accountNum;
    }
    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }

}
