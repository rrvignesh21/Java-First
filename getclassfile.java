import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Scanner;
import java.lang.ClassLoader;
import java.lang.Object;
import org.json.*;
class getclassfile {
    public static void main(String[] args) throws Exception {
        Scanner scn = new Scanner(System.in);
        String ipath = scn.nextLine();
        //Path p = Path.get("D:/Projects1/java/intro.class");
        File cls = new File(ipath);
        String fname = cls.getName();
        String camd = fname.substring(0, fname.lastIndexOf('.'));
        int dotindex = fname.lastIndexOf('.');
        String filetype = fname.substring(dotindex+1);
        //if ((filetype != "java") && (filetype != "class") ){
        //    System.out.println("undesiarable filetype");
        
        
        if(cls.exists()){
            System.out.println("found");
        }
        else{
            System.out.println("not found");
        }
        //File f = new File(MyClass.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        clsfil sumt = new clsfil();
        sumt.func(camd);
        JSONArray k = new JSONArray();
        k.put(sumt);
       // System.out.println(k);
    /* 
       System.getProperty("java.class.path");
        
        ClassLoader c = getclassfile.class.getClassLoader();
        Class<?> recls = Class.forName(camd,true,c);
        ClassLoader anoc = recls.getClass().getClassLoader();
        System.out.println("PackageName"+c.getPackageName());
        Method[] met = recls.getDeclaredMethods();
        System.out.println("methods:");
        for (Method method : met){
            System.out.print(method.getReturnType());
            System.out.print(" "+method.getName());
            System.out.print("(");
            Class[] meth =  method.getParameterTypes();
            
            for (Class som : meth){
                String[] tp = som.getName().split("[.]");
                if (tp.length > 0){
                String parname = tp[(tp.length)-1];
                String[] anther = parname.split(";");
                System.out.print(anther[0]);                
            }
                else{
                    System.out.print(som.getName());
                }                
            }
            System.out.print(")\n"); */       
    }
   
}  
class clsfil{
    public void func(String camd) throws Exception{
        ClassLoader c = getclassfile.class.getClassLoader();
        Class<?> recls = Class.forName(camd,true,c);
        JSONArray ob = new JSONArray();
        //ClassLoader anoc = recls.getClass().getClassLoader();
        //System.out.println("PackageName"+c.getPackageName());
        ob.put(recls.getName());
        System.out.println(recls.getName());
        Method[] met = recls.getDeclaredMethods();
        System.out.println("methods:");
        String[] returntype = new String[met.length];
        String[] methodname = new String[met.length];
        String [] paraname = new String[met.length];          
        int i = 0;
        for (Method method : met){
            JSONObject t = new JSONObject();
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
            paraname [i] = "";
            for (Class som : meth){
                paraname[i] = paraname[i] + " " + som.getName();
                String[] tp = som.getName().split("[.]");
               // paraname[i] = paraname[i] + som.getName();
           /* if (tp.length > 0){
                String parname = tp[(tp.length)-1];
                String[] anther = parname.split(";");
                paraname[i] = paraname[i] + " " +anther[0];
                //System.out.print(anther[0]);                
            }
                else{
                    paraname[i] = som.getName();
                    //System.out.print(som.getName());
                }*/                
                }
                t.put("methodname", methodname[i]);
                t.put("returntype", returntype[i]);
                t.put("paraname", paraname[i]);
                i = i+1;
                ob.put(t);
            //System.out.print(")\n");
    } 
    int j = 0;
    for (String s : returntype){

        System.out.println(s+" "+methodname[j]+" "+paraname[j]+" ");
        
        j = j+1;
       
    }
    System.out.println(ob.toString());


    }
}
    
          
        
    
    

