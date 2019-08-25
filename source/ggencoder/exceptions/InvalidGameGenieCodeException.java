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

package ggencoder.exceptions;

/**
 * Thrown when a game genie code is not valid.
 *
 * @author John David Ratliff
 * @version 1.1, 07/13/04
 */
public class InvalidGameGenieCodeException extends RuntimeException {
    /**
     * Gets a String representation of this exception.
     *
     * @return A String representation of this exception.
     */
    public String toString() {
        return "InvalidGameGenieExeception";
    }
}
