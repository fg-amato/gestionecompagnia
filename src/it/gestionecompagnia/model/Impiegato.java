package it.gestionecompagnia.model;

import java.util.Date;

public class Impiegato {
	private Long id;
	private String nome;
	private String cognome;
	private String codiceFiscale;
	private Date dataDiNascita;
	private Date dataAssunzione;
	private Compagnia compagniaPerCuiLavora;

	public Impiegato() {
		super();
	}

	public Impiegato(Long id, String nome, String cognome, String codiceFiscale, Date dataDiNascita,
			Date dataAssunzione, Compagnia compagniaPerCuiLavora) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		this.dataDiNascita = dataDiNascita;
		this.dataAssunzione = dataAssunzione;
		this.compagniaPerCuiLavora = compagniaPerCuiLavora;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public Date getDataDiNascita() {
		return dataDiNascita;
	}

	public void setDataDiNascita(Date dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}

	public Date getDataAssunzione() {
		return dataAssunzione;
	}

	public void setDataAssunzione(Date dataAssunzione) {
		this.dataAssunzione = dataAssunzione;
	}

	public Compagnia getCompagniaPerCuiLavora() {
		return compagniaPerCuiLavora;
	}

	public void setCompagniaPerCuiLavora(Compagnia compagniaPerCuiLavora) {
		this.compagniaPerCuiLavora = compagniaPerCuiLavora;
	}

	@Override
	public String toString() {
		return "Impiegato [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", codiceFiscale=" + codiceFiscale
				+ ", dataDiNascita=" + dataDiNascita + ", dataAssunzione=" + dataAssunzione + ", compagniaPerCuiLavora="
				+ compagniaPerCuiLavora + "]";
	}

}
