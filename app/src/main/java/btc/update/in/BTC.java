package btc.update.in;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by subhashhardaha on 13/12/17.
 */


    @IgnoreExtraProperties
    public class BTC {
    private String name;
    private String buy;
    private String sell;
    public String ref;
    private int thumbnail;

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }



    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }



    public BTC(String name,  String buy, String sell,String ref,int thumbnail) {
        this.name = name;
        this.ref = ref;
        this.buy = buy;
        this.sell = sell;
        this.thumbnail=thumbnail;

    }

    public BTC() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }



}