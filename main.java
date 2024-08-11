import org.json.*;
//勿改
String 当前版本="0.6";
String 脚本作者="云上升";
String 更新时间="24年3月27日";
//附加
new Thread(new Runnable(){
public void run(){
loadJava(JavaPath+"/云上升/附加/云上升.java");
loadJava(JavaPath+"/云上升/附加/微信卡片.java");
loadJava(JavaPath+"/云上升/附加/ItemCore.java");
loadJava(JavaPath+"/云上升/附加/HttpURL.java");
}}).start();
//系统
new Thread(new Runnable(){
public void run(){
loadJava(JavaPath+"/云上升/系统/菜单.java");
loadJava(JavaPath+"/云上升/系统/开关设置.java");
loadJava(JavaPath+"/云上升/系统/音乐系统.java");
loadJava(JavaPath+"/云上升/系统/智能系统.java");
loadJava(JavaPath+"/云上升/系统/图片系统.java");
loadJava(JavaPath+"/云上升/系统/搜索功能.java");
loadJava(JavaPath+"/云上升/系统/视频系统.java");
loadJava(JavaPath+"/云上升/系统/词条系统.java");
loadJava(JavaPath+"/云上升/系统/整点报时.java");
loadJava(JavaPath+"/云上升/系统/艾特回复.java");
loadJava(JavaPath+"/云上升/系统/进群欢迎.java");
}}).start();