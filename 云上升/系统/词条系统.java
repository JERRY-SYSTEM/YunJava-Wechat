public void 词条(Object data) {
    String text=data.content;
    String qun=data.talker;
    String wxid=data.sendTalker;
    if(text.equals("动画一言")) {
        String result=get("https://v1.hitokoto.cn/?c=a");
        try {
            JSONObject json=new JSONObject(result);
            String hitokoto=json.getString("hitokoto");
            String from=json.getString("from");
            String c=hitokoto+"\n--"+from;
            sendm(qun,c);
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(text.equals("漫画一言")) {
        String result=get("https://v1.hitokoto.cn/?c=b");
        try {
            JSONObject json=new JSONObject(result);
            String hitokoto=json.getString("hitokoto");
            String from=json.getString("from");
            String c=hitokoto+"\n--"+from;
            sendm(qun,c);
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(text.equals("游戏一言")) {
        String result=get("https://v1.hitokoto.cn/?c=c");
        try {
            JSONObject json=new JSONObject(result);
            String hitokoto=json.getString("hitokoto");
            String from=json.getString("from");
            String c=hitokoto+"\n--"+from;
            sendm(qun,c);
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(text.equals("文学一言")) {
        String result=get("https://v1.hitokoto.cn/?c=d");
        try {
            JSONObject json=new JSONObject(result);
            String hitokoto=json.getString("hitokoto");
            String from=json.getString("from");
            String c=hitokoto+"\n--"+from;
            sendm(qun,c);
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(text.equals("原创一言")) {
        String result=get("https://v1.hitokoto.cn/?c=e");
        try {
            JSONObject json=new JSONObject(result);
            String hitokoto=json.getString("hitokoto");
            String from=json.getString("from");
            String c=hitokoto+"\n--"+from;
            sendm(qun,c);
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(text.equals("网络一言")) {
        String result=get("https://v1.hitokoto.cn/?c=f");
        try {
            JSONObject json=new JSONObject(result);
            String hitokoto=json.getString("hitokoto");
            String from=json.getString("from");
            String c=hitokoto+"\n--"+from;
            sendm(qun,c);
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(text.equals("其他一言")) {
        String result=get("https://v1.hitokoto.cn/?c=g");
        try {
            JSONObject json=new JSONObject(result);
            String hitokoto=json.getString("hitokoto");
            String from=json.getString("from");
            String c=hitokoto+"\n--"+from;
            sendm(qun,c);
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(text.equals("影视一言")) {
        String result=get("https://v1.hitokoto.cn/?c=h");
        try {
            JSONObject json=new JSONObject(result);
            String hitokoto=json.getString("hitokoto");
            String from=json.getString("from");
            String c=hitokoto+"\n--"+from;
            sendm(qun,c);
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(text.equals("诗词一言")) {
        String result=get("https://v1.hitokoto.cn/?c=i");
        try {
            JSONObject json=new JSONObject(result);
            String hitokoto=json.getString("hitokoto");
            String from=json.getString("from");
            String c=hitokoto+"\n--"+from;
            sendm(qun,c);
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(text.equals("网易一言")) {
        String result=get("https://v1.hitokoto.cn/?c=j");
        try {
            JSONObject json=new JSONObject(result);
            String hitokoto=json.getString("hitokoto");
            String from=json.getString("from");
            String c=hitokoto+"\n--"+from;
            sendm(qun,c);
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(text.equals("哲学一言")) {
        String result=get("https://v1.hitokoto.cn/?c=k");
        try {
            JSONObject json=new JSONObject(result);
            String hitokoto=json.getString("hitokoto");
            String from=json.getString("from");
            String c=hitokoto+"\n--"+from;
            sendm(qun,c);
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(text.equals("机灵一言")) {
        String result=get("https://v1.hitokoto.cn/?c=l");
        try {
            JSONObject json=new JSONObject(result);
            String hitokoto=json.getString("hitokoto");
            String from=json.getString("from");
            String c=hitokoto+"\n--"+from;
            sendm(qun,c);
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
}