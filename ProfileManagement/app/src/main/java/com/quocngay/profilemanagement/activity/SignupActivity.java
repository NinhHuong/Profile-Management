package com.quocngay.profilemanagement.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.quocngay.profilemanagement.DBContext;
import com.quocngay.profilemanagement.R;
import com.quocngay.profilemanagement.model.AccountModel;

public class SignupActivity extends Activity {

    private EditText edtAccount, edtPassword, edtFirstname, edtLastname, edtAddress, edtPhoneNumber, edtRollNumber, edtEmail;
    private Button btnSignup;
    AccountModel accountModel;
    DBContext dbContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        dbContext = DBContext.getInst();

        init();
    }

    private void init() {
        edtAccount = (EditText) findViewById(R.id.edt_account);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        edtFirstname = (EditText) findViewById(R.id.edt_firstname);
        edtLastname = (EditText) findViewById(R.id.edt_lastname);
        edtAddress = (EditText) findViewById(R.id.edt_address);
        edtPhoneNumber = (EditText) findViewById(R.id.edt_phonenumber);
        edtRollNumber = (EditText) findViewById(R.id.edt_rollnumber);
        edtEmail = (EditText) findViewById(R.id.edt_email);

        btnSignup = (Button) findViewById(R.id.btn_signup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder b = new AlertDialog.Builder(SignupActivity.this);

                b.setTitle("Create Account");
                b.setMessage("Are you sure you want to create account");
                b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int id = dbContext.getMaxClassId()+1;
                        accountModel = AccountModel.create(
                                id,
                                edtAccount.getText().toString(),
                                edtPassword.getText().toString(),
                                edtFirstname.getText().toString(),
                                edtLastname.getText().toString(),
                                1,
                                edtAddress.getText().toString(),
                                edtPhoneNumber.getText().toString(),
                                edtRollNumber.getText().toString(),
                                edtEmail.getText().toString(),
                                1,
                                "",
                                false);
                        dbContext.addAccountModel(accountModel);
                        Log.v("data",dbContext.getAccountModelByID(id).toString());
                        finish();
                    }
                });
                b.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                b.create().show();

            }
        });
    }
}
