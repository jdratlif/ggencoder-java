/*
 * Copyright (C) 2004 emuWorks
 * http://games.technoplaza.net
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package ggencoder;

import java.applet.Applet;

import java.awt.Frame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ggencoder.datastructures.AbstractGameGenieCode;
import ggencoder.datastructures.BasicGBGGRawCode;
import ggencoder.datastructures.BasicGenesisRawCode;
import ggencoder.datastructures.BasicNESRawCode;
import ggencoder.datastructures.BasicSNESRawCode;
import ggencoder.datastructures.GBGGRawCode;
import ggencoder.datastructures.GameGenieCode;
import ggencoder.datastructures.GenesisRawCode;
import ggencoder.datastructures.NESRawCode;
import ggencoder.datastructures.SNESRawCode;

import ggencoder.encoder.AbstractGameGenieDecoder;
import ggencoder.encoder.AbstractGameGenieEncoder;
import ggencoder.encoder.GameGenieDecoder;
import ggencoder.encoder.GameGenieEncoder;

import ggencoder.exceptions.InvalidGameGenieCodeException;

import ggencoder.view.EncoderApplet;

/**
 * The runclass for the game genie encoder program in text or GUI mode.
 *
 * @author John David Ratliff
 * @version 1.4, 11/16/04
 */
public class GGEncoder {
    private static final int SYSTEM_NES = 1;
    private static final int SYSTEM_SNES = 2;
    private static final int SYSTEM_GENESIS = 3;
    private static final int SYSTEM_GBGG = 4;
    private static final int ACTION_DECODE = 1;
    private static final int ACTION_ENCODE = 2;

    /**
     * The application's name.
     */
    public static final String APP_NAME = "Java GGEncoder";

    /**
     * The application's version.
     */
    public static final String APP_VERSION = "1.1";
    
    /**
     * The application's copyright.
     */
    public static final String APP_COPYRIGHT = "Copyright (C) 2004 emuWorks";
    
    /**
     * The application's website.
     */
    public static final String APP_URL = "http://games.technoplaza.net/";

    /**
     * Pattern for recognizing NES Raw Codes.
     */
    public static final Pattern NES_PATTERN = Pattern.compile("^([A-Fa-f0-9]{1,2}):([A-Fa-f0-9]{1,4})(?:\\?([A-Fa-f0-9]{1,2}))?$");

    /**
     * Pattern for recognizing SNES Raw Codes.
     */
    public static final Pattern SNES_PATTERN = Pattern.compile("^([A-Fa-f0-9]{1,2}):([A-Fa-f0-9]{1,6})$");

    /**
     * Pattern for recognizing Genesis Raw Codes.
     */
    public static final Pattern GENESIS_PATTERN = Pattern.compile("^([A-Fa-f0-9]{1,4}):([A-Fa-f0-9]{1,6})$");

    /**
     * Pattern for recognizing GameBoy/GameGear Raw Codes.
     */
    public static final Pattern GBGG_PATTERN = NES_PATTERN;

    /**
     * Creates a new GGEncoder object.
     */
    private GGEncoder() {
        // disallow outside instantiation
        // we should be accessed only from the main method
    }

    /**
     * Displays the copyright notice.
     */
    public static void printCopyright() {
        System.out.println(APP_NAME + " " + APP_VERSION);
        System.out.println("Copyright (C) 2004 emuWorks");
        System.out.println("http://games.technoplaza.net");
    }

    /**
     * Displays syntax and usage information.
     */
    public static void printSyntax() {
        System.out.println("syntax: java -jar ggencoder.jar [ system ] [ action ] [ code [,code2, ... ]]");
        System.out.println("   e.g. java -jar ggencoder.jar or");
        System.out.println("        java -jar ggencoder.jar nintendo decode ATVATGSL");
        System.out.println("        java -jar ggencoder.jar nintendo encode FF:1E51?01 C8:1E52?02");
    }

