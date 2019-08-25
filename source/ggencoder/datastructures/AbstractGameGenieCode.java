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

import ggencoder.exceptions.InvalidGameGenieCodeException;
import ggencoder.exceptions.InvalidGameGenieLetterException;

/**
 * An abstract implementation of the GameGenieCode interface.
 *
 * @author John David Ratliff
 * @version 1.4, 07/15/04
 */
public abstract class AbstractGameGenieCode implements GameGenieCode {
    private String code;

    /**
     * Game Genie alphabet for NES codes.
     */
    public static final char[] NES_ALPHABET = {
                                                  'A', 'P', 'Z', 'L', 'G',
                                                  'I', 'T', 'Y', 'E', 'O',
                                                  'X', 'U', 'K', 'S', 'V',
                                                  'N'
                                              };

    /**
     * Game Genie alphabet for SNES codes.
     */
    public static final char[] SNES_ALPHABET = {
                                                   'D', 'F', '4', '7', '0',
                                                   '9', '1', '5', '6', 'B',
                                                   'C', '8', 'A', '2', '3',
                                                   'E'
                                               };

    /**
     * Game Genie alphabet for Genesis codes.
     */
    public static final char[] GENESIS_ALPHABET = {
                                                      'A', 'B', 'C', 'D', 'E',
                                                      'F', 'G', 'H', 'J', 'K',
                                                      'L', 'M', 'N', 'P', 'R',
                                                      'S', 'T', 'V', 'W', 'X',
                                                      'Y', 'Z', '0', '1', '2',
                                                      '3', '4', '5', '6', '7',
                                                      '8', '9'
                                                  };

    /**
     * Game Genie alphabet for GameBoy/GameGear codes.
     */
    public static final char[] GBGG_ALPHABET = {
                                                   '0', '1', '2', '3', '4',
                                                   '5', '6', '7', '8', '9',
                                                   'A', 'B', 'C', 'D', 'E',
                                                   'F'
                                               };

    /**
     * Parses an NES Game Genie code into a GameGenieCode object.
     *
     * @param code The NES game genie code.
     *
     * @return The GameGenieCode object.
     *
     * @throws InvalidGameGenieCodeException if the code is not a valid NES
     *         game genie code.
     */
    public static GameGenieCode parseNES(String code)
                                  throws InvalidGameGenieCodeException {
        if (NESGameGenieCode.isValidCode(code)) {
            return new NESGameGenieCode(code);
        }

        throw new InvalidGameGenieCodeException();
    }

    /**
     * Parses an SNES Game Genie code into a GameGenieCode object.
     *
     * @param code The SNES game genie code.
     *
     * @return The GameGenieCode object.
     *
     * @throws InvalidGameGenieCodeException if the code is not a valid SNES
     *         game genie code.
     */
    public static GameGenieCode parseSNES(String code)
                                   throws InvalidGameGenieCodeException {
        if (SNESGameGenieCode.isValidCode(code)) {
            return new SNESGameGenieCode(code);
        }

        throw new InvalidGameGenieCodeException();
    }

    /**
     * Parses a Genesis Game Genie code into a GameGenieCode object.
     *
     * @param code The Genesis game genie code.
     *
     * @return The GameGenieCode object.
     *
     * @throws InvalidGameGenieCodeException if the code is not a valid
     *         Genesis game genie code.
     */
    public static GameGenieCode parseGenesis(String code)
                                      throws InvalidGameGenieCodeException {
        if (GenesisGameGenieCode.isValidCode(code)) {
            return new GenesisGameGenieCode(code);
        }

        throw new InvalidGameGenieCodeException();
    }

    /**
     * Parses a GameBoy/GameGear Game Genie code into a GameGenieCode
     * object.
     *
     * @param code The Genesis game genie code.
     *
     * @return The GameGenieCode object.
     *
     * @throws InvalidGameGenieCodeException if the code is not a valid
     *         GameBoy/GameGear game genie code.
     */
    public static GameGenieCode parseGBGG(String code)
                                   throws InvalidGameGenieCodeException {
        if (GBGGGameGenieCode.isValidCode(code)) {
            return new GBGGGameGenieCode(code);
        }

        throw new InvalidGameGenieCodeException();
    }

    /**
     * Gets the code string.
     *
     * @return The code string.
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Sets the code string.
     *
     * @param code The new code string.
     */
    public void setCode(String code) {
        this.code = code.toUpperCase();
    }

    /**
     * Translates a game genie letter to hexadecimal.
     *
     * @param letter The letter to translate.
     *
     * @return The hex value of the letter.
     *
     * @throws InvalidGameGenieLetterException if the letter is not part of
     *         the game genie alphabet.
     */
    public int toHex(char letter) throws InvalidGameGenieLetterException {
        letter = Character.toUpperCase(letter);

        char[] alphabet = this.getAlphabet();

        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i] == letter) {
                return i;
            }
        }

        throw new InvalidGameGenieLetterException();
    }
}
