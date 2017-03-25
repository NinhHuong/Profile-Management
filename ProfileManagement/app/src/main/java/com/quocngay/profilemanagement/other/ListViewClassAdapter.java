package com.quocngay.profilemanagement.other;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quocngay.profilemanagement.DBContext;
import com.quocngay.profilemanagement.R;
import com.quocngay.profilemanagement.activity.ViewStudentOfClassActivity;
import com.quocngay.profilemanagement.model.AccountModel;
import com.quocngay.profilemanagement.model.SubjectOfClassModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ninh huong on 3/23/2017.
 */

public class ListViewClassAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<SubjectOfClassModel> list;
    private SubjectOfClassModel model;
    private Context context;
    private int role;

    public ListViewClassAdapter(Context context, List<SubjectOfClassModel> list, int role) {
        if (list != null) {
            this.list = list;
        } else {
            this.list = new ArrayList<>();
        }
        this.context = context;
        this.role = role;
        this.inflater = LayoutInflater.from(context);

        //if account is teacher => hightlight class name
        role = 2;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private class ViewHolder {
        TextView tvNo;
        TextView tvClassName;
        TextView tvSubjectCode;
        TextView tvSubjectName;
        TextView tvCredit;
        TextView tvStart;
        TextView tvEnd;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.list_item_class, null);

            holder = new ViewHolder();
            holder.tvNo = (TextView) convertView.findViewById(R.id.txt_no);
            holder.tvClassName = (TextView) convertView.findViewById(R.id.txt_class_name);
            holder.tvSubjectCode = (TextView) convertView.findViewById(R.id.txt_subject_code);
            holder.tvSubjectName = (TextView) convertView.findViewById(R.id.txt_subject_name);
            holder.tvCredit = (TextView) convertView.findViewById(R.id.txt_credit);
            holder.tvStart = (TextView) convertView.findViewById(R.id.txt_start);
            holder.tvEnd = (TextView) convertView.findViewById(R.id.txt_end);
            convertView.setTag(holder);

            holder.tvClassName.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    if(role == Constanst.KEY_ROLL_TEACHER || role == Constanst.KEY_ROLL_ADMIN) {
                        Intent intent = new Intent(context, ViewStudentOfClassActivity.class);
                        intent.putExtra("role", role);
                        intent.putExtra("subclassId", list.get(position).getId());
                        context.startActivity(intent);
                    }
                }
            });
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        model = list.get(position);
        holder.tvNo.setText(String.valueOf(position + 1));
        holder.tvClassName.setText(model.getClassModel().getClassName());
        holder.tvSubjectCode.setText(model.getSubjectModel().getCode());
        holder.tvSubjectName.setText(model.getSubjectModel().getName());
        holder.tvCredit.setText(String.valueOf(model.getSubjectModel().getCredit()));//
        holder.tvStart.setText(Constanst.KEY_DATE_FORMAT.format(model.getClassModel().getSemesterModel().getStartDate()));
        holder.tvEnd.setText(Constanst.KEY_DATE_FORMAT.format(model.getClassModel().getSemesterModel().getEndDate()));
        if(role == Constanst.KEY_ROLL_TEACHER || role == Constanst.KEY_ROLL_ADMIN)
            holder.tvClassName.setTextColor(context.getResources().getColor(R.color.text_link));

        return convertView;
    }

    public void swap(List<SubjectOfClassModel> list1){
        this.list.clear();
        this.list.addAll(list1);
        notifyDataSetChanged();
    }
}
