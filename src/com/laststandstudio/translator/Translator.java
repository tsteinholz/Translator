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

import java.io.File;

public class Translator {

    /** List (Enumeration) of all supported languages. */
    enum Language {
        Afrikaans,
        Albanian,
        Arabic,
        Armenian,
        Azerbaijani,
        Basque,
        Belarusian,
        Bengali,
        Bosnian,
        Bulgarian,
        Catalan,
        Cebuano,
        Chichewa,
        Chinese,
        Croatian,
        Czech,
        Danish,
        Dutch,
        English,
        Esperanto,
        Estonian,
        Filipino,
        Finnish,
        French,
        Galician,
        Georgian,
        German,
        Greek,
        Gujarati,
        Haitian_Creole,
        Hausa,
        Hebrew,
        Hindi,
        Hmong,
        Hungarian,
        Icelandic,
        Igbo,
        Indonesian,
        Irish,
        Italian,
        Japanese,
        Javanese,
        Kannada,
        Kazakh,
        Khmer,
        Korean,
        Lao,
        Latin,
        Latvian,
        Lithuanian,
        Macedonian,
        Malagasy,
        Malay,
        Malayalam,
        Maltese,
        Maori,
        Marathi,
        Mongolian,
        Myanmar,
        Nepali,
        Norwegian,
        Persian,
        Polish,
        Portuguese,
        Punjabi,
        Romanian,
        Russian,
        Serbian,
        Sesotho,
        Sinhala,
        Slovak,
        Slovenian,
        Somali,
        Spanish,
        Sundanese,
        Swahili,
        Swedish,
        Tajik,
        Tamil,
        Telugu,
        Thai,
        Turkish,
        Ukrainian,
        Urdu,
        Uzbek,
        Vietnamese,
        Welsh,
        Yiddish,
        Yoruba,
        Zulu,
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
            //TODO : Generate the files from the input
        } else {
            System.out.println("ERROR: " + file + " does not exist!\n");
            help();
        }
    }

    /** Generate the basic template File */
    public static void template() {
        //TODO Generate Template
    }

    public static void list() {
        //TODO List all Supported Languages
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
