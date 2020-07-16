package co.il.zmanim;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ZemanimObject {

    @SerializedName("itim")
    private List<ItimObject> itimObjectList;

    public List<ItimObject> getItimObjectList() {
        return itimObjectList;
    }

    public void setItimObjectList(List<ItimObject> itimObjectList) {
        this.itimObjectList = itimObjectList;
    }

}
