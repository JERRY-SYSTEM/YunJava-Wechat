public static void 图片(Object data){
String text=data.content;
String qun=data.talker;
String wxid=data.sendTalker;
//部分接口来源于遇见
if(text.equals("诱惑图")){
String 图片=fetchRedirectUrl("http://api.yujn.cn/api/yht.php?type=image");
sendPic(qun,图片);
}
if(text.equals("猫咪图")){
String 图片=fetchRedirectUrl("http://www.yujn.cn/api/maomi.php?type=image");
sendPic(qun,图片);
}
if(text.equals("买家秀")){
String 图片=fetchRedirectUrl("http://api.yujn.cn/api/mjx.php?");
sendPic(qun,图片);
}
if(text.equals("兽猫酱")){
String 图片="http://api.yujn.cn/api/smj.php?";
sendPic(qun,图片);
}
if(text.equals("帅哥图")){
String 图片="https://api.lolimi.cn/API/boy/api.php";
sendPic(qun,图片);
}
if(text.equals("小清新")){
String 图片="https://api.lolimi.cn/API/360bz/api.php?n=5&type=image";
sendPic(qun,图片);
}
if(text.equals("动漫图")){
String 图片="https://api.lolimi.cn/API/360bz/api.php?n=6&type=image";
sendPic(qun,图片);
}
if(text.equals("看汽车")){
String 图片="https://api.lolimi.cn/API/360bz/api.php?n=10&type=image";
sendPic(qun,图片);
}
if(text.equals("看炫酷")){
String 图片="https://api.lolimi.cn/API/360bz/api.php?n=11&type=image";
sendPic(qun,图片);
}
if(text.equals("美女图")){
String 图片="http://api.yujn.cn/api/ksxjj.php?";
sendPic(qun,图片);
}
if(text.equals("风景图")){
String 图片="http://api.yujn.cn/api/fengjing.php?";
sendPic(qun,图片);
}
if(text.equals("看腹肌")){
String 图片="http://api.yujn.cn/api/fujiimg.php?";
sendPic(qun,图片);
}
if(text.equals("萌宠图")){
String 图片="http://api.yujn.cn/api/mc.php?";
sendPic(qun,图片);
}
if(text.equals("原神图")){
String 图片="http://api.yujn.cn/api/ys.php?";
sendPic(qun,图片);
}
if(text.equals("看黑丝")){
String 图片="http://api.yujn.cn/api/heisi.php?";
sendPic(qun,图片);
}
if(text.equals("看白丝")){
String 图片="http://api.yujn.cn/api/baisi.php?";
sendPic(qun,图片);
}
if(text.equals("60秒看世界")){
String 图片="http://api.yujn.cn/api/60SReadWorld.php?";
sendPic(qun,图片);
}
}