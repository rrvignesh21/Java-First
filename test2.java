import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URLClassLoader;
import java.util.Scanner;
import java.lang.ClassLoader;
import java.lang.Object;
import java.util.jar.JarFile;
import java.util.jar.JarEntry;
import java.util.Set;
import java.util.HashSet;
import java.util.Enumeration;
public class test2 {
    public static void main(String[] args) throws Exception{
        Scanner scn = new Scanner(System.in);
        String ipath = scn.nextLine();
        //Path p = Path.get("D:/Projects1/java/intro.class");
        File cls = new File(ipath);
        String fname = cls.getName();
        System.out.println(fname);
        String camd = fname.substring(0, fname.lastIndexOf('.'));
        int dotindex = fname.lastIndexOf('.');
        String filetype = fname.substring(dotindex+1);
        FileInputStream inputstrm = new FileInputStream(cls);
        byte[] by =  inputstrm.readAllBytes();
        FileOutputStream ot = new FileOutputStream(ipath);
      
        ot.write(by);



        //System.out.println(camd);
        //ClassLoader clslod = new ClassLoader() {
          
        //};


        
        //System.out.println(tpcs.getName());
          //loadcls(camd);
          System.out.println(filetype);
          clsfil clsfind = new clsfil();
         if (filetype.equals("jar")){
          Set <String> jarcls = getClassNamesFromJarFile(cls);
          for (String s:jarcls){
            if (s.endsWith(".jar")){continue;}
            else
            {
              clsfind.func(s);
              //String[] clsnamepa = s.split("[.]");
              //clsfind.func(clsnamepa[clsnamepa.length - 1]);
            }

            
          }
        }
        else
        {
          clsfind.func(camd);
        }
        
        
          }
          
        public static Set<String> getClassNamesFromJarFile(File givenFile) throws Exception {
          Set<String> classNames = new HashSet<>();
          JarFile jarFile = new JarFile(givenFile); 
          Enumeration<JarEntry> e = jarFile.entries();
              while (e.hasMoreElements()) {
                  JarEntry jarEntry = e.nextElement();
                  if (jarEntry.getName().endsWith(".class")) {
                      String className = jarEntry.getName()
                        .replace("/", ".")
                        .replace(".class", "");
                      classNames.add(className);
                  }
                }
              
              return classNames;
              /*for(String s : classNames){
                System.out.println(s);               
              }*/
          
      }
}
class clsfil{
  public void func(String camd) throws Exception{

    ClassLoader c = getclassfile.class.getClassLoader();
  //  URLClassLoader child = new URLClassLoader(new URL());
    Class<?> recls = Class.forName(camd,true,c);
      //ClassLoader anoc = recls.getClass().getClassLoader();
      //System.out.println("PackageName"+c.getPackageName());
      System.out.println(recls.getName());
      Method[] met = recls.getDeclaredMethods();
      System.out.println("methods:");
      String[] returntype = new String[met.length];
      String[] methodname = new String[met.length];
      String [] paraname = new String[met.length];          
      int i = 0;
      for (Method method : met){
          Class rettype = method.getReturnType();
          String mod = Modifier.toString(method.getModifiers());
          
          String[] rp = rettype.getName().split("[.]");
          if (rp.length > 0){
          String rtname = rp[(rp.length)-1];
          //System.out.print(rtname+" ");
          String[] finrt = rtname.split(";");
          returntype[i] =  finrt[0];
          //System.out.print(finrt[0]);
          }
          methodname[i] = method.getName();
         // System.out.print("s "+method.getName());
          //System.out.print("(");
          Class[] meth =  method.getParameterTypes();

          for (Class som : meth){
              paraname [i] = "";
              String[] tp = som.getName().split("[.]");
              if (tp.length > 0){
              String parname = tp[(tp.length)-1];
              String[] anther = parname.split(";");
              paraname[i] = paraname[i] +anther[0];
              //System.out.print(anther[0]);                
          }
              else{
                  paraname[i] = som.getName();
                  //System.out.print(som.getName());
              }                
          }i = i + 1;
          //System.out.print(")\n");
  } 
  int j = 0;
  for (String s : returntype){

      System.out.print(s+" "+methodname[j]+" "+paraname[j]+" ");
      j = j+1;
     
    }
  }
}
