public String post(String urlPath, String cookie,String data) {
    StringBuffer buffer = new StringBuffer();
    InputStreamReader isr = null;
    CookieManager cookieManager = new CookieManager();
    CookieHandler.setDefault(cookieManager);
    try {
        URL url = new URL(urlPath);
        uc = (HttpURLConnection) url.openConnection();
        uc.setDoInput(true);
        uc.setDoOutput(true);
        uc.setConnectTimeout(2000000);// 设置连接主机超时（单位：毫秒）
        uc.setReadTimeout(2000000);// 设置从主机读取数据超时（单位：毫秒）
        uc.setRequestMethod("POST");
        uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        uc.setRequestProperty("Cookie",cookie);
        uc.setRequestProperty("Referer","https://music.163.com");

        uc.getOutputStream().write(data.getBytes("UTF-8"));
        uc.getOutputStream().flush();
        uc.getOutputStream().close();
        isr = new InputStreamReader(uc.getInputStream(), "utf-8");
        BufferedReader reader = new BufferedReader(isr); //缓冲
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line + "\n");
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (null != isr) {
                isr.close();
            }
        } catch (IOException e) {
            Toast("错误:\n"+e);
        }
    }
    if(buffer.length()==0)return buffer.toString();
    buffer.delete(buffer.length()-1,buffer.length());
    return buffer.toString();
}
public static final String iv = "0102030405060708";

String mima="91789428";

public static final String modulus = "00e0b509f6259df8642dbc35662901477df22677ec152b5ff68ace615bb7b725152b3ab17a876aea8a5aa76d2e417629ec4ee341f56135fccf695280104e0312ecbda92557c93870114af6c9d05c4f7f0c3685b7a46bee255932575cce10b424d813cfe4875d3e82047b97ddef52741d546b8e289dc6935b3ece0462db0a22b8e7";
public static final String pubKey="010001";
public static final String presetKey = "0CoJUm6Qyw8W8jud";
public static final String keys = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

public static String createSecretKey() {
    StringBuilder key = new StringBuilder();
    for (int i = 0; i < 16; i++) {
        double index = Math.floor(Math.random() * keys.length());
        key.append(keys.charAt((int) index));
    }
    return key.toString();
}

public static String zFill(String str) {
    StringBuilder strBuilder = new StringBuilder(str);
    while (strBuilder.length() < 256) {
        strBuilder.insert(0, "0");
    }
    str = strBuilder.toString();
    return str;
}

public static String rsaEncrypt(String text) {
    // 反转字符串
    text = new StringBuffer(text).reverse().toString();

    BigInteger biText = new BigInteger(strToHex(text), 16);
    BigInteger biEx = new BigInteger(pubKey, 16);
    BigInteger biMod = new BigInteger(modulus, 16);
    BigInteger biRet = biText.modPow(biEx, biMod);

    return zFill(biRet.toString(16));

}

public static String strToHex(String s) {
    StringBuilder str = new StringBuilder();
    for (int i = 0; i < s.length(); i++) {
        int ch = s.charAt(i);
        String s4 = Integer.toHexString(ch);
        str.append(s4);
    }
    return str.toString();
}


public String aesEncrypt( String content, String key,String iv) {
    String result = null;
    try {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] bytes;
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES"),
                    new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8)));
        bytes = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
        result = Base64.getEncoder().encodeToString(bytes);

    } catch (Exception e) {
        e.printStackTrace();
    }
    return result;
}


public static String[] weapiEncrypt(String content) {
    String[] result = new String[2];
    String key = createSecretKey();
    String encText = aesEncrypt(aesEncrypt(content, presetKey, iv), key, iv);
    String encSecKey = rsaEncrypt(key);
    result[0] = encText;
    result[1] = encSecKey;
    return result;
}
public String GetNeteaseMusicSongUrl(String id,int br) {
    try {
        /*String jsonData=get("http://43.138.145.42:5201/v1/wy/songurl?id="+id+"&br=jymaster");
        JSONArray jsonArray = new JSONArray(jsonData);
        jsonObject = jsonArray.getJSONObject(0);
        JSONArray data = jsonObject.getJSONArray("data");
        JSONObject dataObject = data.getJSONObject(0);
        if(dataObject.isNull("url")) {
            return GetNeteaseMusicSongUrl(id);
        }
        String SongUrl = dataObject.getString("url");
        if(SongUrl.equals("")) {
            return GetNeteaseMusicSongUrl(id);
        }*/
        //return SongUrl.replace("authSecret=","authSecret\\u003d");
        return "https://music-api2.cenguigui.cn/?wy&id="+id+"&type=song&format=mp3&level=jymaster";
    } catch(Exception e) {
        Toast("网易音乐"+id+"直链1获取失败\n"+e);
        return GetNeteaseMusicSongUrl(id);
    }
    Toast("网易音乐"+id+"直链1获取失败");
    return GetNeteaseMusicSongUrl(id);
}
public String getLocation(String downloadUrl) {
    String na;
    if(downloadUrl == null || "".equals(downloadUrl)) {
        na= "不存在";
    } else {
        URL url = new URL(downloadUrl);
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setFollowRedirects(true);
            conn.getResponseCode();
            na=conn.getHeaderField("Location");
            //Toast(na);
            // conn.setFollowRedirects(false);
            //if(na.equals(downloadUrl)) ;
            if(na==null) na=conn.getURL().toString();
            //Toast(na);
        } catch (IOException e) {
            na= "未知";
        } finally {
            conn.disconnect();
        }
    }
    return na;
}


