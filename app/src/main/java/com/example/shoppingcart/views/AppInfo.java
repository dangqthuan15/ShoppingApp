package com.example.shoppingcart.views;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import androidx.core.content.ContextCompat;
import com.example.shoppingcart.R;

import java.util.ArrayList;
import java.util.List;

import jp.tagcast.helper.TGCAdapter;

public class AppInfo extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        TGCAdapter tgcAdapter = TGCAdapter.getInstance(getApplicationContext());
        // API-keyをセット
        tgcAdapter.setApiKey(getString(R.string.tagcast_api_key));
        // 最適化モードを設定
        tgcAdapter.setOptimizationMode(true);
        if (checkPermission().size() == 0) {
            // 初期処理
            tgcAdapter.prepare();
        }
    }

    public List<String> checkPermission() {
        List<String> permissions = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)  != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        return permissions;
    }
}
