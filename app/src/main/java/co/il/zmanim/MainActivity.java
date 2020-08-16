package co.il.zmanim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.lib.widget.verticalmarqueetextview.VerticalMarqueeTextView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Objects;
import java.util.Observable;

import co.il.zmanim.room.MessageDatabase;
import co.il.zmanim.room.ZemanimObject;
import co.il.zmanim.utils.ActivityRunning;
import co.il.zmanim.utils.FragmentHelper;


public class MainActivity extends AppCompatActivity implements ManagerLoginDialog.ManagerLoginDialogListener,
        ManagerFragment.ManagerFragmentListener,
        WallFragment.WallFragmentListener, AddMessageDialog.AddMessageDialogListener, java.util.Observer {


    private static final String TAG = MainActivity.class.getSimpleName();
    private int managerBtnClicked = 5;
    private Toast toast;
    private boolean dialogOpen;
    private ManagerLoginDialog managerLoginDialog;
    private VerticalMarqueeTextView mMessagesTv;
    private MainViewModel model;
    private FragmentHelper mFragmentHelper;
    private ZemanimObject zemanimObject;
    private WallFragment mWallFragment;
    private ManagerFragment mManagerFragment;
    private MessageDatabase messageDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_main);


        initViews();
        setLiveData();
        readTxtFile();
        ObservableObject.getInstance().addObserver(this);

        new Thread(new Runnable() {
            @Override
            public void run() {

                createDataBase();
            }
        }).start();
    }


    private void createDataBase() {


        messageDatabase = MessageDatabase.getInstance(this);
        messageDatabase.messageDao().getMessagesList();

    }


    @Override
    protected void onResume() {
        super.onResume();

//        setLiveData();
    }





    @Override
    public void onBackPressed() {

        if (mFragmentHelper.isCurrent(ManagerFragment.TAG)){

            setWallFragment(zemanimObject);

        }else {

            super.onBackPressed();
        }

    }






    private void setLiveData() {

        model.getFruitList().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String message) {
                // update UI
                mWallFragment.messageChanged(message);

            }
        });


    }


    private void initViews() {

        model = new ViewModelProvider(this).get(MainViewModel.class);
        mFragmentHelper = new FragmentHelper(this, new ActivityRunning());


    }



    private void setWallFragment(ZemanimObject zemanimObject) {

        if (mWallFragment == null) {
            mWallFragment = WallFragment.newInstance(zemanimObject);
        }

        mFragmentHelper.replaceFragment(R.id.MA_content_frame_FL, mWallFragment, WallFragment.TAG, null);

    }




    private void managerLogin() {

        managerLoginDialog = new ManagerLoginDialog();
        managerLoginDialog.showDialog(this, this);


    }


    @Override
    public void onDialogCancel() {
        dialogOpen = false;

    }




    private void readTxtFile() {

        Gson gson = new Gson();
        String txt = null;
        try {
            txt = convertStreamToString(Objects.requireNonNull(this).getAssets().open("tzefat.txt"));
        } catch (Exception e) {
            e.printStackTrace();
        }

         zemanimObject = gson.fromJson(txt, ZemanimObject.class);
        setWallFragment(zemanimObject);




    }







    /**
     * read the file and convert the text to string
     *
     * @param is file location
     * @return String
     * @throws Exception e
     */
    private static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }




    @Override
    public void onLoginClicked(String password) {

        if (password.equals("1234")) {

            managerLoginDialog.cancelDialog();

            if (mManagerFragment == null) {
                mManagerFragment = ManagerFragment.newInstance();
            }

            mFragmentHelper.replaceFragment(R.id.MA_content_frame_FL, mManagerFragment, ManagerFragment.TAG, null);

//            Intent intent = new Intent(this, ManagerActivity.class);
//            intent.putExtra("MODEL", (Parcelable) model);
//            startActivity(intent);

        } else {

            Toast.makeText(this, getResources().getString(R.string.wrong_password), Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void OnManagerLogin() {

        managerLogin();

    }







    @Override
    public void onAddMessageClicked() {

        AddMessageDialog addMessageDialog = new AddMessageDialog();
        addMessageDialog.showDialog(this, this);
    }




    @Override
    public void onAddClicked(final MessageObject messageObject) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                 messageDatabase.messageDao().insertMessage(messageObject);
            }
        }).start();

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 10);

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, MessageReciver.class);
        intent.putExtra("MESSAGE", messageObject.getMessage());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);


    }


    @Override
    public void update(Observable o, Object arg) {

        mWallFragment.refreshMessages(((Intent) arg).getStringExtra("MESSAGE"));

    }


    public static class MessageReciver extends BroadcastReceiver{


        @Override
        public void onReceive(Context context, Intent intent) {

            ObservableObject.getInstance().updateValue(intent);


        }


    }




}
