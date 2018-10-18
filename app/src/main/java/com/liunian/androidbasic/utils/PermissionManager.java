package com.liunian.androidbasic.utils;

import android.app.Activity;
import android.content.Context;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by dell on 2018/10/18.
 */

public class PermissionManager {

    /**
     *
     * @param context
     * return true:已经获取权限
     * return false: 未获取权限，主动请求权限
     */
    public static boolean checkPermission(Context context, String[] perms) {
        return EasyPermissions.hasPermissions(context, perms);
    }
    /**
     * 请求权限
     * @param context
     */
    public static void requestPermission(Activity context, String tip, int requestCode, String[] perms) {
        EasyPermissions.requestPermissions(context, tip,requestCode,perms);
    }
}