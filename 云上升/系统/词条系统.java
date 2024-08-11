public void 词条(Object data) {
    String text=data.content;
    String qun=data.talker;
    String wxid=data.sendTalker;
    if(text.equals("毒鸡汤")) {
        String result=get("https://api.shadiao.pro/du");
        try {
            JSONObject json=new JSONObject(result);
            JSONObject data=json.getJSONObject("data");
            String type=data.getString("type");
            String text=data.getString("text");
            String c=text+"\n--"+type;
            sendm(qun,c);
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(text.equals("彩虹屁")) {
        String result=get("https://api.shadiao.pro/chp");
        try {
            JSONObject json=new JSONObject(result);
            JSONObject data=json.getJSONObject("data");
            String type=data.getString("type");
            String text=data.getString("text");
            String c=text+"\n--"+type;
            sendm(qun,c);
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(text.equals("朋友圈文案")) {
        String result=get("https://api.shadiao.pro/pyq");
        try {
            JSONObject json=new JSONObject(result);
            JSONObject data=json.getJSONObject("data");
            String type=data.getString("type");
            String text=data.getString("text");
            String c=text+"\n--"+type;
            sendm(qun,c);
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(text.equals("疯狂星期四")) {
        String result=get("https://api.shadiao.pro/kfc");
        try {
            JSONObject json=new JSONObject(result);
            JSONObject data=json.getJSONObject("data");
            String type=data.getString("type");
            String text=data.getString("text");
            String c=text+"\n--"+type;
            sendm(qun,c);
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(text.equals("动画文案")) {
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
    if(text.equals("漫画文案")) {
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
    if(text.equals("游戏文案")) {
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
    if(text.equals("文学文案")) {
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
    if(text.equals("原创文案")) {
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
    if(text.equals("网络文案")) {
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
    if(text.equals("其他文案")) {
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
    if(text.equals("影视文案")) {
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
    if(text.equals("诗词文案")) {
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
    if(text.equals("网易文案")) {
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
    if(text.equals("哲学文案")) {
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
    if(text.equals("机灵文案")) {
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