import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class GetClasses
{
    private static boolean debug = false;
    
    /**
     * test function with assumed package esc.util
     */
    public static void main(String... args)
    {
        try
        {
            final Class<?>[] list = getClasses("commons-lang3-3.4.jar");
            for (final Class<?> c : list)
            {
                System.out.println(c.getName());
            }
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @precondition Thread Class loader attracts class and jar files, exclusively
     * @precondition Classes with static code sections are executed, when loaded and thus must not throw exceptions
     *
     * @param packageName
     *            [in] The base package path, dot-separated
     *
     * @return The classes of package /packageName/ and nested packages
     *
     * @throws IOException,
     *             ClassNotFoundException not applicable
     *
     * @author Sam Ginrich, http://www.java2s.com/example/java/reflection/recursive-method-used-to-find-all-classes-in-a-given-directory-and-sub.html
     *
     */
    public static Class<?>[] getClasses(String packageName) throws IOException
    {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        if (debug)
        {
            System.out.println("Class Loader class is " + classLoader.getClass().getName());
        }
        final String packagePath = packageName.replace('.', '/');
        final Enumeration<URL> resources = classLoader.getResources(packagePath);
        final List<Class<?>> classes = new ArrayList<Class<?>>();
        while (resources.hasMoreElements())
        {
            final URL resource = resources.nextElement();
            final String proto = resource.getProtocol();
            if ("file".equals(proto))
            {
                classes.addAll(findFileClasses(new File(resource.getFile()), packageName));
            }
            else if ("jar".equals(proto))
            {
                classes.addAll(findJarClasses(resource));
            }
            else
            {
                System.err.println("Protocol " + proto + " not supported");
                continue;
            }
        }
        return classes.toArray(new Class[classes.size()]);
    }

    
    
    private static List<Class<?>> findJarClasses(URL packageResource)
    {
        final List<Class<?>> classes = new ArrayList<Class<?>>();
        try
        {
            System.out.println("Jar URL Path is " + packageResource.getPath());
            final URL fileUrl = new URL(packageResource.getPath());
            final String proto = fileUrl.getProtocol();
            if ("file".equals(proto))
            {
                final String filePath = fileUrl.getPath().substring(1); // skip leading /
                final int jarTagPos = filePath.indexOf(".jar!/");
                if (jarTagPos < 0)
                {
                    System.err.println("Non-conformant jar file reference " + filePath + " !");
                }
                else
                {
                    final String packagePath = filePath.substring(jarTagPos + 6);
                    final String jarFilename = filePath.substring(0, jarTagPos + 4);
                    if (debug)
                    {
                        System.out.println("Package " + packagePath);
                        System.out.println("Jar file " + jarFilename);
                    }
                    final String packagePrefix = packagePath + '/';
                    try
                    {
                        final JarInputStream jarFile = new JarInputStream(
                                new FileInputStream(jarFilename));
                        JarEntry jarEntry;

                        while (true)
                        {
                            jarEntry = jarFile.getNextJarEntry();
                            if (jarEntry == null)
                            {
                                break;
                            }
                            final String classPath = jarEntry.getName();
                            if (classPath.startsWith(packagePrefix) && classPath.endsWith(".class"))
                            {
                                final String className = classPath
                                        .substring(0, classPath.length() - 6).replace('/', '.');

                                if (debug)
                                {
                                    System.out.println("Found entry " + jarEntry.getName());
                                }
                                try
                                {
                                    classes.add(Class.forName(className));
                                }
                                catch (ClassNotFoundException x)
                                {
                                    System.err.println("Cannot load class " + className);
                                }
                            }
                        }
                        jarFile.close();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            else
            {
                System.err.println("Nested protocol " + proto + " not supprted!");
            }
        }
        catch ( MalformedURLException e)
        {
            e.printStackTrace();
        }
        return classes;
    }

    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory
     *            The base directory
     * @param packageName
     *            The package name for classes found inside the base directory
     * @return The classes
     * @author http://www.java2s.com/example/java/reflection/recursive-method-used-to-find-all-classes-in-a-given-directory-and-sub.html
     * @throws -
     *
     */
    private static List<Class<?>> findFileClasses(File directory, String packageName)
    {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        if (!directory.exists())
        {
            System.err.println("Directory " + directory.getAbsolutePath() + " does not exist.");
            return classes;
        }
        File[] files = directory.listFiles();
        if (debug)
        {
            System.out.println("Directory "
                    + directory.getAbsolutePath()
                    + " has "
                    + files.length
                    + " elements.");
        }
        for (File file : files)
        {
            if (file.isDirectory())
            {
                assert !file.getName().contains(".");
                classes.addAll(findFileClasses(file, packageName + "." + file.getName()));
            }
            else if (file.getName().endsWith(".class"))
            {
                final String className = packageName
                        + '.'
                        + file.getName().substring(0, file.getName().length() - 6);
                try
                {
                    classes.add(Class.forName(className));
                }
                catch (final ClassNotFoundException cnf)
                {
                    System.err.println("Cannot load class " + className);
                }
            }
        }
        return classes;
    }
}
