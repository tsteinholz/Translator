/*/-----------------------------------------------------------------------------------------------------------------/*/
/*/                                                                                                                 /*/
/*/                                 ______________________________________                                          /*/
/*/                        ________|                                      |_______                                  /*/
/*/                        \       |     This file is a part of the       |      /                                  /*/
/*/                         \      |    Last Stand Studio Game Engine     |     /                                   /*/
/*/                         /      |______________________________________|     \                                   /*/
/*/                        /__________)                                (_________\                                  /*/
/*/                                                                                                                 /*/
/*/                                     Copyright Last Stand Studio 2015                                            /*/
/*/                                                                                                                 /*/
/*/               The Last Stand Gaming Engine is free software: you can redistribute it and/or modify              /*/
/*/               it under the terms of the GNU General Public License as published by                              /*/
/*/               the Free Software Foundation, either version 3 of the License, or                                 /*/
/*/               (at your option) any later version.                                                               /*/
/*/                                                                                                                 /*/
/*/               The Last Stand Gaming Engine is distributed in the hope that it will be useful,                   /*/
/*/               but WITHOUT ANY WARRANTY; without even the implied warranty of                                    /*/
/*/               MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                                     /*/
/*/               GNU General Public License for more details.                                                      /*/
/*/                                                                                                                 /*/
/*/               You should have received a copy of the GNU General Public License                                 /*/
/*/               along with The Last Stand Gaming Engine. If not, see <http://www.gnu.org/licenses/>.              /*/
/*/                                                                                                                 /*/
/*/                                                                                                                 /*/
/*/-----------------------------------------------------------------------------------------------------------------/*/

package com.laststandstudio.translator;

import com.memetix.mst.detect.Detect;
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

import java.io.*;
import java.util.Map;
import java.util.Properties;

public class Translator {

    /**
     * Array of all supported language's prefixes for google-translate.
     */
    public static String[] Languages = {
            "af", "sq", "ar", "hy", "az", "eu",
            "be", "bn", "bg", "ca", "hr", "cs",
            "da", "nl", "en", "et", "tl", "fi",
            "fr", "gl", "ka", "de", "el", "gu",
            "ht", "iw", "hi", "hu", "is", "id",
            "ga", "it", "ja", "kn", "ko", "la",
            "lv", "lt", "mk", "ms", "mt", "no",
            "fa", "pl", "pt", "ro", "ru", "sr",
            "sk", "sl", "es", "sw", "sv", "ta",
            "te", "th", "tr", "uk", "ur", "vi",
            "cy", "yi", "zh-CN", "zh-TW",
    };

    /**
     * Print out the Program Help
     */
    public static void help() {
        String help = "Last Stand Engine Translator\n";
        help += "\tTo use this program you need an input file, if you do not have one you can generate one with '-t'\n";
        help += "\tThis will generate a standard input file for you with the default name that requires no parameters.\n";
        help += "\tYou can rename this file as you want but you will need to use '-i filename' parameters instead.\n";
        help += "\t\n";
        help += "\t-h | --help : Display Help Screen\n";
        help += "\t\tusage: java -jar translator.jar -h\n";
        help += "\t-i | --input : Load with config\n";
        help += "\t\tusage: java -jar translator.jar -i SomeFile.json\n";
        help += "\t-t | --template : Generate Template File\n";
        help += "\t\tusage: java -jar translator.jar -t\n";
        help += "\t-l | --list : Lists all supported Languages\n";
        help += "\t\tusage: java -jar translator.jar -l\n";

        System.out.println(help);
    }

    /** Set information for Microsoft Azure */
    private static void setAzure() {
        Console console = System.console();
        String id = console.readLine("Enter your Windows Azure Client Id:");
        String secret = console.readLine("Enter your Windows Azure Client Secret:");

        //Translate
        Translate.setClientId(id);
        Translate.setClientSecret(secret);
        //Detect
        Detect.setClientId(id);
        Detect.setClientSecret(secret);
        //Language
        Language.setClientId(id);
        Language.setClientSecret(secret);
    }

