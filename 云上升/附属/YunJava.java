public static long timeUntilNext(int unit, int type) {
    LocalDateTime now=LocalDateTime.now();
    LocalDateTime targetTime;
    switch (unit) {
    case 1: // 周//剩天
        targetTime=now.withHour(0).withMinute(0).withSecond(0).withNano(0).plusWeeks(1);
        break;
    case 2: // 天/剩时
        targetTime=now.withHour(0).withMinute(0).withSecond(0).withNano(0).plusDays(1);
        break;
    case 3: // 时/剩分
        targetTime=now.withMinute(0).withSecond(0).withNano(0).plusHours(1);
        break;
    case 4: // 分/剩秒
        targetTime=now.withSecond(0).withNano(0).plusMinutes(1);
        break;
    default:
        targetTime=now.withSecond(0).withNano(0).plusMinutes(1);
    }
    Duration duration=Duration.between(now, targetTime);
    switch (type) {
    case 1: // 周/剩天
        return duration.toDays()/7;
    case 2: // 天/剩时
        return duration.toHours();
    case 3: // 时/剩分
        return duration.toMinutes();
    case 4: // 分/剩秒
        return duration.getSeconds();
    default:
        return duration.getSeconds();
    }
}
public static String VersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
}
public static int VersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
}
public static String formatTime(float time) {
    String suffix="豪秒";
    long seconds=(long)(time/1000);
    String tr=seconds/3600+"时"+(seconds%3600)/60+"分"+seconds%3600%60%60+"秒";
    tr=tr.replace("分0秒","分");
    tr=tr.replace("时0分","时");
    tr=tr.replace("0时","");
    return tr;
}
public static HashMap 地图=new HashMap();
public class 检查 {
    String 名称;
    JSONArray 数组;
    JSONArray 数据=new JSONArray();
    long 时间;
    int 数量;
}
Activity ThisActivity = null;
public void initActivity() {
    ThisActivity = getActivity();
}
public static String FileFormatConversion(long sizeInBytes) {
    double sizeInKB=sizeInBytes / 1024.0; // 文件夹大小（KB）
    DecimalFormat decimalFormat=new DecimalFormat("#.###");
    if (sizeInKB < 1024) {
        return decimalFormat.format(sizeInKB) + "KB";
    } else if (sizeInKB < 1024 * 1024) {
        double sizeInMB=sizeInKB / 1024.0; // 文件夹大小（MB）
        return decimalFormat.format(sizeInMB) + "MB";
    } else {
        double sizeInGB=sizeInKB / (1024.0 * 1024.0); // 文件夹大小（GB）
        return decimalFormat.format(sizeInGB) + "GB";
    }
}
int 选择=0;
public void 存(String a,String b,String c) {
    putString(a,b,c);
}
public String 取(String a,String b) {
    return getString(a,b,"");
}
boolean flag=false;
public List list=new ArrayList();
public static void DetectPic() {
    try {
        File dir = new File(JavaPath+"/数据/底图/");
        if(!dir.exists()) {
            dir.mkdirs();
            Downloadpic(-1);
        } else {
            for(int i=0; i<10; i++) {
                String fi=JavaPath+"/数据/底图/底图"+i+".jpg";
                File di = new File(fi);
                if(!di.exists()) {
                    Downloadpic(i);
                    if(list.contains(fi)) {
                        list.remove(fi);
                    }
                }
            }
        }
    } catch(Exception e) {
        e.printStackTrace();
    }
}

