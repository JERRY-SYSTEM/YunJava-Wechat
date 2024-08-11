public void 音乐(Object data){
String text=data.content;
String qun=data.talker;
String wxid=data.sendTalker;
if(text.startsWith("抖音点歌")){
String text=text.substring(4).trim();
if(text.equals("")){return;}
检查 临时标志=new 检查();
临时标志.时间=System.currentTimeMillis();
String result2="";
String result=douyinget("https://api5-normal-lf.amemv.com/webcast/interact/ktv/search_song/?query="+text+"&page=1&count=12&mode=6&search_id=&query_source=0&webcast_sdk_version=3290&webcast_language=zh&webcast_locale=zh_CN&webcast_gps_access=1&current_network_quality_info={}&device_score=9.4431&address_book_access=1&user_id=1410026832147773&is_pad=false&is_android_pad=0&is_landscape=false&carrier_region=CN&iid=3054915011497075&device_id=2755837670459812&ac=5g&channel=xiaomi_1128_64&aid=1128&app_name=aweme&version_code=280800&version_name=28.8.0&device_platform=android&os=android&ssmix=a&device_type=22081212C&device_brand=Redmi&language=zh&os_api=34&os_version=14&resolution=1220*2624&dpi=424&cpu_support64=true&host_abi=arm64-v8a&is_guest_mode=0&app_type=normal&minor_status=0&appTheme=light&is_preinstall=0&need_personal_recommend=1&is_android_fold=0");
JSONObject json = new JSONObject(result);
JSONObject json_data = json.getJSONObject("data");
JSONArray json_data_musiclist = json_data.getJSONArray("musiclist");
for(int h = 0; h < json_data_musiclist.length(); h++){
JSONObject json_data_musiclist_h = json_data_musiclist.getJSONObject(h);
String 标题 = json_data_musiclist_h.getString("title").replace("；",", ");
String 作者 = json_data_musiclist_h.getString("author").replace("；",", ");
JSONObject json_data_musiclist_h_full_track = json_data_musiclist_h.getJSONObject("full_track");
String 链接 = json_data_musiclist_h_full_track.getString("url");
String 封面 = json_data_musiclist_h.getString("new_cover_url");
result2+=(h+1)+"."+标题+"--"+作者+"\n";
临时标志.数量=h+1;
String b=String.valueOf(h+1);
写(qun,"标题",b,标题);
写(qun,"作者",b,作者);
写(qun,"封面",b,封面);
写(qun,"链接",b,链接);
}
地图.put(qun+wxid,临时标志);
result2=result2+"\n请发送序号来进行点歌\n两分钟内有效";
sendm(qun,result2);
选择=1;
}


if(选择==1&&text.matches("^[1-9]\\d*$")){
if(地图==null||!地图.containsKey(qun+wxid)){
return;}
检查 资讯=地图.get(qun+wxid);
if(资讯.数量<Integer.parseInt(text)){
return;}
String musicName=资讯.名称;
if(System.currentTimeMillis()/1000-资讯.时间/1000>120){
地图.remove(qun+wxid);
选择=0;
return;}
try{
String 标题=取(qun,"标题",text);
String 作者=取(qun,"作者",text);
String 封面=取(qun,"封面",text);
String 链接=取(qun,"链接",text);
sendMusic(qun,链接,作者,标题,封面);
}catch(e){
sendm(qun,"错误,请稍候再试");
return;}
}
if(text.startsWith("QQ点歌"))
{
String text=text.substring(4).trim();
if(text.equals("")){return;}
检查 临时标志=new 检查();
临时标志.名称=text;
临时标志.时间=System.currentTimeMillis();
String result2="";
String jsonData=get("https://api.lolimi.cn/API/yiny/?word="+text+"&num=15");
try{
JSONObject jsonObject=new JSONObject(jsonData);
if(jsonObject.get("code")==200){
JSONArray jsonArray=jsonObject.getJSONArray("data");
for (int i=0;i<jsonArray.length();i++) {
JSONObject data=jsonArray.getJSONObject(i);
String song=data.getString("song");
String singer=data.getString("singer");
result2+=(i+1)+"."+song+"--"+singer+"\n";
临时标志.数量=i+1;
}
地图.put(qun+wxid, 临时标志);
result2=result2+"\n请发送序号来进行点歌\n两分钟内有效";
选择=2;
} else {
result2=jsonObject.getString("msg");
}
sendm(qun,result2);
}catch(e){
sendm(qun,"错误,请稍候再试");
return;}
}
if(选择==2&&text.matches("^[1-9]\\d*$"))
{
if(地图==null||!地图.containsKey(qun+wxid)){
return;}
检查 资讯=地图.get(qun+wxid);
if(资讯.数量<Integer.parseInt(text)){
return;}
String musicName=资讯.名称;
if(System.currentTimeMillis()/1000-资讯.时间/1000>120){
地图.remove(qun+wxid);
选择=0;
return;}
String jsonData=get("https://api.lolimi.cn/API/yiny/?word="+musicName+"&n="+text);
try{
JSONObject jsonObject=new JSONObject(jsonData);
JSONObject data=jsonObject.getJSONObject("data");
String song=data.getString("song");
String singer=data.getString("singer");
String cover=data.getString("cover");
String link=data.getString("link");
String url=data.getString("url");
sendMusic(qun,url,singer,song,cover);
}catch(e){
sendm(qun,"错误,请稍候再试");
return;}
}

if(text.startsWith("点歌")){
String text=text.substring(2).trim();
if(text.equals("")){return;}
String result2="";
String jsonData=get("https://api.lolimi.cn/API/yiny/?word="+text+"&n=1");
try{
JSONObject jsonObject=new JSONObject(jsonData);
if(jsonObject.get("code")==200){
JSONObject data=jsonObject.getJSONObject("data");
String song=data.getString("song");
String singer=data.getString("singer");
String cover=data.getString("cover");
String link=data.getString("link");
String url=data.getString("url");
sendMusic(qun,url,singer,song,cover);
} else {
sendm(qun,"未搜到");
}
}catch(e){
sendm(qun,"错误,请稍候再试");
return;}
}
}