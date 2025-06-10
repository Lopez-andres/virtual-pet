import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
public class Mascota implements Runnable {
   //atributos referente a la mascota
    private String nombreMascota;
    private int hambre;
    private int energia;
    private int limpieza;
    private int felicidad;
    private int malasAtenciones;
    private int buenasAtenciones;
    private int nivel;
    private int tiempoSuperiorAl50;

    //atributos referente a la interfaz grafica
    private JButton botonAlimentar;
    private JButton botonJugar;
    private JButton botonBañar;
    private JButton botonDormir;
    private JProgressBar barraHambre;
    private JProgressBar barraEnergia;
    private JProgressBar barraLimpieza;
    private JProgressBar barraFelicidad;
    private JLabel labelNivel;
    private JLabel labelNombreMascota;
    private Timer nivelTimer;
    private InformacionMascota informacionMascota;
    private JLabel imagenMascota;
    private Icon iconoDormir;
    private boolean mostrarImagenDormir = false;
    int indiceImagenActual = 0;

    //inicializa los atributos de esta clase
    public Mascota(InformacionMascota informacionMascota, String nombreMascota) {
        ImageIcon iconoDormir = new ImageIcon(imagenes[3]);
        this.iconoDormir = new ImageIcon(iconoDormir.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
        this.informacionMascota = informacionMascota;
        this.nombreMascota = nombreMascota;
        hambre = 100;
        energia = 100;
        limpieza = 100;
        felicidad = 100;
        malasAtenciones = 0;
        buenasAtenciones = 0;
        nivel = 1;
        tiempoSuperiorAl50 = 0;
        nivelTimer = null;
        imagenMascota = new JLabel();
    }

    public void start() {
        JFrame inicio = new JFrame("Juego de Tamagotchi");
        inicio.setSize(450, 450); //tamaño de la ventana
        inicio.setResizable(false); //no permite que la ventana se redimensionte
        inicio.setLocationRelativeTo(null);
        inicio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inicio.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        botonAlimentar = new JButton("Alimentar");
        botonJugar = new JButton("Entrenar");
        botonBañar = new JButton("Duchar");
        botonDormir = new JButton("Dormir");

        botonAlimentar.addActionListener(new ButtonListener());
        botonJugar.addActionListener(new ButtonListener());
        botonBañar.addActionListener(new ButtonListener());
        botonDormir.addActionListener(new ButtonListener());

        panel.add(botonAlimentar);
        panel.add(botonJugar);
        panel.add(botonBañar);
        panel.add(botonDormir);

        JPanel panelBarras = new JPanel();
        panelBarras.setLayout(new GridLayout(4, 1));

        barraHambre = new JProgressBar(0, 100);
        barraEnergia = new JProgressBar(0, 100);
        barraLimpieza = new JProgressBar(0, 100);
        barraFelicidad = new JProgressBar(0, 100);

        barraHambre.setStringPainted(true);
        barraEnergia.setStringPainted(true);
        barraLimpieza.setStringPainted(true);
        barraFelicidad.setStringPainted(true);

        panelBarras.add(barraHambre);
        panelBarras.add(barraEnergia);
        panelBarras.add(barraLimpieza);
        panelBarras.add(barraFelicidad);

        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new GridLayout(2, 1));

        labelNivel = new JLabel("Nivel: " + nivel);
        labelNombreMascota = new JLabel("Mascota: " + informacionMascota.getNombreMascota());

        panelInfo.add(labelNivel);
        panelInfo.add(labelNombreMascota);

        updateBars();

        // Panel para la imagen de la mascota
        JPanel panelImagen = new JPanel();
        panelImagen.setLayout(new FlowLayout());
        panelImagen.add(imagenMascota);

        inicio.add(panel, BorderLayout.CENTER);
        inicio.add(panelBarras, BorderLayout.NORTH);
        inicio.add(panelInfo, BorderLayout.SOUTH);
        inicio.add(panelImagen, BorderLayout.WEST);

        inicio.setVisible(true);

        // Actualizar las barras con un timer baja la barra cada 15s
        Timer timer = new Timer(15000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bajarBarras();
            }
        });
        timer.start();
    }

    @Override
    public String toString() {
        return informacionMascota.getNombreMascota();
    }
