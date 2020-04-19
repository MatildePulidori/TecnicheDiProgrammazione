package it.polito.porto.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.porto.dao.PortoDAO;

public class Model {
	
	public PortoCreatorIdMap creatorIdMap;
	public PortoArticleIdMap articleIdMap;

	public List<PortoCreator> creators;
	public List<PortoArticle> articles;
	
	public PortoDAO pdao;
	
	public Model() {
		pdao = new PortoDAO();
		
		creatorIdMap = new PortoCreatorIdMap();
		articleIdMap = new PortoArticleIdMap();
		
		creators = new ArrayList<PortoCreator>(pdao.getAllCreator(creatorIdMap));
		articles = new ArrayList<PortoArticle>(pdao.getAllArticle(articleIdMap));
	}

	
	public PortoCreator getCreator(int idCreator) {
		return this.creatorIdMap.getPortoCreator(idCreator);
	}
	
	public PortoArticle getArticle(long eprintid) {
		return this.articleIdMap.getPortoArticle(eprintid);
	}
	
	public boolean isOfCreator(PortoArticle article, PortoCreator creator) {
		
		if ( !this.getCreator(creator.getId()).equals(null) && !this.getArticle(article.getEprintid()).equals(null) ) {
			
			PortoCreator c = this.getCreator(creator.getId());
			PortoArticle a =  this.getArticle(article.getEprintid());
			
			if (c.getArticoli().contains(a) && a.getCreators().contains(creator)) {
				return true;
			}
		}
		return false;
	}
	
	public List<PortoArticle> getArticoliCreator(PortoCreator creator) {
		
		PortoCreator pCreator = this.getCreator(creator.getId()) ;
		if (!pCreator.equals(null)) {
			List<PortoArticle> articoli = new ArrayList<PortoArticle>(pdao.getArticlesOfCreator(pCreator, creatorIdMap, articleIdMap));
			Collections.sort(articoli);
			return articoli;
		}
		return null;
		
	}


	public List<PortoCreator> cercaRevisori(PortoCreator creator, PortoArticle article) {
		
		PortoCreator pCreator = this.getCreator(creator.getId()); 
		PortoArticle pArticle = this.getArticle(article.getEprintid());
		Map<Integer, PortoCreator> coautori = new HashMap<Integer, PortoCreator>();
		
		if (!pCreator.equals(null) && !pArticle.equals(null) && this.isOfCreator(pArticle, pCreator)==true) {
			
			List<PortoArticle> articoliCreator= this.getArticoliCreator(pCreator);
			
			// Per ogni articolo dell'autore
			for (PortoArticle a : articoliCreator) {
				
				if (!a.equals(pArticle)) {
					
					// Cerco gli altri coautori
					List<PortoCreator> coautoriArticolo = pdao.getCreatorsOfArticle(a, creatorIdMap, articleIdMap);
					
					// Li aggiungo alla mappa, cosi non ci sono duplicati
					for (PortoCreator pc : coautoriArticolo) {
						coautori.put(pc.getId(), pc);
					}
				}
				
			}
		}
		
		List<PortoCreator> reviewerPossibili = new ArrayList<PortoCreator>(this.creators);
		reviewerPossibili.removeAll(coautori.values());
		return reviewerPossibili ;
		
	}


	


	

	
	
	
}
