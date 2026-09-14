public class Main {
    public static void main(String[] args) {
        Docente doc1 = new Docente(101, "Juan Herencia", "Construcción de Software");

        Curso c1 = new Curso("SW505", "Construcción de Software", 4, doc1);
        Curso c2 = new Curso("CC212", "Estructura de Datos", 4, doc1);

        Curso[] listaCursos = { c1, c2 };

        Alumno alu1 = new Alumno(20241001, "Jean García", 5, 1);

        Matricula mat = new Matricula(1, alu1, listaCursos);

        mat.mostrar();
    }
}
