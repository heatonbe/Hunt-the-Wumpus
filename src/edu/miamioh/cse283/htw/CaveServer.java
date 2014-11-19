package edu.miamioh.cse283.htw;

import java.net.*;
import java.util.*;

public class CaveServer {

	/** Port base for this cave server. */
	protected int portBase;

	/** Socket for accepting connections from players. */
	protected ServerSocket clientSocket;

	/** Proxy to the CaveSystemServer. */

	protected CaveSystemServerProxy caveSystem;

	/** Rooms in this CaveServer. */
	protected ArrayList<Room> rooms;
	boolean welcome = true, nowWhat = false;
	int numRooms = 9;
	protected Room r;
	protected RoomSetup rS;

	/** Constructor. */
	public CaveServer(CaveSystemServerProxy caveSystem, int portBase) {
		this.caveSystem = caveSystem;
		this.portBase = portBase;

		// --Builds all the rooms in the correct layout
		rS = new RoomSetup(numRooms);
		numRooms = rS.updateNumRooms();
		rS.buildList();
		rooms = rS.getList();
		// ArrayList<Integer> avNeigh0 = rooms.get(0).avNeigh();

	}

	/** Returns the port number to use for accepting client connections. */
	public int getClientPort() {
		return portBase;
	}

	/** This is the thread that handles a single client connection. */
	public class ClientThread implements Runnable {
		/**
		 * This is our "client" (actually, a proxy to the network-connected
		 * client).
		 */
		protected ClientProxy client;

		/** Constructor. */
		public ClientThread(ClientProxy client) {
			this.client = client;
		}

		/**
		 * Play the game with this client.
		 */
		public void run() {
			while (true) {
				try {

					if (welcome) {
						client.message("Welcome!");

						r = rooms.get(0);
						r.enterRoom(client);
						System.out.println();
						welcome = false;
						//client.message("Action?");

					} else if(nowWhat) { 
						String action = client.getAction();

						// -- and perform the action
						if (action.toUpperCase().equals("MOVE")) {
							client.message("Choose Room: " + r.avNeigh());
							action = client.getAction();
							int newRoom = Integer.parseInt(action) - 1;
							r = rooms.get(newRoom);
							r.enterRoom(client);
						}
						if (action.toUpperCase().equals("SHOOT")) {
							String report = client.shoot(r);
							client.message(report);
							r.enterRoom(client);
							nowWhat = true;
						}
						if (action.toUpperCase().equals("LOOT")) {
							client.loot += r.gold;
							r.gold = 0;
							client.arrows += r.arrows;
							r.arrows = 0;
							r.enterRoom(client);
							nowWhat = true;
						}
						if (action.toUpperCase().equals("ESCAPE")) {
							rS = new RoomSetup(numRooms);
							numRooms = rS.updateNumRooms();
							rS.buildList();
							rooms = rS.getList();
							welcome = true;
						}
					}
					else {
			
						client.message("Action?");

						// now, in a loop while the player is alive:
						// -- send the player their "senses":

						// -- and retrieve their action:
						String action = client.getAction();

						// -- and perform the action
						if (action.toUpperCase().equals("MOVE")) {
							client.message("Choose Room: " + r.avNeigh());
							action = client.getAction();
							int newRoom = Integer.parseInt(action) - 1;
							r = rooms.get(newRoom);
							r.enterRoom(client);
							nowWhat = true;
						}
						if (action.toUpperCase().equals("SHOOT")) {
							String report = client.shoot(r);
							client.message(report);
							r.enterRoom(client);
							nowWhat = true;
						}
						if (action.toUpperCase().equals("LOOT")) {
							client.loot += r.gold;
							r.gold = 0;
							client.arrows += r.arrows;
							r.arrows = 0;
							r.enterRoom(client);
							nowWhat = true;
						}
						if (action.toUpperCase().equals("ESCAPE")) {
							rS = new RoomSetup(numRooms);
							numRooms = rS.updateNumRooms();
							rS.buildList();
							rooms = rS.getList();
							welcome = true;
						}

					}
				} catch (Exception ex) {
					// If an exception is thrown, we can't fix it here -- Crash.
					ex.printStackTrace();
					System.exit(1);
				}
			}
		}
	}

	/** Runs the CaveSystemServer. */
	public void run() {
		try {
			clientSocket = new ServerSocket(getClientPort());
			caveSystem.register(clientSocket);

			while (true) {
				// and now loop forever, accepting client connections:
				while (true) {
					ClientProxy client = new ClientProxy(clientSocket.accept());
					(new Thread(new ClientThread(client))).start();
				}
			}
		} catch (Exception ex) {
			// If an exception is thrown, we can't fix it here -- Crash.
			ex.printStackTrace();
			System.exit(1);
		}
	}

	/** Main method (run the CaveServer). */
	public static void main(String[] args) throws Exception {
		// first, we need our proxy object to the CaveSystemServer:
		CaveSystemServerProxy caveSystem = new CaveSystemServerProxy(
				new Socket(InetAddress.getByName(args[0]),
						Integer.parseInt(args[1]) + 1));
		/**
		 * args[1] + 1 because args[1] is the "port base" where new port numbers
		 * will stem/arise from
		 * 
		 */

		// now construct this cave server, and run it:
		CaveServer cs = new CaveServer(caveSystem, Integer.parseInt(args[2]));
		cs.run();
	}
}
