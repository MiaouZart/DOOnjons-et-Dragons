package printer;

public abstract class StandardOut {

    public abstract void out(String str);

    public abstract void outLn(String str);

    public abstract String in();

    public abstract void printf(String str,Object obj);

    public abstract void printf(String s, int rows, String space);
}
