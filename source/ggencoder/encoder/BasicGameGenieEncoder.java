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

import ggencoder.datastructures.AbstractGameGenieCode;
import ggencoder.datastructures.GameGenieCode;
import ggencoder.datastructures.NESRawCode;
import ggencoder.datastructures.SNESRawCode;
import ggencoder.datastructures.GenesisRawCode;
import ggencoder.datastructures.GBGGRawCode;

/**
 * An implementation of the GameGenieEncoder interface.
 *
 * @author John David Ratliff
 * @version 1.2, 07/15/04
 */
class BasicGameGenieEncoder extends AbstractGameGenieEncoder {
    private static final GameGenieEncoder INSTANCE = new BasicGameGenieEncoder();

    /**
     * Creates a new BasicGameGenieEncoder object.
     */
    private BasicGameGenieEncoder() {
        // no outside instantiation -- we are a singleton
    }

    /**
     * Gets the only instance of this GameGenieEncoder
     *
     * @return The GameGenieEncoder singleton
     */
    public static GameGenieEncoder getInstance() {
        return INSTANCE;
    }

    /**
     * Encodes an NESRawCode into a GameGenieCode.
     *
     * @param code The NESRawCode to encode.
     *
     * @return The encoded GameGenieCode.
     */
    public GameGenieCode encode(NESRawCode code) {
        int genie;
        int temp = 0;
        int value = code.getValue();
        int address = code.getAddress();

        // position 1678
        genie = ((value & 0x80) >> 4) | (value & 0x7);

        // position H234
        temp = ((address & 0x80) >> 4) | ((value & 0x70) >> 4);
        genie <<= 4;
        genie |= temp;

        // position -IJK
        temp = (address & 0x70) >> 4;
        genie <<= 4;
        genie |= temp;

        // position LABC
        temp = (address & 0x8) | ((address & 0x7000) >> 12);
        genie <<= 4;
        genie |= temp;

        // position DMNO
        temp = ((address & 0x800) >> 8) | (address & 0x7);
        genie <<= 4;
        genie |= temp;

        if (code.hasCompareValue()) {
            int compare = code.getCompareValue();

            // position eEFG
            temp = (compare & 0x8) | ((address & 0x700) >> 8);
            genie <<= 4;
            genie |= temp;

            // position afgh
            temp = ((compare & 0x80) >> 4) | (compare & 0x7);
            genie <<= 4;
            genie |= temp;

            // position 5bcd
            temp = (value & 0x8) | ((compare & 0x70) >> 4);
            genie <<= 4;
            genie |= temp;
        } else {
            // position 5EFG
            temp = (value & 0x8) | ((address & 0x700) >> 8);
            genie <<= 4;
            genie |= temp;
        }

        String ggcode = "";
        char[] alphabet = AbstractGameGenieCode.NES_ALPHABET;

        for (int i = 0; i < (code.hasCompareValue() ? 8 : 6); i++) {
            ggcode = alphabet[(genie >> (i * 4)) & 0xF] + ggcode;
        }

        return AbstractGameGenieCode.parseNES(ggcode);
    }

    /**
     * Encodes an SNESRawCode into a GameGenieCode.
     *
     * @param code The SNESRawCode to encode.
     *
     * @return The encoded GameGenieCode.
     */
    public GameGenieCode encode(SNESRawCode code) {
        int genie;
        int temp;
        int value = code.getValue();
        int address = code.getAddress();

        // position 1-8
        genie = value;

        // position IJKL
        temp = (address & 0xF000) >> 12;
        genie <<= 4;
        genie |= temp;

        // position QRST
        temp = (address & 0xF0) >> 4;
        genie <<= 4;
        genie |= temp;

        // position OPAB
        temp = ((address & 0x300) >> 6) | (address >> 22);
        genie <<= 4;
        genie |= temp;

        // position CDUV
        temp = ((address & 0x300000) >> 18) | ((address & 0xC) >> 2);
        genie <<= 4;
        genie |= temp;

        // position WXEF
        temp = ((address & 0x3) << 2) | ((address & 0xC0000) >> 18);
        genie <<= 4;
        genie |= temp;

        // position GHMN
        temp = ((address & 0x30000) >> 14) | ((address & 0xC00) >> 10);
        genie <<= 4;
        genie |= temp;

        String ggcode = "";
        char[] alphabet = AbstractGameGenieCode.SNES_ALPHABET;

        for (int i = 0; i < 8; i++) {
            if (i == 4) {
                ggcode = "-" + ggcode;
            }

            ggcode = alphabet[(genie >> (i * 4)) & 0xF] + ggcode;
        }

        return AbstractGameGenieCode.parseSNES(ggcode);
    }
    
