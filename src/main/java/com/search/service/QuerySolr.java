package com.search.service;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Service;
@Service
public class QuerySolr {
	public static void query(String location) throws SolrServerException, IOException {
		String urlString = "http://localhost:8983/solr/geolocations";
		SolrClient solrClient = new HttpSolrClient.Builder(urlString).build();
		SolrQuery parameters = new SolrQuery();
		System.out.println("Location is: "+location);
		String loc = location;
		parameters.set("q", "*:*");
		parameters.set("fq", "{!geofilt pt=" + loc + " sfield=latlon d=10}");
		QueryResponse response = solrClient.query(parameters);
		
		SolrDocumentList list = response.getResults();
		System.out.println(list);
		System.out.println(list.getNumFound());
	}
}