    /**
     * Generate all of the Language's Files
     */
    public static void Generate(String file) throws Exception {
        setAzure();
        File f = new File(file);
        //If the given path is a file and not a directory or non-existent
        if (f.isFile()) {

            Properties dialog = new Properties();
            FileInputStream inputStream = new FileInputStream(file);

            //Create the Output Directory if it doesn't exist
            File out = new File("Dialogs");

            if (!out.exists()) {
                try {
                    out.mkdir();
                } catch (SecurityException se) {
                    se.printStackTrace();
                }
            }

            //Load Properties File and Close Input Stream
            dialog.load(inputStream);
            inputStream.close();


            for (Language lang : Language.values()) {
                //System.out.println("Generating " + language.getNameLanguage(x) + " Language Translation File...");
                Properties temp = new Properties();
                for (Map.Entry<Object, Object> e : dialog.entrySet()) {
                    String key = (String) e.getKey();
                    String value = (String) e.getValue();
                    //temp.put(key, translator.translate(value, translator.detect(value), x));
                    temp.put(key, Translate.execute(value, Detect.execute(value), lang));

                    try {
                        FileWriter fileWriter = new FileWriter(
                                "Dialog" + System.getProperty("file.separator") + lang.toString() + "-Dialog.properties");
                        temp.store(fileWriter, lang.toString());
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                }

            }

        } else {
            System.out.println("ERROR: " + file + " does not exist!\n");
            help();
        }
    }

    /**
     * Generate the basic template File
     */

    public static void template() {

        Properties dialog = new Properties();
        dialog.setProperty("Greetings", "Hello World");
        dialog.setProperty("Tutorial.1", "Today I am going to show you how to do something very cool");
        dialog.setProperty("Tutorial.2", "Let's get started!");
        dialog.setProperty("Shoot", "Shoot");
        dialog.setProperty("Reload", "Reload");
        dialog.setProperty("Inventory", "Inventory");
        dialog.setProperty("InventoryHelp", "Push this button to open your inventory");

        try {
            FileWriter fileWriter = new FileWriter("TranslatorInput.properties");
            dialog.store(fileWriter, "This template has been auto-magically generated for your use!\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Template has been generated!");
    }

    public static void list() {
        System.out.println("We currently support:");
        System.out.println("Afrikaans, Albanian, Arabic, Armenian, Azerbaijani, Basque, Belarusian, Bengali, Bosnian,\n" +
                "Bulgarian, Catalan, Cebuano, Chichewa, Chinese, Croatian, Czech, Danish, Dutch, English, Esperanto,\n" +
                "Estonian, Filipino, Finnish, French, Galician, Georgian, German, Greek, Gujarati, Haitian_Creole,\n" +
                "Hausa, Hebrew, Hindi, Hmong, Hungarian, Icelandic, Igbo, Indonesian, Irish, Italian, Japanese, \n" +
                "Javanese, Kannada, Kazakh, Khmer, Korean, Lao, Latin, Latvian, Lithuanian, Macedonian, Malagasy, Malay,\n" +
                "Malayalam, Maltese, Maori, Marathi, Mongolian, Myanmar, Nepali, Norwegian, Persian, Polish, Portuguese,\n" +
                "Punjabi, Romanian, Russian, Serbian, Sesotho, Sinhala, Slovak, Slovenian, Somali, Spanish, Sundanese,\n" +
                "Swahili, Swedish, Tajik, Tamil, Telugu, Thai, Turkish, Ukrainian, Urdu, Uzbek, Vietnamese, Welsh,\n" +
                "Yiddish, Yoruba, Zulu");
    }

    public static void main(String[] args) {
        String x = args.length > 0 ? args[0] : "";

        if (x.equalsIgnoreCase("-h") || x.equalsIgnoreCase("--help")) {

            help();

        } else if (x.equalsIgnoreCase("-i") || x.equalsIgnoreCase("--input")) {

            if (args.length < 2) {
                System.out.println("ERROR: No input file specified!\n");
                help();
            } else {
                try {
                    Generate(args[1]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } else if (x.equalsIgnoreCase("-t") || x.equalsIgnoreCase("--template")) {

            template();

        } else if (x.equalsIgnoreCase("-l") || x.equalsIgnoreCase("--list")) {

            list();

        } else {

            try {
                Generate("TranslatorInput.properties");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
