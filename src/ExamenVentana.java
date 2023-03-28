import org.w3c.dom.CDATASection;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ExamenVentana extends JFrame {
    private String anterior = "logIn";
    private String actual = "logIn";
    public JPanel panel = null;
    String datosAnteriores = null;
    String datosNuevos = null;
    private String bienvenidoNombre = "";
    private static int usuarioId = 0;
    private String nombrescaja[];
    private static boolean vieneDeLista = false;

    private static boolean vieneDeMenuBar = false;
    private static String usuarioInfo [] = new String[4];
    private static String usuarioInfoLista[] = new String[4];

    private int numcuenta[];
    boolean leerParaCreer(String correo, int size){
        String userName, passwordConf;
        String cuentas[] = new String[size];
        int entro = 0;
        String filePath = "src/users.txt";
        boolean correoExiste = false;
        try {
            FileReader fileReader = new FileReader("src/users.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String linea;
            int contador = 0;
            int id = usuarioId;
            while ((linea = bufferedReader.readLine()) != null) {
                cuentas = linea.split(",");
                if (cuentas[2].equals(correo) && id !=contador){
                    correoExiste = true;
                }
                contador++;
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException de) {
            de.printStackTrace();
        }
        return correoExiste;
    }

    static void actualizarDatos(String archivo, String infoAnterior, String infoActual, int fileSize)
    {
        int memoriaLinea = 0;

        try {
            FileReader fileReader = new FileReader(archivo);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            String[] sentences = new String[fileSize];
            int i = 0;
            while ((line = bufferedReader.readLine()) != null) {
                sentences[i] = line;
                if (sentences[i].equals(infoAnterior)){
                    usuarioId = i;
                    memoriaLinea = i;
                }
                i++;
            }

            sentences[memoriaLinea] = infoActual;// actualiza la informacion pero no la escribe porque la guarda en un arreglo, es decir, aqui
            //                                      esta tooooooodo el txt pero pues ya con esa unidad (1) de renglon cambiada PERO FALTA ESCRIBIRLO EN EL ARCHIVO PLEBE!!!!!!
            FileWriter fileWriter = new FileWriter(archivo);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (int p = 0; p < i; p++) {
                bufferedWriter.write(sentences[p]);
                bufferedWriter.newLine(); // actualiza vuelve a escribir todo el archivo AQUI LO ESCRIBE Q LOCO
            }

            bufferedWriter.close();
            fileWriter.close();
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public ExamenVentana() throws InterruptedException {
        Splash splashScreen = new Splash("src/splash4.gif");
        Thread.sleep(5900);
        SwingUtilities.invokeLater(() -> {
        });
        splashScreen.dispose();
        this.getContentPane().setBackground(Color.decode("#1C1B20"));
        this.setVisible(true);
        this.setSize(700,900);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        limpiarVentana();
        this.repaint();
        this.revalidate();
    }
    public void limpiarVentana() {
      //  actual="lista";
        if(panel!= null) {
            this.remove(panel);
        }

        if(actual.equals("logIn")){
            //remove(panel);
            panel = logInCambio();
            this.add(panel);

            this.repaint();
            this.revalidate();
        }

        if(actual.equals("loggedIn")){
            //remove(panel);
            panel = loggedInCambio();
            this.add(panel);

            this.repaint();
            this.revalidate();
        }

        if (actual.equals("ModificarCuenta")){
            panel = modificarCuenta();
            this.add(panel);

            this.repaint();
            this.revalidate();
        }

        if (actual.equals("RegistrarUsuario")){
            panel = pRegisterUser();
            this.add(panel);
            this.repaint();
            this.revalidate();
        }

        if (actual.equals("ComoCrearUsuario")){
            panel = pComoRegistrarUsuario();
            this.add(panel);
            this.repaint();
            this.revalidate();
        }
        if (actual.equals("lista")){
            panel = listaUsuarios();
            this.add(panel);
            this.repaint();
            this.revalidate();
        }
    }



    public JScrollPane tabla(){
        DefaultTableModel dfm = new DefaultTableModel();
        dfm.addColumn("Nombre");
        dfm.addColumn("Correo");
        dfm.addColumn("Acciones");
        String cuentas[];
        int a=0;
        try {
            FileReader fileReader = new FileReader("src/users.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                cuentas = linea.split(",");
                String temp[]={cuentas[0],cuentas[2]};
                dfm.addRow(temp);

            }
            fileReader.close();
            bufferedReader.close();
        } catch (IOException de) {
            de.printStackTrace();
        }

        JTable tablaUsers = new JTable(dfm);

        TableColumn botonBorrar = tablaUsers.getColumnModel().getColumn(2);

        botonBorrar.setCellRenderer(new ButtonRenderer());
        botonBorrar.setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane panelTabla = new JScrollPane(tablaUsers);
        getContentPane().add(panelTabla);
        panelTabla.setBounds(76, 267, 340, 200);
        setVisible(true);

        return panelTabla;
    }

    public JPanel listaUsuarios () {
        JPanel pLista = new JPanel();
        pLista.setSize(507,732);
        pLista.setLocation(91,61);
        pLista.setBackground(new Color(28, 27, 32));
        pLista.setLayout(null);

        JLabel list = new JLabel("Lista de usuarios");
        list.setBounds(109, 91, 283, 46);
        list.setHorizontalAlignment(SwingConstants.CENTER);
        list.setFont(new Font("Arial", Font.BOLD,25));
        list.setForeground(new Color(242, 247, 133));
        pLista.add(list);

        JLabel edittext = new JLabel("Editar");
        edittext.setBounds(76, 135, 188, 34);
        edittext.setFont(new Font("Arial", Font.BOLD,18));
        edittext.setForeground(new Color(242, 247, 133));
        pLista.add(edittext);


        JComboBox cajanombres = new JComboBox();
        cajanombres.setBounds(76, 168, 340, 34);
        pLista.add(cajanombres);
        nombrescaja= ButtonEditor.nombreUsuarios().split(",");


        for(int i = 0; i<nombrescaja.length; i++){
            cajanombres.addItem(nombrescaja[i]);

        }
        JButton editar = new JButton("Editar a "+cajanombres.getSelectedItem().toString());
        editar.setBounds(76, 213, 340, 34);
        editar.setFont(new Font("Arial", Font.BOLD, 18));
        editar.setForeground(new Color(28, 27, 32));
        editar.setBackground(new Color(242, 247, 133));
        pLista.add(editar);
        editar.addActionListener(new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent e) {

                String filename2 = "src/users.txt";
                boolean sePuedeEditar = false;
                try {
                    File inputFile = new File(filename2);
                    File tempFile = new File("src/temp.txt");

                    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                    String currentLine;
                    String userpoop[];
                    while ((currentLine = reader.readLine()) != null) {
                        userpoop = currentLine.split(",");
                        if (userpoop[2].equals(cajanombres.getSelectedItem())) {
                            sePuedeEditar = true;
                        }
                    }
                    writer.close();
                    reader.close();

                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "No se encontró el archivo '" + filename2 + "'");
                    ex.printStackTrace();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error al leer/escribir el archivo '" + filename2 + "'");
                    ex.printStackTrace();
                }
                if (sePuedeEditar== true){
                    String filename = "src/users.txt";
                    try {
                        File inputFile = new File(filename);
                        File tempFile = new File("src/temp.txt");

                        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                        String currentLine;
                        String userpoop[];
                        int currentRow = 0;
                        while ((currentLine = reader.readLine()) != null) {
                            userpoop = currentLine.split(",");
                            if (userpoop[2].equals(cajanombres.getSelectedItem())) {
                                if (cajanombres.getSelectedItem().equals(usuarioInfo[2])){
                                    vieneDeMenuBar = true;
                                }
                                usuarioInfoLista[0] = userpoop[0];
                                usuarioInfoLista[1] = userpoop[1];
                                usuarioInfoLista[2] = userpoop[2];
                                usuarioInfoLista[3] = userpoop[3];
                            }
                            currentRow++;
                        }
                        writer.close();
                        reader.close();

                    } catch (FileNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, "No se encontró el archivo '" + filename + "'");
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error al leer/escribir el archivo '" + filename + "'");
                        ex.printStackTrace();
                    }
                    anterior = actual;
                    actual = "ModificarCuenta";
                    limpiarVentana();
                }
                else{
                    JOptionPane.showMessageDialog(null,"No existe ya ese usuario", "Error", JOptionPane.ERROR_MESSAGE);
                    cajanombres.setSelectedItem(usuarioInfo[2]);
                    editar.setText("Editar a "+cajanombres.getSelectedItem().toString());
                }
            }

        });

        cajanombres.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                editar.setText("Editar a "+cajanombres.getSelectedItem().toString());
                cajanombres.removeAllItems();
                nombrescaja = ButtonEditor.nombreUsuarios().split(",");

                for(int i = 0; i< nombrescaja.length; i++){
                    cajanombres.addItem(nombrescaja[i]);

                }
                repaint();
                revalidate();
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                editar.setText("Editar a "+cajanombres.getSelectedItem().toString());

            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
                editar.setText("Editar a "+cajanombres.getSelectedItem().toString());
                cajanombres.removeAllItems();
                nombrescaja = ButtonEditor.nombreUsuarios().split(",");

                for(int i = 0; i< nombrescaja.length; i++){
                    cajanombres.addItem(nombrescaja[i]);

                }
                repaint();
                revalidate();
            }
        });





        pLista.add(tabla());





        return pLista;
    }


    public JPanel logInCambio (){
        JPanel pLogIn = new JPanel();
        pLogIn.setSize(507,732);
        pLogIn.setLocation(91,61);
        pLogIn.setBackground(Color.decode("#1C1B20"));
        pLogIn.setLayout(null);

        JLabel acceder = new JLabel("Accede a tu cuenta");
        acceder.setFont(new Font("Arial", Font.BOLD, 30));
        acceder.setForeground(Color.decode("#F2F785"));
        acceder.setHorizontalAlignment(SwingConstants.CENTER);
        acceder.setBounds(112, 60, 283, 148);
        pLogIn.add(acceder);

        JLabel enterUser = new JLabel("Ingrese su correo", JLabel.LEFT);
        enterUser.setForeground(Color.decode("#F2F785"));
        enterUser.setBounds(76, 304, 156, 34);
        enterUser.setFont(new Font("Arial", Font.BOLD, 17));
        enterUser.setVisible(true);
        pLogIn.add(enterUser);

        JTextField usuarioTF = new JTextField("");
        usuarioTF.setBounds(76, 337, 348, 34);
        usuarioTF.setVisible(true);
        usuarioTF.getBorder();
        pLogIn.add(usuarioTF);

        JLabel enterPass = new JLabel("Ingrese su contraseña", JLabel.LEFT);
        enterPass.setFont(new Font("Arial", Font.BOLD,17));
        enterPass.setForeground(Color.decode("#F2F785"));
        enterPass.setBounds(76, 375, 300, 34);
        enterPass.setVisible(true);
        pLogIn.add(enterPass);

        JPasswordField passwordTF = new JPasswordField("");
        passwordTF.setFont(new Font("Arial", Font.BOLD,24));
        passwordTF.setBounds(76, 414, 348, 34);
        passwordTF.setVisible(true);
        pLogIn.add(passwordTF);

        JButton logIn = new JButton("Acceder");
        logIn.setFont(new Font("Arial", Font.BOLD,24));
        logIn.setBounds(132, 507, 247, 61);
        logIn.setVisible(true);
        logIn.setOpaque(true);
        logIn.setBackground(Color.decode("#F2F785"));
        logIn.setBorderPainted(false);
        pLogIn.add(logIn);

        logIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName, passwordConf;
                String [] cuentas;
                int entro = 0;
                userName = usuarioTF.getText();
                passwordConf = String.valueOf(passwordTF.getPassword());
                try {
                    FileReader fileReader = new FileReader("src/users.txt");
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    String linea;
                    while ((linea = bufferedReader.readLine()) != null) {
                        cuentas = linea.split(",");
                        if (cuentas[2].equals(userName) && cuentas[3].equals(passwordConf)){
                            usuarioInfo = cuentas;
                            bienvenidoNombre = cuentas[0];
                            entro = 1;
                        }
                    }

                    if (entro == 0){
                        JOptionPane.showMessageDialog(null,"Datos incorrectos","mal:(!", JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Ingresando al sistema","Bien!", JOptionPane.INFORMATION_MESSAGE);
                        anterior = actual;
                        actual = "loggedIn";
                        limpiarVentana();
                    }
                    bufferedReader.close();
                    fileReader.close();
                } catch (IOException de) {
                    de.printStackTrace();
                }
            }
        });

        this.add(pLogIn);
        return pLogIn;
    }

    public static String cuentainiciada(){
        String lacuenta ="";
        lacuenta+=usuarioInfo[0]+","+usuarioInfo[2];
        return lacuenta;
    }
    public JPanel loggedInCambio (){
        JPanel pLoggedIn = new JPanel();
        pLoggedIn.setSize(507,732);
        pLoggedIn.setLocation(91,61);
        pLoggedIn.setBackground(Color.decode("#1C1B20"));
        pLoggedIn.setLayout(null);

        JLabel bienvenido = new JLabel("Hola " +bienvenidoNombre); // ver como actualizar el nombre cuando se cambia el usuario
        bienvenido.setBounds(113, 123, 283, 148);
        bienvenido.setFont(new Font("Arial", Font.BOLD, 24));
        bienvenido.setHorizontalAlignment(SwingConstants.CENTER);
        bienvenido.setForeground(Color.decode("#F2F785"));
        bienvenido.setVisible(true);
        pLoggedIn.add(bienvenido);

        JMenuBar barraMenu = new JMenuBar();
        barraMenu.setOpaque(true);
        barraMenu.setBackground(Color.decode("#DEDEDE"));
        barraMenu.setVisible(true);

        JMenu cuenta = new JMenu("Cuenta");
        JMenuItem miCuenta = new JMenuItem("Mi cuenta");
        JMenuItem cerrarSesion = new JMenuItem("Cerrar sesion");
        JMenuItem inicioCuenta = new JMenuItem("Inicio");
        miCuenta.setOpaque(true);
        miCuenta.setBackground(Color.decode("#DEDEDE"));
        cerrarSesion.setOpaque(true);
        cerrarSesion.setBackground(Color.decode("#DEDEDE"));
        inicioCuenta.setOpaque(true);
        inicioCuenta.setBackground(Color.decode("#DEDEDE"));
        cuenta.add(miCuenta);
        cuenta.add(cerrarSesion);
        cuenta.add(inicioCuenta);

        JMenu usuarios= new JMenu("Usuarios");
        JMenuItem listaDeUsuarios = new JMenuItem("Lista de usuarios");
        JMenuItem crearUsuario = new JMenuItem("Crear usuario");
        listaDeUsuarios.setOpaque(true);
        listaDeUsuarios.setBackground(Color.decode("#DEDEDE"));
        crearUsuario.setOpaque(true);
        crearUsuario.setBackground(Color.decode("#DEDEDE"));
        usuarios.add(listaDeUsuarios);
        usuarios.add(crearUsuario);

        JMenu ayuda= new JMenu("Ayuda");
        JMenuItem comoCrear = new JMenuItem("¿Cómo crear usuarios?");
        comoCrear.setOpaque(true);
        comoCrear.setBackground(Color.decode("#DEDEDE"));
        ayuda.add(comoCrear);


        barraMenu.add(cuenta);
        barraMenu.add(usuarios);
        barraMenu.add(ayuda);
        this.setJMenuBar(barraMenu);

        listaDeUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (actual != "lista"){
                    vieneDeMenuBar = false;
                    anterior = actual;
                    actual = "lista";
                    limpiarVentana();
                }
            }
        });
        cerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setJMenuBar(null);
                for (int i = 0; i<usuarioInfo.length; i++){
                    usuarioInfo[i] = null;
                }
                anterior = actual;
                actual = "logIn";
                vieneDeMenuBar = false;
                limpiarVentana();
            }
        });
        miCuenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (actual != "ModificarCuenta"){
                    vieneDeMenuBar = true;
                    anterior = actual;
                    actual = "ModificarCuenta";
                    limpiarVentana();
                }
            }
        });

        crearUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (actual != "RegistrarUsuario"){
                    vieneDeMenuBar = false;
                anterior = actual;
                actual = "RegistrarUsuario";
                limpiarVentana();
                }
            }
        });
        comoCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (actual != "ComoCrearUsuario"){
                    vieneDeMenuBar = false;
                anterior = actual;
                actual = "ComoCrearUsuario";
                limpiarVentana();
                }
            }
        });

        inicioCuenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (actual != "loggedIn"){
                    vieneDeMenuBar = false;
                    anterior = actual;
                    actual = "loggedIn";
                    limpiarVentana();
                }
            }
        });

        this.add(pLoggedIn);
        return pLoggedIn;
    }
    public JPanel modificarCuenta (){

        JPanel pChangeData = new JPanel();
        pChangeData.setSize(507,732);
        pChangeData.setLocation(91,61);
        pChangeData.setBackground(new Color(28, 27, 32));
        pChangeData.setLayout(null);
        this.add(pChangeData);


        JLabel micuenta = new JLabel("Mi cuenta personal");
        micuenta.setFont(new Font("Arial", Font.BOLD, 25));
        micuenta.setHorizontalAlignment(SwingConstants.CENTER);
        micuenta.setForeground(new Color(242, 247, 133));
        micuenta.setBounds(76, 70, 359, 46);
        pChangeData.add(micuenta);

        JLabel cuentaimagen = new JLabel("");
        cuentaimagen.setHorizontalAlignment(SwingConstants.CENTER);
        cuentaimagen.setHorizontalTextPosition(SwingConstants.CENTER);
        cuentaimagen.setIcon(new ImageIcon("src/datos2.png"));
        cuentaimagen.setBounds(166, 127, 173, 108);
        pChangeData.add(cuentaimagen);

        JLabel cambiarNombre = new JLabel("Nombre:");
        cambiarNombre.setForeground(new Color(242, 247, 133));
        cambiarNombre.setHorizontalAlignment(SwingConstants.LEFT);
        cambiarNombre.setFont(new Font("Arial", Font.BOLD, 18));
        cambiarNombre.setBounds(76, 246, 188, 34);
        pChangeData.add(cambiarNombre);

        JTextField cambiarNombreTF = new JTextField(50);
        cambiarNombreTF.setBounds(76, 277, 348, 34);
        pChangeData.add(cambiarNombreTF);


        JLabel cambiarApellidos = new JLabel("Apellidos:");
        cambiarApellidos.setHorizontalAlignment(SwingConstants.LEFT);
        cambiarApellidos.setForeground(new Color(242, 247, 133));
        cambiarApellidos.setFont(new Font("Arial", Font.BOLD, 18));
        cambiarApellidos.setBounds(76, 328, 188, 34);
        pChangeData.add(cambiarApellidos);

        JTextField cambiarApellidosTF = new JTextField();
        cambiarApellidosTF.setBounds(76, 362, 348, 34);
        pChangeData.add(cambiarApellidosTF);


        JLabel cambiarCorreo = new JLabel("Correo:");
        cambiarCorreo.setHorizontalAlignment(SwingConstants.LEFT);
        cambiarCorreo.setForeground(new Color(242, 247, 133));
        cambiarCorreo.setFont(new Font("Arial", Font.BOLD, 18));
        cambiarCorreo.setBounds(76, 407, 188, 34);
        pChangeData.add(cambiarCorreo);

        JTextField cambiarCorreoTF = new JTextField();
        cambiarCorreoTF.setBounds(76, 442, 348, 34);
        pChangeData.add(cambiarCorreoTF);


        JLabel cambiarPassword = new JLabel("Contraseña:");
        cambiarPassword.setHorizontalAlignment(SwingConstants.LEFT);
        cambiarPassword.setForeground(new Color(242, 247, 133));
        cambiarPassword.setFont(new Font("Arial", Font.BOLD, 18));
        cambiarPassword.setBounds(76, 487, 188, 34);
        pChangeData.add(cambiarPassword);

        JPasswordField cambiarPasswordTF = new JPasswordField();
        cambiarPasswordTF.setBounds(76, 519, 348, 34);
        pChangeData.add(cambiarPasswordTF);


        JButton cancelar = new JButton("Cancelar");
        cancelar.setFont(new Font("Arial", Font.BOLD, 22));
        cancelar.setBorderPainted(false);
        cancelar.setForeground(new Color(255, 255, 255));
        cancelar.setBackground(new Color(65, 105, 225));
        cancelar.setBounds(76, 575, 173, 34);
        pChangeData.add(cancelar);

        JButton actualizar = new JButton("Actualizar");
        actualizar.setFont(new Font("Arial", Font.BOLD, 22));
        actualizar.setForeground(new Color(28, 27, 32));
        actualizar.setBorderPainted(false);
        actualizar.setBackground(new Color(255, 215, 0));
        actualizar.setBounds(251, 575,173, 34);
        pChangeData.add(actualizar);

        JPanel fondo = new JPanel();
        fondo.setBackground(new Color(28, 27, 32));
        fondo.setBounds(61, 246, 377, 393);
        pChangeData.add(fondo);

        // acciones de botones
        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bienvenidoNombre = usuarioInfo[0];
                if (anterior != "ModificarCuenta"){
                    String bubble = anterior;
                    anterior = actual;
                    actual = bubble;
                    limpiarVentana();
                }
            }
        });

        actualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File vacia = new File("src/users.txt");
                if (leerParaCreer(cambiarCorreoTF.getText(), contadorFilas("src/users.txt")) == false && vacia.length() != 0 && !cambiarNombreTF.getText().equals("")&& !cambiarApellidosTF.getText().equals("")&& !cambiarCorreoTF.getText().equals("")&& !new String(cambiarPasswordTF.getPassword()).equals("")){

                    JOptionPane.showMessageDialog(null,"todo bien","BIEN:)!", JOptionPane.INFORMATION_MESSAGE);
                    if (vieneDeMenuBar == true){
                        datosAnteriores = usuarioInfo[0]+","+usuarioInfo[1]+","+usuarioInfo[2]+","+usuarioInfo[3];
                        System.out.println("luigi");
                    }
                    else{

                        System.out.println("wja");
                        datosAnteriores = usuarioInfoLista[0]+","+usuarioInfoLista[1]+","+usuarioInfoLista[2]+","+usuarioInfoLista[3];

                    }
                    datosNuevos = cambiarNombreTF.getText()+","+cambiarApellidosTF.getText()+","+cambiarCorreoTF.getText()+","+new String(cambiarPasswordTF.getPassword());

                    actualizarDatos("src/users.txt", datosAnteriores, datosNuevos, contadorFilas("src/users.txt"));
                    if (vieneDeMenuBar == true){
                        datosAnteriores = datosNuevos;
                        usuarioInfo[0] = cambiarNombreTF.getText();
                        usuarioInfo[1] = cambiarApellidosTF.getText();
                        usuarioInfo[2] = cambiarCorreoTF.getText();
                        usuarioInfo[3] = new String(cambiarPasswordTF.getPassword());
                        bienvenidoNombre = usuarioInfo[0];
                        vieneDeMenuBar = false;
                        cuentainiciada();
                    }
                }
                else if (cambiarNombreTF.getText().equals("") || cambiarApellidosTF.getText().equals("")|| cambiarCorreoTF.getText().equals("")|| new String(cambiarPasswordTF.getPassword()).equals("")){
                    JOptionPane.showMessageDialog(null,"Rellene todos los campos solicitados","mal:(!", JOptionPane.ERROR_MESSAGE);
                }
                else if (vacia.length() == 0){
                    JOptionPane.showMessageDialog(null,"Al parecer toda la información de los usuarios ha sido borrada que pena, si fue usted puede revertirlo con ctrl+z","mal:(!", JOptionPane.ERROR_MESSAGE);
                    vacia.delete();
                }
                else{
                    JOptionPane.showMessageDialog(null,"El correo ingresado ya existe","mal:(!", JOptionPane.ERROR_MESSAGE);

                }
                cuentainiciada();
            }
        });


        return pChangeData;
    }

    JPanel pRegisterUser(){
        JPanel pRegister = new JPanel();
        pRegister.setSize(507,732);
        pRegister.setLocation(91,61);
        pRegister.setBackground(new Color(28, 27, 32));
        pRegister.setLayout(null);

        JLabel crearrrr = new JLabel("Crear usuario");
        crearrrr.setFont(new Font("Arial", Font.BOLD, 25));
        crearrrr.setHorizontalAlignment(SwingConstants.CENTER);
        crearrrr.setForeground(new Color(242, 247, 133));
        crearrrr.setBounds(110, 50, 283, 46);
        pRegister.add(crearrrr);

        JLabel userrr = new JLabel("");
        userrr.setIcon(new ImageIcon("src/userReg.png"));
        userrr.setBounds(188, 107, 150, 150);
        userrr.setHorizontalAlignment(SwingConstants.CENTER);
        pRegister.add(userrr);

        JLabel nombre = new JLabel("Nombre:", JLabel.LEFT);
        nombre.setForeground(new Color(242, 247, 133));
        nombre.setHorizontalAlignment(SwingConstants.LEFT);
        nombre.setFont(new Font("Arial", Font.BOLD, 18));
        nombre.setBounds(76, 246, 188, 34);
        nombre.setVisible(true);
        pRegister.add(nombre);

        JTextField nombreTF = new JTextField("");
        nombreTF.setBounds(76, 277, 348, 34);
        nombreTF.setVisible(true);
        nombreTF.getBorder();
        pRegister.add(nombreTF);

        JLabel apellidos = new JLabel("Apellidos:", JLabel.LEFT);
        apellidos.setHorizontalAlignment(SwingConstants.LEFT);
        apellidos.setForeground(new Color(242, 247, 133));
        apellidos.setFont(new Font("Arial", Font.BOLD, 18));
        apellidos.setBounds(76, 328, 188, 34);
        apellidos.setVisible(true);
        pRegister.add(apellidos);

        JTextField apellidosTF = new JTextField("");
        apellidosTF.setBounds(76,362,348,34);
        apellidosTF.setVisible(true);
        apellidosTF.getBorder();
        pRegister.add(apellidosTF);

        JLabel correo = new JLabel("Correo:", JLabel.LEFT);
        correo.setHorizontalAlignment(SwingConstants.LEFT);
        correo.setForeground(new Color(242, 247, 133));
        correo.setFont(new Font("Arial", Font.BOLD, 18));
        correo.setBounds(76, 407, 188, 34);
        correo.setVisible(true);
        pRegister.add(correo);

        JTextField correoTF = new JTextField("");
        correoTF.setBounds(76, 442, 348, 34);
        correoTF.setVisible(true);
        correoTF.getBorder();
        pRegister.add(correoTF);

        JLabel passwordNew = new JLabel("Contraseña:", JLabel.LEFT);
        passwordNew.setHorizontalAlignment(SwingConstants.LEFT);
        passwordNew.setForeground(new Color(242, 247, 133));
        passwordNew.setFont(new Font("Arial", Font.BOLD, 18));
        passwordNew.setBounds(76, 487, 188, 34);
        passwordNew.setVisible(true);
        pRegister.add(passwordNew);

        JPasswordField passwordNewTF = new JPasswordField("");
        passwordNewTF.setBounds(76, 519, 348, 34);
        passwordNewTF.setVisible(true);
        passwordNewTF.getBorder();
        pRegister.add(passwordNewTF);

        JLabel passwordNew2 = new JLabel("Confirmar contraseña:", JLabel.LEFT);
        passwordNew2.setHorizontalAlignment(SwingConstants.LEFT);
        passwordNew2.setForeground(new Color(242, 247, 133));
        passwordNew2.setFont(new Font("Arial", Font.BOLD, 18));
        passwordNew2.setBounds(76, 558, 211, 34);
        passwordNew2.setVisible(true);
        pRegister.add(passwordNew2);

        JPasswordField passwordNew2TF = new JPasswordField("");
        passwordNew2TF.setBounds(76, 586, 348, 34);
        passwordNew2TF.setVisible(true);
        passwordNew2TF.getBorder();
        pRegister.add(passwordNew2TF);

        JButton cancelar = new JButton("Cancelar");
        cancelar.setBorderPainted(false);
        cancelar.setForeground(new Color(255, 255, 255));
        cancelar.setFont(new Font("Arial", Font.BOLD, 17));
        cancelar.setBackground(new Color(65, 105, 225));
        cancelar.setBounds(76, 652, 173, 34);
        cancelar.setVisible(true);
        pRegister.add(cancelar);

        JButton registrar = new JButton("Registrar");
        registrar.setBackground(new Color(255, 215, 0));
        registrar.setFont(new Font("Arial", Font.BOLD, 17));
        registrar.setForeground(new Color(28, 27, 32));
        registrar.setBorderPainted(false);
        registrar.setBounds(252, 652, 173, 34);
        registrar.setVisible(true);
        pRegister.add(registrar);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(28, 27, 32));
        panel_1.setBounds(42, 230, 421, 491);
        panel_1.setLayout(null);
        pRegister.add(panel_1);
        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bubble = anterior;
                anterior = actual;
                actual = bubble;
                limpiarVentana();
            }
        });

        registrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pass1, pass2;
                String nombreR = nombreTF.getText();
                String apellidoR = apellidosTF.getText();
                String correoR = correoTF.getText();
                String cuentas [];
                int registroR = 0;
                pass1 = new String(passwordNewTF.getPassword());
                pass2 = new String(passwordNew2TF.getPassword());

                if (nombreR.equals("") || apellidoR.equals("") || correoR.equals("") || pass1.equals("") || pass2.equals("")){
                        JOptionPane.showMessageDialog(null,"Rellene todos los campos","mal:(!", JOptionPane.ERROR_MESSAGE);
                }

                else{
                    if(pass1.equals(pass2) && !pass1.equals("") && !pass2.equals("")){
                        try {
                            FileReader fileReader = new FileReader("src/users.txt");
                            BufferedReader bufferedReader = new BufferedReader(fileReader);
                            String linea;
                            while ((linea = bufferedReader.readLine()) != null) {
                                cuentas = linea.split(",");
                                if (cuentas[2].equals(correoR)){
                                    registroR = 1;
                                }
                            }
                            if (registroR == 0){
                                try (FileWriter escritorArchivo = new FileWriter("src/users.txt", true);
                                     BufferedWriter escritorBuffer = new BufferedWriter(escritorArchivo);
                                     PrintWriter impresoraEScritora = new PrintWriter(escritorBuffer);)

                                {
                                    impresoraEScritora.println(nombreR + "," + apellidoR + "," + correoR + "," + pass1);

                                } catch (IOException i) {
                                    i.printStackTrace();
                                }
                                JOptionPane.showMessageDialog(null,"Registro exitoso","BIEN(:!", JOptionPane.INFORMATION_MESSAGE);
                            }
                            else{
                                JOptionPane.showMessageDialog(null,"El correo ingresado ya se encuentra registrado","mal:(!", JOptionPane.ERROR_MESSAGE);
                            }

                            bufferedReader.close();
                            fileReader.close();
                        } catch (IOException de) {
                            de.printStackTrace();
                        }


                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Las contraseñas no coinciden","mal:(!", JOptionPane.ERROR_MESSAGE);
                    }
                }
                }
            });
        return pRegister;
    }

    JPanel pComoRegistrarUsuario(){
        JPanel pComoRegistrar = new JPanel();
        pComoRegistrar.setSize(507,732);
        pComoRegistrar.setLocation(91,61);
        pComoRegistrar.setBackground(new Color(28, 27, 32));
        pComoRegistrar.setLayout(null);

        JLabel comoCrearUserLabel = new JLabel("¿Cómo crear usuario?");
        comoCrearUserLabel.setFont(new Font("Arial", Font.BOLD, 25));
        comoCrearUserLabel.setForeground(new Color(255, 215,0));
        comoCrearUserLabel.setHorizontalAlignment(SwingConstants.CENTER);
        comoCrearUserLabel.setBounds(75, 165, 359, 46);
        pComoRegistrar.add(comoCrearUserLabel);

        JButton crearUsuarioAhoraBoton = new JButton("Crear un usuario ahora");
        crearUsuarioAhoraBoton.setFont(new Font("Arial", Font.BOLD, 17));
        crearUsuarioAhoraBoton.setBounds(144, 500, 219, 34);
        crearUsuarioAhoraBoton.setForeground(new Color(255, 215,0));
        crearUsuarioAhoraBoton.setBackground(new Color(65, 105, 225));
        crearUsuarioAhoraBoton.setBorderPainted(false);
        pComoRegistrar.add(crearUsuarioAhoraBoton);


            crearUsuarioAhoraBoton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    anterior = actual;
                    actual = "RegistrarUsuario";
                    limpiarVentana();
                }
            });
        JTextArea comoCrearTA = new JTextArea();
        comoCrearTA.setText("\n    1.- Hacer click en la opción 'usuarios' " +
                            "\n    en el menú superior." +
                            "\n"+
                            "\n    2.- Hacer click en 'Crear usuario'." +
                            "\n"+
                            "\n    3.- Llenar los campos solicitados." +
                            "\n"+
                            "\n    4.- Hacer click en 'Registrar'." +
                            "\n"+
                            "\n    8.- Listo, el usuario se ha creado.");

        comoCrearTA.setFont(new Font("Arial", Font.BOLD, 18));
        comoCrearTA.setEditable(false);
        comoCrearTA.setForeground(new Color(255, 215,0));
        comoCrearTA.setBounds(76, 225, 353, 296);
        comoCrearTA.setBackground(new Color(40, 40, 43));

        pComoRegistrar.add(comoCrearTA);


        return pComoRegistrar;
    }
}