package com.quocngay.profilemanagement.activity;

import android.accounts.Account;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;

import com.quocngay.profilemanagement.DBContext;
import com.quocngay.profilemanagement.R;
import com.quocngay.profilemanagement.model.AccountModel;
import com.quocngay.profilemanagement.model.ClassModel;
import com.quocngay.profilemanagement.model.SemesterModel;
import com.quocngay.profilemanagement.model.StudentOfClassModel;
import com.quocngay.profilemanagement.model.SubjectModel;
import com.quocngay.profilemanagement.model.SubjectOfClassModel;
import com.quocngay.profilemanagement.other.Constanst;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private DBContext dbContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbContext = DBContext.getInst();

        addSampleData();

        Intent intent = new Intent(this, OngoingClassActivity.class);
        startActivity(intent);
    }

    //create sample
    private void addSampleData() {
        Calendar c = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        //account
        Bitmap tuanna = BitmapFactory.decodeResource(getResources(), R.drawable.tuanna);
        AccountModel stu1 = AccountModel.create(1, "tuanna", "tuanna", "Tuan", "Nguyen Anh", Constanst.KEY_GENDER_MALE, "Hanoi",
                "0123456789", "SE01234", "tuannase01234@fpt.edu.vn", Constanst.KEY_ROLL_STUDENT, BitMapToString(tuanna), false);
        Bitmap sidq = BitmapFactory.decodeResource(getResources(), R.drawable.sidq);
        AccountModel stu2 = AccountModel.create(2, "sidq", "sidq", "Si", "Nguyen Quang", Constanst.KEY_GENDER_MALE, "Hanoi",
                "0123456789", "SE01235", "sidqse01235@fpt.edu.vn", Constanst.KEY_ROLL_STUDENT, BitMapToString(sidq), false);
        Bitmap longhd = BitmapFactory.decodeResource(getResources(), R.drawable.longhd);
        AccountModel stu3 = AccountModel.create(3, "longhd", "longhd", "Long", "Hoang Duc", Constanst.KEY_GENDER_MALE, "Hanoi",
                "0123456789", "SE01236", "longhdse01236@fpt.edu.vn", Constanst.KEY_ROLL_STUDENT, BitMapToString(longhd), false);
        AccountModel tea1 = AccountModel.create(4, "anhbn", "anhbn", "Anh", "Bui Ngoc", Constanst.KEY_GENDER_MALE, "Hanoi",
                "0123456789", "XYZ", "anhbn@fpt.edu.vn", Constanst.KEY_ROLL_TEACHER, null, false);
        dbContext.addAccountModel(stu1);
        dbContext.addAccountModel(stu2);
        dbContext.addAccountModel(tea1);
        //semester
        c.set(2016, 9, 1);
        c2.set(2017, 12, 31);
        SemesterModel ses1 = SemesterModel.create(1, "Fall 2016", c.getTime(), c2.getTime());
        c.set(2017, 1, 5);
        c.set(2017, 5, 5);
        SemesterModel ses2 = SemesterModel.create(2, "Spring 2017", c.getTime(), c2.getTime());
        dbContext.addSemesterModel(ses1);
        dbContext.addSemesterModel(ses2);
        //temp class
        ClassModel class1 = ClassModel.create(5, "ES20102", ses1);
        dbContext.addClass(class1);
        //subject
        SubjectModel sub1 = SubjectModel.create(2, "ESS", "Embed System", 3);
        SubjectModel sub2 = SubjectModel.create(3, "SSC", "Soft Skill Comunication",3);
        SubjectModel sub3 = SubjectModel.create(4, "SPM", "Software Project Management",3);
        SubjectModel sub4 = SubjectModel.create(5, "VNR", "Vietnam Revolution",3);
        dbContext.addSubjectModel(sub1);
        dbContext.addSubjectModel(sub2);
        dbContext.addSubjectModel(sub3);
        dbContext.addSubjectModel(sub4);
        //subject of class
        SubjectOfClassModel subOfClass1 = SubjectOfClassModel.create(1, sub1, class1, tea1);
        dbContext.addSubjectOfClassModel(subOfClass1);
        //student of class
        StudentOfClassModel stuClass1 = StudentOfClassModel.create(1, subOfClass1, stu1);
        StudentOfClassModel stuClass2 = StudentOfClassModel.create(2, subOfClass1, stu2);
        StudentOfClassModel stuClass3 = StudentOfClassModel.create(3, subOfClass1, stu3);
        dbContext.addStudentOfClass(stuClass1);
        dbContext.addStudentOfClass(stuClass2);
        dbContext.addStudentOfClass(stuClass3);
    }

    private String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    private Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
