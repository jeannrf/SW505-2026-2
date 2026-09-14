public class Curso {
    private String codigo;
    private String nombre;
    private int creditos;
    private Docente docente;

    public Curso() {
    }

    public Curso(String codigo, String nombre, int creditos, Docente docente) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.creditos = creditos;
        this.docente = docente;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public void mostrar() {
        String doc = (docente != null) ? docente.getNombres() : "Sin asignar";
        System.out.println("Curso: [" + codigo + "] " + nombre + " (" + creditos + " cred) - Docente: " + doc);
    }
}
