package co.il.zmanim;

import android.os.Handler;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;



public class MainViewModel extends ViewModel {

    private MutableLiveData<String> liveDataList;

    MutableLiveData<String> getFruitList() {
        if (liveDataList == null) {
            liveDataList = new MutableLiveData<>();
            loadFruits();
        }
        return liveDataList;
    }

    private void loadFruits() {
        // do async operation to fetch users
        Handler myHandler = new Handler();
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String fruitsStringList = "אין הודעות";

                liveDataList.setValue(fruitsStringList);
            }
        }, 1000);

    }



}
