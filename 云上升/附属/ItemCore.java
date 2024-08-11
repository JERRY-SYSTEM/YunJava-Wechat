HashMap FileMemCache = new HashMap();

public void 写(QQUin, SetName, ItemName, data) {
    try {
        新建(JavaPath + "/缓存/" + QQUin);
        String UserData = 读(JavaPath + "/缓存/" + QQUin + "/" + SetName + ".json");
        JSONObject UserDataJson = null;
        if(UserData.equals("")) {
            UserDataJson = new JSONObject("{}");
        } else {
            UserDataJson = new JSONObject(UserData);
        }
        UserDataJson.put(ItemName, data);
        写(JavaPath + "/缓存/" + QQUin + "/" + SetName + ".json", UserDataJson.toString());
        return;
    } catch(Exception e) {
        return;
    }
}

public String 取(QQUin, SetName, ItemName) {
    try {
        String UserData = 读(JavaPath + "/缓存/" + QQUin + "/" + SetName + ".json");
        JSONObject UserDataJson = null;
        if(UserData.equals("")) {
            return "";
        } else {
            UserDataJson = new JSONObject(UserData);
        }
        if(!UserDataJson.has(ItemName)) return "";
        return UserDataJson.getString(ItemName);
    } catch(Exception e) {
        return "";
    }
}

public void 写(QQUin, SetName, ItemName, long data) {
    try {
        新建(JavaPath + "/缓存/" + QQUin);
        String UserData = 读(JavaPath + "/缓存/" + QQUin + "/" + SetName + ".json");
        JSONObject UserDataJson = null;

        if(UserData.equals("")) {
            UserDataJson = new JSONObject("{}");
        } else {
            UserDataJson = new JSONObject(UserData);
        }
        UserDataJson.put(ItemName, String.valueOf(data));

        写(JavaPath + "/缓存/" + QQUin + "/" + SetName + ".json", UserDataJson.toString());
        return;
    } catch(Exception e) {
        Toast(e+"");
        return;
    }
}

public long 读(QQUin, SetName, ItemName) {
    try {
        String UserData = 读(JavaPath + "/缓存/" + QQUin + "/" + SetName + ".json");
        JSONObject UserDataJson = null;
        if(UserData.equals("")) {
            return 0;
        } else {
            UserDataJson = new JSONObject(UserData);
        }
        if(!UserDataJson.has(ItemName)) return 0;
        return Long.parseLong(UserDataJson.getString(ItemName));;
    } catch(Exception e) {
        return 0;
    }
}


public String 读(String FilePath) {
    try {
        if(FileMemCache.containsKey(FilePath)) {
            return FileMemCache.get(FilePath);
        }
        File file = new File(FilePath);
        if(!file.exists()) {
            file.createNewFile();
        }
        InputStreamReader inputReader = new InputStreamReader(new FileInputStream(file));
        BufferedReader bf = new BufferedReader(inputReader);
        String Text = "";
        while((str = bf.readLine()) != null) {
            Text = Text + "\n"+str;
        }
        if(Text.isEmpty()) {
            return "";
        }
        FileMemCache.put(FilePath,Text.substring(1));
        return Text.substring(1);
    } catch(IOException ioe) {
        return "";
    }
}
public void 写(String Path, WriteData) {
    try {
        FileMemCache.put(Path,WriteData);
        File file = new File(Path);
        FileOutputStream fos = new FileOutputStream(file);
        if(!file.exists()) {
            file.createNewFile();
        }
        byte[] bytesArray = WriteData.getBytes();
        fos.write(bytesArray);
        fos.flush();
    } catch(IOException ioe) {

    }
}
public void 新建(String Path) {
    File dir = null;
    try {
        dir = new File(Path);
        if(!dir.exists()) {
            dir.mkdirs();
        }
    } catch(Exception e) {
        Toast("创建文件夹时发生错误,相关信息:\n" + e);
    }
}