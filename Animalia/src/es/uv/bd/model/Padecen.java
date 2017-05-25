package es.uv.bd.model;

/**
 *
 * @author 
 */
public class Padecen {

    private int id_campista;
    private int id_patologia;
    private boolean activa;
    private String gravedad;
    private String ind_especiales;

    public Padecen() {
    }

    public Padecen(int id_campista, int id_patologia, boolean activa, String gravedad, String ind_especiales) {
        this.id_campista = id_campista;
        this.id_patologia = id_patologia;
        this.activa = activa;
        this.gravedad = gravedad;
        this.ind_especiales = ind_especiales;
    }
    
    public int getId_campista() {
        return id_campista;
    }

    public void setId_campista(int id_campista) {
        this.id_campista = id_campista;
    }

    public int getId_patologia() {
        return id_patologia;
    }

    public void setId_patologia(int id_patologia) {
        this.id_patologia = id_patologia;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public String getGravedad() {
        return gravedad;
    }

    public void setGravedad(String gravedad) {
        this.gravedad = gravedad;
    }

    public String getInd_especiales() {
        return ind_especiales;
    }

    public void setInd_especiales(String ind_especiales) {
        this.ind_especiales = ind_especiales;
    }

    @Override
    public String toString() {
        return "Padecen{" + "id_campista=" + id_campista + ", id_patologia=" + id_patologia + ", activa=" + activa + ", gravedad=" + gravedad + ", ind_especiales=" + ind_especiales + '}';
    }
    
    public String getActiva(){
        if(activa){
            return "activa";
        } else {
            return "inactiva";
        }
    }

}