    public GameGenieCode encode(GenesisRawCode code) {
        int temp;
        long genie;
        int value = code.getValue();
        int address = code.getAddress();
        
        // position ijkl
        genie = (value & 0xF0) >> 4;
        
        // position mnop
        temp = (value & 0xF);
        genie <<= 4;
        genie |= temp;
        
        // position IJKL
        temp = (address & 0xF000) >> 12;
        genie <<= 4;
        genie |= temp;
        
        // position MNOP
        temp = (address & 0xF00) >> 8;
        genie <<= 4;
        genie |= temp;
        
        // position ABCD
        temp = (address & 0xF00000) >> 20;
        genie <<= 4;
        genie |= temp;
        
        // position EFGH
        temp = (address & 0xF0000) >> 16;
        genie <<= 4;
        genie |= temp;
        
        // position defg
        temp = ((value & 0x1000) >> 9) | ((value & 0xE00) >> 9);
        genie <<= 4;
        genie |= temp;
        
        // position habc
        temp = ((value & 0x100) >> 5) | ((value & 0xE000) >> 13);
        genie <<= 4;
        genie |= temp;
        
        // position QRST
        temp = (address & 0xF0) >> 4;
        genie <<= 4;
        genie |= temp;
        
        // position UVWX
        temp = (address & 0xF);
        genie <<= 4;
        genie |= temp;
        
        String ggcode = "";
        char[] alphabet = AbstractGameGenieCode.GENESIS_ALPHABET;
        
        for (int i = 0; i < 8; i++) {
            if (i == 4) {
                ggcode = "-" + ggcode;
            }

            ggcode = alphabet[(int)((genie >> (i * 5)) & 0x1F)] + ggcode;
        }
        
        return AbstractGameGenieCode.parseGenesis(ggcode);
    }
    
    /**
     * Encodes a GBGGRawCode into a GameGenieCode.
     *
     * @param code The GBGGRawCode to encode.
     *
     * @return The encoded GameGenieCode.
     */
    public GameGenieCode encode(GBGGRawCode code) {
        int temp, address = code.getAddress(), value = code.getValue();
        long genie;
        
        // start with the value
        genie = value;
        
        temp = (address & 0xF000) >> 12;
        temp = (~temp & 0xF);
        temp |= (address & 0xFFF) << 4;
        genie <<= 16;
        genie |= temp;
        
        if (code.hasCompareValue()) {
            int compare = code.getCompareValue();
            
            compare ^= 0xBA;
            compare = (compare << 2) | (compare >> 6);
            temp = (compare & 0xF0) >> 4;
            genie <<= 4;
            genie |= temp;
            
            temp ^= 8;
            genie <<= 4;
            genie |= temp;
            
            temp = (compare & 0xF);
            genie <<= 4;
            genie |= temp;
        }
        
        String ggcode = "";
        char[] alphabet = AbstractGameGenieCode.GBGG_ALPHABET;

        for (int i = 0; i < (code.hasCompareValue() ? 9 : 6); i++) {
            if ((i == 3) || (i == 6)) {
                ggcode = "-" + ggcode;
            }

            ggcode = alphabet[(int)((genie >> (i * 4)) & 0xF)] + ggcode;
        }

        return AbstractGameGenieCode.parseGBGG(ggcode);
    }

    /**
     * Returns a String representation of this GameGenieEncoder.
     *
     * @return A String representation.
     */
    public String toString() {
        return "BasicGameGenieEncoder";
    }
}
