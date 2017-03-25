package com.quocngay.profilemanagement.other;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.quocngay.profilemanagement.R;
import com.quocngay.profilemanagement.activity.ProfileActivity;
import com.quocngay.profilemanagement.model.AccountModel;
import com.quocngay.profilemanagement.model.AccountModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ninh huong on 3/23/2017.
 */

public class ListViewAssignStudentAdapter extends BaseAdapter {
    private List<AccountModel> list;
    private List<AccountModel> addedList;
    private AccountModel model;
    private Context context;
    private int role;

    public ListViewAssignStudentAdapter(Context context, List<AccountModel> list, List<AccountModel> addedList) {
        if (list != null) {
            this.list = list;
        } else {
            this.list = new ArrayList<>();
        }
        if(addedList != null) {
            this.addedList = addedList;
        } else {
            this.addedList = new ArrayList<>();
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
        CheckBox ckbAssign;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.list_item_assign_student, null);

            holder = new ViewHolder();
            holder.tvNo = (TextView) convertView.findViewById(R.id.txt_no);
            holder.tvRollNumber = (TextView) convertView.findViewById(R.id.txt_roll_number);
            holder.tvFirstName = (TextView) convertView.findViewById(R.id.txt_first_name);
            holder.tvLastName = (TextView) convertView.findViewById(R.id.txt_last_name);
            holder.ckbAssign = (CheckBox) convertView.findViewById(R.id.ckb_assign);
            convertView.setTag(holder);

            holder.ckbAssign.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    if(cb.isChecked()) {
                        addedList.add(list.get(position));
                    } else {
                        addedList.remove(list.get(position));
                    }
                }
            });
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        model = list.get(position);
        holder.tvNo.setText(String.valueOf(position + 1));
        holder.tvRollNumber.setText(model.getRollNumber());
        holder.tvFirstName.setText(model.getFirstname());
        holder.tvLastName.setText(model.getLastname());

        return convertView;
    }

    public void swap(List<AccountModel> list1){
        this.list.clear();
        this.list.addAll(list1);
        this.addedList = new ArrayList<>();
        notifyDataSetChanged();
    }
}
