package com.ivt.android.me.utils;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.Looper;
import android.os.StrictMode;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangshuai on 2017/1/7.
 */
public class SystemTools {

    public final static boolean isScreenLocked(Context c) {
        KeyguardManager mKeyguardManager = (KeyguardManager) c
                .getSystemService(Context.KEYGUARD_SERVICE);
        return !mKeyguardManager.inKeyguardRestrictedInputMode();
    }

    public static String getSystemDateTime() {
        return android.text.format.DateFormat.format("yyyy.MM.dd/kk:mm",
                new Date()).toString();
    }

    public static int getCurrentTime() {
        Time localTime = new Time("Asia/Hong_Kong");
        localTime.setToNow();
        return Integer.valueOf(localTime.format("%H%M"));
    }

    public static int getCurrentDate() {
        Time localTime = new Time("Asia/Hong_Kong");
        localTime.setToNow();
        return Integer.valueOf(localTime.format("%Y%m%d"));
    }

    /**
     * @param err_msg
     * @return
     * @description 规定EditText输入长度
     */
    public static void lengthFilter(final Context context,
                                    final EditText editText, final int max_length, final String err_msg) {

        InputFilter[] filters = new InputFilter[1];

        filters[0] = new InputFilter.LengthFilter(max_length) {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                int destLen = getCharacterNum(dest.toString()); // 获取字符个数(一个中文算2个字符)
                int sourceLen = getCharacterNum(source.toString());
                if (destLen + sourceLen > max_length) {
                    editText.setText(dest.toString());
                    Toast toast = Toast.makeText(context, err_msg, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return "";
                }
                return source;
            }
        };
        editText.setFilters(filters);
    }

    /**
     * @param content
     * @return
     * @description 获取一段字符串的字符个数（包含中英文，一个中文算2个字符）
     */
    public static int getCharacterNum(final String content) {
        if (null == content || "".equals(content)) {
            return 0;
        } else {
            return (content.length() + getChineseNum(content));
        }
    }

    /**
     * @param s
     * @return
     * @description 返回字符串里中文字或者全角字符的个数
     */
    public static int getChineseNum(String s) {

        int num = 0;
        char[] myChar = s.toCharArray();
        for (int i = 0; i < myChar.length; i++) {
            if ((char) (byte) myChar[i] != myChar[i]) {
                num++;
            }
        }
        return num;
    }

    public static InputStream readURLInputStream(String URL) throws Exception {
        java.net.URL sourceUrl;
        URLConnection conn;
        sourceUrl = new URL(URL);
        conn = sourceUrl.openConnection();
        conn.setConnectTimeout(7000);
        return conn.getInputStream();
    }

