package it.polito.tdp.borders.model;

public class Border {
	private Country nazione1;
	private Country nazione2;
	public Border(Country nazione1, Country nazione2) {
		super();
		this.nazione1 = nazione1;
		this.nazione2 = nazione2;
	}
	public Country getNazione1() {
		return nazione1;
	}
	public void setNazione1(Country nazione1) {
		this.nazione1 = nazione1;
	}
	public Country getNazione2() {
		return nazione2;
	}
	public void setNazione2(Country nazione2) {
		this.nazione2 = nazione2;
	}
	@Override
	public String toString() {
		return "Border [nazione1=" + nazione1 + ", nazione2=" + nazione2 + "]";
	}
	

}
