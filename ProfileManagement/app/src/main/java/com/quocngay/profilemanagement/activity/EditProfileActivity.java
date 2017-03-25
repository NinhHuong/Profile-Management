package com.quocngay.profilemanagement.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.quocngay.profilemanagement.DBContext;
import com.quocngay.profilemanagement.R;
import com.quocngay.profilemanagement.model.AccountModel;

public class EditProfileActivity extends AppCompatActivity {

    private TextView txtUsername,txtRollNumber;
    private EditText  edtFirstName, edtLastName, edtPhoneNumber, edtAdress;
    private Button btnSaveProfile;
    private ImageView imgAvatar;
    private DBContext dbContext;
    private AccountModel accountModel;
    private int accountIdRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        accountIdRoot = getIntent().getExtras().getInt("accountIdRoot");

        dbContext = DBContext.getInst();
        accountModel = dbContext.getAccountModelByID(accountIdRoot);
        init();
        toolbar.setTitle(accountModel.getFirstname()+ " "+accountModel.getLastname());
        setSupportActionBar(toolbar);

    }

    private void init() {
        txtUsername = (TextView) findViewById(R.id.edt_username_profile);
        edtFirstName = (EditText) findViewById(R.id.edt_firstname_profile);
        edtLastName = (EditText) findViewById(R.id.edt_lastname_profile);
        txtRollNumber = (EditText) findViewById(R.id.edt_rollnumber_profile);
        edtPhoneNumber = (EditText) findViewById(R.id.edt_phonenumber_profile);
        edtAdress = (EditText) findViewById(R.id.edt_address_profile);
        btnSaveProfile = (Button) findViewById(R.id.btn_save_profile);
        imgAvatar = (ImageView) findViewById(R.id.image_avatar);


        //Log.v("image",accountModel.getPhoto());
        imgAvatar.setImageBitmap(StringToBitMap(accountModel.getPhoto()));
        txtUsername.setText(accountModel.getUsername());
        edtFirstName.setText(accountModel.getFirstname());
        edtLastName.setText(accountModel.getLastname());
        edtAdress.setText(accountModel.getAddress());
        txtRollNumber.setText(accountModel.getRollNumber());
        edtPhoneNumber.setText(accountModel.getPhoneNumber());


        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountModel model = AccountModel.create(
                        accountModel.getId(),
                        accountModel.getUsername(),
                        accountModel.getPassword(),
                        edtFirstName.getText().toString(),
                        edtLastName.getText().toString(),
                        accountModel.getGender(),
                        edtAdress.getText().toString(),
                        edtPhoneNumber.getText().toString(),
                        accountModel.getRollNumber(),
                        accountModel.getEmail(),
                        accountModel.getRole(),
                        accountModel.getPhoto(),
                        false);
                dbContext.addAccountModel(model);
                Log.v("dataedit",dbContext.getAccountModelByID(accountIdRoot).toString());

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
