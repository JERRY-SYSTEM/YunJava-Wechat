public void 音乐(Object data){
String text=data.content;
String qun=data.talker;
String wxid=data.sendTalker;
if(选择==1&&text.matches("^[1-9]\\d*$")){
if(地图==null||!地图.containsKey(qun+wxid)){return;}
检查 资讯=地图.get(qun+wxid);
if(资讯.数量<Integer.parseInt(text)){return;}
String musicName=资讯.名称;
if(System.currentTimeMillis()/1000-资讯.时间/1000>120){
地图.remove(qun+wxid);
选择=0;return;}
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
if(text.startsWith("抖音点歌")){
text=text.substring(4).trim();
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

if(选择==2&&text.matches("^[1-9]\\d*$")){
if(地图==null||!地图.containsKey(qun+wxid)){return;}
检查 资讯=地图.get(qun+wxid);
if(资讯.数量<Integer.parseInt(text)){return;}
String musicName=资讯.名称;
if(System.currentTimeMillis()/1000-资讯.时间/1000>120){
地图.remove(qun+wxid);
选择=0;return;}
String c=取(qun,"音乐",text);
String song=取(qun,"标题",text);
String singer=取(qun,"作者",text);
String pmid=取(qun,"封面",text);
String media_mid=取(qun,"专辑",text);
String jsonData=get("http://u.y.qq.com/cgi-bin/musicu.fcg?format=json&data={\"req_0\":{\"module\":\"vkey.GetVkeyServer\",\"method\":\"CgiGetVkey\",\"param\":{\"guid\":\"7714352534\",\"songmid\":[\""+c+"\"],\"songtype\":[0],\"uin\":\"1052906040\",\"loginflag\":1,\"platform\":\"20\"}},\"comm\":{\"uin\":\"1052906040\",\"format\":\"json\",\"ct\":24,\"cv\":0}}","");
try{
JSONObject jsonObject=new JSONObject(jsonData);
JSONObject req0Object = jsonObject.getJSONObject("req_0");
JSONObject dataObject = req0Object.getJSONObject("data");
JSONArray sipArray = dataObject.getJSONArray("sip");
String firstSip = sipArray.getString(0);
JSONArray midurlinfoArray = dataObject.getJSONArray("midurlinfo");
JSONObject midurlinfoObject = midurlinfoArray.getJSONObject(0);
String purl = midurlinfoObject.getString("purl");
if(purl.equals("")){
song=song+"(付费)";
String url2=httppost1("https://u.y.qq.com/cgi-bin/musicu.fcg","","{\"comm\":{\"uin\":\"1052906040\",\"authst\":\"\",\"mina\":1,\"appid\":1109523715,\"ct\":29},\"urlReq0\":{\"module\":\"vkey.GetVkeyServer\",\"method\":\"CgiGetVkey\",\"param\":{\"guid\":\"7982463958\",\"songmid\":[\""+c+"\"],\"songtype\":[0],\"filename\":[\"RS02"+media_mid+".mp3\"],\"uin\":\"1052906040\",\"loginflag\":1,\"platform\":\"23\",\"h5to\":\"speed\"}}}");
if(url2.contains("\"purl\":\"")){
int index3 = url2.lastIndexOf("\"purl\":\"");
String text3 = url2.substring(index3 + 8);
int rd3 = text3.indexOf("\",\"errtype\":\"");
firstSip="http://sjy.stream.qqmusic.qq.com/";
purl=u解(text3.substring(0,rd3));
}}
String cover="http://y.gtimg.cn/music/photo_new/T002R500x500M000"+pmid+".jpg";
sendMusic(qun,firstSip+purl,singer,song,cover);
}catch(e){
sendm(qun,"错误,请稍候再试");return;}
}
if(text.startsWith("QQ点歌")){
String name=text.substring(4).trim();
if(name.equals("")){return;}
检查 临时标志=new 检查();
临时标志.名称=name;
临时标志.时间=System.currentTimeMillis();
String result2="";
String jsonData=get("http://u.y.qq.com/cgi-bin/musicu.fcg?data={\"comm\":{\"ct\":\"19\",\"cv\":\"1882\",\"uin\":\"0\"},\"searchMusic\":{\"method\":\"DoSearchForQQMusicDesktop\",\"module\":\"music.search.SearchCgiService\",\"param\":{\"grp\":1,\"num_per_page\":15,\"page_num\":1,\"query\":\""+name+"\",\"search_type\":0}}}");
try{
JSONObject jsonObject=new JSONObject(jsonData);
if(jsonObject.get("code")==0){
JSONObject searchMusicObject = jsonObject.getJSONObject("searchMusic");
JSONObject dataObject = searchMusicObject.getJSONObject("data");
JSONObject bodyObject = dataObject.getJSONObject("body");
JSONObject songObject = bodyObject.getJSONObject("song");
JSONArray songList = songObject.getJSONArray("list");
if(songList==null||songList.length()==0){sendm(qun,"未搜到");return;}
for(int i=0;i<songList.length();i++){
JSONObject data=songList.getJSONObject(i);
JSONObject album = data.getJSONObject("album");
String pmid = album.getString("pmid");
JSONArray singerArray = data.getJSONArray("singer");
JSONObject firstSinger = singerArray.getJSONObject(0);
String firstSingerName = firstSinger.getString("name");
String name=data.getString("name");
String mid=data.getString("mid");
String file=data.getString("file");
JSONObject json10=new JSONObject(file);
String media_mid=json10.get("media_mid");//专辑
int strB=i+1;
String b=String.valueOf(strB);
写(qun,"音乐",b,mid);
写(qun,"标题",b,name);
写(qun,"作者",b,firstSingerName);
写(qun,"封面",b,pmid);
写(qun,"专辑",b,media_mid);
result2+=(i+1)+"."+name+"--"+firstSingerName+"\n";
临时标志.数量=i+1;
}
地图.put(qun+wxid, 临时标志);
result2=result2+"\n请发送序号来进行点歌\n两分钟内有效";
选择=2;}
sendm(qun,result2);
}catch(e){
sendm(qun,"错误,请稍候再试");
return;}
}
if(选择==3&&text.matches("^[1-9]\\d*$")){
if(地图==null||!地图.containsKey(qun+wxid)){return;}
检查 资讯=地图.get(qun+wxid);
if(资讯.数量<Integer.parseInt(text)){return;}
String musicName=资讯.名称;
if(System.currentTimeMillis()/1000-资讯.时间/1000>120){
地图.remove(qun+wxid);
选择=0;return;}
String id=取(qun,"音乐",text);
String jsonData=get("https://apis.jxcxin.cn/api/kuwo?apiKey=72e66153f61ceebd04dddec1c0730e1b&type=json&id="+id);
try{
JSONObject jsonObject=new JSONObject(jsonData);
if(jsonObject.get("code")==200){
JSONObject data=jsonObject.getJSONObject("data");
String name=data.getString("name");
String author=data.getString("author");
String pic=get("http://artistpicserver.kuwo.cn/pic.web?type=rid_pic&pictype=url&size=500&rid="+id);
String url=data.getString("url");
sendMusic(qun,url,author,name,pic);
}
}catch(e){
sendm(qun,"错误,请稍候再试");
return;}
}
if(text.startsWith("酷我点歌")){
String name=text.substring(4).trim();
if(name.equals("")){return;}
检查 临时标志=new 检查();
临时标志.时间=System.currentTimeMillis();
String result2="";
String jsonData=get("http://search.kuwo.cn/r.s?client=kt&all="+name+"&pn=0&rn=15&uid=794762570&ver=kwplayer_ar_9.2.2.1&vipver=1&show_copyright_off=1&newver=1&ft=music&cluster=0&strategy=2012&encoding=utf8&rformat=json&vermerge=1&mobi=1&issubtitle=1&_=1657713784395");
try{
JSONObject jsonObject=new JSONObject(jsonData);
JSONArray jsonArray=jsonObject.getJSONArray("abslist");
if(jsonArray==null||jsonArray.length()==0){
sendm(qun,"未搜到");
return;}
for (int i=0;i<jsonArray.length();i++) {
JSONObject data=jsonArray.getJSONObject(i);
String SONGNAME=data.getString("SONGNAME");
String FARTIST=data.getString("FARTIST");
String MUSICRID=data.getString("MUSICRID");
String DC_TARGETID=data.getString("DC_TARGETID");
result2+=(i+1)+"."+SONGNAME+"--"+FARTIST+"\n";
int strB=i+1;
String b=String.valueOf(strB);
写(qun,"音乐",b,DC_TARGETID);
临时标志.数量=i+1;}
地图.put(qun+wxid, 临时标志);
result2=result2+"\n请发送序号来进行点歌\n两分钟内有效";
选择=3;
sendm(qun,result2);
}catch(e){
sendm(qun,"错误,请稍候再试");
return;}
}
if(选择==4&&text.matches("^[1-9]\\d*$")){
if(地图==null||!地图.containsKey(qun+wxid)){return;}
检查 资讯=地图.get(qun+wxid);
if(资讯.数量<Integer.parseInt(text)){return;}
String musicName=资讯.名称;
if(System.currentTimeMillis()/1000-资讯.时间/1000>120){
地图.remove(qun+wxid);
选择=0;return;}
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
if(text.startsWith("VIP点歌")){
text=text.substring(5).trim();
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
选择=4;
} else {
result2=jsonObject.getString("msg");
}
sendm(qun,result2);
}catch(e){
sendm(qun,"错误,请稍候再试");
return;}
}

if(text.startsWith("点歌")){
text=text.substring(2).trim();
if(text.equals("")){return;}
String result2="";
String jsonData=get("http://u.y.qq.com/cgi-bin/musicu.fcg?data={\"comm\":{\"ct\":\"19\",\"cv\":\"1882\",\"uin\":\"0\"},\"searchMusic\":{\"method\":\"DoSearchForQQMusicDesktop\",\"module\":\"music.search.SearchCgiService\",\"param\":{\"grp\":1,\"num_per_page\":1,\"page_num\":1,\"query\":\""+text+"\",\"search_type\":0}}}");
try{
JSONObject jsonObject=new JSONObject(jsonData);
if(jsonObject.get("code")==0){
JSONObject searchMusicObject = jsonObject.getJSONObject("searchMusic");
JSONObject dataObject = searchMusicObject.getJSONObject("data");
JSONObject bodyObject = dataObject.getJSONObject("body");
JSONObject songObject = bodyObject.getJSONObject("song");
JSONArray songList = songObject.getJSONArray("list");
if(songList==null||songList.length()==0){sendm(qun,"未搜到");return;}
for(int i=0;i<1;i++){
JSONObject data=songList.getJSONObject(i);
JSONObject album = data.getJSONObject("album");
String pmid = album.getString("pmid");
JSONArray singerArray = data.getJSONArray("singer");
JSONObject firstSinger = singerArray.getJSONObject(0);
String firstSingerName = firstSinger.getString("name");
String name=data.getString("name");
String mid=data.getString("mid");
String file=data.getString("file");
JSONObject json10=new JSONObject(file);
String media_mid=json10.get("media_mid");//专辑
String jsonData=get("http://u.y.qq.com/cgi-bin/musicu.fcg?format=json&data={\"req_0\":{\"module\":\"vkey.GetVkeyServer\",\"method\":\"CgiGetVkey\",\"param\":{\"guid\":\"7714352534\",\"songmid\":[\""+mid+"\"],\"songtype\":[0],\"uin\":\"1052906040\",\"loginflag\":1,\"platform\":\"20\"}},\"comm\":{\"uin\":\"1052906040\",\"format\":\"json\",\"ct\":24,\"cv\":0}}","");
try{
JSONObject jsonObject=new JSONObject(jsonData);
JSONObject req0Object = jsonObject.getJSONObject("req_0");
JSONObject dataObject = req0Object.getJSONObject("data");
JSONArray sipArray = dataObject.getJSONArray("sip");
String firstSip = sipArray.getString(0);
JSONArray midurlinfoArray = dataObject.getJSONArray("midurlinfo");
JSONObject midurlinfoObject = midurlinfoArray.getJSONObject(0);
String purl = midurlinfoObject.getString("purl");
if(purl.equals("")){
name=name+"(付费)";
String url2=httppost1("https://u.y.qq.com/cgi-bin/musicu.fcg","","{\"comm\":{\"uin\":\"1052906040\",\"authst\":\"\",\"mina\":1,\"appid\":1109523715,\"ct\":29},\"urlReq0\":{\"module\":\"vkey.GetVkeyServer\",\"method\":\"CgiGetVkey\",\"param\":{\"guid\":\"7982463958\",\"songmid\":[\""+mid+"\"],\"songtype\":[0],\"filename\":[\"RS02"+media_mid+".mp3\"],\"uin\":\"1052906040\",\"loginflag\":1,\"platform\":\"23\",\"h5to\":\"speed\"}}}");
if(url2.contains("\"purl\":\"")){
int index3 = url2.lastIndexOf("\"purl\":\"");
String text3 = url2.substring(index3 + 8);
int rd3 = text3.indexOf("\",\"errtype\":\"");
firstSip="http://sjy.stream.qqmusic.qq.com/";
purl=u解(text3.substring(0,rd3));
}}
String cover="http://y.gtimg.cn/music/photo_new/T002R500x500M000"+pmid+".jpg";
sendMusic(qun,firstSip+purl,firstSingerName,name,cover);
}catch(e){
sendm(qun,"错误,请稍候再试");return;}
}
}
}catch(e){
sendm(qun,"错误,请稍候再试");return;}
}
}