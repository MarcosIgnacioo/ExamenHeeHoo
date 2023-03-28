import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;

class ButtonEditor extends DefaultCellEditor {
    protected JButton button;

    private String label;

    private String lacuent[];


    private boolean isPushed;
    JTable table;
    public static String nombreUsuarios(){
        String cuentas[];
        String nombrecuentas="";


        try {
            FileReader fileReader = new FileReader("src/users.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                cuentas = linea.split(",");
                nombrecuentas+=cuentas[2]+","; // puse el correo como lo que se muestra en el combobox para evitar confusiones

            }
            fileReader.close();
            bufferedReader.close();
        } catch (IOException de) {
            de.printStackTrace();
        }
        return nombrecuentas;
    }

    public int contadorFilas(String archivo){
        try{
            FileReader fileReader = new FileReader(archivo);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String linea;
            int filas = 0;
            while ((linea = bufferedReader.readLine()) != null) {
                filas++;
            };
            fileReader.close();
            bufferedReader.close();
            return filas;
        }
        catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if(contadorFilas("src/users.txt")>1) {

                    String lacuent[]=ExamenVentana.cuentainiciada().split(",");
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    int selectedRow = table.getSelectedRow();
                    Object[] rowData = new Object[2];
                    for (int i = 0; i < 2; i++) {
                        rowData[i] = model.getValueAt(selectedRow, i);
                    }
                    String rowText = Arrays.toString(rowData);
                    System.out.println("Row text: " + rowText);
                    System.out.println("La cuenta iniciada: " + Arrays.toString(lacuent));
                    if (Arrays.toString(lacuent).equals(Arrays.toString(rowData))) {
                        JOptionPane.showMessageDialog(null, "No se puede eliminar la cuenta iniciada.");
                        return;
                    }

                    int dialogButton = JOptionPane.YES_NO_OPTION;
                    int dialogResult = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar esta fila?", "Confirmación", dialogButton);
                    if (dialogResult == JOptionPane.YES_OPTION) {

                        fireEditingStopped();
                         model = (DefaultTableModel) table.getModel();
                        selectedRow = table.getSelectedRow();

                        model.removeRow(selectedRow);

                        String filename = "src/users.txt";
                        try {
                            File inputFile = new File(filename);
                            File tempFile = new File("src/temp.txt");

                            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                            String currentLine;


                            int currentRow = 0;
                            while ((currentLine = reader.readLine()) != null) {
                                if (currentRow == selectedRow) {

                                } else {
                                    writer.write(currentLine + System.getProperty("line.separator"));
                                }
                                currentRow++;
                            }
                            writer.close();
                            reader.close();

                            nombreUsuarios();
                            inputFile.delete();
                            tempFile.renameTo(inputFile);

                            JOptionPane.showMessageDialog(null, "El perfil se ha eliminado correctamente");
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
                }else{
                    JOptionPane.showMessageDialog(null,"No hay otras cuenta paaaa");
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


        button.setIcon(new ImageIcon("src/basura.png"));
        isPushed = true;
        this.table = table;
        return button;
    }

    public Object getCellEditorValue() {
        if (isPushed) {

        }
        isPushed = false;
        return new ImageIcon("src/basura.png");
    }

    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}