//微信卡片
public static WXMediaMessage CreateMusic(JSONObject JSONObject) {
        WXMusicObject Music=new WXMusicObject();
        Music.musicUrl=JSONObject.optString("musicUrl");
        Music.musicDataUrl=JSONObject.optString("musicDataUrl");
        Music.songLyric=JSONObject.optString("songLyric");
        Music.songAlbumUrl=JSONObject.optString("songAlbumUrl");
        WXMediaMessage Media=new WXMediaMessage(Music);
        Media.title=JSONObject.optString("title");
        Media.description=JSONObject.optString("description");
        return Media;
}
public void sendMusic(String talker,String src,String name,String title,String cover,String songLyric) {
    try {
        JSONObject musicData = new JSONObject();
        musicData.put("musicDataUrl",src);
        musicData.put("description",name);
        musicData.put("title",title);
        musicData.put("songAlbumUrl",cover);
        if(songLyric==null||songLyric.length()==0) {
            songLyric="[99:99.99]"+脚本作者+"提醒您，该歌曲没有歌词";
        }
        musicData.put("songLyric",songLyric);
        sendCard(talker,CreateMusic(musicData),"wx485a97c844086dc9");
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