package co.il.zmanim;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.lib.widget.verticalmarqueetextview.VerticalMarqueeTextView;
import android.os.Bundle;
import android.view.View;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.dafyomilibrary.DafYomiCalculator;
import com.example.dafyomilibrary.DafYomiDetailes;
import com.google.gson.Gson;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {


    private TextView mDafYomiTV;
    private TextClock mTimeTC;
    private VerticalMarqueeTextView mZemanimTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setCurrentTime();
        setCurrentEnDate();
        setTheDafYomi();
        readTxtFile();

    }


    private void readTxtFile() {

        Gson gson = new Gson();
        String txt = null;
        try {
            txt = convertStreamToString(Objects.requireNonNull(this).getAssets().open("tzefat.txt"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        ZemanimObject zemanimObject = gson.fromJson(txt, ZemanimObject.class);

        setZmanimViews(zemanimObject);

    }





    private void setZmanimViews(ZemanimObject zemanimObject) {

        SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm:ss a");
        Date date = null;
        try {
            date = parseFormat.parse(zemanimObject.getItimObjectList().get(0).getShkiah() + " PM");
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        mZemanimTv.setText(displayFormat.format(date));
        mZemanimTv.setText(zemanimObject.getItimObjectList().get(0).toString());

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





    private void initViews() {

        mDafYomiTV = findViewById(R.id.MA_DafYomiTV);
        mTimeTC = findViewById(R.id.MA_TimeTC);
        mZemanimTv = findViewById(R.id.MA_zmanim_TV);


    }




    @SuppressLint("SetTextI18n")
    private void setTheDafYomi() {

        DafYomiCalculator dafYomiCalculator = new DafYomiCalculator();
        DafYomiDetailes todayDafYomiDetailes = dafYomiCalculator.getTodayDafYomi(this);

        mDafYomiTV.setText(todayDafYomiDetailes.getMasechetName() + " " + todayDafYomiDetailes.getMasechetPage());


    }


    private void setCurrentTime() {

        TextClock textClock = mTimeTC;
//        textClock.setFormat24Hour("dd MMM yyyy hh:mm:ss cccc");
//        textClock.setText(textClock.getText());


    }


    private void setCurrentEnDate() {

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

    }

}