    /**
     * Displays the help menu.
     */
    public static void printHelp() {
        printCopyright();
        System.out.println();

        printSyntax();
        System.out.println();

        System.out.println("With no arguments, ggencoder starts in GUI mode.");
        System.out.println("system = nintendo | nes | supernintendo | snes | genesis |");
        System.out.println("         megadrive | gameboy | gamegear | gb | gg | gbgg");
        System.out.println("action = decode | encode");
        System.out.println("code   = game genie code, or raw code. Case is ignored.");
        System.out.println("         NES game genie = 6 or 8 letter game genie code");
        System.out.println("         SNES game genie = 9 letter of form XXXX-XXXX");
        System.out.println("         Genesis game genie = 9 letter of form XXXX-XXXX");
        System.out.println("         GameBoy/GameGear game genie = 7 or 11 letter code");
        System.out.println("                                       of form XXX-XXX, or XXX-XXX-XXX");
        System.out.println();
        System.out.println("         NES Raw Code = VV:AAAA (VV = value, AAAA = address)");
        System.out.println("                     or VV:AAAA?CC (CC = compare value)");
        System.out.println("         SNES Raw Code = VV:AAAAAA (VV = value, AAAAAA = address)");
        System.out.println("         Genesis Raw Code = VVVV:AAAAAA (VVVV = value, AAAAAA = address)");
        System.out.println("         GameBoy/GameGear Raw Code = VV:AAAA (VV = value, AAAA = address)");
        System.out.println("                                  or VV:AAAA?CC (CC = compare value)");
        System.out.println("         All values and addresses must be specified in hexadecimal.");
    }

    /**
     * Decodes an NES game genie code.
     *
     * @param code The NES game genie code String.
     */
    public void decodeNES(String code) {
        GameGenieDecoder decoder = AbstractGameGenieDecoder.getInstance();

        try {
            GameGenieCode ggcode = AbstractGameGenieCode.parseNES(code);
            NESRawCode rawcode = decoder.decodeNES(ggcode);

            System.out.println(ggcode + " = " + rawcode);
        } catch (InvalidGameGenieCodeException e) {
            System.err.println(code +
                               " is not a valid NES game genie code.");
        }
    }

    /**
     * Encodes an NES game genie code.
     *
     * @param code The NES raw code String.
     */
    public void encodeNES(String code) {
        GameGenieEncoder encoder = AbstractGameGenieEncoder.getInstance();

        Matcher matcher = NES_PATTERN.matcher(code);

        if (matcher.matches()) {
            int value = Integer.parseInt(matcher.group(1), 16);
            int address = Integer.parseInt(matcher.group(2), 16);
            NESRawCode rawcode;

            if (matcher.group(3) != null) {
                int compare = Integer.parseInt(matcher.group(3), 16);
                rawcode = new BasicNESRawCode(address, value, compare);
            } else {
                rawcode = new BasicNESRawCode(address, value);
            }

            GameGenieCode ggcode = encoder.encode(rawcode);

            System.out.println(rawcode + " = " + ggcode);
        } else {
            System.err.println(code + " is not a valid NES Raw Code.");
            System.err.println("Use the form VV:AAAA?CC, VV=value, AAAA=address, CC=compare (optional)");
            System.err.println("e.g. FF:1E51?01 or 2F:3B19");
        }
    }

    /**
     * Decodes an SNES game genie code.
     *
     * @param code The SNES game genie code String.
     */
    public void decodeSNES(String code) {
        GameGenieDecoder decoder = AbstractGameGenieDecoder.getInstance();

        try {
            GameGenieCode ggcode = AbstractGameGenieCode.parseSNES(code);
            SNESRawCode rawcode = decoder.decodeSNES(ggcode);

            System.out.println(ggcode + " = " + rawcode);
        } catch (InvalidGameGenieCodeException e) {
            System.err.println(code +
                               " is not a valid SNES game genie code.");
        }
    }

