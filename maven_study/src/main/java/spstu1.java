/**
 * Created by jojo on 2017/7/25.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class spstu1 {
    public static void main(String[] args) {
        //定义访问的连接
        String url = "https://www.bilibili.com";
        //定义字符串储存网页内容
        String result = "";
        //定义一个缓冲字符输入流
        BufferedReader in = null;
        try {
            //讲string转化为url对象
            URL realUrl = new URL(url);
            //初始化一个连接到那个url的连接
            URLConnection connection = realUrl.openConnection();
            //开始实际的链接
            connection.connect();
            //初始化BufferedReader输入流来读取URL的反应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            //用来临时储存抓到的每一行数据
            String line;
            while ((line = in.readLine()) != null) {
                //遍历抓取到的每一行数据并储存到result里
                result += line + "\n";
            }

        }
        catch (Exception e) {
            System.out.println("error"+e);
            e.printStackTrace();
        }
        //使用finally关闭输入流
        finally {
            try{
                if(in != null) {
                    in.close();
                }
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        System.out.println(result);
    }
}
