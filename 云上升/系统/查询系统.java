public void 查询(Object data) {
    String text=data.content;
    String qun=data.talker;
    String wxid=data.sendTalker;
    if(text.startsWith("天气")) { //无心
        text=text.substring(2);
        if(text.equals("")) {
            return;
        }
        new Thread(new Runnable() {
            public void run() {
                String u="";
                String r="";
                String uo="";
                String f="";
                int j=1;
                try {
                    f=get("https://m.baidu.com/sf?pd=life_compare_weather&openapi=1&dspName=iphone&from_sf=1&resource_id=4982&oe=utf8&alr=1&multiDayWeather=1&title=4天天气预报&query="+text+"天气");
                    u =f.substring(f.lastIndexOf("\"weatherList\":[{\"date\":\""),f.lastIndexOf("\"}],\"calendarText\":[\""));
                    String op="{"+u.substring(u.lastIndexOf("\"weatherList\":[{\"date\":\""),u.lastIndexOf(",\"lowHighTempDay\""))+"}";
                    JSONObject menu1=new JSONObject(op);
                    JSONArray menu2=menu1.getJSONArray("weatherList");
                    for(int i=0; i<menu2.length(); i++) {
                        try {
                            JSONObject info=menu2.getJSONObject(i);
                            if(7>=j) {
                                String time=info.getString("monthDayWeek");
                                String tq=info.getString("weatherAndTempDesc");
                                String fx=info.getString("wind_power_day");
                                String smf=info.getString("wind_direction_day");
                                String rc=info.getString("sunriseTime");
                                String rl=info.getString("sunsetTime");
                                String pic1=info.getString("iconKey");
                                String pic =pic1.replace("url(","").replace(")","");
                                r+=time+":"+tq+"℃\n("+smf+")"+fx+"\n(日出:"+rc+";日落:"+rl+")\n\n";
                            }
                            String ut=info.getString("wind_power_night");
                            j++;
                        } catch(e) {
                        }
                    }
                    j=j-1;
                    sendm(qun,"中国天气->\n当前地区("+text+")天气\n"+r+"共查询到了"+j+"天后的天气");
                } catch(e) {
                    try {
                        try {
                            f=get("https://m.baidu.com/s?from=1001192y&word="+text+"天气&sa=is_br_0");
                            u =f.substring(f.lastIndexOf("\"itemList\":[[{\""),f.lastIndexOf(",\"titleLine\":"));
                        } catch(e) {
                            try {
                                f=get("https://m.baidu.com/s?from=1001192y&word="+text+"省天气&sa=is_br_0");
                                u =f.substring(f.lastIndexOf("\"itemList\":[[{\""),f.lastIndexOf(",\"titleLine\":"));
                            } catch(e) {
                            }
                        }
                        u ="{"+u.replace("[[","[").replace("]]","]")+"}";
                        JSONObject menu1=new JSONObject(u);
                        JSONArray menu2=menu1.getJSONArray("itemList");
                        for(int i = 0; i<1; i++) {
                            try {
                                JSONObject info=menu2.getJSONObject(i);
                                uo=info.getString("cityWeather");
                            } catch(e) {
                            }
                        }
                        f=get("https://m.baidu.com/sf?pd=life_compare_weather&openapi=1&dspName=iphone&from_sf=1&resource_id=4982&oe=utf8&alr=1&multiDayWeather=1&title=4天天气预报&query="+uo+"天气");
                        u =f.substring(f.lastIndexOf("\"weatherList\":[{\"date\":\""),f.lastIndexOf("\"}],\"calendarText\":[\""));
                        String op="{"+u.substring(u.lastIndexOf("\"weatherList\":[{\"date\":\""),u.lastIndexOf(",\"lowHighTempDay\""))+"}";
                        JSONObject menu1=new JSONObject(op);
                        JSONArray menu2=menu1.getJSONArray("weatherList");
                        for(int i=0; i<menu2.length(); i++) {
                            try {
                                JSONObject info=menu2.getJSONObject(i);
                                if(7>=j) {
                                    String time=info.getString("monthDayWeek");
                                    String tq=info.getString("weatherAndTempDesc");
                                    String fx=info.getString("wind_power_day");
                                    String smf=info.getString("wind_direction_day");
                                    String rc=info.getString("sunriseTime");
                                    String rl=info.getString("sunsetTime");
                                    String pic1=info.getString("iconKey");
                                    String pic =pic1.replace("url(","").replace(")","");
                                    r+=time+":"+tq+"℃\n("+smf+")"+fx+"\n(日出:"+rc+";日落:"+rl+")\n\n";
                                }
                                String ut=info.getString("wind_power_night");
                                j++;
                            } catch(e) {
                            }
                        }
                        j=j-1;
                        sendm(qun,"中国天气->\n当前找到了("+text+"->"+uo+")天气\n"+r+"共查询到了"+j+"天后的天气");
                    } catch(e) { //开始搜索世界天气
                        try {
                            String op="{"+f.substring(f.lastIndexOf("\"dayForecastList\":")+0,f.lastIndexOf(",\"hourInfoData\":"))+"}";
                            f=op;
                            JSONObject menu1=new JSONObject(op);
                            JSONArray menu2=menu1.getJSONArray("dayForecastList");
                            for(int i=0; i<menu2.length(); i++) {
                                try {
                                    JSONObject info=menu2.getJSONObject(i);
                                    if(7>=j) {
                                        String time=info.getString("weather_night");
                                        String tq=info.getString("wind_direction_night");
                                        String smf=info.getString("wind_power_night");
                                        String u1=info.getString("date");
                                        String u2=info.getString("word");
                                        String rc=info.getString("sunriseTime");
                                        String rl=info.getString("sunsetTime");
                                        String pic=info.getString("listWeatherIcon");
                                        r+=u2+u1+":"+time+"\n"+tq+"("+smf+")\n(日出:"+rc+";日落:"+rl+")\n\n";
                                    }
                                    String ut=info.getString("wind_power_night");
                                    j++;
                                } catch(e) {
                                }
                            }
                            j=j-1;
                            sendm(qun,"世界天气->\n当前找到了("+text+")的天气\n"+r+"共找到了"+j+"天后的天气");
                        } catch(e) {
                            sendm(qun,"没有找到");
                        }
                    }
                }
                j=1;
                break;
            }
        }).start();
    }
    if(text.startsWith("百度")) { //无心
        text=text.substring(2);
        if(text.equals("")) {
            return;
        }
        new Thread(new Runnable() {
            public void run() {
                String f=get2("https://baike.baidu.com/item/"+text+"");
                try {
                    String u =f.substring(f.lastIndexOf("<meta name=\"description\" content=\"")+34,f.lastIndexOf("\"><meta name=\"keywords\""));
                    if(u.equals("百度百科是一部内容开放、自由的网络百科全书，旨在创造一个涵盖所有领域知识，服务所有互联网用户的中文知识性百科全书。在这里你可以参与词条编辑，分享贡献你的知识。")) {
                        try {
                            String o =f.substring(f.lastIndexOf("                var sourceUrl = \"")+33,f.lastIndexOf("\";                var fromPage ="));
                            sendm(qun,"请求限制,请完成验证:\n"+o);
                            break;
                        } catch(e) {
                            sendm(qun,"抓取限制验证错误");
                            break;
                        }
                    }
                    sendWeb(qun,"百度"+text,u+"",getAvatar(wxid),"https://baike.baidu.com/item/"+text);
                    break;
                } catch(e) {
                    sendm(qun,"没有找到");
                    break;
                }
            }
        }).start();
    }
    if(text.startsWith("王者战力")) {
        text=text.substring(4);
        if(text.equals("")) {
            return;
        }
        Pattern pattern = Pattern.compile("(安卓|苹果)\\s*(\\S+)");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            String type = matcher.group(1)
                          .replace("安卓","a")
                          .replace("苹果","i");
            String hero = matcher.group(2);
            String wzqqzl = get("https://www.sapi.run/hero/select.php?hero="+hero+"&type="+type+"qq");
            String wzwxzl = get("https://www.sapi.run/hero/select.php?hero="+hero+"&type="+type+"wx");
            try {
                JSONObject json = new JSONObject(wzqqzl);
                Integer code = json.getInt("code");
                if(code==200) {
                    JSONObject data = json.getJSONObject("data");
                    String name = data.getString("name");
                    String alias = data.getString("alias");
                    String platform = data.getString("platform");
                    String area = data.getString("area");
                    String areaPower = data.getString("areaPower");
                    String city = data.getString("city");
                    String cityPower = data.getString("cityPower");
                    String province = data.getString("province");
                    String provincePower = data.getString("provincePower");
                    String guobiao = data.getString("guobiao");
                    String updatetime = data.getString("updatetime");
                    text = "英雄:"+alias+"\n"
                           +"提示:结果仅供参考\n"
                           +" -------------------------\n"
                           +"系统:"+platform+"\n"
                           +"国标:"+guobiao+"\n"
                           +"省标:"+provincePower+"-"+province+"\n"
                           +"市标:"+cityPower+"-"+city+"\n"
                           +"县标:"+areaPower+"-"+area+"\n"
                           +"时间:"+updatetime+"\n";
                } else if(code==400) {
                    String msg = json.getString("msg");
                    text = "系统:"+matcher.group(1)+"-扣扣区\n提示:"+msg+"\n"
                           +" -------------------------\n";
                } else {
                    text = "系统:"+matcher.group(1)+"-扣扣区\n提示:出现未知错误\n"
                           +" -------------------------\n";
                }
                JSONObject json1 = new JSONObject(wzwxzl);
                Integer code1 = json.getInt("code");
                if(code1==200) {
                    JSONObject data = json1.getJSONObject("data");
                    String name = data.getString("name");
                    String alias = data.getString("alias");
                    String platform = data.getString("platform");
                    String area = data.getString("area");
                    String areaPower = data.getString("areaPower");
                    String city = data.getString("city");
                    String cityPower = data.getString("cityPower");
                    String province = data.getString("province");
                    String provincePower = data.getString("provincePower");
                    String guobiao = data.getString("guobiao");
                    String updatetime = data.getString("updatetime");
                    text = text+" -------------------------\n"
                           +"系统:"+platform+"\n"
                           +"国标:"+guobiao+"\n"
                           +"省标:"+provincePower+"-"+province+"\n"
                           +"市标:"+cityPower+"-"+city+"\n"
                           +"县标:"+areaPower+"-"+area+"\n"
                           +"时间:"+updatetime;
                } else if(code1==400) {
                    String msg = json.getString("msg");
                    text = text+"系统:"+matcher.group(1)+"-微信区\n提示:"+msg;
                } else {
                    text = text+"系统:"+matcher.group(1)+"-微信区\n提示:出现未知错误";
                }
                sendm(qun,text);
            } catch(e) {
                sendm(qun,"错误,请稍候再试");
                return;
            }
        }
    }
    if(text.startsWith("今日油价")) { //M.
        text=text.substring(4);
        if(text.equals("")) {
            return;
        }
        String result=get("https://api.qqsuu.cn/api/dm-oilprice?prov="+text);
        try {
            JSONObject json=new JSONObject(result);
            Integer code = json.getInt("code");
            String msg = json.getString("msg");
            if(code==200) {
                JSONObject data=json.getJSONObject("data");
                String prov=data.getString("prov");
                String p0=data.getString("p0");
                String p89=data.getString("p89");
                String p92=data.getString("p92");
                String p95=data.getString("p95");
                String p98=data.getString("p98");
                String time=data.getString("time");
                text="查询地区:"+prov+"\n"
                     +"0#柴油:"+p0+"元/L\n"
                     +"89#汽油:"+p89+"元/L\n"
                     +"92#汽油:"+p92+"元/L\n"
                     +"95#汽油:"+p95+"元/L\n"
                     +"98#汽油:"+p98+"元/L\n"
                     +time;
                sendm(qun,text);
            } else if(code==250) {
                sendm(qun,"只支持查询省级");
            } else {
                sendm(qun,msg);
            }
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(选择==11&&text.matches("^[1-9]\\d*$")) {
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
        String name=取(qun,"名称",text);
        String mainIngredient=取(qun,"食材",text);
        String url=取(qun,"链接",text);
        String cover=取(qun,"封面",text);
        try {
            sendWeb(qun,name,mainIngredient,cover,url);
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(text.startsWith("菜谱查询")) {
        String name=text.substring(4).trim();
        if(name.equals("")) {
            return;
        }
        检查 临时标志=new 检查();
        临时标志.时间=System.currentTimeMillis();
        String result2="";
        String jsonData=get("https://tools-api.2345.com/tools/v1/menu/category/list?type=3&page=1&pageSize=15&name="+name);
        try {
            JSONObject json = new JSONObject(jsonData);
            JSONObject data=json.getJSONObject("data");
            JSONArray resultList = data.getJSONArray("list");
            if(resultList==null||resultList.length()==0) {
                sendm(qun,"未搜到");
                return;
            }

            for(int i = 0; i <resultList.length(); i++) {
                JSONObject jsonObject = resultList.getJSONObject(i);
                String name = jsonObject.getString("name");
                String category = jsonObject.getString("category");
                String url = jsonObject.getString("url");
                String pic = jsonObject.getString("pic");
                String mainIngredient = jsonObject.getString("mainIngredient");
                result2+=(i+1)+"."+name+"--"+category+"\n";
                int strB=i+1;
                String b=String.valueOf(strB);
                写(qun,"名称",b,name);
                写(qun,"食材",b,mainIngredient);
                写(qun,"链接",b,url);
                写(qun,"封面",b,pic);
                临时标志.数量=i+1;
            }
            地图.put(qun+wxid, 临时标志);
            result2=result2+"\n请发送序号来进行查看\n两分钟内有效";
            选择=11;
            sendm(qun,result2);
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(选择==12&&text.matches("^[1-9]\\d*$")) {
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
        String name=取(qun,"名称",text);
        String mainIngredient=取(qun,"类型",text);
        String url=取(qun,"链接",text);
        String cover=取(qun,"封面",text);
        try {
            sendWeb(qun,name,mainIngredient,cover,url);
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(text.startsWith("宠物查询")) {
        String name=text.substring(4).trim();
        if(name.equals("")) {
            return;
        }
        检查 临时标志=new 检查();
        临时标志.时间=System.currentTimeMillis();
        String result2="";
        String jsonData=get("https://api.7kcat.com/Chongwuapi/search?name="+name+"&webname=波奇宠物&page=1");
        try {
            JSONObject json = new JSONObject(jsonData);
            JSONArray resultList = json.getJSONArray("data");
            if(resultList==null||resultList.length()==0) {
                sendm(qun,"未搜到");
                return;
            }

            for(int i = 0; i <resultList.length(); i++) {
                JSONObject jsonObject = resultList.getJSONObject(i);
                String name = jsonObject.getString("name");
                String sort = jsonObject.getString("sort");
                String url = jsonObject.getString("url");
                String img = jsonObject.getString("img");
                result2+=(i+1)+"."+name+"--"+sort+"\n";
                int strB=i+1;
                String b=String.valueOf(strB);
                写(qun,"名称",b,name);
                写(qun,"类型",b,sort);
                写(qun,"链接",b,url);
                写(qun,"封面",b,img);
                临时标志.数量=i+1;
            }
            地图.put(qun+wxid, 临时标志);
            result2=result2+"\n请发送序号来进行点歌\n两分钟内有效";
            选择=12;
            sendm(qun,result2);
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(选择==13&&text.matches("^[1-9]\\d*$")) {
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
        String name=取(qun,"名称",text);
        String content=取(qun,"内容",text);
        String url=取(qun,"链接",text);
        try {
            sendWeb(qun,name,content,getAvatar(wxid),url);
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(text.startsWith("扩展名查询")) {
        String name=text.substring(5).trim();
        if(name.equals("")) {
            return;
        }
        检查 临时标志=new 检查();
        临时标志.时间=System.currentTimeMillis();
        String result2="";
        String jsonData=jsonPost("https://api.7kcat.com/Wenjiankuozhanming/getlist","{\"word\":\""+name+"\",\"type\":\"全部\",\"page\":1}");
        try {
            JSONObject json = new JSONObject(jsonData);
            JSONArray resultList = json.getJSONArray("data");
            if(resultList==null||resultList.length()==0) {
                sendm(qun,"未搜到");
                return;
            }

            for(int i = 0; i <resultList.length(); i++) {
                JSONObject jsonObject = resultList.getJSONObject(i);
                String name = jsonObject.getString("name");
                String type = jsonObject.getString("type");
                String intro = jsonObject.getString("intro");
                String url = jsonObject.getString("url");
                String content = jsonObject.getString("content");
                result2+=(i+1)+"."+name.replace(".","")+"--"+type+"\n";
                int strB=i+1;
                String b=String.valueOf(strB);
                写(qun,"名称",b,intro);
                写(qun,"内容",b,content);
                写(qun,"链接",b,url);
                临时标志.数量=i+1;
            }
            地图.put(qun+wxid, 临时标志);
            result2=result2+"\n请发送序号来进行查看\n两分钟内有效";
            选择=13;
            sendm(qun,result2);
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
}