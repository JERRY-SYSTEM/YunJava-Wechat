public void 代管(Object data) {
String text=data.content;
String qun=data.talker;
String wxid=data.sendTalker;
    File 代管=new File(JavaPath+"/数据/"+qun+"/代管.txt");
    if(data.isReply()) {
    String wxid=getElementContent(data.content,"chatusr");
    String text=getElementContent(data.content,"title");
    if(text.equals("添加代管")) {
        if(wxid.equals(mWxid)) {
            sendm(qun,"无法操作");
            return;
        }
        if(简读用户(代管,wxid)) {
            sendm(qun,getName(wxid)+"("+wxid+")\n已经是代管了");
            } else {
            简写(代管,wxid);
            sendm(qun,getName(wxid)+"("+wxid+")\n已添加为代管");
        }
    }
    if(text.equals("删除代管")) {
        if(wxid.equals(mWxid)) {
            sendm(qun,"无法操作");
            return;
        }
        if(!简读用户(代管,wxid)) {
            sendm(qun,getName(wxid)+"("+wxid+")\n列表中未找到该用户");
            } else {
            简弃(代管,wxid);
            sendm(qun,getName(wxid)+"("+wxid+")\n已删除该用户");
        }
    }
    }
    if(text.equals("查看代管")||text.equals("代管列表")) {
        String g=read(JavaPath+"/数据/代管/"+qun+"/代管.txt");
        String c;
        if(g.equals("还没有添加")) {
            c="还没有添加";
        } else {
            c="列表如下:\n"+g;
        }
        sendm(qun,c);
    }
    if(text.equals("清空代管")) {
        全弃(代管);
        sendm(qun,"已清空");
    }
}