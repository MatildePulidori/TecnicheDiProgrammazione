package it.polito.tdp.ufo.model;

import java.util.HashMap;

@SuppressWarnings("serial")
public class StateIdMap extends HashMap<String, State>{

	public State get(State state) {
		State old = this.get(state.getState());
		
		if (old == null) {
			this.put(state.getState(), state);
			return state;
		}
		else return old;
	}
}
