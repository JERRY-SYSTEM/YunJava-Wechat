public static String[] emojilist={"☘️","🎈","🌼","🌸","🍀","🪐","☀️","🌕","⚾","🏀","🥎","🏆","🟥","💥","🔔","🔅","🔆","💫","⭐","🪙","🃏","🔮","🎀","👑","🎪","🎄","🍒","🍧","🍇","🎉","🍁","🍑","🍊","🍓","🍅","🍥","🏵","🎊","🎁","🎃","🍏","🍎","🍐","🍊","🍋","🍌","🍉","🍆","⚽️"};
public static void 开关(Object data){
String text=data.content;
String qun=data.talker;
String wxid=data.sendTalker;
if(text.equals("切换文字发送")){
putString(qun,"发送模式",null);
sendMsg(qun,"已切换为文字发送");
}
if(text.equals("切换图片发送")){
putString(qun, "发送模式", "1");
sendMsg(qun,"已切换为图片发送");
}
if(text.equals("开启音乐系统")){
if("1".equals(getString(qun,"音乐系统",""))){
sendMsg(qun,"已经开了");
return;}
putString(qun,"音乐系统","1");
sendMsg(qun,"已开启");
}
if(text.equals("关闭音乐系统")){
if(!"1".equals(getString(qun,"音乐系统","")))
{
sendMsg(qun,"还没开");
return;}
putString(qun, "音乐系统", null);
sendMsg(qun,"已关闭");
}
if(text.equals("开启图片系统")){
if("1".equals(getString(qun,"图片系统",""))){
sendMsg(qun,"已经开了");
return;}
putString(qun,"图片系统","1");
sendMsg(qun,"已开启");
}
if(text.equals("关闭图片系统")){
if(!"1".equals(getString(qun,"图片系统",""))){
sendMsg(qun,"还没开");
return;}
putString(qun, "图片系统", null);
sendMsg(qun,"已关闭");
}
if(text.equals("开启智能系统")){
if("1".equals(getString(qun,"智能系统",""))){
sendMsg(qun,"已经开了");
return;}
putString(qun,"智能系统","1");
sendMsg(qun,"已开启");
}
if(text.equals("关闭智能系统")){
if(!"1".equals(getString(qun,"智能系统",""))){
sendMsg(qun,"还没开");
return;}
putString(qun, "智能系统", null);
sendMsg(qun,"已关闭");
}
if(text.equals("开启搜索功能")){
if("1".equals(getString(qun,"搜索功能",""))){
sendMsg(qun,"已经开了");
return;}
putString(qun,"搜索功能","1");
sendMsg(qun,"已开启");
}
if(text.equals("关闭搜索功能")){
if(!"1".equals(getString(qun,"搜索功能",""))){
sendMsg(qun,"还没开");
return;}
putString(qun, "搜索功能", null);
sendMsg(qun,"已关闭");
}
if(text.equals("开启自身撤回")){
if("1".equals(getString(qun,"自身撤回",""))){
sendMsg(qun,"已经开了");
return;}
putString(qun,"自身撤回","1");
sendMsg(qun,"已开启");
}
if(text.equals("关闭自身撤回")){
if(!"1".equals(getString(qun,"自身撤回",""))){
sendMsg(qun,"还没开");
return;}
putString(qun, "自身撤回", null);
sendMsg(qun,"已关闭");
}
if(text.equals("开启底部文案")){
if("1".equals(getString("开关","底部文案",""))){
sendMsg(qun,"已经开了");
return;}
putString("开关","底部文案","1");
sendMsg(qun,"已开启");
}
if(text.equals("关闭底部文案")){
if(!"1".equals(getString("开关","底部文案",""))){
sendMsg(qun,"还没开");
return;}
putString("开关", "底部文案", null);
sendMsg(qun,"已关闭");
}
if(text.equals("开启底部时间")){
if("1".equals(getString("开关","底部时间",""))){
sendMsg(qun,"已经开了");
return;}
putString("开关","底部时间","1");
sendMsg(qun,"已开启");
}
if(text.equals("关闭底部时间")){
if(!"1".equals(getString("开关","底部时间",""))){
sendMsg(qun,"还没开");
return;}
putString("开关", "底部时间", null);
sendMsg(qun,"已关闭");
}
if(text.equals("开启底部尾巴")){
if("1".equals(getString("开关","底部尾巴",""))){
sendMsg(qun,"已经开了");
return;}
putString("开关","底部尾巴","1");
sendMsg(qun,"已开启");
}
if(text.equals("关闭底部尾巴")){
if(!"1".equals(getString("开关","底部尾巴",""))){
sendMsg(qun,"还没开");
return;}
putString("开关", "底部尾巴", null);
sendMsg(qun,"已关闭",2);
}
if(text.startsWith("设置底部内容")){
text=text.substring(6);
if(text.equals("")){
sendMsg(qun,"请输入内容");
return;}
putString("开关","底部内容",text);
sendMsg(qun,"设置成功");
}
if(text.startsWith("设置撤回时间")){
text=text.substring(6);
if(text.matches("^(?:120|[1-9][0-9]{0,2})$")){
putInt(qun,"撤回时间",Integer.parseInt(text));
sendMsg(qun,"已设置为"+text+"秒");
}}
}
public static String 文案(File f){
	String result=null;
	Random rand=new Random();
	int n=0;
for(Scanner sc=new Scanner(f);sc.hasNext();){
	++n;
	String line=sc.nextLine();
	//循环输出文件每行内容
	if(rand.nextInt(n) == 0)
	result=line;
	}
	return result;
}
import java.text.SimpleDateFormat;
public static void sendm(String qun,String text){
String 菜单名字=" ────云上升────\n";
String e=emojilist[new Random().nextInt(emojilist.length)];
text=text.replace("◇",e);
if("1".equals(getString("开关","底部文案",""))){
File f=new File(JavaPath+"/云上升/文案.txt");
text=text+"\n ───────────\n "+文案(f);
}
if("1".equals(getString("开关","底部时间",""))){
SimpleDateFormat df=new SimpleDateFormat("yy年MM月dd日HH:mm:ss");
if("1".equals(getString(qun,"发送模式",""))){
df=new SimpleDateFormat("yy-MM-dd HH:mm:ss");
}
Calendar calendar=Calendar.getInstance();
String time=df.format(calendar.getTime());
text=text+"\n ───────────\n "+time;
}
if("1".equals(getString("开关","底部尾巴",""))){
String 尾巴="这是底部尾巴";
if(!"".equals(getString("开关","底部内容",""))){
尾巴=getString("开关","底部内容","");}
text=text+"\n ───────────\n "+尾巴;
}
if("1".equals(getString(qun,"发送模式","")))
try{
getData(qun,菜单名字+text);
} catch (Exception e) {
Toast("错误,已自动切换为文字发送");
putString(qun,"发送模式",null);
} else{
sendMsg(qun,菜单名字+text);
}
}