public String GetNeteaseMusicSongUrl(String id) {
    try {
        String SongUrl=getLocation("http://music.163.com/song/media/outer/url?id="+id+".mp3");
        if(!SongUrl.contains("music.126.net/")) {
            Toast("网易音乐"+id+"直链2获取失败");
            return "";
        }
        return SongUrl.replace("authSecret=","authSecret\\u003d");;
    } catch(Throwable e) {
        Toast("网易音乐"+id+"直链2获取失败\n"+e);
        return ""+e;
    }
}
public static String SearchNeteaseMusicList(String searchName) {
    if(searchName != null && !searchName.isEmpty()) {
        try {
            JSONObject object=new JSONObject();
            object.put("s", searchName);
            object.put("total", true);
            object.put("type", 1);
            object.put("limit", 6);
            object.put("offset", 0);
            String data=object.toString();
            String[] r2=weapiEncrypt(data);
            String post=post("http://interface.music.163.com/weapi/search/get","","params="+java.net.URLEncoder.encode(r2[0])+"&encSecKey="+r2[1]);
            JSONObject json=new JSONObject(post);
            if(!json.has("result")||!json.get("result").has("songs")) {
                return "未搜到";
            }
            JSONArray array = json.get("result").get("songs");

            JSONArray mJson = new JSONArray();
            for(int i = 0; i < array.length(); i++) {
                JSONObject TempJson = new JSONObject();
                JSONObject mRawJson = array.getJSONObject(i);
                try {
                    TempJson.put("name", mRawJson.getString("name"));
                    TempJson.put("mid", mRawJson.getString("id"));

                    JSONArray array2=mRawJson.getJSONArray("artists");
                    String singer="";
                    for(int h=0; h<array2.length(); h++) {
                        if(h==0) singer = array2.getJSONObject(h).getString("name");
                        else singer = singer+"/"+array2.getJSONObject(h).getString("name");
                    }
                    TempJson.put("singer", singer);
                    JSONObject t=  mRawJson.getJSONObject("album");
                    TempJson.put("album", t.getString("name"));
                    //TempJson.put("pic", t.getString("picUrl"));
                    mJson.put(TempJson);
                } catch(Exception w) {
                    //Toast(w+"");
                    continue;
                }
            }
            JSONObject returnObj = new JSONObject();
            returnObj.put("data", mJson);
            return returnObj.toString();
        } catch(Exception e) {
            // Toast(""+e);
            return("遇到错误\n" + e.toString());
        }
    } else {
        return("未输入点歌内容");
    }
}
public void 音乐(Object data) {
    String text=data.content;
    String qun=data.talker;
    String wxid=data.sendTalker;
    if(选择==1&&text.matches("^[1-9]\\d*$")) {
        if(地图==null||!地图.containsKey(qun+wxid)) {
            return;
        }
        检查 资讯=地图.get(qun+wxid);
        if(资讯.数量<Integer.parseInt(text)) {
            return;
        }
        String musicName=资讯.名称;
        if(System.currentTimeMillis()/1000-资讯.时间/1000>120) {
            地图.remove(qun+wxid);
            选择=0;
            return;
        }
        try {
            String 标题=取(qun,"标题",text);
            String 作者=取(qun,"作者",text);
            String 封面=取(qun,"封面",text);
            String 链接=取(qun,"链接",text);
            String 歌词=取(qun,"歌词",text);
            if(歌词.equals("")) {
                歌词="";
            } else {
                歌词="";
                //歌词=get(歌词).replaceAll("\\<.*?\\>","").replaceAll("\\[(.*?),(.*?)\\]", "[$1]");
                }
            sendMusic(qun,链接,作者,标题,封面,歌词);
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(text.startsWith("抖音点歌")) {
        text=text.substring(4).trim();
        if(text.equals("")) {
            return;
        }
        检查 临时标志=new 检查();
        临时标志.时间=System.currentTimeMillis();
        String result2="";
        String result=douyinget("https://api5-normal-lf.amemv.com/webcast/interact/ktv/search_song/?query="+text+"&page=1&count=6&mode=6&search_id=&query_source=0&webcast_sdk_version=3290&webcast_language=zh&webcast_locale=zh_CN&webcast_gps_access=1&current_network_quality_info={}&device_score=9.4431&address_book_access=1&user_id=1410026832147773&is_pad=false&is_android_pad=0&is_landscape=false&carrier_region=CN&iid=3054915011497075&device_id=2755837670459812&ac=5g&channel=xiaomi_1128_64&aid=1128&app_name=aweme&version_code=280800&version_name=28.8.0&device_platform=android&os=android&ssmix=a&device_type=22081212C&device_brand=Redmi&language=zh&os_api=34&os_version=14&resolution=1220*2624&dpi=424&cpu_support64=true&host_abi=arm64-v8a&is_guest_mode=0&app_type=normal&minor_status=0&appTheme=light&is_preinstall=0&need_personal_recommend=1&is_android_fold=0");
        JSONObject json = new JSONObject(result);
        JSONObject json_data = json.getJSONObject("data");
        JSONArray json_data_musiclist = json_data.getJSONArray("musiclist");
        for(int h = 0; h < json_data_musiclist.length(); h++) {
            JSONObject json_data_musiclist_h = json_data_musiclist.getJSONObject(h);
            JSONArray artistsArray = json_data_musiclist_h.getJSONArray("lyric_url_list");
            String 歌词="";
            if(artistsArray != null && artistsArray.length() != 0) {
                歌词 = artistsArray.getString(0);
            }

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
            写(qun,"歌词",b,歌词);
        }
        地图.put(qun+wxid,临时标志);
        result2=result2+"\n请发送序号来进行点歌\n两分钟内有效";
        sendm(qun,result2);
        选择=1;
    }

    if(选择==2&&text.matches("^[1-9]\\d*$")) {
        if(地图==null||!地图.containsKey(qun+wxid)) {
            return;
        }
        检查 资讯=地图.get(qun+wxid);
        if(资讯.数量<Integer.parseInt(text)) {
            return;
        }
        String musicName=资讯.名称;
        if(System.currentTimeMillis()/1000-资讯.时间/1000>120) {
            地图.remove(qun+wxid);
            选择=0;
            return;
        }
        String c=取(qun,"音乐",text);
        String song=取(qun,"标题",text);
        String singer=取(qun,"作者",text);
        String pmid=取(qun,"封面",text);
        String media_mid=取(qun,"专辑",text);
        //String jsonData=get("http://u.y.qq.com/cgi-bin/musicu.fcg?format=json&data={\"req_0\":{\"module\":\"vkey.GetVkeyServer\",\"method\":\"CgiGetVkey\",\"param\":{\"guid\":\"7714352534\",\"songmid\":[\""+c+"\"],\"songtype\":[0],\"loginflag\":1,\"platform\":\"20\"}},\"comm\":{\"format\":\"json\",\"ct\":24,\"cv\":0}}","uin=3449496653;qm_keyst=Q_H_L_63k3NduOcbt3t3IhPSaNF0C88d8oHPUW8aa8UB8UG6T4xsq5I0lkraJ2V_jIAZIOqA-P8Lp9RV1OmX98qBhTwPgY7;");
        try {
            /*JSONObject jsonObject=new JSONObject(jsonData);
            JSONObject req0Object = jsonObject.getJSONObject("req_0");
            JSONObject dataObject = req0Object.getJSONObject("data");
            JSONArray sipArray = dataObject.getJSONArray("sip");
            String firstSip = sipArray.getString(0);
            JSONArray midurlinfoArray = dataObject.getJSONArray("midurlinfo");
            JSONObject midurlinfoObject = midurlinfoArray.getJSONObject(0);
            String purl = midurlinfoObject.getString("purl");
            if(purl.equals("")) {
                song=song+"(试听)";
                String url2=httppost1("https://u.y.qq.com/cgi-bin/musicu.fcg","","{\"comm\":{\"uin\":\"1052906040\",\"authst\":\"\",\"mina\":1,\"appid\":1109523715,\"ct\":29},\"urlReq0\":{\"module\":\"vkey.GetVkeyServer\",\"method\":\"CgiGetVkey\",\"param\":{\"guid\":\"7982463958\",\"songmid\":[\""+c+"\"],\"songtype\":[0],\"filename\":[\"RS02"+media_mid+".mp3\"],\"uin\":\"1052906040\",\"loginflag\":1,\"platform\":\"23\",\"h5to\":\"speed\"}}}");
                if(url2.contains("\"purl\":\"")) {
                    int index3 = url2.lastIndexOf("\"purl\":\"");
                    String text3 = url2.substring(index3 + 8);
                    int rd3 = text3.indexOf("\",\"errtype\":\"");
                    firstSip="http://sjy.stream.qqmusic.qq.com/";
                    purl=u解(text3.substring(0,rd3));
                }
            }*/
            String firstSip="https://music-api2.cenguigui.cn/?qq&id="+c+"&type=song&format=mp3&level=jymaster";
            String cover="http://y.gtimg.cn/music/photo_new/T002R500x500M000"+pmid+".jpg";
            String songLyric=qqLyricget("https://c.y.qq.com/lyric/fcgi-bin/fcg_query_lyric_new.fcg?format=json&nobase64=1&g_tk=5381&songmid="+c);
            JSONObject Lyric = new JSONObject(songLyric);
            if(Lyric.has("lyric")) {
                String json_lyric = Lyric.getString("lyric");
                sendMusic(qun,firstSip,singer,song,cover,json_lyric);
            } else {
                sendMusic(qun,firstSip,singer,song,cover,"");
            }
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(text.startsWith("QQ点歌")) {
        String name=text.substring(4).trim();
        if(name.equals("")) {
            return;
        }
        检查 临时标志=new 检查();
        临时标志.名称=name;
        临时标志.时间=System.currentTimeMillis();
        String result2="";
        String jsonData=get("http://u.y.qq.com/cgi-bin/musicu.fcg?data={\"comm\":{\"ct\":\"19\",\"cv\":\"1882\",\"uin\":\"0\"},\"searchMusic\":{\"method\":\"DoSearchForQQMusicDesktop\",\"module\":\"music.search.SearchCgiService\",\"param\":{\"grp\":1,\"num_per_page\":6,\"page_num\":1,\"query\":\""+name+"\",\"search_type\":0}}}");
        try {
            JSONObject jsonObject=new JSONObject(jsonData);
            if(jsonObject.get("code")==0) {
                JSONObject searchMusicObject = jsonObject.getJSONObject("searchMusic");
                JSONObject dataObject = searchMusicObject.getJSONObject("data");
                JSONObject bodyObject = dataObject.getJSONObject("body");
                JSONObject songObject = bodyObject.getJSONObject("song");
                JSONArray songList = songObject.getJSONArray("list");
                if(songList==null||songList.length()==0) {
                    sendm(qun,"未搜到");
                    return;
                }
                for(int i=0; i<songList.length(); i++) {
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
                选择=2;
            }
            sendm(qun,result2);
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(选择==5&&text.matches("^[1-9]\\d*$")) {
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
        String mid=取(qun,"音乐",text);
        String singer=取(qun,"歌手",text);
        String songname=取(qun,"歌曲",text);
        try {
            String html=get("https://y.music.163.com/m/song?id="+mid);
            String cover="https://p1.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg";
            int index1=html.indexOf("<meta property=\"og:image\" content=");
            if (index1!=-1) {
                cover=html.substring(index1+"<meta property=\"og:image\" content=\"".length(),html.indexOf("\" />",index1))+"?param=114x114";
            }
            String audio=GetNeteaseMusicSongUrl(mid,128000);
            String songLyric=post("https://music.163.com/api/song/lyric?_nmclfl=1","","id="+mid+"&tv=-1&lv=-1&rv=-1&kv=-1");
            JSONObject Lyric = new JSONObject(songLyric);
            JSONObject lrc = Lyric.getJSONObject("lrc");
            if(lrc.has("lyric")) {
                String lyric = lrc.getString("lyric");
                sendMusic(qun,audio,singer,songname,cover,lyric);
            } else {
                sendMusic(qun,audio,singer,songname,cover,"");
            }
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(text.startsWith("网易点歌")) {
        String name=text.substring(4).trim();
        if(name.equals("")) {
            return;
        }
        检查 临时标志=new 检查();
        临时标志.时间=System.currentTimeMillis();
        String result2="";
        String jsonData=SearchNeteaseMusicList(name);
        try {
            if(!jsonData.contains("data")) {
                sendm(qun,"搜索\""+name+"\"时\n"+jsonData);
                return;
            }
            JSONObject jsonObject=new JSONObject(jsonData);
            JSONArray jsonArray=jsonObject.getJSONArray("data");
            for (int i=0; i<jsonArray.length(); i++) {
                JSONObject data=jsonArray.getJSONObject(i);
                String singer=data.getString("singer");
                String name=data.getString("name");
                String mid=data.getString("mid");
                result2+=(i+1)+"."+name+"--"+singer+"\n";
                int strB=i+1;
                String b=String.valueOf(strB);
                写(qun,"音乐",b,mid);
                写(qun,"歌手",b,singer);
                写(qun,"歌曲",b,name);
                临时标志.数量=i+1;
            }
            地图.put(qun+wxid, 临时标志);
            result2=result2+"\n请发送序号来进行点歌\n两分钟内有效";
            选择=5;
            sendm(qun,result2);
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(选择==3&&text.matches("^[1-9]\\d*$")) {
        if(地图==null||!地图.containsKey(qun+wxid)) {
            return;
        }
        检查 资讯=地图.get(qun+wxid);
        if(资讯.数量<Integer.parseInt(text)) {
            return;
        }
        String musicName=资讯.名称;
        if(System.currentTimeMillis()/1000-资讯.时间/1000>120) {
            地图.remove(qun+wxid);
            选择=0;
            return;
        }
        String id=取(qun,"音乐",text);
        String author=取(qun,"歌手",text);
        String name=取(qun,"歌曲",text);
        String quality=取(qun,"品质",text);
        String jsonData=KWGET("http://nmobi.kuwo.cn/mobi.s?f=web&source=kwplayerhd_ar_4.3.0.8_tianbao_T1A_qirui.apk&type=convert_url_with_sign&rid="+id+"&br="+quality);
       //String jsonData=post("http://ghyinyue.com//core/Ajax.php","","id="+id+"&type=flac&p=kw&fun=getMusicDownloadUrl");
        try {
            JSONObject jsonObject=new JSONObject(jsonData);
            if(jsonObject.get("code")==200) {
                JSONObject data=jsonObject.getJSONObject("data");
                String pic=get("http://artistpicserver.kuwo.cn/pic.web?type=rid_pic&pictype=url&size=500&rid="+id);
                String url=data.getString("url");
                String songLyric=pdget("https://bd-api.kuwo.cn/api/service/mv/lyric?musicId="+id+"&uid=-1&token=");
                JSONObject Lyric = new JSONObject(songLyric);
                JSONObject Lyric_data = Lyric.getJSONObject("data");
                if(Lyric_data.has("content")) {
                    String Lyric_data_content = Lyric_data.getString("content");
                    Base64.Decoder decoder = Base64.getDecoder();
                    byte[] decodedBytes = decoder.decode(Lyric_data_content);
                    sendMusic(qun,url,author,name,pic,new String(decodedBytes));
                } else {
                    sendMusic(qun,url,author,name,pic,"");
                }
            }
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(text.startsWith("酷我点歌")) {
        String name=text.substring(4).trim();
        if(name.equals("")) {
            return;
        }
        检查 临时标志=new 检查();
        临时标志.时间=System.currentTimeMillis();
        String result2="";
        String jsonData=get("http://search.kuwo.cn/r.s?client=kt&all="+name+"&pn=0&rn=6&uid=794762570&ver=kwplayer_ar_9.2.2.1&vipver=1&show_copyright_off=1&newver=1&ft=music&cluster=0&strategy=2012&encoding=utf8&rformat=json&vermerge=1&mobi=1&issubtitle=1&_=1657713784395");
        try {
            JSONObject jsonObject=new JSONObject(jsonData);
            JSONArray jsonArray=jsonObject.getJSONArray("abslist");
            if(jsonArray==null||jsonArray.length()==0) {
                sendm(qun,"未搜到");
                return;
            }
            for (int i=0; i<jsonArray.length(); i++) {
                JSONObject data=jsonArray.getJSONObject(i);
                String SONGNAME=data.getString("SONGNAME");
                String FARTIST=data.getString("FARTIST").replace("&","/");
                String MUSICRID=data.getString("MUSICRID");
                String DC_TARGETID=data.getString("DC_TARGETID");
                String MINFO=data.getString("MINFO");
                result2+=(i+1)+"."+SONGNAME+"--"+FARTIST+"\n";
                int strB=i+1;
                String b=String.valueOf(strB);
                String regex="bitrate:(\\d+),format:(\\w+)";
                Pattern pattern=Pattern.compile(regex);
                Matcher matcher=pattern.matcher(MINFO);
                if(matcher.find()) {
                    int bitrate=Integer.parseInt(matcher.group(1));
                    String format=matcher.group(2);
                    写(qun,"品质",b,bitrate+"k"+format);
                }
                写(qun,"音乐",b,DC_TARGETID);
                写(qun,"歌手",b,FARTIST);
                写(qun,"歌曲",b,SONGNAME);
                临时标志.数量=i+1;
            }
            地图.put(qun+wxid, 临时标志);
            result2=result2+"\n请发送序号来进行点歌\n两分钟内有效";
            选择=3;
            sendm(qun,result2);
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(选择==8&&text.matches("^[1-9]\\d*$")) {
        if(地图==null||!地图.containsKey(qun+wxid)) {
            return;
        }
        检查 资讯=地图.get(qun+wxid);
        if(资讯.数量<Integer.parseInt(text)) {
            return;
        }
        String musicName=资讯.名称;
        if(System.currentTimeMillis()/1000-资讯.时间/1000>120) {
            地图.remove(qun+wxid);
            选择=0;
            return;
        }
        String id=取(qun,"音乐",text);
        String author=取(qun,"歌手",text);
        String name=取(qun,"歌曲",text);
        String quality=取(qun,"品质",text);
        String jsonData=KWGET("http://nmobi.kuwo.cn/mobi.s?f=web&source=kwplayerhd_ar_4.3.0.8_tianbao_T1A_qirui.apk&type=convert_url_with_sign&rid="+id+"&br="+quality);
        //String jsonData=post("http://ghyinyue.com//core/Ajax.php","","id="+id+"&type=flac&p=kw&fun=getMusicDownloadUrl");
        try {
            JSONObject jsonObject=new JSONObject(jsonData);
            if(jsonObject.get("code")==200) {
                JSONObject data=jsonObject.getJSONObject("data");
                String pic=get("http://artistpicserver.kuwo.cn/pic.web?type=rid_pic&pictype=url&size=500&rid="+id);
                String url=data.getString("url");
                String songLyric=pdget("https://bd-api.kuwo.cn/api/service/mv/lyric?musicId="+id+"&uid=-1&token=");
                JSONObject Lyric = new JSONObject(songLyric);
                JSONObject Lyric_data = Lyric.getJSONObject("data");
                if(Lyric_data.has("content")) {
                    String Lyric_data_content = Lyric_data.getString("content");
                    Base64.Decoder decoder = Base64.getDecoder();
                    byte[] decodedBytes = decoder.decode(Lyric_data_content);
                    sendMusic(qun,url,author,name,pic,new String(decodedBytes));
                } else {
                    sendMusic(qun,url,author,name,pic,"");
                }
            }
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(text.startsWith("波点点歌")) {
        String name=text.substring(4).trim();
        if(name.equals("")) {
            return;
        }
        检查 临时标志=new 检查();
        临时标志.时间=System.currentTimeMillis();
        String result2="";
        String jsonData=pdget("https://bd-api.kuwo.cn/api/search/music/list?pn=0&rn=6&keyword="+name+"&correct=1&uid=-1");
        try {
            JSONObject json = new JSONObject(jsonData);
            Integer code = json.getInt("code");
            JSONObject data=json.getJSONObject("data");
            JSONArray resultList = data.getJSONArray("resultList");
            if(resultList==null||resultList.length()==0) {
                sendm(qun,"未搜到");
                return;
            }

            for(int i = 0; i <resultList.length(); i++) {
                JSONObject jsonObject = resultList.getJSONObject(i);
                String artist = jsonObject.getString("artist").replace("&","/");
                Integer id = jsonObject.getInt("id");
                String songName = jsonObject.getString("songName");
                JSONArray audiosArray = jsonObject.getJSONArray("audios");
                result2+=(i+1)+"."+songName+"--"+artist+"\n";
                int strB=i+1;
                String b=String.valueOf(strB);
                if (audiosArray.length() > 0) {
                    for(int i = 0; i <audiosArray.length(); i++) {
                        JSONObject firstAudio = audiosArray.getJSONObject(i);
                        String bitrate = firstAudio.getString("bitrate");
                        String format = firstAudio.getString("format");
                        if (Integer.parseInt(bitrate) <= 4000) {
                            写(qun,"品质",b,bitrate+"k"+format);
                            break;
                        }
                    }


                }
                写(qun,"音乐",b,id);
                写(qun,"歌手",b,artist);
                写(qun,"歌曲",b,songName);
                临时标志.数量=i+1;
            }
            地图.put(qun+wxid, 临时标志);
            result2=result2+"\n请发送序号来进行点歌\n两分钟内有效";
            选择=8;
            sendm(qun,result2);
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    /*if(选择==6&&text.matches("^[1-9]\\d*$")) {
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
        String mid=取(qun,"音乐",text);
        String singer=取(qun,"歌手",text);
        String songname=取(qun,"歌曲",text);
        String cover=取(qun,"封面",text);
        String lyric="";
        String jsonp=post("https://music.gdstudio.xyz/api.php?callback=jQuery111309607005563109974_1716008181403","","types=url&id="+mid+"&source=spotify&br=320");
        String jsonp1=get("https://music-api.gdstudio.xyz/api.php?types=pic&id="+cover+"&source=spotify&size=300");
        String songLyric=get("https://music-api.gdstudio.xyz/api.php?types=lyric&source=spotify&id="+mid);
        try {
            JSONObject jsonObject1=new JSONObject(jsonp1);
            String url1=jsonObject1.getString("url");
            String json=jsonp.substring(jsonp.indexOf('{'),jsonp.lastIndexOf('}')+1);
            JSONObject jsonObject = new JSONObject(json);
            String url=jsonObject.getString("url");
            JSONObject Lyric = new JSONObject(songLyric);
            if(Lyric.has("lyric")) {
                lyric = Lyric.getString("lyric");
            }
            sendMusic(qun,"https://music.gdstudio.xyz/"+url,singer,songname,url1,lyric);
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(text.startsWith("声破天点歌")) {
        String name=text.substring(5).trim();
        if(name.equals("")) {
            return;
        }
        检查 临时标志=new 检查();
        临时标志.时间=System.currentTimeMillis();
        String result2="";
        String jsonp=post("https://music.gdstudio.xyz/api.php?callback=jQuery111306275575862724738_1716001564160","","types=search&count=6&source=spotify&pages=1&name="+name);
        try {
            String json=jsonp.substring(jsonp.indexOf('['),jsonp.lastIndexOf(']')+1);
            JSONArray jsonArray=new JSONArray(json);
            if(jsonArray==null||jsonArray.length()==0) {
                sendm(qun,"未搜到");
                return;
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                String id=jsonObject.getString("id");
                String name=jsonObject.getString("name");
                JSONArray artistsArray=jsonObject.getJSONArray("artist");
                String artist=artistsArray.getString(0);
                String picId=jsonObject.getString("pic_id");
                result2+=(i+1)+"."+name+"--"+artist+"\n";
                int strB=i+1;
                String b=String.valueOf(strB);
                写(qun,"音乐",b,id);
                写(qun,"封面",b,picId);
                写(qun,"歌曲",b,name);
                写(qun,"歌手",b,artist);
                临时标志.数量=i+1;
            }
            地图.put(qun+wxid, 临时标志);
            result2=result2+"\n请发送序号来进行点歌\n两分钟内有效";
            选择=6;
            sendm(qun,result2);
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(选择==7&&text.matches("^[1-9]\\d*$")) {
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
        String mid=取(qun,"音乐",text);
        String singer=取(qun,"歌手",text);
        String songname=取(qun,"歌曲",text);
        String cover=取(qun,"封面",text);
        String lyric="";
        String jsonp=get("https://music-api.gdstudio.xyz/api.php?types=url&id="+mid+"&source=tidal&br=999");
        String jsonp1=get("https://music-api.gdstudio.xyz/api.php?types=pic&id="+cover+"&source=tidal&size=300");
        String songLyric=get("https://music-api.gdstudio.xyz/api.php?types=lyric&source=tidal&id="+mid);
        try {
            JSONObject jsonObject=new JSONObject(jsonp);
            String url=jsonObject.getString("url");
            JSONObject jsonObject1=new JSONObject(jsonp1);
            String url1=jsonObject1.getString("url");
            JSONObject Lyric = new JSONObject(songLyric);
            if(Lyric.has("lyric")) {
                lyric = Lyric.getString("lyric");
            }
            sendMusic(qun,url,singer,songname,url1,lyric);
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(text.startsWith("潮汐点歌")) {
        String name=text.substring(4).trim();
        if(name.equals("")) {
            return;
        }
        检查 临时标志=new 检查();
        临时标志.时间=System.currentTimeMillis();
        String result2="";
        String jsonp=post("https://music.gdstudio.xyz/api.php?callback=jQuery111306275575862724738_1716001564160","","types=search&count=6&source=tidal&pages=1&name="+name);
        try {
            String json=jsonp.substring(jsonp.indexOf('['),jsonp.lastIndexOf(']')+1);
            JSONArray jsonArray=new JSONArray(json);
            if(jsonArray==null||jsonArray.length()==0) {
                sendm(qun,"未搜到");
                return;
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                String id=jsonObject.getString("id");
                String name=jsonObject.getString("name");
                JSONArray artistsArray=jsonObject.getJSONArray("artist");
                String artist=artistsArray.getString(0);
                String picId=jsonObject.getString("pic_id");
                result2+=(i+1)+"."+name+"--"+artist+"\n";
                int strB=i+1;
                String b=String.valueOf(strB);
                写(qun,"音乐",b,id);
                写(qun,"封面",b,picId);
                写(qun,"歌曲",b,name);
                写(qun,"歌手",b,artist);
                临时标志.数量=i+1;
            }
            地图.put(qun+wxid, 临时标志);
            result2=result2+"\n请发送序号来进行点歌\n两分钟内有效";
            选择=7;
            sendm(qun,result2);
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(选择==4&&text.matches("^[1-9]\\d*$")) {
        if(地图==null||!地图.containsKey(qun+wxid)) {
            return;
        }
        检查 资讯=地图.get(qun+wxid);
        if(资讯.数量<Integer.parseInt(text)) {
            return;
        }
        String musicName=资讯.名称;
        if(System.currentTimeMillis()/1000-资讯.时间/1000>120) {
            地图.remove(qun+wxid);
            选择=0;
            return;
        }
        String jsonData=get("https://api.lolimi.cn/API/yiny/?word="+musicName+"&n="+text);
        try {
            JSONObject jsonObject=new JSONObject(jsonData);
            JSONObject data=jsonObject.getJSONObject("data");
            String song=data.getString("song");
            String singer=data.getString("singer");
            String cover=data.getString("cover");
            String link=data.getString("link");
            String url=data.getString("url");
            sendMusic(qun,url,singer,song,cover,"");
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(text.startsWith("VIP点歌")) {
        text=text.substring(5).trim();
        if(text.equals("")) {
            return;
        }
        检查 临时标志=new 检查();
        临时标志.名称=text;
        临时标志.时间=System.currentTimeMillis();
        String result2="";
        String jsonData=get("https://api.lolimi.cn/API/yiny/?word="+text+"&num=6");
        try {
            JSONObject jsonObject=new JSONObject(jsonData);
            if(jsonObject.get("code")==200) {
                JSONArray jsonArray=jsonObject.getJSONArray("data");
                for (int i=0; i<jsonArray.length(); i++) {
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
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }*/
    if(选择==9&&text.matches("^[1-9]\\d*$")) {
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
        String mid=取(qun,"音乐",text);
        String singer=取(qun,"歌手",text);
        String songname=取(qun,"歌曲",text);
        String cover=取(qun,"封面",text);
        String lyric="";
        String jsonp=MGGET("https://m.music.migu.cn/migumusic/h5/play/auth/getSongPlayInfo?type=4&copyrightId="+mid);
        String songLyric=MGGET("https://m.music.migu.cn/migumusic/h5/song/lyric?copyrightId="+mid);
        try {
            JSONObject jsonObject=new JSONObject(jsonp);
            JSONObject data=jsonObject.getJSONObject("data");
            String url=data.getString("playUrl");
            JSONObject Lyric=new JSONObject(songLyric);
            JSONObject data1=Lyric.getJSONObject("data");
            if(data1.has("lyric")) {
                lyric=data1.getString("lyric");
            }
            sendMusic(qun,"https:"+url,singer,songname,"https:"+cover,lyric);
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(text.startsWith("咪咕点歌")) {
        String name=text.substring(4).trim();
        if(name.equals("")) {
            return;
        }
        检查 临时标志=new 检查();
        临时标志.时间=System.currentTimeMillis();
        String result2="";
        String jsonData=MGGET("https://m.music.migu.cn/migumusic/h5/search/all?text="+name+"&pageSize=6");
        try {
            JSONObject json = new JSONObject(jsonData);
            Integer code = json.getInt("code");
            JSONObject data=json.getJSONObject("data");
            JSONObject songsData =data.getJSONObject("songsData");
            JSONArray resultList = songsData.getJSONArray("items");
            if(resultList==null||resultList.length()==0) {
                sendm(qun,"未搜到");
                return;
            }

            for(int i = 0; i <resultList.length(); i++) {
                JSONObject jsonObject = resultList.getJSONObject(i);
                String name = jsonObject.getString("name");
                String mediumPic = jsonObject.getString("mediumPic");
                String id = jsonObject.getString("copyrightId");
                JSONArray singerArray = jsonObject.getJSONArray("singers");
                JSONObject firstSinger = singerArray.getJSONObject(0);
                String firstSingerName = firstSinger.getString("name");
                result2+=(i+1)+"."+name+"--"+firstSingerName+"\n";
                int strB=i+1;
                String b=String.valueOf(strB);
                写(qun,"音乐",b,id);
                写(qun,"歌手",b,firstSingerName);
                写(qun,"歌曲",b,name);
                写(qun,"封面",b,mediumPic);
                临时标志.数量=i+1;
            }
            地图.put(qun+wxid, 临时标志);
            result2=result2+"\n请发送序号来进行点歌\n两分钟内有效";
            选择=9;
            sendm(qun,result2);
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }

    if(选择==10&&text.matches("^[1-9]\\d*$")) {
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
        String url=取(qun,"音乐",text);
        String singer=取(qun,"歌手",text);
        String songname=取(qun,"歌曲",text);
        String cover=取(qun,"封面",text);
        if(cover.equals("")||cover.equals("null")) cover="https://ringimgbssdl.tx.kugou.com/uploadpic/softhead/100/20210124/20210124135824950079.jpg";
        try {
            sendMusic(qun,url,singer,songname,cover,"");
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }

    if(text.startsWith("酷狗铃声")) {
        String name=text.substring(4).trim();
        if(name.equals("")) {
            return;
        }
        检查 临时标志=new 检查();
        临时标志.时间=System.currentTimeMillis();
        String result2="";
        String jsonData=get("http://api.ring.kugou.com/ring/all_search?q="+name+"&t=2&subtype=1&p=1&pn=6&plat=3&st=3");
        try {
            JSONObject json = new JSONObject(jsonData);
            JSONObject data=json.getJSONObject("response");
            JSONArray resultList = data.getJSONArray("musicInfo");
            if(resultList==null||resultList.length()==0) {
                sendm(qun,"未搜到");
                return;
            }

            for(int i = 0; i <resultList.length(); i++) {
                JSONObject jsonObject = resultList.getJSONObject(i);
                String singerName = jsonObject.getString("singerName");
                String ringName = jsonObject.getString("ringName");
                String url = jsonObject.getString("url");
                JSONObject image=jsonObject.getJSONObject("image");
                String hd="";
                if(image.has("head")) {
                    hd=image.getString("head");
                }
                result2+=(i+1)+"."+ringName+"--"+singerName+"\n";
                int strB=i+1;
                String b=String.valueOf(strB);
                写(qun,"音乐",b,url);
                写(qun,"歌手",b,singerName);
                写(qun,"歌曲",b,ringName);
                写(qun,"封面",b,hd);
                临时标志.数量=i+1;
            }
            地图.put(qun+wxid, 临时标志);
            result2=result2+"\n请发送序号来进行点歌\n两分钟内有效";
            选择=10;
            sendm(qun,result2);
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(选择==14&&text.matches("^[1-9]\\d*$")) {
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
        String url="https://music-api2.cenguigui.cn/?kg&id="+取(qun,"音乐",text)+"&type=song&format=mp3&level=lossless";
        String lrc=get("http://m.kugou.com/app/i/krc.php?cmd=100&timelength=999999&hash="+取(qun,"音乐",text));
        String singer=取(qun,"歌手",text);
        String songname=取(qun,"歌曲",text);
        String cover=取(qun,"封面",text);
        if(cover.equals("")||cover.equals("null")) cover="https://ringimgbssdl.tx.kugou.com/uploadpic/softhead/100/20210124/20210124135824950079.jpg";
        try {
            sendMusic(qun,url,singer,songname,cover,lrc);
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(text.startsWith("酷狗点歌")) {
        String name=text.substring(4).trim();
        if(name.equals("")) {
            return;
        }
        检查 临时标志=new 检查();
        临时标志.时间=System.currentTimeMillis();
        String result2="";
        String jsonData=get("https://songsearch.kugou.com/song_search_v2?keyword="+name+"&page=1&pagesize=6&userid=-1&clientver=&platform=WebFilter&tag=em&filter=2&iscorrection=1&privilege_filter=0");
        try {
            JSONObject json = new JSONObject(jsonData);
            JSONObject data=json.getJSONObject("data");
            JSONArray lists = data.getJSONArray("lists");
            if(lists==null||lists.length()==0) {
                sendm(qun,"未搜到");
                return;
            }

            for(int i = 0; i <lists.length(); i++) {
                JSONObject jsonObject = lists.getJSONObject(i);
                String SingerName = jsonObject.getString("SingerName").replace("<em>","").replace("</em>","").replace("、","/");
                String SongName = jsonObject.getString("SongName").replace("<em>","").replace("</em>","").replace("、","/");
                String SQFileHash = jsonObject.getString("SQFileHash");
                String Image = jsonObject.getString("Image").replace("{size}","400");
                result2+=(i+1)+"."+SongName+"--"+SingerName+"\n";
                int strB=i+1;
                String b=String.valueOf(strB);
                写(qun,"音乐",b,SQFileHash);
                写(qun,"歌手",b,SingerName);
                写(qun,"歌曲",b,SongName);
                写(qun,"封面",b,Image);
                临时标志.数量=i+1;
            }
            地图.put(qun+wxid, 临时标志);
            result2=result2+"\n请发送序号来进行点歌\n两分钟内有效";
            选择=14;
            sendm(qun,result2);
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
    if(text.startsWith("点歌")) {
        text=text.substring(2).trim();
        if(text.equals("")) {
            return;
        }
        String result2="";
        String jsonData=get("http://u.y.qq.com/cgi-bin/musicu.fcg?data={\"comm\":{\"ct\":\"19\",\"cv\":\"1882\",\"uin\":\"0\"},\"searchMusic\":{\"method\":\"DoSearchForQQMusicDesktop\",\"module\":\"music.search.SearchCgiService\",\"param\":{\"grp\":1,\"num_per_page\":1,\"page_num\":1,\"query\":\""+text+"\",\"search_type\":0}}}");
        try {
            JSONObject jsonObject=new JSONObject(jsonData);
            if(jsonObject.get("code")==0) {
                JSONObject searchMusicObject = jsonObject.getJSONObject("searchMusic");
                JSONObject dataObject = searchMusicObject.getJSONObject("data");
                JSONObject bodyObject = dataObject.getJSONObject("body");
                JSONObject songObject = bodyObject.getJSONObject("song");
                JSONArray songList = songObject.getJSONArray("list");
                if(songList==null||songList.length()==0) {
                    sendm(qun,"未搜到");
                    return;
                }
                for(int i=0; i<1; i++) {
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
                    try {
                        String firstSip="https://music-api2.cenguigui.cn/?qq&id="+mid+"&type=song&format=mp3&level=jymaster";
                        String cover="http://y.gtimg.cn/music/photo_new/T002R500x500M000"+pmid+".jpg";
                        String songLyric=qqLyricget("https://c.y.qq.com/lyric/fcgi-bin/fcg_query_lyric_new.fcg?format=json&nobase64=1&g_tk=5381&songmid="+mid);
                        JSONObject Lyric = new JSONObject(songLyric);
                       if(Lyric.has("lyric")) {
                           String json_lyric = Lyric.getString("lyric");
                           sendMusic(qun,firstSip,firstSingerName,name,cover,json_lyric);
                       } else {
                           sendMusic(qun,firstSip,firstSingerName,name,cover,"");
                       }
                    } catch(e) {
                        sendm(qun,"错误,请稍候再试");
                        return;
                    }
               }
            }
        } catch(e) {
            sendm(qun,"错误,请稍候再试");
            return;
        }
    }
}