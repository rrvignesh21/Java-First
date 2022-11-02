import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Scanner;
import java.lang.ClassLoader;
import java.lang.Object;
import java.util.jar.JarFile;
import java.util.jar.JarEntry;
import java.util.Set;
import java.util.HashSet;
import java.util.Enumeration;


import java.io.InputStream;
 
public class javaClassLoader extends ClassLoader {
 
    public static void main(String args[]) throws Exception {
        javaClassLoader javaclslod = new javaClassLoader();
        javaclslod.load();
 
    }
 
    public void load() throws Exception {
        Scanner scn = new Scanner(System.in);
        String ipath = scn.nextLine();
        //Path p = Path.get("D:/Projects1/java/intro.class");
        File cls = new File(ipath);
        String fname = cls.getName();
 
        
        InputStream fileInputStream = this.getClass().getClassLoader().getResourceAsStream(fname);
 
        
        byte rawBytes[] = new byte[fileInputStream.available()];
 
       
        fileInputStream.read(rawBytes);
 
        
        
        Class<?> regeneratedClass = this.defineClass( "",rawBytes,0, rawBytes.length);
        System.out.println(regeneratedClass.getName());
        
        Method[] met =  regeneratedClass.getMethods();
        for (Method m : met){
            System.out.println(m.getName());
        }
    }
}