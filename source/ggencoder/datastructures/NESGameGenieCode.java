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
 * An implementation of the GameGenieCode interface for NES Game Genie codes.
 *
 * @author John David Ratliff
 * @version 1.1, 07/13/04
 */
class NESGameGenieCode extends AbstractGameGenieCode {
    /**
     * Creates a new NESGameGenieCode object. This method should never be called
     * directly except by AbstractGameGenieCode.nesParse(String).
     *
     * @param code The game genie code to encapsulate.
     */
    protected NESGameGenieCode(String code) {
        this.setCode(code);
    }

    /**
     * Checks a code to tell if it's valid.
     *
     * @param code The coe to check.
     *
     * @return true if the code is a valid NES game genie code, false otherwise.
     */    
    protected static boolean isValidCode(String code) {
        code = code.toUpperCase();
        int length = code.length();

        if ((length != 6) && (length != 8)) {
            return false;
        }

        char[] alphabet = AbstractGameGenieCode.NES_ALPHABET;

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
        return NES_ALPHABET;
    }

    /**
     * Returns a String representation of this code.
     *
     * @return A String representation.
     */
    public String toString() {
        return "NESGameGenieCode[" + this.getCode() + "]";
    }
}
