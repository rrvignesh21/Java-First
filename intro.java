import java.util.Scanner;


class sue{
    public static int o(int num){
        System.out.println(num);
        return 0;
    } 
}
class intro extends sue{
    public static int o(int num){
        System.out.println(num*2);
        return 0;
    }
    public static void main(String args[]){
        String something;
        
        //Scanner inp = System.in;
        //Class summa = ClassLoaderExample.class;
        intro p = new intro();
        int n = p.o(10);
        System.out.println(n);
        
    }
}