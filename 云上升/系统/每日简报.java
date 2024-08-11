public class 每日简报 {
    int start=8;//开始(0-23)
    int end=11;//结束(1-24)
    ScheduledExecutorService scheduler=Executors.newScheduledThreadPool(1);
    scheduler.scheduleAtFixedRate(new Runnable() {
        public void run() {
            SimpleDateFormat df=new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
            Calendar calendar=Calendar.getInstance();
            LocalDateTime now=LocalDateTime.now();
            String Day=calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.DAY_OF_YEAR);
            if(!Day.equals(取("执行","简报"))&&now.getHour()>=start&&now.getHour()<=end) {
                for (String qun:getGroups()) {
                    if("1".equals(getString(qun,"每日简报",""))&&"1".equals(getString(qun,"开关",""))) {
                        String time=df.format(calendar.getTime());
                        String text="";
                        String result=get("https://api.pearktrue.cn/api/60s");
                        JSONObject json=new JSONObject(result);
                        Integer code=json.getInt("code");
                        if(code==200) {
                            //String time=json.getString("time");
                            JSONArray data=json.getJSONArray("data");
                            for(int i=0;i<data.length();i++) {
                                if(i<data.length()-1) {
                                    text+=data.getString(i)+"\n";
                                } else {
                                    text+=data.getString(i);
                                }
                            }
                            text=time+"\n"+text;
                            sendMsg(qun,text);
                        }
                    }
                }
                putString("执行","简报",Day);
            }
        }
    },0,3,TimeUnit.MINUTES);
} new 每日简报();
public void 简报(Object data) {
    String text=data.content;
    String qun=data.talker;
    String wxid=data.sendTalker;
    if(text.equals("测试简报")) {
        SimpleDateFormat df=new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
        Calendar calendar=Calendar.getInstance();
        LocalDateTime now=LocalDateTime.now();
        String time=df.format(calendar.getTime());
        String text="";
        String result=get("https://api.pearktrue.cn/api/60s");
        JSONObject json=new JSONObject(result);
        Integer code=json.getInt("code");
        if(code==200) {
            //String time=json.getString("time");
            JSONArray data=json.getJSONArray("data");
            for(int i=0;i<data.length();i++) {
                if(i<data.length()-1) {
                    text+=data.getString(i)+"\n";
                } else {
                    text+=data.getString(i);
                }
            }
            text=time+"\n"+text;
            sendMsg(qun,text);
        }
    }
}