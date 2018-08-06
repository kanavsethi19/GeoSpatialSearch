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
import org.springframework.stereotype.Service;
@Service
public class QuerySolrImpl implements QuerySolr {
	public Map<Object, Object> query(String location) throws SolrServerException, IOException {
		String urlString = "http://localhost:8983/solr/geolocations";
		SolrClient solrClient = new HttpSolrClient.Builder(urlString).build();
		SolrQuery parameters = new SolrQuery();
		System.out.println("Location is: "+location);
		String loc = location;
		parameters.set("q", "*:*");
		parameters.set("fq", "{!geofilt pt=" + loc + " sfield=latlon d=10}");
		QueryResponse response = solrClient.query(parameters);
		
		SolrDocumentList list = response.getResults();
		Map<Object, Object> locations = new HashMap<>();
		for (SolrDocument solrDocument : list) {
			locations.put(solrDocument.getFieldValue("name"), solrDocument.getFieldValue("latlon"));
		}
		for(Map.Entry<Object, Object> entry: locations.entrySet()) {
			System.out.println(entry.getKey() +" : "+entry.getValue());
		}
		System.out.println(list.getNumFound());
		return locations;
	}
}
