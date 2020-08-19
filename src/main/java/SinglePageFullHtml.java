import java.io.*;

public class SinglePageFullHtml {
    public static void main( String[] args ) {
        if (args.length<3) {
            showUsage();
            System.exit(1);
        }

        long t1 = System.currentTimeMillis();

        String page_url = args[0]; //"https://www.wonderslist.com/10-most-amazing-places-on-earth";
        String curr_dir = args[1]; // "/home/bgu/Downloads";
        String py_script = args[2]; // "/home/bgu/Projects/SinglePageFullHtml/src/main/python/OpenAndSavePage.py";

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

        ///*
        String in_file = curr_dir + "/" + file_stem + ".html";
        String out_file = curr_dir + "/F" + file_stem + ".html";

        Utils.mergeIntoFatHtml(page_url, in_file, out_file, curr_dir);
        //*/

        long t2 = System.currentTimeMillis();

        System.out.println("\nResult saved to: " + out_file);

        System.out.println("\nTime: " + (t2-t1)/1000.0 + "sec");

        System.exit(0);
    }

    public static void showUsage() {
        System.out.println("This program takes a page url, runs a python script to open the page in Firefox.");
        System.out.println("The python script will trigger Ctrl-S command to save the fully loaded page.");
        System.out.println("The saved html and resources (css, js, images, etc.) will be merge into one fat html.");
        System.out.println("\nUsage: java -jar SinglePageFullHtml.jar <page_url> <download_dir> <py_script>\n");
    }
}
