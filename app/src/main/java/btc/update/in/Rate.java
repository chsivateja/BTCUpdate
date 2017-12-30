package btc.update.in;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by subhashhardaha on 13/12/17.
 */


@IgnoreExtraProperties
public class Rate {
    public String b;
    public String s;

    public Rate() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Rate(String b, String s) {
        this.b = b;
        this.s = s;
    }

}