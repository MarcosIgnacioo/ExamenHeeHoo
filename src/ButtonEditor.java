import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

class ButtonEditor extends DefaultCellEditor {
    protected JButton button;

    private String label;

    private boolean isPushed;
    JTable table;

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar esta fila?", "Confirmación", dialogButton);
                if (dialogResult == JOptionPane.YES_OPTION) {

                    fireEditingStopped();
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    int selectedRow = table.getSelectedRow();
                    model.removeRow(selectedRow);


                    String filename = "src/users.txt";
                    try {
                        File inputFile = new File(filename);
                        File tempFile = new File("src/temp.txt");

                        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                        String currentLine;


                        int deletedRow = -1;
                        int currentRow = 0;
                        while ((currentLine = reader.readLine()) != null) {
                            if (currentRow == selectedRow) {
                                deletedRow = currentRow;
                            } else {
                                writer.write(currentLine + System.getProperty("line.separator"));
                            }
                            currentRow++;
                        }
                        writer.close();
                        reader.close();


                        inputFile.delete();
                        tempFile.renameTo(inputFile);

                        JOptionPane.showMessageDialog(null, "La fila se ha eliminado correctamente");
                    } catch (FileNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, "No se encontró el archivo '" + filename + "'");
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error al leer/escribir el archivo '" + filename + "'");
                        ex.printStackTrace();
                    }
                } else {
                    isPushed = false;
                }
            }
        });
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        label = (value == null) ? "X" : value.toString();
        button.setText(label);
        isPushed = true;
        this.table = table;
        return button;
    }

    public Object getCellEditorValue() {
        if (isPushed) {
            JOptionPane.showMessageDialog(button, label + ": Ouch!");
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