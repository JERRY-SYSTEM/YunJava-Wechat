public void 娱乐(Object data) {
    String text=data.content;
    String qun=data.talker;
    String wxid=data.sendTalker;
    if(text.equals("签到")) {
        Calendar cl=Calendar.getInstance();
        SimpleDateFormat df=new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
        String time=df.format(cl.getTime());
        String Day=cl.get(Calendar.YEAR)+"-"+cl.get(Calendar.DAY_OF_YEAR);
        if(!Day.equals(取("签到",wxid))) {
            存("签到",wxid,Day);
            String c="用户："+getName(wxid)+"\n"
                    +time;
            sendm(qun,c);
        } else {
        String c="用户："+getName(wxid)+"\n"
                +"今天你已经签到过了";
        sendm(qun,c);
        }
    }
}