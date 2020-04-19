package it.polito.tdp.rivers.model;

import java.util.ArrayList;
import java.util.List;
import it.polito.tdp.rivers.db.RiversDAO;

public class Model {

	private List<River> allRivers;
	private RiverIdMap riverIdMap;
	private RiversDAO rdao;
	
	public Model() {
		rdao = new RiversDAO();
		allRivers = new ArrayList<River>();
		riverIdMap = new RiverIdMap();
	}
	
	
	public List<River> getAllRivers() {
		allRivers.addAll(rdao.getAllRivers(riverIdMap));
		return allRivers;
	}


	public void getRiverFlowMesures(River river) {
		River r = riverIdMap.get(river.getId());
		rdao.getRiverFlowMisures(r, riverIdMap);
	}

}
