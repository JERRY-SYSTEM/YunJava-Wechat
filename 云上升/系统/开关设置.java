public static String[] emojilist= {"☘️","🎈","🌼","🌸","🍀","🪐","☀️","🌕","⚾","🏀","🥎","🏆","🟥","💥","🔔","🔅","🔆","💫","🪙","🃏","🔮","🎀","👑","🎪","🎄","🍒","🍧","🍇","🎉","🍁","🍑","🍊","🍓","🍅","🍥","🏵","🎊","🎁","🎃","🍏","🍎","🍐","🍊","🍋","🍌","🍉","🍆","⚽️"};
public static void 开关(Object data) {
    String text=data.content;
    String qun=data.talker;
    String wxid=data.sendTalker;
    if(text.equals("切换文字发送")) {
        putString(qun,"发送模式",null);
        sendMsg(qun,"已切换为文字发送");
    }
    if(text.equals("切换图片发送")) {
        putString(qun, "发送模式", "1");
        sendMsg(qun,"已切换为图片发送");
    }
    if(text.equals("切换卡片发送")) {
        putString(qun,"发送模式","2");
        sendMsg(qun,"已切换为卡片发送");
    }
    if(text.equals("开启查询系统")) {
        if("1".equals(getString(qun,"查询系统",""))) {
            sendMsg(qun,"已经开了");
            return;
        }
        putString(qun,"查询系统","1");
        sendMsg(qun,"已开启");
    }
    if(text.equals("关闭查询系统")) {
        if(!"1".equals(getString(qun,"查询系统",""))) {
            sendMsg(qun,"还没开");
            return;
        }
        putString(qun, "查询系统", null);
        sendMsg(qun,"已关闭");
    }
    if(text.equals("开启词条系统")) {
        if("1".equals(getString(qun,"词条系统",""))) {
            sendMsg(qun,"已经开了");
            return;
        }
        putString(qun,"词条系统","1");
        sendMsg(qun,"已开启");
    }
    if(text.equals("关闭词条系统")) {
        if(!"1".equals(getString(qun,"词条系统",""))) {
            sendMsg(qun,"还没开");
            return;
        }
        putString(qun, "词条系统", null);
        sendMsg(qun,"已关闭");
    }
    if(text.equals("开启进群欢迎")) {
        if("1".equals(getString(qun,"进群欢迎",""))) {
            sendMsg(qun,"已经开了");
            return;
        }
        putString(qun,"进群欢迎","1");
        sendMsg(qun,"已开启");
    }
    if(text.equals("关闭进群欢迎")) {
        if(!"1".equals(getString(qun,"进群欢迎",""))) {
            sendMsg(qun,"还没开");
            return;
        }
        putString(qun, "进群欢迎", null);
        sendMsg(qun,"已关闭");
    }
    if(text.equals("开启菜单屏蔽")) {
        if("1".equals(getString(qun,"菜单屏蔽",""))) {
            sendMsg(qun,"已经开了");
            return;
        }
        putString(qun,"菜单屏蔽","1");
        sendMsg(qun,"已开启");
    }
    if(text.equals("关闭菜单屏蔽")) {
        if(!"1".equals(getString(qun,"菜单屏蔽",""))) {
            sendMsg(qun,"还没开");
            return;
        }
        putString(qun, "菜单屏蔽", null);
        sendMsg(qun,"已关闭");
    }
    if(text.equals("开启菜单限制")) {
        if("1".equals(getString(qun,"菜单限制",""))) {
            sendMsg(qun,"已经开了");
            return;
        }
        putString(qun,"菜单限制","1");
        sendMsg(qun,"已开启");
    }
    if(text.equals("关闭菜单限制")) {
        if(!"1".equals(getString(qun,"菜单限制",""))) {
            sendMsg(qun,"还没开");
            return;
        }
        putString(qun, "菜单限制", null);
        sendMsg(qun,"已关闭");
    }
    if(text.equals("开启音乐系统")) {
        if("1".equals(getString(qun,"音乐系统",""))) {
            sendMsg(qun,"已经开了");
            return;
        }
        putString(qun,"音乐系统","1");
        sendMsg(qun,"已开启");
    }
    if(text.equals("关闭音乐系统")) {
        if(!"1".equals(getString(qun,"音乐系统",""))) {
            sendMsg(qun,"还没开");
            return;
        }
        putString(qun, "音乐系统", null);
        sendMsg(qun,"已关闭");
    }
    if(text.equals("开启图片系统")) {
        if("1".equals(getString(qun,"图片系统",""))) {
            sendMsg(qun,"已经开了");
            return;
        }
        putString(qun,"图片系统","1");
        sendMsg(qun,"已开启");
    }
    if(text.equals("关闭图片系统")) {
        if(!"1".equals(getString(qun,"图片系统",""))) {
            sendMsg(qun,"还没开");
            return;
        }
        putString(qun, "图片系统", null);
        sendMsg(qun,"已关闭");
    }
    if(text.equals("开启智能系统")) {
        if("1".equals(getString(qun,"智能系统",""))) {
            sendMsg(qun,"已经开了");
            return;
        }
        putString(qun,"智能系统","1");
        sendMsg(qun,"已开启");
    }
    if(text.equals("关闭智能系统")) {
        if(!"1".equals(getString(qun,"智能系统",""))) {
            sendMsg(qun,"还没开");
            return;
        }
        putString(qun, "智能系统", null);
        sendMsg(qun,"已关闭");
    }
    if(text.equals("开启视频系统")) {
        if("1".equals(getString(qun,"视频系统",""))) {
            sendMsg(qun,"已经开了");
            return;
        }
        putString(qun,"视频系统","1");
        sendMsg(qun,"已开启");
    }
    if(text.equals("关闭视频系统")) {
        if(!"1".equals(getString(qun,"视频系统",""))) {
            sendMsg(qun,"还没开");
            return;
        }
        putString(qun, "视频系统", null);
        sendMsg(qun,"已关闭");
    }
    if(text.equals("开启搜索功能")) {
        if("1".equals(getString(qun,"搜索功能",""))) {
            sendMsg(qun,"已经开了");
            return;
        }
        putString(qun,"搜索功能","1");
        sendMsg(qun,"已开启");
    }
    if(text.equals("关闭搜索功能")) {
        if(!"1".equals(getString(qun,"搜索功能",""))) {
            sendMsg(qun,"还没开");
            return;
        }
        putString(qun, "搜索功能", null);
        sendMsg(qun,"已关闭");
    }
    if(text.equals("开启艾特回复")) {
        if("1".equals(getString(qun,"艾特回复",""))) {
            sendMsg(qun,"已经开了");
            return;
        }
        putString(qun,"艾特回复","1");
        sendMsg(qun,"已开启");
    }
    if(text.equals("关闭艾特回复")) {
        if(!"1".equals(getString(qun,"艾特回复",""))) {
            sendMsg(qun,"还没开");
            return;
        }
        putString(qun, "艾特回复", null);
        sendMsg(qun,"已关闭");
    }
    if(text.equals("开启自身撤回")) {
        if("1".equals(getString(qun,"自身撤回",""))) {
            sendMsg(qun,"已经开了");
            return;
        }
        putString(qun,"自身撤回","1");
        sendMsg(qun,"已开启");
    }
    if(text.equals("关闭自身撤回")) {
        if(!"1".equals(getString(qun,"自身撤回",""))) {
            sendMsg(qun,"还没开");
            return;
        }
        putString(qun, "自身撤回", null);
        sendMsg(qun,"已关闭");
    }
    if(text.equals("开启底部文案")) {
        if("1".equals(getString("开关","底部文案",""))) {
            sendMsg(qun,"已经开了");
            return;
        }
        putString("开关","底部文案","1");
        sendMsg(qun,"已开启");
    }
    if(text.equals("关闭底部文案")) {
        if(!"1".equals(getString("开关","底部文案",""))) {
            sendMsg(qun,"还没开");
            return;
        }
        putString("开关", "底部文案", null);
        sendMsg(qun,"已关闭");
    }
    if(text.equals("开启底部时间")) {
        if("1".equals(getString("开关","底部时间",""))) {
            sendMsg(qun,"已经开了");
            return;
        }
        putString("开关","底部时间","1");
        sendMsg(qun,"已开启");
    }
    if(text.equals("关闭底部时间")) {
        if(!"1".equals(getString("开关","底部时间",""))) {
            sendMsg(qun,"还没开");
            return;
        }
        putString("开关", "底部时间", null);
        sendMsg(qun,"已关闭");
    }
    if(text.equals("开启底部尾巴")) {
        if("1".equals(getString("开关","底部尾巴",""))) {
            sendMsg(qun,"已经开了");
            return;
        }
        putString("开关","底部尾巴","1");
        sendMsg(qun,"已开启");
    }
    if(text.equals("关闭底部尾巴")) {
        if(!"1".equals(getString("开关","底部尾巴",""))) {
            sendMsg(qun,"还没开");
            return;
        }
        putString("开关", "底部尾巴", null);
        sendMsg(qun,"已关闭",2);
    }
    if(text.startsWith("设置底部内容")) {
        text=text.substring(6);
        if(text.equals("")) {
            sendMsg(qun,"请输入内容");
            return;
        }
        putString("开关","底部内容",text);
        sendMsg(qun,"设置成功");
    }
    if(text.startsWith("设置撤回时间")) {
        text=text.substring(6);
        if(text.matches("^[1-9]\\d*$")) {
            if(110<Integer.parseInt(text)) {
                return;
            }
            putInt(qun,"撤回时间",Integer.parseInt(text));
            sendMsg(qun,"已设置为"+text+"秒");
        }
    }
    if(text.equals("开启全部功能")) {
        putString(qun, "音乐系统", "1");
        putString(qun, "图片系统", "1");
        putString(qun, "智能系统", "1");
        putString(qun, "搜索功能", "1");
        putString(qun, "自身撤回", "1");
        putString(qun, "视频系统", "1");
        putString(qun, "艾特回复", "1");
        putString(qun, "菜单限制", "1");
        putString(qun, "菜单屏蔽", "1");
        putString(qun, "进群欢迎", "1");
        putString(qun, "词条系统", "1");
        putString(qun, "查询系统", "1");
        sendm(qun,"已开启全部功能");
    }
    if(text.equals("关闭全部功能")) {
        putString(qun, "音乐系统", null);
        putString(qun, "图片系统", null);
        putString(qun, "智能系统", null);
        putString(qun, "搜索功能", null);
        putString(qun, "自身撤回", null);
        putString(qun, "视频系统", null);
        putString(qun, "艾特回复", null);
        putString(qun, "菜单限制", null);
        putString(qun, "菜单屏蔽", null);
        putString(qun, "进群欢迎", null);
        putString(qun, "词条系统", null);
        putString(qun, "查询系统", null);
        sendm(qun,"已关闭全部功能");
    }
}
public static String 文案(File f) {
    String result=null;
    Random rand=new Random();
    int n=0;
    for(Scanner sc=new Scanner(f); sc.hasNext();) {
        ++n;
        String line=sc.nextLine();
        //循环输出文件每行内容
        if(rand.nextInt(n) == 0)
            result=line;
    }
    return result;
}
import java.text.SimpleDateFormat;
public static void sendm(String qun,String text) {
    String 菜单名字=" ────云上升────";
    if(!取("开关","菜单名字").equals("")) {
        菜单名字=取("开关","菜单名字");
    }
    String e=emojilist[new Random().nextInt(emojilist.length)];
    String 昵称=菜单名字+"\n";
    text=text.replace("◇",e);
    if("1".equals(getString("开关","底部文案",""))) {
        File f=new File(JavaPath+"/云上升/文案.txt");
        text=text+"\n ───────────\n "+文案(f);
    }
    if("1".equals(getString("开关","底部时间",""))) {
        SimpleDateFormat df=new SimpleDateFormat("yy年MM月dd日HH:mm:ss");
        if("1".equals(getString(qun,"发送模式",""))) {
            df=new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        }
        Calendar calendar=Calendar.getInstance();
        String time=df.format(calendar.getTime());
        text=text+"\n ───────────\n "+time;
    }
    if("1".equals(getString("开关","底部尾巴",""))) {
        String 尾巴="这是底部尾巴";
        if(!"".equals(getString("开关","底部内容",""))) {
            尾巴=getString("开关","底部内容","");
        }
        text=text+"\n ───────────\n "+尾巴;
    }
    if("1".equals(getString(qun,"发送模式","")))
        try {
            getData(qun,昵称+text);
        } catch (Exception e) {
            Toast("错误,已自动切换为文字发送");
            putString(qun,"发送模式",null);
        } else if("2".equals(getString(qun,"发送模式","")))
        try {
            sendTextCard(qun,昵称+text);
        } catch (Exception e) {
            Toast("错误,已自动切换为文字发送");
            putString(qun,"发送模式",null);
        } else {
        sendMsg(qun,昵称+text);
    }
}

