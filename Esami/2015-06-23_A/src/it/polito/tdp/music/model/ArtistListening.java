package it.polito.tdp.music.model;

import java.time.Month;

public class ArtistListening {

	private Artist artist;
	private Month month;
	private int numAscolti;
	public ArtistListening(Artist artist, Month month, int numAscolti) {
		super();
		this.artist = artist;
		this.month = month;
		this.numAscolti = numAscolti;
	}
	public Artist getArtist() {
		return artist;
	}
	public void setArtist(Artist artist) {
		this.artist = artist;
	}
	public Month getMonth() {
		return month;
	}
	public void setMonth(Month month) {
		this.month = month;
	}
	public int getNumAscolti() {
		return numAscolti;
	}
	public void setNumAscolti(int numAscolti) {
		this.numAscolti = numAscolti;
	}
	@Override
	public String toString() {
		return artist + ", numAscolti= " + numAscolti ;
	}
	
	
}
