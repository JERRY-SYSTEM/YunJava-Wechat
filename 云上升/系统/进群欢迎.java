public String joinGroup(String xmlContent) {// 陌然
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    InputSource is = new InputSource(new StringReader(xmlContent));
    Document doc = dBuilder.parse(is);
    doc.getDocumentElement().normalize();
    NodeList linkList = doc.getElementsByTagName("link_list").item(0).getChildNodes();
    JSONObject json = new JSONObject();
    for (int i = 0; i < linkList.getLength(); i++) {
        Node linkNode = linkList.item(i);
        if (linkNode.getNodeType() == Node.ELEMENT_NODE && "link".equals(linkNode.getNodeName())) {
            Element linkElement = (Element) linkNode;
            String linkName = linkElement.getAttribute("name");
            NodeList memberList = linkElement.getElementsByTagName("memberlist");
            if (memberList.getLength() > 0) {
                Node memberListNode = memberList.item(0);
                NodeList usernameList = ((Element) memberListNode).getElementsByTagName("username");
                if (usernameList.getLength() > 0) {
                    Node usernameNode = usernameList.item(0);
                    json.putOpt(linkName, usernameNode.getTextContent());
                }
            }
        }
    }
    return json.toString();

}

public void 进群(Object data) {
    String text=data.content;
    String qun=data.talker;
    String wxid=data.sendTalker;
    if("1".equals(getString(qun,"进群欢迎",""))) {
        if(wxid.equals(AuthorWxid)||mWxid.equals(wxid)) {
            if(text.startsWith("设置进群欢迎")) {
                text=text.substring(6);
                putString(qun,"进群欢迎内容",text);
                sendm(qun,"已设置");
            }
            if(text.equals("查看进群欢迎内容")) {
                text="还没有设置";
                if(!getString(qun,"进群欢迎内容","").equals("")) {
                    text=getString(qun,"进群欢迎内容","");
                }
                sendm(qun,text);
            }
            if(text.equals("重置进群欢迎内容")) {
                putString(qun,"进群欢迎内容",null);
                sendm(qun,"已重置");
            }
            if(text.equals("查看进群欢迎变量")) {
                String c="◇当前群名:[当前群名]\n"
                         +"◇用户名字:[用户名字]\n"
                         +"◇当前时间:[当前时间]\n"
                         +"◇自己名字:[自己名字]\n"
                         +"◇群人数:[群人数]";
                sendm(qun,c);
            }
        }
        if(data.type==570425393) {
            if(text.contains("加入")) {
                SimpleDateFormat df=new SimpleDateFormat("yy年MM月dd日HH:mm");
                Calendar calendar=Calendar.getInstance();
                String joinGroup=joinGroup(data.content);
                JSONObject json=new JSONObject(joinGroup);
                String name="获取失败";
                if(json.has("adder")) {
                    name=getName(json.getString("adder"));
                } else if(json.has("names")) {
                    name=getName(json.getString("names"));
                } else if(json.has("username")) {
                    name=getName(json.getString("username"));
                }
                String time=df.format(calendar.getTime());
                text="╔═╗╔═╗╔═╗╔═╗\n╟欢╢╟迎╢╟新╢╟人╢\n╚═╝╚═╝╚═╝╚═╝\n欢迎新人进群\n群名:[当前群名]\n用户:[用户名字]\n人数:[群人数]\n[当前时间]";
                if(!getString(qun,"进群欢迎内容","").equals("")) {
                    text=getString(qun,"进群欢迎内容","");
                }
                text=text.replace("[当前时间]",time);
                text=text.replace("[用户名字]",name);
                text=text.replace("[当前群名]",getName(qun));
                text=text.replace("[自己名字]",getName(mWxid));
                text=text.replace("[群人数]",String.valueOf(getChatMembers(qun)));
                sendm(qun,text);
            }
        }
    }
}