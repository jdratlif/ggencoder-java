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

package ggencoder.datastructures;

/**
 * An implementation of the GameGenieCode interface for Genesis Game Genie
 * Codes.
 *
 * @author John David Ratliff
 * @version 1.1, 07/14/04
 */
public class GenesisGameGenieCode extends AbstractGameGenieCode {
    /**
     * Creates a new GenesisGameGenieCode object. This method should never
     * be called directly except by
     * AbstractGameGenieCode.genesisParse(String).
     *
     * @param code The game genie code to encapsulate.
     */
    protected GenesisGameGenieCode(String code) {
        this.setCode(code);
    }

    /**
     * Checks a code to tell if it's valid.
     *
     * @param code The coe to check.
     *
     * @return true if the code is a valid Genesis game genie code, false
     *         otherwise.
     */
    protected static boolean isValidCode(String code) {
        code = code.toUpperCase();

        int length = code.length();

        if (length != 9) {
            return false;
        }

        if (code.charAt(4) != '-') {
            return false;
        }

        code = code.substring(0, 4) + code.substring(5);
        --length;

        char[] alphabet = AbstractGameGenieCode.GENESIS_ALPHABET;

        for (int i = 0; i < length; i++) {
            boolean found = false;

            for (int j = 0; j < alphabet.length; j++) {
                if (code.charAt(i) == alphabet[j]) {
                    found = true;
                    j = alphabet.length;
                }
            }

            if (!found) {
                return false;
            }
        }

        return true;
    }

    /**
     * Gets the alphabet for the game genie code.
     *
     * @return The game genie alphabet.
     */
    public char[] getAlphabet() {
        return AbstractGameGenieCode.GENESIS_ALPHABET;
    }

    /**
     * Returns a String representation of this code.
     *
     * @return A String representation.
     */
    public String toString() {
        return "GenesisGameGenieCode[" + this.getCode() + "]";
    }
}
