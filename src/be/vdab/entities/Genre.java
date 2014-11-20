package be.vdab.entities;

import java.util.Objects;

public class Genre implements Comparable<Genre> {

    private long genreId;
    private String naam;

    public Genre(long genreId, String naam) {
        this.genreId = genreId;
        this.naam = naam;
    }

    public long getGenreId() {
        return genreId;
    }

    public void setGenreId(long genreId) {
        this.genreId = genreId;
    }

    public static boolean isTekstValid(String tekst) {
        return tekst != null && tekst.isEmpty();
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        if (!isTekstValid(naam)) {
            throw new IllegalArgumentException();
        }
        this.naam = naam;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (int) (this.genreId ^ (this.genreId >>> 32));
        hash = 89 * hash + Objects.hashCode(this.naam);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Genre other = (Genre) obj;
        if (this.genreId != other.genreId) {
            return false;
        }
        return !(this.naam == null ? other.naam != null : !this.naam.equals(other.naam));
    }

    @Override
    public int compareTo(Genre o) {
        return (this.naam).compareTo(o.naam);
    }

    @Override
    public String toString() {
        return "Genre{" + "genreId=" + genreId + ", naam=" + naam + '}';
    }  
}
