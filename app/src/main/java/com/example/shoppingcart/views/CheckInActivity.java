package com.example.shoppingcart.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.example.shoppingcart.R;


import java.util.List;
import java.util.Map;

import jp.tagcast.bleservice.TGCErrorCode;
import jp.tagcast.bleservice.TGCScanListener;
import jp.tagcast.bleservice.TGCState;
import jp.tagcast.bleservice.TGCType;
import jp.tagcast.bleservice.TagCast;
import jp.tagcast.helper.TGCAdapter;

public class CheckInActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    Button btncheckin;
    public TGCAdapter tgcAdapter;
    private boolean flgBeacon = false;
    public int mErrorDialogType = ErrorDialogFragment.TYPE_NO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_view);
        final Context context = getApplicationContext();
        tgcAdapter = TGCAdapter.getInstance(context);
        btncheckin = findViewById(R.id.btnCheckIn);

        btncheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tgcAdapter.setScanInterval(10000);
                tgcAdapter.startScan();

            }
        });
     final  TGCScanListener mTgcScanListener = new TGCScanListener() {
            @Override
            public void changeState(TGCState tgcState) {

            }

            @Override
            public void didDiscoverdTagcast(TagCast tagCast) {
                if(tagCast.getTgcType() == TGCType.TGCTypePaperBeacon){
                    flgBeacon = true;
                    Map<String, String> s = tagCast.getDetails();
                    Log.d("here", s.toString());
                }
            }

            @Override
            public void didScannedTagcasts(List<TagCast> list) {

            }

            @Override
            public void didScannedStrengthOrderTagcasts(List<TagCast> list) {

            }

            @Override
            public void didFailWithError(TGCErrorCode tgcErrorCode) {
                final FragmentManager fragmentManager = getSupportFragmentManager();

                    if (fragmentManager == null) {
                        return;
                    }
                    String title = null;
                    String message = null;
                    switch (tgcErrorCode) {
                        case TGCErrorCodeUnknown:
                            mErrorDialogType = ErrorDialogFragment.TYPE_RETRY;
                            title = getString(R.string.unknownErrorTitle);
                            message = getString(R.string.unknownErrorMessage);
                            break;
                        case TGCErrorCodeDatabase:
                            mErrorDialogType = ErrorDialogFragment.TYPE_RETRY;
                            title = getString(R.string.databaseErrorTitle);
                            message = getString(R.string.databaseErrorMessage);
                            break;
                        case TGCErrorCodeNetwork:
                            mErrorDialogType = ErrorDialogFragment.TYPE_RETRY;
                            title = getString(R.string.networkErrorTitle);
                            message = getString(R.string.networkErrorMessage);
                            break;
                        case TGCErrorCodeBluetooth:
                            if (mErrorDialogType == ErrorDialogFragment.TYPE_RETRY) {
                                return;
                            }
                            mErrorDialogType = ErrorDialogFragment.TYPE_RETRY;
                            title = getString(R.string.bluetoothErrorTitle);
                            message = getString(R.string.bluetoothErrorMessage);
                            break;
                        case TGCErrorCodeDebugDataInvalid:
                            mErrorDialogType = ErrorDialogFragment.TYPE_OK;
                            title = getString(R.string.databaseErrorTitle);
                            message = getString(R.string.databaseErrorMessage);
                            break;
                        case TGCErrorCodeAPIKeyNotRegistered:
                            mErrorDialogType = ErrorDialogFragment.TYPE_OK;
                            title = getString(R.string.apiKeyNotRegisteredErrorTitle);
                            message = getString(R.string.apiKeyNotRegisteredErrorMessage);
                            break;
                        case TGCErrorCodeInvalidScanInterval:
                            mErrorDialogType = ErrorDialogFragment.TYPE_NO;
                            break;
                        case TGCErrorCodePermissionDenied:
                            mErrorDialogType = ErrorDialogFragment.TYPE_OK;
                            title = getString(R.string.permissionDeniedErrorTitle);
                            message = getString(R.string.permissionDeniedErrorMessage);
                            break;
                        case TGCErrorCodeMasterDataFailedUpdate:
                            mErrorDialogType = ErrorDialogFragment.TYPE_UPDATE;
                            title = getString(R.string.networkErrorTitle);
                            message = getString(R.string.failedUpdateErrorMessage);
                            break;
                        case TGCErrorCodeLocationAccess:
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (mErrorDialogType == ErrorDialogFragment.TYPE_RETRY) {
                                    return;
                                }
                                mErrorDialogType = ErrorDialogFragment.TYPE_RETRY;
                                title = getString(R.string.localeAccessErrorTitle);
                                message = getString(R.string.localeAccessErrorMessage);
                            } else {
                                return;
                            }
                            break;
                        default:
                            break;
                    }
                    if (mErrorDialogType != ErrorDialogFragment.TYPE_NO){
                        ErrorDialogFragment errorDialog = new ErrorDialogFragment();
                        Bundle arguments = new Bundle();
                        arguments.putString(ErrorDialogFragment.KEY_TITLE, title);
                        arguments.putString(ErrorDialogFragment.KEY_MESSAGE, message);
                        arguments.putInt(ErrorDialogFragment.KEY_TYPE, mErrorDialogType);
                        errorDialog.setArguments(arguments);
                        ErrorDialogFragment.showDialogFragment(fragmentManager,ErrorDialogFragment.class.getName(),errorDialog);
                    }

            }
        };

        tgcAdapter.setTGCScanListener(mTgcScanListener);

    }

    @Override
    protected void onPause() {
        super.onPause();
        tgcAdapter.stopScan();
    }
    @Override
    protected void onResume() {
        if(checkPermission()) {
            super.onResume();
            tgcAdapter.prepare();
        }
    }

    /**
     * Yêu cầu cấp phép
     */
    private boolean checkPermission() {
        List<String> permissions = ((AppInfo) getApplication()).checkPermission();
        if (permissions.size() == 0) {
            return true;
        } else {
            try {
                String[] array = new String[permissions.size()];
                permissions.toArray(array);
                ActivityCompat.requestPermissions(CheckInActivity.this, array, 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1) {
            if (permissions.length == 0 || grantResults.length == 0) {
                return;
            }
            boolean isGranted = true;
            for (int i = 0; i< permissions.length; i++) {
                if (grantResults.length <= i || grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    isGranted = false;
                }
            }
            if (isGranted) {
                // Thu thập dữ liệu quản lý TagCast
                tgcAdapter.prepare();

                // Bắt đầu quét
                tgcAdapter.startScan();

            } else {
                finish();
            }
        }
    }
}