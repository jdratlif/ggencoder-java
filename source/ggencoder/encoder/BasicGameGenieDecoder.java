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

package ggencoder.encoder;

import ggencoder.datastructures.BasicGBGGRawCode;
import ggencoder.datastructures.BasicGenesisRawCode;
import ggencoder.datastructures.BasicNESRawCode;
import ggencoder.datastructures.BasicSNESRawCode;
import ggencoder.datastructures.GBGGRawCode;
import ggencoder.datastructures.GameGenieCode;
import ggencoder.datastructures.GenesisRawCode;
import ggencoder.datastructures.NESRawCode;
import ggencoder.datastructures.SNESRawCode;

import ggencoder.exceptions.InvalidGameGenieCodeException;
import ggencoder.exceptions.InvalidGameGenieLetterException;

/**
 * An implementation of the GameGenieDecoder interface.
 *
 * @author John David Ratliff
 * @version 1.1, 07/15/04
 */
class BasicGameGenieDecoder extends AbstractGameGenieDecoder {
    private static final GameGenieDecoder INSTANCE = new BasicGameGenieDecoder();

    /**
     * Creates a new BasicGameGenieDecoder object.
     */
    private BasicGameGenieDecoder() {
        // no outside instantiation -- we are a singleton
    }

    /**
     * Gets an instance of a GameGenieDecoder.
     *
     * @return A GameGenieDecoder instance.
     */
    public static GameGenieDecoder getInstance() {
        return INSTANCE;
    }

    /**
     * Decodes an NES game genie code.
     *
     * @param code The NES GameGenieCode to decode.
     *
     * @return The NES code in "raw" format.
     *
     * @throws InvalidGameGenieCodeException if the code is not a valid NES
     *         GameGenieCode.
     */
    public NESRawCode decodeNES(GameGenieCode code)
                         throws InvalidGameGenieCodeException {
        String ggcode = code.getCode();
        int length = ggcode.length();

        if ((length != 6) && (length != 8)) {
            throw new InvalidGameGenieCodeException();
        }

        int bitstring = 0;

        try {
            for (int i = 0; i < length; i++) {
                bitstring <<= 4;
                bitstring |= code.toHex(ggcode.charAt(i));
            }
        } catch (InvalidGameGenieLetterException e) {
            throw new InvalidGameGenieCodeException();
        }

        int value;
        int address;
        int temp;

        if (length == 6) {
            bitstring <<= 8;
        }

        // position 1234
        value = ((bitstring >> 28) & 0x8) | ((bitstring >> 24) & 0x7);

        if (length == 6) {
            temp = (bitstring & 0x800) >> 8;
        } else {
            temp = bitstring & 0x8;
        }

        temp |= ((bitstring >> 28) & 0x7);

        value <<= 4;
        value |= temp;

        // position -ABC
        address = (bitstring & 0x70000) >> 16;

        // position DEFG
        temp = ((bitstring & 0x8000) >> 12) | ((bitstring & 0x700) >> 8);
        address <<= 4;
        address |= temp;

        // position HIJK
        temp = ((bitstring & 0x8000000) >> 24) |
               ((bitstring & 0x700000) >> 20);
        address <<= 4;
        address |= temp;

        // position LMNO
        temp = ((bitstring & 0x80000) >> 16) | ((bitstring & 0x7000) >> 12);
        address <<= 4;
        address |= temp;

        if (length == 6) {
            return new BasicNESRawCode(address, value);
        }

        int compare;

        // position abcd
        compare = ((bitstring & 0x80) >> 4) | (bitstring & 0x7);

        // position efgh
        temp = ((bitstring & 0x800) >> 8) | ((bitstring & 0x70) >> 4);
        compare <<= 4;
        compare |= temp;

        return new BasicNESRawCode(address, value, compare);
    }

    /**
     * Decodes an SNES game genie code.
     *
     * @param code The SNES GameGenieCode to decode.
     *
     * @return The SNES code in "raw" format.
     *
     * @throws InvalidGameGenieCodeException if the code is not a valid SNES
     *         GameGenieCode.
     */
    public SNESRawCode decodeSNES(GameGenieCode code)
                           throws InvalidGameGenieCodeException {
        String ggcode = code.getCode();
        int length = ggcode.length();

        if (length != 9) {
            throw new InvalidGameGenieCodeException();
        }

        // remove the -
        ggcode = ggcode.substring(0, 4) + ggcode.substring(5);
        --length;

        int bitstring = 0;

        try {
            for (int i = 0; i < length; i++) {
                bitstring <<= 4;
                bitstring |= code.toHex(ggcode.charAt(i));
            }
        } catch (InvalidGameGenieLetterException e) {
            throw new InvalidGameGenieCodeException();
        }

        int value;
        int address;
        int temp;

        // position 12345678
        value = (bitstring >> 24) & 0xFF;

        // position ABCD
        address = ((bitstring >> 10) & 0xC) | ((bitstring >> 10) & 0x3);

        // position EFGH
        temp = ((bitstring >> 2) & 0xC) | ((bitstring >> 2) & 0x3);
        address <<= 4;
        address |= temp;

        // position IJKL
        temp = (bitstring >> 20) & 0xF;
        address <<= 4;
        address |= temp;

        // position MNOP
        temp = ((bitstring << 2) & 0xC) | ((bitstring >> 14) & 0x3);
        address <<= 4;
        address |= temp;

        // position QRST
        temp = (bitstring >> 16) & 0xF;
        address <<= 4;
        address |= temp;

        // position UVWX
        temp = ((bitstring >> 6) & 0xC) | ((bitstring >> 6) & 0x3);
        address <<= 4;
        address |= temp;

        return new BasicSNESRawCode(address, value);
    }

