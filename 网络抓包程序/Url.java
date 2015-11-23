import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * 网络抓包
 * Created by liutong on 15-11-12.
 * 抓取相应网页的城市信息
 */

public class Url {
    private  String UrlName;//网页URL
    private String FilePath;//要写入的文件
    private int count=0;

    //    构造器
    public Url(String UrlName,String FilePath){
        this.UrlName=UrlName;
        this.FilePath=FilePath;
    }

//    将网页中信息抓取到文件FilePath中
    public  void UrlWrite()throws Exception{
        String str1="Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.71 Safari/537.36";
        String pageData=UrlConnection(UrlName,str1);
//            利用正则表达式提取中有用信息
        String regEx="(<h3><a href=[\"])([^\"]*)([\"] target=)([^&]*)([^&]*>)([^&]*)(&)([^>]*>)([^<]*)<";
        Matcher matcher=Pattern.compile(regEx).matcher(pageData);
        String regEx2="(PID :')([0-9]*)('.*)";
        Matcher matcher2=Pattern.compile(regEx2).matcher(pageData);
        String pid="0";
        String fatherpid=null;
        while(matcher2.find()) {
            fatherpid = matcher2.group(2);
        }
        FileWriter fileWriter=new FileWriter(FilePath,true);
        while(matcher.find()){
            count++;
            String urlString=matcher.group(2);
            String cname=matcher.group(6);
            String ename=matcher.group(9);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String time=df.format(new Date());
//                System.out.println(time);// new Date()为获取当前系统时间
//            抓取每个城市对应的id
            //System.out.println(fatherpid+","+"3,"+"\""+cname+"\""+","+"\""+ename+"\""+","+"0,0,"+"\""+urlString+"\""+","+"\""+time+"\""+"\r\n");
            String str2="Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36";
            String pageData2=UrlConnection(urlString+"map/",str2);
            Matcher matcher3=Pattern.compile(regEx2).matcher(pageData2);
            while(matcher3.find()) {
                pid = matcher3.group(2);
                System.out.println(pid);
            }
//            最终的抓取信息
            String cities=pid+","+fatherpid+","+"3,"+"\""+cname+"\""+","+"\""+ename+"\""+","+"0,0,"+"\""+urlString+"\""+","+"\""+time+"\""+"\r\n";
            fileWriter.write(cities);
        }
        System.out.println("一共读入了:" + count + "个城市");
        fileWriter.close();
    }
//    获取URL网页中信息，返回到字符串String中
    public String UrlConnection(String Urlname,String property)throws Exception{
        URL url=new URL(Urlname);
        HttpURLConnection con=(HttpURLConnection)url.openConnection();
        con.addRequestProperty("User-Agent",property);
        con.addRequestProperty("Referer",Urlname);
        InputStream input=con.getInputStream();
        byte[] by = new byte[10024];
        int len=0;
        String pageData1="";
        while((len=input.read(by))!=-1){
            pageData1 +=new String(by,0,len);
        }
       // System.out.println(pageData1);
        Thread.sleep(1000);
        input.close();
        con.disconnect();
        return pageData1;
    }
}
