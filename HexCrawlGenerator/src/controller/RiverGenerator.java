package controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import model.ConnectedHexMap;
import model.Connection;
import model.FilledHex;
import model.Pathfinder;
import model.RiverNetwork;
import model.Riverfinder;

public class RiverGenerator extends Generator {
	ConnectedHexMap hexmap;

	public RiverGenerator(ConnectedHexMap chm, Random rd) {
		super(rd);
		hexmap = chm;
	}

	public Set<RiverNetwork> generateRivers() {
		List<FilledHex> riverstarts = new ArrayList<>();

		List<FilledHex> riverends = new ArrayList<>();

		RiverNetwork rn;
		Set<RiverNetwork> networks = new HashSet<>();

		Iterator<FilledHex> it = hexmap.getHexes().values().iterator();
		while (it.hasNext()) {
			FilledHex fh = it.next();

			// Start
			if (rollChance(fh.getHabitat().getRiverOrigin())) {
				riverstarts.add(fh);
			} else if (Double.compare(fh.getHabitat().getCoreBiome().getRiverEnd(), 1d) >= 0) {
				riverends.add(fh);
			}
		}

		Iterator<FilledHex> it2 = riverstarts.iterator();

		Pathfinder rf = new Riverfinder();
		while (it2.hasNext() && riverends.size() > 0) {
			rn = new RiverNetwork(networks);
			FilledHex fh2 = it2.next();
			int random = 0;

			// Choosing a random ending that isn't too close
			FilledHex fend = null;
			random = rand.nextInt(riverends.size());
			fend = riverends.get(random);
			riverends.remove(random);
			/*
			 * for(int ii = 0;ii<5;ii++) {
			 * 
			 * 
			 * if (fend.distance(fh2) > (int) mindistance*5) break; }
			 */

			Set<Connection> path = rf.GreedyBFS(hexmap, fend, fh2);
			rn.createRiver(hexmap, path);
			networks.add(rn);
		}

		return networks;
	}
}
