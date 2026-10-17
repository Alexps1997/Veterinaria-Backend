package veterinaria;

public class Hospital {
    private Paciente[] pacientes;
    private int contadorPacientes;
    private Jaula[] jaulas;

    public Hospital() {
        pacientes = new Paciente[10];
        contadorPacientes = 0;
        jaulas = new Jaula[6];
        cargarDatosIniciales();
    }

    public void cargarDatosIniciales() {
        jaulas[0] = new Jaula(1, "S", "A");
        jaulas[1] = new Jaula(2, "M", "A");
        jaulas[2] = new Jaula(3, "M", "A");
        jaulas[3] = new Jaula(4, "L", "A");
        jaulas[4] = new Jaula(5, "S", "B");
        jaulas[5] = new Jaula(6, "M", "B");

        pacientes[0] = new Perro("Rocky", 30.0, 4, "labrador", true);
        pacientes[1] = new Gato("Misifu", 4.0, 3, 0);
        pacientes[2] = new AveExotica("Kiwi", 0.8, 2, 70.0);
        contadorPacientes = 3;
    }

    public Paciente buscarPaciente(String nombre) {
        for (int i = 0; i < contadorPacientes; i++) {
            if (pacientes[i].getNombre().equalsIgnoreCase(nombre)) {
                return pacientes[i];
            }
        }
        return null;
    }

    private boolean existeNombre(String nombre) {
        return buscarPaciente(nombre) != null;
    }

    public String ingresarPerro(String nombre, double peso, int edad, String raza, String vacunadoStr) {
        if (nombre == null || nombre.trim().isEmpty() || peso <= 0 || peso > 80 || edad < 0 || edad > 30) {
            return "ERROR: datos invalidos";
        }
        if (existeNombre(nombre)) { return "ERROR: nombre duplicado"; }
        if (contadorPacientes >= 10) { return "ERROR: hospital lleno"; }

        boolean vacunado = vacunadoStr.equalsIgnoreCase("si") || vacunadoStr.equalsIgnoreCase("sí");
        Perro perro = new Perro(nombre, peso, edad, raza, vacunado);
        pacientes[contadorPacientes++] = perro;

        if (!vacunado) {
            return "OK: " + nombre + " ingresado en CUARENTENA";
        } else {
            return "OK: " + nombre + " ingresado";
        }
    }

    public String ingresarGato(String nombre, double peso, int edad) {
        if (nombre == null || nombre.trim().isEmpty() || peso <= 0 || peso > 15 || edad < 0 || edad > 30) {
            return "ERROR: datos invalidos";
        }
        if (existeNombre(nombre)) { return "ERROR: nombre duplicado"; }
        if (contadorPacientes >= 10) { return "ERROR: hospital lleno"; }

        Gato gato = new Gato(nombre, peso, edad, 0);
        pacientes[contadorPacientes++] = gato;
        return "OK: " + nombre + " ingresado";
    }

    public String ingresarAve(String nombre, double peso, int edad, double envergadura) {
        if (nombre == null || nombre.trim().isEmpty() || peso <= 0 || peso > 15 || edad < 0 || edad > 30 || envergadura <= 0) {
            return "ERROR: datos invalidos";
        }
        if (existeNombre(nombre)) { return "ERROR: nombre duplicado"; }
        if (contadorPacientes >= 10) { return "ERROR: hospital lleno"; }

        AveExotica ave = new AveExotica(nombre, peso, edad, envergadura);
        pacientes[contadorPacientes++] = ave;
        return "OK: " + nombre + " ingresado";
    }

    public String vacunar(String nombre) {
        Paciente p = buscarPaciente(nombre);
        if (p == null) { return "ERROR: paciente no encontrado"; }
        if (!(p instanceof Perro)) { return "ERROR: solo aplica a perros"; }
        Perro perro = (Perro) p;
        if (perro.isVacunado()) { return "ERROR: ya vacunado"; }
        perro.setVacunado(true);
        return "OK: " + nombre + " vacunado (pasa a INGRESADO)";
    }

