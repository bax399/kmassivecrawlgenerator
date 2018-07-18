package model;
import model.graphresource.*;

import java.util.*;

public class HexMap {
	LinkedHashMap<Tuple<Integer>, Hex> hexes;
	Graph<Hex,Connection> neighbours;
	Graph<Hex,Road> roads;
	Graph<Hex,River> rivers;
}
