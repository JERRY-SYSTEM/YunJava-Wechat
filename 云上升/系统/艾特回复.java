public void 艾特(Object data){
String text=data.content;
String qun=data.talker;
String wxid=data.sendTalker;
if(wxid.equals(AuthorWxid)||mWxid.equals(wxid)){
if(text.startsWith("设置艾特回复")){
text=text.substring(6);
putString(qun,"艾特回复内容",text);
sendm(qun,"已设置");}
if(text.equals("查看艾特回复内容")){
text="还没有设置";
if(!getString(qun,"艾特回复内容","").equals("")){text=getString(qun,"艾特回复内容","");}
sendm(qun,text);}
if(text.equals("重置艾特回复内容")){
putString(qun,"艾特回复内容",null);
sendm(qun,"已重置");}
if(text.equals("查看艾特回复变量")){
String c="◇当前群名:[当前群名]\n"
+"◇用户名字:[用户名字]\n"
+"◇当前时间:[当前时间]\n"
+"◇自己名字:[自己名字]\n"
+"◇消息内容:[消息内容]\n"
+"◇群人数:[群人数]";
sendm(qun,c);}
if(text.equals("切换内容艾特回复")){
putString(qun,"艾特回复类型",null);
sendm(qun,"已切换为内容回复");}
if(text.equals("切换智能艾特回复")){
putString(qun,"艾特回复类型","1");
sendm(qun,"已切换为智能回复");}
}
if(text.contains("@"+getName(mWxid)+" ")){
text=text.trim();
Pattern pattern=Pattern.compile("\\@(.*?)\\s");
Matcher matcher=pattern.matcher(text);
text=matcher.replaceAll("");
if("1".equals(getString(qun,"艾特回复类型",""))){
if(!取("开关","GLMToken").equals("")){
if(text.equals("")){return;}
if(text.contains("")){
text="带有特殊小表情，换个吧";
sendm(qun,text);return;}
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
String message=process.toString().replace("AI", "ai");
sendTextCard(qun,message);
}
}else{
SimpleDateFormat df=new SimpleDateFormat("yy年MM月dd日HH:mm");
Calendar calendar=Calendar.getInstance();
String time=df.format(calendar.getTime());
Messagecontent=text;
text="[用户名字]你艾特我干嘛？";
if(!getString(qun,"艾特回复内容","").equals("")){text=getString(qun,"艾特回复内容","");}
text=text.replace("[消息内容]",Messagecontent+" ");
text=text.replace("[当前时间]",time);
text=text.replace("[用户名字]",getName(wxid));
text=text.replace("[当前群名]",getName(qun));
text=text.replace("[自己名字]",getName(mWxid));
text=text.replace("[群人数]",String.valueOf(getChatMembers(qun)));
sendMsg(qun,text);
}
}

}