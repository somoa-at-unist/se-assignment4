package examples.simple;

public class ExampleClass {
    public int x, y;

    public class A{
        int Avar;
    }

    public ExampleClass(int xVal, int yVal) {
        System.out.println("ExampleClass is called");
        this.x = xVal;
        this.y = yVal;
    }

    public void halfY(){
        this.y = this.y/2;
    }

    public void foo() {
        //log(100, "ExampleClass.java");
    }

    public void bar() {
//        long li = 1;
//        double f = 1.0;
//        Object o = 0;
//        float[] farr = {0};
//        float k = farr[0];
//        double[] darr = {0};
//        double d = darr[0];
//        String[] sarr = {"Hello"};
//        String s = sarr[0];
        byte[] barr = {0};
//        byte b = barr[0];
//        b = 1;
//        char[] carr = {'a'};
//        char c = carr[0];
//        short[] sharr = {0};
//        short sh = sharr[0];
        int a = 3;
        int b = 5;
        int addi = a+b;
        long al = 3;
        long bl = 5;
        long addl = al+bl;
        float af = 3;
        float bf = 5;
        float addf = af+bf;
        double ad = 3;
        double bd = 5;
        double addd = ad+bd;
//        a = a<<2;
//        a = a>>2;
//        a = a>>>2;
//
//        al = al<<2;
//        al = al>>2;
//        al = al>>>2;
//
//        int aab = a & b;
//        int aob = a | b;
//        int axb = a ^ b;
//
//        long aabl = al & bl;
//        long aobl = al | bl;
//        long axbl = al ^ bl;

        long itol = (long)a;
        float itof = (float)a;

        int ftoi = (int)af;
        int ltoi = (int)al;
        float ltof = (float)al;
        double ltod = (double)al;

        long ftol = (long)af;
        double ftod = (double)af;



        double k = (a/b)*b + (a%b);
        int dtoi = (int)k;
        long dtol = (long)k;
        float dtof = (float)k;

        byte itob = (byte)a;
        char itoc = (char)a;
        short itos = (short)a;

        boolean ic = ftoi > ltoi;
        boolean lc = ftol > itol;
        boolean fc = ltof > itof;
        boolean dc = ftod > ltod;

        int blangth = barr.length;

        byte b2 = 1;

        int btoi = (int)b2;


        int c;
        int div = 0;
        if(div==0) {
            throw new ArithmeticException();
        }
        if (barr!=null){
            System.out.println("notnull");
        }

        String[] newStr = new String[] {"So", "tired"};

    }



    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }


    public static void log(int linenumber, String s) {

    }
}
