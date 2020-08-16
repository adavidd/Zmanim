package co.il.zmanim;

import android.annotation.SuppressLint;
import android.content.Context;
import android.lib.widget.verticalmarqueetextview.VerticalMarqueeTextView;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dafyomilibrary.DafYomiCalculator;
import com.example.dafyomilibrary.DafYomiDetailes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import co.il.zmanim.room.ZemanimObject;


public class WallFragment extends Fragment {


    public static final String TAG = WallFragment.class.getSimpleName();
    private static final String ZMANIM_OBJECT = "ZMANIM_OBJECT";
    private TextView mDafYomiTV;
    private TextClock mTimeTC;
    private VerticalMarqueeTextView mZemanimTv;
    private TextView mDateTv;
    private VerticalMarqueeTextView mTefilaZemanimTv;
    private TextView mShullNameTv;
    private int managerBtnClicked = 5;
    private Toast toast;
    private boolean dialogOpen;
    private ManagerLoginDialog managerLoginDialog;
    private VerticalMarqueeTextView mMessagesTv;
    private MainViewModel model;
    private WallFragmentListener mListener;
    private ZemanimObject mZmanimObject;


    public WallFragment() {
        // Required empty public constructor
    }


    public static WallFragment newInstance(ZemanimObject zemanimObject) {
        WallFragment fragment = new WallFragment();
        Bundle args = new Bundle();
        args.putSerializable(ZMANIM_OBJECT, zemanimObject);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mZmanimObject = (ZemanimObject) getArguments().getSerializable(ZMANIM_OBJECT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wall, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews();
        setTheDafYomi();
        setCurrentTime();
        initManagerLoginListener();
        setZmanimViews(mZmanimObject);
        setTefilaZemanim();
        setFiledsFromLiveData();
    }




    @Override
    public void onResume() {
        super.onResume();

        dialogOpen = false;
    }

    private void initViews() {


        mDafYomiTV = getView().findViewById(R.id.MA_DafYomiTV);
        mTimeTC = getView().findViewById(R.id.MA_TimeTC);
        mZemanimTv = getView().findViewById(R.id.MA_zmanim_TV);
        mDateTv = getView().findViewById(R.id.MA_date_TV);
        mTefilaZemanimTv = getView().findViewById(R.id.MA_tefila_zmanim_TV);
        mShullNameTv = getView().findViewById(R.id.MA_name_TV);
        mMessagesTv = getView().findViewById(R.id.MA_messages_TV);


    }



    @SuppressLint("SetTextI18n")
    private void setTheDafYomi() {

        DafYomiCalculator dafYomiCalculator = new DafYomiCalculator();
        DafYomiDetailes todayDafYomiDetailes = dafYomiCalculator.getTodayDafYomi(getContext(), "He");

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
        mDateTv.setText(zemanimObject.getItimObjectList().get(0).getHeDate());
    }





    private void setTefilaZemanim() {

        @SuppressLint("DefaultLocale") String output = String.format("%s : %s\n", "שחרית", "08:15");
        @SuppressLint("DefaultLocale") String output2 = String.format("%s : %s\n", "מנחה", "18:15");
        @SuppressLint("DefaultLocale") String output3 = String.format("%s : %s\n", "ערבית", "20:30");

        mTefilaZemanimTv.setText(output + output2 + output3);



    }



    private void setFiledsFromLiveData() {


        MainViewModel model = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
        mMessagesTv.setText(model.getFruitList().getValue());

    }





    private void initManagerLoginListener() {

        mShullNameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!dialogOpen) {

                    managerBtnClicked--;

                    if (toast != null) {

                        toast.cancel();
                    }

                    if (managerBtnClicked == 0) {

                        mListener.OnManagerLogin();
                        managerBtnClicked = 5;
                        dialogOpen = true;

                    } else {

                        toast = Toast.makeText(getContext(), getResources().getString(R.string.press_again, managerBtnClicked), Toast.LENGTH_SHORT);

                        toast.show();

                    }


                }

            }
        });


    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof WallFragmentListener) {
            mListener = (WallFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement WallFragmentListener");
        }
    }

    public void messageChanged(String message) {

        mMessagesTv.setText(message);

    }




    public void refreshMessages(String message) {


        mMessagesTv.setText(message);


    }



    interface WallFragmentListener{


        void OnManagerLogin();
    }



}
