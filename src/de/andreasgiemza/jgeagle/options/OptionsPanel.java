package de.andreasgiemza.jgeagle.options;

import java.awt.Color;
import java.io.File;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

/**
 *
 * @author hurik
 */
public class OptionsPanel extends javax.swing.JPanel {

    private final JDialog jDialog;
    private final Options options;

    /**
     * Creates new form OptionsPanel
     *
     * @param jDialog
     * @param options
     */
    public OptionsPanel(JDialog jDialog, Options options) {
        initComponents();

        this.jDialog = jDialog;
        this.options = options;

        loadOptions();
    }

    private void loadOptions() {
        eagleBinaryTextField.setText(options.getPropEagleBinary());
        imageDpiSchematicTextField.setText(options.getPropSchematicDpi());
        imageDpiBoardTextField.setText(options.getPropBoardDpi());
        dicAddedElementTextField.setText(options.getPropAddedElementColor());
        dicAddedElementPanel.setBackground(Color.decode(options.getPropAddedElementColor()));
        dicRemovedElementTextField.setText(options.getPropRemovedElementColor());
        dicRemovedElementPanel.setBackground(Color.decode(options.getPropRemovedElementColor()));
        dicUndefinedTextField.setText(options.getPropUndefinedColor());
        dicUndefinedPanel.setBackground(Color.decode(options.getPropUndefinedColor()));
    }

