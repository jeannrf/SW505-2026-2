public class Alumno {
    private int codigo;
    private String nombres;
    private int ciclo_relativo;
    private int estado;

    public Alumno() {
    }

    public Alumno(int codigo, String nombres, int ciclo_relativo, int estado) {
        this.codigo = codigo;
        this.nombres = nombres;
        this.ciclo_relativo = ciclo_relativo;
        this.estado = estado;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public int getCiclo_relativo() {
        return ciclo_relativo;
    }

    public void setCiclo_relativo(int ciclo_relativo) {
        this.ciclo_relativo = ciclo_relativo;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public void mostrar() {
        System.out.println("Alumno: " + nombres + " | Código: " + codigo + " | Ciclo: " + ciclo_relativo + " | Estado: " + estado);
    }
}
