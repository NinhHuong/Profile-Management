package com.quocngay.profilemanagement.model;

import com.quocngay.profilemanagement.DBContext;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by HuongNTM5 on 3/15/2017.
 */

public class SemesterModel extends RealmObject {
    @PrimaryKey
    private int id;
    private String semesterName;
    private Date startDate;
    private Date endDate;

    public static SemesterModel create(int id, String semesterName, Date startDate, Date endDate) {
        SemesterModel semesterModel = new SemesterModel();
        semesterModel.id = id;
        semesterModel.semesterName = semesterName;
        semesterModel.startDate = startDate;
        semesterModel.endDate = endDate;
        return semesterModel;
    }

    public static SemesterModel createWithoutId(String semesterName, Date startDate, Date endDate) {
        SemesterModel semesterModel = new SemesterModel();
        DBContext dbContext = DBContext.getInst();
        semesterModel.id = dbContext.getMaxSemesterModelId() + 1;
        semesterModel.semesterName = semesterName;
        semesterModel.startDate = startDate;
        semesterModel.endDate = endDate;
        return semesterModel;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public int getId() {
        return id;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
