public class 每日总结 {
    int start=20;//开始(0-23)
    int end=24;//结束(1-24)
    ScheduledExecutorService scheduler=Executors.newScheduledThreadPool(1);
    scheduler.scheduleAtFixedRate(new Runnable() {
        public void run() {
            SimpleDateFormat df=new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
            Calendar calendar=Calendar.getInstance();
            LocalDateTime now=LocalDateTime.now();
            String Day=calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.DAY_OF_YEAR);
            if(!Day.equals(取("执行","总结"))&&now.getHour()>=start&&now.getHour()<=end) {
                putString("执行","总结",Day);
                for (String qun:getGroups()) {
                    if("1".equals(getString(qun,"每日总结",""))&&"1".equals(getString(qun,"开关",""))) {
                        存(qun,"对话1",null);
                        AIGO(qun,0,"",JavaPath+"/数据/"+qun+"/消息.txt",true);
                        Thread.sleep(60*1000);
                    }
                }
            }
        }
    },0,5,TimeUnit.MINUTES);
} new 每日总结();
public void 总结(Object data) {
    String text=data.content;
    String qun=data.talker;
    String wxid=data.sendTalker;
    int msgId=data.msgId;
    if(text.equals("一键总结")) {
        存(qun,"对话1",null);
        AIGO(qun,msgId,"",JavaPath+"/数据/"+qun+"/消息.txt",false);
    }
    if(text.startsWith("追问")) {
        text=text.substring(2);
        if(text.equals("")) {
            return;
        }
        AIGO(qun,msgId,text,"",false);
    }
    File 代管=new File(JavaPath+"/数据/"+qun+"/代管.txt");
    if(wxid.equals(AuthorWxid)||mWxid.equals(wxid)||简读用户(代管,wxid)) {
        if(text.equals("清空总结内容")) {
           File 昨日消息=new File(JavaPath+"/数据/"+qun+"/消息.txt");
           全弃(昨日消息);
           sendReply(msgId,qun,"已清空总结的消息内容");
        }
    }
}
public void 消息(Object data) {
    String text=data.content;
    String qun=data.talker;
    String name=getName(data.sendTalker);
    File 昨日消息=new File(JavaPath+"/数据/"+qun+"/消息.txt");
    SimpleDateFormat df=new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
    Calendar calendar=Calendar.getInstance();
    String Day=calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.DAY_OF_YEAR);
    if(!Day.equals(取(qun,"消息"))) {
        全弃(昨日消息);
        putString(qun,"消息",Day);
    }
    if(data.talkerType==1&&data.isSend==0||data.talkerType==0) {
        String text1="[特殊消息]";
        if(data.type==1) text1=text.replace("\n","\\n");
        if(data.type==10000) {
            if(data.content==null) text1="[提示消息]";
            if(data.content!=null) text1="[提示消息]"+text;
        }
        if(data.type==822083633) {
            text=getElementContent(text,"title");
            text1="[回复消息]"+text.replace("\n","\\n");
        }
        SimpleDateFormat df=new SimpleDateFormat("HH:mm:ss");
        Calendar calendar=Calendar.getInstance();
        String time=df.format(calendar.getTime());
        String cs="";
        if(name!=null) {
            cs ="|"+time+
                "|name:"+name.replaceAll("\n","")+
                "|content:"+text1+"|";
        } else {
            cs ="|"+time+
                "|name:未知"+
                "|content:"+text1+"|";
        }
        简写(昨日消息,cs);
    }
}
public void AIGO(String qun,int msgId,String text,String FilePath,boolean delete) {
    if(!取("开关","accessToken").equals("")) {
        StringBuilder ask=new StringBuilder();
        StringBuilder image=new StringBuilder();
        String text1="";
        String text2="";
        String text3="";
        if(!FilePath.equals("")) {
            File folder=new File(FilePath);
            long fileSize=folder.length();
            if(fileSize==0) {
                if(msgId!=0) {
                    sendReply(msgId,qun,"警告：内容为空");
                } else sendMsg(qun,"警告：内容为空");
                return;
            }
            String jsonStr=uploadFile("https://chatglm.cn/chatglm/backend-api/assistant/file_upload","file",FilePath);
            if(jsonStr.equals("10001")) {
               if(msgId!=0) {
                   sendReply(msgId,qun,"错误：文件不存在或不可读");
                } else sendMsg(qun,"错误：文件不存在或不可读");
                return;
            } else if(jsonStr.equals("10002")) {
                if(msgId!=0) {
                    sendReply(msgId,qun,"警告：文件大小超过20MB");
                } else sendMsg(qun,"警告：文件大小超过20MB");
                return;
            }
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONObject resultObject = jsonObject.getJSONObject("result");
            if(resultObject.has("file_url")) {
                String file_id = resultObject.getString("file_id");
                String file_name = resultObject.getString("file_name");
                File file = new File(FilePath);
                long fileSize = file.length();
                String file_url = resultObject.getString("file_url");
                text3="{\"type\": \"file\",\"file\": [{\"file_id\": \""+file_id+"\",\"file_url\": \""+file_url+"\",\"file_name\": \""+file_name+"\",\"file_size\": "+fileSize+"}]}";
                matcher = Pattern.compile("\\s目录#(.*?)\\.[^\\s]*").matcher(text);
                text = matcher.replaceAll("");
            }
        }
        if(!text.equals("")) {
            text2=text3+image.toString()+"{\"type\":\"text\",\"text\":\""+ask.append(u加(text)).toString()+"\"}";
        } else {
            text2=text3+image.toString();
        }
        String 记忆对话="";
        String 智能体="6646e1fe08e3b3a4b7985299";
        if(!取(qun,"对话1").equals("")) {
            记忆对话=取(qun,"对话1");
        }
        String eventMessage=GLM("https://chatglm.cn/chatglm/backend-api/assistant/stream","{\"assistant_id\":\""+智能体+"\",\"conversation_id\":\""+记忆对话+"\",\"meta_data\":{\"is_test\":false,\"input_question_type\":\"xxxx\",\"channel\":\"\"},\"messages\":["+text1+"{\"role\":\"user\",\"content\":["+text2.replace("}{","},{")+"]}]}");
        StringBuilder process=new StringBuilder();//判断
        String[] patterns= {"发生错误: (.*?)","<title>(.*?)</title>","\"msg\":\"(.*?)\"","\"message\":\"(.*?)\""};
        for(String patternStr:patterns) {
            Pattern pattern=Pattern.compile(patternStr);
            Matcher matcher=pattern.matcher(eventMessage);
            if(matcher.find()) {
                String message=matcher.group(1);
                if(patternStr.equals("\"message\":\"(.*?)\"")&&message.equals("success")) {
                    continue;
                }
                process.append(message);
                message=process.toString();
                if(message.contains("Signature has expired")||message.contains("Signature verification failed")) {
                    process.delete(0, process.length());
                    String jsonString=RGLM("https://chatglm.cn/chatglm/user-api/user/refresh","");
                    String c="";
                    String b="";
                    Pattern pattern=Pattern.compile("\"message\":\"(.*?)\"");
                    Matcher matcher=pattern.matcher(jsonString);
                    if(matcher.find()) {
                        String message=matcher.group(1);
                        if(message.equals("参数错误")) {
                            if(msgId!=0) {
                                sendReply(msgId,qun,message);
                            } else sendMsg(qun,message);
                            return;
                        }
                        if(message.equals("success")) {
                            JSONObject jsonObject = new JSONObject(jsonString);
                            String accessToken = jsonObject.getJSONObject("result").getString("access_token");
                            String refreshtoken = jsonObject.getJSONObject("result").getString("refresh_token");
                            b="Bearer "+refreshtoken;
                            putString("开关","refreshtoken",b);
                            c="Bearer "+accessToken;
                            putString("开关","accessToken",c);
                            Toast("Token获取成功，已自动填写");
                        }
                    } else {
                        Toast("失败，请重新再试！");
                        return;
                    }
                } else {
                    if(msgId!=0) {
                        sendReply(msgId,qun,message);
                    } else sendMsg(qun,message);
                    return;
                }
                eventMessage=GLM("https://chatglm.cn/chatglm/backend-api/assistant/stream","{\"assistant_id\":\""+智能体+"\",\"conversation_id\":\""+记忆对话+"\",\"meta_data\":{\"is_test\":false,\"input_question_type\":\"xxxx\",\"channel\":\"\"},\"messages\":["+text1+"{\"role\":\"user\",\"content\":["+text2.replace("}{","},{")+"]}]}");
                for(String patternStr:patterns) {
                    Pattern pattern=Pattern.compile(patternStr);
                    Matcher matcher=pattern.matcher(eventMessage);
                    if(matcher.find()) {
                        message=matcher.group(1);
                        if(patternStr.equals("\"message\":\"(.*?)\"")&&message.equals("success")) {
                            continue;
                        }
                        process.append(message);
                        message=process.toString();
                        if(msgId!=0) {
                            sendReply(msgId,qun,message);
                        } else sendMsg(qun,message);
                        return;
                    }
                }
            }
        }//回答内容
        String[] lines=eventMessage.split("event:message\ndata:");//图片内容循环遍历每一行
        for (int i = 0; i < lines.length; i += 2) {
            String firstLine = lines[i]; // 获取当前行的数据
            if(firstLine != null && firstLine.contains("image_urls")) { //是否包含image_urls
                JSONObject json = new JSONObject(firstLine);
                JSONArray json_parts = json.getJSONArray("parts");
                JSONObject json_parts_i = json_parts.getJSONObject(0);
                JSONArray json_parts_i_content = json_parts_i.getJSONArray("content");
                JSONObject json_parts_i_content_i = json_parts_i_content.getJSONObject(0);
                JSONObject json_parts_i_meta_data = json_parts_i.getJSONObject("meta_data");
                if(json_parts_i_meta_data.has("image_urls")) {
                    JSONArray json_parts_i_meta_data_image_urls = json_parts_i_meta_data.getJSONArray("image_urls");
                    String json_parts_i_meta_data_image_urls_i = json_parts_i_meta_data_image_urls.getString(0);
                    process.append("[pic=").append(json_parts_i_meta_data_image_urls_i).append("]");
                }
            }
        }
        for(int i=0; i<lines.length; i+=3) {
            String firstLine=lines[i]; // 获取当前行的数据
            if(firstLine!=null&&firstLine.contains("image_url")) { //是否包含image_url
                JSONObject jsonObject=new JSONObject(firstLine);
                JSONArray partsArray=jsonObject.getJSONArray("parts");
                JSONObject partObject=partsArray.getJSONObject(0);
                JSONArray contentArray=partObject.getJSONArray("content");
                JSONObject contentObject=contentArray.getJSONObject(0);
                JSONArray imageArray=contentObject.getJSONArray("image");
                JSONObject imageObject=imageArray.getJSONObject(0);
                String imageUrl=imageObject.getString("image_url");//提取图片的URL
                sendPic(qun,imageUrl);
            }
        }//提取最后一个
        String lastLine=lines[lines.length - 1];
        JSONObject jsonObject=new JSONObject(lastLine);
        String conversation_id=jsonObject.getString("conversation_id");
        存(qun,"对话1",conversation_id);
        JSONArray partsArray=jsonObject.getJSONArray("parts");
        JSONObject partObject=partsArray.getJSONObject(0);
        JSONArray contentArray=partObject.getJSONArray("content");
        if(contentArray!=null&&contentArray.length()>0) {
            JSONObject contentObject=contentArray.getJSONObject(0);//问答内容
            if(contentObject.has("text")) {
                process.append(contentObject.getString("text"));
            }
        }//最后希望
        JSONObject lastErrorObject=jsonObject.getJSONObject("last_error");
        if(lastErrorObject!=null) {
            if(lastErrorObject.has("intervene_text")) {
                String interveneText=lastErrorObject.getString("intervene_text");
                process.append(interveneText);
            }
        }//空字符
        if(process.toString().equals("")) {
            process.append("出错了，请换个话题或重置对话再试！");
            if(msgId!=0) {
                sendReply(msgId,qun,process.toString());
            } else sendMsg(qun,process.toString());
            return;
        }
        String message=process.toString();
        if(msgId!=0) {
            sendReply(msgId,qun,message);
        } else sendMsg(qun,message);
        if(delete) {
            File 昨日消息=new File(FilePath);
            全弃(昨日消息);
        }
    } else if(!data.isReply()) {
        if(msgId!=0) {
            sendReply(msgId,qun,"请主人发送 智能系统 来绑定");
        } else sendMsg(qun,"请主人发送 智能系统 来绑定");
    }
}