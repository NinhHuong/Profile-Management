package com.quocngay.profilemanagement.model;

import com.quocngay.profilemanagement.DBContext;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ninh huong on 3/24/2017.
 */

public class SubjectModel extends RealmObject {
    @PrimaryKey
    private int id;
    private String name;
    private String code;
    private int credit;

    public static SubjectModel create(int id, String code, String name, int credit) {
        SubjectModel subjectModel = new SubjectModel();
        subjectModel.id = id;
        subjectModel.name = name;
        subjectModel.code = code;
        subjectModel.credit = credit;

        return subjectModel;
    }

    public static SubjectModel createWithoutId(String code, String name, int credit) {
        SubjectModel subjectModel = new SubjectModel();
        DBContext dbContext = DBContext.getInst();
        subjectModel.id = dbContext.getMaxSubjectModelId() + 1;
        subjectModel.name = name;
        subjectModel.code = code;
        subjectModel.credit = credit;

        return subjectModel;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getCredit() {
        return credit;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
