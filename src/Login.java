import javax.swing.*;
import java.util.List;

/*Esta clase representa el flujo inicial del programa, permitiendo al usuario
crear una nueva mascota virtual o continuar con una ya existente.*/

public class Login {
    public static JComboBox<InformacionMascota> comboBox;
    private Login login = this;

    /*Metodo principal que inicia el proceso de login o creación de mascota.
    Muestra un menú de opciones y reacciona según la elección del usuario.*/

    public void start() {
        //Opciones disponibles para el usuario
        String[] options = {"Crear Nuevo", "Continuar"};

        int eleccion = JOptionPane.showOptionDialog(null, "Bienvenido \n ¿Qué deseas hacer?\nPuedes crear una mascota o continuar con una creada", "Login", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (eleccion == 0) { //si elige crear una nueva mascota
            String nombreMascota = JOptionPane.showInputDialog(null, "Ingrese el nombre de su mascota:");

            if (nombreMascota != null) {
                InformacionMascota informacionTamagotchi = new InformacionMascota(nombreMascota, 100, 100, 100, 100, 0, 0, 1);
                informacionTamagotchi.setNombreMascota(nombreMascota); //modifica e nombre de la mascota y sus atributos
                List<InformacionMascota> mascotasGuardadas = InformacionMascota.cargarMascotas(); //guardamos la mascota

                // Verificar si la mascota ya existe en la lista
                boolean existeMascota = mascotasGuardadas.stream().anyMatch(mascota -> mascota.getNombreMascota().equals(nombreMascota));

                if (!existeMascota) {
                    mascotasGuardadas.add(informacionTamagotchi); // Agregar la nueva mascota a la lista de mascotas
                    InformacionMascota.guardarMascotas(mascotasGuardadas); // Guardar la lista de mascotas actualizada

                    Mascota game = new Mascota(informacionTamagotchi, nombreMascota, this); //crear la ventana del juego
                    SwingUtilities.invokeLater(game::iniciar); //asegura de que el metodo iniciar se ejecute en el hilo de la interfaz grafica
                } else {
                    JOptionPane.showMessageDialog(null, "Ya existe una mascota con ese nombre.");
                    login.start();
                }
            }
        } else if (eleccion == 1) {
            List<InformacionMascota> mascotasGuardadas = InformacionMascota.cargarMascotas(); // Utilizar la clase InformacionTamagotchi para cargar las mascotas
            if (!mascotasGuardadas.isEmpty()) {
                comboBox = new JComboBox<>(mascotasGuardadas.toArray(new InformacionMascota[0])); //cargar las mascotas en el JComboBox
                add(comboBox);
                
                int result = JOptionPane.showConfirmDialog(null, comboBox, "Seleccionar Mascota", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {

                    //recibe la mascota seleccionada del JComboBox
                    InformacionMascota mascotaSeleccionada = (InformacionMascota) comboBox.getSelectedItem();
                    Mascota game = new Mascota(mascotaSeleccionada, mascotaSeleccionada.getNombreMascota(), this); //inicia la ventana del juego

                    game.setInformacionMascota(mascotaSeleccionada); //modificamos la informacion de la mascota con la del archivo binario
                    SwingUtilities.invokeLater(game::iniciar);  //asegura de que el metodo iniciar se ejecute en el hilo de la interfaz grafica
                } else {
                    login.start(); //inicia el login
                }
            } else {
                try{
                    JOptionPane.showMessageDialog(null, "No hay mascotas guardadas.");
                    login.start();
                }catch(NullPointerException e){
                    System.out.println("Error");
                }

            }
        }
    }

    //getter de el JComboBox y el Main, y metodo add para evitar exepcion
    private static void add(JComboBox<InformacionMascota> comboBox) {
    }

    public static JComboBox<InformacionMascota> getComboBox() {
        return comboBox;
    }

    public static void main(String[] args) {
        Login login = new Login();
        login.start();
    }
}