    /**
     * Encodes an SNES game genie code.
     *
     * @param code The SNES raw code String.
     */
    public void encodeSNES(String code) {
        GameGenieEncoder encoder = AbstractGameGenieEncoder.getInstance();

        Matcher matcher = SNES_PATTERN.matcher(code);

        if (matcher.matches()) {
            int value = Integer.parseInt(matcher.group(1), 16);
            int address = Integer.parseInt(matcher.group(2), 16);

            SNESRawCode rawcode = new BasicSNESRawCode(address, value);
            GameGenieCode ggcode = encoder.encode(rawcode);

            System.out.println(rawcode + " = " + ggcode);
        } else {
            System.err.println(code + " is not a valid SNES Raw Code.");
            System.err.println("Use the form VV:AAAAAA, VV=value, AAAAAA=address");
            System.err.println("e.g. 44:01AF62");
        }
    }

    /**
     * Decodes a Genesis game genie code.
     *
     * @param code The Genesis game genie code String.
     */
    public void decodeGenesis(String code) {
        GameGenieDecoder decoder = AbstractGameGenieDecoder.getInstance();

        try {
            GameGenieCode ggcode = AbstractGameGenieCode.parseGenesis(code);
            GenesisRawCode rawcode = decoder.decodeGenesis(ggcode);

            System.out.println(ggcode + " = " + rawcode);
        } catch (InvalidGameGenieCodeException e) {
            System.err.println(code +
                               " is not a valid Genesis game genie code.");
        }
    }

    /**
     * Encodes a Genesis game genie code.
     *
     * @param code The Genesis raw code String.
     */
    public void encodeGenesis(String code) {
        GameGenieEncoder encoder = AbstractGameGenieEncoder.getInstance();

        Matcher matcher = GENESIS_PATTERN.matcher(code);

        if (matcher.matches()) {
            int value = Integer.parseInt(matcher.group(1), 16);
            int address = Integer.parseInt(matcher.group(2), 16);

            GenesisRawCode rawcode = new BasicGenesisRawCode(address, value);
            GameGenieCode ggcode = encoder.encode(rawcode);

            System.out.println(rawcode + " = " + ggcode);
        } else {
            System.err.println(code + " is not a valid Genesis Raw Code.");
            System.err.println("Use the form VVVV:AAAAAA, VVVV=value, AAAAAA=address");
            System.err.println("e.g. 44:01AF62");
        }
    }

    /**
     * Decodes a GameBoy/GameGear game genie code.
     *
     * @param code The GameBoy/GameGear game genie code String.
     */
    public void decodeGBGG(String code) {
        GameGenieDecoder decoder = AbstractGameGenieDecoder.getInstance();

        try {
            GameGenieCode ggcode = AbstractGameGenieCode.parseGBGG(code);
            GBGGRawCode rawcode = decoder.decodeGBGG(ggcode);

            System.out.println(ggcode + " = " + rawcode);
        } catch (InvalidGameGenieCodeException e) {
            System.err.println(code +
                               " is not a valid GameBoy/GameGear game genie code.");
        }
    }

    /**
     * Encodes a GameBoy/GameGear game genie code.
     *
     * @param code The GameBoy/GameGear raw code String.
     */
    public void encodeGBGG(String code) {
        GameGenieEncoder encoder = AbstractGameGenieEncoder.getInstance();

        Matcher matcher = GBGG_PATTERN.matcher(code);

        if (matcher.matches()) {
            int value = Integer.parseInt(matcher.group(1), 16);
            int address = Integer.parseInt(matcher.group(2), 16);
            GBGGRawCode rawcode;

            if (matcher.group(3) != null) {
                int compare = Integer.parseInt(matcher.group(3), 16);
                rawcode = new BasicGBGGRawCode(address, value, compare);
            } else {
                rawcode = new BasicGBGGRawCode(address, value);
            }

            GameGenieCode ggcode = encoder.encode(rawcode);

            System.out.println(rawcode + " = " + ggcode);
        } else {
            System.err.println(code + " is not a valid GBGG Raw Code.");
            System.err.println("Use the form VV:AAAA?CC, VV=value, AAAA=address, CC=compare (optional)");
            System.err.println("e.g. 00:4A17?C8 or 2A:2E36");
        }
    }

