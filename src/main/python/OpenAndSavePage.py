#!/usr/bin/python

import sys, getopt

# sudo apt install python3-pip
# sudo apt-get install python3-tk python3-dev
# pip3 install pyautogui
# pip3 install selenium
# sudo cp /home/bgu/Packages/geckodriver-v0.27.0-linux64/geckodriver /usr/bin/

from selenium import webdriver
from selenium.webdriver.common.desired_capabilities import DesiredCapabilities

import pyautogui
import time

def OpenPageThenSave(page_url, save_to_file):
    cap = DesiredCapabilities().FIREFOX
    cap["marionette"] = True

    browser = webdriver.Firefox(capabilities=cap)
    browser.set_page_load_timeout(30)  # seconds

    try:
        browser.get(page_url)
    except:
        print("timeout loading page: ", page_url)

    # To simulate a Save As dialog. You can remove this since you'll be saving/downloading a file from a link
    pyautogui.hotkey('ctrl', 's')

    # Wait for the Save As dialog to load. Might need to increase the wait time on slower machines
    time.sleep(3)

    # File path + name
    # FILE_NAME = 'C:\\path\\to\\file\\file.ext'
    # Type the file path and name is Save AS dialog
    pyautogui.typewrite(save_to_file)

    time.sleep(3)

    # Hit Enter to save
    pyautogui.hotkey('enter')

    time.sleep(3)

    browser.quit()

def main(argv):
    page_url = ''
    out_dir  = ''
    out_file = ''
    try:
        opts, args = getopt.getopt(argv,"h:u:o:f:",["url=","dir=","file="])
    except getopt.GetoptError:
        print('GetFullWebPage.py -u <page_url> -o <output_dir> -f <output_filename>')
        sys.exit(2)
    for opt, arg in opts:
        if opt == '-h':
            print('GetFullWebPage.py -u <page_url> -o <output_dir> -f <output_filename>')
            sys.exit()
        elif opt in ("-u", "--url"):
            page_url = arg
        elif opt in ("-o", "--dir"):
            out_dir = arg
        elif opt in ("-f", "--file"):
            out_file = arg

    print('page_url = ', page_url)
    print('out_dir  = ', out_dir)
    print('out_file = ', out_file)

    OpenPageThenSave(page_url, out_dir + "/" + out_file)

if __name__ == "__main__":
    main(sys.argv[1:])