//--------------------------------------------arreglo para las imagenes-----------------------------------------------//
    String[] imagenes = {
            "imagenes/mimitchi.jpg",
            "imagenes/mimitchi_cry.png",
            "imagenes/mimitchi_happy.png",
            "imagenes/mimitchi_sleep.png",
    };


    // Método para actualizar la imagen de la mascota
    public void actualizarImagen() {
        String rutaImagen;

        if (energia >= 100) {
            rutaImagen = imagenes[3];  // Imagen de dormir (mimitchi_sleep.png)
        } else if (felicidad <= 30 || hambre <= 50 || limpieza <= 50) {
            rutaImagen = imagenes[1];  // Imagen de felicidad baja (mimitchi_cry.png)
        } else if (felicidad >= 70) {
            rutaImagen = imagenes[2];  // Imagen de felicidad alta (mimitchi_happy.png)
        } else {
            rutaImagen = imagenes[0];  // Imagen por defecto (mimitchi.jpg)
        }

        ImageIcon icono = new ImageIcon(rutaImagen);
        Image imagen = icono.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        imagenMascota.setIcon(new ImageIcon(imagen));
    }
//--------------------------------------------------------------------------------------------------------------------//
//------------------------------------metodo para actualizar las barras-----------------------------------------------//
    private void updateBars() {
        if (barraHambre != null) {
            barraHambre.setValue(hambre);
            barraHambre.setString("Hambre: " + hambre);
            if (hambre <= 30) {
                barraHambre.setForeground(Color.RED);
            } else {
                barraHambre.setForeground(null);
            }
        }

        if (barraEnergia != null) {
            barraEnergia.setValue(energia);
            barraEnergia.setString("Energía: " + energia);
            if (energia <= 30) {
                barraEnergia.setForeground(Color.RED);
            } else {
                barraEnergia.setForeground(null);
            }
        }

        if (barraLimpieza != null) {
            barraLimpieza.setValue(limpieza);
            barraLimpieza.setString("Limpieza: " + limpieza);
            if (limpieza <= 30) {
                barraLimpieza.setForeground(Color.RED);
            } else {
                barraLimpieza.setForeground(null);
            }
        }

        if (barraFelicidad != null) {
            barraFelicidad.setValue(felicidad);
            barraFelicidad.setString("Felicidad: " + felicidad);
            if (felicidad <= 30) {
                barraFelicidad.setForeground(Color.RED);
            } else {
                barraFelicidad.setForeground(null);
            }
        }
        indiceImagenActual = 1;
        actualizarImagen();
        verificarAtencion();
    }

//-----------------------------metodos para los botones y se actualice las barras-------------------------------------//

    private void alimentar() {
        hambre += 10;
        if (hambre > 100) {
            hambre = 100;
        }
        energia += 3;
        if (energia > 100) {
            energia = 100;
        }

        dormir();
        updateBars();
        guardarInformacionTamagotchi();
    }


    private void jugar() {
        energia -= 20;
        if (energia < 0) {
            energia = 0;
        }
        limpieza -= 10;
        if (limpieza < 0) {
            limpieza = 0;
        }
        felicidad += 10;
        if (felicidad > 100) {
            felicidad = 100;
        }
        updateBars();
        guardarInformacionTamagotchi();
    }

    private void bañar() {
        limpieza += 10;
        if (limpieza > 100) {
            limpieza = 100;
        }
        felicidad -= 1;
        if (felicidad < 0) {
            felicidad = 0;
        }
        updateBars();
        guardarInformacionTamagotchi();
    }

    private void dormir() {
        energia += 20;
        if (energia > 100) {
            energia = 100;
        }
        felicidad += 10;
        if (felicidad > 100) {
            felicidad = 100;
        }
        mostrarImagenDormir = true; // Mostrar la imagen de dormir
        actualizarImagen(); // Actualizar la imagen
        updateBars();
        guardarInformacionTamagotchi();

        Timer dormirTimer = new Timer(2000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarImagenDormir = false; // Ocultar la imagen de dormir
                actualizarImagen(); // Actualizar la imagen
                updateBars();
            }
        });
        dormirTimer.setRepeats(false); // Para que el Timer se ejecute solo una vez después de 2 segundos
        dormirTimer.start();
    }
//--------------------------------------------------------------------------------------------------------------------//
//---------------------------metodos del timer para bajar las barras despues de los 15s-------------------------------//
    private void bajarBarras() {
        hambre -= 10;
        if (hambre < 0) {
            hambre = 0;
        }
        energia -= 10;
        if (energia < 0) {
            energia = 0;
        }
        limpieza -= 10;
        if (limpieza < 0) {
            limpieza = 0;
        }
        felicidad -= 10;
        if (felicidad < 0) {
            felicidad = 0;
        }
        updateBars();

        if (hambre >= 50 && hambre <= 100 && energia >= 50 && energia <= 100 && limpieza >= 50 && limpieza <= 100 && felicidad >= 50 && felicidad <= 100) {
            tiempoSuperiorAl50 += 15000;
            subirNivel();
        } else {
            tiempoSuperiorAl50 = 0;
        }
        guardarInformacionTamagotchi();
    }
