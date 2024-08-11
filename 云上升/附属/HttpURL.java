public String get2(String url) {
    try {
        URL url = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        isr = new InputStreamReader(connection.getInputStream(), "utf-8");
        BufferedReader reader = new BufferedReader(isr);
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        connection.disconnect();
        return response.toString();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

public static String getQrCodePost(String actionUrl, String imageUrl) {
    String result = "";
    String BOUNDARY = UUID.randomUUID().toString();
    String PREFIX = "--", LINEND = "\r\n";
    String MULTIPART_FROM_DATA = "multipart/form-data";
    String CHARSET = "UTF-8";
    try {
        // 下载图片链接的内容
        URL url = new URL(imageUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10 * 1000);
        conn.setDoInput(true);
        conn.setRequestMethod("GET");
        // 检查连接是否成功
        if (conn.getResponseCode() != 200) {
            return "10001";
        }
        // 获取图片内容
        InputStream inputStream = conn.getInputStream();
        byte[] imageBytes = inputStream.readAllBytes();
        inputStream.close();
        // 上传图片
        conn = (HttpURLConnection) new URL(actionUrl).openConnection();
        conn.setReadTimeout(10 * 1000);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        String Bearer = "";
        if (!取("开关","accessToken").equals("")) {
            Bearer = 取("开关","accessToken");
        }
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("authorization",Bearer);
        conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);
        DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
        // 发送图片数据
        outStream.writeBytes(PREFIX + BOUNDARY + LINEND);
        outStream.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"image.jpg\"" + LINEND);
        outStream.writeBytes("Content-Type: application/octet-stream" + LINEND);
        outStream.writeBytes(LINEND);
        outStream.write(imageBytes);
        outStream.writeBytes(LINEND);
        // 请求结束标志
        outStream.writeBytes(PREFIX + BOUNDARY + PREFIX + LINEND);
        outStream.flush();
        // 获取响应
        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            InputStream responseStream = conn.getInputStream();
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(responseStream));
            String responseLine;
            while ((responseLine = responseReader.readLine()) != null) {
                result += responseLine;
            }
            responseReader.close();
        } else {
            throw new IOException( responseCode);
        }
        outStream.close();
        conn.disconnect();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return result;
}
public static String uploadFile(String urlStr, String formName, String filePath) {
    long start = System.currentTimeMillis();
    File file = new File(filePath.replace("\\/","/"));
    if (!file.exists() || !file.canRead()) {
        return "10001";
    }
    long fileSize = file.length();
    if (fileSize > 20 * 1024 * 1024) {
        return "10002";
    }
    String baseResult = null;
    try {
        final String newLine = "\r\n";
        final String boundaryPrefix = "--";
        String BOUNDARY = "------" + System.currentTimeMillis();// 模拟数据分隔线
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");// 设置为POST请求
        conn.setDoOutput(true);
        conn.setDoInput(true);
        String Bearer = "";
        if (!取("开关","accessToken").equals("")) {
            Bearer = 取("开关","accessToken");
        }
        conn.setRequestProperty("Connection", "keep-alive");
        conn.setRequestProperty("Accept","application/json, text/plain, */*");
        conn.setRequestProperty("authorization",Bearer);
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
        OutputStream out = conn.getOutputStream();
        StringBuilder sb = new StringBuilder();
        sb.append(boundaryPrefix);
        sb.append(BOUNDARY);
        sb.append(newLine);
        sb.append("Content-Disposition: form-data; name=\"").append(formName).append("\";filename=\"").append(file.getName()).append("\"").append(newLine);
        String extension = "zip";
        String filename = file.getName();
        int i = filename.lastIndexOf('.');
        if (i > 0) {
            extension = filename.substring(i+1);
        }
        sb.append("Content-Type: application/"+extension);
        sb.append(newLine);
        sb.append(newLine);
        out.write(sb.toString().getBytes());// 将参数头的数据写入到输出流中
        DataInputStream in = new DataInputStream(new FileInputStream(file));// 数据输入流,用于读取文件数据
        byte[] bufferOut = new byte[2 * 1024* 1024]; // 2 M
        int bytes = 0;
        final int uploadAvailable = in.available();
        int curUploadSize = 0;
        StringBuilder prgBar = new StringBuilder();
        long duration = System.currentTimeMillis();
        while ((bytes = in.read(bufferOut)) != -1) {// 每次读2M数据,并且将文件数据写入到输出流中
            out.write(bufferOut, 0, bytes);
            curUploadSize += bytes;
            if ((System.currentTimeMillis() - duration) >= 100) {
                prgBar.append("=");
                String percent = String.format("%.2f", (curUploadSize / (float)uploadAvailable) * 100);
                String tail = "=>[" + percent + "]";
                System.out.println(prgBar.toString() + tail);
                duration = System.currentTimeMillis();
            }
        }
        System.out.println(prgBar.toString() + "==>[" + 100 + "]");
        System.out.println("send tcp packet data..");
        out.write(newLine.getBytes());
        in.close();
        byte[] end_data = (newLine + boundaryPrefix + BOUNDARY + boundaryPrefix + newLine).getBytes();
        out.write(end_data);
        out.flush();
        out.close();
        StringBuilder builder = new StringBuilder();
        builder.append(conn.getResponseCode()) //<===注意，实际发送请求的代码段就在这里
        .append(" ")
        .append(conn.getResponseMessage())
        .append("\n");
        Map map = conn.getHeaderFields();
        for (Map.Entry entry : map.entrySet()) {
            if (entry.getKey() == null)
                continue;
            builder.append(entry.getKey())
            .append(": ");
            List headerValues = entry.getValue();
            Iterator it = headerValues.iterator();
            if (it.hasNext()) {
                builder.append(it.next());
                while (it.hasNext()) {
                    builder.append(", ")
                    .append(it.next());
                }
            }
            builder.append("\n");
        }
        System.out.println(builder);
        //读取响应体
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = null;
        StringBuilder retStr = new StringBuilder("");
        while ((line = reader.readLine()) != null) {
            retStr.append(line);
        }
        baseResult = retStr.toString();
        conn.disconnect();
    } catch (Exception e) {
        baseResult = e.getMessage();
    }
    return baseResult;
}
public String get(String url,String Cookie) {
    StringBuffer buffer = new StringBuffer();
    InputStreamReader isr = null;
    try {
        URL urlObj = new URL(url);
        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
        URLConnection uc = urlObj.openConnection();
        uc.setRequestProperty("Cookie",Cookie);
        uc.setConnectTimeout(10000);
        uc.setReadTimeout(10000);
        isr = new InputStreamReader(uc.getInputStream(), "utf-8");
        BufferedReader reader = new BufferedReader(isr); //缓冲
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line + "\n");
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (null != isr) {
                isr.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    if(buffer.length()==0)return buffer.toString();
    buffer.delete(buffer.length()-1,buffer.length());
    return buffer.toString();
}
public static String xingye(String url, String jsonPost) {
    try {
        StringBuffer buffer = new StringBuffer();
        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Host","api.xingyeai.com");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        String auth="";
        if(!取("xingye","auth").equals("")) {
            auth=取("xingye","auth");
        }
        connection.setRequestProperty("x-token",auth);
        connection.setConnectTimeout(30000); // 连接超时时间为30秒
        connection.setReadTimeout(35000);   // 读取超时时间为35秒

        connection.setDoOutput(true);

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(jsonPost.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            //返回200 OK，处理响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            reader.close();
        } else {
            //处理非200响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "utf-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            reader.close();
        }

        return buffer.toString();
    } catch (Exception e) {

        e.printStackTrace();
        return "发生错误: " + e.getMessage();
    }
}
public String get(String url) {
    try {
        StringBuffer buffer = new StringBuffer();
        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        connection.setConnectTimeout(30000); // 连接超时时间为30秒
        connection.setReadTimeout(35000);   // 读取超时时间为35秒
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            //返回200 OK，处理响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            reader.close();
        } else {
            //处理非200响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "utf-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            reader.close();
        }

        return buffer.toString();
    } catch (Exception e) {

        e.printStackTrace();
        return "发生错误: " + e.getMessage();
    }
}
public String qqLyricget(String url) {
    try {
        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.105 Safari/537.36");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (iPad; CPU OS 13_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/87.0.4280.77 Mobile/15E148 Safari/604.1");
        connection.setRequestProperty("referer", "https://y.qq.com/");
        


        isr = new InputStreamReader(connection.getInputStream(), "utf-8");
        BufferedReader reader = new BufferedReader(isr);
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        connection.disconnect();
        return response.toString();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
public String pdget(String url) {
    try {
        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.setRequestProperty("devid","97cdfbe3f8d364df");
        connection.setRequestProperty("host", "bd-api.kuwo.cn");
        connection.setRequestProperty("user-agent", "Dart/2.19 (dart:io)");
        connection.setRequestProperty("plat", "ar");
        connection.setRequestProperty("channel", "xiaomi");
        connection.setRequestProperty("ver", "4.1.3");

        isr = new InputStreamReader(connection.getInputStream(), "utf-8");
        BufferedReader reader = new BufferedReader(isr);
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        connection.disconnect();
        return response.toString();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
public String douyinget(String url) {
    try {
        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.setRequestProperty("Host","webcast5-normal-hl.amemv.com");
        connection.setRequestProperty("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        connection.setRequestProperty("user-agent", "com.ss.android.ugc.aweme/280801 (Linux; U; Android 14; zh_CN; 22081212C; Build/UKQ1.230917.001;tt-ok/3.12.13.1)");

        isr = new InputStreamReader(connection.getInputStream(), "utf-8");
        BufferedReader reader = new BufferedReader(isr);
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        connection.disconnect();
        return response.toString();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
public String jsonPost(String url, String jsonPost) {
    try {
        StringBuffer buffer = new StringBuffer();
        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");

        connection.setConnectTimeout(5000); // 连接超时时间为5秒
        connection.setReadTimeout(10000);   // 读取超时时间为10秒

        connection.setDoOutput(true);

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(jsonPost.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            //返回200 OK，处理响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            reader.close();
        } else {
            //处理非200响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "utf-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            reader.close();
        }

        return buffer.toString();
    } catch (Exception e) {

        e.printStackTrace();
        return "发生错误: " + e.getMessage();
    }
}
public String GLMGET(String url) {
    try {
        StringBuffer buffer = new StringBuffer();
        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        String Bearer = "";
        if (!取("开关","accessToken").equals("")) {
            Bearer = 取("开关","accessToken");
        }
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", Bearer);



        connection.setConnectTimeout(30000); // 连接超时时间为30秒
        connection.setReadTimeout(35000);   // 读取超时时间为35秒




        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            //返回200 OK，处理响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            reader.close();
        } else {
            //处理非200响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "utf-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            reader.close();
        }

        return buffer.toString();
    } catch (Exception e) {

        e.printStackTrace();
        return "发生错误: " + e.getMessage();
    }
}

public String RGLM(String url, String jsonPost) {
    try {
        StringBuffer buffer = new StringBuffer();
        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setRequestMethod("POST");
        String Bearer = "";

        if (!取("开关","refreshtoken").equals("")) {
            Bearer = 取("开关","refreshtoken");
        }
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("authorization", Bearer);

        connection.setConnectTimeout(30000); // 连接超时时间为30秒
        connection.setReadTimeout(35000);   // 读取超时时间为35秒

        connection.setDoOutput(true);

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(jsonPost.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            //返回200 OK，处理响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            reader.close();
        } else {
            //处理非200响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "utf-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            reader.close();
        }

        return buffer.toString();
    } catch (Exception e) {

        e.printStackTrace();
        return "发生错误: " + e.getMessage();
    }
}
public String GLM(String url, String jsonPost) {
    try {
        StringBuffer buffer = new StringBuffer();
        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setRequestMethod("POST");
        String Bearer = "";

        if (!取("开关","accessToken").equals("")) {
            Bearer = 取("开关","accessToken");
        }
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", Bearer);

        connection.setConnectTimeout(30000); // 连接超时时间为30秒
        connection.setReadTimeout(35000);   // 读取超时时间为35秒

        connection.setDoOutput(true);

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(jsonPost.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            //返回200 OK，处理响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            reader.close();
        } else {
            //处理非200响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "utf-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            reader.close();
        }

        return buffer.toString();
    } catch (Exception e) {

        e.printStackTrace();
        return "发生错误: " + e.getMessage();
    }
}
public String httppost1(String urlPath, String cookie,String data) {
    StringBuffer buffer = new StringBuffer();
    InputStreamReader isr = null;
    try {
        URL url = new URL(urlPath);
        uc = (HttpURLConnection) url.openConnection();
        uc.setDoInput(true);
        uc.setDoOutput(true);
        uc.setConnectTimeout(2000000);
        uc.setReadTimeout(2000000);
        uc.setRequestMethod("POST");
        uc.setRequestProperty("Content-Type", "application/json");
        uc.setRequestProperty("Cookie",cookie);
        uc.getOutputStream().write(data.getBytes("UTF-8"));
        uc.getOutputStream().flush();
        uc.getOutputStream().close();
        isr = new InputStreamReader(uc.getInputStream(), "utf-8");
        BufferedReader reader = new BufferedReader(isr);
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line + "\n");
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (null != isr) {
                isr.close();
            }
        } catch (IOException e) {
            Toast("错误:\n"+e);
        }
    }
    if(buffer.length()==0)return buffer.toString();
    buffer.delete(buffer.length()-1,buffer.length());
    return buffer.toString();
}