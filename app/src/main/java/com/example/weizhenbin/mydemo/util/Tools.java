package com.example.weizhenbin.mydemo.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by weizhenbin on 16/8/4.
 * 工具类
 */
public class Tools {
    /**
     * dp转px
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转px
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px转sp
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 各种单位转换
     * 该方法存在于TypedValue
     */
    public static float applyDimension(int unit, float value, DisplayMetrics metrics) {
        switch (unit) {
            case TypedValue.COMPLEX_UNIT_PX:
                return value;
            case TypedValue.COMPLEX_UNIT_DIP:
                return value * metrics.density;
            case TypedValue.COMPLEX_UNIT_SP:
                return value * metrics.scaledDensity;
            case TypedValue.COMPLEX_UNIT_PT:
                return value * metrics.xdpi * (1.0f / 72);
            case TypedValue.COMPLEX_UNIT_IN:
                return value * metrics.xdpi;
            case TypedValue.COMPLEX_UNIT_MM:
                return value * metrics.xdpi * (1.0f / 25.4f);
        }
        return 0;
    }

    /**
     * 获取设备MAC地址
     * 需添加权限<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
     */
    public static String getMacAddress(Context context) {
        String macAddress = null;
        WifiManager wifi = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        if (null != info ) {
            macAddress = info.getMacAddress();
            if (null != macAddress) {
                macAddress = macAddress.replace(":", "");
            }
        }
        return macAddress;
    }

    /**
     * 获取设备厂商，如Xiaomi
     */
    public static String getManufacturer() {
        String MANUFACTURER = Build.MANUFACTURER;
        return MANUFACTURER;
    }

    /**
     * 获取设备型号，如MI2SC
     */
    public static String getModel() {
        String model = Build.MODEL;
        if (model != null) {
            model = model.trim().replaceAll("\\s*", "");
        } else {
            model = "";
        }
        return model;
    }

