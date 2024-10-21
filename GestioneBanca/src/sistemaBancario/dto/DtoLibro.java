package sistemaBancario.dto;

import java.util.List;

public class DtoLibro {
    private Integer id_libro = null;
    private String titolo;
    private String genere;
    private List<DtoConto> autor;
    private boolean disponibile; // true per disponibile

    public DtoLibro(String titolo, String genere, List<DtoConto> autor, boolean disponibile) {
        this.titolo = titolo;
        this.genere = genere;
        this.autor = autor;
        this.disponibile = disponibile;
    }

    public DtoLibro(Integer id_libro, String titolo, String genere, boolean disponibile) {
        this.id_libro = id_libro;
        this.titolo = titolo;
        this.genere = genere;
        this.autor = null;
        this.disponibile = disponibile;
    }

    public Integer getId_libro() {
        return id_libro;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getGenere() {
        return genere;
    }

    public List<DtoConto> getAutor() {
        return autor;
    }

    public boolean isDisponibile() {
        return disponibile;
    }

    @Override
    public String toString() {
        return "Libri{" +
                "id_libro=" + id_libro +
                ", titolo='" + titolo + '\'' +
                ", genere='" + genere + '\'' +
                ", autor=" + autor +
                ", disponibile=" + disponibile +
                '}';
    }
}
