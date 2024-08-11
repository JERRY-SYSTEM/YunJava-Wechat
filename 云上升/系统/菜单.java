import java.util.regex.Pattern;
import java.util.regex.Matcher;
import android.content.ContentValues;

public void 菜单(Object data){
String text=data.content;
String qun=data.talker;
String wxid=data.sendTalker;
if(mWxid.equals(wxid)){开关(data);}
String 菜单限制="";
if("1".equals(取(qun,"菜单限制"))){菜单限制=mWxid;}else{菜单限制=data.sendTalker;}
if(菜单限制.equals(wxid)){
if("1".equals(getString(qun,"音乐系统",""))){音乐(data);}
if("1".equals(getString(qun,"图片系统",""))){图片(data);}
if("1".equals(getString(qun,"智能系统",""))){智能(data);}
if("1".equals(getString(qun,"搜索功能",""))){搜索(data);}
if(!"1".equals(取(qun,"菜单屏蔽"))){
if(text.equals("菜单")){
String c="◇音乐系统◇智能系统◇\n"
+"◇配置设置◇图片系统◇\n"
+"◇开关系统◇底部样式◇\n"
+"◇搜索功能◇开关设置◇\n"
+"◇版本信息◇自身撤回◇\n"
+"◇敬请期待◇敬请期待◇";
sendm(qun,c);}
if(text.equals("自身撤回")){
int 撤回时间=30;
String 自身撤回="";
if("1".equals(getString(qun,"自身撤回",""))){自身撤回="开";}else{自身撤回="关";}
if(getString(qun,"撤回时间","")!=null){
撤回时间=getInt(qun,"撤回时间",30);}
String c="◇设置撤回时间\n"
+"当前撤回时间:"+撤回时间+"秒\n"
+"时间不得超过120秒\n"
+"◇开启/关闭自身撤回["+自身撤回+"]";
sendm(qun,c);}
if(text.equals("版本信息")){
File folder=new File(JavaPath);
String formattedSize=getFormattedSize(folder);
String c="账号昵称:"+getName(mWxid)+"\n"
+"目录大小:"+formattedSize+"\n"
+"脚本昵称:"+脚本作者+"\n"
+"当前版本:"+当前版本+"\n"
+"更新时间:"+更新时间+"\n"
+"脚本作者:"+脚本作者;
sendm(qun,c);}
if(text.equals("搜索功能")){
String 搜索功能="";
if("1".equals(getString(qun,"搜索功能",""))){搜索功能="开";}else{搜索功能="关";}
String c="◇堆糖搜图+内容\n"
+"◇搜索内容+内容\n"
+"◇开启/关闭搜索功能["+搜索功能+"]";
sendm(qun,c);}
if(text.equals("音乐系统")){
String 音乐系统="";
if("1".equals(getString(qun,"音乐系统",""))){音乐系统="开";}else{音乐系统="关";}
String c="◇点歌+歌名\n"
+"◇QQ点歌+歌名\n"
+"◇抖音点歌+歌名\n"
+"◇开启/关闭音乐系统["+音乐系统+"]";
sendm(qun,c);}
if(text.equals("配置设置")){
String 发送模式="";
if("1".equals(getString(qun,"发送模式",""))){发送模式="图片";}else{发送模式="文字";}
String c="◇切换文字发送\n"
+"◇切换图片发送\n"
+"当前是["+发送模式+"]发送";
sendm(qun,c);}
if(text.equals("图片系统")){
String 图片系统="";
if("1".equals(getString(qun,"图片系统",""))){图片系统="开";}else{图片系统="关";}
String c="◇诱惑图◇猫咪图◇\n"
+"◇买家秀◇兽猫酱◇\n"
+"◇帅哥图◇小清新◇\n"
+"◇动漫图◇看汽车◇\n"
+"◇看炫酷◇美女图◇\n"
+"◇风景图◇看腹肌◇\n"
+"◇萌宠图◇原神图◇\n"
+"◇看黑丝◇看白丝◇\n"
+"◇60秒看世界\n"
+"◇开启/关闭图片系统["+图片系统+"]";
sendm(qun,c);}
if(text.equals("开关系统")){
String 音乐系统="";
String 图片系统="";
String 智能系统="";
String 搜索功能="";
String 自身撤回="";
if("1".equals(getString(qun,"自身撤回",""))){自身撤回="开";}else{自身撤回="关";}
if("1".equals(getString(qun,"搜索功能",""))){搜索功能="开";}else{搜索功能="关";}
if("1".equals(getString(qun,"音乐系统",""))){音乐系统="开";}else{音乐系统="关";}
if("1".equals(getString(qun,"图片系统",""))){图片系统="开";}else{图片系统="关";}
if("1".equals(getString(qun,"智能系统",""))){智能系统="开";}else{智能系统="关";}
String c="◇开启/关闭音乐系统["+音乐系统+"]\n"
+"◇开启/关闭图片系统["+图片系统+"]\n"
+"◇开启/关闭智能系统["+智能系统+"]\n"
+"◇开启/关闭搜索功能["+搜索功能+"]\n"
+"◇开启/关闭自身撤回["+自身撤回+"]";
sendm(qun,c);}
if(text.equals("底部样式")){
String 底部时间="";
String 底部文案="";
String 底部尾巴="";
if("1".equals(getString("开关","底部时间",""))){底部时间="开";}else{底部时间="关";}
if("1".equals(getString("开关","底部文案",""))){底部文案="开";}else{底部文案="关";}
if("1".equals(getString("开关","底部尾巴",""))){底部尾巴="开";}else{底部尾巴="关";}
String c="◇开启/关闭底部时间["+底部时间+"]\n"
+"◇开启/关闭底部文案["+底部文案+"]\n"
+"◇开启/关闭底部尾巴["+底部尾巴+"]\n"
+"◇设置底部内容+内容";
sendm(qun,c);
}
if(text.equals("智能系统")){
String Token="";
String 手机号码="";
String 智能系统="";
if(取("开关","GLMToken").equals("")){Token="未绑定";}else{Token="已绑定";}
if(取("开关","手机号码").equals("")){手机号码="未绑定";}else{手机号码="已绑定";}
if("1".equals(getString(qun,"智能系统",""))){智能系统="开";}else{智能系统="关";}
String c="◇GLM4+问题\n"
+"◇重置对话\n"
+"◇搜索智能体\n"
+"◇查看智能体\n"
+"◇重置智能体\n"
+"绑定手机号可正常使用\n"
+"◇绑定手机号+号码\n"
+"◇手机状态:"+手机号码+"\n"
+"给自己发送该指令即可\n"
+"◇发送验证码\n"
+"◇填写+验证码\n"
+"◇清除绑定状态\n"
+"◇绑定状态:"+Token+"\n"
+"配置设置里填写手机号\n"
+"◇开启/关闭智能系统["+智能系统+"]";
sendm(qun,c);}
if("1".equals(getString(qun,"自身撤回",""))){
int 撤回时间=30;
if(getInt(qun,"撤回时间",0)!=null){
撤回时间=getInt(qun,"撤回时间",30);}
if(wxid.equals(mWxid)){
new Thread(new Runnable(){
public void run(){
Thread.sleep(撤回时间*1000);
recallMsg(data.values);
}}).start();
}}
}
}
}