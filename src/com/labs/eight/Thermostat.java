package com.labs.eight;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Adrian Cooney (12394581)
 * 11/11/14 com.labs.eight
 */
public class Thermostat extends JFrame {
    public enum Mode { MANUAL, TIMED }
    public String mode;

    public JPanel panel1;
    private JCheckBox onOffCheckBox;
    private JComboBox comboBox1;
    private JRadioButton timedRadioButton;
    private JRadioButton manualRadioButton;
    private ButtonGroup modeGroup;
    private JButton statusButton;
    private JTextArea textArea;

    /**
     * Create a new Thermostat.
     */
    public Thermostat() {
        Dimension screenSize = getToolkit().getScreenSize();

        // Setup the window
        setTitle("Thermostat");
        setLocation((screenSize.width / 2), (screenSize.height / 2));

        // Create the UI
        scaffold();

        // Add the listeners
        addListeners();

        // Set the content and options
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        // Set the defaults
        this.turnOn();
        this.setIntensity(4);
        this.setMode(Mode.TIMED);
    }

    /**
     * Use IntelliJ's handy UI builder. Outputs pretty
     * messy code but saves a TON of work. Layout managers
     * are a pain in the ass.
     */
    private void scaffold() {
        panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(panel2, gbc);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout(0, 0));
        panel2.add(panel3);
        final JLabel label1 = new JLabel();
        label1.setFont(new Font(label1.getFont().getName(), Font.BOLD, 18));
        label1.setText("Thermostat");
        label1.setVerticalAlignment(1);
        panel3.add(label1, BorderLayout.WEST);
        onOffCheckBox = new JCheckBox();
        onOffCheckBox.setText("On/off");
        onOffCheckBox.setVerticalAlignment(0);
        panel3.add(onOffCheckBox, BorderLayout.SOUTH);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new BorderLayout(0, 0));
        panel2.add(panel4);
        final JLabel label2 = new JLabel();
        label2.setText("Intensity");
        panel4.add(label2, BorderLayout.WEST);
        comboBox1 = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("1");
        defaultComboBoxModel1.addElement("2");
        defaultComboBoxModel1.addElement("3");
        defaultComboBoxModel1.addElement("4");
        defaultComboBoxModel1.addElement("5");
        comboBox1.setModel(defaultComboBoxModel1);
        panel4.add(comboBox1, BorderLayout.SOUTH);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(panel5, gbc);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new BorderLayout(0, 0));
        panel5.add(panel6);
        timedRadioButton = new JRadioButton();
        timedRadioButton.setText("Timed");
        panel6.add(timedRadioButton, BorderLayout.WEST);
        manualRadioButton = new JRadioButton();
        manualRadioButton.setText("Manual");
        panel6.add(manualRadioButton, BorderLayout.SOUTH);
        modeGroup = new ButtonGroup();
        modeGroup.add(timedRadioButton);
        modeGroup.add(manualRadioButton);
        final JPanel panel7 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel1.add(panel7, gbc);
        statusButton = new JButton();
        statusButton.setText("Status");
        panel7.add(statusButton, BorderLayout.SOUTH);
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setEnabled(true);
        textArea.setRows(4);
        textArea.setText(toString());
        panel7.add(textArea, BorderLayout.NORTH);
    }

    public void addListeners() {
        // Bind the mouse click to the status button
        statusButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                updateStatus();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {}
            @Override
            public void mouseReleased(MouseEvent mouseEvent) {}
            @Override
            public void mouseEntered(MouseEvent mouseEvent) {}
            @Override
            public void mouseExited(MouseEvent mouseEvent) {}
        });

        // Add the check/uncheck event
        onOffCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                System.out.println("Thermostat turned " + (isOn() ? "on" : "off") + "!");
            }
        });

        // Add the intensity listener
        comboBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if(itemEvent.getStateChange() == ItemEvent.SELECTED) {
                    System.out.println("Intensity changed to " + getIntensity() + "!");
                }
            }
        });

        // Event listener for the radion buttons
        ItemListener radio = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if(itemEvent.getStateChange() == ItemEvent.SELECTED) {
                    System.out.println("Mode changed to " + (mode = ((JRadioButton) itemEvent.getItem()).getText()) + ".");
                }
            }
        };

        manualRadioButton.addItemListener(radio);
        timedRadioButton.addItemListener(radio);
    }

    /**
     * Return the mode of the thermostat.
     * @return
     */
    public String getMode() {
        return mode;
    }

    /**
     * Set the mode of the Thermostat
     * @param m Mode MANUAL|TIMED
     */
    public void setMode(Mode m) {
        switch (m) {
            case MANUAL:
                modeGroup.setSelected(manualRadioButton.getModel(), true);
                break;

            case TIMED:
                modeGroup.setSelected(timedRadioButton.getModel(), true);
                break;
        }
    }

    /**
     * Set the intensity of the thermostat.
     * @param i
     */
    public void setIntensity(int i) {
        comboBox1.setSelectedIndex(i - 1);
    }

    /**
     * Get the intensity of the thermostat.
     * @return
     */
    public int getIntensity() {
        return Integer.parseInt((String) comboBox1.getSelectedItem());
    }

    /**
     * Turn the thermostat on.
     */
    public void turnOn() {
        this.onOffCheckBox.setSelected(true);
    }

    /**
     * Turn the thermostat off.
     */
    public void turnOff() {
        this.onOffCheckBox.setSelected(false);
    }

    /**
     * Check whether the thermostat is on or off.
     * @return
     */
    public boolean isOn() {
        return this.onOffCheckBox.isSelected();
    }

    @Override
    public String toString() {
        return "The thermostat is " + (this.isOn() ?
                ("on.\n It is in " + this.getMode() + " mode\n with an intensity of " + this.getIntensity() + ".")
                : "off.");
    }

    /**
     * Update the status box of the thermostat.
     */
    public void updateStatus() {
        System.out.println("Updating status.");
        textArea.setText(toString());
    }
}
