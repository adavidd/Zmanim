package co.il.zmanim;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import java.util.Objects;

public class TestDialog {

    private Dialog dialog;

    public void showDialog(Context context){

        if (context != null) {
            dialog = new Dialog(context);
            dialog.setContentView(R.layout.test_dialog);
            dialog.setCanceledOnTouchOutside(false);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

            Window window = dialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();

            wlp.gravity = Gravity.BOTTOM;
            wlp.flags &= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(wlp);

            dialog.show();

        }



    }

}
