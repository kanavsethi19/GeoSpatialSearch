package com.search.service;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
/**
 * @author kanav.sethi
 *
 */
@Service
public class QuerySolrImpl implements QuerySolr {
	private static final Logger logger = LoggerFactory.getLogger(QuerySolrImpl.class);
	
	public Map<Object, Object> query(String location,Integer miles) throws SolrServerException, IOException {
		Map<Object, Object> locations = new HashMap<>();
		try {
			String urlString = "http://localhost:8983/solr/restaurants";
			SolrClient solrClient = new HttpSolrClient.Builder(urlString).build();
			SolrQuery parameters = new SolrQuery();
			logger.info("Location is: "+location);
			String loc = location;
			parameters.set("q", "*:*");
			parameters.set("fq", "{!geofilt pt=" + loc + " sfield=latlon d="+miles+"}");
			QueryResponse response = solrClient.query(parameters);
			
			SolrDocumentList list = response.getResults();
			
			if(list.size() > 0 ) {
				for (SolrDocument solrDocument : list) {
					locations.put(solrDocument.getFieldValue("name"), solrDocument.getFieldValue("latlon"));
				}
			}
			else if(list.size() == 0)
				locations.put("No restaurants found near you, please go home and cook!","N.A");
			
			logger.info("Number of results from SOLR: "+list.getNumFound());
		}
		catch (Exception e) {
			logger.info("Exception occurred while querying SOLR! "+e);
			throw e;
		}
		return locations;
	}
}