    /**
     * Decodes a Genesis game genie code.
     *
     * @param code The Genesis GameGenieCode to decode.
     *
     * @return The Genesis code in "raw" format.
     *
     * @throws InvalidGameGenieCodeException if the code is not a valid
     *         Genesis GameGenieCode.
     */
    public GenesisRawCode decodeGenesis(GameGenieCode code)
                                 throws InvalidGameGenieCodeException {
        String ggcode = code.getCode();
        int length = ggcode.length();

        if (length != 9) {
            throw new InvalidGameGenieCodeException();
        }

        // remove the -
        ggcode = ggcode.substring(0, 4) + ggcode.substring(5);
        --length;

        long bitstring = 0;

        try {
            for (int i = 0; i < length; i++) {
                bitstring <<= 5;
                bitstring |= code.toHex(ggcode.charAt(i));
            }
        } catch (InvalidGameGenieLetterException e) {
            throw new InvalidGameGenieCodeException();
        }

        int value;
        int address;
        int temp;

        // position abcd
        value = (int)(((bitstring >> 7) & 0xE) | ((bitstring >> 15) & 0x1));

        // position efgh
        temp = (int)(((bitstring >> 11) & 0xE) | ((bitstring >> 11) & 0x1));
        value <<= 4;
        value |= temp;

        // position ijklmnop
        temp = (int)(bitstring >> 32);
        value <<= 8;
        value |= temp;

        // a-p = value, a-x = addy
        // ijkl mnop IJKL MNOP ABCD EFGH defg habc QRST UVWX
        // position ABCDEFGH
        address = (int)((bitstring >> 16) & 0xFF);

        // position IJKLMNOP
        temp = (int)((bitstring >> 24) & 0xFF);
        address <<= 8;
        address |= temp;

        // position QRSTUVWX
        temp = (int)(bitstring & 0xFF);
        address <<= 8;
        address |= temp;

        return new BasicGenesisRawCode(address, value);
    }

    /**
     * Decodes a GameBoy/GameGear game genie code.
     *
     * @param code The GameBoy/GameGear GameGenieCode to decode.
     *
     * @return The GameBoy/GameGear code in "raw" format.
     *
     * @throws InvalidGameGenieCodeException if the code is not a valid
     *         GameBoy/GameGear GameGenieCode.
     */
    public GBGGRawCode decodeGBGG(GameGenieCode code)
                           throws InvalidGameGenieCodeException {
        String ggcode = code.getCode();
        int length = ggcode.length();

        if ((length != 7) && (length != 11)) {
            throw new InvalidGameGenieCodeException();
        }

        // remove the -'s
        if (length == 11) {
            ggcode = ggcode.substring(0, 7) + ggcode.substring(8);
            --length;
        }

        ggcode = ggcode.substring(0, 3) + ggcode.substring(4);
        --length;

        long bitstring = 0;

        try {
            for (int i = 0; i < length; i++) {
                bitstring <<= 4;
                bitstring |= code.toHex(ggcode.charAt(i));
            }
        } catch (InvalidGameGenieLetterException e) {
            throw new InvalidGameGenieCodeException();
        }

        int value;
        int address;
        int temp;

        if (length == 6) {
            bitstring <<= 12;
        }

        value = (int)(bitstring >> 28);

        temp = (int)((bitstring >> 12) & 0xF);
        temp = (~temp & 0xF) << 12;
        address = (int)((bitstring >> 16) & 0xFFF) | temp;

        if (length == 6) {
            return new BasicGBGGRawCode(address, value);
        }

        temp = (int)(((bitstring >> 4) & 0xF0) | (bitstring & 0xF));
        temp = (temp >> 2) | ((temp << 6) & 0xFC);

        int compare = temp ^ 0xBA;

        return new BasicGBGGRawCode(address, value, compare);
    }

    /**
     * Returns a String representation of this GameGenieDecoder.
     *
     * @return A String representation.
     */
    public String toString() {
        return "BasicGameGenieDecoder";
    }
}
