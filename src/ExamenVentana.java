import org.w3c.dom.CDATASection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private static String usuarioInfo [] = new String[4];

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
            }
            return filas;
        }
        catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    public ExamenVentana() throws InterruptedException {

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

    public String nombreUsuarios(){
        String cuentas[];
        String nombrecuentas="";


        try {
            FileReader fileReader = new FileReader("src/users.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                cuentas = linea.split(",");
                nombrecuentas+=cuentas[0]+",";



            }
            fileReader.close();
        } catch (IOException de) {
            de.printStackTrace();
        }
        return nombrecuentas;
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
        } catch (IOException de) {
            de.printStackTrace();
        }

        JTable tablaUsers = new JTable(dfm);

        TableColumn botonBorrar = tablaUsers.getColumnModel().getColumn(2);

        botonBorrar.setCellRenderer(new ButtonRenderer());
        botonBorrar.setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane panelTabla = new JScrollPane(tablaUsers);
        getContentPane().add(panelTabla);
        panelTabla.setBounds(76, 267, 340, 401);
        setVisible(true);

        return panelTabla;
    }
    public JPanel listaUsuarios () {
        JPanel pLista = new JPanel();
        pLista.setSize(507,732);
        pLista.setLocation(91,61);
        pLista.setBackground(new Color(0, 255, 127));
        pLista.setLayout(null);

        JLabel list = new JLabel("Lista de usuarios");
        list.setBounds(109, 91, 283, 46);
        list.setHorizontalAlignment(SwingConstants.CENTER);
        list.setFont(new Font("Arial", Font.BOLD,25));
        pLista.add(list);

        JLabel edittext = new JLabel("Editar");
        edittext.setBounds(76, 135, 188, 34);
        edittext.setFont(new Font("Arial", Font.BOLD,18));
        pLista.add(edittext);


        JComboBox cajanombres = new JComboBox();
        cajanombres.setBounds(76, 168, 340, 34);
        pLista.add(cajanombres);
        String nombrescaja[];
        nombrescaja = nombreUsuarios().split(",");

        for(int i=0;i<nombrescaja.length;i++){
            cajanombres.addItem(nombrescaja[i]);

        }

        JButton editar = new JButton("Editar a "+cajanombres.getSelectedItem().toString());
        editar.setBounds(76, 213, 340, 34);
        editar.setFont(new Font("Arial", Font.BOLD, 18));
        editar.setBackground(new Color(0, 255, 0));
        pLista.add(editar);

        cajanombres.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editar.setText("Editar a "+cajanombres.getSelectedItem().toString());
            }
        });
        pLista.add(tabla());





        return pLista;
    }


    public JPanel logInCambio (){
        JPanel pLogIn = new JPanel();
        pLogIn.setSize(507,732);
        pLogIn.setLocation(91,61);
        pLogIn.setBackground(new Color(0, 255, 127));
        pLogIn.setLayout(null);


        JLabel acceder = new JLabel("Accede a tu cuenta");
        acceder.setFont(new Font("Arial", Font.PLAIN, 25));
        acceder.setHorizontalAlignment(SwingConstants.CENTER);
        acceder.setBounds(112, 60, 283, 148);
        pLogIn.add(acceder);

        JLabel enterUser = new JLabel("Ingrese su correo", JLabel.LEFT);
        enterUser.setBounds(76, 304, 156, 34);
        enterUser.setFont(new Font("Arial", Font.PLAIN, 17));
        enterUser.setForeground(new Color(67, 42, 42));
        enterUser.setVisible(true);
        pLogIn.add(enterUser);

        JTextField usuarioTF = new JTextField("nombre");
        usuarioTF.setBounds(76, 337, 348, 34);
        usuarioTF.setVisible(true);
        usuarioTF.getBorder();
        pLogIn.add(usuarioTF);

        JLabel enterPass = new JLabel("Ingrese su contraseña", JLabel.LEFT);
        enterPass.setBounds(76, 375, 188, 34);
        enterPass.setFont(new Font("Arial", Font.PLAIN, 17));
        enterPass.setForeground(new Color(67, 42, 42));
        enterPass.setVisible(true);
        pLogIn.add(enterPass);

        JPasswordField passwordTF = new JPasswordField("contra");
        passwordTF.setBounds(76, 414, 348, 34);
        passwordTF.setVisible(true);
        pLogIn.add(passwordTF);

        JButton logIn = new JButton("Acceder");
        logIn.setBounds(132, 507, 247, 61);
        logIn.setVisible(true);
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
                        if (cuentas[0].equals(userName) && cuentas[3].equals(passwordConf)){
                            usuarioInfo = cuentas;
                            bienvenidoNombre = userName;
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
                    fileReader.close();
                } catch (IOException de) {
                    de.printStackTrace();
                }
            }
        });

        this.add(pLogIn);
        return pLogIn;
    }
    public JPanel loggedInCambio (){
        JPanel pLoggedIn = new JPanel();
        pLoggedIn.setSize(507,732);
        pLoggedIn.setLocation(91,61);
        pLoggedIn.setBackground(new Color(0, 255, 127));
        pLoggedIn.setLayout(null);

        JLabel bienvenido = new JLabel("Hola " +bienvenidoNombre); // ver como actualizar el nombre cuando se cambia el usuario
        bienvenido.setBounds(113, 123, 283, 148);
        bienvenido.setFont(new Font("Arial", Font.BOLD, 24));
        bienvenido.setHorizontalAlignment(SwingConstants.CENTER);
        bienvenido.setVisible(true);
        pLoggedIn.add(bienvenido);

        JMenuBar barraMenu = new JMenuBar();
        barraMenu.setVisible(true);

        JMenu cuenta = new JMenu("Cuenta");
        JMenuItem miCuenta = new JMenuItem("Mi cuenta");
        JMenuItem cerrarSesion = new JMenuItem("Cerrar sesion");
        cuenta.add(miCuenta);
        cuenta.add(cerrarSesion);

        JMenu usuarios= new JMenu("Usuarios");
        JMenuItem listaDeUsuarios = new JMenuItem("Lista de usuarios");
        JMenuItem crearUsuario = new JMenuItem("Crear usuario");
        usuarios.add(listaDeUsuarios);
        usuarios.add(crearUsuario);

        JMenu ayuda= new JMenu("Ayuda");
        JMenuItem comoCrear = new JMenuItem("Como crear usuarios?");
        ayuda.add(comoCrear);


        barraMenu.add(cuenta);
        barraMenu.add(usuarios);
        barraMenu.add(ayuda);
        this.setJMenuBar(barraMenu);

        listaDeUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                anterior = actual;
                actual = "lista";
                limpiarVentana();
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
                limpiarVentana();
            }
        });
        miCuenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (actual != "ModificarCuenta"){
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
                anterior = actual;
                actual = "ComoCrearUsuario";
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
        pChangeData.setBackground(new Color(0, 255, 127));
        pChangeData.setLayout(null);
        this.add(pChangeData);


        JLabel micuenta = new JLabel("Mi cuenta personal");
        micuenta.setFont(new Font("Arial", Font.BOLD, 25));
        micuenta.setHorizontalAlignment(SwingConstants.CENTER);
        micuenta.setBounds(76, 70, 359, 46);
        pChangeData.add(micuenta);

        JLabel cuentaimagen = new JLabel("");
        cuentaimagen.setHorizontalAlignment(SwingConstants.CENTER);
        cuentaimagen.setHorizontalTextPosition(SwingConstants.CENTER);
        cuentaimagen.setIcon(new ImageIcon("src/cuenta.png"));
        cuentaimagen.setBackground(new Color(255, 255, 255));
        cuentaimagen.setBounds(166, 127, 173, 108);
        pChangeData.add(cuentaimagen);

        JLabel cambiarNombre = new JLabel("Nombre:");
        cambiarNombre.setForeground(new Color(0, 0, 0));
        cambiarNombre.setHorizontalAlignment(SwingConstants.LEFT);
        cambiarNombre.setFont(new Font("Arial", Font.BOLD, 18));
        cambiarNombre.setBounds(76, 246, 188, 34);
        pChangeData.add(cambiarNombre);

        JTextField cambiarNombreTF = new JTextField(50);
        cambiarNombreTF.setBounds(76, 277, 348, 34);
        pChangeData.add(cambiarNombreTF);


        JLabel cambiarApellidos = new JLabel("Apellidos:");
        cambiarApellidos.setHorizontalAlignment(SwingConstants.LEFT);
        cambiarApellidos.setForeground(Color.BLACK);
        cambiarApellidos.setFont(new Font("Arial", Font.BOLD, 18));
        cambiarApellidos.setBounds(76, 328, 188, 34);
        pChangeData.add(cambiarApellidos);

        JTextField cambiarApellidosTF = new JTextField();
        cambiarApellidosTF.setBounds(76, 362, 348, 34);
        pChangeData.add(cambiarApellidosTF);


        JLabel cambiarCorreo = new JLabel("Correo:");
        cambiarCorreo.setHorizontalAlignment(SwingConstants.LEFT);
        cambiarCorreo.setForeground(Color.BLACK);
        cambiarCorreo.setFont(new Font("Arial", Font.BOLD, 18));
        cambiarCorreo.setBounds(76, 407, 188, 34);
        pChangeData.add(cambiarCorreo);

        JTextField cambiarCorreoTF = new JTextField();
        cambiarCorreoTF.setBounds(76, 442, 348, 34);
        pChangeData.add(cambiarCorreoTF);


        JLabel cambiarPassword = new JLabel("Contraseña:");
        cambiarPassword.setHorizontalAlignment(SwingConstants.LEFT);
        cambiarPassword.setForeground(Color.BLACK);
        cambiarPassword.setFont(new Font("Arial", Font.BOLD, 18));
        cambiarPassword.setBounds(76, 487, 188, 34);
        pChangeData.add(cambiarPassword);

        JPasswordField cambiarPasswordTF = new JPasswordField();
        cambiarPasswordTF.setBounds(76, 519, 348, 34);
        pChangeData.add(cambiarPasswordTF);


        JButton cancelar = new JButton("Cancelar");
        cancelar.setFont(new Font("Arial", Font.BOLD, 11));
        cancelar.setBorderPainted(false);
        cancelar.setBackground(Color.RED);
        cancelar.setBounds(76, 575, 173, 34);
        pChangeData.add(cancelar);

        JButton actualizar = new JButton("Actualizar");
        actualizar.setFont(new Font("Arial", Font.BOLD, 11));
        actualizar.setBorderPainted(false);
        actualizar.setBackground(new Color(50, 205, 50));
        actualizar.setBounds(251, 575,173, 34);
        pChangeData.add(actualizar);

        JPanel fondo = new JPanel();
        fondo.setBackground(new Color(60, 179, 113));
        fondo.setBounds(61, 246, 377, 393);
        pChangeData.add(fondo);

        // acciones de botones
        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bienvenidoNombre = usuarioInfo[0];
                String bubble = anterior;
                anterior = actual;
                actual = bubble;
                limpiarVentana();
            }
        });

        actualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File vacia = new File("src/users.txt");
                if (leerParaCreer(cambiarCorreoTF.getText(), contadorFilas("src/users.txt")) == false && vacia.length() != 0 && !cambiarNombreTF.getText().equals("")&& !cambiarApellidosTF.getText().equals("")&& !cambiarCorreoTF.getText().equals("")&& !new String(cambiarPasswordTF.getPassword()).equals("")){

                    JOptionPane.showMessageDialog(null,"todo bien","BIEN:)!", JOptionPane.INFORMATION_MESSAGE);

                    datosAnteriores = usuarioInfo[0]+","+usuarioInfo[1]+","+usuarioInfo[2]+","+usuarioInfo[3];
                    datosNuevos = cambiarNombreTF.getText()+","+cambiarApellidosTF.getText()+","+cambiarCorreoTF.getText()+","+new String(cambiarPasswordTF.getPassword());

                    actualizarDatos("src/users.txt", datosAnteriores, datosNuevos, contadorFilas("src/users.txt"));
                    datosAnteriores = datosNuevos;
                    usuarioInfo[0] = cambiarNombreTF.getText();
                    usuarioInfo[1] = cambiarApellidosTF.getText();
                    usuarioInfo[2] = cambiarCorreoTF.getText();
                    usuarioInfo[3] = new String(cambiarPasswordTF.getPassword());
                    bienvenidoNombre = usuarioInfo[0];
                    String bubble = anterior;
                    anterior = actual;
                    actual = bubble;
                    limpiarVentana();
                }
                else if (cambiarNombreTF.getText().equals("") || cambiarApellidosTF.getText().equals("")|| cambiarCorreoTF.getText().equals("")|| new String(cambiarPasswordTF.getPassword()).equals("")){
                    JOptionPane.showMessageDialog(null,"Rellene todos los campos","mal:(!", JOptionPane.ERROR_MESSAGE);
                }
                else if (vacia.length() == 0){
                    JOptionPane.showMessageDialog(null,"Al parecer toda la informacion de los usuarios ha sido borrada por alguna mañosada, reviertalo","mal:(!", JOptionPane.ERROR_MESSAGE);

                }
                else{
                    JOptionPane.showMessageDialog(null,"El correo ingresado ya existe","mal:(!", JOptionPane.ERROR_MESSAGE);

                }
            }
        });


        return pChangeData;
    }

    JPanel pRegisterUser(){
        JPanel pRegister = new JPanel();
        pRegister.setSize(507,732);
        pRegister.setLocation(91,61);
        pRegister.setBackground(new Color(0, 255, 127));
        pRegister.setLayout(null);

        JLabel crearrrr = new JLabel("Crear usuario");
        crearrrr.setFont(new Font("Arial", Font.BOLD, 25));
        crearrrr.setHorizontalAlignment(SwingConstants.CENTER);
        crearrrr.setBounds(110, 50, 283, 46);
        pRegister.add(crearrrr);

        JLabel userrr = new JLabel("");
        userrr.setIcon(new ImageIcon("src/crearr.png"));
        userrr.setBounds(188, 107, 110, 133);
        pRegister.add(userrr);

        JLabel nombre = new JLabel("Nombre:", JLabel.LEFT);
        nombre.setForeground(new Color(0, 0, 0));
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
        apellidos.setForeground(Color.BLACK);
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
        correo.setForeground(Color.BLACK);
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
        passwordNew.setForeground(Color.BLACK);
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
        passwordNew2.setForeground(Color.BLACK);
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
        cancelar.setForeground(Color.BLACK);
        cancelar.setFont(new Font("Arial", Font.BOLD, 11));
        cancelar.setBackground(new Color(255, 0, 0));
        cancelar.setBounds(76, 652, 173, 34);
        cancelar.setVisible(true);
        pRegister.add(cancelar);

        JButton registrar = new JButton("Registrar");
        registrar.setBackground(new Color(50, 205, 50));
        registrar.setFont(new Font("Arial", Font.BOLD, 11));
        registrar.setBorderPainted(false);
        registrar.setBounds(252, 652, 173, 34);
        registrar.setVisible(true);
        pRegister.add(registrar);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(60, 179, 113));
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
                                setJMenuBar(null);
                                anterior = actual;
                                actual = "logIn";
                                limpiarVentana();
                            }
                            else{
                                JOptionPane.showMessageDialog(null,"El correo ingresado ya se encuentra registrado","mal:(!", JOptionPane.ERROR_MESSAGE);
                            }
                            fileReader.close();
                        } catch (IOException de) {
                            de.printStackTrace();
                        }


                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Las contrase;as no coinciden","mal:(!", JOptionPane.ERROR_MESSAGE);
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
        pComoRegistrar.setBackground(new Color(0, 255, 127));
        pComoRegistrar.setLayout(null);

        JLabel comoCrearUserLabel = new JLabel("¿Como crear usuario?");
        comoCrearUserLabel.setFont(new Font("Arial", Font.BOLD, 25));
        comoCrearUserLabel.setHorizontalAlignment(SwingConstants.CENTER);
        comoCrearUserLabel.setBounds(75, 165, 359, 46);
        pComoRegistrar.add(comoCrearUserLabel);

        JButton crearUsuarioAhoraBoton = new JButton("Crear un usuario ahora");
        crearUsuarioAhoraBoton.setFont(new Font("Arial", Font.BOLD, 13));
        crearUsuarioAhoraBoton.setBounds(144, 500, 219, 34);
        crearUsuarioAhoraBoton.setBackground(new Color(50, 205, 50));
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
                            "\n    en el menú superior" +
                            "\n"+
                            "\n    2.- Hacer click en 'Crear usuario'" +
                            "\n"+
                            "\n    3.- Llenar los campos solicitados" +
                            "\n"+
                            "\n    4.- Hacer click en 'Registrar'" +
                            "\n"+
                            "\n    8.- Listo, el usuario se ha creado");

        comoCrearTA.setFont(new Font("Arial", Font.BOLD, 18));
        comoCrearTA.setEditable(false);
        comoCrearTA.setBounds(76, 225, 353, 296);
        comoCrearTA.setBackground(new Color(0, 255, 127));

        pComoRegistrar.add(comoCrearTA);


        return pComoRegistrar;
    }
}