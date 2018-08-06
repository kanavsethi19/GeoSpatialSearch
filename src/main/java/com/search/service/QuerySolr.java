package com.search.service;

import java.io.IOException;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;

public interface QuerySolr {
	public Map<Object, Object>  query(String location) throws SolrServerException, IOException;
}