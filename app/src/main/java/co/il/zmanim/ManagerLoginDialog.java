package co.il.zmanim;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class ManagerLoginDialog {

    private ManagerLoginDialogListener Listener;
    private Dialog dialog;

    public void showDialog(final Context context, final ManagerLoginDialogListener mListener) {

        if (context != null) {
            dialog = new Dialog(context);
            this.Listener = mListener;
            dialog.setContentView(R.layout.manager_login_dialog);
            dialog.setCanceledOnTouchOutside(false);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();

            final EditText password = dialog.findViewById(R.id.MLD_password_ET);
            Button loginBtn = dialog.findViewById(R.id.MLD_login_BTN);


            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (password.getText().length() > 0){

                        mListener.onLoginClicked(password.getText().toString());
                    }else {

                         Toast.makeText(context, context.getResources().getString(R.string.set_password), Toast.LENGTH_SHORT).show();

                    }
                }
            });



            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    mListener.onDialogCancel();
                }
            });


            final android.os.Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    dialog.setCanceledOnTouchOutside(true);

                }
            }, 1000);

        }
    }


    void cancelDialog() {

        dialog.cancel();


    }


    public interface ManagerLoginDialogListener {


        void onDialogCancel();

        void onLoginClicked(String password);
    }

}
