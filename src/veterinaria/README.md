Hospital Veterinario
Este proyecto es un sistema de consola en Java (JDK 17+) desarrollado para administrar el ingreso, asignación de jaulas, medicación y alta de pacientes en una clínica veterinaria. El programa fue diseñado aplicando los conceptos vistos en clase: Programación Orientada a Objetos (POO), encapsulamiento, herencia, el uso exclusivo de vectores de tamaño fijo y la separación entre la lógica del programa y la interacción por consola.

¿Qué se hizo?
Se construyó una aplicación por línea de comandos que permite gestionar el estado de tres tipos de animales: Perros, Gatos y Aves Exóticas.

El sistema realiza las siguientes funciones:

Carga de datos iniciales: Al iniciar, el hospital crea 6 jaulas predefinidas y registra 3 pacientes por defecto (Rocky, Misifú y Kiwi).

Registro de pacientes: Permite ingresar perro, gato o ave validando que el nombre no esté repetido, que los datos de peso y edad sean correctos y que no se supere el límite de 10 pacientes.

Vacunación y cuarentena: Si un perro no está vacunado ingresa en estado de cuarentena, lo que le impide recibir medicación o asignarle jaula hasta ser vacunado.

Asignación de jaulas: Busca la primera jaula disponible que cumpla con la zona requerida (Zona A para perros y gatos; Zona B para aves) y con el tamaño adecuado según el peso del animal (S, M o L).

Medicación: Aplica medicamentos calculando la dosis máxima y acumulada según el peso y tipo de animal. En los gatos, controla que su nivel de estrés no sea demasiado alto para medicarlos.

Alta y compactación de vectores: Al dar de alta a un paciente en tratamiento, se libera su jaula y se borra del vector de pacientes desplazando las posiciones siguientes para no dejar huecos vacíos.

Reporte: Muestra la lista actual de pacientes con su estado y jaula, junto con la cantidad de jaulas libres.

¿Cómo se hizo?
El desarrollo se estructuró en varias clases respetando las reglas de la asignatura:

Herencia: Se creó la clase padre Paciente, de la cual heredan Perro, Gato y AveExotica. Cada subclase le pasa a la clase padre su factor de dosis específico mediante la instrucción super.

Encapsulamiento: Todos los atributos son privados y solo se puede acceder o modificarlos mediante getters y setters que contienen validaciones para evitar datos inválidos.

Uso de vectores: No se utilizaron colecciones avanzadas como ArrayList. Los datos se almacenan en arreglos estáticos (Paciente[10] y Jaula[6]). La eliminación de elementos se maneja manualmente mediante desplazamientos en el vector.

Separación de consola y dominio: Toda la lógica de negocio se encuentra en la clase Hospital y en las clases del modelo. La lectura de comandos con Scanner y las impresiones con System.out se realizan únicamente dentro de la clase Main.

Estructura de archivos
src/
└── veterinaria/
├── AveExotica.java
├── Gato.java
├── Hospital.java
├── Jaula.java
├── Main.java
├── Paciente.java
└── Perro.java