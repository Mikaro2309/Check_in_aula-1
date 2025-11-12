package edu.cerp.checkin.persistence;

import edu.cerp.checkin.model.Inscripcion;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de la persistencia de datos en archivo.
 * Guarda y carga las inscripciones en formato CSV.
 */
public class Persistencia {
    private static final String ARCHIVO = "inscripciones.csv";
    private static final String SEPARADOR = ";";
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Guarda la lista de inscripciones en un archivo CSV.
     * @param inscripciones Lista de inscripciones a guardar
     * @return true si se guardó correctamente, false en caso de error
     */
    public static boolean guardar(List<Inscripcion> inscripciones) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO))) {
            // Escribir encabezado
            writer.write("Nombre" + SEPARADOR + "Documento" + SEPARADOR + "Curso" + SEPARADOR + "FechaHora");
            writer.newLine();

            // Escribir cada inscripción
            for (Inscripcion insc : inscripciones) {
                String linea = insc.getNombre() + SEPARADOR +
                               insc.getDocumento() + SEPARADOR +
                               insc.getCurso() + SEPARADOR +
                               insc.getFechaHora().format(FORMATO_FECHA);
                writer.write(linea);
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar datos: " + e.getMessage());
            return false;
        }
    }

    /**
     * Carga las inscripciones desde el archivo CSV.
     * @return Lista de inscripciones cargadas (vacía si no existe el archivo o hay error)
     */
    public static List<Inscripcion> cargar() {
        List<Inscripcion> inscripciones = new ArrayList<>();
        File archivo = new File(ARCHIVO);

        // Si no existe el archivo, retornar lista vacía
        if (!archivo.exists()) {
            System.out.println("No se encontró archivo de datos previos. Iniciando con lista vacía.");
            return inscripciones;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            boolean primeraLinea = true;

            while ((linea = reader.readLine()) != null) {
                // Saltar el encabezado
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                // Parsear la línea
                String[] datos = linea.split(SEPARADOR);
                if (datos.length == 4) {
                    String nombre = datos[0];
                    String documento = datos[1];
                    String curso = datos[2];
                    LocalDateTime fechaHora = LocalDateTime.parse(datos[3], FORMATO_FECHA);

                    inscripciones.add(new Inscripcion(nombre, documento, curso, fechaHora));
                }
            }
            System.out.println("Archivo leído correctamente");
        } catch (IOException e) {
            System.err.println("Error al cargar datos: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error al procesar archivo: " + e.getMessage());
        }

        return inscripciones;
    }

    /**
     * Verifica si existe el archivo de datos.
     * @return true si existe, false en caso contrario
     */
    public static boolean existeArchivo() {
        return new File(ARCHIVO).exists();
    }

    /**
     * Elimina el archivo de datos (útil para pruebas o reset).
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public static boolean eliminarArchivo() {
        File archivo = new File(ARCHIVO);
        if (archivo.exists()) {
            return archivo.delete();
        }
        return false;
    }
}