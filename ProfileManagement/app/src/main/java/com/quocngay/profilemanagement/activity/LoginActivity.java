package com.quocngay.profilemanagement.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LoginActivity extends Activity implements View.OnClickListener {

    private Button btnLogin;
    private EditText edtAccount, edtPassword;

    private TextView btnSignup;

    private DBContext dbContext;
    private List<AccountModel> listAccount;

    private NotificationCompat.Builder notBuilder;

    private static final int MY_NOTIFICATION_ID = 12345;

    private static final int MY_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.notBuilder = new NotificationCompat.Builder(this);
        this.notBuilder.setAutoCancel(true);

        init();
        if(dbContext.getAllAccountModel().size()==0){
            addSampleData();
        }
    }

    private void init() {
        //link
        btnLogin = (Button) findViewById(R.id.btn_login);
        edtAccount = (EditText) findViewById(R.id.edt_account);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        btnSignup =(TextView)findViewById(R.id.link_signup);

        //event
        btnLogin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);

        dbContext = DBContext.getInst();
        listAccount = new ArrayList<>();
    }

    //create sample
    private void addSampleData() {
        Calendar c = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        //account
        Bitmap tuanna = BitmapFactory.decodeResource(getResources(), R.drawable.tuanna);
        AccountModel stu1 = AccountModel.createWithoutId("tuanna", "tuanna", "Tuan", "Nguyen Anh", Constanst.KEY_GENDER_MALE, "Hanoi",
                "0123456789", "SE01234", "tuannase01234@fpt.edu.vn", Constanst.KEY_ROLL_STUDENT, BitMapToString(tuanna), false);
        dbContext.addAccountModel(stu1);
        Bitmap sidq = BitmapFactory.decodeResource(getResources(), R.drawable.sidq);
        AccountModel stu2 = AccountModel.createWithoutId("sidq", "sidq", "Si", "Nguyen Quang", Constanst.KEY_GENDER_MALE, "Hanoi",
                "0123456789", "SE01235", "sidqse01235@fpt.edu.vn", Constanst.KEY_ROLL_STUDENT, BitMapToString(sidq), false);
        dbContext.addAccountModel(stu2);
        Bitmap longhd = BitmapFactory.decodeResource(getResources(), R.drawable.longhd);
        AccountModel stu3 = AccountModel.createWithoutId("longhd", "longhd", "Long", "Hoang Duc", Constanst.KEY_GENDER_MALE, "Hanoi",
                "0123456789", "SE01236", "longhdse01236@fpt.edu.vn", Constanst.KEY_ROLL_STUDENT, BitMapToString(longhd), false);
        dbContext.addAccountModel(stu3);
        Bitmap anhbn = BitmapFactory.decodeResource(getResources(), R.drawable.anhbn);
        AccountModel tea1 = AccountModel.createWithoutId("anhbn", "anhbn", "Anh", "Bui Ngoc", Constanst.KEY_GENDER_MALE, "Hanoi",
                "0123456789", "XYZ", "anhbn@fpt.edu.vn", Constanst.KEY_ROLL_TEACHER, BitMapToString(anhbn), false);
        dbContext.addAccountModel(tea1);
        //semester
        c.set(2016, 9, 1);
        c2.set(2017, 12, 31);
        SemesterModel ses1 = SemesterModel.createWithoutId("Fall 2016", c.getTime(), c2.getTime());
        dbContext.addSemesterModel(ses1);
        c.set(2017, 1, 5);
        c.set(2017, 5, 5);
        SemesterModel ses2 = SemesterModel.createWithoutId("Spring 2017", c.getTime(), c2.getTime());
        dbContext.addSemesterModel(ses2);
        //temp class
        ClassModel class1 = ClassModel.createWithoutId("ES20102", ses1);
        dbContext.addClass(class1);
        //subject
        SubjectModel sub1 = SubjectModel.createWithoutId("ESS", "Embedded System", 3);
        dbContext.addSubjectModel(sub1);
        SubjectModel sub2 = SubjectModel.createWithoutId("SSC", "Soft Skill Comunication",3);
        dbContext.addSubjectModel(sub2);
        SubjectModel sub3 = SubjectModel.createWithoutId("SPM", "Software Project Management",3);
        dbContext.addSubjectModel(sub3);
        SubjectModel sub4 = SubjectModel.createWithoutId("VNR", "Vietnam Revolution",3);
        dbContext.addSubjectModel(sub4);
        //subject of class
        SubjectOfClassModel subOfClass1 = SubjectOfClassModel.createWithoutId(sub1, class1, tea1);
        dbContext.addSubjectOfClassModel(subOfClass1);
        //student of class
        StudentOfClassModel stuClass1 = StudentOfClassModel.createWithoutId(subOfClass1, stu1);
        dbContext.addStudentOfClass(stuClass1);
        StudentOfClassModel stuClass2 = StudentOfClassModel.createWithoutId(subOfClass1, stu2);
        dbContext.addStudentOfClass(stuClass2);
        StudentOfClassModel stuClass3 = StudentOfClassModel.createWithoutId(subOfClass1, stu3);
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_login:
                onClickLogin();
                break;
            case R.id.link_signup:
                Intent intent = new Intent(this, SignupActivity.class);
                startActivity(intent);
            default:
        }
    }

    private void onClickLogin() {
        String account = edtAccount.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        if (!account.isEmpty() && !password.isEmpty()) {
            AccountModel acc = dbContext.getLoginAccount(account, password);
            if (acc != null) {
                if(acc.isNeedUpdate())
                    notiButtonClicked(acc.getId());
                Intent intent = new Intent(this, OngoingClassActivity.class);
                intent.putExtra("accountIdRoot", acc.getId());
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Account & Password Incorrect !!!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Please enter Account & Password!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void notiButtonClicked(int accId)  {

        // --------------------------
        // Chuẩn bị một thông báo
        // --------------------------

        this.notBuilder.setSmallIcon(R.mipmap.ic_launcher);
        this.notBuilder.setTicker("This is a ticker");

        this.notBuilder.setWhen(System.currentTimeMillis()+ 10* 1000);
        this.notBuilder.setContentTitle("Update your profile");
        this.notBuilder.setContentText("Your profile is old. You should update your profile ...");

        // Tạo một Intent
        Intent intent = new Intent(this, EditProfileActivity.class);
        intent.putExtra("accountIdRoot", accId);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, MY_REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);


        this.notBuilder.setContentIntent(pendingIntent);

        // Lấy ra dịch vụ thông báo (Một dịch vụ có sẵn của hệ thống).
        NotificationManager notificationService  =
                (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);

        // Xây dựng thông báo và gửi nó lên hệ thống.

        Notification notification =  notBuilder.build();
        notificationService.notify(MY_NOTIFICATION_ID, notification);

    }
}
