public class 整点报时 {
    int start=7;//开始(0-23)
    int end=23;//结束(1-24)
    ScheduledExecutorService scheduler=Executors.newScheduledThreadPool(1);
    scheduler.scheduleAtFixedRate(new Runnable() {
        public void run() {
            SimpleDateFormat df=new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
            Calendar calendar=Calendar.getInstance();
            LocalDateTime now=LocalDateTime.now();
            String Hour=""+calendar.get(Calendar.HOUR_OF_DAY);
            if(!Hour.equals(取("签到","整点"))&&now.getHour()>=start&&now.getHour()<=end) {
                String time=df.format(calendar.getTime());
                String baoshi=get("https://xiaoapi.cn/API/zs_zdbs.php?h="+now.getHour());
                String msg="";
                String mp3="";
                JSONObject json=new JSONObject(baoshi);
                Integer code=json.getInt("code");
                if(code==200) {
                    msg=json.getString("msg");
                    mp3=json.getString("mp3");
                }
                for (String qun:getGroups()) {
                    if("1".equals(getString(qun,"整点报时",""))&&"1".equals(getString(qun,"开关",""))) {
                        sendMsg(qun,"整点报时\n"+msg+"\n"+time);
                    }
                }
                putString("签到","整点",Hour);
            }
        }
    },0,1,TimeUnit.SECONDS);
} new 整点报时();

public void 报时(Object data) {
    String text=data.content;
    String qun=data.talker;
    String wxid=data.sendTalker;
    if(text.equals("报时测试")) {
        SimpleDateFormat df=new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
        Calendar calendar=Calendar.getInstance();
        LocalDateTime now=LocalDateTime.now();
        String time=df.format(calendar.getTime());
        String baoshi=get("https://xiaoapi.cn/API/zs_zdbs.php?h="+now.getHour());
        String msg="";
        String mp3="";
        JSONObject json=new JSONObject(baoshi);
        Integer code=json.getInt("code");
        if(code==200) {
            msg=json.getString("msg");
            mp3=json.getString("mp3");
        }
        Toast(msg);
        sendMsg(qun,"报时测试\n"+msg+"\n"+time);
        提示音(mContext,mp3);
    }
}