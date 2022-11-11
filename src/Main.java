import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        //NO ESTA PUESTO EL NULL NE LOS JAR CREO

        Scanner sc = new Scanner(System.in);
        System.out.println("Dime la frase");
        try{
            ArrayList<String> palabras = cambiarLetra(sc.nextLine());
            String frase = reconstruir(palabras);
            System.out.println(frase);

        } catch (IOException e) {
            System.out.println("Algo se ha ido a la mierda");
        }


    }

    private static String reconstruir(ArrayList<String> palabras) throws IOException {
        Process process = crearProceso("ConstruirS.jar");

        BufferedReader br = escribir(process);

        PrintStream ps = leer(process);

        for (String palabra : palabras ) {
            ps.println(palabra);
            ps.flush();
        }
        ps.println("fin");
        ps.flush();


        return br.readLine();
    }

    private static ArrayList<String> cambiarLetra(String cambiarLetra) throws IOException {

        Process process = crearProceso("LetraCambiadaExamenS.jar");

        BufferedReader br = escribir(process);

        PrintStream ps = leer(process);

        //Inicializamos el ArrayList ahora porque antes no tiene sentido por si falla, para ahorrar.
        ArrayList<String> palabras = new ArrayList<>();

        ps.println(cambiarLetra);


        String palabra;

        while(!(palabra = br.readLine()).equalsIgnoreCase("fin")){
            palabras.add(palabra);
        }

        return palabras;
    }

    private static BufferedReader escribir(Process process) {
        InputStreamReader isr = new InputStreamReader(process.getInputStream());
        BufferedReader br = new BufferedReader(isr);
        return br;
    }

    private static PrintStream leer(Process process) {
        OutputStream os = process.getOutputStream();
        PrintStream ps = new PrintStream(os);
        return ps;
    }

    private static Process crearProceso( String proceso) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("java","-jar", proceso);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        return process;
    }


}