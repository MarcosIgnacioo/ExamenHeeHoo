import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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

    private String bienvenidoNombre = "";
    private static String usuarioInfo [] = new String[4];

    static void modifyFile(String filePath, String oldString, String newString)
    {
        File fileToBeModified = new File(filePath);

        String oldContent = "";

        BufferedReader reader = null;

        FileWriter writer = null;

        try
        {
            reader = new BufferedReader(new FileReader(fileToBeModified));

            //Reading all the lines of input text file into oldContent

            String line;

            while ((line = reader.readLine()) != null)
            {
                oldContent = oldContent + line.split(",");
            }

            //Replacing oldString with newString in the oldContent

            String newContent = oldContent.replaceAll(oldString, newString);

            //Rewriting the input text file with newContent

            writer = new FileWriter(fileToBeModified);

            writer.write(newContent);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                //Closing the resources

                reader.close();

                writer.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
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
    public ExamenVentana(){
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
            System.out.println("yepa");
            panel = modificarCuenta();
            this.add(panel);

            this.repaint();
            this.revalidate();
        }
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

        JLabel bienvenido = new JLabel("Hola " + bienvenidoNombre); // ver como actualizar el nombre cuando se cambia el usuario
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

        JMenu ayuda= new JMenu("Ayuda");
            JMenuItem comoCrear = new JMenuItem("Como crear usuarios?");
        ayuda.add(comoCrear);


        barraMenu.add(cuenta);
        barraMenu.add(usuarios);
        barraMenu.add(ayuda);
        this.setJMenuBar(barraMenu);
        cerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setJMenuBar(null);
                anterior = actual;
                actual = "logIn";
                limpiarVentana();
            }
        });
        miCuenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                anterior = actual;
                actual = "ModificarCuenta";
                limpiarVentana();
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

        JTextField cambiarPasswordTF = new JTextField();
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
                String bubble = anterior;
                System.out.println(anterior);
                anterior = actual;
                actual = bubble;
                limpiarVentana();
            }
        });

        actualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i<usuarioInfo.length; i++){
                    System.out.println(usuarioInfo[i]);
                }

                modifyFile("src/users.txt", usuarioInfo[0], cambiarNombreTF.getText());
                modifyFile("src/users.txt", usuarioInfo[1], cambiarApellidosTF.getText());
                modifyFile("src/users.txt", usuarioInfo[2], cambiarCorreoTF.getText());
                modifyFile("src/users.txt", usuarioInfo[3], cambiarPasswordTF.getText());
                for (int i = 0; i<usuarioInfo.length; i++){
                    System.out.println(usuarioInfo[i]);
                }
            }
        });


        return  pChangeData;
    }
}