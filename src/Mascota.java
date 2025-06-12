import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Mascota extends JFrame{
    //atributos referente a la mascota
    private final String nombreMascota;
    private int hambre;
    private int energia;
    private int limpieza;
    private int felicidad;
    private int malasAtenciones;
    private int buenasAtenciones;
    private int nivel;
    private int tiempoSuperiorAl50;
    private JFrame inicio;
    private String ruta_imagen;
    private Login login;

    //atributos referente a la interfaz grafica
    private JButton botonAlimentar;
    private JButton botonEntrenar;
    private JButton botonDuchar;
    private JButton botonDormir;
    private JButton botonPausar;
    private JButton botonRegresar;
    private JProgressBar barraHambre;
    private JProgressBar barraEnergia;
    private JProgressBar barraLimpieza;
    private JProgressBar barraFelicidad;
    private JLabel labelNivel;
    private JLabel labelNombreMascota;
    private Timer nivelTimer;
    private Timer progresoTimer;
    private ReproductorMusica reproductor;
    private InformacionMascota informacionMascota;
    private final JLabel imagenMascota;
    private final Icon iconoDormir;

    private boolean mostrarImagenDormir = false;
    int indiceImagenActual = 0;

    //inicializa los atributos de esta clase
    public Mascota(InformacionMascota informacionMascota, String nombreMascota, Login login) {
        ImageIcon iconoDormir = new ImageIcon(imagenes[3]);
        this.iconoDormir = new ImageIcon(iconoDormir.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
        this.informacionMascota = informacionMascota;
        this.nombreMascota = nombreMascota;
        this.login = login;
        hambre = 100;
        energia = 100;
        limpieza = 100;
        felicidad = 100;
        malasAtenciones = 0;
        buenasAtenciones = 0;
        nivel = 1;
        tiempoSuperiorAl50 = 0;
        nivelTimer = null;
        progresoTimer = null;
        imagenMascota = new JLabel();

        ImageIcon iconoInicial = new ImageIcon(imagenes[2]); //inicializamos una imagen por defecto para la mascota
        Image imagenInicial = iconoInicial.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        imagenMascota.setIcon(new ImageIcon(imagenInicial));

        reproductor = new ReproductorMusica("sonidos/audio_tom.wav"); //inicializar el reproductor
    }

    //crea e inicializa la ventana grafica
    public void iniciar() {
        //creamos las caracteristicas de la ventana
        inicio = new JFrame("Juego de Tamagotchi"); // Asignamos al atributo de clase
        inicio.setSize(450, 450); //tamaño de la ventana
        inicio.setResizable(false); //no permite que la ventana se redimensionar
        inicio.setLocationRelativeTo(null);
        inicio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inicio.setLayout(new BorderLayout());

        //panel general del JFrame
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10)); // margen alrededor del panel

        //botones de las acciones del juego
        botonAlimentar = new JButton("Alimentar");
        botonEntrenar = new JButton("Entrenar");
        botonDuchar = new JButton("Duchar");
        botonDormir = new JButton("Dormir");
        botonPausar = new JButton("Pausar");
        botonRegresar = new JButton("Regresar");

        //listeners para las acciones del juego
        botonAlimentar.addActionListener(new ButtonListener());
        botonEntrenar.addActionListener(new ButtonListener());
        botonDuchar.addActionListener(new ButtonListener());
        botonDormir.addActionListener(new ButtonListener());
        botonPausar.addActionListener(new ButtonListener());
        botonRegresar.addActionListener(new ButtonListener());

        //alineación de los botones
        botonAlimentar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonEntrenar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonDuchar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonDormir.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonPausar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonRegresar.setAlignmentX(Component.CENTER_ALIGNMENT);

        //añadimos estos botones al panel
        panel.add(botonAlimentar);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(botonEntrenar);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(botonDuchar);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(botonDormir);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(botonPausar);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(botonRegresar);

        //nuevo panel para los JProgressBar
        JPanel panelBarras = new JPanel();
        panelBarras.setLayout(new GridLayout(4, 1));

        //inicializamos JProgressBar con la barra en 100% de su capacidad
        barraHambre = new JProgressBar(0, 100);
        barraEnergia = new JProgressBar(0, 100);
        barraLimpieza = new JProgressBar(0, 100);
        barraFelicidad = new JProgressBar(0, 100);

        //se muestra un texto en la barra indicando su valor
        barraHambre.setStringPainted(true);
        barraEnergia.setStringPainted(true);
        barraLimpieza.setStringPainted(true);
        barraFelicidad.setStringPainted(true);

        //añadimos los JProgressBar al panel de barras
        panelBarras.add(barraHambre);
        panelBarras.add(barraEnergia);
        panelBarras.add(barraLimpieza);
        panelBarras.add(barraFelicidad);

        //creacion de panel para almacenar el nivel de la mascota y el nombre
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new GridLayout(2, 1));

        //espaciado al panel
        panelInfo.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 10));

        //etiqueta del nivel de la mascota
        labelNivel = new JLabel("Nivel: " + nivel);
        labelNombreMascota = new JLabel("Mascota: " + informacionMascota.getNombreMascota());

        //añadimos el nivel y el nombre de la mascota al panel de informacion
        panelInfo.add(labelNivel);
        panelInfo.add(labelNombreMascota);

        actualizacionBarras(); //metodo para la actualizacion de los JProgressBar

        // Panel para la imagen de la mascota
        JPanel panelImagen = new JPanel();
        panelImagen.setLayout(new FlowLayout());
        imagenMascota.setPreferredSize(new Dimension(250, 250));
        panelImagen.add(imagenMascota); //añadimos la imagen de la mascota a un panel dedicado a este
        panelImagen.setBorder(BorderFactory.createEmptyBorder(10, 30, 0, 0));
        panelImagen.repaint();
        panelImagen.revalidate();

        //repintamos y revalidamos la imagen
        imagenMascota.repaint();
        imagenMascota.revalidate();

        //añadimos todos los paneles al JFrame con la orientacion de ubicaciones
        inicio.add(panel, BorderLayout.CENTER);
        inicio.add(panelBarras, BorderLayout.NORTH);
        inicio.add(panelInfo, BorderLayout.SOUTH);
        inicio.add(panelImagen, BorderLayout.WEST);

        inicio.setVisible(true); //damos visibilidad al JFrame

        // Actualizar las barras con un timer baja la barra cada 15s
        progresoTimer = new Timer(15000, e -> bajarBarras());
        progresoTimer.start();
    }

    @Override
    public String toString() {
        return informacionMascota.getNombreMascota();
    }

    // arreglo de string que almacena la ruta de las imagenes
    private String[] imagenes = {
            "imagenes/mimitchi.jpg",    //0 perro juguetón
            "imagenes/mimitchi_cry.png", //1 perro sucio
            "imagenes/mimitchi_happy.png", //2 perro contento
            "imagenes/mimitchi_sleep.png", //3 perro dormir
    };

    // Metodo para actualizar la imagen de la mascota por el progreso
    public void actualizarImagen() {
        if (energia >= 100) {
            ruta_imagen = imagenes[3];  // Imagen de dormir (mimitchi_sleep.png)
        } else if (felicidad <= 30 || hambre <= 50 || limpieza <= 50) {
            ruta_imagen = imagenes[1];  // Imagen de felicidad baja (mimitchi_cry.png)
        } else if (felicidad >= 70) {
            ruta_imagen= imagenes[2];  // Imagen de felicidad alta (mimitchi_happy.png)
        } else {
            ruta_imagen= imagenes[0];  // Imagen por defecto (mimitchi.jpg)
        }

        ImageIcon icono = new ImageIcon(ruta_imagen); //tipo de clase para cargar las imagenes en Java Swing
        Image imagen = icono.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH); //obtenemos la imagen
        SwingUtilities.invokeLater(() -> {
            imagenMascota.setIcon(new ImageIcon(imagen)); //creamos la nueva imagen redimensionada

            imagenMascota.repaint();
            imagenMascota.revalidate();
        });
    }

    //mostrar la imagen cada vez que se presiona un boton con una accion, alimentar, duchar, dormir, y jugar
    public void mostrarImagenAccion(String rutaImagenAccion) {
        ImageIcon icono = new ImageIcon(rutaImagenAccion); //creamos un objeto de tipo ImageIcon para cargar las imagenes en Java Swing
        Image imagen = icono.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        SwingUtilities.invokeLater(() -> { //asegura que los cambios en el componente swing ocurran en el hilo correcto
            imagenMascota.setIcon(new ImageIcon(imagen)); //creamos la nueva imagen
            imagenMascota.repaint(); //repintamos el componente swing
            imagenMascota.revalidate(); //revalidamos el componente swing
        });
    }

    //metodo para la actualizacion de las barras
    private void actualizacionBarras() {
        //se establecen las diferentes acciones ante las atenciones del jugador

        //si la barra ya ha sido creada
        if (barraHambre != null) {
            barraHambre.setValue(hambre); //establece el valor actual de hambre
            barraHambre.setString("Hambre: " + hambre); //muestra el texto de hambre en la barra
            if (hambre <= 30) { //si hambre es menor o igual a 30 la barra cambia a color rojo
                barraHambre.setForeground(Color.RED);
            } else {
                barraHambre.setForeground(Color.BLACK); //color por defecto
            }
        }

        //si la barra ya ha sido creada
        if (barraEnergia != null) {
            barraEnergia.setValue(energia); //establece el valor actual de energia
            barraEnergia.setString("Energía: " + energia); //muestra el texto de energia en la barra
            if (energia <= 30) { //si energia es menor o igual a 30 la barra cambia a color rojo
                barraEnergia.setForeground(Color.RED);
            } else {
                barraEnergia.setForeground(Color.BLACK); //color por defecto
            }
        }

        //si la barra ya ha sido creada
        if (barraLimpieza != null) {
            barraLimpieza.setValue(limpieza); //establece el valor actual de limpieza
            barraLimpieza.setString("Limpieza: " + limpieza); //muestra el texto de limpieza en la barra
            if (limpieza <= 30) { //si limpieza es menor o igual a 30 la barra cambia a color rojo
                barraLimpieza.setForeground(Color.RED);
            } else {
                barraLimpieza.setForeground(Color.BLACK); //color por defecto
            }
        }

        //si la barra ya ha sido creada
        if (barraFelicidad != null) {
            barraFelicidad.setValue(felicidad); //establece el valor actual de felicidad
            barraFelicidad.setString("Felicidad: " + felicidad); //muestra el texto de limpieza en la barra
            if (felicidad <= 30) { //si la limpieza es menor o igual a 30 la barra cambia a color rojo
                barraFelicidad.setForeground(Color.BLUE);
            } else {
                barraFelicidad.setForeground(Color.BLACK); //color por defecto
            }
        }

        indiceImagenActual = 1; //mascota en estado 1
        actualizarImagen(); //llamada al metodo actualizar imagen
        verificarAtencion(); //verificamos la atencion del usuario
    }

    private void alimentar() {
        hambre += 10; //aumentamos el valor de hambre en 10, es decir menos hambre
        if (hambre > 100) { //si ya hasta al maximo no suma mas
            hambre = 100;
        }
        energia += 10; //aumentamos la energia en 3 puntos, y si ya hasta al maximo, no aumenta mas
        if (energia > 100) {
            energia = 100;
        }
        actualizacionBarras(); //actualizamos las barras de la interfaz
        guardarInformacionMascota(); //guardamos el progreso de la mascota
    }

    private void jugar() {
        energia -= 20; //al jugar la mascota pierde 20 puntos de energia
        if (energia < 0) { //asegura el limite de que la energia no baje de 0
            energia = 0;
        }
        limpieza -= 10; //al jugar la mascota pierde 10 puntos de limpieza
        if (limpieza < 0) { //asegura el limite de que la limpieza no baje de 0
            limpieza = 0;
        }
        felicidad += 10; //al jugar la mascota gana 10 puntos de felicidad
        if (felicidad > 100) { //asegura el limite de que la felicidad no suba de 100, es el limite
            felicidad = 100;
        }

        actualizacionBarras(); //actualizamos las barras
        guardarInformacionMascota(); //guardamos el progreso de la mascota
    }

    private void duchar() {
        limpieza += 20; //al duchar la mascota gana 15 puntos de limpieza
        if (limpieza > 100) { //asegura el limite de que la limpieza no suba de 100
            limpieza = 100;
        }
        felicidad -= 5; //baja 5 puntos de felicidad cuando la mascota se ducha
        if (felicidad < 0) { //asegura el limite de que la felicidad no sea negativa
            felicidad = 0;
        }
        actualizacionBarras(); //actualizamos las barras
        guardarInformacionMascota(); //guardamos el progreso de la mascota
    }

    private void dormir() {
        energia += 20; //al dormir la mascota, gana 20 puntos de energia
        if (energia > 100) { //asegura el limite de que la energia no suba de 100
            energia = 100;
        }
        felicidad += 10; //sube 10 puntos de felicidad al dormir y el limite de felicidad no pase de 100
        if (felicidad > 100) {
            felicidad = 100;
        }
        hambre -= 10; //al dormir el hambre aumenta, disminuye 10 puntos de hambre
        if (hambre < 0) { //asegura el limite de que el hambre no baje de 0
            hambre = 0;
        }
        mostrarImagenDormir = true; // Mostrar la imagen de dormir
        actualizarImagen(); // Actualizar la imagen
        actualizacionBarras(); //actualizacion de barras
        guardarInformacionMascota(); //guardamos el progreso de la mascota

        Timer dormirTimer = new Timer(7000, e -> {
            mostrarImagenDormir = false; // Ocultar la imagen de dormir
            actualizarImagen(); // Actualizar la imagen
            actualizacionBarras(); //actualizacion de barras
        });
        dormirTimer.setRepeats(false); // Para que el Timer se ejecute solo una vez después de 5 segundos
        dormirTimer.start(); //empezamos el timer
    }

    private void regresar() {
        int opcion = JOptionPane.showConfirmDialog(null, "¿Deseas regresar al menú principal?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            if (reproductor != null) {
                reproductor.pausarMusica(); // pausa si está sonando
            }

            if (inicio != null) {
                inicio.dispose(); // Cierra la ventana actual
            }

            //me sirve para que el progreso de la mascota no se ejecute cuando no estoy jugando con esa mascota
            if (progresoTimer != null) {
                progresoTimer.stop();
            }
            if (nivelTimer != null) {
                nivelTimer.stop();
            }
            guardarInformacionMascota();
            login.start(); // Vuelve a mostrar el login
        }
    }

    //metodo que sirve para pausar la musica del reproductor
    private void pausar(){
        if (reproductor != null) {
            reproductor.toggleMusica(); //pausa

            // Cambiar el texto según el estado
            if (botonPausar.getText().contains("⏸")) {
                botonPausar.setText("▶ Música");
            } else {
                botonPausar.setText("⏸ Música");
            }
        }
    }

    //metodos del timer para bajar las barras después de los 10s de inactividad por el jugador
    private void bajarBarras() {
        hambre -= 10; //aumenta el hambre de la mascota en 10 puntos
        if (hambre < 0) {
            hambre = 0;
        }
        energia -= 10; //disminuye la energia en 10 puntos
        if (energia < 0) {
            energia = 0;
        }
        limpieza -= 10; //disminuye la limpieza en 10 puntos
        if (limpieza < 0) {
            limpieza = 0;
        }
        felicidad -= 10; //disminuye la felicidad en 10 puntos
        if (felicidad < 0) {
            felicidad = 0;
        }
        actualizacionBarras(); //actualizamos las barras

        //verifica si la mascota esta en buenas condiciones entre 50 y 100 puntos
        if (hambre >= 50 && hambre <= 100 && energia >= 50 && energia <= 100 && limpieza >= 50 && limpieza <= 100 && felicidad >= 50 && felicidad <= 100) {
            tiempoSuperiorAl50 += 20000; //se suman 20 segundos al contador
            subirNivel(); //aumenta el nivel de la mascota
        } else {
            tiempoSuperiorAl50 = 0; //si la mascota no esta en buenas condiciones, no aumenta el nivel
        }
        guardarInformacionMascota(); //guardamos el progreso
    }

    //metodo para subir el nivel con el timer si las barras se mantienen en 50 o mas de 50
    private void subirNivel() {
        if (tiempoSuperiorAl50 >= 80000) { //si el tiempo de las barras en buenos niveles es mayor o igual de 80 segundos
            nivel++; //aumenta el nivel de la mascota en 1
            labelNivel.setText("Nivel: " + nivel); //actualizamos la etiqueta del JLabel

            //mensaje sobre aviso al subir de nivel la mascota
            JOptionPane.showMessageDialog(null, "¡Felicidades! Tu mascota ha subido de nivel.", "Subir de nivel", JOptionPane.INFORMATION_MESSAGE);

            actualizacionBarras(); //actualizamos las barras

            // Reiniciar el tiempo después de subir de nivel
            tiempoSuperiorAl50 = 0;

            //temporizador de 25 segundos donde el nivel no puede ser aumentado
            nivelTimer = new Timer(25000, e -> {
                nivelTimer.stop(); //se detiene el mismo
                nivelTimer = null;
            });

            nivelTimer.start(); //iniciamos el tiempo de 25 segundos
            guardarInformacionMascota(); //guardamos el nuevo nivel
        }
    }

    //metodo para verificar la atencion para saber si sube de nivel o muere
    private void verificarAtencion() {
        //si la mascota esta mal
        if (hambre == 0 || energia == 0 || limpieza == 0 || felicidad == 0) {
            malasAtenciones++; //aumenta en 1 las malas atenciones
            if (malasAtenciones >= 4) { //si las malas atenciones son mayores o iguales a 4 la mascota muere
                JOptionPane.showMessageDialog(null, "Tu mascota ha muerto por falta de atención. Game over.");
                eliminarTamagotchi(); //metodo para eliminar la mascota
                System.exit(0);
            }
            //si la mascota esta bien
        } else if (hambre >= 100 && energia >= 100 && limpieza >= 100 && felicidad >= 100) {
            buenasAtenciones++; //aumenta en 1 las buenas atenciones
            if (buenasAtenciones >= 3) {//si las buenas atenciones son a mayores o iguales a 3 aumenta el nivel de la mascota
                subirNivel();
            }
        } else { //si la mascota no esta ni bien ni mal, las malas y buenas atenciones son igual a 0
            malasAtenciones = 0;
            buenasAtenciones = 0;
        }
        guardarInformacionMascota(); //guardamos el progreso de la mascota
    }

    //obtener y guardar informacion de la mascota
    private void guardarInformacionMascota() {
        //guardamos todos los atributos de la mascota
        informacionMascota.setHambre(hambre);
        informacionMascota.setEnergia(energia);
        informacionMascota.setLimpieza(limpieza);
        informacionMascota.setFelicidad(felicidad);
        informacionMascota.setMalasAtenciones(malasAtenciones);
        informacionMascota.setBuenasAtenciones(buenasAtenciones);
        informacionMascota.setNivel(nivel);

        //cargamos al ArrayList la informacion del archivo binario
        List<InformacionMascota> mascotasGuardadas = InformacionMascota.cargarMascotas();

        //se elimina del ArrayList mascotas con el mismo nombre
        mascotasGuardadas.removeIf(mascota -> mascota.getNombreMascota().equals(informacionMascota.getNombreMascota()));
        mascotasGuardadas.add(informacionMascota);
        InformacionMascota.guardarMascotas(mascotasGuardadas); //guardamos las mascotas
    }

    // Modificar la información de la mascota
    public void setInformacionMascota(InformacionMascota informacionMascota) {
        this.informacionMascota = informacionMascota;

        //Actualizar los valores de las barras de estado y otros componentes
        hambre = informacionMascota.getHambre();
        energia = informacionMascota.getEnergia();
        limpieza = informacionMascota.getLimpieza();
        felicidad = informacionMascota.getFelicidad();
        nivel = informacionMascota.getNivel();
        actualizacionBarras(); // Actualiza las barras de progreso
        actualizarImagen(); // Actualiza la imagen de la mascota, si es necesario
    }

    //eliminar tamagotchi cuando muere, lo eliminamos del archivo y el ArrayList
    private void eliminarTamagotchi() {
        List<InformacionMascota> mascotasGuardadas = InformacionMascota.cargarMascotas();
        mascotasGuardadas.removeIf(mascota -> mascota.getNombreMascota().equals(informacionMascota.getNombreMascota()));
        InformacionMascota.guardarMascotas(mascotasGuardadas);

        // 2. Eliminar del JComboBox en Login
        JComboBox<InformacionMascota> combo = Login.getComboBox();
        if (combo != null) {
            combo.removeItem(informacionMascota);
        }
    }

    //ActionListener de los botones
    public class ButtonListener implements ActionListener {

        //devuelve las funcionalidades que generan los eventos de los botones
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == botonAlimentar) {
                alimentar();
                mostrarImagenAccion("imagenes/mimitchi_eat.png");
            } else if (e.getSource() == botonEntrenar) {
                jugar();
                mostrarImagenAccion("imagenes/mimitchi.png");
            } else if (e.getSource() == botonDuchar) {
                duchar();
                mostrarImagenAccion("imagenes/mimitchi_shower.png");
            } else if (e.getSource() == botonDormir) {
                dormir();
                mostrarImagenAccion("imagenes/mimitchi_sleep.png");
            } else if (e.getSource() == botonRegresar){
                regresar();
            } else if (e.getSource() == botonPausar) {
                pausar();
            }
        }
    }

}
