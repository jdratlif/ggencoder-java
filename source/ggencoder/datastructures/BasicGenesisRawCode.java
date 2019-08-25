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
 * An implementation of the GenesisRawCode interface.
 *
 * @author John David Ratliff
 * @version 1.3, 07/14/04
 */
public class BasicGenesisRawCode extends AbstractRawCode
    implements GenesisRawCode {
    /**
     * Creates a new BasicGenesisRawCode object.
     *
     * @param address This code's address.
     * @param value This code's value.
     */
    public BasicGenesisRawCode(int address, int value) {
        this.setAddress(address);
        this.setValue(value);
    }

    /**
     * Sets the address of this code.
     *
     * @param address The code's address.
     */
    public void setAddress(int address) {
        // restrict to 24-bit addresses
        if ((address & 0xFF000000) == 0) {
            super.setAddress(address);
        }
    }

    /**
     * Sets the value of this code.
     *
     * @param value The code's value.
     */
    public void setValue(int value) {
        // restrict to 16-bit values
        if ((value & 0xFFFF0000) == 0) {
            super.setValue(value);
        }
    }

    /**
     * Returns a String representation of this code.
     *
     * @return A String representation.
     */
    public String toString() {
        return "GenesisRawCode[" + this.toHexString(this.getValue(), 4) +
               ":" + this.toHexString(this.getAddress(), 6) + "]";
    }
}
