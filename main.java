String 脚本作者="云上升";//勿改
String 脚本名称="YunJava";
String 更新时间="24年8月10日";
String 当前版本=PluginVersion;
long 开始加载=System.currentTimeMillis();
Thread 附属=new Thread(new Runnable() {//附属
    public void run() {
        loadJava(JavaPath+"/云上升/附属/Import.java");
        loadJava(JavaPath+"/云上升/附属/YunJava.java");
        loadJava(JavaPath+"/云上升/附属/微信卡片.java");
        loadJava(JavaPath+"/云上升/附属/ItemCore.java");
        loadJava(JavaPath+"/云上升/附属/项目核心.java");
    }
});附属.start();
Thread 系统=new Thread(new Runnable() {//系统
    public void run() {
        loadJava(JavaPath+"/云上升/系统/菜单.java");
        loadJava(JavaPath+"/云上升/系统/每日总结.java");
        loadJava(JavaPath+"/云上升/系统/自动签到.java");
        loadJava(JavaPath+"/云上升/系统/私聊播报.java");
        loadJava(JavaPath+"/云上升/系统/代管系统.java");
        loadJava(JavaPath+"/云上升/系统/站长系统.java");
        loadJava(JavaPath+"/云上升/系统/开关设置.java");
        loadJava(JavaPath+"/云上升/系统/音乐系统.java");
        loadJava(JavaPath+"/云上升/系统/智能系统.java");
        loadJava(JavaPath+"/云上升/系统/图片系统.java");
        loadJava(JavaPath+"/云上升/系统/搜索功能.java");
        loadJava(JavaPath+"/云上升/系统/视频系统.java");
        loadJava(JavaPath+"/云上升/系统/词条系统.java");
        loadJava(JavaPath+"/云上升/系统/整点报时.java");
        loadJava(JavaPath+"/云上升/系统/艾特回复.java");
        loadJava(JavaPath+"/云上升/系统/进群欢迎.java");
        loadJava(JavaPath+"/云上升/系统/查询系统.java");
        loadJava(JavaPath+"/云上升/系统/解析系统.java");
        loadJava(JavaPath+"/云上升/系统/每日简报.java");
        loadJava(JavaPath+"/云上升/系统/娱乐系统.java");
    }
});系统.start();
try {
    附属.join();
    系统.join();
} catch (InterruptedException e) {
    Thread.currentThread().interrupt();
}
long 结束加载=System.currentTimeMillis();//耗时
double 加载耗时=(结束加载-开始加载)/1000.0;
Toast("["+脚本名称+"]加载成功,耗时:"+加载耗时+"秒!");