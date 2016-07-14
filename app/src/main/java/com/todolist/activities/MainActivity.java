package com.todolist.activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.todolist.R;
import com.todolist.adapters.ToDo_ListViewAdapter;
import com.todolist.database.DatabaseHelper;
import com.todolist.models.ToDoModel;
import com.todolist.utilities.AppUtil;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by dhananjay on 19/3/16.
 */
public class MainActivity extends Activity implements View.OnClickListener {

    /**
     * UI elements declaration
     */
    private ListView listView_MainActivity;
    private Dialog dialog_addToDoItem;
    private EditText editText_text_to_do;
    private TextView textView_Time;
    private TextView textView_Date;

    /**
     * Member variable declaration
     */
    private final String LOG_TAG = this.getClass().getSimpleName();
    //    private final int PLACE_PICKER_REQUEST = 1;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private ArrayList<ToDoModel> arrayList_ToDoModel;
    private ToDo_ListViewAdapter mToDo_ListViewAdapter;
    private View.OnClickListener listItem_onClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeView();
    }

    private void initializeView() {
        listView_MainActivity = (ListView) findViewById(R.id.listView_MainActivity);

        listItem_onClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.v(LOG_TAG, "in onClick() of listItem_onClickListener");

                switch (view.getId()) {

                    case R.id.imageButton_delete_list_item:
                        int positionClicked = (Integer) view.getTag();

                        arrayList_ToDoModel.remove(positionClicked);
                        mToDo_ListViewAdapter.notifyDataSetChanged();
                        listView_MainActivity.invalidate();
                        break;
                }
            }
        };

        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
        arrayList_ToDoModel = databaseHelper.getAllToDo();

        mToDo_ListViewAdapter = new ToDo_ListViewAdapter(MainActivity.this, arrayList_ToDoModel,
                listItem_onClickListener);
        listView_MainActivity.setAdapter(mToDo_ListViewAdapter);

        initialize_dialog_addToDoItem();
        initialize_datePicker();
        initialize_timePicker();
    }

    private void initialize_timePicker() {
        Calendar calendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                Log.v(LOG_TAG, "onTimeSet hour:" + hour + " minute:" + minute);
                textView_Time.setText(hour + ":" + minute);
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
    }

    private void initialize_datePicker() {
        final Calendar calendar = Calendar.getInstance();
//        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
//        final int month = calendar.get(Calendar.MONTH);
//        int year = calendar.get(Calendar.YEAR);

        datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Log.v(LOG_TAG, "onDateSet() year:" + year + " monthOfYear:" + monthOfYear +
                        " dayOfMonth:" + dayOfMonth + " date:" + calendar.getTime());

                textView_Date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void initialize_dialog_addToDoItem() {
        dialog_addToDoItem = new Dialog(MainActivity.this);
        dialog_addToDoItem.setContentView(R.layout.dialog_add_to_do_item);

        dialog_addToDoItem.setTitle(getString(R.string.AddANewToDoItem));

        editText_text_to_do = (EditText) dialog_addToDoItem.findViewById(R.id.editText_text_to_do);

        textView_Time = (TextView) dialog_addToDoItem.findViewById(R.id.textView_Time);
        textView_Time.setOnClickListener(this);

        textView_Date = (TextView) dialog_addToDoItem.findViewById(R.id.textView_Date);
        textView_Date.setOnClickListener(this);

        Button button_Cancel = (Button) dialog_addToDoItem.findViewById(R.id.button_Cancel);
        button_Cancel.setOnClickListener(this);

        Button button_Save = (Button) dialog_addToDoItem.findViewById(R.id.button_Save);
        button_Save.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.textView_Location:// open map to pick a place
                break;

            case R.id.textView_Time:// open Time picker
                timePickerDialog.show();
                break;

            case R.id.textView_Date:// open Date picker
                datePickerDialog.show();
                break;

            case R.id.button_Cancel:
                if (dialog_addToDoItem != null) {
                    dialog_addToDoItem.dismiss();
                }
                break;

            case R.id.button_Save:// check values then save to-do item in DB and update listView

                if (editText_text_to_do.getText().toString().equals("")) {

                    AppUtil.showToast(MainActivity.this, getString(R.string.PleaseEnterYourToDoText), false);
                    return;

                } else if (textView_Date.getText().equals(getString(R.string.Date))) {

                    AppUtil.showToast(MainActivity.this, getString(R.string.PleaseSelectToDoDate), false);
                    return;

                } else if (textView_Time.getText().equals(getString(R.string.PleaseSelectToDoTime))) {

                    AppUtil.showToast(MainActivity.this, getString(R.string.Time), false);
                    return;
                }

                if (dialog_addToDoItem != null) {
                    dialog_addToDoItem.dismiss();
                }

                ToDoModel toDoModel = new ToDoModel();
                toDoModel.setText_to_do(editText_text_to_do.getText().toString());
                toDoModel.setDate(textView_Date.getText().toString());
                toDoModel.setTime(textView_Time.getText().toString());
                toDoModel.setLocation("");// TODO set Location

                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                long result = databaseHelper.insertToDo(toDoModel);

                if (result != -1) {
                    toDoModel.set_id((int) result);
                    arrayList_ToDoModel.add(toDoModel);
                    mToDo_ListViewAdapter.notifyDataSetChanged();
//                AppUtil.showToast(MainActivity.this, getString(R.string.Saved), false);
                    showAlertDialog(MainActivity.this, getString(R.string.Saved), null, null);
                } else {
                    showAlertDialog(MainActivity.this, getString(R.string.UnableToSave), null, null);
                }
                break;
        }
    }

    private void showAlertDialog(Context mContext, String messageToShow, String positiveButtonText, String negativeButtonText) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        if (positiveButtonText == null) {
            positiveButtonText = mContext.getString(android.R.string.ok);
        }

//        final String finalPositiveButtonText = positiveButtonText;
        builder.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

//                if (finalPositiveButtonText.equals(getString(R.string.Delete))) {
//                    deleteSelected
//                }
            }
        });

        if (negativeButtonText == null) {
            negativeButtonText = getString(android.R.string.cancel);
        }

        builder.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setMessage(messageToShow);
        builder.setCancelable(false);
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_AddANewToDoItem) {
            if (dialog_addToDoItem != null) {
                dialog_addToDoItem.show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}