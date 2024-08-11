//勿改
String 当前版本="0.3";
String 脚本作者="云上升";
String 更新时间="24年3月21日";
import org.json.*;
new Thread(new Runnable(){
public void run(){
loadJava(JavaPath+"/云上升/附加/云上升.java");
loadJava(JavaPath+"/云上升/附加/音乐卡片.java");
loadJava(JavaPath+"/云上升/附加/ItemCore.java");
loadJava(JavaPath+"/云上升/附加/HttpURL.java");


loadJava(JavaPath+"/云上升/系统/菜单.java");
loadJava(JavaPath+"/云上升/系统/开关设置.java");
loadJava(JavaPath+"/云上升/系统/音乐系统.java");
loadJava(JavaPath+"/云上升/系统/智能系统.java");
loadJava(JavaPath+"/云上升/系统/图片系统.java");
loadJava(JavaPath+"/云上升/系统/搜索功能.java");
}}).start();
public void onMsg(Object data){
String text=data.content;
String qun=data.talker;
String wxid=data.sendTalker;
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
if(text.equals("开关设置")){
	开关设置(qun);
	recallMsg(data.values);
}
}
if("1".equals(getString(qun,"开关",""))){菜单(data);}
}