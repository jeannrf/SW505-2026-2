public class Matricula {
    private int cod_matricula;
    private Alumno alumno;
    private Curso cursos[];

    public Matricula() {
    }

    public Matricula(int cod_matricula, Alumno alumno, Curso[] cursos) {
        this.cod_matricula = cod_matricula;
        this.alumno = alumno;
        this.cursos = cursos;
    }

    public int getCod_matricula() {
        return cod_matricula;
    }

    public void setCod_matricula(int cod_matricula) {
        this.cod_matricula = cod_matricula;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Curso[] getCursos() {
        return cursos;
    }

    public void setCursos(Curso[] cursos) {
        this.cursos = cursos;
    }

    public void mostrar() {
        System.out.println("=== MATRÍCULA N° " + cod_matricula + " ===");
        if (alumno != null) {
            alumno.mostrar();
        }
        System.out.println("Cursos:");
        if (cursos != null) {
            for (Curso c : cursos) {
                if (c != null) {
                    c.mostrar();
                }
            }
        }
    }
}