//--------------------------------------------------------------------------------------------------------------------//
//----------------metodo para subir el nivel con el timer si las barras se mantienen en 50 o mas de 50----------------//
    private void subirNivel() {
        if (tiempoSuperiorAl50 >= 80000) {
            nivel++;
            labelNivel.setText("Nivel: " + nivel);
            JOptionPane.showMessageDialog(null, "¡Felicidades! Tu mascota ha subido de nivel.", "Subir de nivel", JOptionPane.INFORMATION_MESSAGE);

            updateBars();

            nivelTimer = new Timer(20000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    nivelTimer.stop();
                    nivelTimer = null;
                }
            });
            nivelTimer.start();
            guardarInformacionTamagotchi();
        }
    }
//--------------------------------------------------------------------------------------------------------------------//
//---------------metodo para verificar la atencion para saber si sube de nivel o muere--------------------------------//
    private void verificarAtencion() {
        if (hambre == 0 || energia == 0 || limpieza == 0 || felicidad == 0) {
            malasAtenciones++;
            if (malasAtenciones >= 3) {
                JOptionPane.showMessageDialog(null, "Tu mascota ha muerto por falta de atención. Game over.");
                eliminarTamagotchi();
                System.exit(0);
            }
        } else if (hambre >= 100 && energia >= 100 && limpieza >= 100 && felicidad >= 100) {
            buenasAtenciones++;
            if (buenasAtenciones >= 3) {
                subirNivel();
            }
        } else {
            malasAtenciones = 0;
            buenasAtenciones = 0;
        }
        guardarInformacionTamagotchi();
    }
//--------------------------------------------------------------------------------------------------------------------//
//-------------------------------------actionlistener de los botones--------------------------------------------------//
    public class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == botonAlimentar) {
                alimentar();
            } else if (e.getSource() == botonJugar) {
                jugar();
            } else if (e.getSource() == botonBañar) {
                bañar();
            } else if (e.getSource() == botonDormir) {
                dormir();
            }
        }
    }
//--------------------------------------------------------------------------------------------------------------------//
//----------------------------obtener y guardar informacion del tamagotchi--------------------------------------------//
    private void guardarInformacionTamagotchi() {
        informacionMascota.setHambre(hambre);
        informacionMascota.setEnergia(energia);
        informacionMascota.setLimpieza(limpieza);
        informacionMascota.setFelicidad(felicidad);
        informacionMascota.setMalasAtenciones(malasAtenciones);
        informacionMascota.setBuenasAtenciones(buenasAtenciones);
        informacionMascota.setNivel(nivel);

        List<InformacionMascota> mascotasGuardadas = InformacionMascota.cargarMascotas();
        mascotasGuardadas.removeIf(mascota -> mascota.getNombreMascota().equals(informacionMascota.getNombreMascota()));
        mascotasGuardadas.add(informacionMascota);
        InformacionMascota.guardarMascotas(mascotasGuardadas);

    }

    // Obtener la información de la mascota
    public void setInformacionMascota(InformacionMascota informacionMascota) {
        this.informacionMascota = informacionMascota;

        // Actualizar los valores de las barras de estado y otros componentes
        hambre = informacionMascota.getHambre();
        energia = informacionMascota.getEnergia();
        limpieza = informacionMascota.getLimpieza();
        felicidad = informacionMascota.getFelicidad();
        nivel = informacionMascota.getNivel();
        updateBars(); // Actualiza las barras de progreso
        actualizarImagen(); // Actualiza la imagen de la mascota, si es necesario
        // Resto del código de actualización de otros componentes
    }
//--------------------------------------------------------------------------------------------------------------------//
//--------------------------------------eliminar tamagotchi cuando muere----------------------------------------------//
    private void eliminarTamagotchi() {
        List<InformacionMascota> mascotasGuardadas = InformacionMascota.cargarMascotas();
        mascotasGuardadas.removeIf(mascota -> mascota.getNombreMascota().equals(informacionMascota.getNombreMascota()));
        InformacionMascota.guardarMascotas(mascotasGuardadas);
    }


    @Override
    public void run() {
        start();
    }
}
