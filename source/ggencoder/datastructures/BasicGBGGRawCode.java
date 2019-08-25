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

import ggencoder.exceptions.NoCompareValueException;

/**
 * An implementation of the GBGGRawCode interface.
 *
 * @author John David Ratliff
 * @version 1.4, 07/15/04
 */
public class BasicGBGGRawCode extends AbstractRawCode implements GBGGRawCode {
    private boolean usesCompare = false;
    private int compare;

    /**
     * Creates a new BasicGBGGRawCode object without a compare or shadow
     * value.
     *
     * @param address This code's address.
     * @param value This code's value.
     */
    public BasicGBGGRawCode(int address, int value) {
        this.setAddress(address);
        this.setValue(value);
    }

    /**
     * Creates a new BasicGBGGRawCode object with a compare value.
     *
     * @param address This code's address.
     * @param value This code's value.
     * @param compare This code's compare value.
     */
    public BasicGBGGRawCode(int address, int value, int compare) {
        this(address, value);
        this.usesCompare = true;
        this.setCompareValue(compare);
    }

    /**
     * Queries if this code uses a compare value.
     *
     * @return true if a compare value is used; false otherwise.
     */
    public boolean hasCompareValue() {
        return this.usesCompare;
    }

    /**
     * Gets this code's compare value.
     *
     * @return The compare value.
     *
     * @throws NoCompareValueException if no compare value is used by this
     *         code.
     */
    public int getCompareValue() throws NoCompareValueException {
        if (!this.hasCompareValue()) {
            throw new NoCompareValueException();
        }

        return this.compare;
    }

    /**
     * Sets this code's compare value.
     *
     * @param compare The new compare value.
     *
     * @throws NoCompareValueException f no compare value is used by this
     *         code.
     */
    public void setCompareValue(int compare) throws NoCompareValueException {
        if (!this.hasCompareValue()) {
            throw new NoCompareValueException();
        }

        // restrict to 8-bit values
        if ((compare & 0xFFFFFF00) == 0) {
            this.compare = compare;
        }
    }

    /**
     * Sets the address of this code.
     *
     * @param address The code's address.
     */
    public void setAddress(int address) {
        // restrict to 16-bit values
        if ((address & 0xFFFF0000) == 0) {
            super.setAddress(address);
        }
    }

    /**
     * Sets the value of this code.
     *
     * @param value The code's value.
     */
    public void setValue(int value) {
        // restrict to 8-bit values
        if ((compare & 0xFFFFFF00) == 0) {
            super.setValue(value);
        }
    }

    /**
     * Returns a String representation of this code.
     *
     * @return A String representation.
     */
    public String toString() {
        String alpha = "GBGGRawCode[" +
                       this.toHexString(this.getValue(), 2) + ":" +
                       this.toHexString(this.getAddress(), 4);
        String beta = "";

        if (this.hasCompareValue()) {
            beta = "?" + this.toHexString(this.getCompareValue(), 2);
        }

        return (alpha + beta + "]");
    }
}
