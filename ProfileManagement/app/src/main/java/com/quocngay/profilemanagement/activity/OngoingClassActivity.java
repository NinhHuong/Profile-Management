package com.quocngay.profilemanagement.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.quocngay.profilemanagement.DBContext;
import com.quocngay.profilemanagement.R;
import com.quocngay.profilemanagement.model.SemesterModel;
import com.quocngay.profilemanagement.model.SubjectOfClassModel;
import com.quocngay.profilemanagement.other.ListViewClassAdapter;
import com.quocngay.profilemanagement.other.SemesterSpinnerAdapter;

import java.util.List;

public class OngoingClassActivity extends AppCompatActivity {

    private ListView lvClass;
    private Spinner spnSemester;
    private DBContext dbContext;
    private List<SemesterModel> semesterList;
    private List<SubjectOfClassModel> subjectOfClassModelList;
    private SemesterModel semesterModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ongoing_class);
        lvClass = (ListView) findViewById(R.id.lv_class);
        spnSemester = (Spinner) findViewById(R.id.spn_semester);
        //
        dbContext = DBContext.getInst();
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
                    subjectOfClassModelList = dbContext.getSubjectOfClassModelsBySemesterId(semesterModel.getId());
                    ListViewClassAdapter listViewClassAdapter = new ListViewClassAdapter(OngoingClassActivity.this, subjectOfClassModelList);
                    lvClass.setAdapter(listViewClassAdapter);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            //
            subjectOfClassModelList = dbContext.getSubjectOfClassModelsBySemesterId(semesterModel.getId());
            ListViewClassAdapter listViewClassAdapter = new ListViewClassAdapter(this, subjectOfClassModelList);
            lvClass.setAdapter(listViewClassAdapter);
        }
    }
}
