public static void 视频(Object data){
String text=data.content;
String qun=data.talker;
String wxid=data.sendTalker;
if(text.equals("随机小世界")){
try{
String url=get("https://xsj.qq.com");
int index=url.lastIndexOf("window.__INITIAL_STATE__=");
String text1=url.substring(index + 25);
int rd=text1.indexOf("}<");
String re=text1.substring(0,rd+1);
JSONObject json=new JSONObject(re);
String video=json.get("video");//视频
String avatar=json.get("avatar");//作者头像
String nick=json.get("nick");//作者昵称
String desc=json.get("desc").replace("{tagName=","").replace("}","");//标签
sendWeb(qun,text,desc,avatar,video);
}catch(e){
Toast("错误");
}
}
}