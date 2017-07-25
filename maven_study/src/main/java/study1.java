import org.jsoup.Jsoup;

/**
 * Created by jojo on 2017/7/24.
 */
public class study1 {

    public static void main(String[] args){
        System.out.println(Tools.sayHelloToPerson("zhang"));
        System.out.println("hahahaha");
        System.out.println("hello");

    }
}
abstract class Tools
{

    public static String sayHelloToPerson(String name)
    {
        return String.format("hello world,from %s",name);
    }
    abstract void process();
}
