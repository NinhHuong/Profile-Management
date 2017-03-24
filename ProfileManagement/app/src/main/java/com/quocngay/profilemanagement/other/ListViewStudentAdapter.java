package com.quocngay.profilemanagement.other;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quocngay.profilemanagement.R;
import com.quocngay.profilemanagement.activity.ViewStudentOfClassActivity;
import com.quocngay.profilemanagement.model.StudentOfClassModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ninh huong on 3/23/2017.
 */

public class ListViewStudentAdapter extends BaseAdapter {
    private List<StudentOfClassModel> list;
    private StudentOfClassModel model;
    private Context context;
    private int role;

    public ListViewStudentAdapter(Context context, List<StudentOfClassModel> list) {
        if (list != null) {
            this.list = list;
        } else {
            this.list = new ArrayList<>();
        }
        this.context = context;
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
        TextView tvRollNumber;
        TextView tvFirstName;
        TextView tvLastName;
        TextView tvPhone;
        TextView tvEmail;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.list_item_student, null);

            holder = new ViewHolder();
            holder.tvNo = (TextView) convertView.findViewById(R.id.txt_no);
            holder.tvRollNumber = (TextView) convertView.findViewById(R.id.txt_roll_number);
            holder.tvFirstName = (TextView) convertView.findViewById(R.id.txt_first_name);
            holder.tvLastName = (TextView) convertView.findViewById(R.id.txt_last_name);
            holder.tvPhone = (TextView) convertView.findViewById(R.id.txt_phone);
            holder.tvEmail = (TextView) convertView.findViewById(R.id.txt_email);
            convertView.setTag(holder);

            holder.tvPhone.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    //go to info detail
                }
            });
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        model = list.get(position);
        holder.tvNo.setText(String.valueOf(position + 1));
        holder.tvRollNumber.setText(model.getAccountModel().getRollNumber());
        holder.tvFirstName.setText(model.getAccountModel().getFirstname());
        holder.tvLastName.setText(model.getAccountModel().getLastname());
        holder.tvPhone.setText(model.getAccountModel().getPhoneNumber());
        holder.tvEmail.setText(model.getAccountModel().getEmail());

        return convertView;
    }

    public void swap(List<StudentOfClassModel> list1){
        this.list.clear();
        this.list.addAll(list1);
        notifyDataSetChanged();
    }
}
