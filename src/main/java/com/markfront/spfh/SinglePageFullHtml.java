package com.markfront.spfh;

import org.apache.tika.utils.SystemUtils;

import java.io.*;

public class SinglePageFullHtml {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            showUsage();
            System.exit(1);
        }

        long t1 = System.currentTimeMillis();

        String page_url = args[0];

        boolean use_cache = true;
        if (args.length > 1 && args[1].equalsIgnoreCase("false")) use_cache = false;

        run(page_url, "", "", use_cache);

        long t2 = System.currentTimeMillis();

        System.out.println("\nTime: " + (t2 - t1) / 1000.0 + "sec");

        System.exit(0);
    }

    /* e.g.,
     page_url = "https://www.wonderslist.com/10-most-amazing-places-on-earth";
     curr_dir = args[1]; // "/home/bgu/Downloads";
     py_script = args[2]; // "/home/bgu/Projects/com.markfront.spfh.SinglePageFullHtml/src/main/python/GetFullWebPage.py";
     */
    public static String run(String page_url, String curr_dir, String py_script, boolean use_cache) throws Exception {
        if (curr_dir == null || curr_dir.length() == 0) curr_dir = getTmpDirectory();
        if (py_script == null || py_script.length() == 0) py_script = findAbsPathOfPythonScript("GetFullWebPage.py");

        if (py_script == null) {
            throw new Exception("Cant find 'GetFullWebPage.py'");
        }

        String file_stem = "P" + page_url.hashCode();

        System.out.println("page_url = " + page_url);
        System.out.println("curr_dir = " + curr_dir);
        System.out.println("file_stem = " + file_stem);

        System.out.println("py_script: " + py_script);

        String in_file = curr_dir + File.separatorChar + file_stem + ".html";
        String out_file = curr_dir + File.separatorChar + "F" + file_stem + ".html";

        File fin = new File(in_file);

        if (!fin.exists() || !use_cache) {

            if (fin.exists()) {
                fin.delete();
            }

            String pythonExecutable = "";
            if (SystemUtils.IS_OS_WINDOWS) {
                // TODO please verify, for me this works
                pythonExecutable = "python";
            } else if (SystemUtils.IS_OS_LINUX) {
                pythonExecutable = "/usr/bin/python3";
            } else if (SystemUtils.IS_OS_MAC_OSX) {
                // TODO please verify
                pythonExecutable = "/usr/bin/python";
            } else {
                pythonExecutable = "python"; // some fallback that may or may not work
            }

            // e.g., /usr/bin/python GetFullWebPage.py  -o /home/bgu/Downloads/ -u https://www.wonderslist.com/10-most-amazing-places-on-earth
            String[] py_cmd = new String[]{
                    pythonExecutable,
                    py_script,
                    "-o", curr_dir,
                    "-u", page_url,
                    "-f", file_stem
            };

            Utils.execSystemCommand(py_cmd);
        } // else use previously saved html download


        File fout = new File(out_file);
        if (fout.exists()) {
            fout.delete();
        }

        try {
            Thread.sleep(2000);
            long bytes1 = fin.length();

            System.out.println("before merge file size: " + bytes1);

            Utils.mergeIntoFatHtml(page_url, in_file, out_file, curr_dir);

            long bytes2 = fout.length();

            System.out.println("\nresult fat html saved to: " + out_file);
            System.out.println("\nresult merged file size: " + bytes2);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

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

        try (InputStream in_stream = classLoader.getResourceAsStream(resourceName)) {
            if (in_stream != null) {
                // save the file embedded in the resource to a temp file in file system
                String tmp_dir = getTmpDirectory();
                String tmp_file = tmp_dir + File.separatorChar + resourceName;
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(in_stream));
                     BufferedWriter writer = new BufferedWriter(new FileWriter(tmp_file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        writer.write(line + "\n");
                    }
                    return tmp_file;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getTmpDirectory() {
        /*
        String java_io_tmp = System.getProperty("java.io.tmpdir");
        File f = new File(java_io_tmp);
        if (f.exists() && f.canRead() && f.canWrite()) return java_io_tmp;

        String user_home = System.getProperty("user.home");
        f = new File(user_home);
        if (f.exists() && f.canRead() && f.canWrite()) return user_home;

        String user_dir = System.getProperty("user.dir");
        f = new File(user_dir);
        if (f.exists() && f.canRead() && f.canWrite()) return user_dir;
        */
        String cwd = new File("").getAbsolutePath();

        ///*
        String tmp_dir = cwd + File.separatorChar + "temp";

        File f = new File(tmp_dir);

        if (!f.exists()) {
            try {
                f.mkdir();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        // */

        return tmp_dir;
    }
}
