package com.example.myapplication.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;


import androidx.annotation.NonNull;
import androidx.annotation.Size;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;


/**
 * des ：
 * created by ：wuchangbin
 * created on：2018/7/12
 */
public class PermissionUtil {

    public static boolean hasPermissions(@NonNull Context context, @Size(min = 1) @NonNull String... perms) {
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        for (String perm : perms) {
            if (ContextCompat.checkSelfPermission(context, perm) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static boolean shouldShowRationale(Activity activity, List<String> permissionList) {
        for (String perm : permissionList) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, perm)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAllGranted(int[] grantResults){
        boolean isAllGranted = true;
        if(grantResults.length > 0){
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }
        }else{
            isAllGranted = false;
        }
        return isAllGranted;
    }


    /**
     * 跳转到系统设置页面
     */
    public static void goIntentSetting(Activity activity, int requestCode) {
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
            intent.setData(uri);
            activity.startActivityForResult(intent,requestCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static List<String> getNecessaryAuthorizePermissionList(Context context) {
        if (Build.VERSION.SDK_INT < 23) {
            return null;
        }
        List<String> permissionList = new ArrayList<>();
        String[] permissionArray = new String[]{
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        for (String permission : permissionArray) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }
        return permissionList;
    }

}
