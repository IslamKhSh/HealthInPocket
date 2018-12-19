package islamkhsh.com.healthinpocket.data.model;


import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Created by ESLAM on 6/23/2017.
 */

public class MeasuredItem {
    private int itemType;
    private String itemValue, itemKey, itemDate;

    public MeasuredItem() {

    }

    public MeasuredItem(int itemType, String itemValue) {
        this.itemType = itemType;
        this.itemValue = itemValue;
        this.itemDate = new SimpleDateFormat("dd/mm/yyyy  HH:MM").format(new GregorianCalendar());
    }


    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public String getItemDate() {
        return itemDate;
    }

    public void setItemDate(String itemDate) {
        this.itemDate = itemDate;
    }

    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }
}
