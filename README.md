# Single-Page-Full-Html (SPFH)

## Introduction

This repository contains code (Java and Python) to download a fully-loaded web page and save as one single html that contains everything (css, javascript, images, etc.) to open in any web browser.

Here the fully-loaded page is not the one you download using tools like wget or curl, or write code to connect to the url to fetch the content. The html obtained in these ways is sometimes called a static page. Rather, the fully-loaded page is what you see when open the static html in a web browser, where many styles, javascripts and images are dynamically exceuted or loaded.

The idea to get the fully loaded page is to use python and selenium to open the given url in a browser (currently Firefox), and wait for the page to load fully (or timeout after 30 sec). Then it uses pyautogui to mimic human interaction with browser by programmatically issuing "Ctrl+S" keys to save the loaded page to disk file. (Acknowledgement: the idea was inspired by FThompson's answer to this StackOverflow question: https://stackoverflow.com/questions/53729201/save-complete-web-page-incl-css-images-using-python-selenium)

Note that Firefox saves the complete page in a html file, along with a folder containing all resources like css, javascripts, and images, which can only be opened in Firefox. If you try to open it in other browsers, e.g., Chrome, it likely won't open properly. Note that, you would need to turn on Airplane mode (or turn off cable or wifi internet connection) to see the effect.

To ensure the fully loaded page can be reopened in any browser, the separated resources are embedded back to the html to form a big fat html that is self-contained.

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
 
 1. Clone the project, then run maven to build:
    ```
    $> git clone https://github.com/markfront/SinglePageFullHtml.git
    
    $> cd ~/git/SinglePageFullHtml
    $> mvn clean compile package
    ```
 2. Find the generated jar file in target folder: SinglePageFullHtml-1.0-SNAPSHOT-jar-with-dependencies.jar
 
 3. Run the jar in command line like:
    ```    
    $> java -jar ./target/SinglePageFullHtml-1.0-SNAPSHOT-jar-with-dependencies.jar https://www.wonderslist.com/10-most-amazing-places-on-earth/
    
 4. The result fat html will be saved to the tmp folder (which can get by System.getProperty("java.io.tmp"). In my case, it's "/tmp". 
 
 5. Note: the hashcode of the page url string is used to name generate files:
    - The page saved by Firefox has a name like "P-470668507.html" and the resources are saved in folder named "P-470668507_files". Here "-470668507" is the hashCode() of the page url string. 
 
    - The final single big fat html will have a name like "FP-470668507.html", where the file size can be 10 times as "P-470668507.html" after embedding the resources.
 
 ## To-Do
 
  1. add option to use Chrome (Firefox seems ignored some urls in css files when save loaded page to file)
  2. turn the function into a restful api and make a docker for easy deploy
  3. add configurable webdriver path
  
  
