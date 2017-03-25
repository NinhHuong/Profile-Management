package com.quocngay.profilemanagement.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.quocngay.profilemanagement.DBContext;
import com.quocngay.profilemanagement.R;
import com.quocngay.profilemanagement.model.SemesterModel;
import com.quocngay.profilemanagement.model.SubjectOfClassModel;
import com.quocngay.profilemanagement.other.Constanst;
import com.quocngay.profilemanagement.other.ListViewClassAdapter;
import com.quocngay.profilemanagement.other.SemesterSpinnerAdapter;

import java.util.List;

import static com.quocngay.profilemanagement.other.Constanst.*;

public class OngoingClassActivity extends AppCompatActivity {

    private ListView lvClass;
    private Spinner spnSemester;
    private DBContext dbContext;
    private List<SemesterModel> semesterList;
    private List<SubjectOfClassModel> subjectOfClassModelList;
    private SemesterModel semesterModel;
    private int accountIdRoot;
    private int role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ongoing_class);
        //
        dbContext = DBContext.getInst();
        //accId Root
        accountIdRoot = getIntent().getIntExtra("accountIdRoot", -1);
        role = dbContext.getAccountModelByID(accountIdRoot).getRole();
        lvClass = (ListView) findViewById(R.id.lv_class);
        spnSemester = (Spinner) findViewById(R.id.spn_semester);
        //semester
        semesterList = dbContext.getAllSemesterModel();
        if(semesterList != null && semesterList.size() > 0) {
            final SemesterSpinnerAdapter spinnerAdapter = new SemesterSpinnerAdapter(this, android.R.layout.simple_spinner_item, semesterList);
            spnSemester.setAdapter(spinnerAdapter);
            semesterModel = dbContext.getCurrentSemesterModel();
            if(semesterModel != null) {
                int i = semesterList.indexOf(semesterModel);
                spnSemester.setSelection(i);
            }

            spnSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    semesterModel = spinnerAdapter.getItem(i);
                    //update data//
                    if(role == Constanst.KEY_ROLL_TEACHER) {
                        subjectOfClassModelList = dbContext.getSubjectOfClassModelsBySemesterIdTeacherId(semesterModel.getId(), accountIdRoot);
                    } else if(role == Constanst.KEY_ROLL_STUDENT) {
                        subjectOfClassModelList = dbContext.getSubjectOfClassModelsBySemesterIdStudentId(semesterModel.getId(), accountIdRoot);
                    } else if(role == Constanst.KEY_ROLL_ADMIN) {
                        subjectOfClassModelList = dbContext.getSubjectOfClassModelsBySemesterId(semesterModel.getId());
                    }

                    ListViewClassAdapter listViewClassAdapter = new ListViewClassAdapter(OngoingClassActivity.this, subjectOfClassModelList, role);
                    lvClass.setAdapter(listViewClassAdapter);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            //
            subjectOfClassModelList = dbContext.getSubjectOfClassModelsBySemesterId(semesterModel.getId());
            ListViewClassAdapter listViewClassAdapter = new ListViewClassAdapter(this, subjectOfClassModelList, role);
            lvClass.setAdapter(listViewClassAdapter);
        }
    }

    // Initiating Menu XML file (menu.xml)
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Event Handling for Individual menu item selected
     * Identify single menu item by it's id
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.menu_profile:
                // Single menu item is selected do something
                // Ex: launching new activity/screen or show alert message
                //Toast.makeText(OngoingClassActivity.this, "Profile is Selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, ProfileActivity.class);
                intent.putExtra("accountIdView",accountIdRoot);
                intent.putExtra("accountIdRoot",accountIdRoot);
                startActivity(intent);
                return true;

            case R.id.menu_logout:
               // Toast.makeText(OngoingClassActivity.this, "Logout is Selected", Toast.LENGTH_SHORT).show();
                onBackPressed();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
