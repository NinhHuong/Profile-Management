package com.quocngay.profilemanagement.model;

import com.quocngay.profilemanagement.DBContext;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ninh huong on 3/24/2017.
 */

public class AccountModel extends RealmObject {
    @PrimaryKey
    private int id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private int gender;
    private String address;
    private String phoneNumber;
    private String rollNumber;
    private String email;
    private int role;
    private String photo;
    private boolean isNeedUpdate;

    public static AccountModel create(int id, String username, String password, String firstname, String lastname, int gender,
                                      String address, String phoneNumber, String rollNumber, String email, int role, String photo, boolean isNeedUpdate) {
        AccountModel accountModel = new AccountModel();
        accountModel.id = id;
        accountModel.username = username;
        accountModel.password = password;
        accountModel.firstname = firstname;
        accountModel.lastname = lastname;
        accountModel.email = email;
        accountModel.gender = gender;
        accountModel.address = address;
        accountModel.phoneNumber = phoneNumber;
        accountModel.rollNumber = rollNumber;
        accountModel.role = role;
        accountModel.photo = photo;
        accountModel.isNeedUpdate = isNeedUpdate;
        return accountModel;
    }

    public static AccountModel createWithoutId(String username, String password, String firstname, String lastname, int gender,
                                               String address, String phoneNumber, String rollNumber, String email, int role, String photo, boolean isNeedUpdate) {
        AccountModel accountModel = new AccountModel();
        DBContext dbContext = DBContext.getInst();
        accountModel.id = dbContext.getMaxAccountModelId() + 1;
        accountModel.username = username;
        accountModel.password = password;
        accountModel.firstname = firstname;
        accountModel.lastname = lastname;
        accountModel.email = email;
        accountModel.gender = gender;
        accountModel.address = address;
        accountModel.phoneNumber = phoneNumber;
        accountModel.rollNumber = rollNumber;
        accountModel.role = role;
        accountModel.photo = photo;
        accountModel.isNeedUpdate = isNeedUpdate;
        return accountModel;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setNeedUpdate(boolean needUpdate) {
        isNeedUpdate = needUpdate;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public int getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public int getRole() {
        return role;
    }

    public String getPhoto() {
        return photo;
    }

    public boolean isNeedUpdate() {
        return isNeedUpdate;
    }
}
