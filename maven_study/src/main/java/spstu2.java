/**
 * Created by jojo on 2017/7/25.
 */

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.net.URL;
import java.net.HttpURLConnection;
import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.concurrent.Callable;


public class spstu2 {
    /**
     * get url and print http get request returns
     * @param url
     * @throws MalformedURLException
     *
        just means create Observalbe object, in this case, i will create observalbe<URL>
        map means transform current observalbe<T> to observable<S>
        subscribe means do action in every <T> in observable
        i use lambda expression and functional reference, it's tough a little :)
        try to understand, your next version should like this.
     */
    public static void rx_version(String url) throws MalformedURLException {
        Function<URL,HttpURLConnection> cast_and_set_attribute = (url_ )-> {
            try {
                HttpURLConnection t = (HttpURLConnection)url_.openConnection();
                t.setDoInput(true);
                t.setRequestMethod("GET");
                return t;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        };

        Observable.just(new URL(url))
                .map(cast_and_set_attribute)
                .map(httpURLConnection -> httpURLConnection.getInputStream())
//                .map(InputStreamReader::new) // without close stream
                .flatMap(inputStream -> Observable.using(
                        () -> inputStream,
                        d -> Observable.just(new InputStreamReader(d)),
                        d -> {
                            System.out.println("close inputStream");
                            d.close();}
                ))//extend lambda expression below
//                .flatMap(inputStream -> Observable.using(new Callable<InputStream>() {
//                    @Override
//                    public InputStream call() throws Exception {
//                        return inputStream;
//                    }
//                },new Function<InputStream, ObservableSource<? extends InputStreamReader>>() {
//                    @Override
//                    public ObservableSource<? extends InputStreamReader> apply(InputStream d) throws Exception {
//                        return Observable.just(new InputStreamReader(d));
//                    }
//                },new Consumer<InputStream>() {
//                    @Override
//                    public void accept(InputStream d) throws Exception {
//                        System.out.println("close inputStream");
//                        d.close();
//                    }
//                }))
                .map(BufferedReader::new)
                .map(new Function<BufferedReader, Object>() {
                    @Override
                    public Object apply(BufferedReader bufferedReader) throws Exception {
                        String str = null;
                        StringBuffer buffer = new StringBuffer();
                        while ((str=bufferedReader.readLine()) != null){
                            buffer.append(str+"\n");
                        }
                        return buffer;
                    }
                })
                .subscribe(System.out::println);
    }
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

    public static void main(String[] args) throws MalformedURLException {

        rx_version("https://www.bilibili.com");
//        System.out.println(httprequest("https://www.bilibili.com"));
    }
}
