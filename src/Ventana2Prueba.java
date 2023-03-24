import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Ventana2Prueba extends JFrame {
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
    public Ventana2Prueba(){
        // menu inicio
        JMenuBar barraMenu = new JMenuBar();
        barraMenu.setVisible(true);
        JMenu registrarte = new JMenu("Registro");
        JMenu loggearte= new JMenu("Logueos");
        JMenuItem logInItem = new JMenuItem("Inicia sesion");
        JMenuItem signInItem = new JMenuItem("Registra tu cuenta");


        registrarte.add(signInItem);
        loggearte.add(logInItem);

        barraMenu.add(registrarte);
        barraMenu.add(loggearte);
        this.setJMenuBar(barraMenu);
        //panel inicio
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

        JTextField usuarioTF = new JTextField();
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

        JPasswordField passwordTF = new JPasswordField();
        passwordTF.setBounds(200,300,200,40);
        passwordTF.setVisible(true);
        pLogIn.add(passwordTF);

        JButton logIn = new JButton("Acceder");
        logIn.setBounds(200, 370, 150,40);
        logIn.setVisible(true);
        pLogIn.add(logIn);


        JPanel pLoggedIn = new JPanel();
        pLoggedIn.setSize(600,700);
        pLoggedIn.setLocation(50,50);
        pLoggedIn.setBackground(Color.pink);
        pLoggedIn.setLayout(null);

        JLabel bienvenido = new JLabel();
        bienvenido.setSize(400,30);
        bienvenido.setFont(new Font("Arial", Font.BOLD, 24));
        bienvenido.setLocation(0,0);
        bienvenido.setVisible(true);
        pLoggedIn.add(bienvenido);


        JButton mostrarTabla = new JButton("iowo");
        mostrarTabla.setSize(100,100);
        mostrarTabla.setLocation(50,250);
        pLoggedIn.add(mostrarTabla);



        JTable tablaUsuarios = new JTable();
        tablaUsuarios.setSize(400,200);
        tablaUsuarios.setLocation(0,20);
        pLoggedIn.add(tablaUsuarios);

        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);
        scrollPane.setSize(400,200);
        scrollPane.setLocation(0,20);

        mostrarTabla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String columnasNombre [] = {"nombre", "apellidos", "correo", "contraseña"};
                Object filasDatos[][] = new Object[contadorFilas("src/users.txt")][columnasNombre.length];

                Object columnasNombre2 [] = {"nombre", "apellidos", "correo", "contraseña","Button"};
                Object filasDatos2[][] = new Object[0][columnasNombre2.length];

                DefaultTableModel dtm = new DefaultTableModel();
                dtm.setDataVector(new Object[][]{}, // filas
                        new Object[]{"nombre", "apellidos", "correo", "contraseña","Button"});// nombre de columnas columnas

                String cuentas[];
                try{
                    FileReader fileReader = new FileReader("src/users.txt");
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    String linea;
                    int popo = 0;
                    tablaUsuarios.setModel(dtm);
                    while ((linea = bufferedReader.readLine()) != null) {
                        cuentas = linea.split(",");
                        tablaUsuarios.getColumn("Button").setCellRenderer(new ButtonRenderer());
                        tablaUsuarios.getColumn("Button").setCellEditor(new ButtonEditor(new JCheckBox(),tablaUsuarios));
                        popo++;
                        dtm.addRow(cuentas);
                    }
                }
                catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                pLoggedIn.add(scrollPane);
            }
        });

        logIn.addActionListener(new ActionListener() {
            String userName, passwordConf;
            String [] cuentas;
            @Override
            public void actionPerformed(ActionEvent e) {
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

                            entro = 1;
                        }
                    }

                    if (entro == 0){
                        JOptionPane.showMessageDialog(enterPass,"Datos incorrectos","mal:(!", JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        bienvenido.setText("Hola: "+userName);
                        bienvenido.setLocation(pLoggedIn.getWidth()/2, pLoggedIn.getHeight()/2);
                        JOptionPane.showMessageDialog(enterPass,"Ingresando al sistema","Bien!", JOptionPane.INFORMATION_MESSAGE);
                        remove(pLogIn);
                        add(pLoggedIn);
                        repaint();
                        revalidate();
                    }
                    fileReader.close();
                } catch (IOException de) {
                    de.printStackTrace();
                }
            }
        });

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

        JCheckBox cb = new JCheckBox("Aceptar terminos y condiciones");
        cb.setFont(new Font("Arial", Font.CENTER_BASELINE, 10));
        cb.setBounds(215,520,200,60);
        cb.setVisible(true);
        pRegister.add(cb);

        JButton registrar = new JButton("Registrar");
        registrar.setBounds(215,600,200,40);
        registrar.setVisible(true);
        pRegister.add(registrar);
        String p, po;

        registrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pass1, pass2;
                String nombreR = nombreTF.getText();
                String apellidoR = apellidosTF.getText();
                String correoR = correoTF.getText();
                String cuentas [];
                int registroR = 0;
                pass1 = String.valueOf(passwordNewTF.getPassword());
                pass2 = String.valueOf(passwordNew2TF.getPassword());
                //pass2 = new String(passwordNew2TF.getPassword());
                if(pass1.equals(pass2) && !pass1.equals("") && !pass2.equals("")){
                    try {
                        FileReader fileReader = new FileReader("src/users.txt");
                        BufferedReader bufferedReader = new BufferedReader(fileReader);
                        String linea;
                        while ((linea = bufferedReader.readLine()) != null) {
                            cuentas = linea.split(",");
                            if (cuentas[0].equals(nombreR) || cuentas[2].equals(correoR)){
                                registroR = 1;
                            }
                        }
                        if (registroR == 0){
                            try (FileWriter escritorArchivo = new FileWriter("src/users.txt", true);
                                 BufferedWriter escritorBuffer = new BufferedWriter(escritorArchivo);
                                 PrintWriter impresoraEScritora = new PrintWriter(escritorBuffer);)
                            {
                                impresoraEScritora.println(nombreR + "," + apellidoR + "," + correoR + "," + pass1);
                                System.out.println("ola");
                            } catch (IOException i) {
                                i.printStackTrace();
                            }
                            JOptionPane.showMessageDialog(enterPass,"Registro exitoso","BIEN(:!", JOptionPane.INFORMATION_MESSAGE);
                            remove(pRegister);
                            remove(pLoggedIn);
                            passwordTF.setText(pass1);
                            usuarioTF.setText(nombreR);
                            add(pLogIn);
                            repaint();
                            revalidate();
                        }
                        else{
                            JOptionPane.showMessageDialog(enterPass,"El usuario o correo ingresado ya se encuentran registrados","mal:(!", JOptionPane.ERROR_MESSAGE);
                        }
                        fileReader.close();
                    } catch (IOException de) {
                        de.printStackTrace();
                    }


                }
            }
        });
        logInItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(pLoggedIn);
                remove(pRegister);
                passwordTF.setText("");
                usuarioTF.setText("");
                add(pLogIn);
                repaint();
                revalidate();
            }
        });

        signInItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(pLoggedIn);
                remove(pLogIn);
                nombreTF.setText("");
                apellidosTF.setText("");
                correoTF.setText("");
                passwordNewTF.setText("");
                passwordNew2TF.setText("");
                add(pRegister);
                repaint();
                revalidate();
            }
        });

        this.getContentPane().setBackground(Color.ORANGE);
        this.setVisible(true);
        this.setSize(700,900);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}