import java.io.*;
import java.lang.reflect.Method;
import java.util.Scanner;
import java.lang.ClassLoader;
import java.lang.Object;


public class changes {
        
    public static void main(String[] args) throws Exception {
        Scanner scn = new Scanner(System.in);
        String ipath = scn.nextLine();
        //Path p = Path.get("D:/Projects1/java/intro.class");
        //String ipath = "";
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
        System.out.println(cls.getName());
        FileInputStream in = new FileInputStream(cls);
        byte[] by =new byte[in.available()];
        by = in.readAllBytes();
        File tp = new File("com/manageengine/ela/server/common/alerts/");
        
        String p = "com/manageengine/ela/server/common/alerts/AlertEditAndUpdateHandler.class"; 
        String classnameres = p.substring(0, p.lastIndexOf('.'));
        String[] s = p.split("/");
        //FileOutputStream op = new FileOutputStream("/");
        File loc = new File(p);
        if (!loc.exists()){
            File t = new File("com/manageengine/ela/server/common/alerts/");
            t.mkdirs();
    }
    if (loc.exists()){
        System.out.println("exist");
    }


        //tp.mkdir();
       String clsnam = s[s.length-1]; 

       FileOutputStream ou = new FileOutputStream("com/manageengine/ela/server/common/alerts/"+clsnam);
       ou.write(by);
       String fclsname = classnameres.replaceAll("/",".");
       System.out.println(classnameres);
       System.out.println(fclsname);
       //Class<?> c = Class.forName(fclsname);
       //System.out.println(c.getName());
        clsfil finv = new clsfil();
        finv.func(fclsname);
       //File f = new File(MyClass.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        //clsfil sumt = new clsfil();
        //sumt.func(camd);
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
        Class<?> recls = c.loadClass(camd); 
        ClassLoader anoc = recls.getClass().getClassLoader();
        //System.out.println("PackageName"+c.getPackageName());
        System.out.println(recls.getName());
        Method[] met = recls.getDeclaredMethods();
        System.out.println("methods:");
        for (Method method : met){
            Class rettype = method.getReturnType();
            String[] rp = rettype.getName().split("[.]");
            if (rp.length > 0){
            String rtname = rp[(rp.length)-1];
            //System.out.print(rtname+" ");
            String[] finrt = rtname.split(";");
            System.out.print(finrt[0]);
            }
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
            System.out.print(")\n");
    }  

    }
}
