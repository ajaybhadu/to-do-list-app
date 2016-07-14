package com.todolist.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.todolist.R;
import com.todolist.models.ToDoModel;

import java.util.ArrayList;

/**
 * Created by dhananjay on 22/3/16.
 */
public class ToDo_ListViewAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ToDoModel> arrayList_toDoModel;
    private View.OnClickListener listItem_OnClickListener;

    public ToDo_ListViewAdapter(Context mContext, ArrayList<ToDoModel> arrayList_toDoModel,
                                View.OnClickListener listItem_OnClickListener) {
        this.mContext = mContext;
        this.arrayList_toDoModel = arrayList_toDoModel;
        this.listItem_OnClickListener = listItem_OnClickListener;
    }

    @Override
    public int getCount() {
        if (arrayList_toDoModel != null)
            return arrayList_toDoModel.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        return arrayList_toDoModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder mViewHolder;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Activity.
                    LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item_to_do, null);

            mViewHolder = new ViewHolder();
            mViewHolder.textView_text_to_do_list_item = (TextView) convertView.findViewById(R.id.
                    textView_text_to_do_list_item);

            mViewHolder.textView_date_list_item = (TextView) convertView.findViewById(R.id.
                    textView_date_list_item);

            mViewHolder.textView_time_list_item = (TextView) convertView.findViewById(R.id.
                    textView_time_list_item);

            mViewHolder.textView_location_list_item = (TextView) convertView.findViewById(R.id.
                    textView_location_list_item);

            mViewHolder.imageButton_delete_list_item = (ImageButton) convertView.findViewById(R.id.
                    imageButton_delete_list_item);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        mViewHolder.textView_text_to_do_list_item.setText(arrayList_toDoModel.get(position).getText_to_do());
        mViewHolder.textView_date_list_item.setText(arrayList_toDoModel.get(position).getDate());
        mViewHolder.textView_time_list_item.setText(arrayList_toDoModel.get(position).getTime());
        mViewHolder.textView_location_list_item.setText(arrayList_toDoModel.get(position).getLocation());
        mViewHolder.imageButton_delete_list_item.setTag(position);
        mViewHolder.imageButton_delete_list_item.setOnClickListener(listItem_OnClickListener);

        return convertView;
    }

    private static class ViewHolder {
        private TextView textView_text_to_do_list_item, textView_date_list_item,
                textView_time_list_item, textView_location_list_item;
        private ImageButton imageButton_delete_list_item;
    }
}
