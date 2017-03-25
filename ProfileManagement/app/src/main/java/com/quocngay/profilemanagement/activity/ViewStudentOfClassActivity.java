package com.quocngay.profilemanagement.activity;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.quocngay.profilemanagement.DBContext;
import com.quocngay.profilemanagement.R;
import com.quocngay.profilemanagement.model.AccountModel;
import com.quocngay.profilemanagement.model.ClassModel;
import com.quocngay.profilemanagement.model.StudentOfClassModel;
import com.quocngay.profilemanagement.model.SubjectOfClassModel;
import com.quocngay.profilemanagement.other.Constanst;
import com.quocngay.profilemanagement.other.ListViewAssignStudentAdapter;
import com.quocngay.profilemanagement.other.ListViewStudentAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewStudentOfClassActivity extends AppCompatActivity {

    private ListView listView, lvAssign;
    private List<StudentOfClassModel> studentOfClassList;
    private List<AccountModel> listAssignStudent;
    private List<AccountModel> listAddedStudent;
    private DBContext dbContext;
    private Button btnAssign, btnSave;
    private LinearLayout layoutAssign;
    private int subOfClassId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_of_class);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.activity_title_view_students));

        listView = (ListView) findViewById(R.id.lv_students);
        lvAssign = (ListView) findViewById(R.id.lv_assign_students);
        layoutAssign = (LinearLayout) findViewById(R.id.layout_assign);
        btnAssign = (Button) findViewById(R.id.btn_assign);
        btnSave = (Button) findViewById(R.id.btn_save);

        int role = getIntent().getIntExtra("role", Constanst.KEY_ROLL_TEACHER);
        btnAssign.setVisibility(role == Constanst.KEY_ROLL_ADMIN ? View.VISIBLE : View.INVISIBLE);

        subOfClassId = getIntent().getIntExtra("subclassId", 0);
        dbContext = DBContext.getInst();
        studentOfClassList = dbContext.getStudentOfClassesBySubOfClassId(subOfClassId);
        ListViewStudentAdapter listViewStudentAdapter = new ListViewStudentAdapter(this, studentOfClassList);
        listView.setAdapter(listViewStudentAdapter);

        btnAssign.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                List<AccountModel> allStudent = dbContext.getAllStudent();
                listAddedStudent = new ArrayList<>();
                listAssignStudent = new ArrayList<>();
                for(AccountModel acc : allStudent) {
                    boolean contain = false;
                    for(StudentOfClassModel stuOfClass : studentOfClassList) {
                        if(stuOfClass.getAccountModel().getId() == acc.getId()) {
                            contain = true;
                            break;
                        }
                    }
                    if(!contain)
                        listAssignStudent.add(acc);
                }

                if(listAssignStudent.size() > 0) {
                    btnAssign.setEnabled(false);
                    layoutAssign.setVisibility(View.VISIBLE);
                    ListViewAssignStudentAdapter assignStudentAdapter = new ListViewAssignStudentAdapter(ViewStudentOfClassActivity.this, listAssignStudent, listAddedStudent);
                    lvAssign.setAdapter(assignStudentAdapter);
                } else {
                    showMessage("Assign student", "There is no student to add to this class");
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubjectOfClassModel subOfClassModel = dbContext.getSubjectOfClassModelByID(subOfClassId);
                for(AccountModel acc : listAddedStudent) {
                    StudentOfClassModel stuOfClass = StudentOfClassModel.createWithoutId(subOfClassModel, acc);
                    dbContext.addStudentOfClass(stuOfClass);
                }

                showMessage("Assign student", "Your students is assigned to class successfully.");
                layoutAssign.setVisibility(View.INVISIBLE);
                btnAssign.setEnabled(true);
                studentOfClassList = dbContext.getStudentOfClassesBySubOfClassId(subOfClassId);
                ListViewStudentAdapter listViewStudentAdapter = new ListViewStudentAdapter(ViewStudentOfClassActivity.this, studentOfClassList);
                listView.setAdapter(listViewStudentAdapter);
            }
        });
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

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.show();
    }

}
