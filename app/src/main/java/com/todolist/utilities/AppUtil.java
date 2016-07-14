package com.todolist.utilities;

import android.content.Context;
import android.widget.Toast;

/**
 * Utility class having Utility functions for complete app
 * <p/>
 * Created by dhananjay on 19/3/16.
 */
public class AppUtil {


    /**
     * method to show toast msg
     *
     * @param applicationContext Context
     * @param strToastMsg        String message to display in Toast
     */
    public static void showToast(Context applicationContext, String strToastMsg, boolean isDurationLong) {

        int durationOfToast;

        if (isDurationLong)
            durationOfToast = Toast.LENGTH_LONG;
        else
            durationOfToast = Toast.LENGTH_SHORT;

        Toast.makeText(applicationContext, strToastMsg, durationOfToast).show();
    }

//    /**
//     * show Alert Dialog
//     *
//     * @param mContext         Context
//     * @param strAlertMessage  String Alert Message to display
//     * @param strBtnText       String Button Text to display
//     * @param isCancellable    boolean Alert should hide on back press or not
//     * @param mOnClickListener OnClickListener to handle click of the button on Alert
//     */
//    public static void showAlert(final Context mContext, String strAlertMessage, String strBtnText,
//                                 boolean isCancellable, View.OnClickListener mOnClickListener) {
//        final Dialog mDialog;
////        if (mDialog != null) {
////            if (mDialog.isShowing()) {
////                mDialog.dismiss();
////            }
////            mDialog = null;
////        }
//        mDialog = new Dialog(mContext/*, R.style.Dialog_No_Border*/);
////        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        mDialog.setContentView(R.layout.alert_with_header);
//        mDialog.setCancelable(isCancellable);
//        TextView txtAlertText_no_header = (TextView) mDialog.findViewById(R.id.txtAlertText_no_header);
//        Button btnAlert = (Button) mDialog.findViewById(R.id.btnAlert_no_header);
//        RelativeLayout relativeAlertTop_Header = (RelativeLayout) mDialog.findViewById(R.id.
//                relativeAlertTop_Header);
//
//        if (mOnClickListener != null) {
//            btnAlert.setOnClickListener(mOnClickListener);
//        } else {
//            btnAlert.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mDialog.dismiss();
//                }
//            });
//        }
//
//        if (strAlertMessage != null) {
//            txtAlertText_no_header.setText(strAlertMessage);
//        }
//
//        if (strBtnText != null) {
//            btnAlert.setText(strBtnText);
//        }
//        try {
//            mDialog.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
}