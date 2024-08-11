public void 播报(Object data) {
    String text=data.content;
    String qun=data.talker;
    String wxid=data.sendTalker;
    if(data.talkerType==0&&data.isSend==0) {
        String f=JavaPath+"/缓存/音频/"+wxid+"/"+data.msgId+".aac";
        String text1="发了条特殊消息";
        if(data.type==1) {
            text1=text;
            if(text1.length()>201) {
                text1=text.substring(0,201)+"……后面的消息请前往微信查看";
            }
        }
        if(data.type==3) text1="发了张图片";
        if(data.type==34) text1="发了条语音";
        if(data.type==43) text1="发了条视频";
        if(data.type==47) text1="发了张表情包";
        if(data.type==49) text1="发了个卡片";
        if(data.type==10000) {
            if(data.content==null) text1="发了个提示";
            if(data.content!=null) text1="提示\n"+data.content;
            if(data.content.contains("添加到通讯录")) text1="刚刚添加了你为好友";
        }
        if(data.type==1040187441) text1="发了个音乐卡片";
        if(data.type==419430449) text1="发了个转账";
        if(data.type==436207665) text1="发了个红包";
        if(data.type==469762097) text1="发了个拜年红包";
        if(data.type==822083633) {
            text=getElementContent(data.content,"title");
            text1="回复\n"+text;
            if(text1.length()>201) {
                text1="回复\n"+text.substring(0,201)+"……后面的消息请前往微信查看";
            }
        }
        if(data.type==1090519089) text1="发了个文件";
        text1=u加(getName(wxid)+"("+wxid+")\n"+text1);
        Toast(u解(text1));
    }
}