import android.app.AlertDialog;
import android.app.Activity;
import android.text.*;
import android.widget.*;
import android.text.Html;
import android.view.Gravity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ProgressDialog;
import android.content.DialogInterface;

public void 开关设置(String qun)
{
int i=0;
boolean[] boolArr;
String[] kname;
String[] ww;

boolArr=new boolean[8];
kname=new String[]{"开关","菜单屏蔽","菜单限制","音乐系统","图片系统","搜索功能","智能系统","自身撤回"};
ww=new String[]{"开/关机","菜单屏蔽","菜单限制","音乐系统","图片系统","搜索功能","智能系统","自身撤回"};

for(String tex: kname)
{
if(!取(qun,tex).equals("1"))
{
boolArr[i]=false;
}else{
boolArr[i]=true;
}
i++;
}
Activity act=getActivity();
act.runOnUiThread(new Runnable(){
public void run(){
AlertDialog.Builder dialog = new AlertDialog.Builder(act,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
dialog.setTitle(Html.fromHtml("<font color=\"red\">开关设置</font>"));
dialog.setMultiChoiceItems(ww, boolArr, new DialogInterface.OnMultiChoiceClickListener(){
public void onClick(DialogInterface dialog, int which, boolean isChecked){
boolArr[which]=isChecked;
}});
dialog.setPositiveButton(Html.fromHtml("<font color=\"#893BFF\">确认</font>"),new DialogInterface.OnClickListener(){
public void onClick(DialogInterface dialog, int which){
boolean[] cs=boolArr;
i=0;
for(String tex:kname)
{
if(cs[i]==false)
{
存(qun,tex,null);
}else{
存(qun,tex,"1");
}
i++;
}
Toast("设置成功");
dialog.dismiss();
}});
dialog.setNegativeButton(Html.fromHtml("<font color=\"#E3319D\">取消</font>"),new DialogInterface.OnClickListener(){
public void onClick(DialogInterface dialog, int which){
//Toast("a");
dialog.dismiss();
}});
dialog.setCancelable(false);
dialog.show();
}
});
}