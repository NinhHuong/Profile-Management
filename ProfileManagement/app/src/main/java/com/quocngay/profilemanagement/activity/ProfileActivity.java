package com.quocngay.profilemanagement.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.quocngay.profilemanagement.DBContext;
import com.quocngay.profilemanagement.R;
import com.quocngay.profilemanagement.model.AccountModel;

public class ProfileActivity extends AppCompatActivity {

    private TextView txtUsername, txtFirstName, txtLastName, txtRollNumber, txtPhoneNumber, txtAdress;
    private Button btnRequestProfile, btnEditProfile;
    private ImageView imgAvatar;
    private DBContext dbContext;
    private AccountModel accountModel;
    private int accountIdRoot, accountIdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        accountIdRoot = getIntent().getExtras().getInt("accountIdRoot");
        accountIdView = getIntent().getExtras().getInt("accountIdView");

        dbContext = DBContext.getInst();
        accountModel = dbContext.getAccountModelByID(accountIdView);
        init();
        toolbar.setTitle(accountModel.getFirstname() + " " + accountModel.getLastname());
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //accountModel = dbContext.getAccountModelByID(accountIdView);
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                callIntent.setData(Uri.parse("tel://"+accountModel.getPhoneNumber()));
                startActivity(callIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        accountModel = dbContext.getAccountModelByID(accountIdView);
        init();
        super.onResume();
    }

    private void init() {
        txtUsername = (TextView) findViewById(R.id.txt_username_profile);
        txtFirstName = (TextView) findViewById(R.id.txt_firstname_profile);
        txtLastName = (TextView) findViewById(R.id.txt_lastname_profile);
        txtRollNumber = (TextView) findViewById(R.id.txt_rollnumber_profile);
        txtPhoneNumber = (TextView) findViewById(R.id.txt_phonenumber_profile);
        txtAdress = (TextView) findViewById(R.id.txt_address_profile);
        btnRequestProfile = (Button) findViewById(R.id.btn_request_profile);
        btnEditProfile = (Button) findViewById(R.id.btn_edit_profile);
        imgAvatar = (ImageView) findViewById(R.id.image_avatar);

        if (accountIdRoot == 0) {
            btnEditProfile.setVisibility(View.GONE);
        } else {
            btnRequestProfile.setVisibility(View.GONE);
        }
        //Log.v("image",accountModel.getPhoto());
        imgAvatar.setImageBitmap(StringToBitMap(accountModel.getPhoto()));
        txtUsername.setText(accountModel.getUsername());
        txtFirstName.setText(accountModel.getFirstname());
        txtLastName.setText(accountModel.getLastname());
        txtAdress.setText(accountModel.getAddress());
        txtRollNumber.setText(accountModel.getRollNumber());
        txtPhoneNumber.setText(accountModel.getPhoneNumber());

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                //intent.putExtra("accountIdView",accountIdRoot);
                intent.putExtra("accountIdRoot", accountIdRoot);
                startActivity(intent);

            }
        });

        btnRequestProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder b = new AlertDialog.Builder(ProfileActivity.this);

                b.setTitle("Request");
                b.setMessage("Are you sure you want to request?");
                b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AccountModel model = AccountModel.create(
                                accountModel.getId(),
                                accountModel.getUsername(),
                                accountModel.getPassword(),
                                accountModel.getFirstname(),
                                accountModel.getLastname(),
                                accountModel.getGender(),
                                accountModel.getAddress(),
                                accountModel.getPhoneNumber(),
                                accountModel.getRollNumber(),
                                accountModel.getEmail(),
                                accountModel.getRole(),
                                accountModel.getPhoto(),
                                true);
                        dbContext.addAccountModel(model);
                        //Log.v("data",dbContext.getAccountByID(accountIdView).toString());
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
