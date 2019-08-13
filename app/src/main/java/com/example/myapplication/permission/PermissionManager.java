package com.example.myapplication.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;


import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import java.util.ArrayList;
import java.util.List;

/**
 * des ：
 * created by ：wuchangbin
 * created on：2019/5/6
 */
public class PermissionManager {

    private static final int PERMISSION_REQUEST_CODE = 9999;

    private static PermissionManager mInstance;

    private PermissionManager() {

    }

    public static PermissionManager getInstance() {
        if (mInstance == null) {
            synchronized (PermissionManager.class) {
                if (mInstance == null) {
                    mInstance = new PermissionManager();
                }
            }
        }
        return mInstance;
    }

    public void requestPermissionsIfNecessary(Activity activity, String[] permissionArray, PermissionResultCallback callback) {
        List<String[]> groupList = new ArrayList<>();
        groupList.add(permissionArray);
        requestPermissionsIfNecessary(activity, groupList,PERMISSION_REQUEST_CODE,callback);
    }

    public void requestPermissionsIfNecessary(Activity activity, List<String[]> permissionGroupList, PermissionResultCallback callback) {
        requestPermissionsIfNecessary(activity,permissionGroupList,PERMISSION_REQUEST_CODE,callback);
    }

    private void requestPermissionsIfNecessary(final Activity activity, List<String[]> permissionGroupList, final int requestCode, PermissionResultCallback permissionResultCallback) {
        if (activity instanceof BaseActivity) {
            if (Build.VERSION.SDK_INT < 23) {
                if (permissionResultCallback != null) {
                    permissionResultCallback.onGranted();
                }
                return;
            }
            List<String> permissionList = new ArrayList<>();
            for(String[] permissionArray : permissionGroupList){
                for (String permission : permissionArray) {
                    if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                        permissionList.add(permission);
                    }
                }
            }
            if (permissionList.size() > 0) {
                BaseActivity baseActivity = (BaseActivity) activity;
                baseActivity.setPermissionsResultAction(permissionResultCallback);

                String[] requestArray = new String[permissionList.size()];
                permissionList.toArray(requestArray);
                ActivityCompat.requestPermissions(activity, requestArray, requestCode);
            } else {
                if (permissionResultCallback != null) {
                    permissionResultCallback.onGranted();
                }
            }
        }
    }

    public void onRequestPermissionsResult(int[] grantResults, PermissionResultCallback action) {
        if (PermissionUtil.isAllGranted(grantResults)) {
            action.onGranted();
        } else {
            action.onDenied();
        }
    }
}
