package edu.miamioh.cse283.htw;

import java.util.ArrayList;

public class RoomSetup {
	
	private int numRooms;
	private static int nth;
	private int DEFAULT_NUM_ROOMS = 4;
	private ArrayList<Room> rmList = new ArrayList<Room>();

	public RoomSetup() {
		this.nth = (int) Math.sqrt(DEFAULT_NUM_ROOMS);
		this.numRooms = (int) Math.pow(nth, 2);
	}
	
	public RoomSetup(int numRooms) {
		this.nth = (int) Math.sqrt(numRooms);
		this.numRooms = (int) Math.pow(nth, 2);
	}
	
	
	public int updateNumRooms() {
		return numRooms;
	}
	
	
	public void buildList() {
		for(int x = 0; x<nth; x++) {
			for(int y = 0; y <nth; y++) {
				rmList.add( new Room( (nth*x + y + 1), x, y, nth) );
			}
		}
	}
	
	public ArrayList<Room> getList() {
		return rmList;
	}
	
	
	
	
	
	/*
	public static void main(String[] args) {
		RoomSetup rooms = new RoomSetup(9);
		int x = rooms.updateNumRooms();
		System.out.println("numRooms= "+x);
		System.out.println("nth= "+nth);
		rooms.buildList();
		ArrayList<Room> list = rooms.getList();
		System.out.println(list.toString());
		int rm = 7;
		System.out.println();
		System.out.println(list.get(rm-1));
		ArrayList<Integer> avNeigh = list.get(rm-1).avNeigh();
		System.out.println("AN to "+rm+":"+avNeigh.toString());
	}
	**/
	
}
