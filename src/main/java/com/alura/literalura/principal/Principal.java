package com.alura.literalura.principal;

import com.alura.literalura.model.*;
import com.alura.literalura.service.AutorService;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;
import com.alura.literalura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Principal {
    @Autowired
    private LibroService libroService;
    @Autowired
    private AutorService autorService;
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();

    public Principal(LibroService libroService, AutorService autorService) {
        this.libroService = libroService;
        this.autorService = autorService;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    |************************************************|
                    |*****       BIENVENIDO A LITERALURA       ******|
                    |************************************************|
                    
                    Elige la opcion a traves de su número
                    
                    1 - Buscar libros por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado ano
                    5 - Listar libros por idioma
                    
                    0 - Salir
                    
                    |************************************************|
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosEnUnDeterminadoAno();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }
    private void buscarLibroPorTitulo() {
        System.out.println("Ingrese el nombre del libro que desea buscar:");
        String busquedaTituloLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + busquedaTituloLibro.replace(" ","+"));
        var busquedaDatos = conversor.obtenerDatos(json, Datos.class);

        Optional<DatosLibro> busquedaLibro = busquedaDatos.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(busquedaTituloLibro.toUpperCase()))
                .findFirst();
        if(busquedaLibro.isPresent()){
            System.out.println("Libro encontrado");
            System.out.println(busquedaLibro.get());

            DatosLibro busquedaDatosLibro = (DatosLibro) busquedaLibro.get();
            List<DatosAutor> busquedaDatosAutor = busquedaDatosLibro.autor();
            Autor autor = new Autor(busquedaDatosAutor.get(0));
            Libro libro = new Libro(busquedaDatosLibro);
            libroService.registrarAutorYLibro(libro, autor);
        }else {
            System.out.println("Libro no encontrado");
        }
    }

    private void listarLibrosRegistrados() {
        List<Libro> librosRegistrados = libroService.listarLibros();
        if (librosRegistrados.isEmpty()) {
            System.out.println("No se encontraron libros registrados");
        } else {
            System.out.println("Lista de Libros Registrados");
            librosRegistrados.forEach(System.out::println);
        }
    }

    private void listarAutoresRegistrados() {
        List<Autor> autoresRegistrados = autorService.listarAutoresConLibros();
        if (autoresRegistrados.isEmpty()) {
            System.out.println("No se encontraron autores registrados");
        } else {
            System.out.println("Lista de Autores Registrados");
            Set<Autor> autoresRegistradosUnicos = new HashSet<>();
            autoresRegistradosUnicos.addAll(autoresRegistrados);
            autoresRegistradosUnicos.forEach(System.out::println);
        }
    }

    private void listarAutoresVivosEnUnDeterminadoAno() {
        System.out.println("Ingrese el año para consultar que autores estan vivos:");
        Integer anoBusqueda = teclado.nextInt();
        teclado.nextLine();
        List<Autor> autoresVivosEnUnDeterminadoAno = autorService.listarAutoresVivosEnAno(anoBusqueda);

        if (autoresVivosEnUnDeterminadoAno.isEmpty()) {
            System.out.println("No se encontraron autores que estuvieran vivos en el año " + anoBusqueda);
        } else {
            System.out.println("Los autores que estaban vivos en el año " + anoBusqueda + " son:");
            Set<Autor> autoresUnicos = new HashSet<>();
            for (Autor autor : autoresVivosEnUnDeterminadoAno) {
                autoresUnicos.add(autor);
            }
            autoresUnicos.stream().forEach(System.out::println);
        }
    }

    private void listarLibrosPorIdioma() {
        var menuIdioma = """
                    |Opción - "es" : Libros en español  |
                    |Opción - "en" : Libros en ingles   |
                    |Opción - "fr" : Libros en frances  |
                    |Opción - "pt" : Libros en portugues|
                    
                    Ingrese el idioma a consultar:
                    """;
        System.out.println(menuIdioma);
        String idiomaBusqueda = teclado.nextLine();
        List<Libro> librosPorIdioma = libroService.listarLibrosPorIdioma(idiomaBusqueda);
        if (librosPorIdioma.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma consultado");
        } else {
            System.out.println("Los libros consultados en " + idiomaBusqueda + " son");
            librosPorIdioma.stream().forEach(System.out::println);
        }
    }

}
