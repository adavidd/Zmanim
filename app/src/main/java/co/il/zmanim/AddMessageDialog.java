package co.il.zmanim;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Objects;

public class AddMessageDialog {

    private Dialog dialog;
    private AddMessageDialogListener Listener;
    private TextView mStartDate;
    private TextView mEndDate;

    public void showDialog(final Context context, final AddMessageDialogListener mListener) {


        if (context != null) {
            dialog = new Dialog(context);
            this.Listener = mListener;
            dialog.setContentView(R.layout.add_message_dialog);
            dialog.setCanceledOnTouchOutside(false);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();


            final EditText message = dialog.findViewById(R.id.AMD_message_TV);
            
            
            mStartDate = dialog.findViewById(R.id.AMD_start_time_TV);
            mEndDate = dialog.findViewById(R.id.AMD_end_time_TV);
            

            Calendar c = Calendar.getInstance();
            final int mYear = c.get(Calendar.YEAR);
            final int mMonth = c.get(Calendar.MONTH);
            final int mDay = c.get(Calendar.DAY_OF_MONTH);

            final Calendar startDateCalender = Calendar.getInstance();
            final Calendar endDateCalender = Calendar.getInstance();


            mStartDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                            mStartDate.setText(dayOfMonth + "/" + month + "/" + year);
                            startDateCalender.set(Calendar.DAY_OF_MONTH, view.getDayOfMonth());
                            startDateCalender.set(Calendar.MONTH, view.getMonth());
                            startDateCalender.set(Calendar.YEAR, view.getYear());
                        }
                    },  mYear, mMonth, mDay);

                    datePickerDialog.show();
                }
            });


            mEndDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                            mEndDate.setText(dayOfMonth + "/" + month + "/" + year);
                            endDateCalender.set(Calendar.DAY_OF_MONTH, view.getDayOfMonth());
                            endDateCalender.set(Calendar.MONTH, view.getMonth());
                            endDateCalender.set(Calendar.YEAR, view.getYear());

                        }
                    },  mYear, mMonth, mDay);

                    datePickerDialog.show();

                }


            });



            dialog.findViewById(R.id.AMD_add_BTN).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MessageObject messageObject = new MessageObject(message.getText().toString(), startDateCalender.getTimeInMillis(), endDateCalender.getTimeInMillis());

                    mListener.onAddClicked(messageObject);
                    dialog.dismiss();

                }
            });



        }


    }




    public interface AddMessageDialogListener{

        void onAddClicked(MessageObject messageObject);
    }

}
