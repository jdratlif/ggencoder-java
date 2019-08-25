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

import ggencoder.exceptions.InvalidGameGenieCodeException;

/**
 * An interface for decoding game genie codes into "raw" format.
 *
 * @author John David Ratliff
 * @version 1.1, 07/14/04
 */
public interface GameGenieDecoder {
    /**
     * Decodes an NES game genie code.
     *
     * @param code The NES GameGenieCode to decode.
     *
     * @return The NES code in "raw" format.
     *
     * @throws InvalidGameGenieCodeException if the code is not a valid NES
     *         GameGenieCode.
     */
    public NESRawCode decodeNES(GameGenieCode code)
                         throws InvalidGameGenieCodeException;

    /**
     * Decodes an SNES game genie code.
     *
     * @param code The SNES GameGenieCode to decode.
     *
     * @return The SNES code in "raw" format.
     *
     * @throws InvalidGameGenieCodeException if the code is not a valid SNES
     *         GameGenieCode.
     */
    public SNESRawCode decodeSNES(GameGenieCode code)
                           throws InvalidGameGenieCodeException;

    /**
     * Decodes a Genesis game genie code.
     *
     * @param code The Genesis GameGenieCode to decode.
     *
     * @return The Genesis code in "raw" format.
     *
     * @throws InvalidGameGenieCodeException if the code is not a valid
     *         Genesis GameGenieCode.
     */
    public GenesisRawCode decodeGenesis(GameGenieCode code)
                                 throws InvalidGameGenieCodeException;

    /**
     * Decodes a GameBoy/GameGear game genie code.
     *
     * @param code The GameBoy/GameGear GameGenieCode to decode.
     *
     * @return The GameBoy/GameGear code in "raw" format.
     *
     * @throws InvalidGameGenieCodeException if the code is not a valid
     *         GameBoy/GameGear GameGenieCode.
     */
    public GBGGRawCode decodeGBGG(GameGenieCode code)
                           throws InvalidGameGenieCodeException;
}
