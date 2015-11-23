import java.io.FileReader;
import java.io.FileWriter;
import java.util.Comparator;
import java.lang.*;
import java.util.*;
/**
 * Created by liutong on 15-11-16.
 * 对得到的数据进行排序
 */

public class Sort {
//    内部类，实现排序接口
//    数据格式为：15839,15,3,"巴特多伯兰","Bad Doberan",0,0,"http://place.qyer.com/bad-doberan/","2015-11-16 16:03:22"
//    按照id15839进行排序
    public static class infro implements Comparable<infro>{
        public String str;
        public infro(String str1){
            str=str1;
        }
        public int compareTo(infro other){
            String[] substr=str.split(",",2);
            String[] othersubstr=other.str.split(",",2);
            int subint1=Integer.parseInt(substr[0]);
            int subint2=Integer.parseInt(othersubstr[0]);
            return subint1<subint2?-1:1;
        }
    }
//    将所有的数据按行，以infro的形式存入链表中，对链表进行排序
    public static void main(String[]args){
        List<infro>Sortinfro=new LinkedList<infro>();
        try{
            FileReader read=new FileReader("/Users/liutong/Desktop/Cities.txt");
            Scanner in=new Scanner(read);
            while(in.hasNext()){
                String s=in.nextLine();
                Sortinfro.add(new infro(s));
            }
            read.close();
            Collections.sort(Sortinfro);//排序
            //排序好的数据存入到相应的文件中
            FileWriter write=new FileWriter("/Users/liutong/Desktop/City.txt",true);
            ListIterator<infro>iter=Sortinfro.listIterator();
            while(iter.hasNext()){
                infro s2 = iter.next();
                write.write(s2.str+"\r\n");
                System.out.println(s2.str);
            }
            write.close();
        }catch(Exception e){

        }
    }
}
