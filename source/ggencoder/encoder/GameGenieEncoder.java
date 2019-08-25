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

import ggencoder.datastructures.GBGGRawCode;
import ggencoder.datastructures.GameGenieCode;
import ggencoder.datastructures.GenesisRawCode;
import ggencoder.datastructures.NESRawCode;
import ggencoder.datastructures.SNESRawCode;

/**
 * An interface for encoding RAW codes into game genie codes.
 *
 * @author John David Ratliff
 * @version 1.1, 07/13/04
 */
public interface GameGenieEncoder {
    /**
     * Encodes an NESRawCode into a GameGenieCode.
     *
     * @param code The NESRawCode to encode.
     *
     * @return The encoded GameGenieCode.
     */
    public GameGenieCode encode(NESRawCode code);

    /**
     * Encodes an SNESRawCode into a GameGenieCode.
     *
     * @param code The SNESRawCode to encode.
     *
     * @return The encoded GameGenieCode.
     */
    public GameGenieCode encode(SNESRawCode code);

    /**
     * Encodes a GenesisRawCode into a GameGenieCode.
     *
     * @param code The GenesisRawCode to encode.
     *
     * @return The encoded GameGenieCode.
     */
    public GameGenieCode encode(GenesisRawCode code);

    /**
     * Encodes a GBGGRawCode into a GameGenieCode.
     *
     * @param code The GBGGRawCode to encode.
     *
     * @return The encoded GameGenieCode.
     */
    public GameGenieCode encode(GBGGRawCode code);
}
