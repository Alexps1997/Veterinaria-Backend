import veterinaria.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hospital hospital = new Hospital();
        String linea;

        while (true) {
            if (!scanner.hasNextLine()) break;
            linea = scanner.nextLine().trim();
            if (linea.equalsIgnoreCase("salir")) {
                break;
            }
            if (linea.isEmpty()) { continue; }

            String[] partes = linea.split("\\s+");
            String comando = partes[0].toLowerCase();

            if (comando.equals("ingresar")) {
                if (partes.length < 2) {
                    System.out.println("ERROR: comando desconocido.");
                    continue;
                }
                String tipoAnimal = partes[1].toLowerCase();
                if (tipoAnimal.equals("perro")) {
                    if (partes.length < 7) {
                        System.out.println("ERROR: datos invalidos");
                    } else {
                        try {
                            String nombre = partes[2];
                            double peso = Double.parseDouble(partes[3]);
                            int edad = Integer.parseInt(partes[4]);
                            String raza = partes[5];
                            String vacunado = partes[6];
                            System.out.println(hospital.ingresarPerro(nombre, peso, edad, raza, vacunado));
                        } catch (NumberFormatException e) {
                            System.out.println("ERROR: datos invalidos");
                        }
                    }
                } else if (tipoAnimal.equals("gato")) {
                    if (partes.length < 5) {
                        System.out.println("ERROR: datos invalidos");
                    } else {
                        try {
                            String nombre = partes[2];
                            double peso = Double.parseDouble(partes[3]);
                            int edad = Integer.parseInt(partes[4]);
                            System.out.println(hospital.ingresarGato(nombre, peso, edad));
                        } catch (NumberFormatException e) {
                            System.out.println("ERROR: datos invalidos");
                        }
                    }
                } else if (tipoAnimal.equals("ave")) {
                    if (partes.length < 6) {
                        System.out.println("ERROR: datos invalidos");
                    } else {
                        try {
                            String nombre = partes[2];
                            double peso = Double.parseDouble(partes[3]);
                            int edad = Integer.parseInt(partes[4]);
                            double envergadura = Double.parseDouble(partes[5]);
                            System.out.println(hospital.ingresarAve(nombre, peso, edad, envergadura));
                        } catch (NumberFormatException e) {
                            System.out.println("ERROR: datos invalidos");
                        }
                    }
                } else {
                    System.out.println("ERROR: comando desconocido.");
                }
            } else if (comando.equals("vacunar")) {
                if (partes.length < 2) {
                    System.out.println("ERROR: paciente no encontrado");
                } else {
                    System.out.println(hospital.vacunar(partes[1]));
                }
            } else if (comando.equals("asignar")) {
                if (partes.length < 2) {
                    System.out.println("ERROR: paciente no encontrado");
                } else {
                    System.out.println(hospital.asignar(partes[1]));
                }
            } else if (comando.equals("medicar")) {
                if (partes.length < 3) {
                    System.out.println("ERROR: datos invalidos");
                } else {
                    try {
                        String nombre = partes[1];
                        double mg = Double.parseDouble(partes[2]);
                        System.out.println(hospital.medicar(nombre, mg));
                    } catch (NumberFormatException e) {
                        System.out.println("ERROR: datos invalidos");
                    }
                }
            } else if (comando.equals("alta")) {
                if (partes.length < 2) {
                    System.out.println("ERROR: paciente no encontrado");
                } else {
                    System.out.println(hospital.alta(partes[1]));
                }
            } else if (comando.equals("reporte")) {
                hospital.generarReporte();
            } else {
                System.out.println("ERROR: comando desconocido.");
            }
        }
        scanner.close();
    }
}

