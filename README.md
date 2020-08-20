# SinglePageFullHtml

## Introduction

This repository contains code (Java and Python) to download a fully-loaded web page and save as one single html that contains everything (css, javascript, images, etc.) to open in any web browser.

Here the fully-loaded page is not the one you get by wget or curl, which can be called a static page. Rather, it is the page you see when open the static html in a web browser, where many styles, javascripts and images are dynamically loaded.

It uses python and selenium to open the given page url in Firefox, and wait for the page to load fully (or timeout after 30 sec). Then it uses pyautogui to issue "Ctrl-S" to save the loaded page to disk.

Note that Firefox saves the complete page in a html file, along with a folder containing all resources like css, javascripts, images, which can only be opened in Firefox. If you try to open it in other browsers, e.g., Chrome, it likely won't open properly.

To ensure the fully loaded page can be reopened in any browser, the separated resources will be embedded back to the html to form a big fat html.

## Dependencies

The code has been tested on Ubuntu 20.04 LTS, with the following dependencies installed:

 - Java 8 (jdk1.8.0_241)
 - Python 3 (python 3.8.2)
 - Firefox (79.0)
 - Selenium (https://www.selenium.dev/)
 - Firefox WebDriver for Selenium (geckodriver-v0.27.0-linux64)
 - PyAutoGui (https://pyautogui.readthedocs.io/en/latest/)
 
 - (Optional) IntelliJ (2020.2)
 - (Optional) Maven (3.6.3)
 
 ## Usage
 
 1. After clone the project, run:
    ```
    $> mvn clean compile package
    ```
 2. Find the generated jar file in target folder: SinglePageFullHtml-1.0-SNAPSHOT.jar
 
 3. Find the python script included in src/main/python/OpenAndSavePath.py
 
 4. Run the jar in command line like:
    ```
    $> cd ~/git/SinglePageFullHtml
    $> java -jar .target/SinglePageFullHtml-1.0-SNAPSHOT.jar <page_url> <download_dir> <python_script_path>
    ```
 5. Note: the hashcode of the page url string is used to name generate files:
  - The page saved by Firefox has a name like "P-470668507.html" and the resources are saved in folder named "P-470668507_files". Here "-470668507" is the hashCode() of the page url string. 
 
  - The finanl single big fat html will have a name like "FP-470668507.html", where the file size can be 10 times as "P-470668507.html" after embedding the resources.
 
 
