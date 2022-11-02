import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Scanner;
import java.lang.ClassLoader;
import java.lang.Object;
import java.util.jar.JarFile;
import java.util.jar.JarEntry;
import java.util.Set;
import java.util.HashSet;
import java.util.Enumeration;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.NoSuchFileException;
import java.io.File;
import org.json.*;

public class thirddaytry
{
    public static void main(String args[])throws Exception{
       Scanner sc = new Scanner(System.in);
       String ipath = sc.nextLine();
        //Path p = Path.get("D:/Projects1/java/file-fold");
       File cls = new File(ipath);
       /*
       String fname = cls.getName();
       System.out.println(fname);
        String camd = fname.substring(0, fname.lastIndexOf('.'));
        int dotindex = fname.lastIndexOf('.');          
        String filetype = fname.substring(dotindex+1);
        Class<?> c = Class.forName(camd);
        System.out.println(c.getName());
       stsatic();
        */
        File[] fnae = cls.listFiles();
        JSONObject ob = new JSONObject();
        for(File f:fnae){
            String fname = f.getName();
       //System.out.println(fname);
        String camd = fname.substring(0, fname.lastIndexOf('.'));
        int dotindex = fname.lastIndexOf('.');          
        String filetype = fname.substring(dotindex+1);
            Class<?> clslo = Class.forName(camd);
            
            System.out.println(clslo.getName());
            String[] rtype = returntype(clslo);
            String[] metname = methodname(clslo);
            String[] paraname = paraname(clslo);
            int i = 0;
            JSONArray ar = new JSONArray();
            for(String m:metname){
                JSONObject tp = new JSONObject();
                System.out.println(rtype[i]+ " " +m+" "+paraname[i]);
                tp.put("returntype", rtype[i]);
                tp.put("methodname", m);
                tp.put("paraname", paraname[i]);
                i = i  + 1;
                ar.put(tp);
            }
            System.out.println("\n");
            ob.put(clslo.getName(), ar);
        }

        //Class<?> c = Class.
    }
    public static String[] returntype(Class recls){
        //JSONArray t = new JSONArray();
        Method[] met = recls.getDeclaredMethods();
        String[] returtypenname = new String[met.length];
        int i = 0;
        for(Method m : met){
           // JSONObject tp = new JSONObject();
            Class rtype = m.getReturnType();
            String[] rp = rtype.getName().split("[.]");
            if (rp.length > 0){
            
            String rtname = rp[(rp.length)-1];
            //System.out.print(rtname+" ");
            String[] finrt = rtname.split(";");
            returtypenname[i] = finrt[0];
            //tp.accumulate("returntype", finrt);
            //System.out.print(finrt[0]);
            }
            else{
                returtypenname[i] = rtype.getName();
                //tp.accumulate("returntype", rtype.getName());
            }
            i = i+1;
        }
        return returtypenname; 

    }

    public static String[] methodname(Class recls){
        Method[] met = recls.getDeclaredMethods();
        String[] methodname = new String[met.length];
        int i = 0;
        for(Method m : met){
            methodname[i] = m.getName();
            i = i+1;
        }
        return methodname; 

    }

    public static String[] paraname(Class recls){
        Method[] met = recls.getDeclaredMethods();
        String[] paraname = new String[met.length];
        int i = 0;
        for(Method m : met){
            Class[] meth =  m.getParameterTypes();

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
            }
            i = i+1;
            //System.out.print(")\n");
    } return paraname;

    }
    public static void testing() {

    }
    public static void stsatic() throws NoSuchMethodException {
        Method m = thirddaytry.class.getMethod("testing", null);

        if(Modifier.isStatic(m.getModifiers())){
            System.out.println("isStatics");

        }
        else{
            System.out.println("not statics");
        }

    }
    
}
