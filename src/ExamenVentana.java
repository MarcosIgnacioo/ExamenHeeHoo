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
        this.getContentPane().setBackground(Color.ORANGE);
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
        actual="lista";
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
        String hola[]= new String[2];
        hola[0]="Wep";
        hola[1]="Si";
        String hola2[]= new String[2];
        hola2[0]="asd";
        hola2[1]="Sasdi";

        DefaultTableModel dfm = new DefaultTableModel();
        dfm.addColumn("Usuario");
        dfm.addColumn("Nombre");
        dfm.addColumn("Acciones");
        dfm.addRow(hola);
        dfm.addRow(hola2);
        JTable tablaUsers = new JTable(dfm);

        TableColumn botonBorrar = tablaUsers.getColumnModel().getColumn(2);

        botonBorrar.setCellRenderer(new ButtonRenderer());
        botonBorrar.setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane panelTabla = new JScrollPane(tablaUsers);
        getContentPane().add(panelTabla);
        panelTabla.setSize(400,370);
        panelTabla.setLocation(100,300);
        setVisible(true);

        return panelTabla;
    }
    public JPanel listaUsuarios () {
        JPanel pLista = new JPanel();
        pLista.setSize(600,700);
        pLista.setLocation(50,50);
        pLista.setBackground(Color.green);
        pLista.setLayout(null);

        JLabel list = new JLabel("Lista de usuarios");
        list.setSize(250,30);
        list.setLocation(180,100);
        list.setFont(new Font("Roboto Slab", Font.BOLD,30));
        pLista.add(list);

        JLabel edittext = new JLabel("Editar");
        edittext.setSize(100,20);
        edittext.setLocation(100,200);
        edittext.setFont(new Font("Roboto Slab", Font.BOLD,20));
        pLista.add(edittext);


        JComboBox cajanombres = new JComboBox();
        cajanombres.setSize(400,30);
        cajanombres.setLocation(100,225);
        pLista.add(cajanombres);
        String nombrescaja[];
        nombrescaja = nombreUsuarios().split(",");

        for(int i=0;i<nombrescaja.length;i++){
            cajanombres.addItem(nombrescaja[i]);

        }

        JButton editar = new JButton("Editar");
        editar.setSize(400,30);
        editar.setLocation(100,260);
        editar.setBackground(Color.blue);
        editar.setForeground(Color.white);
        pLista.add(editar);

        pLista.add(tabla());





        return pLista;
    }


    public JPanel logInCambio (){
        JPanel pLogIn = new JPanel();
        pLogIn.setSize(600,700);
        pLogIn.setLocation(50,50);
        pLogIn.setBackground(Color.CYAN);
        pLogIn.setLayout(null);


        JLabel enterUser = new JLabel("Ingrese su usuario", JLabel.LEFT);
        enterUser.setBounds(200,180,200,40);
        enterUser.setFont(new Font("Arial", Font.BOLD, 14));
        enterUser.setForeground(new Color(67, 42, 42));
        enterUser.setVisible(true);
        pLogIn.add(enterUser);

        JTextField usuarioTF = new JTextField("nombre");
        usuarioTF.setBounds(200,220,200,40);
        usuarioTF.setVisible(true);
        usuarioTF.getBorder();
        pLogIn.add(usuarioTF);

        JLabel enterPass = new JLabel("Ingrese su contraseña", JLabel.LEFT);
        enterPass.setBounds(200,270,200,40);
        enterPass.setFont(new Font("Arial", Font.BOLD, 14));
        enterPass.setForeground(new Color(67, 42, 42));
        enterPass.setVisible(true);
        pLogIn.add(enterPass);

        JPasswordField passwordTF = new JPasswordField("contra");
        passwordTF.setBounds(200,300,200,40);
        passwordTF.setVisible(true);
        pLogIn.add(passwordTF);

        JButton logIn = new JButton("Acceder");
        logIn.setBounds(200, 370, 150,40);
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
                        JOptionPane.showMessageDialog(enterPass,"Datos incorrectos","mal:(!", JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        JOptionPane.showMessageDialog(enterPass,"Ingresando al sistema","Bien!", JOptionPane.INFORMATION_MESSAGE);
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
        pLoggedIn.setSize(600,700);
        pLoggedIn.setLocation(50,50);
        pLoggedIn.setBackground(Color.pink);
        pLoggedIn.setLayout(null);

        JLabel bienvenido = new JLabel("Hola " +bienvenidoNombre); // ver como actualizar el nombre cuando se cambia el usuario
        bienvenido.setSize(400,30);
        bienvenido.setFont(new Font("Arial", Font.BOLD, 24));
        bienvenido.setLocation(0,0);
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
        pChangeData.setSize(600,700);
        pChangeData.setLocation(50,50);
        pChangeData.setBackground(Color.yellow);
        pChangeData.setLayout(null);
        this.add(pChangeData);

        JLabel cambiarNombre = new JLabel("Nombre:");
        cambiarNombre.setSize(100,100);
        cambiarNombre.setLocation(100,100);
        pChangeData.add(cambiarNombre);

        JTextField cambiarNombreTF = new JTextField(50);
        cambiarNombreTF.setSize(300,25);
        cambiarNombreTF.setLocation(100,180);
        pChangeData.add(cambiarNombreTF);


        JLabel cambiarApellidos = new JLabel("Apellidos:");
        cambiarApellidos.setSize(100,100);
        cambiarApellidos.setLocation(100,225);
        pChangeData.add(cambiarApellidos);

        JTextField cambiarApellidosTF = new JTextField();
        cambiarApellidosTF.setSize(300,25);
        cambiarApellidosTF.setLocation(100,305);
        pChangeData.add(cambiarApellidosTF);


        JLabel cambiarCorreo = new JLabel("Correo:");
        cambiarCorreo.setSize(100,100);
        cambiarCorreo.setLocation(100,330);
        pChangeData.add(cambiarCorreo);

        JTextField cambiarCorreoTF = new JTextField();
        cambiarCorreoTF.setSize(300,25);
        cambiarCorreoTF.setLocation(100,405);
        pChangeData.add(cambiarCorreoTF);


        JLabel cambiarPassword = new JLabel("Contrasena:");
        cambiarPassword.setSize(100,100);
        cambiarPassword.setLocation(100,430);
        pChangeData.add(cambiarPassword);

        JPasswordField cambiarPasswordTF = new JPasswordField();
        cambiarPasswordTF.setSize(300,25);
        cambiarPasswordTF.setLocation(100,510);
        pChangeData.add(cambiarPasswordTF);


        JButton cancelar = new JButton("Cancelar");
        cancelar.setSize(200,50);
        cancelar.setLocation(90,560);
        pChangeData.add(cancelar);

        JButton actualizar = new JButton("Actualizar");
        actualizar.setSize(200,50);
        actualizar.setLocation(310,560);
        pChangeData.add(actualizar);

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
        pRegister.setSize(600,700);
        pRegister.setLocation(50,50);
        pRegister.setBackground(Color.green);
        pRegister.setLayout(null);

        JLabel nombre = new JLabel("Ingrese su nombre", JLabel.LEFT);
        nombre.setBounds(215,180,200,40);
        nombre.setFont(new Font("Arial", Font.BOLD, 14));
        nombre.setForeground(new Color(67, 42, 42));
        nombre.setVisible(true);
        pRegister.add(nombre);

        JTextField nombreTF = new JTextField("");
        nombreTF.setBounds(215,220,200,40);
        nombreTF.setVisible(true);
        nombreTF.getBorder();
        pRegister.add(nombreTF);

        JLabel apellidos = new JLabel("Ingrese su apellido", JLabel.LEFT);
        apellidos.setBounds(215,250,200,40);
        apellidos.setFont(new Font("Arial", Font.BOLD, 14));
        apellidos.setForeground(new Color(67, 42, 42));
        apellidos.setVisible(true);
        pRegister.add(apellidos);

        JTextField apellidosTF = new JTextField("");
        apellidosTF.setBounds(215,280,200,40);
        apellidosTF.setVisible(true);
        apellidosTF.getBorder();
        pRegister.add(apellidosTF);

        JLabel correo = new JLabel("Ingrese su correo", JLabel.LEFT);
        correo.setBounds(215,310,200,40);
        correo.setFont(new Font("Arial", Font.BOLD, 14));
        correo.setForeground(new Color(67, 42, 42));
        correo.setVisible(true);
        pRegister.add(correo);

        JTextField correoTF = new JTextField("");
        correoTF.setBounds(215,340,200,40);
        correoTF.setVisible(true);
        correoTF.getBorder();
        pRegister.add(correoTF);

        JLabel passwordNew = new JLabel("Ingrese su contraseña", JLabel.LEFT);
        passwordNew.setBounds(215,370,200,40);
        passwordNew.setFont(new Font("Arial", Font.BOLD, 14));
        passwordNew.setForeground(new Color(67, 42, 42));
        passwordNew.setVisible(true);
        pRegister.add(passwordNew);

        JPasswordField passwordNewTF = new JPasswordField("");
        passwordNewTF.setBounds(215,400,200,40);
        passwordNewTF.setVisible(true);
        passwordNewTF.getBorder();
        pRegister.add(passwordNewTF);

        JLabel passwordNew2 = new JLabel("Confirma tu contraseña", JLabel.LEFT);
        passwordNew2.setBounds(215,430,200,40);
        passwordNew2.setFont(new Font("Arial", Font.BOLD, 14));
        passwordNew2.setForeground(new Color(67, 42, 42));
        passwordNew2.setVisible(true);
        pRegister.add(passwordNew2);

        JPasswordField passwordNew2TF = new JPasswordField("");
        passwordNew2TF.setBounds(215,460,200,40);
        passwordNew2TF.setVisible(true);
        passwordNew2TF.getBorder();
        pRegister.add(passwordNew2TF);

        JButton cancelar = new JButton("Cancelar");
        cancelar.setBounds(100,600,200,40);
        cancelar.setVisible(true);
        pRegister.add(cancelar);

        JButton registrar = new JButton("Registrar");
        registrar.setBounds(300,600,200,40);
        registrar.setVisible(true);
        pRegister.add(registrar);

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
        pComoRegistrar.setSize(600,700);
        pComoRegistrar.setLocation(50,50);
        pComoRegistrar.setBackground(Color.magenta);
        pComoRegistrar.setLayout(null);

            JLabel comoCrearUserLabel = new JLabel("   ¿Como crear usuario?", JLabel.CENTER);
            comoCrearUserLabel.setFont(new Font("Arial",Font.BOLD, 24));
            comoCrearUserLabel.setSize(500,200);
            comoCrearUserLabel.setLocation(10,0);
            pComoRegistrar.add(comoCrearUserLabel);

            JButton crearUsuarioAhoraBoton = new JButton("Crear un usuario ahora");
            crearUsuarioAhoraBoton.setFont(new Font("Arial",Font.BOLD, 14));
            crearUsuarioAhoraBoton.setLocation(180,390);
            crearUsuarioAhoraBoton.setSize(200,40);
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
                                "\n    2.- Hacer click en el menu desplegado" +
                                "\n"+
                                "\n    3.- Llenar los campos solicitados" +
                                "\n"+
                                "\n    4.- Hacer click en 'Crear usuario'" +
                                "\n"+
                                "\n    8.- Listo, el usuario se ha creado");
            comoCrearTA.setSize(325,300);
            comoCrearTA.setFont(new Font("Arial", Font.BOLD, 16));
            comoCrearTA.setEditable(false);
            comoCrearTA.setLocation(120,150);
            pComoRegistrar.add(comoCrearTA);


        return pComoRegistrar;
    }
}