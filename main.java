//勿改
String 脚本作者="云上升";
String 更新时间="24年5月26日";
String 当前版本=PluginVersion;
long 开始加载=System.currentTimeMillis();
//附属
Thread 附属=new Thread(new Runnable() {
    public void run() {
        loadJava(JavaPath+"/云上升/附属/import.java");
        loadJava(JavaPath+"/云上升/附属/云上升.java");
        loadJava(JavaPath+"/云上升/附属/微信卡片.java");
        loadJava(JavaPath+"/云上升/附属/ItemCore.java");
        loadJava(JavaPath+"/云上升/附属/HttpURL.java");
    }
});附属.start();
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
        loadJava(JavaPath+"/云上升/系统/解析系统.java");
    }
});系统.start();
try {
    附属.join();
    系统.join();
} catch (InterruptedException e) {
    Thread.currentThread().interrupt();
}
//耗时
long 结束加载=System.currentTimeMillis();
double 加载耗时=(结束加载-开始加载)/1000.0;
Toast("["+脚本作者+"]加载成功,耗时:"+加载耗时+"秒!");