    /**
     * Runs the program in TEXT mode.
     *
     * @param args The program arguments.
     */
    public void startTextMode(String[] args) {
        int system = 0;
        int action = 0;

        String arg = args[0];

        if ((arg.compareToIgnoreCase("nes") == 0) ||
            (arg.compareToIgnoreCase("nintendo") == 0)) {
            system = SYSTEM_NES;
        } else if ((arg.compareToIgnoreCase("snes") == 0) ||
                   (arg.compareToIgnoreCase("supernintendo") == 0)) {
            system = SYSTEM_SNES;
        } else if ((arg.compareToIgnoreCase("genesis") == 0) ||
                   (arg.compareToIgnoreCase("megadrive") == 0)) {
            system = SYSTEM_GENESIS;
        } else if ((arg.compareToIgnoreCase("gameboy") == 0) ||
                   (arg.compareToIgnoreCase("gamegear") == 0) ||
                   (arg.compareToIgnoreCase("gb") == 0) ||
                   (arg.compareToIgnoreCase("gg") == 0) ||
                   (arg.compareToIgnoreCase("gbgg") == 0)) {
            system = SYSTEM_GBGG;
        } else {
            printSyntax();
            System.exit(-1);
        }

        arg = args[1];

        if (arg.compareToIgnoreCase("decode") == 0) {
            action = ACTION_DECODE;
        } else if (arg.compareToIgnoreCase("encode") == 0) {
            action = ACTION_ENCODE;
        } else {
            printSyntax();
            System.exit(-1);
        }

        Method method = null;
        Class[] argTypes = new Class[1];
        argTypes[0] = String.class;

        try {
            switch (system) {
                case SYSTEM_NES:

                    if (action == ACTION_DECODE) {
                        method = this.getClass().getMethod("decodeNES",
                                                           argTypes);
                    } else {
                        method = this.getClass().getMethod("encodeNES",
                                                           argTypes);
                    }

                    break;
                case SYSTEM_SNES:

                    if (action == ACTION_DECODE) {
                        method = this.getClass().getMethod("decodeSNES",
                                                           argTypes);
                    } else {
                        method = this.getClass().getMethod("encodeSNES",
                                                           argTypes);
                    }

                    break;
                case SYSTEM_GENESIS:

                    if (action == ACTION_DECODE) {
                        method = this.getClass().getMethod("decodeGenesis",
                                                           argTypes);
                    } else {
                        method = this.getClass().getMethod("encodeGenesis",
                                                           argTypes);
                    }

                    break;
                case SYSTEM_GBGG:

                    if (action == ACTION_DECODE) {
                        method = this.getClass().getMethod("decodeGBGG",
                                                           argTypes);
                    } else {
                        method = this.getClass().getMethod("encodeGBGG",
                                                           argTypes);
                    }

                    break;
            }
        } catch (NoSuchMethodException e) {
            // this should be impossible
            e.printStackTrace();
            System.exit(-1);
        }

        for (int i = 2; i < args.length; i++) {
            Object[] passedArgs = new Object[1];
            passedArgs[0] = args[i];

            try {
                method.invoke(this, passedArgs);
            } catch (IllegalAccessException e) {
                // this should be impossible
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Called to start the program.
     *
     * @param args The program arguments. Use --help for information.
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            Frame frame = new Frame(APP_NAME + " " + APP_VERSION);

            Applet applet = new EncoderApplet();
            applet.init();
            applet.start();

            frame.add(applet);
            frame.setSize(400, 350);
            frame.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });
            frame.setVisible(true);
        } else if (args.length == 1) {
            if (args[0].compareToIgnoreCase("--help") == 0) {
                printHelp();
            } else {
                printSyntax();
            }
        } else if (args.length >= 3) {
            new GGEncoder().startTextMode(args);
        } else {
            printSyntax();
        }
    }
}
