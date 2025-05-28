package printer;

import java.util.ArrayList;
import java.util.Scanner;

public class SytemOut extends StandardOut{

    private Scanner scan ;
    public SytemOut(){
        scan = new Scanner(System.in);
    }


    @Override
    public void out(String str) {
        System.out.print(str);
    }

    @Override
    public void outLn(String str) {
        System.out.println(str);
    }

    @Override
    public String in() {
        return scan.nextLine();
    }

    @Override
    public void printf(String str, Object obj) {
        System.out.printf(str,obj);
    }

    @Override
    public void printf(String s, int rows, String space) {
        System.out.printf(s,rows,space);
    }

}
