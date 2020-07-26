package co.il.zmanim.room;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import co.il.zmanim.ItimObject;

public class ZemanimObject implements Serializable {

    @SerializedName("itim")
    private List<ItimObject> itimObjectList;

    public List<ItimObject> getItimObjectList() {
        return itimObjectList;
    }

    public void setItimObjectList(List<ItimObject> itimObjectList) {
        this.itimObjectList = itimObjectList;
    }

}
