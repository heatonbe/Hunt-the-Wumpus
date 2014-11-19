package edu.miamioh.cse283.htw;

import java.util.*;

public class Room {
	public ArrayList<ClientProxy> players;
	
	private int roomID, x, y, nth, numRooms;
	Client c;
	protected int gold, arrows;
	protected boolean isBats = false, isWumpus = false;
	protected int count = 0;
	
	public Room() {
		players = new ArrayList<ClientProxy>();
	}
	
	public Room(int roomID, int x, int y, int nth) {
		players = new ArrayList<ClientProxy>();
		this.roomID = roomID;
		this.x = x;
		this.y = y;
		this.nth = nth;
		numRooms = (int) Math.pow(nth, 2);
		gold = (int) (Math.random()*10);
		arrows = (int) (Math.random()*10);
		isBats = isBats();
		isWumpus = isWumpus();
		reset();
	}
	
	public void reset() {
		//how and when to reset values
	}
	
	public boolean isBats() {
		double x = Math.random();
		isBats = (x < (0.3)) ? true : false;
		return isBats;
	}
	
	public boolean isWumpus() {
		double x = Math.random();
		isWumpus = (x < (0.15)) ? true : false;
		return isWumpus;
	}
	
	public int getRoomID() {
		return roomID;
	}
	
	public void enterRoom(ClientProxy c) {
		c.senses("You have entered Room "+roomID+
				"\nGold: "+gold+
				"\nArrows: "+arrows+
				"\nBats: "+isBats+
				"\nWumpus: "+isWumpus);
		//return (isBats) ? (int) (Math.random()*9) + 1 : roomID;
	}
	
	public String avNeigh() {
		ArrayList<Integer> avNeigh = new ArrayList<Integer>();
		
		int tmpID = roomID - 1;
		if (tmpID > 0 && (tmpID % nth != 0)) avNeigh.add(tmpID); 
		
		tmpID = roomID + 1;
		if (tmpID <= numRooms && (roomID % nth != 0)) avNeigh.add(tmpID); 
		
		tmpID = roomID - nth;
		if (tmpID > 0) avNeigh.add(tmpID); 
		
		tmpID = roomID + nth;
		if (tmpID <= numRooms) avNeigh.add(tmpID);
		
		return avNeigh.toString();
	}
	
	public String toString() {
		return "ID: "+roomID+"  x: "+x+"  y: "+y+"\n";
	}
}