    private void close() {
        jDialog.dispose();
        jDialog.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        eagleBinaryFileChooser = new javax.swing.JFileChooser();
        eagleBinaryPanel = new javax.swing.JPanel();
        eagleBinaryButton = new javax.swing.JButton();
        eagleBinaryTextField = new javax.swing.JTextField();
        imageDpiPanel = new javax.swing.JPanel();
        imageDpiSchematicLabel = new javax.swing.JLabel();
        imageDpiSchematicTextField = new javax.swing.JTextField();
        imageDpiBoardLabel = new javax.swing.JLabel();
        imageDpiBoardTextField = new javax.swing.JTextField();
        diffImageColorsPanel = new javax.swing.JPanel();
        dicAddedElementLabel = new javax.swing.JLabel();
        dicAddedElementTextField = new javax.swing.JTextField();
        dicAddedElementPanel = new javax.swing.JPanel();
        dicAddedElementButton = new javax.swing.JButton();
        dicRemovedElementLabel = new javax.swing.JLabel();
        dicRemovedElementTextField = new javax.swing.JTextField();
        dicRemovedElementPanel = new javax.swing.JPanel();
        dicRemovedElementButton = new javax.swing.JButton();
        dicUndefinedLabel = new javax.swing.JLabel();
        dicUndefinedTextField = new javax.swing.JTextField();
        dicUndefinedPanel = new javax.swing.JPanel();
        dicUndefinedButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        eagleBinaryFileChooser.setDialogTitle("Select eagle binary ...");

        eagleBinaryPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Eagle binary"));

        eagleBinaryButton.setText("Select");
        eagleBinaryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eagleBinaryButtonActionPerformed(evt);
            }
        });

        eagleBinaryTextField.setEditable(false);

        javax.swing.GroupLayout eagleBinaryPanelLayout = new javax.swing.GroupLayout(eagleBinaryPanel);
        eagleBinaryPanel.setLayout(eagleBinaryPanelLayout);
        eagleBinaryPanelLayout.setHorizontalGroup(
            eagleBinaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, eagleBinaryPanelLayout.createSequentialGroup()
                .addComponent(eagleBinaryTextField)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(eagleBinaryButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        eagleBinaryPanelLayout.setVerticalGroup(
            eagleBinaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(eagleBinaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(eagleBinaryButton)
                .addComponent(eagleBinaryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        imageDpiPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Image DPI"));

        imageDpiSchematicLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        imageDpiSchematicLabel.setText("Schematic");

        imageDpiBoardLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        imageDpiBoardLabel.setText("Board");

        javax.swing.GroupLayout imageDpiPanelLayout = new javax.swing.GroupLayout(imageDpiPanel);
        imageDpiPanel.setLayout(imageDpiPanelLayout);
        imageDpiPanelLayout.setHorizontalGroup(
            imageDpiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(imageDpiPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(imageDpiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(imageDpiPanelLayout.createSequentialGroup()
                        .addComponent(imageDpiBoardLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(imageDpiBoardTextField))
                    .addGroup(imageDpiPanelLayout.createSequentialGroup()
                        .addComponent(imageDpiSchematicLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(imageDpiSchematicTextField))))
        );
        imageDpiPanelLayout.setVerticalGroup(
            imageDpiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(imageDpiPanelLayout.createSequentialGroup()
                .addGroup(imageDpiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(imageDpiSchematicTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imageDpiSchematicLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(imageDpiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(imageDpiBoardLabel)
                    .addComponent(imageDpiBoardTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        diffImageColorsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Diff image colors"));

        dicAddedElementLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        dicAddedElementLabel.setText("Added elements");

        dicAddedElementTextField.setEditable(false);

        dicAddedElementPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout dicAddedElementPanelLayout = new javax.swing.GroupLayout(dicAddedElementPanel);
        dicAddedElementPanel.setLayout(dicAddedElementPanelLayout);
        dicAddedElementPanelLayout.setHorizontalGroup(
            dicAddedElementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );
        dicAddedElementPanelLayout.setVerticalGroup(
            dicAddedElementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );

        dicAddedElementButton.setText("Choose");
        dicAddedElementButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dicAddedElementButtonActionPerformed(evt);
            }
        });

        dicRemovedElementLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        dicRemovedElementLabel.setText("Removed elements");

        dicRemovedElementTextField.setEditable(false);

        dicRemovedElementPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout dicRemovedElementPanelLayout = new javax.swing.GroupLayout(dicRemovedElementPanel);
        dicRemovedElementPanel.setLayout(dicRemovedElementPanelLayout);
        dicRemovedElementPanelLayout.setHorizontalGroup(
            dicRemovedElementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );
        dicRemovedElementPanelLayout.setVerticalGroup(
            dicRemovedElementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );

        dicRemovedElementButton.setText("Choose");
        dicRemovedElementButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dicRemovedElementButtonActionPerformed(evt);
            }
        });

        dicUndefinedLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        dicUndefinedLabel.setText("Undefined");

        dicUndefinedTextField.setEditable(false);

        dicUndefinedPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout dicUndefinedPanelLayout = new javax.swing.GroupLayout(dicUndefinedPanel);
        dicUndefinedPanel.setLayout(dicUndefinedPanelLayout);
        dicUndefinedPanelLayout.setHorizontalGroup(
            dicUndefinedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );
        dicUndefinedPanelLayout.setVerticalGroup(
            dicUndefinedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );

        dicUndefinedButton.setText("Choose");
        dicUndefinedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dicUndefinedButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout diffImageColorsPanelLayout = new javax.swing.GroupLayout(diffImageColorsPanel);
        diffImageColorsPanel.setLayout(diffImageColorsPanelLayout);
        diffImageColorsPanelLayout.setHorizontalGroup(
            diffImageColorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, diffImageColorsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(diffImageColorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dicRemovedElementLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dicAddedElementLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dicUndefinedLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(diffImageColorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dicRemovedElementTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
                    .addComponent(dicUndefinedTextField)
                    .addComponent(dicAddedElementTextField, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(diffImageColorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dicRemovedElementPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dicAddedElementPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dicUndefinedPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(diffImageColorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dicAddedElementButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(dicRemovedElementButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dicUndefinedButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        diffImageColorsPanelLayout.setVerticalGroup(
            diffImageColorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(diffImageColorsPanelLayout.createSequentialGroup()
                .addGroup(diffImageColorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(diffImageColorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(dicAddedElementTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(dicAddedElementLabel))
                    .addGroup(diffImageColorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(dicAddedElementButton, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(dicAddedElementPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(diffImageColorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dicRemovedElementPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dicRemovedElementButton)
                    .addGroup(diffImageColorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(dicRemovedElementLabel)
                        .addComponent(dicRemovedElementTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(diffImageColorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dicUndefinedPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(diffImageColorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(dicUndefinedLabel)
                        .addComponent(dicUndefinedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(dicUndefinedButton)))
        );

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(eagleBinaryPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(imageDpiPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(diffImageColorsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(eagleBinaryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(imageDpiPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(diffImageColorsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(saveButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void eagleBinaryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eagleBinaryButtonActionPerformed
        int returnVal = eagleBinaryFileChooser.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File eagleBinary = eagleBinaryFileChooser.getSelectedFile();

            if (eagleBinary.exists()) {
                eagleBinaryTextField.setText(eagleBinaryFileChooser.getSelectedFile().toString());
            }
        }
    }//GEN-LAST:event_eagleBinaryButtonActionPerformed

    private void dicAddedElementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dicAddedElementButtonActionPerformed
        Color color
                = JColorChooser.showDialog(this,
                        "Choose color for added elements ...",
                        Color.decode(dicAddedElementTextField.getText()));

        if (color != null) {
            dicAddedElementTextField.setText("#" + Integer.toHexString(color.getRGB()).substring(2, 8));
            dicAddedElementPanel.setBackground(color);
        }
    }//GEN-LAST:event_dicAddedElementButtonActionPerformed

    private void dicUndefinedButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dicUndefinedButtonActionPerformed
        Color color = JColorChooser.showDialog(
                this,
                "Choose color for undedined ...",
                Color.decode(dicUndefinedTextField.getText()));

        if (color != null) {
            dicUndefinedTextField.setText("#" + Integer.toHexString(color.getRGB()).substring(2, 8));
            dicUndefinedPanel.setBackground(color);
        }
    }//GEN-LAST:event_dicUndefinedButtonActionPerformed

    private void dicRemovedElementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dicRemovedElementButtonActionPerformed
        Color color = JColorChooser.showDialog(
                this,
                "Choose color for removed elements ...",
                Color.decode(dicRemovedElementTextField.getText()));

        if (color != null) {
            dicRemovedElementTextField.setText("#" + Integer.toHexString(color.getRGB()).substring(2, 8));
            dicRemovedElementPanel.setBackground(color);
        }
    }//GEN-LAST:event_dicRemovedElementButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        options.save(
                eagleBinaryTextField.getText(),
                imageDpiSchematicTextField.getText(),
                imageDpiBoardTextField.getText(),
                dicAddedElementTextField.getText(),
                dicRemovedElementTextField.getText(),
                dicUndefinedTextField.getText());

        close();
    }//GEN-LAST:event_saveButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        close();
    }//GEN-LAST:event_cancelButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton dicAddedElementButton;
    private javax.swing.JLabel dicAddedElementLabel;
    private javax.swing.JPanel dicAddedElementPanel;
    private javax.swing.JTextField dicAddedElementTextField;
    private javax.swing.JButton dicRemovedElementButton;
    private javax.swing.JLabel dicRemovedElementLabel;
    private javax.swing.JPanel dicRemovedElementPanel;
    private javax.swing.JTextField dicRemovedElementTextField;
    private javax.swing.JButton dicUndefinedButton;
    private javax.swing.JLabel dicUndefinedLabel;
    private javax.swing.JPanel dicUndefinedPanel;
    private javax.swing.JTextField dicUndefinedTextField;
    private javax.swing.JPanel diffImageColorsPanel;
    private javax.swing.JButton eagleBinaryButton;
    private javax.swing.JFileChooser eagleBinaryFileChooser;
    private javax.swing.JPanel eagleBinaryPanel;
    private javax.swing.JTextField eagleBinaryTextField;
    private javax.swing.JLabel imageDpiBoardLabel;
    private javax.swing.JTextField imageDpiBoardTextField;
    private javax.swing.JPanel imageDpiPanel;
    private javax.swing.JLabel imageDpiSchematicLabel;
    private javax.swing.JTextField imageDpiSchematicTextField;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables
}
