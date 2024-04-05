package school.tecno.cinema;

import java.sql.Date;
import java.sql.Time;

/**
 * Film
 */
public class Film {

	public int id;
	public String nome;
	public String regista;
	public String genere;
	public Date uscita;
	public Time durata;
	public String imgPath;

	public Film(int id, String title, Time duration) {
		this.id = id;
		this.nome = title;
		this.durata = duration;

	}

	public Film(int id, String title, String director, String genre, Date release, Time duration, String imgPath) {
		this.id = id;
		this.nome = title;
		this.regista = director;
		this.genere = genre;
		this.uscita = release;
		this.durata = duration;
		this.imgPath = "images/" + imgPath;
	}

}
