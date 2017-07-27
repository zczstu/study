/**
 * Created by jojo on 2017/7/25.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.net.URLConnection;
import java.net.URL;
import java.net.HttpURLConnection;
import java.sql.BatchUpdateException;

public class spstu2 {
    public static String httprequest(String requesturl){
        StringBuffer buffer = null;
        try{
            //建立连接
            URL url = new URL(requesturl);
            HttpURLConnection httpurlconn = (HttpURLConnection) url.openConnection();
            httpurlconn.setDoInput(true);
            httpurlconn.setRequestMethod("GET");

            //获取输入流
            InputStream inputStream = httpurlconn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            //读取返回结果
            buffer = new StringBuffer();
            String str = null;
            while ((str=bufferedReader.readLine()) != null){
                buffer.append(str+"\n");
            }

            //释放资源
            bufferedReader.close();
            inputStream.close();
            inputStreamReader.close();
            httpurlconn.disconnect();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public static void main(String[] args) {
        System.out.println(httprequest("https://www.bilibili.com"));
    }
}
