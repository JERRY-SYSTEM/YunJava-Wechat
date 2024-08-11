public void 搜索(Object data) {
    String text=data.content;
    String qun=data.talker;
    String wxid=data.sendTalker;
    if(text.startsWith("堆糖搜图")) {
        text=text.substring(4);
        if(text.equals("")) {
            return;
        }
        检查 临时标志=new 检查();
        临时标志.时间=System.currentTimeMillis();
        String jsonData=get("https://www.duitang.com/napi/blog/list/by_search/?include_fields=album&kw="+text+"&start=24&locale=zh&app_version=110");
        JSONObject jsonObject=new JSONObject(jsonData);
        int retcode=jsonObject.getInt("status");
        String result2="";
        if(retcode==1) {
            JSONObject dataObject=jsonObject.getJSONObject("data");
            JSONArray jsonArray=dataObject.getJSONArray("object_list");
            if(jsonArray==null||jsonArray.length()==0) {
                sendm(qun,"未搜到");
                return;
            }
            for(int i=0; i<jsonArray.length(); i++) {
                JSONObject data=jsonArray.getJSONObject(i);
                JSONObject album = data.getJSONObject("album");
                String name = album.getString("name").replace("\n","");
                String like_count = album.getString("like_count");
                JSONObject photo = data.getJSONObject("photo");
                String path = photo.getString("path");
                String msg=data.getString("msg");
                if(name.equals("N")) {
                    name=msg;
                }
                String favorite_count =data.getString("favorite_count");
                String add_datetime=data.getString("add_datetime");
                String add_datetime_pretty=data.getString("add_datetime_pretty");
                result2+=(i+1)+"."+name+" "+add_datetime_pretty+"\n喜欢:"+like_count+"次 收藏:"+favorite_count+"人\n";
                临时标志.数量=i+1;
                int strB=i+1;
                String b=String.valueOf(strB);
                写(qun,"数据",b,path);
            }
        }
        地图.put(qun+wxid,临时标志);
        选择=34;
        result2=result2+"\n请发送序号来进行查看\n两分钟内有效";
        sendm(qun,result2);
    }
    if(选择==34&&text.matches("^[1-9]\\d*$")) {
        if(地图==null||!地图.containsKey(qun+wxid)) {
            return;
        }
        检查 资讯=地图.get(qun+wxid);
        if(资讯.数量<Integer.parseInt(text)) {
            return;
        }
        if(System.currentTimeMillis()/1000-资讯.时间/1000>120) {
            地图.remove(qun+wxid);
            选择=0;
            return;
        }
        String id=取(qun,"数据",text);
        sendPic(qun,id);
    }
    if(选择==33&&text.matches("^[1-9]\\d*$")) {
        if(地图==null||!地图.containsKey(qun+wxid)) {
            return;
        }
        检查 资讯=地图.get(qun+wxid);
        if(资讯.数量<Integer.parseInt(text)) {
            return;
        }
        if(System.currentTimeMillis()/1000-资讯.时间/1000>120) {
            地图.remove(qun+wxid);
            选择=0;
            return;
        }
        String id=取(qun,"数据",text);
        sendm(qun,id);
    }
    if(text.startsWith("搜索内容")) {
        text=text.substring(4);
        if(text.equals("")) {
            return;
        }
        检查 临时标志=new 检查();
        临时标志.时间=System.currentTimeMillis();
        String jsonData=get("https://gogo.webbillion.cn/api/search?q="+text+"&p=1");
        JSONObject jsonObject=new JSONObject(jsonData);
        String result2="";
        JSONArray jsonArray=jsonObject.getJSONArray("result");
        if(jsonArray==null||jsonArray.length()==0) {
            sendm(qun,"未搜到");
            return;
        }
        for(int i=0; i<jsonArray.length(); i++) {
            JSONObject data=jsonArray.getJSONObject(i);
            String name=data.getString("name").replace("          VIDEO","视频");
            String url=data.getString("url");
            String desc=data.getString("desc");
            result2+=(i+1)+"."+name+"\n";
            临时标志.数量=i+1;
            int strB=i+1;
            String b=String.valueOf(strB);
            写(qun,"数据",b,"《"+name+"》\n\n"+desc+"\n\n"+url);
        }
        地图.put(qun+wxid,临时标志);
        选择=33;
        result2=result2+"\n请发送序号来进行查看\n两分钟内有效";
        sendm(qun,result2);
    }
}