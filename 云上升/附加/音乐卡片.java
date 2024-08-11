//微信音乐卡片
public void sendMusic(String talker,String src,String name,String title,String cover){
    try{
        JSONObject musicData = new JSONObject();
        musicData.put("musicDataUrl", src);
        musicData.put("description",name);
        musicData.put("title",title);
        musicData.put("thumb",cover);
        sendMusicCard(talker,musicData);
    }catch(Exception e){
        sendMsg(talker,src);
    }
}