package printer;

import java.util.Scanner;

public class SystemOut extends StandardOut{
    private final Scanner m_scan;

    /**
     * Constructeur de SystemOut<br>
     */
    public SystemOut(){
        m_scan = new Scanner(System.in);
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
        return m_scan.nextLine();
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
