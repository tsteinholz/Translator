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

import com.gtranslate.Language;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Iterator;

public class Translator {

    /** Array of all supported language's prefixes for google-translate. */
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

    /** Print out the Program Help */
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

    /** Generate all of the Language's Files */
    public static void Generate(String file) {
        File f = new File(file);
        if (f.isFile()) {
            com.gtranslate.Translator translator = com.gtranslate.Translator.getInstance();
            Language language = Language.getInstance();

            File out = new File("Dialogs");

            if (!out.exists()) {
                try {
                    out.mkdir();
                } catch(SecurityException se) {
                    se.printStackTrace();
                }
            }

            for (String x : Languages) {
                JSONParser parser = new JSONParser();

                try {

                    Object obj = parser.parse(new FileReader(file));
                    JSONObject jsonObj = (JSONObject) obj;

                    JSONArray levels = (JSONArray) ((JSONObject) obj).get("Levels");

                    JSONObject outObj = new JSONObject();
                    JSONArray outLevel = new JSONArray();

                    int z = 0;

                    //for each object in loaded json array
                    for (Object y : levels) {
                        //objects past this point are all JSON objs
                        if (y instanceof JSONObject) {
                            JSONObject tempJobj = (JSONObject) y;
                            Iterator<JSONObject> iterator = tempJobj.values().iterator();
                            while (iterator.hasNext()) {

                                //tempJobj.put(tempJobj.key());
                                translator.translate("", translator.detect(""), language.getNameLanguage(x));
                                outLevel.add(z, iterator.next());
                                z++;
                            }
                        }
                    }

                    outObj.put("Levels", outLevel);

                    try {
                        FileWriter fileWriter = new FileWriter("Dialogs" +
                                System.getProperty("file.separator") + x + "-Dialog.json");
                        fileWriter.write(outObj.toJSONString());
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

        } else {
            System.out.println("ERROR: " + file + " does not exist!\n");
            help();
        }
    }

    /** Generate the basic template File */
    public static void template() {
        JSONObject template = new JSONObject();

        JSONObject lvl1 = new JSONObject();
        lvl1.put("Greetings", "Hello World");
        lvl1.put("Tutorial.1", "Today I am going to show you how to do something very cool");
        lvl1.put("Tutorial.2", "Let's get started!");

        JSONObject lvl2 = new JSONObject();
        lvl2.put("Shoot", "Shoot");
        lvl2.put("Reload", "Reload");
        lvl2.put("Inventory", "Inventory");
        lvl2.put("InventoryHelp", "Push this button to open your inventory");

        JSONArray levels = new JSONArray();
        levels.add(0, lvl1);
        levels.add(1, lvl2);

        template.put("Levels", levels);

        try {
            FileWriter fileWriter = new FileWriter("TranslatorInput.json");
            fileWriter.write(template.toJSONString());
            fileWriter.flush();
            fileWriter.close();
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
                Generate(args[1]);
            }

        } else if (x.equalsIgnoreCase("-t") || x.equalsIgnoreCase("--template")) {

            template();

        } else if (x.equalsIgnoreCase("-l") || x.equalsIgnoreCase("--list")) {

            list();

        } else {

            Generate("TranslatorInput.json");

        }
    }
}
