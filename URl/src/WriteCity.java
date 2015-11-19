import java.io.FileReader;
import java.util.Scanner;

/**
 * Created by liutong on 15-11-16.
 * 从包含国家信息的文件中，读取国家的URL,然后抓取每个国家的城市信息，写入城市文件中
 */
public class WriteCity {
    public static void main(String []args){
        try{
//            从包含国家信息文件中，依次提取出URL信息
            FileReader filereader=new FileReader("/Users/liutong/Desktop/1.csv");
            Scanner in=new Scanner(filereader);
            while(in.hasNext()) {
//                读取出URL信息，保留在字符串UrlString2中
                String UrlString=in.nextLine();
                int index1=UrlString.lastIndexOf("http");
                int index2=UrlString.lastIndexOf("\",");
                if((index1>=0)&&(index2>=0)){
                    String UrlString2=UrlString.substring(index1,index2);
                    System.out.println("国家网址："+UrlString2);
//                    得到每个国家所有城市网页的URL
                    String Url1=UrlString2+"/citylist-1-0-1/";
                    String Url2=UrlString2+"/citylist-1-0-2/";
                    String Url3=UrlString2+"/citylist-1-0-3/";
                    String filepath="/Users/liutong/Desktop/Cities.txt";
//                    抓取URL网页中的城市信息
                    try{
                        Url UrlWrite1=new Url(Url1,filepath);
                        UrlWrite1.UrlWrite();
                        Url UrlWrite2=new Url(Url2,filepath);
                        UrlWrite2.UrlWrite();
                        Url UrlWrite3=new Url(Url3,filepath);
                        UrlWrite3.UrlWrite();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }catch(Exception e){
        e.printStackTrace();
        }
    }
}