public boolean[] boolArr=new boolean[13];
public String[] kname=new String[] {"开关","菜单屏蔽","菜单限制","音乐系统","图片系统","搜索功能","智能系统","视频系统","艾特回复","进群欢迎","自身撤回","词条系统","查询系统"};
public String[] ww=new String[] {"开/关机","菜单屏蔽","菜单限制","音乐系统","图片系统","搜索功能","智能系统","视频系统","艾特回复","进群欢迎","自身撤回","词条系统","查询系统"};

public void 开关设置(String qun) {
    initActivity();
    int i=0;
    for(String tex: kname) {
        if(!取(qun,tex).equals("1")) {
            boolArr[i]=false;
        } else {
            boolArr[i]=true;
        }
        i++;
    }
    ThisActivity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder dialog = new AlertDialog.Builder(ThisActivity,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            dialog.setTitle(Html.fromHtml("<font color=\"red\">开关设置</font>"));
            dialog.setMultiChoiceItems(ww, boolArr, new DialogInterface.OnMultiChoiceClickListener() {
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    boolArr[which]=isChecked;
                }
            });
            dialog.setPositiveButton(Html.fromHtml("<font color=\"#893BFF\">确认</font>"),new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    boolean[] cs=boolArr;
                    i=0;
                    for(String tex:kname) {
                        if(cs[i]==false) {
                            存(qun,tex,null);
                        } else {
                            存(qun,tex,"1");
                        }
                        i++;
                    }
                    Toast("设置成功");
                    dialog.dismiss();
                }
            });
            dialog.setNegativeButton(Html.fromHtml("<font color=\"#E3319D\">取消</font>"),new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
//Toast("a");
                    dialog.dismiss();
                }
            });
            dialog.setCancelable(false);
            dialog.show();
        }
    });
}
public void 所有群设置() {
    initActivity();
    ThisActivity.runOnUiThread(new Runnable() {
        public void run() {
            List like = new ArrayList();
            List like2 = new ArrayList();
            for(String qun:getGroups()) {
                if("1".equals(getString(qun,"开关",""))) {
                    like.add("[√]"+getName(qun)+"("+getChatMembers(qun)+")");
                } else {
                    like.add("[×]"+getName(qun)+"("+getChatMembers(qun)+")");
                }
                like2.add(qun);
            }
            String[] items = (String[])like.toArray(new String[like.size()]);
            LinearLayout cy=new LinearLayout(ThisActivity);
            cy.setOrientation(LinearLayout.VERTICAL);
            new AlertDialog.Builder(ThisActivity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).setTitle(Html.fromHtml("<font color=\"red\">所有群设置</font>")).setPositiveButton(Html.fromHtml("<font color=\"#E3319D\">取消</font>"), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            }).setNeutralButton(Html.fromHtml("<font color=\"#893BFF\">开启所有群</font>"), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    for(String qun:getGroups()) {
                        存(qun,"开关","1");
                    }
                    所有群设置();
                }
            }).setNegativeButton(Html.fromHtml("<font color=\"#93A9DF\">关闭所有群</font>"), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    for(String qun:getGroups()) {
                        存(qun,"开关",null);
                    }
                    所有群设置();
                }
            }).setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String qun=like2.get(which);
                    int i=0;
                    for(String tex: kname) {
                        if(!取(qun,tex).equals("1")) {
                            boolArr[i]=false;
                        } else {
                            boolArr[i]=true;
                        }
                        i++;
                    }
                    ThisActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(ThisActivity,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                            dialog.setTitle(Html.fromHtml("<font color=\"red\">开关设置</font>"));
                            dialog.setMultiChoiceItems(ww, boolArr, new DialogInterface.OnMultiChoiceClickListener() {
                                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                    boolArr[which]=isChecked;
                                }
                            });
                            dialog.setPositiveButton(Html.fromHtml("<font color=\"#893BFF\">确认</font>"),new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    boolean[] cs=boolArr;
                                    i=0;
                                    for(String tex:kname) {
                                        if(cs[i]==false) {
                                            存(qun,tex,null);
                                        } else {
                                            存(qun,tex,"1");
                                        }
                                        i++;
                                    }
                                    所有群设置();
                                    dialog.dismiss();
                                }
                            });
                            dialog.setNegativeButton(Html.fromHtml("<font color=\"#E3319D\">取消</font>"),new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    所有群设置();
                                    dialog.dismiss();
                                }
                            });
                            dialog.setCancelable(false);
                            dialog.show();
                        }
                    });
                }
            }).setCancelable(false).show();
        }
    });
}