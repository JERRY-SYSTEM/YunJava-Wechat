public static void 图片(Object data) {
    String text=data.content;
    String qun=data.talker;
    String wxid=data.sendTalker;
    if(text.equals("小狐狸")) {
        String 图片="https://t.alcy.cc/xhl";
        sendPic(qun,图片);
    }
    if(text.equals("七濑胡桃")) {
        String 图片="https://t.alcy.cc/lai";
        sendPic(qun,图片);
    }
    if(text.equals("方形头像")) {
        String 图片="https://t.alcy.cc/tx";
        sendPic(qun,图片);
    }
    if(text.equals("原神竖图")) {
        String 图片="https://t.alcy.cc/ysmp";
        sendPic(qun,图片);
    }
    if(text.equals("萌版竖图")) {
        String 图片="https://t.alcy.cc/moemp";
        sendPic(qun,图片);
    }
    if(text.equals("移动竖图")) {
        String 图片="https://t.alcy.cc/mp";
        sendPic(qun,图片);
    }
    if(text.equals("原神横图")) {
        String 图片="https://t.alcy.cc/ys";
        sendPic(qun,图片);
    }
    if(text.equals("白底横图")) {
        String 图片="https://t.alcy.cc/bd";
        sendPic(qun,图片);
    }
    if(text.equals("风景横图")) {
        String 图片="https://t.alcy.cc/fj";
        sendPic(qun,图片);
    }
    if(text.equals("萌版横图")) {
        String 图片="https://t.alcy.cc/moe";
        sendPic(qun,图片);
    }
    if(text.equals("PC横图")) {
        String 图片="https://t.alcy.cc/pc";
        sendPic(qun,图片);
    }
    if(text.equals("r18")) {
        String 图片=get("https://api.shenke.love/api/r18.php");
        sendPic(qun,图片);
    }
    //部分接口来源于遇见
    if(text.equals("诱惑图")) {
        String 图片=fetchRedirectUrl("https://api.yujn.cn/api/yht.php?type=image");
        sendPic(qun,图片);
    }
    if(text.equals("猫咪图")) {
        String 图片=fetchRedirectUrl("https://www.yujn.cn/api/maomi.php?type=image");
        sendPic(qun,图片);
    }
    if(text.equals("买家秀")) {
        String 图片=fetchRedirectUrl("https://api.yujn.cn/api/mjx.php");
        sendPic(qun,图片);
    }
    if(text.equals("兽猫酱")) {
        String 图片="https://api.yujn.cn/api/smj.php";
        sendPic(qun,图片);
    }
    if(text.equals("帅哥图")) {
        String 图片="https://api.lolimi.cn/API/boy/api.php";
        sendPic(qun,图片);
    }
    if(text.equals("小清新")) {
        String 图片="https://api.lolimi.cn/API/360bz/api.php?n=5&type=image";
        sendPic(qun,图片);
    }
    if(text.equals("动漫图")) {
        String 图片="https://api.lolimi.cn/API/360bz/api.php?n=6&type=image";
        sendPic(qun,图片);
    }
    if(text.equals("看汽车")) {
        String 图片="https://api.lolimi.cn/API/360bz/api.php?n=10&type=image";
        sendPic(qun,图片);
    }
    if(text.equals("看炫酷")) {
        String 图片="https://api.lolimi.cn/API/360bz/api.php?n=11&type=image";
        sendPic(qun,图片);
    }
    if(text.equals("美女图")) {
        String 图片="https://v2.api-m.com/api/meinvpic?return=302";
        sendPic(qun,图片);
    }
    if(text.equals("风景图")) {
        String 图片="https://api.yujn.cn/api/fengjing.php";
        sendPic(qun,图片);
    }
    if(text.equals("看腹肌")) {
        String 图片="https://api.yujn.cn/api/fujiimg.php";
        sendPic(qun,图片);
    }
    if(text.equals("萌宠图")) {
        String 图片="https://api.yujn.cn/api/mc.php";
        sendPic(qun,图片);
    }
    if(text.equals("原神图")) {
        String 图片="https://api.lolimi.cn/API/yuan/api.php?type=image";
        sendPic(qun,图片);
    }
    if(text.equals("看黑丝")) {
        String 图片="https://v2.api-m.com/api/heisi?return=302";
        sendPic(qun,图片);
    }
    if(text.equals("看白丝")) {
        String 图片="https://v2.api-m.com/api/baisi?return=302";
        sendPic(qun,图片);
    }
    if(text.equals("60秒看世界")) {
        String 图片="https://api.yujn.cn/api/60SReadWorld.php";
        sendPic(qun,图片);
    }
}