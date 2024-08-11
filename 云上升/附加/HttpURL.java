import java.nio.charset.StandardCharsets;
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
        if (!取("开关","GLMToken").equals("")) {
            Bearer = 取("开关","GLMToken");
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


public String GLM(String url, String jsonPost) {
    try {
        StringBuffer buffer = new StringBuffer();
        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setRequestMethod("POST");
        String Bearer = "";

        if (!取("开关","GLMToken").equals("")) {
            Bearer = 取("开关","GLMToken");
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

