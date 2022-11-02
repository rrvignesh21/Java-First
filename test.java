import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Scanner;
import java.util.jar.JarFile;
import org.json.*;
import java.lang.ClassLoader;
import java.lang.Object;
import java.util.jar.JarEntry;
import java.util.ArrayList;
import java.util.Enumeration;

public class test {
    public static void main(String[] args) throws Exception{
        Scanner scn = new Scanner(System.in);
       
       
        String ipath = scn.nextLine();
        File fl  = new File(ipath);
        ArrayList<String> classNames = getClassNamesFromJarFile(fl);
        ArrayList<Class> classes = new ArrayList<>(classNames.size());
        try (URLClassLoader cl = URLClassLoader.newInstance(
               new URL[] { new URL("jar:file:" + ipath + "!/") })) {
            for (String name : classNames) {
                Class clazz = cl.loadClass(name); // Load the class by its name
                classes.add(clazz);
            }
        }
        
        //return classes;
        JSONObject ob = new JSONObject();
        for (Class c : classes){
            System.out.println(c.getName());
            String[] methname = methodname(c);
            String[] rtype = returntype(c);
            String[] paratype = paraname(c);
            int[] sta = isstatic(c);
            int[] priv = isprivate(c);
            int[] publ = ispublic(c);
            int[] proc = isproc(c);
            JSONArray ar = new JSONArray();
            int i = 0;
            for (String mn:methname) {
                JSONObject tp = new JSONObject();
                tp.put("returntype", rtype[i]);
                tp.put("methodname", methname[i]);
                tp.put("paratype", paratype[i]);
                if(sta[i] == 1){
                System.out.println("Static"+ " " +rtype[i]+" "+methname[i]+" "+paratype[i]);
                }
                else if(priv[i] == 1){
                    System.out.println("private"+" "+rtype[i]+" "+methname[i]+" "+paratype[i]);
                }
                else if(publ[i]==1){
                    System.out.println("public"+ " " +rtype[i]+" "+methname[i]+" "+paratype[i]);
                }
                else if(proc[i] == 1){
                    System.out.println("proctected"+" "+rtype[i]+" "+methname[i]+ " " +paratype[i]);
                }
                else{
                    System.out.println(rtype[i]+" "+methname[i]+" "+paratype[i]);
                }
                ar.put(tp);
                i = i+1; 
            }
            ob.put(c.getName(), ar);
            System.out.println("\n");
        }
        System.out.println(ob.toString());
    }
    
    public static ArrayList<String> getClassNamesFromJarFile(File givenFile) throws Exception {
        ArrayList<String> classNames = new ArrayList<String>();
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
   
    public static String[] returntype(Class recls){
        Method[] met = recls.getDeclaredMethods();
        String[] returtypenname = new String[met.length];
        int i = 0;
        for(Method m : met){
            Class rtype = m.getReturnType();
            String[] rp = rtype.getName().split("[.]");
            if (rp.length > 0){
            String rtname = rp[(rp.length)-1];
            //System.out.print(rtname+" ");
            String[] finrt = rtname.split(";");
            returtypenname[i] = finrt[0];
            //System.out.print(finrt[0]);
            }
            else{
                returtypenname[i] = rtype.getName();
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
            paraname [i] = "";
            for (Class som : meth){
                
                String[] tp = som.getName().split("[.]");
                if (tp.length > 0){
                String parname = tp[(tp.length)-1];
                String[] anther = parname.split(";");
                paraname[i] = paraname[i] + " " +anther[0];
                //System.out.print(anther[0]);                
            }
                else{
                    paraname[i] =paraname[i] + " " +som.getName();
                    //System.out.print(som.getName());
                }                
            }
            i = i+1;
            //System.out.print(")\n");
    } return paraname;

    }

    
    public static void methodclass(Class recls){
        Method[] met = recls.getDeclaredMethods();
        //System.out.println("methods:");
        String[] modname = new String[met.length];
        String[] returntype = new String[met.length];
        String[] methodname = new String[met.length];
        String [] paraname = new String[met.length];          
        int i = 0;
        for (Method method : met){
            Class rettype = method.getReturnType();
            String mod = Modifier.toString(method.getModifiers());
            modname[i] = mod;
            
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
            }i = i+1;
            //System.out.print(")\n");
    } 
 /*   int j = 0;

    for (String s : returntype){

        System.out.print( modname[j] + " " +s+" "+methodname[j]+" "+paraname[j]+" "+"\n");
        j = j+1;
       
    }*/
    }
    public static int[] isstatic(Class recls){
        Method[] met = recls.getDeclaredMethods();
        //System.out.println("methods:");
        int[] issta = new int[met.length];
        int i = 0;
        for (Method method : met){
            //Class rettype = method.getReturnType();
            //String mod = Modifier.toString(method.getModifiers());
            if(Modifier.isStatic(method.getModifiers())){
                issta[i] = 1;
            } 
            else{
                issta[i] = 0;
            }
            i = i+1;               
            }
       // for(int in:issta){
       //     System.out.println(in);
       // }
        return issta;    //System.out.print(")\n");
    }
    public static int[] isproc(Class recls){
        Method[] met = recls.getDeclaredMethods();
        //System.out.println("methods:");
        int[] issta = new int[met.length];
        int i = 0;
        for (Method method : met){
            //Class rettype = method.getReturnType();
            //String mod = Modifier.toString(method.getModifiers());
            if(Modifier.isProtected(method.getModifiers())){
                issta[i] = 1;
            } 
            else{
                issta[i] = 0;
            }
            i = i+1;               
            }
       // for(int in:issta){
        //    System.out.println(in);
       // }
       return issta;
    }

    public static int[] ispublic(Class recls){
        Method[] met = recls.getDeclaredMethods();
       // System.out.println("methods:");
        int[] issta = new int[met.length];
        int i = 0;
        for (Method method : met){
            //Class rettype = method.getReturnType();
            //String mod = Modifier.toString(method.getModifiers());
            if(Modifier.isPublic(method.getModifiers())){
                issta[i] = 1;
            } 
            else{
                issta[i] = 0;
            }
            i = i+1;               
            }
            return issta;
       // for(int in:issta){
       //     System.out.println(in);
       // }

    }

    public static int[] isprivate(Class recls){
        Method[] met = recls.getDeclaredMethods();
       // System.out.println("methods:");
        int[] issta = new int[met.length];
        int i = 0;
        for (Method method : met){
            //Class rettype = method.getReturnType();
            //String mod = Modifier.toString(method.getModifiers());
            if(Modifier.isPrivate(method.getModifiers())){
                issta[i] = 1;
            } 
            else{
                issta[i] = 0;
            }
            i = i+1;               
            }
        //for(int in:issta){
        //    System.out.println(in);
        //}
        return issta;

    }
}

    
    


