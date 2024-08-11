//微信卡片
public void sendMusic(String talker,String src,String name,String title,String cover) {
    try {
        JSONObject musicData = new JSONObject();
        musicData.put("musicDataUrl", src);
        musicData.put("description",name);
        musicData.put("title",title);
        musicData.put("thumb",cover);
        sendMusicCard(talker,musicData);
    } catch(Exception e) {
        sendMsg(talker,"错误");
    }
}
public void sendWeb(String talker,String title,String description,String thumb,String Url) {
    try {
        JSONObject webData = new JSONObject();
        webData.put("title",title);
        webData.put("description",description);
        webData.put("thumb",thumb);
        webData.put("webpageUrl",Url);
        sendWebCard(talker,webData);
    } catch(Exception e) {
        sendMsg(talker,"错误");
    }
}