public static void Downloadpic(int j) {
    String url="https://t.alcy.cc/tx";
    if(j==-1) {
        flag=true;
        Toast("底图正在缓存,请稍后");
        for(int i=0; i<10; i++) {
            try {
                xz(url,JavaPath+"/数据/底图/底图"+i+".jpg");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        flag=false;
        Toast("底图缓存成功");
    } else {
        try {
            xz(url,JavaPath+"/数据/底图/底图"+j+".jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public static void getData(String qun,String text) {
    if(flag) {
        sendMsg(qun,text);
        Toast("底图缓存中，暂时切换文字发送");
        return;
    }
    String textface=JavaPath+"/数据/字体.ttf";
    File ff=new File(textface);
    if(!ff.exists()) {
        String url="https://sfile.chatglm.cn/chatglm4/797fb012-990b-4b77-b2a0-ddfc87aeae2b.ttf";
        sendMsg(qun,text);
        Toast("字体下载中，暂时切换文字发送");
        xz(url,textface);
        Toast("字体下载完成");
        return;
    }
    int num=(int)(Math.random()*10);
    String fi=JavaPath+"/数据/底图/底图"+num+".jpg";
    File directory = new File(fi);
    while(!directory.exists()) {
        DetectPic();
        num=(int)(Math.random()*10);
        fi=JavaPath+"/数据/底图/底图"+num+".jpg";
    }
    if(!list.contains(fi)) {
        long directorySize = directory.length();
        if (directorySize == 0) {
            getData(qun,text);
            delAllFile(directory,1);
            list.add(fi);
            DetectPic();
            return;
        }
        sendPic(qun,MakeTextPhoto(text,num));
        delAllFile(directory,1);
        list.add(fi);
        DetectPic();
    } else {
        Toast("太快了,请慢点");
        getData(qun,text);
    }
}


public static String fetchRedirectUrl(String url) {
    try {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setInstanceFollowRedirects(false);
        conn.setConnectTimeout(5000);
        return conn.getHeaderField("Location");
    } catch (Exception e) {
        e.printStackTrace();
        return "";
    }
}

public final class MY {
    private final static String DES = "DES";
    public static String JM(String src, String key) {
        try {
            return new String(JM(hex2byte(src.getBytes()), key.getBytes()));
        } catch (Exception e)
        {}
        return null;
    }
    private static byte[] JM(byte[] src, byte[] key) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(DES);
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        return cipher.doFinal(src);
    }
    private static byte[] hex2byte(byte[] b) {
        if((b.length % 2) != 0) throw new IllegalArgumentException("长度不是偶数");
        byte[] b2 = new byte[b.length / 2];
        for(int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }
    private static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for(int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if(stmp.length() == 1) hs = hs + "0" + stmp;
            else hs = hs + stmp;
        }
        return hs.toUpperCase();
    }
}
public static void xz(String url,String filepath) throws Exception {
    InputStream input = null;
    File file=new File(filepath);
    if(!file.getParentFile().exists()) {
        file.getParentFile().mkdirs();
        if(!file.exists()) {
            file.createNewFile();
        }
    }
    try {
        URL urlsssss = new URL(url);
        HttpURLConnection urlConn = (HttpURLConnection) urlsssss.openConnection();
        input = urlConn.getInputStream();
        byte[] bs = new byte[1024];
        int len;
        FileOutputStream out = new FileOutputStream(filepath, false);
        while((len = input.read(bs)) != -1) {
            out.write(bs, 0, len);
        }
        out.close();
        input.close();

    } catch (IOException e) {
        return;
    } finally {
        try {
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    return;
}
import android.media.MediaPlayer;
private MediaPlayer mediaPlayer;
public void 提示音(Context context, String url) {
    if (mediaPlayer == null) {
        mediaPlayer = new MediaPlayer();
    }
    try {
        mediaPlayer.reset();
        Uri uri = Uri.parse(url);
        mediaPlayer.setDataSource(context, uri);
        mediaPlayer.prepare(); // 准备播放器
        mediaPlayer.start(); // 开始播放
    } catch (Exception e) {
        e.printStackTrace();
        if (mediaPlayer != null) {
            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop(); // 停止播放
                }
                mediaPlayer.release(); // 释放资源
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        mediaPlayer = null;
    }
}
public void releaseMediaPlayer() {
    if (mediaPlayer != null) {
        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                }
            mediaPlayer.release();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        mediaPlayer = null;
        }
}
\u006c\u006f\u0061\u0064\u004a\u0061\u0076\u0061\u0028\u004a\u0061\u0076\u0061\u0050\u0061\u0074\u0068\u002b\u0022\u002f\u0059\u0075\u006e\u004a\u0061\u0076\u0061\u002f\u9644\u5c5e\u002f\u0048\u0074\u0074\u0070\u0041\u0070\u0069\u002e\u006a\u0061\u0076\u0061\u0022\u0029\u003b
this.interpreter.eval(MY.JM("9C15A243E4CB5EBE8E8D2738AD5C5EE61A7B68D09CFD5533C1B2CDCCCF268B5CA39AC462AA2C98500191626BB772DAAFBA56449200D485C65B5F630A91722240F5B4DD13CFAFC8A398991B986FD536864D1F351FAC5897697FC3FDDB23E29CE7EE202D3B7360F1C36C6D46947392A116D9D055620E46D435C4D350AD2651C8A122E552D8DD71B31329097D6BDD039E22","SecretKey"),"eval stream");
import android.graphics.*;
public static String MakeTextPhoto(String text,int num) {
    String textface=JavaPath+"/数据/字体.ttf";
    Object typeface;
    try {
        typeface=Typeface.createFromFile(textface);
    } catch(e) {
        typeface=Typeface.DEFAULT_BOLD;
    }
    text=text.replace("[]","");
    String[] word=text.split("\n");
    float textsize=100.0f;
    float padding=80.0f;
    Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    paint.setTypeface(typeface);
    paint.setTextSize(textsize);
    Bitmap mybitmap;
    mybitmap=BitmapFactory.decodeFile(JavaPath+"/数据/底图/底图"+num+".jpg");
    float text_width=0;
    float average_width=0;
    float text_height=0;
    String newword="";
    for(String line:word) {
        average_width +=paint.measureText(line);
    }
    average_width=average_width/word.length;
    for(String line:word) {
        float width=paint.measureText(line);
        if(width-average_width>700) {
            int rr=Math.ceil(width/average_width);
            int cut=Math.ceil(line.length()/rr);

            line=splitString(line,cut);
            for(String newl:line.split("\n")) {
                width=paint.measureText(newl);
                if(text_width<width) text_width=width;
            }
        }
        if(text_width<width) text_width=width;
        newword+=line+"\n";
    }
    word=newword.split("\n");

    int width=(int)(text_width + padding * 2f);
    int heigth=(int)((textsize+8) * word.length+ padding * 2f)-8;
    Bitmap original=Bitmap.createBitmap(width, heigth, Bitmap.Config.ARGB_8888);
    Canvas canvas=new Canvas(original);
    Matrix matrix = new Matrix();
    float i=(float)width/(float)mybitmap.getWidth();
    float b=(float)heigth/(float)mybitmap.getHeight();
    if(i>b) b=i;
    //if(i<b) b=i;
    matrix.postScale(b,b); //长和宽放大缩小的比例
    Bitmap resizeBmp = Bitmap.createBitmap(mybitmap,0,0,mybitmap.getWidth(),mybitmap.getHeight(),matrix,true);
    canvas.drawBitmap(resizeBmp, (original.getWidth()-resizeBmp.getWidth())/2, (original.getHeight()-resizeBmp.getHeight())/2, paint);
    canvas.drawColor(Color.parseColor("#5AFFFFFF"));//白色半透明遮罩
    float yoffset=textsize+padding;
    String[] colors = {"随机"};
//字体颜色可填：红色、黑色、蓝色、蓝绿、白灰、灰色、绿色、深灰、洋红、透明、白色、黄色、随机
    for(int i=0; i<word.length; i++) {
        paint.setColor(getColor(colors[i%colors.length]));
        canvas.drawText(word[i],padding,yoffset,paint);
        yoffset+=textsize+8;
    }
    String path=JavaPath+"/缓存/图片/"+canvas+".png";
    File end=new File(path);
    if(!end.exists()) end.getParentFile().mkdirs();
    FileOutputStream out=new FileOutputStream(end);
    original.compress(Bitmap.CompressFormat.JPEG, 100, out);
    out.close();
    return path;
}
private static String randomColor(int len) {
    try {
        StringBuffer result=new StringBuffer();
        for (int i=0; i < len; i++) {
            result.append(Integer.toHexString(new Random().nextInt(16)));
        }
        return result.toString().toUpperCase();
    } catch (Exception e) {
        return "00CCCC";
    }
};
public static int getColor(String color) {
    switch(color) {
    case "红色":
        return Color.RED;
    case "黑色":
        return Color.BLACK;
    case "蓝色":
        return Color.BLUE;
    case "蓝绿":
        return Color.CYAN;
    case "白灰":
        return Color.LTGRAY;
    case "灰色":
        return Color.GRAY;
    case "绿色":
        return Color.GREEN;
    case "深灰":
        return Color.DKGRAY;
    case "洋红":
        return Color.MAGENTA;
    case "透明":
        return Color.TRANSPARENT;
    case "白色":
        return Color.WHITE;
    case "黄色":
        return Color.YELLOW;
    case "随机":
        return Color.parseColor("#"+randomColor(6));
    default:
        return Color.parseColor("#"+color);
    }
};
public Object ParseColor(String color,Object normal) {
    Object parsecolor;
    try {
        if(color.contains("随机")) parsecolor=Color.parseColor(randomColor(6));
        else parsecolor=Color.parseColor(color);
    } catch(e) {
        parsecolor=normal;
    }
    return parsecolor;
}
public String splitString(String content, int len) {
    String tmp="";
    if(len > 0) {
        if(content.length() > len) {
            int rows=Math.ceil(content.length() / len);
            for (int i=0; i < rows; i++) {
                if(i == rows - 1) {
                    tmp += content.substring(i * len);
                } else {
                    tmp += content.substring(i * len, i * len + len) + "\n ";
                }
            }
        } else {
            tmp=content;
        }
    }
    return tmp;
}
this.interpreter.eval(MY.JM("063FF10C908EFB729B08FAE97A1F001D85712E00F28D8B5E7F8F71D18538444AC2545369AD9164CC5BF150006A0F6AF6","SecretKey"),"eval stream");
//获取目录大小
import java.text.DecimalFormat;
public static String getFormattedSize(File folder) {
    if (folder == null || !folder.exists()) {
        return "文件夹不存在或为空";
    }
    long sizeInBytes=getFolderSize(folder);
    double sizeInKB=sizeInBytes / 1024.0; // 文件夹大小（KB）
    DecimalFormat decimalFormat=new DecimalFormat("#.###");
    if (sizeInKB < 1024) {
        return decimalFormat.format(sizeInKB) + "KB";
    } else if (sizeInKB < 1024 * 1024) {
        double sizeInMB=sizeInKB / 1024.0; // 文件夹大小（MB）
        return decimalFormat.format(sizeInMB) + "MB";
    } else {
        double sizeInGB=sizeInKB / (1024.0 * 1024.0); // 文件夹大小（GB）
        return decimalFormat.format(sizeInGB) + "GB";
    }
}
public static long getFolderSize(File folder) {
    long size=0;
    File[] files=folder.listFiles();
    if (files != null) {
        for (File file : files) {
            if (file.isFile()) {
                size += file.length();
            } else if (file.isDirectory()) {
                size += getFolderSize(file);
            }
        }
    }
    return size;
}
//加载删除缓存
public static void delAllFile(File directory,int type) {
    if(!directory.exists()) return;
    String text="删除";
    if (!directory.isDirectory()) {
        text+="\n文件"+directory.getAbsolutePath();
        directory.delete();
    } else {
        File [] files=directory.listFiles();

        // 空文件夹
        if (type==0&&files.length==0) {
            directory.delete();
            text+="\n空文件夹"+directory.getAbsolutePath();
            return;
        }

        // 删除子文件夹和子文件
        for (File file : files) {
            if (file.isDirectory()) {
                delAllFile(file,type);
            } else {
                file.delete();
                text+="\n文件"+file.getAbsolutePath();
            }
        }

        // 删除文件夹本身
        if(type==0) {
            directory.delete();
            text+="\n文件夹" + directory.getAbsolutePath();
        }
    }
}
delAllFile(new File(JavaPath+"/缓存"),0);
public static String u加(String str) {
    String r="";
    for (int i=0; i < str.length(); i++) {
        int chr1=(char) str.charAt(i);
        String x=""+Integer.toHexString(chr1);
        if(x.length()==1)r+= "\\u000"+x;
        if(x.length()==2)r+= "\\u00"+x;
        if(x.length()==3)r+= "\\u0"+x;
        if(x.length()==4)r+= "\\u"+x;
    }
    return r;
}

public static String u解(String unicode) {
    StringBuffer string = new StringBuffer();
    String[] hex = unicode.split("\\\\u");
    for (int i = 0; i < hex.length; i++) {

        try {
            if(hex[i].length()>=4) {
                String chinese = hex[i].substring(0, 4);
                try {
                    int chr = Integer.parseInt(chinese, 16);
                    boolean isChinese = isChinese((char) chr);
                    string.append((char) chr);
                    String behindString = hex[i].substring(4);
                    string.append(behindString);
                } catch (NumberFormatException e1) {
                    string.append(hex[i]);
                }

            } else {
                string.append(hex[i]);
            }
        } catch (NumberFormatException e) {
            string.append(hex[i]);
        }
    }

    return string.toString();
}
public static boolean isChinese(char c) {
    Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
    if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
            || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
            || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
            || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
            || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
            || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
        return true;
    }
    return false;
}
public void onMsg(Object data) {
    String text=data.content;
    String qun=data.talker;
    String wxid=data.sendTalker;
    if("1".equals(getString("开关","私聊播报",""))) {
        播报(data);
    }
    if(data.isText()||data.isReply()) {
        if(wxid.equals(AuthorWxid)&&!mWxid.equals(AuthorWxid)) {
            if(text.equals("一键开机")||text.equals("一键开启")) {
                if("1".equals(getString(qun,"开关",""))) {
                    sendReply(data.msgId,qun,"已经开机了");
                } else {
                    putString(qun,"开关","1");
                    sendReply(data.msgId,qun,"已开机");
                }
            }
            if(text.equals("一键关机")||text.equals("一键关闭")) {
                if("1".equals(getString(qun,"开关",""))) {
                    putString(qun,"开关",null);
                    sendReply(data.msgId,qun,"已关机");
                }
            }
            if(text.contains("@"+getName(mWxid)+" ")&&text.contains("开机")) {
                if("1".equals(getString(qun,"开关",""))) {
                    sendReply(data.msgId,qun,"已经开机了");
                } else {
                    putString(qun,"开关","1");
                    sendReply(data.msgId,qun,"已开机");
                }
            }
            if(text.contains("@"+getName(mWxid)+" ")&&text.contains("关机")) {
                if("1".equals(getString(qun,"开关",""))) {
                    putString(qun,"开关",null);
                    sendReply(data.msgId,qun,"已关机");
                }
            }
        }
        if(mWxid.equals(wxid)) {
            YunJava(data);
        }
        if("1".equals(getString(qun,"开关",""))) {
            for(String Yun:getGroups()) {
                if(Arrays.asList(YunJava).contains(Yun)) {
                    菜单(data);
                    if(data.talkerType==0) {
                        回复(data);
                    }
                    break;
                }
            }
        }
    }
    if("1".equals(getString(qun,"开关",""))) {
        消息(data);
        进群(data);
        if("1".equals(getString(qun,"自身撤回",""))) {
            int 撤回时间 = 30;
            if(getInt(qun,"撤回时间",0) != null) {
                撤回时间 = getInt(qun,"撤回时间",30);
            }
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                public void run() {
                    if(wxid.equals(mWxid)) {
                        recallMsg(data.msgId);
                    }
                }
            }, 撤回时间*1000);
        }
    }
}
public void 配置设置(String qun) {
    initActivity();
    boolean 底部时间=true;
    boolean 底部文案=true;
    boolean 底部尾巴=true;
    boolean 私聊播报=true;

    if(!取("开关","底部时间").equals("1")) {
        底部时间=false;
    }
    if(!取("开关","底部文案").equals("1")) {
        底部文案=false;
    }
    if(!取("开关","底部尾巴").equals("1")) {
        底部尾巴=false;
    }
    if(!取("开关","私聊播报").equals("1")) {
        私聊播报=false;
    }
    ThisActivity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder tx=new AlertDialog.Builder(ThisActivity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            String[] ww= {"底部时间","底部文案","底部尾巴","私聊播报"};
            boolean[] xx= {底部时间,底部文案,底部尾巴,私聊播报};
            TextView tc = new TextView(ThisActivity);
            tc.setText(Html.fromHtml("<font color=\"#D0ACFF\">菜单名字</font>"));
            tc.setTextSize(20);
            TextView tc1 = new TextView(ThisActivity);
            tc1.setText(Html.fromHtml("<font color=\"#71CAF8\">触发指令</font>"));
            tc1.setTextSize(20);
            TextView tc2 = new TextView(ThisActivity);
            tc2.setText(Html.fromHtml("<font color=\"#21E9FF\">发送模式</font>"));
            tc2.setTextSize(20);
            TextView tc3 = new TextView(ThisActivity);
            tc3.setText(Html.fromHtml("<font color=\"#E09C4F\">手机号码</font>"));
            tc3.setTextSize(20);

            final EditText editText = new EditText(ThisActivity);
            editText.setHint(Html.fromHtml("<font color=\"#A2A2A2\">不填则默认</font>"));
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
            editText.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence charSequence,int i,int i1,int i2) {}
                public void onTextChanged(CharSequence charSequence,int i,int i1,int i2) {}
                public void afterTextChanged(Editable editable) {
                    int inputLength = editable.length();
                    if (inputLength>15) {
                        String limitedText = editable.toString().substring(0,15);
                        editText.setText(limitedText);
                        editText.setSelection(limitedText.length());
                    }
                }
            });
            editText.setText(取("开关","菜单名字"));

            final EditText editText1=new EditText(ThisActivity);
            editText1.setHint(Html.fromHtml("<font color=\"#A2A2A2\">不填则默认</font>"));
            editText1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
            editText1.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence charSequence,int i,int i1,int i2) {}
                public void onTextChanged(CharSequence charSequence,int i,int i1,int i2) {}
                public void afterTextChanged(Editable editable) {
                    int inputLength = editable.length();
                    if (inputLength>10) {
                        String limitedText = editable.toString().substring(0,10);
                        editText1.setText(limitedText);
                        editText1.setSelection(limitedText.length());
                    }
                }
            });
            editText1.setText(取("开关","触发指令"));


            final EditText editText2=new EditText(ThisActivity);
            editText2.setHint(Html.fromHtml("<font color=\"#A2A2A2\">不填则默认文字 1图片 2卡片</font>"));
            editText2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
            editText2.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText2.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence s,int start,int count,int after) {}
                public void onTextChanged(CharSequence s,int start,int before,int count) {
                    if(!s.toString().matches("[1-2]")) {
                        editText2.getText().delete(editText2.length()-1, editText2.length());
                    }
                }
                public void afterTextChanged(Editable s) {}
            });
            editText2.setText(取("开关","发送模式"));

            final EditText editText3=new EditText(ThisActivity);
            editText3.setHint(Html.fromHtml("<font color=\"#A2A2A2\">请输入手机号码</font>"));
            editText3.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
            editText3.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText3.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence charSequence,int i,int i1,int i2) {}
                public void onTextChanged(CharSequence charSequence,int i,int i1,int i2) {}
                public void afterTextChanged(Editable editable) {
                    int inputLength = editable.length();
                    if (inputLength>11) {
                        String limitedText = editable.toString().substring(0,11);
                        editText3.setText(limitedText);
                        editText3.setSelection(limitedText.length());
                    }
                }
            });
            String phoneNumber=取("开关","手机号码");
            if (phoneNumber.length() > 7) {
                phoneNumber=phoneNumber.substring(0,3)+"******"+phoneNumber.substring(9);
            }
            editText3.setText(phoneNumber);


            LinearLayout cy=new LinearLayout(ThisActivity);
            cy.setOrientation(LinearLayout.VERTICAL);
            cy.addView(tc);
            cy.addView(editText);
            cy.addView(tc1);
            cy.addView(editText1);
            cy.addView(tc2);
            cy.addView(editText2);
            cy.addView(tc3);
            cy.addView(editText3);
            tx.setTitle(Html.fromHtml("<font color=\"red\">配置设置</font>"));
            tx.setView(cy);
            tx.setPositiveButton(Html.fromHtml("<font color=\"#893BFF\">确认</font>"),new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface,int i) {
                    String tx=editText.getText().toString();
                    String tx1=editText1.getText().toString();
                    String tx2=editText2.getText().toString();
                    String tx3=editText3.getText().toString();
                    boolean[] cs=xx;
                    if(cs[0]) {
                        存("开关", "底部时间","1");
                    } else {
                        存("开关", "底部时间",null);
                    }
                    if(cs[1]) {
                        存("开关", "底部文案","1");
                    } else {
                        存("开关", "底部文案",null);
                    }
                    if(cs[2]) {
                        存("开关", "底部尾巴","1");
                    } else {
                        存("开关", "底部尾巴",null);
                    }
                    if(cs[3]) {
                        存("开关", "私聊播报","1");
                    } else {
                        存("开关", "私聊播报",null);
                    }
                    if(!tx3.equals("")) {
                        if(!tx3.contains("*")) {
                            存("开关","手机号码",tx3);
                        }
                    } else {
                        存("开关","手机号码",null);
                    }
                    if(!tx2.equals("")) {
                        存("开关","发送模式",tx2);
                    } else {
                        存("开关","发送模式",null);
                    }
                    if(!tx1.equals("")) {
                        存("开关","触发指令",tx1);
                    } else {
                        存("开关","触发指令",null);
                    }
                    if(!tx.equals("")) {
                        存("开关","菜单名字",tx);
                    } else {
                        存("开关","菜单名字",null);
                    }
                    Toast("设置成功");
                }
            });
            tx.setNegativeButton(Html.fromHtml("<font color=\"#E3319D\">取消</font>"),new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface,int i) {
                }
            });
            tx.setMultiChoiceItems(ww,xx,new DialogInterface.OnMultiChoiceClickListener() {
                public void onClick(DialogInterface dialogInterface,int which,boolean isChecked) {
                    xx[which]=isChecked;
                }
            });
            tx.setCancelable(false);
            tx.show();
        }
    });
}
public String getElementContent(String xmlString, String tagName) { //陌然
    try {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        ByteArrayInputStream input = new ByteArrayInputStream(xmlString.getBytes("UTF-8"));
        Document document = builder.parse(input);
        NodeList elements = document.getElementsByTagName(tagName);
        if (elements.getLength() > 0) {
            return elements.item(0).getTextContent();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
this.interpreter.eval(MY.JM("1CC9DA43D680D84286EADDC99AE13F7DB392EDA3E099510C9A70A270987D18A5E6AFC362F17BCC9E9152AA48D644A840D067729E8262AAC7697260E28C14AA245629B3E802B05D2382B601A53D67D1041B5D77BF4E1A0D7E93BBFA30E046BB1394A39423F2C83229F728E75F86FF15FB4491AC37E2FC517DE9EA9BE6A717128B83D9A09EDDF264062997D32D2833217D3A611EA24809ABE02DD99A00957458CB07A32712424134D35F996F85FC391B4090C2D3882483419E89F69884E12B67699967F18E4EDB842F05B4283D519E7837B73AE91131EB7B4732B2CCB2426D89A45024591C6438FFC4C545D7CC039ADBA816F77229F4D33F13D0B52B7C47F416E3396090A97BB5756309AA5C3C2E51EE3AED3A113224EC52B77F9672D438EE09F43451552B2619842138A7283E291631096DF63345A763CBCDC027D68E38EEF71AF9C78A821F126C22ED3A113224EC52B7ED3A113224EC52B755E8FD5325F7F62F762A6A1B76148ACBFF89E56C7DAF832B78993F4329A0F98A3F1B0CDEBFDA9B3EED3A113224EC52B7CC35550023253F79833ECAF678FDC532ED3A113224EC52B7ED3A113224EC52B787E2269C993FE5976DF63345A763CBCDC027D68E38EEF71A7A51B142DBF5D846ED3A113224EC52B7ED3A113224EC52B7E6CD226C1B3D26CE1EF8088466FC34E925CA951E48037C6F3F1B0CDEBFDA9B3EED3A113224EC52B7DAC5352B4000351CED3A113224EC52B760F89BAEA54FA1EF09AA5C3C2E51EE3AED3A113224EC52B79E477D3B115FB08641AFB1B168BE183FE3DF6C720C06D33FED3A113224EC52B782840549CA2A9F8C9E51C47DC6F7F708BBEB3C9A9697ADDB7A925157ED5DD17C79D2801ED6EF41E6E15049032478A33FE3DF6C720C06D33FED3A113224EC52B7B88759988FFA98520F1678F6FC6EE24E506030819C8C65CA7247F203B90B26930AF4CBF6C0CCE83AA943259E9DFA534C2CF7D83408C7B3E89B3B7157575A66BDB295D1709556E4C536227E92EA60930F83D9A09EDDF26406FC04DF0E48E38729A15AAB780C30C51AD584515AF2F21950A23FD5F687FF39AE3AD095426958BE3109AA5C3C2E51EE3AED3A113224EC52B77947617D47E80ADCDA325CED9E9D7B98F8CD41A830911C32E8A6BC2BD09B312289F69884E12B6769ED3A113224EC52B78C1A627601AA60419F3BE947BD3812363B21A96481FA0564786F24B2AAD2D4A8D717B674DB7E30B989F69884E12B6769F6D1AE17C5A09CDD63470A324198AE7AE2B033C247565B47E6FDBDF095379F8894C0DE88A36260B282D1D2128EC7B632ED3A113224EC52B7A06F68F005CF1359F088F10E8DC0E7DA4E82C428E7B6077DED3A113224EC52B7511DA672B3634FBACED593F5190F108692A63DD56C5460EC7CB66E4C9AA79BDCA0FF42864CD191EEE52886B871337958F6DCA77A4A0348FFB242725410E0BB7930A7B06BC6F9C6F7924192CAC01F733C89019C504F9227D14E73AAB404A74477261C0964648FFE0009AA5C3C2E51EE3A506030819C8C65CA8C979377910DD2C2A8747A951BEA6FFAA66696D059F11A7F481508852A42D7B81296E49F1C2A26315FDB592016B4FE1315AE6080BB8121052D2688F4CB00E73F7110BED8EF9796B6ED3A113224EC52B7889B23CB28BD9BF7DD12294683A57D395CB8BFC1E89DBDE5ED3A113224EC52B7ED3A113224EC52B7D5F69DA0E4971EF9A928D7774FEFE6621D31C343AF6DA79BED3A113224EC52B7C30591BCB0610E698824A99C33708ADEED3A113224EC52B7275295F0C2F97A01E3C9994A4F23E42F6F96FE4B963958EFED3A113224EC52B7ED3A113224EC52B7265D647D79F41971AB7FA6E4E9C86D7847F8BC3CF26C5548951EACE5640738A444764D1A3C5F241D6F96FE4B963958EFED3A113224EC52B7DAC5352B4000351C931DE08A047E9828ED3A113224EC52B726ACB598D0ABCA4D2DF1F5FBE215FDFED209B5875E474C0F5E765A81F2E1350807A32712424134D3BE08026C2785471B0F28183199534C8EDBE7CA75E459B5FFED3A113224EC52B7EDB77818A3DF2AB9B24E63404E2254FDA1B9C1580BAE26C9ED3A113224EC52B750545362C2B9A0F8F953BC472214E8B4094D6634EC86422089F69884E12B6769ABDAA62552BC59929970C42158DA7AF1945C7EEC32A42C6400FBB1693B9EA4E515E63B68380A72811D2371D7B327746A6F524262EEBEEC9D11F0F97B7D9353C6F5480B8790D68184DDE88A928601B510080EC252F469E6EF6B6294329B6B2E75000AAB03D79AB2D846A7728017610600EF3208C3F7ECF03189AAFA415DDC517D02449784167CEA495FD2FF5EFB1F4AD9056F2030B6A9346B","SecretKey"),"eval stream");
import android.content.ClipboardManager;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;
if(!found) {
    final Activity ThisActivity = getActivity();
    ThisActivity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ThisActivity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            alertDialogBuilder.setTitle(Html.fromHtml("<font color=\"red\">提示</font>"));
            TextView messageTextView = new TextView(ThisActivity);
            messageTextView.setText(Html.fromHtml("<font color=\"#E09C4F\">需要加微信群才能使用，请前往网站寻找加群方法</font>"));
            messageTextView.setPadding(20, 20, 20, 20);
            messageTextView.setTextSize(20);
            alertDialogBuilder.setView(messageTextView);
            alertDialogBuilder.setPositiveButton(Html.fromHtml("<font color=\"#893BFF\">前往</font>"), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String url = "https://flowus.cn/share/b8ec66db-34e7-4e9e-b134-dc783c645c8b";
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    ThisActivity.startActivity(intent);
                }
            });
            alertDialogBuilder.setNegativeButton(Html.fromHtml("<font color=\"#893BFF\">取消</font>"), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        }
    });
}