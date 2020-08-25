package com.markfront.spfh;

import java.io.*;

public class SinglePageFullHtml {
    public static void main( String[] args ) {
        if (args.length<1) {
            showUsage();
            System.exit(1);
        }

        long t1 = System.currentTimeMillis();

        String page_url = args[0];

        run(page_url, "", "");

        long t2 = System.currentTimeMillis();

        System.out.println("\nTime: " + (t2-t1)/1000.0 + "sec");

        System.exit(0);
    }

    /* e.g.,
     page_url = "https://www.wonderslist.com/10-most-amazing-places-on-earth";
     curr_dir = args[1]; // "/home/bgu/Downloads";
     py_script = args[2]; // "/home/bgu/Projects/com.markfront.spfh.SinglePageFullHtml/src/main/python/OpenAndSavePage.py";
     */
    public static String run(String page_url, String curr_dir, String py_script) {
        if (curr_dir == null || curr_dir.length() == 0) curr_dir = getTmpDirectory();
        if (py_script == null || py_script.length() == 0) py_script = findAbsPathOfPythonScript("OpenAndSavePage.py");

        String file_stem = "P" + page_url.hashCode();

        System.out.println("page_url = " + page_url);
        System.out.println("curr_dir = " + curr_dir);
        System.out.println("file_stem = " + file_stem);

        System.out.println("py_script: " + py_script);

        // e.g., /usr/bin/python OpenAndSavePage.py  -o /home/bgu/Downloads/ -u https://www.wonderslist.com/10-most-amazing-places-on-earth
        String[] py_cmd = new String[] {
                "/usr/bin/python3",
                py_script,
                "-o", curr_dir,
                "-u", page_url,
                "-f", file_stem
        };

        Utils.execSystemCommand(py_cmd);

        String in_file = curr_dir + "/" + file_stem + ".html";
        String out_file = curr_dir + "/F" + file_stem + ".html";

        Utils.mergeIntoFatHtml(page_url, in_file, out_file, curr_dir);

        System.out.println("\nresult fat html saved to: " + out_file);

        return out_file;
    }

    public static void showUsage() {
        System.out.println("This program takes a page url, runs a python script to open the page in Firefox.");
        System.out.println("The python script will trigger Ctrl-S command to save the fully loaded page.");
        System.out.println("The saved html and resources (css, js, images, etc.) will be merge into one fat html.");
        System.out.println("\nUsage: java -jar SinglePageFullHtml-with-dependencies.jar <page_url>");
    }

    private static String findAbsPathOfPythonScript(String resourceName) {
        ClassLoader classLoader = SinglePageFullHtml.class.getClassLoader();
        InputStream in_stream = classLoader.getResourceAsStream(resourceName);

        // save the file embedded in the resource to a temp file in file system
        String tmp_dir = getTmpDirectory();
        String tmp_file = tmp_dir + "/" + resourceName;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in_stream));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tmp_file));
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line + "\n");
            }
            writer.close();
            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return tmp_file;
    }

    private static String getTmpDirectory() {
        String java_io_tmp = System.getProperty("java.io.tmpdir");
        if (java_io_tmp!=null) return java_io_tmp;

        String user_home = System.getProperty("user.home");
        if (user_home!=null) return user_home;

        String user_dir = System.getProperty("user.dir");
        if (user_dir!=null) return user_dir;

        String cwd = new File("").getAbsolutePath();
        return cwd;
    }
}
