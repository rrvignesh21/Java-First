import java.io.*;
import java.lang.reflect.Method;
import java.util.Scanner;


class firsttry {
    public static void main(String[] args) throws Exception{
        Scanner scn = new Scanner(System.in);
        String ipath = scn.nextLine();
        //Path p = Path.get("D:/Projects1/java/intro.class");
        File cls = new File(ipath);
        String fname = cls.getName();
        //if ()
        String camd = fname.substring(0, fname.lastIndexOf('.'));
        if(cls.exists()){
            System.out.println("found");
        }
        else{
            System.out.println("not found");
        }
        //String cmd = "cd \"d:\\Projects1\\java\" ; if ($?) { javap -p "+camd+" }";
        //ProcessBuilder x = new ProcessBuilder("cmd.exe",cmd);
        //x.redirectErrorStream(true);
        String ncmd = "javac "+camd;
        String n2md = "\""+ncmd+"\"";
        Process p = Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start","cmd.exe", "/D ",ncmd});
        System.out.println(n2md);
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while (true) {
            line = r.readLine();
            if (line == null) { break; }
            System.out.println(line);
        }
        
    }

    
}