    /**
     * 获取网络文件长度
     *
     * @param downloadUrl
     * @return
     * @throws IOException
     */
    @TargetApi(9)
    public static int getNetFileLength(String downloadUrl) throws IOException {

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        int fileSize = 0;

        URL url = new URL(downloadUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();// 建立连接
        conn.setConnectTimeout(6 * 1000);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept-Language", "zh-CN");
        conn.setRequestProperty("Referer", downloadUrl);
        conn.setRequestProperty("Charset", "UTF-8");
        conn.setRequestProperty(
                "User-Agent",
                "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
        conn.connect();
        if (conn.getResponseCode() == 200) {
            fileSize = conn.getContentLength();
        }

        return fileSize;
    }

    public static final int LOCAL_FILE_VIDEO = 4;

    public static final int FILE_STREAM_VIDEO = 5;

    public static final int LIVE_STREAM_VIDEO = 6;

    public static Integer getMediaType(String videoPath) {
        if (videoPath.contains("/playlist")) {
            return SystemTools.LIVE_STREAM_VIDEO;
        } else if (videoPath.contains(".m3u8")) {
            return SystemTools.FILE_STREAM_VIDEO;
        } else {
            return SystemTools.LOCAL_FILE_VIDEO;
        }
    }

    /**
     * 替换
     *
     * @param str
     * @return
     */
    public static String string_filter(String str) {
        return str.replaceAll("【", "[").replaceAll("】", "]")
                .replaceAll("！", "!").replaceAll("：", ":").replaceAll("《", "<")
                .replaceAll("》", ">").replaceAll("，", ",").replaceAll("。", ".")
                .replaceAll("、", ".").replaceAll("？", "?").replaceAll("“", "'")
                .replaceAll("”", "'").replaceAll("；", ";").replaceAll("‘", "'")
                .replaceAll("’", "'").replaceAll("（", "(").replaceAll("）", ")")
                .replaceAll("｝", "}").replaceAll("｛", "{");
    }

    /**
     * 半角转换为全角
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 获取 XML dip 所占 宽度
     *
     * @param r
     * @param dipValue 修改历史： ZhuXianSong,2013-1-29-上午10:23:12,description
     */

    public static int dip2px(Resources r, float dipValue) {
        if (density == 0)
            density = r.getDisplayMetrics().density;
        return (int) (dipValue * density + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        Log.d("test", "scale :" + scale);
        return (int) (pxValue / scale + 0.5f);
    }

    public static void show_msg_looper(Context context, String message) {
        Looper.prepare();
        show_msg(context, message);
        Looper.loop();
    }

    public static void show_msg(Context context, int msg_res) {
        show_msg(context, context.getString(msg_res));
    }

    public static void show_msg(Context context, String msg) {
        if (context == null) return;
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 图片的缩放方法
     *
     * @param bgimage   ：源图片资源
     * @param newWidth  ：缩放后宽度
     * @param newHeight ：缩放后高度
     * @return
     */
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                   double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param context（DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static String DownloadFile(String path, String string_url, String file_name) {
        // 未下载完成
        int buffer_size = 1024;
        HttpURLConnection http = null;
        InputStream is = null;
        RandomAccessFile raf = null;
        try {
            File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + path);
            if (!dir.exists()) {
                dir.mkdirs();
            } else if (!dir.isDirectory()) {
                dir.delete();
                dir.mkdirs();
            }
            String filePath = dir.getAbsolutePath() + "/" + file_name;
            File f = new File(filePath);
            if (f.exists()) {
            }

            URL url = new URL(string_url);
            http = (HttpURLConnection) url.openConnection(); //
            http.setConnectTimeout(5 * 1000);
            http.setRequestMethod("GET");//
            http.setRequestProperty(
                    "Accept",
                    "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
            http.setRequestProperty("Accept-Language", "zh-CN");
            http.setRequestProperty("Referer", url.toString());
            http.setRequestProperty("Charset", "UTF-8");
            http.setRequestProperty(
                    "User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
            http.setRequestProperty("Connection", "Keep-Alive");

            is = http.getInputStream();
            byte[] buffer = new byte[buffer_size];
            int offset = 0;
            int tempLen = 0;

            raf = new RandomAccessFile(filePath, "rwd");//
            raf.seek(0);
            while ((offset = is.read(buffer, 0, buffer_size)) != -1) {
                raf.write(buffer, 0, offset);
                tempLen += offset;
                if (tempLen > buffer_size * 50) {
                    tempLen = 0;
                }
            }
            is.close();
            raf.close();
            return filePath;
        } catch (Exception e) {
            LogUtils.LOGE("AnimationUtils", e);
            return "";
        } finally {
            if (http != null)
                http.disconnect();
            if (is != null)
                is = null;
            if (raf != null)
                raf = null;
        }
    }

    public static void pauseMusic(Context context) {
        //暂停 音乐 - 程序关闭 恢复播放
//        AudioManager audiomanage = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
//        audiomanage.requestAudioFocus(new OnAudioFocusChangeListener() {
//            @Override
//            public void onAudioFocusChange(int focusChange) {
//            }
//        }, AudioManager.STREAM_RING, AudioManager.AUDIOFOCUS_GAIN);
        //暂停 音乐
        Intent i = new Intent("com.android.music.musicservicecommand");
        i.putExtra("command", "pause");
        context.sendBroadcast(i);
    }


    public static void initNetworkStateReceiver(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(network_br, filter);
        refreshNetworkConnection(context);
        // 解除监听：'
        // unregisterReceiver(network_br);// 取消监听
    }

    public static void unregisterNetworkStateReceiver(Context context) {
        context.unregisterReceiver(network_br);
    }

    private static boolean network_connection = false;

    //    public static boolean getConnectionState(){
//        return network_connection;
//    }
    public static boolean getConnectionState(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager.getActiveNetworkInfo() != null) {
            network_connection = connManager.getActiveNetworkInfo().isAvailable();
        } else {
            network_connection = false;
        }
        return network_connection;
    }

    public static boolean getConnectionWifi(Context context){
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=null;
        if ((networkInfo=connManager.getActiveNetworkInfo()) != null) {
                return ((networkInfo!=null) && (networkInfo.getType()== ConnectivityManager.TYPE_WIFI));
        }
        return false;
    }

    public static void showConnectionState(Context context) {
        if (!network_connection) {
            Toast.makeText(context, "请检查您的网络是否正常！",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private static BroadcastReceiver network_br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            refreshNetworkConnection(context);
        }
    };

    public static void refreshNetworkConnection(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager.getActiveNetworkInfo() != null) {
            network_connection = connManager.getActiveNetworkInfo()
                    .isAvailable();
            Log.d("my_log", "network_connection:" + network_connection);
        } else {
            network_connection = false;
        }
    }

    public static String getTimeFromInt(int time) {
        if (time < 0) {
            return "00:00";
        }

        int secondnd = time / 60;
        int minutesnd = time % 60;

        String f = secondnd >= 10 ? String.valueOf(secondnd) : "0" + String.valueOf(secondnd);
        String m = minutesnd >= 10 ? String.valueOf(minutesnd) : "0" + String.valueOf(minutesnd);

        return f + ":" + m;
    }

    public static String storeImageToFile(Context mContext, Bitmap bitmap, String headName) {
        if (bitmap == null) {
            return null;
        }
        File rootFile = null;
        File file = null;
        RandomAccessFile accessFile = null;
        String path = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath().toString() + "/" + "com.cnlive.spring/image";
            rootFile = new File(path);
            if (!rootFile.exists()) {
                rootFile.mkdirs();
            }
            file = new File(path + "/" + headName);
            ByteArrayOutputStream steam = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, steam);
            byte[] buffer = steam.toByteArray();

            try {
                accessFile = new RandomAccessFile(file, "rw");
                accessFile.write(buffer);
            } catch (Exception e) {
                return null;
            }

            try {
                steam.close();
                accessFile.close();
            } catch (IOException e) {
                //Note: do nothing.
            }
        } else {
            SystemTools.show_msg(mContext, "请检查您的SD卡是否安装！");
        }
        return path;
    }


    public static Bitmap getImageFromAssetsFile(Context context, String fileName) {
        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;

    }

//	public static boolean isConnectNet(Context context){
//		ConnectivityManager con=(ConnectivityManager)context.getSystemService(Activity.CONNECTIVITY_SERVICE);  
//		boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();  
//		boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();  
//		if(wifi|internet){  
//			return true;
//		}else{  
//			return false;
//		}
//	}

    /**
     * 验证手机号码
     *
     * @param mobiles
     * @return [0-9]{5,9}
     */
    public static boolean isMobileNO(String mobiles) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,2-3,5-9]))\\d{8}$");
            Matcher m = p.matcher(mobiles);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static boolean isPwd(String pwd) {  //只能是数字和字母
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("^[A-Za-z0-9]+$");
            Matcher m = p.matcher(pwd);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static boolean isNickName(String nickName) {  //只能是数字和字母
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("^[A-Za-z0-9\\u4E00-\\u9FA5]+$");
            Matcher m = p.matcher(nickName);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    private static float density = 0;

    public static int dip2px(Context context, float dipValue) {
        if (density == 0)
            density = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * density + 0.5f);
    }

    public static String formatTime(int ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;

        int hour = ms / hh;
        int minute = (ms - hour * hh) / mi;
        int second = (ms - hour * hh - minute * mi) / ss;
        int milliSecond = ms - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        if (hour > 0 && hour < 10) {
            sb.append("0" + hour + ":");
        } else if (hour >= 10) {
            sb.append(hour + ":");
        } else {
            sb.append("00:");
        }

        if (minute > 0 && minute < 10) {
            sb.append("0" + minute + ":");
        } else if (minute >= 10) {
            sb.append(minute + ":");
        } else {
            sb.append("00:");
        }

        if (second > 0 && second < 10) {
            sb.append("0" + second);
        } else if (second >= 10) {
            sb.append(second);
        } else {
            sb.append("00");
        }
        return sb.toString();
    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public static boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    public static boolean checkPwd(String pwd) {
        if (TextUtils.isEmpty(pwd)) return false;
        String strPattern = "^[0-9a-zA-Z_]{6,16}$";
        return pwd.matches(strPattern);
    }

    public static boolean checkName(String pwd) {
        String strPattern = "^[0-9a-zA-Z_]{6,15}$";

        return pwd.matches(strPattern);
    }


}
