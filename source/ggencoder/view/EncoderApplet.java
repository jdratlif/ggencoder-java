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

package ggencoder.view;

import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

import ggencoder.GGEncoder;

import ggencoder.datastructures.AbstractGameGenieCode;
import ggencoder.datastructures.BasicGBGGRawCode;
import ggencoder.datastructures.BasicGenesisRawCode;
import ggencoder.datastructures.BasicNESRawCode;
import ggencoder.datastructures.BasicSNESRawCode;
import ggencoder.datastructures.GBGGRawCode;
import ggencoder.datastructures.GameGenieCode;
import ggencoder.datastructures.GenesisRawCode;
import ggencoder.datastructures.NESRawCode;
import ggencoder.datastructures.RawCode;
import ggencoder.datastructures.SNESRawCode;

import ggencoder.encoder.AbstractGameGenieDecoder;
import ggencoder.encoder.AbstractGameGenieEncoder;
import ggencoder.encoder.GameGenieDecoder;
import ggencoder.encoder.GameGenieEncoder;

import ggencoder.exceptions.InvalidGameGenieCodeException;

/**
 * Applet providing a GUI interface for encoding/decoding game genie codes.
 *
 * @author John David Ratliff
 * @version 1.3, 11/16/04
 */
public class EncoderApplet extends java.applet.Applet {
    private Label addressLabel;
    private TextField addressText;
    private Label compareLabel;
    private TextField compareText;
    private Label copyrightLabel;
    private Checkbox gbggRadio;
    private Checkbox genesisRadio;
    private Label genieLabel;
    private Panel geniePanel;
    private TextField genieText;
    private Checkbox nesRadio;
    private Panel rawPanel;
    private Checkbox snesRadio;
    private Panel systemPanel;
    private Label titleLabel;
    private Panel titlePanel;
    private Label urlLabel;
    private Label valueLabel;
    private TextField valueText;
    private CheckboxGroup systemRadio;
    private TextListener rawListener;
    private TextListener genieListener;
    private int encoding = 0;
    private int decoding;

    /**
     * Initializes this applet.
     */
    public void init() {
        initComponents();
    }

