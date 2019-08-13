package com.example.myapplication.permission;

import androidx.fragment.app.FragmentActivity;

public class BaseActivity extends FragmentActivity {
    private PermissionResultCallback mPermissionResultCallback;
    public void setPermissionsResultAction(PermissionResultCallback callback) {
        mPermissionResultCallback = callback;
    }
}
