public void 站长(Object data) {
String text=data.content;
String qun=data.talker;
String wxid=data.sendTalker;
    if(text.startsWith("json")||text.startsWith("JSON")) { //萌新
        String result=text.substring(4);
        try {
            JSONObject json=new JSONObject(result);
            String start="JSONObject json=new JSONObject(result);";
            String end=jiexi(json,"json");
            sendReply(data.msgId,qun,start+end);
        } catch (JSONException e) {
            sendReply(data.msgId,qun,"JSON解析错误:"+e.getMessage());
        }
    }
    if(text.startsWith("访问")) {
        Thread thread=new Thread(new Runnable() {
            public void run() {
                text=text.substring(2);
                String urlPattern="(\\/?|\\b)((https?|ftp|file):\\/\\/|www\\.|[-a-zA-Z0-9+&@#/%?=~_|!:,.;])[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|\\p{IsHan}]*";
                Pattern pattern=Pattern.compile(urlPattern);
                Matcher matcher=pattern.matcher(text);
                if(matcher.find()) {
                    String url=matcher.group();
                    try {
                        StringBuffer buffer=new StringBuffer();
                        InputStreamReader isr=null;
                        HttpURLConnection uc=(HttpURLConnection) new URL(url).openConnection();
                        uc.setConnectTimeout(240000);
                        uc.setReadTimeout(240000);
                        String c="";
                        int responseCode=uc.getResponseCode();
                        if(responseCode==HttpURLConnection.HTTP_OK) {
                            isr=new InputStreamReader(uc.getInputStream(),"utf-8");
                            BufferedReader reader=new BufferedReader(isr);
                            String line;
                            while ((line=reader.readLine()) != null) {
                                buffer.append(line+"\n");
                            }
                            isr.close();
                            String contentType=uc.getContentType();
                            if(contentType!=null) {
                                if(contentType.startsWith("video/")) {
                                    sendMsg(qun,"视频链接："+url);
                                } else if(contentType.startsWith("image/")) {
                                    sendPic(qun,url);
                                } else if(contentType.startsWith("audio/")) {
                                    sendMsg(qun,"音频链接："+url);
                                } else {
                                    c=buffer.toString();
                                    if(c.length()>3000) {
                                        c=c.substring(0,3000)+"\n\n……（超过3000字，内容已截断）";
                                    }
                                    sendMsg(qun,c);
                                }
                            }
                        } else if(responseCode==HttpURLConnection.HTTP_MOVED_PERM||responseCode==HttpURLConnection.HTTP_MOVED_TEMP) {
                            String redirectUrl=uc.getHeaderField("Location");
                            if(redirectUrl!=null) {
                                c=redirectUrl;
                                if(c.length()>500) {
                                    c=c.substring(0,500)+"\n\n……（超过500字，内容已截断）";
                                }
                                sendMsg(qun,c);
                            } else {
                                c="失败，无法获取重定向链接";
                                sendMsg(qun,c);
                            }
                        } else {
                            c="失败，HTTP状态码: "+responseCode;
                            sendMsg(qun,c);
                        }
                    } catch(Exception e) {
                        String errorMessage="失败，原因: "+e.getMessage();
                        sendMsg(qun,errorMessage);
                    }
                }
            }
        });
        thread.start();
    }
    if(text.startsWith("文转直链")) {
        String one=text.substring(4);
        String jsonStr=uploadFile("https://chatglm.cn/chatglm/backend-api/assistant/file_upload","file",one);
        if(jsonStr.equals("10001")) {
            sendReply(data.msgId,qun,"错误：文件不存在或不可读");
            return;
        } else if(jsonStr.equals("10002")) {
            sendReply(data.msgId,qun,"警告：文件大小超过20MB");
            return;
        }
        JSONObject jsonObject = new JSONObject(jsonStr);
        JSONObject resultObject = jsonObject.getJSONObject("result");
        if(resultObject.has("file_url")) {
            String file_id = resultObject.getString("file_id");
            String file_name = resultObject.getString("file_name");
            File file = new File(one);
            long fileSize = file.length();
            String file_url = resultObject.getString("file_url");
            sendReply(data.msgId,qun,file_name+"("+FileFormatConversion(fileSize)+")\n"+file_url);
        }
    }
}

public String jiexi(JSONObject json, String tag) {
    String result="";
    for(String str:json.keySet()) {
        result+="\n"+jiexi(json.get(str),str,tag);
    }
    return result;
}
public String jiexi(JSONObject json, String name, String tag) {
    String newTag=tag+"_"+name/*+ "_JSON"*/;
    if(!name.equals("i")) name="\""+name+"\"";
    String result="\nJSONObject "+newTag+"="+tag+".getJSONObject("+name+");";
    for(String str:json.keySet()) {
        result+="\n"+jiexi(json.get(str),str,newTag);
    }
    return result;
}
public String jiexi(JSONArray json, String name, String tag) {
    String newTag=tag+"_"+name/*+ "_Array"*/;
    if(!name.equals("i")) name="\""+name+"\"";
    int length=json.length();
    if(length>0)
    return "\nJSONArray "+newTag+"="+tag+".getJSONArray("+name+");\nfor(int i=0;i<"+newTag+".length();i++)\n{\n"+jiexi(json.get(0),"i",newTag)+"\n}";
    else return "//"+newTag+"没有数据\n";
}
public String jiexi(Object object, String name, String tag) {
    String newTag=tag+"_"+name;
    if(!name.equals("i")) name="\""+name+"\"";
    if(object instanceof Integer)
    return "\nInteger "+newTag+"="+tag+".getInt("+name+");//"+object;
    else if(object instanceof Long)
    return "\nLong "+newTag+"="+tag+".getLong("+name+");//"+object;
    else if(object instanceof Double)
    return "\nDouble " +newTag+"="+tag+".getDouble("+name+");//"+object;
    else if(object instanceof Boolean)
    return "\nBoolean "+newTag+"="+tag+".getBoolean("+name+");//"+object;
    else if(object instanceof String)
    return "\nString " +newTag+"="+tag+".getString("+name+");//\""+object+"\"";
    else return "\nObject "+newTag+"="+tag+".get("+name+");//"+object;
}