    public String asignar(String nombre) {
        Paciente p = buscarPaciente(nombre);
        if (p == null) { return "ERROR: paciente no encontrado"; }
        if ("CUARENTENA".equals(p.getEstado())) { return "ERROR: paciente en cuarentena"; }
        if (p.getJaulaAsignada() != null) { return "ERROR: paciente ya tiene jaula"; }

        String tamanoNecesario = "";
        double peso = p.getPeso();
        if (peso < 5) { tamanoNecesario = "S"; }
        else if (peso <= 25) { tamanoNecesario = "M"; }
        else { tamanoNecesario = "L"; }

        if (p instanceof AveExotica) {
            AveExotica ave = (AveExotica) p;
            if (ave.getEnvergadura() > 60 && tamanoNecesario.equals("S")) {
                tamanoNecesario = "M";
            }
        }

        String zonaNecesaria = "";
        if (p instanceof Perro || p instanceof Gato) { zonaNecesaria = "A"; }
        else if (p instanceof AveExotica) { zonaNecesaria = "B"; }

        Jaula jaulaEncontrada = null;
        for (int i = 0; i < jaulas.length; i++) {
            Jaula j = jaulas[i];
            if (j.estaLibre() && j.getZona().equalsIgnoreCase(zonaNecesaria)) {
                if (esTamanoCompatible(tamanoNecesario, j.getTamano())) {
                    jaulaEncontrada = j;
                    break;
                }
            }
        }

        if (jaulaEncontrada == null) { return "ERROR: no hay jaula compatible"; }

        jaulaEncontrada.setOcupante(p);
        p.setJaulaAsignada(jaulaEncontrada);
        return "OK: " + nombre + " asignado a jaula " + jaulaEncontrada.getNumero();
    }

    private boolean esTamanoCompatible(String requerido, String disponible) {
        int r = nivelTamano(requerido);
        int d = nivelTamano(disponible);
        return d >= r;
    }

    private int nivelTamano(String t) {
        if (t.equalsIgnoreCase("S")) return 1;
        if (t.equalsIgnoreCase("M")) return 2;
        if (t.equalsIgnoreCase("L")) return 3;
        return 0;
    }

    public String medicar(String nombre, double mg) {
        Paciente p = buscarPaciente(nombre);
        if (p == null) { return "ERROR: paciente no encontrado"; }
        if (mg <= 0) { return "ERROR: datos invalidos"; }
        if ("CUARENTENA".equals(p.getEstado())) { return "ERROR: paciente en cuarentena"; }
        if (p.getJaulaAsignada() == null) { return "ERROR: paciente sin jaula"; }
        if (p instanceof Gato) {
            Gato g = (Gato) p;
            if (g.getNivelEstres() >= 8) { return "ERROR: gato con estres alto"; }
        }

        double dosisMaxima = p.getPeso() * p.getFactorDosis();
        if (mg > dosisMaxima) { return "ERROR: dosis excede el maximo"; }

        double acumuladoPermitido = 3 * dosisMaxima;
        if (p.getDosisAcumulada() + mg > acumuladoPermitido) { return "ERROR: tope acumulado superado"; }

        p.setDosisAcumulada(p.getDosisAcumulada() + mg);
        p.setEstado("EN_TRATAMIENTO");

        if (p instanceof Gato) {
            Gato g = (Gato) p;
            int nuevoEstres = g.getNivelEstres() + 2;
            if (nuevoEstres > 10) nuevoEstres = 10;
            g.setNivelEstres(nuevoEstres);
        }

        return "OK: " + nombre + " medicado con " + mg + " mg";
    }

    public String alta(String nombre) {
        Paciente p = buscarPaciente(nombre);
        if (p == null) { return "ERROR: paciente no encontrado"; }
        if (!"EN_TRATAMIENTO".equals(p.getEstado())) { return "ERROR: paciente no apto para alta"; }

        if (p.getJaulaAsignada() != null) {
            p.getJaulaAsignada().setOcupante(null);
            p.setJaulaAsignada(null);
        }

        int index = -1;
        for (int i = 0; i < contadorPacientes; i++) {
            if (pacientes[i].getNombre().equalsIgnoreCase(nombre)) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            for (int i = index; i < contadorPacientes - 1; i++) {
                pacientes[i] = pacientes[i + 1];
            }
            pacientes[contadorPacientes - 1] = null;
            contadorPacientes--;
        }

        return "OK: " + nombre + " dado de alta";
    }

    public void generarReporte() {
        System.out.println("REPORTE");
        System.out.println("Pacientes: " + contadorPacientes + "/10");
        for (int i = 0; i < contadorPacientes; i++) {
            Paciente p = pacientes[i];
            String tipo = "";
            if (p instanceof Perro) tipo = "Perro";
            else if (p instanceof Gato) tipo = "Gato";
            else if (p instanceof AveExotica) tipo = "Ave";

            String jaulaStr = (p.getJaulaAsignada() != null) ? String.valueOf(p.getJaulaAsignada().getNumero()) : "-";
            System.out.println(p.getNombre() + " | " + tipo + " | " + String.format("%.1f", p.getPeso()) + " kg | " + p.getEstado() + " | jaula: " + jaulaStr);
        }

        int jaulasLibres = 0;
        for (int i = 0; i < jaulas.length; i++) {
            if (jaulas[i].estaLibre()) { jaulasLibres++; }
        }
        System.out.println("Jaulas libres: " + jaulasLibres);
    }
}