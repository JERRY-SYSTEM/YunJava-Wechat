public void 解析(Object data) {
    String text=data.content;
    String qun=data.talker;
    String wxid=data.sendTalker;
    if(text.startsWith("短视频解析")) {
        text=text.substring(5);
        if(text.equals("")) {
            return;
        }
        String url="";
        String urlPattern="(\\/?|\\b)((https?|ftp|file):\\/\\/|www\\.)[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|\\p{IsHan}]*";
                Pattern pattern=Pattern.compile(urlPattern);
                Matcher matcher=pattern.matcher(text);
                if(matcher.find()) {
                    url=matcher.group();
                    }
        String result=get("https://api.xiaoyatou.site/home/api?type=dsp&uid=1319485&key=befjkmqswxyCDHN349&unique_k=2333&url="+url);
        try {
        JSONObject json=new JSONObject(result);
        Integer code=json.getInt("code");
        String msg=json.getString("msg");
        if(code==200) {
            String video=json.getJSONObject("data").getString("video");
            sendReply(data.msgId,qun,video);
        } else {
        sendm(qun,msg);
        }
                } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
        if(text.startsWith("长视频解析")) {
        text=text.substring(5);
        if(text.equals("")) {
            return;
        }
        String url="";
        String urlPattern="(\\/?|\\b)((https?|ftp|file):\\/\\/|www\\.)[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|\\p{IsHan}]*";
                Pattern pattern=Pattern.compile(urlPattern);
                Matcher matcher=pattern.matcher(text);
                if(matcher.find()) {
                    url=matcher.group();
                    }
        String result=get("https://json.vipjx.cnow.eu.org/?url="+url);
        try {
        JSONObject json=new JSONObject(result);
        Integer code=json.getInt("code");
        String msg=json.getString("msg");
        if(code==200) {
            String url=json.getString("url");
            sendReply(data.msgId,qun,url);
        } else {
        sendm(qun,msg);
        }
                } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
}