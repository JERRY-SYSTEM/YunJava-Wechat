/*public String 睿声签到() {
    睿声 睿声=new 睿声();
    String result=睿声.post("https://v1.reecho.cn/api/billing/checkin","");
    JSONObject json=new JSONObject(result);
    Integer status=json.getInt("status");
    String message=json.getString("message");
    if(status==200) {
        Integer credit=json.getInt("credit");
        return "福利:签到成功+"+credit+"点数";
    } else if(status==403) {
        return "福利:重复签到";
    } else {
        return "福利:"+message;
    }
}
public String 睿声群签到() {
    睿声 睿声=new 睿声();
    String result=睿声.post("https://v1.reecho.cn/api/qbot/checkInByUser","");
    JSONObject json=new JSONObject(result);
    Integer status=json.getInt("status");
    String message=json.getString("message");
    if(status==200) {
        return "群聊:签到成功"+message;
    } else if(status==403) {
        return "群聊:重复签到";
    } else {
        return "群聊:"+message;
    }
}
public String 睿声信息() {
    睿声 睿声=new 睿声();
    String result=睿声.get("https://v1.reecho.cn/api/account/info");
    JSONObject json=new JSONObject(result);
    JSONObject user=json.getJSONObject("user");
    Integer status=json.getInt("status");
    String message=json.getString("message");
    if(status==200) {
        Integer credit=user.getInt("credit");
        Integer credits=user.getInt("credits");
        String name=user.getString("name");
        String text="用户:"+name+"\n"
                   +"永久:"+credit+"点数\n"
                   +"临时:"+credits+"点数";
        return text;
    } else {
        return message;
    }
}
public void 签到(Object data) {
    String text=data.content;
    String qun=data.talker;
    String wxid=data.sendTalker;
    if(text.equals("自动签到")) {
        Calendar cl=Calendar.getInstance();
        String Day=cl.get(Calendar.YEAR)+"-"+cl.get(Calendar.DAY_OF_YEAR);
       String 自动签到="×";
       if("1".equals(getString("开关","自动签到",""))) {
           自动签到="√";
       }
       String 睿声绑定="";
       if(取("绑定","睿声").equals("")) {
           睿声绑定="╠睿声未绑定\n";
       }
       String 睿声状态="未签到";
       if(Day.equals(取("签到","睿声"))) {
           睿声状态="已签到";
       }
        String c="╔绑定睿声#账号#密码\n"
                +睿声绑定
                +"╚睿声"+睿声状态+"\n\n"
                +"╔一键签到\n"
                +"╠每天凌晨自动执行\n"
                +"╚开启/关闭自动签到("+自动签到+")";
        sendm(qun,c);
    }
    if(text.equals("一键签到")) {
        Calendar cl=Calendar.getInstance();
        String Day=cl.get(Calendar.YEAR)+"-"+cl.get(Calendar.DAY_OF_YEAR);
        String c="";
        if(!取("绑定","睿声").equals("")) {
            c+="----睿声----\n"
               +睿声签到()+"\n"
               +睿声群签到()+"\n"
               +睿声信息();
               putString("签到","睿声",Day);
        }
        sendm(qun,c);
    }
    if(text.startsWith("绑定睿声")) {
        String[] parts=text.split("(#|＃)", -1);
        try {
            if(parts.length>=2) {
                String 账号=parts[1];
                String 密码=parts[2];
                String result=jsonPost("https://v1.reecho.cn/api/account/loginByPhone","{\"phoneNumber\":\""+账号+"\",\"password\":\""+密码+"\"}");
                JSONObject json=new JSONObject(result);
                Integer status=json.getInt("status");
                String message=json.getString("message");
                if(status==200) {
                    String token=json.getString("token");
                    putString("绑定","睿声",token);
                    sendReply(data.msgId,qun,"睿声绑定成功!");
                } else {
                    sendReply(data.msgId,qun,message);
                }
            }
        } catch(Exception e) {
            sendReply(data.msgId,qun,"输入格式错误!");
        }
    }
}
public class 自动签到 {
    ScheduledExecutorService scheduler=Executors.newScheduledThreadPool(1);
    scheduler.scheduleAtFixedRate(new Runnable() {
        public void run() {
            Calendar cl=Calendar.getInstance();
            String Day=cl.get(Calendar.YEAR)+"-"+cl.get(Calendar.DAY_OF_YEAR);
            if("1".equals(getString("开关","自动签到",""))) {
                if(!Day.equals(取("签到","睿声"))) {
                    String c="";
                    if(!取("绑定","睿声").equals("")) {
                        c+="----睿声----\n"
                           +睿声签到()+"\n"
                           +睿声群签到()+"\n"
                           +睿声信息();
                    }
                    Toast(c);
                    sendm(mWxid,c);
                    putString("签到","睿声",Day);
                }
            }
        }
    },0,1,TimeUnit.MINUTES);
} new 自动签到();*/