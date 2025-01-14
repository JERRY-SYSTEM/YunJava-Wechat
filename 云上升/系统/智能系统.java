public void 回复(Object data) {
    String text=data.content;
    String qun=data.talker;
    String wxid=data.sendTalker;
            if(text.equals("开启智能回复")) {
                if("1".equals(getString(qun,"智能回复",""))) {
                    sendReply(data.msgId,qun,"已经开了");
                    return;
                }
                putString(qun,"智能回复","1");
                sendReply(data.msgId,qun,"已开启");
                return;
            }
            if(text.equals("关闭智能回复")) {
                if(!"1".equals(getString(qun,"智能回复",""))) {
                    sendReply(data.msgId,qun,"还没开");
                    return;
                }
                putString(qun, "智能回复", null);
                sendReply(data.msgId,qun,"已关闭");
                return;
            }
            if(取(qun,"智能回复").equals("1")) {
                if(text.equals("重新绑定")) {
                    String jsonString=RGLM("https://chatglm.cn/chatglm/user-api/user/refresh","");
                    String c="";
                    String b="";
                    Pattern pattern=Pattern.compile("\"message\":\"(.*?)\"");
                    Matcher matcher=pattern.matcher(jsonString);
                    if(matcher.find()) {
                        String message=matcher.group(1);
                        if(message.equals("参数错误")) {
                            sendReply(data.msgId,qun,message);
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
                            sendReply(data.msgId,qun,"Token获取成功，已自动填写");
                        }
                    } else {
                        sendReply(data.msgId,qun,"失败，请重新再试！");
                    }
                    return;
                }
                if(text.equals("重置智能体")) {
                    putString(qun,"智能体",null);
                    putString(qun,"对话",null);
                    putString(qun,"名字",null);
                    putString(qun,"简介",null);
                    putString(qun,"作者",null);
                    sendReply(data.msgId,qun,"已重置");
                    return;
                }
                if(text.equals("查看智能体")) {
                    String 名字="";
                    String 描述="";
                    String 作者="";
                    if(!取(qun,"名字").equals("")) {
                        名字=取(qun,"名字");
                    }
                    if(!取(qun,"描述").equals("")) {
                        描述=取(qun,"描述");
                    }
                    if(!取(qun,"作者").equals("")) {
                        作者=取(qun,"作者");
                    }
                    String result="智能体:"+名字+"\n来自于:"+作者+"\n描述语:\n"+描述;
                    if(取(qun,"名字").equals("")||取(qun,"描述").equals("")) {
                        result="当前默认体";
                    }
                    sendReply(data.msgId,qun,result);
                    return;
                }
                if(text.startsWith("搜索智能体")) {
                    text=text.substring(5).trim();
                    if(text.equals("")) {
                        return;
                    }
                    检查 临时标志=new 检查();
                    临时标志.时间=System.currentTimeMillis();
                    String GLMGET=GLMGET("https://chatglm.cn/chatglm/feed-api/assistant/search_list?page=1&pageSize=15&keyword="+text);
                    String message="";
                    StringBuilder process=new StringBuilder();//判断
                    String[] patterns= {"发生错误: (.*?)","<title>(.*?)</title>","\"msg\":\"(.*?)\"","\"message\":\"(.*?)\""};
                    for (String patternStr : patterns) {
                        Pattern pattern=Pattern.compile(patternStr);
                        Matcher matcher=pattern.matcher(GLMGET);
                        if (matcher.find()) {
                            String message = matcher.group(1);
                            if(patternStr.equals("\"message\":\"(.*?)\"")&&message.equals("success")) {
                                continue;
                            }
                            process.append(message);
                            message=process.toString();
                            sendReply(data.msgId,qun,message);
                            return;
                        }
                    }
                    String result2="";
                    JSONObject json=new JSONObject(GLMGET);
                    JSONObject json_result=json.getJSONObject("result");
                    JSONArray json_result_list=json_result.getJSONArray("list");
                    if(json_result_list==null||json_result_list.length()==0) {
                        sendReply(data.msgId,qun,"未搜到");
                        return;
                    }
                    for(int i=0; i<json_result_list.length(); i++) {
                        JSONObject json_result_list_i=json_result_list.getJSONObject(i);
                        String json_result_list_i_assistant_id=json_result_list_i.getString("assistant_id");
                        String json_result_list_i_user_nickname=json_result_list_i.getString("user_nickname");
                        String json_result_list_i_name=json_result_list_i.getString("name");
                        String json_result_list_i_description=json_result_list_i.getString("description");
                        JSONArray json_result_list_i_starter_prompts=json_result_list_i.getJSONArray("starter_prompts");
                        String result3="";
                        if(json_result_list_i_starter_prompts!=null&&json_result_list_i_starter_prompts.length()!=0) {
                            for(int h=0; h<json_result_list_i_starter_prompts.length(); h++) {
                                String json_result_list_i_starter_prompts_i = json_result_list_i_starter_prompts.getString(h);
                                result3+=(h+1)+"."+json_result_list_i_starter_prompts_i+"\n";
                            }
                        } else {
                            result3="无开场白";
                        }
                        result2+=(i+1)+"."+json_result_list_i_name+"\n";
                        String b=String.valueOf(i+1);
                        写(qun,"提示",b,result3);
                        写(qun,"名字",b,json_result_list_i_name);
                        写(qun,"作者",b,json_result_list_i_user_nickname);
                        写(qun,"助手",b,json_result_list_i_assistant_id);
                        写(qun,"描述",b,json_result_list_i_description);
                        临时标志.数量=i+1;
                    }
                    地图.put(qun+wxid,临时标志);
                    result2=result2+"\n请发送序号来进行选择\n两分钟内有效";
                    选择=61;
                    sendReply(data.msgId,qun,result2);
                    return;
                }
                if(text.equals("我的智能体")) {
                    检查 临时标志=new 检查();
                    临时标志.时间=System.currentTimeMillis();
                    String GLMGET=GLMGET("https://chatglm.cn/chatglm/feed-api/assistant/feed_list?page=1&page_size=40&tag=self");
                    String message="";
                    StringBuilder process=new StringBuilder();
//判断
                    String[] patterns= {"发生错误: (.*?)","<title>(.*?)</title>","\"msg\":\"(.*?)\"","\"message\":\"(.*?)\""};
                    for (String patternStr : patterns) {
                        Pattern pattern=Pattern.compile(patternStr);
                        Matcher matcher=pattern.matcher(GLMGET);
                        if (matcher.find()) {
                            String message = matcher.group(1);
                            if(patternStr.equals("\"message\":\"(.*?)\"")&&message.equals("success")) {
                                continue;
                            }
                            process.append(message);
                            message=process.toString();
                            sendReply(data.msgId,qun,message);
                            return;
                        }
                    }
                    String result2="";
                    JSONObject json=new JSONObject(GLMGET);
                    JSONObject json_result=json.getJSONObject("result");
                    JSONArray json_result_list=json_result.getJSONArray("list");
                    if(json_result_list==null||json_result_list.length()==0) {
                        sendReply(data.msgId,qun,"未找到\n请到智谱清言创建一个吧");
                        return;
                    }
                    for(int i=0; i<json_result_list.length(); i++) {
                        JSONObject json_result_list_i=json_result_list.getJSONObject(i);
                        String json_result_list_i_assistant_id=json_result_list_i.getString("assistant_id");
                        String json_result_list_i_user_nickname=json_result_list_i.getString("user_nickname");
                        String json_result_list_i_name=json_result_list_i.getString("name");
                        String json_result_list_i_description=json_result_list_i.getString("description");
                        JSONArray json_result_list_i_starter_prompts=json_result_list_i.getJSONArray("starter_prompts");
                        String result3="";
                        if(json_result_list_i_starter_prompts!=null&&json_result_list_i_starter_prompts.length()!=0) {
                            for(int h=0; h<json_result_list_i_starter_prompts.length(); h++) {
                                String json_result_list_i_starter_prompts_i = json_result_list_i_starter_prompts.getString(h);
                                result3+=(h+1)+"."+json_result_list_i_starter_prompts_i+"\n";
                            }
                        } else {
                            result3="无开场白";
                        }
                        result2+=(i+1)+"."+json_result_list_i_name+"\n";
                        String b=String.valueOf(i+1);
                        写(qun,"提示",b,result3);
                        写(qun,"名字",b,json_result_list_i_name);
                        写(qun,"作者",b,json_result_list_i_user_nickname);
                        写(qun,"助手",b,json_result_list_i_assistant_id);
                        写(qun,"描述",b,json_result_list_i_description);
                        临时标志.数量=i+1;
                    }
                    地图.put(qun+wxid,临时标志);
                    result2=result2+"\n请发送序号来进行选择\n两分钟内有效";
                    选择=62;
                    sendReply(data.msgId,qun,result2);
                    return;
                }
                if((选择==61||选择==62)&&text.matches("^[1-9]\\d*$")) {
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
                    try {
                        String 名字=取(qun,"名字",text);
                        String 助手=取(qun,"助手",text);
                        String 描述=取(qun,"描述",text);
                        String 作者=取(qun,"作者",text);
                        String 提示=取(qun,"提示",text);
                        存(qun,"描述",描述);
                        存(qun,"智能体",助手);
                        存(qun,"对话",null);
                        存(qun,"名字",名字);
                        存(qun,"作者",作者);
                        String result="智能体:"+名字+"\n来自于:"+作者+"\n开场白:\n"+提示+"\n描述语:\n"+描述;
                        sendReply(data.msgId,qun,result);
                    } catch(e) {
                        sendReply(data.msgId,qun,"错误,请稍候再试");
                        return;
                    }
                    return;
                }
                if(text.equals("重置对话")) {
                    putString(qun,"对话",null);
                    sendReply(data.msgId,qun,"已重置");
                    return;
                }
        if(!取("开关","accessToken").equals("")&&!data.isReply()) {
            text=text.trim();
            Matcher matcher=Pattern.compile("\\@(.*?)\\s").matcher(text);
            text=matcher.replaceAll("");
            if(text.contains("")) {
                text="带有特殊小表情，换个吧";
                sendReply(data.msgId,qun,text);
                return;
            }
            StringBuilder ask=new StringBuilder();
            StringBuilder image=new StringBuilder();
            String text1="";
            String text2="";
            String text3="";
            if(text.contains("目录#")||text.contains("#目录")) {
                String one=text.split("目录#|#目录")[1];
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
                    sendReply(data.msgId,qun,"正在解读您的文件，请稍等...\n"+file_name+"-"+FileFormatConversion(fileSize)+"\n"+file_url);
                    text3="{\"type\": \"file\",\"file\": [{\"file_id\": \""+file_id+"\",\"file_url\": \""+file_url+"\",\"file_name\": \""+file_name+"\",\"file_size\": "+fileSize+"}]}";
                    matcher = Pattern.compile("\\s目录#(.*?)\\.[^\\s]*").matcher(text);// 将匹配到的内容替换为空字符串
                    text = matcher.replaceAll("");
                }
            }
            if(text.contains("头像")&&text.contains("我")) {
                String jsonStr=getQrCodePost("https://chatglm.cn/chatglm/backend-api/v1/image_upload",getAvatar(wxid));
                if(jsonStr.equals("10001")) {
                    sendReply(data.msgId,qun,"解读失败，无法访问该链接");
                    return;
                }
                JSONObject jsonObject = new JSONObject(jsonStr);
                JSONObject resultObject = jsonObject.getJSONObject("result");
                if(resultObject.has("image_url")) {
                    String imageUrl = resultObject.getString("image_url");
                    sendReply(data.msgId,qun,"正在解读您的头像，请稍等...\n"+imageUrl);
                    image.append("{\"type\": \"image\",\"image\": [{\"image_url\": \"").append(imageUrl).append("\"}]}");
                }
            }
            if(!text.equals("")) {
                text2=text3+image.toString()+"{\"type\":\"text\",\"text\":\""+ask.append(u加(text)).toString()+"\"}";
            } else {
                text2=text3+image.toString();
            }
            String 记忆对话="";
            String 智能体="65940acff94777010aa6b796";
            if(!取(qun,"对话").equals("")) {
                记忆对话=取(qun,"对话");
            }
            if(!取(qun,"智能体").equals("")) {
                智能体=取(qun,"智能体");
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
                                sendReply(data.msgId,qun,message);
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
                        sendReply(data.msgId,qun,message);
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
                            sendReply(data.msgId,qun,message);
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
            存(qun,"对话",conversation_id);
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
                sendReply(data.msgId,qun,process.toString());
                return;
            }
            String message=process.toString();
            sendReply(data.msgId,qun,message);
        } else if(!data.isReply()) {
            sendReply(data.msgId,qun,"请主人发送 智能系统 来绑定");
        }
    }
}
public void 智能(Object data) {
    String text=data.content;
    String qun=data.talker;
    String wxid=data.sendTalker;
    if(mWxid.equals(wxid)) {
        if(text.equals("获取验证码")) {
            if(!getString("开关","手机号码","").equals("")) {
                检查 临时标志=new 检查();
                临时标志.时间=System.currentTimeMillis();
                String jsonString=jsonPost("https://chatglm.cn/chatglm/backend-api/v1/user/checkphone","{\"phone\":\""+getString("开关","手机号码","")+"\",\"rid\":\"\",\"ismobile\":1,\"distinct_id\":\"57c0290a67a7b93d\",\"tm\":\"android\",\"oaid\":\"81ffef1e1dc98fdb\",\"fr\":\"xiaomi\"}");
                if(!jsonString.contains("<!DOCTYPE html>")) {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    String accessToken = jsonObject.getString("message");
                    if(accessToken.equals("短信验证码已发送")) {
                        sendReply(data.msgId,qun,accessToken+"\n一分钟内有效");
                    } else {
                        sendReply(data.msgId,qun,accessToken);
                    }
                    地图.put(qun+wxid,临时标志);
                    选择=51;
                }
            } else {
                sendReply(data.msgId,qun,"请发送[配置设置]来绑定手机号");
            }
        }
        if(选择==51&&text.matches("^[0-9][0-9]{1,8}$")) {
            检查 资讯=地图.get(qun+wxid);
            if(System.currentTimeMillis()/1000-资讯.时间/1000>60) {
                地图.remove(qun+wxid);
                选择=0;
                return;
            }
            String jsonString=jsonPost("https://chatglm.cn/chatglm/backend-api/v1/user/login","{\"phone\":\""+getString("开关","手机号码","")+"\",\"code\":\""+text+"\",\"ismobile\":1,\"distinct_id\":\"57c0290a67a7b93d\",\"tm\":\"android\",\"oaid\":\"81ffef1e1dc98fdb\",\"fr\":\"xiaomi\",\"ffr\":\"xiaomi\"}");
            String c="";
            String b="";
            Pattern pattern=Pattern.compile("\"message\":\"(.*?)\"");
            Matcher matcher=pattern.matcher(jsonString);
            if(matcher.find()) {
                String message=matcher.group(1);
                if(message.equals("参数错误")) {
                    sendReply(data.msgId,qun,message);
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
                    sendReply(data.msgId,qun,"Token获取成功，已自动填写");
                    选择=0;
                    return;
                }
            } else {
                sendReply(data.msgId,qun,"失败，请重新再试！");
            }
        }
    }
    if(!getString("开关","accessToken","").equals("")) {
        if(text.equals("重新绑定")) {
            String jsonString=RGLM("https://chatglm.cn/chatglm/user-api/user/refresh","");
            String c="";
            String b="";
            Pattern pattern=Pattern.compile("\"message\":\"(.*?)\"");
            Matcher matcher=pattern.matcher(jsonString);
            if(matcher.find()) {
                String message=matcher.group(1);
                if(message.equals("参数错误")) {
                    sendReply(data.msgId,qun,message);
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
                    sendReply(data.msgId,qun,"Token获取成功，已自动填写");
                }
            } else {
                sendReply(data.msgId,qun,"失败，请重新再试！");
            }
        }
        if(text.equals("重置智能体")) {
            putString(qun,"智能体",null);
            putString(qun,"对话",null);
            putString(qun,"名字",null);
            putString(qun,"简介",null);
            putString(qun,"作者",null);
            sendReply(data.msgId,qun,"已重置");
        }
        if(text.equals("查看智能体")) {
            String 名字="";
            String 描述="";
            String 作者="";
            if(!取(qun,"名字").equals("")) {
                名字=取(qun,"名字");
            }
            if(!取(qun,"描述").equals("")) {
                描述=取(qun,"描述");
            }
            if(!取(qun,"作者").equals("")) {
                作者=取(qun,"作者");
            }
            String result="智能体:"+名字+"\n来自于:"+作者+"\n描述语:\n"+描述;
            if(取(qun,"名字").equals("")||取(qun,"描述").equals("")) {
                result="当前默认体";
            }
            sendReply(data.msgId,qun,result);
        }
        if(text.startsWith("搜索智能体")) {
            text=text.substring(5).trim();
            if(text.equals("")) {
                return;
            }
            检查 临时标志=new 检查();
            临时标志.时间=System.currentTimeMillis();
            String GLMGET=GLMGET("https://chatglm.cn/chatglm/feed-api/assistant/search_list?page=1&pageSize=15&keyword="+text);
            String message="";
            StringBuilder process=new StringBuilder();//判断
            String[] patterns= {"发生错误: (.*?)","<title>(.*?)</title>","\"msg\":\"(.*?)\"","\"message\":\"(.*?)\""};
            for (String patternStr : patterns) {
                Pattern pattern=Pattern.compile(patternStr);
                Matcher matcher=pattern.matcher(GLMGET);
                if (matcher.find()) {
                    String message = matcher.group(1);
                    if(patternStr.equals("\"message\":\"(.*?)\"")&&message.equals("success")) {
                        continue;
                    }
                    process.append(message);
                    message=process.toString();
                    sendReply(data.msgId,qun,message);
                    return;
                }
            }
            String result2="";
            JSONObject json=new JSONObject(GLMGET);
            JSONObject json_result=json.getJSONObject("result");
            JSONArray json_result_list=json_result.getJSONArray("list");
            if(json_result_list==null||json_result_list.length()==0) {
                sendReply(data.msgId,qun,"未搜到");
                return;
            }
            for(int i=0; i<json_result_list.length(); i++) {
                JSONObject json_result_list_i=json_result_list.getJSONObject(i);
                String json_result_list_i_assistant_id=json_result_list_i.getString("assistant_id");
                String json_result_list_i_user_nickname=json_result_list_i.getString("user_nickname");
                String json_result_list_i_name=json_result_list_i.getString("name");
                String json_result_list_i_description=json_result_list_i.getString("description");
                JSONArray json_result_list_i_starter_prompts=json_result_list_i.getJSONArray("starter_prompts");
                String result3="";
                if(json_result_list_i_starter_prompts!=null&&json_result_list_i_starter_prompts.length()!=0) {
                    for(int h=0; h<json_result_list_i_starter_prompts.length(); h++) {
                        String json_result_list_i_starter_prompts_i = json_result_list_i_starter_prompts.getString(h);
                        result3+=(h+1)+"."+json_result_list_i_starter_prompts_i+"\n";
                    }
                } else {
                    result3="无开场白";
                }
                result2+=(i+1)+"."+json_result_list_i_name+"\n";
                String b=String.valueOf(i+1);
                写(qun,"提示",b,result3);
                写(qun,"名字",b,json_result_list_i_name);
                写(qun,"作者",b,json_result_list_i_user_nickname);
                写(qun,"助手",b,json_result_list_i_assistant_id);
                写(qun,"描述",b,json_result_list_i_description);
                临时标志.数量=i+1;
            }
            地图.put(qun+wxid,临时标志);
            result2=result2+"\n请发送序号来进行选择\n两分钟内有效";
            选择=61;
            sendReply(data.msgId,qun,result2);
        }
        if(text.equals("我的智能体")) {
            检查 临时标志=new 检查();
            临时标志.时间=System.currentTimeMillis();
            String GLMGET=GLMGET("https://chatglm.cn/chatglm/feed-api/assistant/feed_list?page=1&page_size=40&tag=self");
            String message="";
            StringBuilder process=new StringBuilder();//判断
            String[] patterns= {"发生错误: (.*?)","<title>(.*?)</title>","\"msg\":\"(.*?)\"","\"message\":\"(.*?)\""};
            for (String patternStr : patterns) {
                Pattern pattern=Pattern.compile(patternStr);
                Matcher matcher=pattern.matcher(GLMGET);
                if (matcher.find()) {
                    String message = matcher.group(1);
                    if(patternStr.equals("\"message\":\"(.*?)\"")&&message.equals("success")) {
                        continue;
                    }
                    process.append(message);
                    message=process.toString();
                    sendReply(data.msgId,qun,message);
                    return;
                }
            }
            String result2="";
            JSONObject json=new JSONObject(GLMGET);
            JSONObject json_result=json.getJSONObject("result");
            JSONArray json_result_list=json_result.getJSONArray("list");
            if(json_result_list==null||json_result_list.length()==0) {
                sendReply(data.msgId,qun,"未找到\n请到智谱清言创建一个吧");
                return;
            }
            for(int i=0; i<json_result_list.length(); i++) {
                JSONObject json_result_list_i=json_result_list.getJSONObject(i);
                String json_result_list_i_assistant_id=json_result_list_i.getString("assistant_id");
                String json_result_list_i_user_nickname=json_result_list_i.getString("user_nickname");
                String json_result_list_i_name=json_result_list_i.getString("name");
                String json_result_list_i_description=json_result_list_i.getString("description");
                JSONArray json_result_list_i_starter_prompts=json_result_list_i.getJSONArray("starter_prompts");
                String result3="";
                if(json_result_list_i_starter_prompts!=null&&json_result_list_i_starter_prompts.length()!=0) {
                    for(int h=0; h<json_result_list_i_starter_prompts.length(); h++) {
                        String json_result_list_i_starter_prompts_i = json_result_list_i_starter_prompts.getString(h);
                        result3+=(h+1)+"."+json_result_list_i_starter_prompts_i+"\n";
                    }
                } else {
                    result3="无开场白";
                }
                result2+=(i+1)+"."+json_result_list_i_name+"\n";
                String b=String.valueOf(i+1);
                写(qun,"提示",b,result3);
                写(qun,"名字",b,json_result_list_i_name);
                写(qun,"作者",b,json_result_list_i_user_nickname);
                写(qun,"助手",b,json_result_list_i_assistant_id);
                写(qun,"描述",b,json_result_list_i_description);
                临时标志.数量=i+1;
            }
            地图.put(qun+wxid,临时标志);
            result2=result2+"\n请发送序号来进行选择\n两分钟内有效";
            选择=62;
            sendReply(data.msgId,qun,result2);
            return;
        }
        if((选择==61||选择==62)&&text.matches("^[1-9]\\d*$")) {
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
            try {
                String 名字=取(qun,"名字",text);
                String 助手=取(qun,"助手",text);
                String 描述=取(qun,"描述",text);
                String 作者=取(qun,"作者",text);
                String 提示=取(qun,"提示",text);
                存(qun,"描述",描述);
                存(qun,"智能体",助手);
                存(qun,"对话",null);
                存(qun,"名字",名字);
                存(qun,"作者",作者);
                String result="智能体:"+名字+"\n来自于:"+作者+"\n开场白:\n"+提示+"\n描述语:\n"+描述;
                sendReply(data.msgId,qun,result);
            } catch(e) {
                sendReply(data.msgId,qun,"错误,请稍候再试");
                return;
            }
        }
        if(text.equals("重置对话")) {
            putString(qun,"对话",null);
            sendReply(data.msgId,qun,"已重置");
        }
    }
    if(text.equals("清除绑定状态")) {
        putString("开关","accessToken",null);
        sendReply(data.msgId,qun,"已清除");
    }
    new Thread(new Runnable() {
        public void run() {
            if(text.startsWith("AI")||text.startsWith("ai")) {
                if(!取("开关","accessToken").equals("")) {
                    text=text.substring(2).trim();
                    Matcher matcher=Pattern.compile("\\@(.*?)\\s").matcher(text);
                    text=matcher.replaceAll("");
                    if(text.contains("")) {
                        text="带有特殊小表情，换个吧";
                        sendReply(data.msgId,qun,text);
                        return;
                    }
                    StringBuilder ask=new StringBuilder();
                    StringBuilder image=new StringBuilder();
                    String text1="";
                    String text2="";
                    String text3="";
                    if(text.contains("目录#")||text.contains("#目录")) {
                        String one=text.split("目录#|#目录")[1];
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
                            sendReply(data.msgId,qun,"正在解读您的文件，请稍等...\n"+file_name+"-"+FileFormatConversion(fileSize)+"\n"+file_url);
                            text3="{\"type\": \"file\",\"file\": [{\"file_id\": \""+file_id+"\",\"file_url\": \""+file_url+"\",\"file_name\": \""+file_name+"\",\"file_size\": "+fileSize+"}]}";
                            matcher = Pattern.compile("\\s目录#(.*?)\\.[^\\s]*").matcher(text);// 将匹配到的内容替换为空字符串
                            text = matcher.replaceAll("");
                        }
                    }
                    if(text.contains("头像")&&text.contains("我")) {
                        String jsonStr=getQrCodePost("https://chatglm.cn/chatglm/backend-api/v1/image_upload",getAvatar(wxid));
                        if(jsonStr.equals("10001")) {
                            sendReply(data.msgId,qun,"解读失败，无法访问该链接");
                            return;
                        }
                        JSONObject jsonObject = new JSONObject(jsonStr);
                        JSONObject resultObject = jsonObject.getJSONObject("result");
                        if(resultObject.has("image_url")) {
                            String imageUrl = resultObject.getString("image_url");
                            sendReply(data.msgId,qun,"正在解读您的头像，请稍等...\n"+imageUrl);
                            image.append("{\"type\": \"image\",\"image\": [{\"image_url\": \"").append(imageUrl).append("\"}]}");
                        }
                    }
                    if(!text.equals("")) {
                        text2=text3+image.toString()+"{\"type\":\"text\",\"text\":\""+ask.append(u加(text)).toString()+"\"}";
                    } else {
                        text2=text3+image.toString();
                    }
                    String 记忆对话="";
                    String 智能体="65940acff94777010aa6b796";
                    if(!取(qun,"对话").equals("")) {
                        记忆对话=取(qun,"对话");
                    }
                    if(!取(qun,"智能体").equals("")) {
                        智能体=取(qun,"智能体");
                    }
                    String eventMessage=GLM("https://chatglm.cn/chatglm/backend-api/assistant/stream","{\"assistant_id\":\""+智能体+"\",\"conversation_id\":\""+记忆对话+"\",\"meta_data\":{\"is_test\":false,\"input_question_type\":\"xxxx\",\"channel\":\"\"},\"messages\":["+text1+"{\"role\":\"user\",\"content\":["+text2.replace("}{","},{")+"]}]}");
                    StringBuilder process=new StringBuilder();
//判断
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
                                        sendReply(data.msgId,qun,message);
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
                                sendReply(data.msgId,qun,message);
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
                                    sendReply(data.msgId,qun,message);
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
                    存(qun,"对话",conversation_id);
                    JSONArray partsArray=jsonObject.getJSONArray("parts");
                    JSONObject partObject=partsArray.getJSONObject(0);
                    JSONArray contentArray=partObject.getJSONArray("content");
                    if(contentArray!=null&&contentArray.length()>0) {
                        JSONObject contentObject=contentArray.getJSONObject(0);
//问答内容
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
                        sendReply(data.msgId,qun,process.toString());
                        return;
                    }
                    String message=process.toString();
                    sendReply(data.msgId,qun,message);
                } else sendReply(data.msgId,qun,"请主人发送 智能系统 来绑定");
            }
        }
    }).start();
}