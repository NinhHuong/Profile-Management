package com.quocngay.profilemanagement.model;

import com.quocngay.profilemanagement.DBContext;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by HuongNTM5 on 3/17/2017.
 */

public class SubjectOfClassModel extends RealmObject {
    @PrimaryKey
    private int id;
    private SubjectModel subjectModel;
    private ClassModel classModel;
    private AccountModel AccountModel;

    public static SubjectOfClassModel create(int id, SubjectModel subjectModel, ClassModel classModel, AccountModel AccountModel) {
        SubjectOfClassModel sub = new SubjectOfClassModel();
        sub.id = id;
        sub.subjectModel= subjectModel;
        sub.classModel = classModel;
        sub.AccountModel = AccountModel;
        return sub;
    }

    public static SubjectOfClassModel createWithoutId(SubjectModel subjectModel, ClassModel classModel, AccountModel AccountModel) {
        SubjectOfClassModel sub = new SubjectOfClassModel();
        DBContext dbContext = DBContext.getInst();
        sub.id = dbContext.getMaxSubjectOfClassModelId() + 1;
        sub.subjectModel= subjectModel;
        sub.classModel = classModel;
        sub.AccountModel = AccountModel;
        return sub;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClassModel(ClassModel classModel) {
        this.classModel = classModel;
    }

    public void setAccountModel(AccountModel AccountModel) {
        this.AccountModel = AccountModel;
    }

    public int getId() {
        return id;
    }

    public void setSubjectModel(SubjectModel subjectModel) {
        this.subjectModel = subjectModel;
    }

    public SubjectModel getSubjectModel() {
        return subjectModel;
    }

    public ClassModel getClassModel() {
        return classModel;
    }

    public AccountModel getAccountModel() {
        return AccountModel;
    }
}