    /**
     * 获取设备SD卡是否可用
     */
    public static boolean isSDCardEnable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取设备SD卡路径
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    }

    /**
     * 判断设备是否是手机
     */
    public static boolean isPhone(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
    }

    /**
     * 获取当前设备的IMIE，需与上面的isPhone一起使用
     * 需添加权限<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
     */
    public static String getDeviceIMEI(Context context) {
        String deviceId;
        if (isPhone(context)) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = tm.getDeviceId();
        } else {
            deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceId;
    }


    /**
     * 获取手机状态信息
     * 需添加权限<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
     * 返回如下
     * DeviceId(IMEI) = 99000311726612
     * DeviceSoftwareVersion = 00
     * Line1Number =
     * NetworkCountryIso = cn
     * NetworkOperator = 46003
     * NetworkOperatorName = 中国电信
     * NetworkType = 6
     * honeType = 2
     * SimCountryIso = cn
     * SimOperator = 46003
     * SimOperatorName = 中国电信
     * SimSerialNumber = 89860315045710604022
     * SimState = 5
     * SubscriberId(IMSI) = 460030419724900
     * VoiceMailNumber = *86
     */
    public static String getPhoneStatus(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String str = "";
        str += "DeviceId(IMEI) = " + tm.getDeviceId() + "\n";
        str += "DeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion() + "\n";
        str += "Line1Number = " + tm.getLine1Number() + "\n";
        str += "NetworkCountryIso = " + tm.getNetworkCountryIso() + "\n";
        str += "NetworkOperator = " + tm.getNetworkOperator() + "\n";
        str += "NetworkOperatorName = " + tm.getNetworkOperatorName() + "\n";
        str += "NetworkType = " + tm.getNetworkType() + "\n";
        str += "honeType = " + tm.getPhoneType() + "\n";
        str += "SimCountryIso = " + tm.getSimCountryIso() + "\n";
        str += "SimOperator = " + tm.getSimOperator() + "\n";
        str += "SimOperatorName = " + tm.getSimOperatorName() + "\n";
        str += "SimSerialNumber = " + tm.getSimSerialNumber() + "\n";
        str += "SimState = " + tm.getSimState() + "\n";
        str += "SubscriberId(IMSI) = " + tm.getSubscriberId() + "\n";
        str += "VoiceMailNumber = " + tm.getVoiceMailNumber() + "\n";
        return str;
    }


    // 需添加权限<uses-permission android:name="android.permission.CALL_PHONE"/>
    /**
     * 拨打电话
     */
    /*public static void callDial(Context context, String phoneNumber) {
        context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber)));
    }*/

    /**
     * 发送短信
     */
    public static void sendSms(Context context, String phoneNumber, String content) {
        Uri uri = Uri.parse("smsto:" + (TextUtils.isEmpty(phoneNumber) ? "" : phoneNumber));
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", TextUtils.isEmpty(content) ? "" : content);
        context.startActivity(intent);
    }

    /**
     * 获取手机联系人
     * 需添加权限<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
     * 需添加权限<uses-permission android:name="android.permission.READ_CONTACTS" />
     */
    public static List<HashMap<String, String>> getAllContactInfo(Context context) {
        SystemClock.sleep(3000);
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        // 1.获取内容解析者
        ContentResolver resolver = context.getContentResolver();
        // 2.获取内容提供者的地址:com.android.contacts
        // raw_contacts表的地址 :raw_contacts
        // view_data表的地址 : data
        // 3.生成查询地址
        Uri raw_uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri date_uri = Uri.parse("content://com.android.contacts/data");
        // 4.查询操作,先查询raw_contacts,查询contact_id
        // projection : 查询的字段
        Cursor cursor = resolver.query(raw_uri, new String[] { "contact_id" },
                null, null, null);
        // 5.解析cursor
        while (cursor.moveToNext()) {
            // 6.获取查询的数据
            String contact_id = cursor.getString(0);
            // cursor.getString(cursor.getColumnIndex("contact_id"));//getColumnIndex
            // : 查询字段在cursor中索引值,一般都是用在查询字段比较多的时候
            // 判断contact_id是否为空
            if (!TextUtils.isEmpty(contact_id)) {//null   ""
                // 7.根据contact_id查询view_data表中的数据
                // selection : 查询条件
                // selectionArgs :查询条件的参数
                // sortOrder : 排序
                // 空指针: 1.null.方法 2.参数为null
                Cursor c = resolver.query(date_uri, new String[] { "data1",
                                "mimetype" }, "raw_contact_id=?",
                        new String[] { contact_id }, null);
                HashMap<String, String> map = new HashMap<String, String>();
                // 8.解析c
                while (c.moveToNext()) {
                    // 9.获取数据
                    String data1 = c.getString(0);
                    String mimetype = c.getString(1);
                    // 10.根据类型去判断获取的data1数据并保存
                    if (mimetype.equals("vnd.android.cursor.item/phone_v2")) {
                        // 电话
                        map.put("phone", data1);
                    } else if (mimetype.equals("vnd.android.cursor.item/name")) {
                        // 姓名
                        map.put("name", data1);
                    }
                }
                // 11.添加到集合中数据
                list.add(map);
                // 12.关闭cursor
                c.close();
            }
        }
        // 12.关闭cursor
        cursor.close();
        return list;
    }


    /**
     * 打开手机联系人界面点击联系人后便获取该号码
     * 参照以下注释代码
     */
    public static void getContantNum() {
        Log.i("tips", "U should copy the follow code.");
    /*
    Intent intent = new Intent();
    intent.setAction("android.intent.action.PICK");
    intent.setType("vnd.android.cursor.dir/phone_v2");
    startActivityForResult(intent, 0);
    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Uri uri = data.getData();
            String num = null;
            // 创建内容解析者
            ContentResolver contentResolver = getContentResolver();
            Cursor cursor = contentResolver.query(uri,
                    null, null, null, null);
            while (cursor.moveToNext()) {
                num = cursor.getString(cursor.getColumnIndex("data1"));
            }
            cursor.close();
            num = num.replaceAll("-", "");//替换的操作,555-6 -> 5556
        }
    }
    */
    }


    /**
     * 获取手机短信并保存到xml中
     * 需添加权限<uses-permission android:name="android.permission.READ_SMS"/>
     * 需添加权限<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
     */
    public static void getAllSMS(Context context) {
        //1.获取短信
        //1.1获取内容解析者
        ContentResolver resolver = context.getContentResolver();
        //1.2获取内容提供者地址   sms,sms表的地址:null  不写
        //1.3获取查询路径
        Uri uri = Uri.parse("content://sms");
        //1.4.查询操作
        //projection : 查询的字段
        //selection : 查询的条件
        //selectionArgs : 查询条件的参数
        //sortOrder : 排序
        Cursor cursor = resolver.query(uri, new String[]{"address", "date", "type", "body"}, null, null, null);
        //设置最大进度
        int count = cursor.getCount();//获取短信的个数
        //2.备份短信
        //2.1获取xml序列器
        XmlSerializer xmlSerializer = Xml.newSerializer();
        try {
            //2.2设置xml文件保存的路径
            //os : 保存的位置
            //encoding : 编码格式
            xmlSerializer.setOutput(new FileOutputStream(new File("/mnt/sdcard/backupsms.xml")), "utf-8");
            //2.3设置头信息
            //standalone : 是否独立保存
            xmlSerializer.startDocument("utf-8", true);
            //2.4设置根标签
            xmlSerializer.startTag(null, "smss");
            //1.5.解析cursor
            while (cursor.moveToNext()) {
                SystemClock.sleep(1000);
                //2.5设置短信的标签
                xmlSerializer.startTag(null, "sms");
                //2.6设置文本内容的标签
                xmlSerializer.startTag(null, "address");
                String address = cursor.getString(0);
                //2.7设置文本内容
                xmlSerializer.text(address);
                xmlSerializer.endTag(null, "address");
                xmlSerializer.startTag(null, "date");
                String date = cursor.getString(1);
                xmlSerializer.text(date);
                xmlSerializer.endTag(null, "date");
                xmlSerializer.startTag(null, "type");
                String type = cursor.getString(2);
                xmlSerializer.text(type);
                xmlSerializer.endTag(null, "type");
                xmlSerializer.startTag(null, "body");
                String body = cursor.getString(3);
                xmlSerializer.text(body);
                xmlSerializer.endTag(null, "body");
                xmlSerializer.endTag(null, "sms");
                System.out.println("address:" + address + "   date:" + date + "  type:" + type + "  body:" + body);
            }
            xmlSerializer.endTag(null, "smss");
            xmlSerializer.endDocument();
            //2.8将数据刷新到文件中
            xmlSerializer.flush();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 打开网络设置界面
     * 3.0以下打开设置界面
     */
    public static void openWirelessSettings(Context context) {
        if (Build.VERSION.SDK_INT > 10) {
            context.startActivity(new Intent(Settings.ACTION_SETTINGS));
        } else {
            context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
        }
    }

    /**
     * 判断是否网络连接
     * 需添加权限<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }


    /**
     * 判断wifi是否连接状态
     * 需添加权限<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm != null && cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 获取移动网络运营商名称
     * 如中国联通、中国移动、中国电信
     */
    public static String getNetworkOperatorName(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getNetworkOperatorName() : null;
    }

    /**
     * 获取移动终端类型
     * PHONE_TYPE_NONE  : 0 手机制式未知
     * PHONE_TYPE_GSM   : 1 手机制式为GSM，移动和联通
     * PHONE_TYPE_CDMA  : 2 手机制式为CDMA，电信
     * PHONE_TYPE_SIP   : 3
     */
    public static int getPhoneType(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getPhoneType() : -1;
    }

    /**
     * 获取连接的网络类型(2G,3G,4G)
     * 联通的3G为UMTS或HSDPA，移动和联通的2G为GPRS或EGDE，电信的2G为CDMA，电信的3G为EVDO
     */
    public static int getNetTpye(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return Constants.NETWORK_CLASS_2_G;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return Constants.NETWORK_CLASS_3_G;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return Constants.NETWORK_CLASS_4_G;
            default:
                return Constants.NETWORK_CLASS_UNKNOWN;
        }
    }
    public class Constants {
        // Unknown network class
        public static final int NETWORK_CLASS_UNKNOWN = 0;
        // wifi network
        public static final int NETWORK_WIFI = 1;
        // "2G" networks
        public static final int NETWORK_CLASS_2_G = 2;
        // "3G" networks
        public static final int NETWORK_CLASS_3_G = 3;
        // "4G" networks
        public static final int NETWORK_CLASS_4_G = 4;
    }


    /**
     * 获取当前手机的网络类型(WIFI,2G,3G,4G)
     * 需添加权限<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
     * 需要用到上面的方法
     */
    public static int getCurNetworkType(Context context) {
        int netWorkType = Constants.NETWORK_CLASS_UNKNOWN;
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            int type = networkInfo.getType();
            if (type == ConnectivityManager.TYPE_WIFI) {
                netWorkType = Constants.NETWORK_WIFI;
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                netWorkType = getNetTpye(context);
            }
        }
        return netWorkType;
    }

    /**
     * 安装指定路径下的Apk
     * 根据路径名是否符合和文件是否存在判断是否安装成功
     * 更好的做法应该是startActivityForResult回调判断是否安装成功比较妥当
     * 这里做不了回调，后续自己做处理
     */
    public static boolean installApp(Context context, String filePath) {
        if (filePath != null && filePath.length() > 4
                && filePath.toLowerCase().substring(filePath.length() - 4).equals(".apk")) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            File file = new File(filePath);
            if (file.exists() && file.isFile() && file.length() > 0) {
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                return true;
            }
        }
        return false;
    }


    /**
     * 卸载指定包名的App
     * 这里卸载成不成功只判断了packageName是否为空
     * 如果要根据是否卸载成功应该用startActivityForResult回调判断是否还存在比较妥当
     * 这里做不了回调，后续自己做处理
     */
    public boolean uninstallApp(Context context, String packageName) {
        if (!TextUtils.isEmpty(packageName)) {
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(Uri.parse("package:" + packageName));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        }
        return false;
    }


    /**
     * 封装App信息的Bean类
     */
    public static class AppInfo {

        private String name;
        private Drawable icon;
        private String packagName;
        private String versionName;
        private int versionCode;
        private boolean isSD;
        private boolean isUser;

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(Drawable icon) {
            this.icon = icon;
        }

        public boolean isSD() {
            return isSD;
        }

        public void setSD(boolean SD) {
            isSD = SD;
        }

        public boolean isUser() {
            return isUser;
        }

        public void setUser(boolean user) {
            isUser = user;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPackagName() {
            return packagName;
        }

        public void setPackagName(String packagName) {
            this.packagName = packagName;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        /**
         * @param name        名称
         * @param icon        图标
         * @param packagName  包名
         * @param versionName 版本号
         * @param versionCode 版本Code
         * @param isSD        是否安装在SD卡
         * @param isUser      是否是用户程序
         */
        public AppInfo(String name, Drawable icon, String packagName,
                       String versionName, int versionCode, boolean isSD, boolean isUser) {
            this.setName(name);
            this.setIcon(icon);
            this.setPackagName(packagName);
            this.setVersionName(versionName);
            this.setVersionCode(versionCode);
            this.setSD(isSD);
            this.setUser(isUser);
        }
    }
    /**
     * 获取当前App信息
     * AppInfo（名称，图标，包名，版本号，版本Code，是否安装在SD卡，是否是用户程序）
     */
    public static AppInfo getAppInfo(Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(context.getApplicationContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pi != null ? getBean(pm, pi) : null;
    }

    /**
     * 得到AppInfo的Bean
     */
    private static AppInfo getBean(PackageManager pm, PackageInfo pi) {
        ApplicationInfo ai = pi.applicationInfo;
        String name = pi.packageName;
        Drawable icon = ai.loadIcon(pm);
        String packageName = pi.packageName;
        String versionName = pi.versionName;
        int versionCode = pi.versionCode;
        boolean isSD = (ApplicationInfo.FLAG_SYSTEM & ai.flags) != ApplicationInfo.FLAG_SYSTEM;
        boolean isUser = (ApplicationInfo.FLAG_SYSTEM & ai.flags) != ApplicationInfo.FLAG_SYSTEM;
        return new AppInfo(name, icon, packageName, versionName, versionCode, isSD, isUser);
    }

    /**
     * 获取所有已安装App信息
     * AppInfo（名称，图标，包名，版本号，版本Code，是否安装在SD卡，是否是用户程序）
     * 依赖上面的getBean方法
     */
    public static List<AppInfo> getAllAppsInfo(Context context) {
        List<AppInfo> list = new ArrayList<>();
        PackageManager pm = context.getPackageManager();
        // 获取系统中安装的所有软件信息
        List<PackageInfo> installedPackages = pm.getInstalledPackages(0);
        for (PackageInfo pi : installedPackages) {
            if (pi != null) {
                list.add(getBean(pm, pi));
            }
        }
        return list;
    }

    /**
     * 打开指定包名的App
     */
    public static boolean openAppByPackageName(Context context, String packageName) {
        if (!TextUtils.isEmpty(packageName)) {
            PackageManager pm = context.getPackageManager();
            Intent launchIntentForPackage = pm.getLaunchIntentForPackage(packageName);
            if (launchIntentForPackage != null) {
                context.startActivity(launchIntentForPackage);
                return true;
            }
        }
        return false;
    }

    /**
     * 打开指定包名的App应用信息界面
     */
    public static boolean openAppInfo(Context context, String packageName) {
        if (!TextUtils.isEmpty(packageName)) {
            Intent intent = new Intent();
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.parse("package:" + packageName));
            context.startActivity(intent);
            return true;
        }
        return false;
    }

    /**
     * 可用来做App信息分享
     */
    public static void shareAppInfo(Context context, String info) {
        if (!TextUtils.isEmpty(info)) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, info);
            context.startActivity(intent);
        }
    }

    /**
     * 判断当前App处于前台还是后台
     * 需添加<uses-permission android:name="android.permission.GET_TASKS"/>
     * 并且必须是系统应用该方法才有效
     */
    public static boolean isAppBackground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        @SuppressWarnings("deprecation")
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取屏幕的宽度px
     */
    public static int getDeviceWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();// 创建了一张白纸
        windowManager.getDefaultDisplay().getMetrics(outMetrics);// 给白纸设置宽高
        return outMetrics.widthPixels;
    }

    /**
     * 获取屏幕的高度px
     */
    public static int getDeviceHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();// 创建了一张白纸
        windowManager.getDefaultDisplay().getMetrics(outMetrics);// 给白纸设置宽高
        return outMetrics.heightPixels;
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int result = dp2px(context,25);
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result =context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取ActionBar高度
     */
    public static int getActionBarHeight(Activity activity) {
        TypedValue tv = new TypedValue();
        if (activity.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data, activity.getResources().getDisplayMetrics());
        }
        return 0;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     */
    public static Bitmap captureWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getDeviceWidth(activity);
        int height = getDeviceHeight(activity);
        Bitmap bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     * 需要用到上面获取状态栏高度的方法
     */
    public static Bitmap captureWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int statusBarHeight = getStatusBarHeight(activity);
        int width = getDeviceWidth(activity);
        int height = getDeviceHeight(activity);
        Bitmap bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 设置透明状态栏(api >= 19方可使用)
     * 可在Activity的onCreat()中调用
     * 需在顶部控件布局中加入以下属性让内容出现在状态栏之下
     * android:clipToPadding="true"
     * android:fitsSystemWindows="true"
     */
    public static void setTransparentStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 动态隐藏软键盘
     */
    public static void hideSoftInput(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 动态隐藏软键盘
     */
    public static void hideSoftInput(Context context, EditText edit) {
        edit.clearFocus();
        InputMethodManager inputmanger = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputmanger.hideSoftInputFromWindow(edit.getWindowToken(), 0);
    }


    /**
     * 动态显示软键盘
     */
    public static void showSoftInput(Context context, EditText edit) {
        edit.setFocusable(true);
        edit.setFocusableInTouchMode(true);
        edit.requestFocus();
        InputMethodManager inputManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(edit, 0);
    }

    /****MD5***/
    /**
     * 一个byte转为2个hex字符
     */
    public static String bytes2Hex(byte[] src) {
        char[] res = new char[src.length * 2];
        final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        for (int i = 0, j = 0; i < src.length; i++) {
            res[j++] = hexDigits[src[i] >>> 4 & 0x0f];
            res[j++] = hexDigits[src[i] & 0x0f];
        }
        return new String(res);
    }

    /**
     * MD5加密
     *
     * @param data 明文
     * @return 密文
     */
    public static String getMD5(String data) {
        return TextUtils.isEmpty(data) ? "" : getMD5(data.getBytes());
    }

    /**
     * MD5加密
     *
     * @param data 明文字节数组
     * @return 密文
     */
    public static String getMD5(byte[] data) {
        if (data.length > 0) {
            return bytes2Hex(encryptMD5(data));
        }
        return "";
    }

    /**
     * MD5加密
     *
     * @param data 明文字节数组
     * @return 密文字节数组
     */
    public static byte[] encryptMD5(byte[] data) {
        if (data.length > 0) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(data);
                return md.digest();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 获取文件的MD5校验码
     *
     * @param filePath 文件路径
     * @return 文件的MD5校验码
     */
    public static String getMD5File(String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            FileInputStream in = null;
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                in = new FileInputStream(filePath);
                int len;
                byte[] buffer = new byte[1024];
                while ((len = in.read(buffer)) != -1) {
                    md.update(buffer, 0, len);
                }
                return bytes2Hex(md.digest());
            } catch (NoSuchAlgorithmException | IOException e) {
                e.printStackTrace();
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException ignored) {
                    }
                }
            }
        }
        return "";
    }
    /**----------------*/
    /****SHA加密****/
    /**
     * SHA加密
     *
     * @param data 明文
     * @return 密文
     */
    public static String getSHA(String data) {
        return TextUtils.isEmpty(data) ? "" : getSHA(data.getBytes());
    }

    /**
     * SHA加密
     *
     * @param data 明文字节数组
     * @return 密文
     */
    public static String getSHA(byte[] data) {
        if (data.length > 0) {
            return bytes2Hex(encryptSHA(data));
        }
        return "";
    }

    /**
     * SHA加密
     * @param data 明文字节数组
     * @return 密文字节数组
     */
    public static byte[] encryptSHA(byte[] data) {
        if (data.length > 0) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(data);
                return md.digest();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    /**----------------*/


    /**
     * 获取服务是否开启
     * @param className 完整包名的服务类名
     */
    public static boolean isRunningService(String className, Context context) {
        // 进程的管理者,活动的管理者
        ActivityManager activityManager = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);
        // 获取正在运行的服务，最多获取1000个
        List<ActivityManager.RunningServiceInfo> runningServices = activityManager.getRunningServices(1000);
        // 遍历集合
        for (ActivityManager.RunningServiceInfo runningServiceInfo : runningServices) {
            ComponentName service = runningServiceInfo.service;
            if (className.equals(service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
