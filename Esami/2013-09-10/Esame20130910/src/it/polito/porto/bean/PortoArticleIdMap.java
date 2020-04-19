package it.polito.porto.bean;

import java.util.HashMap;
import java.util.Map;

public class PortoArticleIdMap {
	
public Map<Long, PortoArticle> map;
	
	public PortoArticleIdMap() {
		map = new HashMap<Long, PortoArticle>();
	}
	
	public PortoArticle getPortoArticle (long eprintid) {
		return map.get(eprintid);
	}

	public PortoArticle getPortoArticle (PortoArticle article) {
		PortoArticle old = map.get(article.getEprintid());
		if (old == null) {
			map.put(article.getEprintid(), article);
			return article;
		}
		return old;
	}

}
