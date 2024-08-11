public void 菜单(Object data) {
    String text=data.content;
    String qun=data.talker;
    String wxid=data.sendTalker;
    if(!取(qun,"智能回复").equals("1")||data.talkerType==0&&取("开关","智能回复").equals("1")) {
        if(wxid.equals(AuthorWxid)||mWxid.equals(wxid)) {
            开关(data);
        }
        if("1".equals(getString(qun,"艾特回复",""))) {
            艾特(data);
        }
        String 菜单限制=data.sendTalker;
        if("1".equals(取(qun,"菜单限制"))) {
            菜单限制=mWxid;
        }
        if(wxid.equals(AuthorWxid)||菜单限制.equals(wxid)) {
            if("1".equals(getString(qun,"智能系统",""))) {
                智能(data);
            }
            if("1".equals(getString(qun,"音乐系统",""))) {
                音乐(data);
            }
            if("1".equals(getString(qun,"图片系统",""))) {
                图片(data);
            }
            if("1".equals(getString(qun,"搜索功能",""))) {
                搜索(data);
            }
            if("1".equals(getString(qun,"视频系统",""))) {
                视频(data);
            }
            if("1".equals(getString(qun,"词条系统",""))) {
                词条(data);
            }
            if("1".equals(getString(qun,"查询系统",""))) {
                查询(data);
            }
            if("1".equals(getString(qun,"解析系统",""))) {
                解析(data);
            }
            if(!"1".equals(取(qun,"菜单屏蔽"))) {
                String 菜单="菜单";
                if(!取("开关","触发指令").equals("")) {
                    菜单=取("开关","触发指令");
                }
                if(text.equals(菜单)) {
                    String c="◇音乐系统◇智能系统◇\n"
                             +"◇配置设置◇图片系统◇\n"
                             +"◇开关系统◇底部样式◇\n"
                             +"◇搜索功能◇开关设置◇\n"
                             +"◇版本信息◇自身撤回◇\n"
                             +"◇视频系统◇整点报时◇\n"
                             +"◇艾特回复◇进群欢迎◇\n"
                             +"◇发送模式◇词条系统◇\n"
                             +"◇查询系统◇解析系统◇\n"
                             +"◇敬请期待◇敬请期待◇";
                    sendm(qun,c);
                }
                if(text.equals("解析系统")) {
                    String 解析系统="关";
                    if("1".equals(getString(qun,"解析系统",""))) {
                        解析系统="开";
                    }
                    String c="◇短视频解析+链接\n"
                             +"◇长视频解析+链接\n"
                             +"◇开启/关闭解析系统["+解析系统+"]";
                    sendm(qun,c);
                }
                if(text.equals("查询系统")) {
                    String 查询系统="关";
                    if("1".equals(getString(qun,"查询系统",""))) {
                        查询系统="开";
                    }
                    String c="◇天气+地区\n"
                             +"◇百度+内容\n"
                             +"◇王者战力安卓+英雄\n"
                             +"◇王者战力苹果+英雄\n"
                             +"◇开启/关闭查询系统["+查询系统+"]";
                    sendm(qun,c);
                }
                if(text.equals("词条系统")) {
                    String 词条系统="关";
                    if("1".equals(getString(qun,"词条系统",""))) {
                        词条系统="开";
                    }
                    String c="◇疯狂星期四◇毒鸡汤◇\n"
                             +"◇朋友圈文案◇彩虹屁◇\n"
                             +"◇动画文案◇漫画文案◇\n"
                             +"◇游戏文案◇文学文案◇\n"
                             +"◇原创文案◇网络文案◇\n"
                             +"◇其他文案◇影视文案◇\n"
                             +"◇诗词文案◇哲学文案◇\n"
                             +"◇网易文案◇机灵文案◇\n"
                             +"◇开启/关闭词条系统["+词条系统+"]";
                    sendm(qun,c);
                }
                if(text.equals("发送模式")) {
                    String 发送模式="文字";
                    if("1".equals(取(qun,"发送模式"))) {
                        发送模式="图片";
                    } else if("2".equals(取(qun,"发送模式"))) {
                        发送模式="卡片";
                    }
                    String c="◇切换文字发送\n"
                             +"◇切换图片发送\n"
                             +"◇切换卡片发送\n"
                             +"当前是["+发送模式+"]发送";
                    sendm(qun,c);
                }
                if(text.equals("艾特回复")) {
                    String 艾特回复="关";
                    String 艾特回复类型="内容";
                    if("1".equals(getString(qun,"艾特回复",""))) {
                        艾特回复="开";
                    }
                    if("1".equals(getString(qun,"艾特回复类型",""))) {
                        艾特回复类型="智能";
                    }
                    String c="◇设置艾特回复+内容\n"
                             +"◇重置艾特回复内容\n"
                             +"◇查看艾特回复内容\n"
                             +"◇查看艾特回复变量\n\n"
                             +"Tip:当前是["+艾特回复类型+"]艾特回复\n"
                             +"◇切换内容艾特回复\n"
                             +"◇切换智能艾特回复\n"
                             +"◇开启/关闭艾特回复["+艾特回复+"]";
                    sendm(qun,c);
                }
                if(text.equals("进群欢迎")) {
                    String 进群欢迎="关";
                    if("1".equals(getString(qun,"进群欢迎",""))) {
                        进群欢迎="开";
                    }
                    String c="◇设置进群欢迎+内容\n"
                             +"◇查看进群欢迎内容\n"
                             +"◇重置进群欢迎内容\n"
                             +"◇查看进群欢迎变量\n"
                             +"◇开启/关闭进群欢迎["+进群欢迎+"]";
                    sendm(qun,c);
                }
                if(text.equals("整点报时")) {
                    String 整点报时="关";
                    if("1".equals(getString(qun,"整点报时",""))) {
                        整点报时="开";
                    }
                    String c="◇这是一个饼\n"
                             +"◇开启/关闭整点报时["+整点报时+"]";
                    sendm(qun,c);
                }
                if(text.equals("视频系统")) {
                    String 视频系统="关";
                    if("1".equals(getString(qun,"视频系统",""))) {
                        视频系统="开";
                    }
                    String c="◇随机小世界\n"
                             +"◇开启/关闭视频系统["+视频系统+"]";
                    sendm(qun,c);
                }
                if(text.equals("自身撤回")) {
                    int 撤回时间=30;
                    String 自身撤回="关";
                    if("1".equals(getString(qun,"自身撤回",""))) {
                        自身撤回="开";
                    }
                    if(getInt(qun,"撤回时间",0)!=null) {
                        撤回时间=getInt(qun,"撤回时间",30);
                    }
                    String c="◇设置撤回时间+数字\n"
                             +"当前撤回时间:"+撤回时间+"秒\n"
                             +"时间不得超过110秒\n"
                             +"◇开启/关闭自身撤回["+自身撤回+"]";
                    sendm(qun,c);
                }
                if(text.equals("版本信息")) {
                    File folder=new File(JavaPath);
                    long 结束加载=data.createTime;
                    String formattedSize=getFormattedSize(folder);
                    String c="账号昵称:"+getName(mWxid)+"\n"
                             +"目录大小:"+formattedSize+"\n"
                             +"脚本昵称:"+脚本作者+"\n"
                             +"当前版本:"+当前版本+"\n"
                             +"更新时间:"+更新时间+"\n"
                             +"运行时长:"+formatTime((float)(结束加载-开始加载))+"\n"
                             +"脚本作者:"+脚本作者;
                    sendm(qun,c);
                }
                if(text.equals("搜索功能")) {
                    String 搜索功能="关";
                    if("1".equals(getString(qun,"搜索功能",""))) {
                        搜索功能="开";
                    }
                    String c="◇堆糖搜图+内容\n"
                             +"◇搜索内容+内容\n"
                             +"◇开启/关闭搜索功能["+搜索功能+"]";
                    sendm(qun,c);
                }
                if(text.equals("音乐系统")) {
                    String 音乐系统="关";
                    if("1".equals(getString(qun,"音乐系统",""))) {
                        音乐系统="开";
                    }
                    String c="◇点歌+歌名\n"
                             +"◇QQ点歌+歌名\n"
                             +"◇VIP点歌+歌名\n"
                             +"◇网易点歌+歌名\n"
                             +"◇抖音点歌+歌名\n"
                             +"◇酷我点歌+歌名\n"
                             +"◇波点点歌+歌名\n"
                             +"◇潮汐点歌+歌名\n"
                             +"◇声破天点歌+歌名\n"
                             +"◇开启/关闭音乐系统["+音乐系统+"]";
                    sendm(qun,c);
                }
                if(text.equals("图片系统")) {
                    String 图片系统="关";
                    if("1".equals(getString(qun,"图片系统",""))) {
                        图片系统="开";
                    }
                    String c="◇七濑胡桃◇方形头像◇\n"
                             +"◇原神竖图◇原神横图◇\n"
                             +"◇萌版竖图◇萌版横图◇\n"
                             +"◇移动竖图◇白底横图◇\n"
                             +"◇风景横图◇风景横图◇\n"
                             +"◇诱惑图◇猫咪图◇\n"
                             +"◇买家秀◇兽猫酱◇\n"
                             +"◇帅哥图◇小清新◇\n"
                             +"◇动漫图◇看汽车◇\n"
                             +"◇看炫酷◇美女图◇\n"
                             +"◇风景图◇看腹肌◇\n"
                             +"◇萌宠图◇原神图◇\n"
                             +"◇看黑丝◇看白丝◇\n"
                             +"◇60秒看世界◇\n"
                             +"◇小狐狸◇\n"
                             +"◇开启/关闭图片系统["+图片系统+"]";
                    sendm(qun,c);
                }
                if(text.equals("开关系统")) {
                    String 解析系统="关";
                    String 查询系统="关";
                    String 音乐系统="关";
                    String 图片系统="关";
                    String 智能系统="关";
                    String 搜索功能="关";
                    String 自身撤回="关";
                    String 视频系统="关";
                    String 艾特回复="关";
                    String 词条系统="关";
                    String 菜单限制="关";
                    String 菜单屏蔽="关";
                    String 进群欢迎="关";
                    if("1".equals(getString(qun,"进群欢迎",""))) {
                        进群欢迎="开";
                    }
                    if("1".equals(getString(qun,"菜单屏蔽",""))) {
                        菜单屏蔽="开";
                    }
                    if("1".equals(getString(qun,"菜单限制",""))) {
                        菜单限制="开";
                    }
                    if("1".equals(getString(qun,"艾特回复",""))) {
                        艾特回复="开";
                    }
                    if("1".equals(getString(qun,"视频系统",""))) {
                        视频系统="开";
                    }
                    if("1".equals(getString(qun,"自身撤回",""))) {
                        自身撤回="开";
                    }
                    if("1".equals(getString(qun,"搜索功能",""))) {
                        搜索功能="开";
                    }
                    if("1".equals(getString(qun,"音乐系统",""))) {
                        音乐系统="开";
                    }
                    if("1".equals(getString(qun,"图片系统",""))) {
                        图片系统="开";
                    }
                    if("1".equals(getString(qun,"智能系统",""))) {
                        智能系统="开";
                    }
                    if("1".equals(getString(qun,"词条系统",""))) {
                        词条系统="开";
                    }
                    if("1".equals(getString(qun,"查询系统",""))) {
                        查询系统="开";
                    }
                    if("1".equals(getString(qun,"解析系统",""))) {
                        解析系统="开";
                    }
                    String c="◇开启/关闭菜单屏蔽["+菜单屏蔽+"]\n"
                             +"◇开启/关闭菜单限制["+菜单限制+"]\n"
                             +"◇开启/关闭音乐系统["+音乐系统+"]\n"
                             +"◇开启/关闭图片系统["+图片系统+"]\n"
                             +"◇开启/关闭智能系统["+智能系统+"]\n"
                             +"◇开启/关闭视频系统["+视频系统+"]\n"
                             +"◇开启/关闭搜索功能["+搜索功能+"]\n"
                             +"◇开启/关闭艾特回复["+艾特回复+"]\n"
                             +"◇开启/关闭进群欢迎["+进群欢迎+"]\n"
                             +"◇开启/关闭自身撤回["+自身撤回+"]\n"
                             +"◇开启/关闭词条系统["+词条系统+"]\n"
                             +"◇开启/关闭查询系统["+查询系统+"]\n"
                             +"◇开启/关闭解析系统["+解析系统+"]\n"
                             +"◇开启/关闭全部功能\n"
                             +"◇所有群设置";
                    sendm(qun,c);
                }
                if(text.equals("底部样式")) {
                    String 底部时间="关";
                    String 底部文案="关";
                    String 底部尾巴="关";
                    if("1".equals(getString("开关","底部时间",""))) {
                        底部时间="开";
                    }
                    if("1".equals(getString("开关","底部文案",""))) {
                        底部文案="开";
                    }
                    if("1".equals(getString("开关","底部尾巴",""))) {
                        底部尾巴="开";
                    }
                    String c="◇开启/关闭底部时间["+底部时间+"]\n"
                             +"◇开启/关闭底部文案["+底部文案+"]\n"
                             +"◇开启/关闭底部尾巴["+底部尾巴+"]\n"
                             +"◇设置底部内容+内容";
                    sendm(qun,c);
                }
                if(text.equals("智能系统")) {
                    String Token="已绑定";
                    String 手机号码="已绑定";
                    String 智能系统="关";
                    String 智能回复="";
                    if(取("开关","accessToken").equals("")) {
                        Token="未绑定";
                    }
                    if(取("开关","手机号码").equals("")) {
                        手机号码="未绑定";
                    }
                    if("1".equals(getString(qun,"智能系统",""))) {
                        智能系统="开";
                    }
                    if(data.isText()&&data.talkerType==0) {
                        智能回复=" -------------------------\n"
                                 +"◇开启/关闭全局回复\n"
                                 +"◇开启/关闭智能回复\n"
                                 +"开启后消息将会用AI回复\n"
                                 +"并且其他功能将无法使用\n"
                                 +" -------------------------\n";
                    }
                    String c="◇AI+问题\n"
                             +"◇画图+内容\n"
                             +"◇重新绑定\n"
                             +"◇重置对话\n"
                             +"◇我的智能体\n"
                             +"◇搜索智能体+内容\n"
                             +"◇查看智能体\n"
                             +"◇重置智能体\n"
                             +智能回复
                             +"发送[配置设置]绑定手机号\n"
                             +"◇手机状态:"+手机号码+"\n"
                             +"◇获取验证码\n"
                             +"然后输入[验证码]即可绑定\n"
                             +"◇清除绑定状态\n"
                             +"◇绑定状态:"+Token+"\n"
                             +"◇开启/关闭智能系统["+智能系统+"]";
                    sendm(qun,c);
                }
            }
        }
    }
}