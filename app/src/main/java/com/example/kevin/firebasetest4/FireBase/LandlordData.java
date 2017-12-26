package com.example.kevin.firebasetest4.FireBase;

/**
 * Created by kevin on 2017/12/4.
 */

public class LandlordData {
    private String name;
    private String type;
    private String password;
    private String email;
    private String phone;
    private String bank;
    private String bankNumber;
    private String accunt;
    private String passwordCheck;

    public LandlordData() {
    }

    public String getType() {
        return type;
    }

    public LandlordData(String name,String type, String password, String email, String phone, String bank, String bankNumber, String accunt, String passwordCheck) {
        this.name = name;
        this.type=type;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.bank = bank;
        this.bankNumber = bankNumber;
        this.accunt = accunt;
        this.passwordCheck = passwordCheck;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getBank() {
        return bank;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public String getAccunt() {
        return accunt;
    }

    public String getPasswordCheck() {
        return passwordCheck;
    }
}
