public void 菜单(Object data) {
    String text=data.content;
    String qun=data.talker;
    String wxid=data.sendTalker;
    File 代管=new File(JavaPath+"/数据/代管/"+qun+"/代管.txt");
    if(!代管.getParentFile().exists()) {
        代管.getParentFile().mkdirs();
        if(!代管.exists()) {
            代管.createNewFile();
        }
    }
    if(!取(qun,"智能回复").equals("1")||data.talkerType==0&&取("开关","智能回复").equals("1")) {
        if(wxid.equals(AuthorWxid)||mWxid.equals(wxid)||简读用户(代管,wxid)) {
            开关(data);
            代管(data);
            //签到(data);
            if("1".equals(getString(qun,"整点报时",""))) {
                报时(data);
            }
            if("1".equals(getString(qun,"每日简报",""))) {
                简报(data);
            }
        }
        if("1".equals(getString(qun,"艾特回复",""))) {
            艾特(data);
        }
        String 菜单限制=data.sendTalker;
        if("1".equals(取(qun,"菜单限制"))) {
            菜单限制=mWxid;
        }
        if(wxid.equals(AuthorWxid)||菜单限制.equals(wxid)||简读用户(代管,wxid)) {
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
            if("1".equals(getString(qun,"娱乐系统",""))) {
                娱乐(data);
            }
            if("1".equals(getString(qun,"站长系统",""))) {
                站长(data);
            }
            if(!"1".equals(取(qun,"菜单屏蔽"))) {
                String 菜单="菜单";
                if(!取("开关","触发指令").equals("")) {
                    菜单=取("开关","触发指令");
                }
                if(text.equals(菜单)) {
                    String c="╔音乐系统╦智能系统╗\n"
                             +"╠配置设置╬图片系统╣\n"
                             +"╠开关系统╬底部样式╣\n"
                             +"╠搜索功能╬开关设置╣\n"
                             +"╠版本信息╬自身撤回╣\n"
                             +"╠视频系统╬解析系统╣\n"
                             +"╠艾特回复╬进群欢迎╣\n"
                             +"╠发送模式╬词条系统╣\n"
                             +"╠每日简报╬查询系统╣\n"
                             +"╠整点报时╬站长系统╣\n"
                             +"╚娱乐系统╩代管系统╝";
                    sendm(qun,c);
                }
                if(text.equals("站长系统")) {
                    String 站长系统="×";
                    if("1".equals(getString(qun,"站长系统",""))) {
                        站长系统="√";
                    }
                    String c="╔访问+链接\n"
                             +"╠JSON+数据\n"
                             +"╠文转直链+目录\n"
                             +"╚开启/关闭站长系统["+站长系统+"]";
                    sendm(qun,c);
                }
                if(text.equals("代管系统")) {
                    String c="╔引用+添加代管\n"
                             +"╠引用+删除代管\n"
                             +"╠代管列表\n"
                             +"╚清空代管";
                    sendm(qun,c);
                }
                if(text.equals("娱乐系统")) {
                    String 娱乐系统="×";
                    if("1".equals(getString(qun,"娱乐系统",""))) {
                        娱乐系统="√";
                    }
                    String c="╔签到\n"
                             +"╚开启/关闭娱乐系统["+娱乐系统+"]";
                    sendm(qun,c);
                }
                if(text.equals("解析系统")) {
                    String 解析系统="×";
                    if("1".equals(getString(qun,"解析系统",""))) {
                        解析系统="√";
                    }
                    String c="╔短视频解析+链接\n"
                             +"╠长视频解析+链接\n"
                             +"╚开启/关闭解析系统["+解析系统+"]";
                    sendm(qun,c);
                }
                if(text.equals("查询系统")) {
                    String 查询系统="×";
                    if("1".equals(getString(qun,"查询系统",""))) {
                        查询系统="√";
                    }
                    String c="╔天气+地区\n"
                             +"╠百度+内容\n"
                             +"╠今日油价+省级\n"
                             +"╠菜谱查询+名称\n"
                             +"╠宠物查询+名称\n"
                             +"╠扩展名查询+名称\n"
                             +"╠王者战力安卓+英雄\n"
                             +"╠王者战力苹果+英雄\n"
                             +"╚开启/关闭查询系统["+查询系统+"]";
                    sendm(qun,c);
                }
                if(text.equals("词条系统")) {
                    String 词条系统="×";
                    if("1".equals(getString(qun,"词条系统",""))) {
                        词条系统="√";
                    }
                    String c="╔疯狂星期四╦毒鸡汤╗\n"
                             +"╠朋友圈文案╬彩虹屁╣\n"
                             +"╠动画文案╬漫画文案╣\n"
                             +"╠游戏文案╬文学文案╣\n"
                             +"╠原创文案╬网络文案╣\n"
                             +"╠其他文案╬影视文案╣\n"
                             +"╠诗词文案╬哲学文案╣\n"
                             +"╠网易文案╩机灵文案╝\n"
                             +"╚开启/关闭词条系统["+词条系统+"]";
                    sendm(qun,c);
                }
                if(text.equals("发送模式")) {
                    String 发送模式="文字";
                    if("1".equals(取("开关","发送模式"))) {
                        发送模式="图片";
                    } else if("2".equals(取("开关","发送模式"))) {
                        发送模式="卡片";
                    }
                    String c="╔切换文字发送\n"
                             +"╠切换图片发送\n"
                             +"╠切换卡片发送\n"
                             +"╚当前是["+发送模式+"]发送";
                    sendm(qun,c);
                }
                if(text.equals("艾特回复")) {
                    String 艾特回复="×";
                    String 回复类型="内容";
                    if("1".equals(getString(qun,"艾特回复",""))) {
                        艾特回复="√";
                    }
                    if("1".equals(getString(qun,"回复类型",""))) {
                        回复类型="智能";
                    }
                    String c="╔设置回复+内容\n"
                             +"╠重置回复内容\n"
                             +"╠查看回复内容\n"
                             +"╚查看回复变量\n\n"
                             +"╔当前是["+回复类型+"]回复\n"
                             +"╠切换内容回复\n"
                             +"╠切换智能回复\n"
                             +"╚开启/关闭艾特回复["+艾特回复+"]";
                    sendm(qun,c);
                }
                if(text.equals("进群欢迎")) {
                    String 进群欢迎="×";
                    if("1".equals(getString(qun,"进群欢迎",""))) {
                        进群欢迎="√";
                    }
                    String c="╔设置进群欢迎+内容\n"
                             +"╠查看进群欢迎内容\n"
                             +"╠重置进群欢迎内容\n"
                             +"╠查看进群欢迎变量\n"
                             +"╚开启/关闭进群欢迎["+进群欢迎+"]";
                    sendm(qun,c);
                }
                    if(text.equals("整点报时")) {
                        String 整点报时="×";
                        if("1".equals(getString(qun,"整点报时",""))) {
                            整点报时="√";
                        }
                        String c="╔测试报时\n"
                                 +"╠整点自动发送播报\n"
                                 +"╠目前只支持群使用\n"
                                 +"╚开启/关闭整点报时["+整点报时+"]";
                        sendm(qun,c);
                    }
                    if(text.equals("每日简报")) {
                        String 每日简报="×";
                        if("1".equals(getString(qun,"每日简报",""))) {
                            每日简报="√";
                        }
                        String c="╔测试简报\n"
                                 +"╠九点自动发送简报\n"
                                 +"╠目前只支持群使用\n"
                                 +"╚开启/关闭每日简报["+每日简报+"]";
                        sendm(qun,c);
                    }
                if(text.equals("视频系统")) {
                    String 视频系统="×";
                    if("1".equals(getString(qun,"视频系统",""))) {
                        视频系统="√";
                    }
                    String c="╔随机小世界\n"
                             +"╚开启/关闭视频系统["+视频系统+"]";
                    sendm(qun,c);
                }
                if(text.equals("自身撤回")) {
                    int 撤回时间=30;
                    String 自身撤回="×";
                    if("1".equals(getString(qun,"自身撤回",""))) {
                        自身撤回="√";
                    }
                    if(getInt(qun,"撤回时间",0)!=null) {
                        撤回时间=getInt(qun,"撤回时间",30);
                    }
                    String c="╔设置撤回时间+数字\n"
                             +"╠当前撤回时间:"+撤回时间+"秒\n"
                             +"╠时间不得超过110秒\n"
                             +"╚开启/关闭自身撤回["+自身撤回+"]";
                    sendm(qun,c);
                }
                if(text.equals("版本信息")) {
                    File folder=new File(JavaPath);
                    long 结束加载=data.createTime;
                    String formattedSize=getFormattedSize(folder);
                    String c="╔脚本昵称:"+脚本名称+"\n"
                             +"╠脚本作者:"+脚本作者+"\n"
                             +"╠脚本版本:"+当前版本+"\n"
                             +"╠微信版本:"+VersionName(mContext)+"("+VersionCode(mContext)+")\n"
                             +"╠账号昵称:"+getName(mWxid)+"\n"
                             +"╠目录大小:"+formattedSize+"\n"
                             +"╠运行时长:"+formatTime((float)(结束加载-开始加载))+"\n"
                             +"╚更新时间:"+更新时间;
                    sendm(qun,c);
                }
                if(text.equals("搜索功能")) {
                    String 搜索功能="×";
                    if("1".equals(getString(qun,"搜索功能",""))) {
                        搜索功能="√";
                    }
                    String c="╔搜索图片+内容\n"
                             +"╠搜索内容+内容\n"
                             +"╠搜索应用+名称\n"
                             +"╚开启/关闭搜索功能["+搜索功能+"]";
                    sendm(qun,c);
                }
                if(text.equals("音乐系统")) {
                    String 音乐系统="×";
                    if("1".equals(getString(qun,"音乐系统",""))) {
                        音乐系统="√";
                    }
                    String c="╔点歌+歌名\n"
                             +"╠QQ点歌+歌名\n"
                             //+"╠VIP点歌+歌名\n"
                             +"╠网易点歌+歌名\n"
                             +"╠酷狗点歌+歌名\n"
                             +"╠抖音点歌+歌名\n"
                             +"╠酷我点歌+歌名\n"
                             +"╠波点点歌+歌名\n"
                             +"╠咪咕点歌+歌名\n"
                             +"╠酷狗铃声+歌名\n"
                             //+"╠潮汐点歌+歌名\n"
                             //+"╠声破天点歌+歌名\n"
                             +"╚开启/关闭音乐系统["+音乐系统+"]";
                    sendm(qun,c);
                }
                if(text.equals("图片系统")) {
                    String 图片系统="×";
                    if("1".equals(getString(qun,"图片系统",""))) {
                        图片系统="√";
                    }
                    String c="╔七濑胡桃╦方形头像╗\n"
                             +"╠原神竖图╬原神横图╣\n"
                             +"╠萌版竖图╬萌版横图╣\n"
                             +"╠移动竖图╬白底横图╣\n"
                             +"╠风景横图╬风景横图╣\n"
                             +"╠诱惑图╬猫咪图╣\n"
                             +"╠买家秀╬兽猫酱╣\n"
                             +"╠帅哥图╬小清新╣\n"
                             +"╠动漫图╬看汽车╣\n"
                             +"╠看炫酷╬美女图╣\n"
                             +"╠风景图╬看腹肌╣\n"
                             +"╠萌宠图╬原神图╣\n"
                             +"╠看黑丝╬看白丝╣\n"
                             +"╠60秒看世界╣\n"
                             +"╠小狐狸╝\n"
                             +"╚开启/关闭图片系统["+图片系统+"]";
                    sendm(qun,c);
                }
                if(text.equals("开关系统")) {
                    String 站长系统="×";
                    String 娱乐系统="×";
                    String 每日简报="×";
                    String 整点报时="×";
                    String 解析系统="×";
                    String 查询系统="×";
                    String 音乐系统="×";
                    String 图片系统="×";
                    String 智能系统="×";
                    String 搜索功能="×";
                    String 自身撤回="×";
                    String 视频系统="×";
                    String 艾特回复="×";
                    String 词条系统="×";
                    String 菜单限制="×";
                    String 菜单屏蔽="×";
                    String 进群欢迎="×";
                    if("1".equals(getString(qun,"进群欢迎",""))) {
                        进群欢迎="√";
                    }
                    if("1".equals(getString(qun,"菜单屏蔽",""))) {
                        菜单屏蔽="√";
                    }
                    if("1".equals(getString(qun,"菜单限制",""))) {
                        菜单限制="√";
                    }
                    if("1".equals(getString(qun,"艾特回复",""))) {
                        艾特回复="√";
                    }
                    if("1".equals(getString(qun,"视频系统",""))) {
                        视频系统="√";
                    }
                    if("1".equals(getString(qun,"自身撤回",""))) {
                        自身撤回="√";
                    }
                    if("1".equals(getString(qun,"搜索功能",""))) {
                        搜索功能="√";
                    }
                    if("1".equals(getString(qun,"音乐系统",""))) {
                        音乐系统="√";
                    }
                    if("1".equals(getString(qun,"图片系统",""))) {
                        图片系统="√";
                    }
                    if("1".equals(getString(qun,"智能系统",""))) {
                        智能系统="√";
                    }
                    if("1".equals(getString(qun,"词条系统",""))) {
                        词条系统="√";
                    }
                    if("1".equals(getString(qun,"查询系统",""))) {
                        查询系统="√";
                    }
                    if("1".equals(getString(qun,"解析系统",""))) {
                        解析系统="√";
                    }
                    if("1".equals(getString(qun,"整点报时",""))) {
                        整点报时="√";
                    }
                    if("1".equals(getString(qun,"每日简报",""))) {
                        每日简报="√";
                    }
                    if("1".equals(getString(qun,"娱乐系统",""))) {
                        娱乐系统="√";
                    }
                    if("1".equals(getString(qun,"站长系统",""))) {
                        站长系统="√";
                    }
                    String c="╔开启/关闭菜单屏蔽["+菜单屏蔽+"]\n"
                             +"╠开启/关闭菜单限制["+菜单限制+"]\n"
                             +"╠开启/关闭音乐系统["+音乐系统+"]\n"
                             +"╠开启/关闭图片系统["+图片系统+"]\n"
                             +"╠开启/关闭娱乐系统["+娱乐系统+"]\n"
                             +"╠开启/关闭智能系统["+智能系统+"]\n"
                             +"╠开启/关闭视频系统["+视频系统+"]\n"
                             +"╠开启/关闭搜索功能["+搜索功能+"]\n"
                             +"╠开启/关闭艾特回复["+艾特回复+"]\n"
                             +"╠开启/关闭进群欢迎["+进群欢迎+"]\n"
                             +"╠开启/关闭自身撤回["+自身撤回+"]\n"
                             +"╠开启/关闭词条系统["+词条系统+"]\n"
                             +"╠开启/关闭查询系统["+查询系统+"]\n"
                             +"╠开启/关闭解析系统["+解析系统+"]\n"
                             +"╠开启/关闭站长系统["+站长系统+"]\n"
                             +"╠开启/关闭整点报时["+整点报时+"]\n"
                             +"╠开启/关闭每日简报["+每日简报+"]\n"
                             +"╠开启/关闭全部功能\n"
                             +"╚所有群设置";
                    sendm(qun,c);
                }
                if(text.equals("底部样式")) {
                    String 底部时间="×";
                    String 底部文案="×";
                    String 底部尾巴="×";
                    if("1".equals(getString("开关","底部时间",""))) {
                        底部时间="√";
                    }
                    if("1".equals(getString("开关","底部文案",""))) {
                        底部文案="√";
                    }
                    if("1".equals(getString("开关","底部尾巴",""))) {
                        底部尾巴="√";
                    }
                    String c="╔开启/关闭底部时间["+底部时间+"]\n"
                             +"╠开启/关闭底部文案["+底部文案+"]\n"
                             +"╠开启/关闭底部尾巴["+底部尾巴+"]\n"
                             +"╚设置底部内容+内容";
                    sendm(qun,c);
                }
                if(text.equals("智能系统")) {
                    String Token="已绑定";
                    String 手机号码="已绑定";
                    String 智能系统="×";
                    String 智能回复="";
                    if(取("开关","accessToken").equals("")) {
                        Token="未绑定";
                    }
                    if(取("开关","手机号码").equals("")) {
                        手机号码="未绑定";
                    }
                    if("1".equals(getString(qun,"智能系统",""))) {
                        智能系统="√";
                    }
                    if(data.isText()&&data.talkerType==0) {
                        智能回复=" -------------------------\n"
                                     +"╠开启/关闭智能回复\n"
                                     +"开启后消息将会用AI回复\n"
                                     +"并且其他功能将无法使用\n"
                                     +" -------------------------\n";
                    }
                    String c="╔AI+问题\n"
                             +"╠重新绑定\n"
                             +"╠重置对话\n"
                             +"╠我的智能体\n"
                             +"╠搜索智能体+内容\n"
                             +"╠查看智能体\n"
                             +"╠重置智能体\n"
                             +智能回复
                             +"发送[配置设置]绑定手机号\n"
                             +"╠手机状态:"+手机号码+"\n"
                             +"╠获取验证码\n"
                             +"然后输入[验证码]即可绑定\n"
                             +"╠清除绑定状态\n"
                             +"╠绑定状态:"+Token+"\n"
                             +"╚开启/关闭智能系统["+智能系统+"]";
                    sendm(qun,c);
                }
            }
        }
    }
}