    /**
     * Initializes this applet's components.
     */
    private void initComponents() {
        GridBagConstraints gridBagConstraints;

        rawListener = new TextListener() {
                public void textValueChanged(TextEvent event) {
                    encodeGameGenie();
                }
            };

        genieListener = new TextListener() {
                public void textValueChanged(TextEvent event) {
                    decodeGameGenie();
                }
            };

        ItemListener compareListener = new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    genieText.setText("");
                    valueText.setText("");
                    addressText.setText("");
                    compareText.setText("");
                    compareText.setVisible(true);
                    compareLabel.setVisible(true);
                }
            }
        };

        ItemListener noCompareListener = new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    genieText.setText("");
                    valueText.setText("");
                    addressText.setText("");
                    compareText.setText("");
                    compareText.setVisible(false);
                    compareLabel.setVisible(false);
                }
            }
        };

        titlePanel = new Panel();
        titleLabel = new Label();
        copyrightLabel = new Label();
        urlLabel = new Label();
        systemPanel = new Panel();
        nesRadio = new Checkbox();
        snesRadio = new Checkbox();
        genesisRadio = new Checkbox();
        gbggRadio = new Checkbox();
        rawPanel = new Panel();
        valueText = new TextField();
        addressText = new TextField();
        compareText = new TextField();
        valueLabel = new Label();
        addressLabel = new Label();
        compareLabel = new Label();
        geniePanel = new Panel();
        genieText = new TextField();
        genieLabel = new Label();
        systemRadio = new CheckboxGroup();

        setLayout(new GridBagLayout());

        titlePanel.setLayout(new GridBagLayout());

        titleLabel.setText(GGEncoder.APP_NAME + ' ' + GGEncoder.APP_VERSION);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        titlePanel.add(titleLabel, gridBagConstraints);

        copyrightLabel.setText(GGEncoder.APP_COPYRIGHT);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        titlePanel.add(copyrightLabel, gridBagConstraints);

        urlLabel.setText(GGEncoder.APP_URL);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        titlePanel.add(urlLabel, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(5, 5, 10, 5);
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.25;
        add(titlePanel, gridBagConstraints);

        systemPanel.setLayout(new GridBagLayout());

        nesRadio.setLabel("Nintendo");
        nesRadio.setState(true);
        nesRadio.addItemListener(compareListener);
        nesRadio.setCheckboxGroup(systemRadio);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        systemPanel.add(nesRadio, gridBagConstraints);

        snesRadio.setLabel("Super Nintendo");
        snesRadio.addItemListener(noCompareListener);
        snesRadio.setCheckboxGroup(systemRadio);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        systemPanel.add(snesRadio, gridBagConstraints);

        genesisRadio.setLabel("Genesis");
        genesisRadio.addItemListener(noCompareListener);
        genesisRadio.setCheckboxGroup(systemRadio);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        systemPanel.add(genesisRadio, gridBagConstraints);

        gbggRadio.setLabel("Game Boy / Game Gear");
        gbggRadio.addItemListener(compareListener);
        gbggRadio.setCheckboxGroup(systemRadio);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        systemPanel.add(gbggRadio, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weighty = 0.25;
        add(systemPanel, gridBagConstraints);

        rawPanel.setLayout(new GridBagLayout());

        valueText.setColumns(10);
        valueText.addTextListener(rawListener);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.3;
        rawPanel.add(valueText, gridBagConstraints);

        addressText.setColumns(20);
        addressText.addTextListener(rawListener);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.4;
        rawPanel.add(addressText, gridBagConstraints);

        compareText.setColumns(10);
        compareText.addTextListener(rawListener);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.3;
        rawPanel.add(compareText, gridBagConstraints);

        valueLabel.setText("Value");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        rawPanel.add(valueLabel, gridBagConstraints);

        addressLabel.setText("Address");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        rawPanel.add(addressLabel, gridBagConstraints);

        compareLabel.setText("Compare");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        rawPanel.add(compareLabel, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.25;
        add(rawPanel, gridBagConstraints);

        geniePanel.setLayout(new GridBagLayout());

        genieText.setColumns(20);
        genieText.addTextListener(genieListener);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        geniePanel.add(genieText, gridBagConstraints);

        genieLabel.setText("Game Genie");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        geniePanel.add(genieLabel, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.weighty = 0.25;
        add(geniePanel, gridBagConstraints);
    }

    /**
     * Encodes a "raw" code into a game genie code.
     */
    private void encodeGameGenie() {
        if (--decoding >= 0) {
            return;
        }

        decoding = 0;

        try {
            int value = Integer.parseInt(valueText.getText(), 16);
            int address = Integer.parseInt(addressText.getText(), 16);

            Checkbox selected = systemRadio.getSelectedCheckbox();
            GameGenieEncoder encoder = AbstractGameGenieEncoder.getInstance();
            GameGenieCode ggcode;

            if (selected == nesRadio) {
                NESRawCode rawcode;

                if (compareText.getText().length() > 0) {
                    int compare = Integer.parseInt(compareText.getText(), 16);
                    rawcode = new BasicNESRawCode(address, value, compare);
                } else {
                    rawcode = new BasicNESRawCode(address, value);
                }

                ggcode = encoder.encode(rawcode);
            } else if (selected == snesRadio) {
                SNESRawCode rawcode = new BasicSNESRawCode(address, value);
                ggcode = encoder.encode(rawcode);
            } else if (selected == genesisRadio) {
                GenesisRawCode rawcode = new BasicGenesisRawCode(address,
                                                                 value);
                ggcode = encoder.encode(rawcode);
            } else {
                GBGGRawCode rawcode;

                if (compareText.getText().length() > 0) {
                    int compare = Integer.parseInt(compareText.getText(), 16);
                    rawcode = new BasicGBGGRawCode(address, value, compare);
                } else {
                    rawcode = new BasicGBGGRawCode(address, value);
                }

                ggcode = encoder.encode(rawcode);
            }

            ++encoding;
            genieText.setText(ggcode.getCode());
        } catch (NumberFormatException e) {
            // not yet valid. leave it alone
        }
    }

    /**
     * Decodes a game genie code into "raw" format.
     */
    private void decodeGameGenie() {
        if (--encoding >= 0) {
            return;
        }

        encoding = 0;

        GameGenieDecoder decoder = AbstractGameGenieDecoder.getInstance();
        Checkbox selected = systemRadio.getSelectedCheckbox();

        try {
            String code = genieText.getText();
            GameGenieCode ggcode;
            RawCode rawcode;

            if (selected == nesRadio) {
                ggcode = AbstractGameGenieCode.parseNES(code);
                rawcode = decoder.decodeNES(ggcode);

                NESRawCode nesRawCode = (NESRawCode)rawcode;

                if (nesRawCode.hasCompareValue()) {
                    ++decoding;
                    compareText.setText(Integer.toHexString(nesRawCode.getCompareValue())
                                               .toUpperCase());
                } else {
                    ++decoding;
                    compareText.setText("");
                }
            } else if (selected == snesRadio) {
                ggcode = AbstractGameGenieCode.parseSNES(code);
                rawcode = decoder.decodeSNES(ggcode);
            } else if (selected == genesisRadio) {
                ggcode = AbstractGameGenieCode.parseGenesis(code);
                rawcode = decoder.decodeGenesis(ggcode);
            } else {
                ggcode = AbstractGameGenieCode.parseGBGG(code);
                rawcode = decoder.decodeGBGG(ggcode);

                GBGGRawCode gbggRawCode = (GBGGRawCode)rawcode;

                if (gbggRawCode.hasCompareValue()) {
                    ++decoding;
                    compareText.setText(Integer.toHexString(gbggRawCode.getCompareValue())
                                               .toUpperCase());
                } else {
                    ++decoding;
                    compareText.setText("");
                }
            }

            ++decoding;
            valueText.setText(Integer.toHexString(rawcode.getValue())
                                     .toUpperCase());

            ++decoding;
            addressText.setText(Integer.toHexString(rawcode.getAddress())
                                       .toUpperCase());
        } catch (InvalidGameGenieCodeException e) {
            // not yet valid. leave it alone for now...
        }
    }

    /**
     * Returns a String representation of this applet.
     *
     * @return A String representation.
     */
    public String toString() {
        return "EncoderApplet";
    }
}
