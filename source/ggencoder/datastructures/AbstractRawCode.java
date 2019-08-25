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
 * Abstract implementation of the RawCode interface.
 *
 * @author John David Ratliff
 * @version 1.2, 07/14/04
 */
public abstract class AbstractRawCode implements RawCode {
    private int address;
    private int value;

    /**
     * Gets the address of this code.
     *
     * @return The code's address.
     */
    public int getAddress() {
        return this.address;
    }

    /**
     * Sets the address of this code.
     *
     * @param address The code's address.
     */
    public void setAddress(int address) {
        this.address = address;
    }

    /**
     * Gets the value of this code.
     *
     * @return The code's value.
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Sets the value of this code.
     *
     * @param value The code's value.
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Builds a hex string from an integer.
     *
     * @param number The number to build the hex string from.
     * @param minLength The minimum length of the hex string.
     *
     * @return The constructed hex string.
     */
    public String toHexString(int number, int minLength) {
        String hex = Integer.toHexString(number).toUpperCase();

        while (hex.length() < minLength) {
            hex = "0" + hex;
        }

        return hex;
    }
}
