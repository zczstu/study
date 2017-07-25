/**
 * Created by zj on 17-7-23.
 */


import io.reactivex.*;
import io.reactivex.Observable;

import java.util.*;

public class rx_test {
    public static void main(String[] args) {
        Observable.just("hello world!").subscribe(System.out::println);
    }
}