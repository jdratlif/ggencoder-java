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
 * An interface to represent a RAW NES code.
 *
 * @author John David Ratliff
 * @version 1.1, 07/13/04
 */
public interface NESRawCode extends RawCode {
    /**
     * Queries if this code uses a compare value.
     *
     * @return true if a compare value is used; false otherwise.
     */
    public boolean hasCompareValue();
    
    /**
     * Gets this code's compare value.
     *
     * @return The compare value.
     *
     * @throws NoCompareValueException if no compare value is used by this
     *         code.
     */
    public int getCompareValue() throws NoCompareValueException;

    /**
     * Sets this code's compare value.
     *
     * @param value The new compare value.
     *
     * @throws NoCompareValueException f no compare value is used by this
     *         code.
     */
    public void setCompareValue(int value) throws NoCompareValueException;
}
