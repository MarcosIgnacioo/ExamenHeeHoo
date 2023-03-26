import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class ButtonEditor extends DefaultCellEditor {
    protected JButton boton;
    private String label;
    private boolean isPushed;

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        boton = new JButton("X");
        boton.setOpaque(true);
        boton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (isSelected) {
            boton.setForeground(table.getSelectionForeground());
            boton.setBackground(table.getSelectionBackground());
        } else {
            boton.setForeground(table.getForeground());
            boton.setBackground(table.getBackground());
        }
        label = (value == null) ? "xx" : value.toString();
        boton.setText(label);
        isPushed = true;
        return boton;
    }

    public Object getCellEditorValue() {
        if (isPushed) {
            JOptionPane.showMessageDialog(boton, label + ": Ouch!");
        }
        isPushed = false;
        return new String(label);
    }

    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}