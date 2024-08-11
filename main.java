//勿改
String 脚本作者="云上升";
String 更新时间="24年5月2日";
String 当前版本=PluginVersion;
String 作者扣扣="3449496653";
long 开始加载=System.currentTimeMillis();
//附加
Thread 附加=new Thread(new Runnable() {
    public void run() {
        loadJava(JavaPath+"/云上升/附加/import.java");
        loadJava(JavaPath+"/云上升/附加/云上升.java");
        loadJava(JavaPath+"/云上升/附加/微信卡片.java");
        loadJava(JavaPath+"/云上升/附加/ItemCore.java");
        loadJava(JavaPath+"/云上升/附加/HttpURL.java");
    }
});附加.start();
//系统
Thread 系统=new Thread(new Runnable() {
    public void run() {
        loadJava(JavaPath+"/云上升/系统/菜单.java");
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
    }
});系统.start();
try {
    附加.join();
    系统.join();
} catch (InterruptedException e) {
    Thread.currentThread().interrupt();
}
//耗时
long 结束加载=System.currentTimeMillis();
double 加载耗时=(结束加载-开始加载)/1000.0;
Toast("["+脚本作者+"]加载成功,耗时:"+加载耗时+"秒!");