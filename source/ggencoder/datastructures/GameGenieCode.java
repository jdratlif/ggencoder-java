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

import ggencoder.exceptions.InvalidGameGenieLetterException;

/**
 * Interface representing a Game Genie code.
 *
 * @author John David Ratliff
 * @version 1.2, 07/14/04
 */
public interface GameGenieCode {
    /**
     * Gets the code string.
     *
     * @return The code string.
     */
    public String getCode();

    /**
     * Sets the code string.
     *
     * @param code The new code string.
     */
    public void setCode(String code);

    /**
     * Gets the alphabet for the game genie code.
     *
     * @return The game genie alphabet.
     */
    public char[] getAlphabet();

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
    public int toHex(char letter) throws InvalidGameGenieLetterException;
}
