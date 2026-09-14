public class Docente {
    private int codigo;
    private String nombres;
    private String especialidad;

    public Docente() {
    }

    public Docente(int codigo, String nombres, String especialidad) {
        this.codigo = codigo;
        this.nombres = nombres;
        this.especialidad = especialidad;
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

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public void mostrar() {
        System.out.println("Docente: " + nombres + " | Código: " + codigo + " | Especialidad: " + especialidad);
    }
}
