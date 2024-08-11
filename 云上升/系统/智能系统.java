public void 智能(Object data){
String text=data.content;
String qun=data.talker;
String wxid=data.sendTalker;
if(mWxid.equals(qun)){
if(text.startsWith("绑定手机号")){
text=text.substring(5);
if(text.matches("^[0-9]{11}$")){
putString("开关","手机号码",text);
sendMsg(qun,"已成功绑定");
}}}
if(mWxid.equals(wxid)){
if(text.equals("发送验证码")){
if(!getString("开关","手机号码","").equals("")){
检查 临时标志=new 检查();
临时标志.时间=System.currentTimeMillis();
String jsonString=jsonPost("https://chatglm.cn/chatglm/backend-api/v1/user/checkphone","{\"phone\":\""+getString("开关","手机号码","")+"\",\"rid\":\"\",\"ismobile\":1,\"distinct_id\":\"57c0290a67a7b93d\",\"tm\":\"android\",\"oaid\":\"81ffef1e1dc98fdb\",\"fr\":\"xiaomi\"}");
if(!jsonString.contains("<!DOCTYPE html>")){
JSONObject jsonObject = new JSONObject(jsonString);
String accessToken = jsonObject.getString("message");
if(accessToken.equals("短信验证码已发送")){
sendm(qun,accessToken+"\n一分钟内有效");
}else{
sendm(qun,accessToken);
}
地图.put(qun+wxid,临时标志);
选择=51;
return;}
}
sendm(qun,"请先绑定手机号");
}
if(选择==51&&text.startsWith("填写")){
text=text.substring(2);
if(text.equals("")){return;}
检查 资讯=地图.get(qun+wxid);
if(System.currentTimeMillis()/1000-资讯.时间/1000>60){
地图.remove(qun+wxid);
选择=0;return;}
if(text.matches("^[0-9][0-9]{1,8}$")){
String jsonString=jsonPost("https://chatglm.cn/chatglm/backend-api/v1/user/login","{\"phone\":\""+getString("开关","手机号码","")+"\",\"code\":\""+text+"\",\"ismobile\":1,\"distinct_id\":\"57c0290a67a7b93d\",\"tm\":\"android\",\"oaid\":\"81ffef1e1dc98fdb\",\"fr\":\"xiaomi\",\"ffr\":\"xiaomi\"}");
String c="";
Pattern pattern=Pattern.compile("\"message\":\"(.*?)\"");
Matcher matcher=pattern.matcher(jsonString);
if(matcher.find()){
String message=matcher.group(1);
if(message.equals("参数错误")){
sendm(qun,message);
return;}
if(message.equals("success")){
JSONObject jsonObject = new JSONObject(jsonString);
String accessToken = jsonObject.getJSONObject("result").getString("access_token");
c="Bearer "+accessToken;
putString("开关","GLMToken",c);
sendm(qun,"Token获取成功，已自动填写");
return;}
}else{c="失败，请重新再试！";}
sendm(qun,c);
return;}
}
if(!getString("开关","GLMToken","").equals("")){
if(text.equals("重置智能体")){
putString("GLM4智能体",qun,null);
putString("GLM4对话",qun,null);
putString("GLM4名字",qun,null);
putString("GLM4简介",qun,null);
putString("GLM4作者",qun,null);
sendm(qun,"已重置");
}
if(text.equals("查看智能体")){
String 名字="";
String 描述="";
String 作者="";
if(!取("GLM4名字",qun).equals("")){名字=取("GLM4名字",qun);}
if(!取("GLM4描述",qun).equals("")){描述=取("GLM4描述",qun);}
if(!取("GLM4作者",qun).equals("")){作者=取("GLM4作者",qun);}
String result="智能体:"+名字+"\n来自于:"+作者+"\n描述语:\n"+描述;
if(取("GLM4名字",qun).equals("")||取("GLM4描述",qun).equals(""))
{result="当前默认体";}
sendm(qun,result);
}
if(text.startsWith("搜索智能体")){
text=text.substring(5).trim();
if(text.equals("")){return;}
检查 临时标志=new 检查();
临时标志.时间=System.currentTimeMillis();
String GLMGET=GLMGET("https://chatglm.cn/chatglm/feed-api/assistant/search_list?page=1&pageSize=15&keyword="+text);
String message="";
StringBuilder process=new StringBuilder();
//判断
String[] patterns={"发生错误: (.*?)","<title>(.*?)</title>","\"msg\":\"(.*?)\"","\"message\":\"(.*?)\""};
for (String patternStr : patterns){
Pattern pattern=Pattern.compile(patternStr);
Matcher matcher=pattern.matcher(GLMGET);
if (matcher.find()){
String message = matcher.group(1);
if(patternStr.equals("\"message\":\"(.*?)\"")&&message.equals("success")){
continue;}
process.append(message);
message=process.toString();
sendm(qun,message);
return;}}
String result2="";
JSONObject json=new JSONObject(GLMGET);
JSONObject json_result=json.getJSONObject("result");
JSONArray json_result_list=json_result.getJSONArray("list");
if(json_result_list==null||json_result_list.length()==0){
sendm(qun,"未搜到");
return;}
for(int i=0;i<json_result_list.length();i++){
JSONObject json_result_list_i=json_result_list.getJSONObject(i);
String json_result_list_i_assistant_id=json_result_list_i.getString("assistant_id");
String json_result_list_i_user_nickname=json_result_list_i.getString("user_nickname");
String json_result_list_i_name=json_result_list_i.getString("name");
String json_result_list_i_description=json_result_list_i.getString("description");
JSONArray json_result_list_i_starter_prompts=json_result_list_i.getJSONArray("starter_prompts");
String result3="";
if(json_result_list_i_starter_prompts!=null&&json_result_list_i_starter_prompts.length()!=0){
for(int h=0;h<json_result_list_i_starter_prompts.length();h++){
String json_result_list_i_starter_prompts_i = json_result_list_i_starter_prompts.getString(h);
result3+=(h+1)+"."+json_result_list_i_starter_prompts_i+"\n";
}
} else { result3="无开场白"; }
result2+=(i+1)+"."+json_result_list_i_name+"\n";
String b=String.valueOf(i+1);
写(qun,"提示",b,result3);
写(qun,"名字",b,json_result_list_i_name);
写(qun,"作者",b,json_result_list_i_user_nickname);
写(qun,"助手",b,json_result_list_i_assistant_id);
写(qun,"描述",b,json_result_list_i_description);
临时标志.数量=i+1;
}
地图.put(qun+wxid,临时标志);
result2=result2+"\n请发送序号来进行选择\n两分钟内有效";
选择=61;
sendm(qun,result2);
}
if(选择==61&&text.matches("^[1-9]\\d*$")){
if(地图==null||!地图.containsKey(qun+wxid)){
return;}
检查 资讯=地图.get(qun+wxid);
if(资讯.数量<Integer.parseInt(text)){
return;}
if(System.currentTimeMillis()/1000-资讯.时间/1000>120){
地图.remove(qun+wxid);
选择=0;
return;}
try{
String 名字=取(qun,"名字",text);
String 助手=取(qun,"助手",text);
String 描述=取(qun,"描述",text);
String 作者=取(qun,"作者",text);
String 提示=取(qun,"提示",text);
存("GLM4描述",qun,描述);
存("GLM4智能体",qun,助手);
存("GLM4对话",qun,null);
存("GLM4名字",qun,名字);
存("GLM4作者",qun,作者);
String result="智能体:"+名字+"\n来自于:"+作者+"\n开场白:\n"+提示+"\n描述语:\n"+描述;
sendm(qun,result);
}catch(e){
sendm(qun,"错误,请稍候再试");
return;}
}
}
if(text.equals("清除绑定状态")){
putString("开关","GLMToken",null);
sendm(qun,"已清除");
}
if(text.equals("重置对话")){
putString("GLM4对话",qun,null);
sendm(qun,"已重置");
}
}
new Thread(new Runnable(){
public void run(){
if(!取("开关","GLMToken").equals("")){
if(text.startsWith("GLM4")){
text=text.substring(4).trim();
//回复对象文字
Matcher matcher=Pattern.compile("\\@(.*?)\\s").matcher(text);
text=matcher.replaceAll("");
if(text.contains("")){
text="带有特殊小表情，换个吧";
sendm(qun,text);
return;}
StringBuilder ask=new StringBuilder();
StringBuilder image=new StringBuilder();
String text1="";
String text2="";
String text3="";

text2="{\"type\":\"text\",\"text\":\""+ask.append(text).toString()+"\"}";
String 记忆对话="";
String 智能体="65940acff94777010aa6b796";
if(!取("GLM4对话",qun).equals("")){记忆对话=取("GLM4对话",qun);}
if(!取("GLM4智能体",qun).equals("")){智能体=取("GLM4智能体",qun);}
String eventMessage=GLM("https://chatglm.cn/chatglm/backend-api/assistant/stream","{\"assistant_id\":\""+智能体+"\",\"conversation_id\":\""+记忆对话+"\",\"meta_data\":{\"is_test\":false,\"input_question_type\":\"xxxx\",\"channel\":\"\"},\"messages\":["+text1+"{\"role\":\"user\",\"content\":["+text2.replace("}{","},{")+"]}]}");
StringBuilder process=new StringBuilder();
//判断
String[] patterns={"发生错误: (.*?)","<title>(.*?)</title>","\"msg\":\"(.*?)\"","\"message\":\"(.*?)\""};
for(String patternStr:patterns){
Pattern pattern=Pattern.compile(patternStr);
Matcher matcher=pattern.matcher(eventMessage);
if(matcher.find()){
String message=matcher.group(1);
if(patternStr.equals("\"message\":\"(.*?)\"")&&message.equals("success")){
continue;
}
process.append(message);
message=process.toString();
sendm(qun,message);
return;
}}//回答内容
String[] lines=eventMessage.split("event:message\ndata:");
//图片内容循环遍历每一行
for(int i=0;i<lines.length;i+=3){
String firstLine=lines[i]; // 获取当前行的数据
if(firstLine!=null&&firstLine.contains("image_url")){ //是否包含image_url
JSONObject jsonObject=new JSONObject(firstLine);
JSONArray partsArray=jsonObject.getJSONArray("parts");
JSONObject partObject=partsArray.getJSONObject(0);
JSONArray contentArray=partObject.getJSONArray("content");
JSONObject contentObject=contentArray.getJSONObject(0);
JSONArray imageArray=contentObject.getJSONArray("image");
JSONObject imageObject=imageArray.getJSONObject(0);
String imageUrl=imageObject.getString("image_url");//提取图片的URL
sendPic(qun,imageUrl);
}}//提取最后一个
String lastLine=lines[lines.length - 1];
JSONObject jsonObject=new JSONObject(lastLine);
String conversation_id=jsonObject.getString("conversation_id");
存("GLM4对话",qun,conversation_id);
JSONArray partsArray=jsonObject.getJSONArray("parts");
JSONObject partObject=partsArray.getJSONObject(0);
JSONArray contentArray=partObject.getJSONArray("content");
if(contentArray!=null&&contentArray.length()>0){
JSONObject contentObject=contentArray.getJSONObject(0);
//问答内容
if(contentObject.has("text")){
process.append(contentObject.getString("text"));
}}//最后希望
JSONObject lastErrorObject=jsonObject.getJSONObject("last_error");
if(lastErrorObject!=null){
if(lastErrorObject.has("intervene_text")){
String interveneText=lastErrorObject.getString("intervene_text");
process.append(interveneText);
}}//空字符
if(process.toString().equals("")){
process.append("出错了，请换个话题或重置对话再试！");
sendm(qun,process.toString());
return;}
String message=process.toString().replace("GLM-4", "智普4");
sendTextCard(qun,message);
}
}
if(text.startsWith("\u753b\u753b")){
text=text.substring(2).trim();
if(text.equals("")){return;}
String user="0";String device="0";
if(!取("xingye","user").equals("")){user=取("xingye","user");}
if(!取("xingye","device").equals("")){device=取("xingye","device");}
String xingye=xingye("https://api.xingyeai.com/weaver/api/v1/npc_editor/preview/avatar?app_id=600&device_platform=android&device_type=22081212C&brand=Xiaomi&device_brand=Redmi&resolution=2624*1220&os_version=14&channel=xy_YYB&version_code=1120004&version_name=1.12.004&sys_region=CN&sys_language=zh&oaid=81ffef1e1dc98fdb&ip_region=cn&os=2&is_anonymous=false&license_status=0&emulator=false&network_type=4G&user_id="+user+"&device_id="+device,"{\"prompt\":\""+text+"\",\"user_define_gender\":3,\"reference\":[2],\"avatar_style_id_list\":[]}");
String PainTing="";
JSONObject json=new JSONObject(xingye);
if(json.has("img_list")){
JSONArray img_list=json.getJSONArray("img_list");
for(int i = 0; i <img_list.length(); i++){
JSONObject list=img_list.getJSONObject(i);
String url=list.getString("img_url");
String name=list.getString("style_name");

PainTing+="\u7c7b\u578b:"+name+"[pic="+url+"]";
}
} else if(json.has("base_resp")||json.has("status_msg"))
{
xingye=xingye("https://api.xingyeai.com/weaver/api/v1/account/login?app_id=600&device_platform=android&device_type=22081212C&brand=Xiaomi&device_brand=Redmi&resolution=2624*1220&os_version=14&channel=xy_YYB&version_code=1120004&version_name=1.12.004&sys_region=CN&sys_language=zh&oaid=81ffef1e1dc98fdb&ip_region=cn&user_id=0&os=2&user_mode=0&is_anonymous=false&license_status=0&emulator=false&network_type=4G","{\"login_type\":5}");
JSONObject json=new JSONObject(xingye);
user=json.getString("user_id");
auth=json.getString("auth_token");
device=json.getString("device_id");
存("xingye","user",user);
存("xingye","auth",auth);
存("xingye","device",device);
xingye=xingye("https://api.xingyeai.com/weaver/api/v1/npc_editor/preview/avatar?app_id=600&device_platform=android&device_type=22081212C&brand=Xiaomi&device_brand=Redmi&resolution=2624*1220&os_version=14&channel=xy_YYB&version_code=1120004&version_name=1.12.004&sys_region=CN&sys_language=zh&oaid=81ffef1e1dc98fdb&ip_region=cn&os=2&is_anonymous=false&license_status=0&emulator=false&network_type=4G&user_id="+user+"&device_id="+device,"{\"prompt\":\""+text+"\",\"base_img_url\":\"\",\"user_define_gender\":3,\"reference\":[2],\"avatar_style_id_list\":[]}");
JSONObject json=new JSONObject(xingye);
if(json.has("img_list")){
JSONArray img_list=json.getJSONArray("img_list");
for(int i = 0; i <img_list.length(); i++)
{
JSONObject list=img_list.getJSONObject(i);
String url=list.getString("img_url");
String name=list.getString("style_name");
PainTing+="\u7c7b\u578b:"+name+"[pic="+url+"]";
}
}

}
sendReplyMsg(qun,data.msgId,PainTing+"\n\n\u4f60\u8981\u7684\u56fe\u7247\u6765\u54af",2);
}

}}).start();
}