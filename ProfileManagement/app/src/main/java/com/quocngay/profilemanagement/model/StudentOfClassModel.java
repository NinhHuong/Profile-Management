package com.quocngay.profilemanagement.model;
import com.quocngay.profilemanagement.DBContext;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by bonz on 3/14/17.
 */

public class StudentOfClassModel extends RealmObject {
    @PrimaryKey
    private int id;

    private SubjectOfClassModel subjectOfClassModel;
    private AccountModel accountModel;

    public static StudentOfClassModel create(int id, SubjectOfClassModel subjectOfClassModel, AccountModel accountModel) {
        StudentOfClassModel studentOfClassModel = new StudentOfClassModel();
        studentOfClassModel.id = id;
        studentOfClassModel.subjectOfClassModel = subjectOfClassModel;
        studentOfClassModel.accountModel = accountModel;
        return studentOfClassModel;
    }

    public static StudentOfClassModel createWithoutId(SubjectOfClassModel subjectOfClassModel, AccountModel accountModel) {
        StudentOfClassModel studentOfClassModel = new StudentOfClassModel();
        DBContext dbContext = DBContext.getInst();
        studentOfClassModel.id = dbContext.getMaxStudentOfClassId() + 1;
        studentOfClassModel.subjectOfClassModel = subjectOfClassModel;
        studentOfClassModel.accountModel = accountModel;
        return studentOfClassModel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SubjectOfClassModel getClassModel() {
        return subjectOfClassModel;
    }

    public AccountModel getAccountModel() {
        return accountModel;
    }

    public void setClassModel(SubjectOfClassModel subjectOfClassModel) {
        this.subjectOfClassModel = subjectOfClassModel;
    }

    public void setAccountModel(AccountModel accountModel) {
        this.accountModel = accountModel;
    }
}
