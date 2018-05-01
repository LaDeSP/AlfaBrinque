package com.book.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.book.app.R;
import com.book.app.pojo.Usuario;

import java.util.List;

public class CustomSpinnerAdapter extends BaseAdapter {

    private ImageView imgIcon;
    private TextView txtTitle;
    private List<Usuario> userList;
    private Context context;

    public CustomSpinnerAdapter(Context context,
                                List<Usuario> userList) {
        this.userList = userList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int index) {
        return userList.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.spinner_categories_adapter, null);
        }

        imgIcon = (ImageView) convertView.findViewById(R.id.img_spinner_adapter);
        txtTitle = (TextView) convertView.findViewById(R.id.txt_spinner_adapter);

        txtTitle.setText(userList.get(position).getApelido());
        imgIcon.setImageResource(userList.get(position).getAvatarUrl());


        return convertView;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.spinner_categories_adapter, null);
        }


        imgIcon = (ImageView) convertView.findViewById(R.id.img_spinner_adapter);
        txtTitle = (TextView) convertView.findViewById(R.id.txt_spinner_adapter);

        if (imgIcon == null) {
            txtTitle.setText(userList.get(position).getApelido());
        } else {
            imgIcon.setImageResource(userList.get(position).getAvatarUrl());
            txtTitle.setText(userList.get(position).getApelido());
        }

        return convertView;
    }

}
