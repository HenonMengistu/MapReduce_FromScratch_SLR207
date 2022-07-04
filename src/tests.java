//import org.w3c.dom.ls.LSOutput;

import java.io.File;
//import java.sql.SQLOutput;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class tests {

    public static void main(String[] args) {
        File fileObj = new File("Input/S0.txt");
        Boolean res = fileObj.canRead();
        String hen = fileObj.getAbsolutePath();
        String henon = "I am Henon";
        int x =10;
        System.out.printf("%b%n %s%n %d%n %s%n", res, henon,x,hen);


        Integer a = new Integer("123");
        // Casting not possible
        // int a = (int)"123";
        // Casting not possible
        // int c="123";

        // Casting possible using methods
        // from Integer Wrapper class
        int b = Integer.parseInt("123");
        System.out.print(b + new Float("10.1"));
//        String s ="Henon";
//        Stream stream = (Stream) s.chars();
//
//        stream.map(String::toUpperCase)
//                .forEach(System.out::println);

    }

}
