import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InformacionMascota implements Serializable {
    //atributos de la clase Mascota
    private static final long serialVersionUID = 1L;
    private String nombreMascota;
    private int hambre;
    private int energia;
    private int limpieza;
    private int felicidad;
    private int malasAtenciones;
    private int buenasAtenciones;
    private int nivel;
    private final List<InformacionMascota> listaMascotas;
    private static final String fileName = "mascotas.bin";

    //inicializo los atributos en el constructor y la lista de mascotas
    public InformacionMascota(String nombreMascota, int hambre, int energia, int limpieza
            , int felicidad, int malasAtenciones, int buenasAtenciones, int nivel) {
        this.nombreMascota = nombreMascota;
        this.hambre = hambre;
        this.energia = energia;
        this.limpieza = limpieza;
        this.felicidad = felicidad;
        this.malasAtenciones = malasAtenciones;
        this.buenasAtenciones = buenasAtenciones;
        this.nivel = nivel;
        listaMascotas = new ArrayList<>();
    }

    // getter y setter

    public String getNombreMascota() {
        return nombreMascota;
    }
    public String toString() {
        return nombreMascota;
    }

    public void setNombreMascota(String nombreMascota) {
        this.nombreMascota = nombreMascota;
    }

    public int getHambre() {
        return hambre;
    }

    public void setHambre(int hambre) {
        this.hambre = hambre;
    }

    public int getEnergia() {
        return energia;
    }

    public void setEnergia(int energia) {
        this.energia = energia;
    }

    public int getLimpieza() {
        return limpieza;
    }

    public void setLimpieza(int limpieza) {
        this.limpieza = limpieza;
    }

    public int getFelicidad() {
        return felicidad;
    }

    public void setFelicidad(int felicidad) {
        this.felicidad = felicidad;
    }

    public void setMalasAtenciones(int malasAtenciones) {
        this.malasAtenciones = malasAtenciones;
    }

    public void setBuenasAtenciones(int buenasAtenciones) {
        this.buenasAtenciones = buenasAtenciones;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public static void guardarMascotas(List<InformacionMascota> mascotas) {
        //escribe objetos en un arhivo binario
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(mascotas); // Guardar la nueva lista de mascotas
        } catch (IOException e) {
            e.printStackTrace(); //casting de errores
        }
    }

    public static List<InformacionMascota> cargarMascotas() {
        //crea una lista por defecto si algo sale mal
        List<InformacionMascota> mascotas = new ArrayList<>();

        //lee objetos del archivo binario
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            mascotas = (List<InformacionMascota>) inputStream.readObject(); //lee los datos y los convierte en una lista
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); //casting de errores
        }
        return mascotas; //devuelve la lista cargada
    }
}





