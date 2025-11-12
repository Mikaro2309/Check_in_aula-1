package edu.cerp.checkin.logic;

import edu.cerp.checkin.model.Inscripcion;
import edu.cerp.checkin.persistence.Persistencia;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/** Lógica mínima en memoria (sin validaciones complejas). */
public class SesionService {
    private final List<Inscripcion> inscripciones = new ArrayList<>();

    public SesionService() {
        // Cargar datos existentes al iniciar
        cargarDatosDesdeArchivo();
    }

    public void registrar(String nombre, String documento, String curso) {
        if (nombre == null || nombre.isBlank()) nombre = "(sin nombre)";
        if (documento == null) documento = "";
        if (curso == null || curso.isBlank()) curso = "Prog 1";
        inscripciones.add(new Inscripcion(nombre.trim(), documento.trim(), curso.trim(), LocalDateTime.now()));
        
        // Guardar automáticamente después de cada registro
        guardarDatos();
    }

    public List<Inscripcion> listar() { 
        return Collections.unmodifiableList(inscripciones); 
    }

    public List<Inscripcion> buscar(String q) {
        if (q == null || q.isBlank()) return listar();
        String s = q.toLowerCase();
        return inscripciones.stream()
                .filter(i -> i.getNombre().toLowerCase().contains(s) || i.getDocumento().toLowerCase().contains(s))
                .collect(Collectors.toList());
    }

    public String resumen() {
        Map<String, Long> porCurso = inscripciones.stream()
                .collect(Collectors.groupingBy(Inscripcion::getCurso, LinkedHashMap::new, Collectors.counting()));
        StringBuilder sb = new StringBuilder();
        sb.append("Total: ").append(inscripciones.size()).append("\nPor curso:\n");
        for (var e : porCurso.entrySet()) 
            sb.append(" - ").append(e.getKey()).append(": ").append(e.getValue()).append("\n");
        return sb.toString();
    }

    /** Datos de prueba para arrancar la app. */
    public void cargarDatosDemo() {
        // Solo cargar datos demo si no hay datos previos Y no existe archivo
        if (inscripciones.isEmpty() && !Persistencia.existeArchivo()) {
            registrar("Ana Pérez", "51234567", "Prog 2");
            registrar("Luis Gómez", "49887766", "Prog 1");
            registrar("Camila Díaz", "53422110", "Base de Datos");
            System.out.println("Datos demo cargados (primera ejecución)");
        } else if (!inscripciones.isEmpty()) {
            System.out.println("Datos cargados desde archivo: " + inscripciones.size() + " inscripciones");
        }
    }

    /**
     * Guarda los datos actuales en el archivo.
     * @return true si se guardó correctamente
     */
    public boolean guardarDatos() {
        boolean exito = Persistencia.guardar(inscripciones);
        if (exito) {
            System.out.println("Datos guardados correctamente");
        }
        return exito;
    }

    /**
     * Carga los datos desde el archivo.
     */
    private void cargarDatosDesdeArchivo() {
        List<Inscripcion> cargadas = Persistencia.cargar();
        inscripciones.addAll(cargadas);
        if (!cargadas.isEmpty()) {
            System.out.println("Cargando datos previos...");
        }
    }

    /**
     * Obtiene el total de inscripciones.
     * @return número total de inscripciones
     */
    public int getTotal() {
        return inscripciones.size();
    }
}