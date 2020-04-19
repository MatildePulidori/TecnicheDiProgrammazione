package it.polito.tdp.flight.model;
import java.util.*;

public class RouteIdMap {
	
	private Map<Integer, Route> map  ;

	public RouteIdMap () {
		map = new HashMap<>();
	}
	
	
	/**
	 * Dato un Id, mi restituisce l'oggetto Route corrispondente, se esiste.
	 * Altrimeni ritorna null.
	 * @param RouteId
	 * @return
	 */
	public Route get(int RouteId) {
		return map.get(RouteId);
	}
	
	
	/**
	 * Dato un oggetto Route, controllo se è già presente nella mappa.
	 * Se no, aggiunge l'Route.
	 * @param Route
	 * @return
	 */
	public Route get(Route Route) {
		Route old = map.get(Route.getRouteId());
		if (old == null) {
			map.put(Route.getRouteId(),  Route);
			return Route;
		}
		return old;
	}

	public void put(Route route, int routeId) {
		map.put(routeId, route);
	}
}
