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

    private DBContext dbContext;
    private List<AccountModel> listAccount;
    private int accountId = 0;

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

        //event
        btnLogin.setOnClickListener(this);

        dbContext = DBContext.getInst();
        listAccount = new ArrayList<>();
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_login:
                onClickLogin();
                break;
            default:
        }
    }

    private void onClickLogin() {
        String account = edtAccount.getText().toString();
        String password = edtPassword.getText().toString();
        if (!account.isEmpty() && !password.isEmpty()) {
            if (checkLogin()) {
                Intent intent = new Intent(this, OngoingClassActivity.class);
                //intent.putExtra("accountIdView",accountId);
                intent.putExtra("accountIdRoot",accountId);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Account & Password Incorrect !!!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Please enter Account & Password!!", Toast.LENGTH_SHORT).show();
        }
    }

    //Check login
    private boolean checkLogin() {
        listAccount = dbContext.getAllAccountModel();
        for (AccountModel model : listAccount) {
            if (edtAccount.getText().toString().equals(model.getUsername())
                    && edtPassword.getText().toString().equals(model.getPassword())) {
                accountId = model.getId();
                if(model.isNeedUpdate()){
                    notiButtonClicked();
                }
                return true;
            }
        }
        return false;
    }

    public void notiButtonClicked()  {

        // --------------------------
        // Chuẩn bị một thông báo
        // --------------------------

        this.notBuilder.setSmallIcon(R.mipmap.ic_launcher);
        this.notBuilder.setTicker("This is a ticker");

        // Sét đặt thời điểm sự kiện xẩy ra.
        // Các thông báo trên Panel được sắp xếp bởi thời gian này.
        this.notBuilder.setWhen(System.currentTimeMillis()+ 10* 1000);
        this.notBuilder.setContentTitle("Update your profile");
        this.notBuilder.setContentText("Your profile is old. You should update your profile ...");

        // Tạo một Intent
        Intent intent = new Intent(this, LoginActivity.class);


        // PendingIntent.getActivity(..) sẽ start mới một Activity và trả về
        // đối tượng PendingIntent.
        // Nó cũng tương đương với gọi Context.startActivity(Intent).
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
