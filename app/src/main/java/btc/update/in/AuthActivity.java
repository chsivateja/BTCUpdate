package btc.update.in;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.BuildConfig;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AuthActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            // already signed in
            Intent i = new Intent(this, UpdateActivity.class);
            startActivity(i);
            finish();
        } else {

            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setLogo(R.mipmap.ic_launcher)
                            .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                            .setAvailableProviders(
                                    Arrays.asList(
                                            //new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                            new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build()))
                            //new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                            .build(),
                    RC_SIGN_IN);
        }


    }

    private boolean checkAndRequestPermissions() {
        int permissionPHONE = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE);


        int permissionAccount = ContextCompat.checkSelfPermission(this,


                Manifest.permission.GET_ACCOUNTS);
        int permissionSMS = ContextCompat.checkSelfPermission(this,


                Manifest.permission.READ_SMS);


        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionPHONE != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (permissionAccount != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.GET_ACCOUNTS);
        }
        if (permissionSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,


                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
            return false;
        }

        return true;
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                //startActivity(MainActivity.createIntent(this, response));
                //Toast.makeText(this,"Success"+mAuth.getCurrentUser().getEmail(),Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, UpdateActivity.class);
                startActivity(i);
                finish();
                return;
            } else {
                Toast.makeText(this, "Failed" + response.getErrorCode(), Toast.LENGTH_SHORT).show();
//                    // Sign in failed
//                    if (response == null) {
//                        // User pressed back button
//                        showSnackbar(R.string.sign_in_cancelled);
//                        return;
//                    }
//
//                    if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
//                        showSnackbar(R.string.no_internet_connection);
//                        return;
//                    }
//
//                    if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
//                        showSnackbar(R.string.unknown_error);
//                        return;
//                    }
            }

            //showSnackbar(R.string.unknown_sign_in_response);
        }
    }

}

