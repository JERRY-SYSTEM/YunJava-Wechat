public void 搜索(Object data) {
    String text=data.content;
    String qun=data.talker;
    String wxid=data.sendTalker;
    if(text.startsWith("搜索图片")) {
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
    if(text.startsWith("搜索应用")) {
        text=text.substring(4);
        if(text.equals("")) {
            return;
        }
        检查 临时标志=new 检查();
        临时标志.时间=System.currentTimeMillis();
        String jsonData=jsonPost("https://yybadaccess.3g.qq.com/v2/dynamicard3","{\"head\":{\"cmd\":\"dynamicard3\",\"authInfo\":{\"businessId\":\"AuthName\"},\"hostAppInfo\":{\"scene\":\"search_result\"}},\"body\":{\"bid\":\"yybhome\",\"listS\":{\"keyword\":{\"repStr\":[\""+text+"\"]}}}}");
        JSONObject jsonObject=new JSONObject(jsonData);
        int retcode=jsonObject.getInt("ret");
        String result2="";
        if(retcode==0) {
            JSONObject dataObject = jsonObject.getJSONObject("data");
            JSONArray jsonArray = dataObject.getJSONArray("components");
            if(jsonArray==null||jsonArray.length()==0) {
                sendm(qun,"未搜到");
                return;
            }
            JSONObject data = jsonArray.getJSONObject(0);
            JSONObject dataObject = data.getJSONObject("data");
            JSONArray jsonArray=dataObject.getJSONArray("itemData");
            for(int i=0; i<jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String download =jsonObject.getString("download_url");
                String name = jsonObject.getString("name").replace("\n","");
                String developer = jsonObject.getString("developer");
                String versionname = jsonObject.getString("version_name");
                String apksize = jsonObject.getString("apk_size");
                String averagerating = jsonObject.getString("average_rating");
                String editorintro = jsonObject.getString("editor_intro");
                String updatetime = jsonObject.getString("update_time");
                result2+=(i+1)+"."+name+"\n";
                临时标志.数量=i+1;
                int strB=i+1;
                String b=String.valueOf(strB);
                写(qun,"名称",b,name);
                写(qun,"下载",b,download);
                写(qun,"版本",b,versionname);
                写(qun,"评分",b,averagerating);
                写(qun,"大小",b,apksize);
                写(qun,"公司",b,developer);
                写(qun,"简介",b,editorintro);
                写(qun,"时间",b,updatetime);
            }
        }
        地图.put(qun+wxid,临时标志);
        选择=35;
        result2=result2+"\n请发送序号来进行查看\n两分钟内有效";
        sendm(qun,result2);
    }
    if(选择==35&&text.matches("^[1-9]\\d*$")) {
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
        String 名称=取(qun,"名称",text);
        String 下载=取(qun,"下载",text);
        String 公司=取(qun,"公司",text);
        String 评分=取(qun,"评分",text);
        String 大小=取(qun,"大小",text);
        String 版本=取(qun,"版本",text);
        String 简介=取(qun,"简介",text);
        String 时间=取(qun,"时间",text);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(Long.parseLong(时间)*1000);
        String formattedDate = sdf.format(date);
        String c="名称："+名称+"\n"
                +"版本："+版本+"\n"
                +"公司："+公司+"\n"
                +"评分："+评分+"\n"
                +"大小："+FileFormatConversion(Long.parseLong(大小))+"\n"
                +"时间："+formattedDate+"\n"
                +"简介："+简介+"\n"
                +下载;
        sendm(qun,c);
    }
}