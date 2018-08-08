package com.search.service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
/**
 * @author kanav.sethi
 *
 */
@Service
public class LatLongImpl implements LatLong {
	
	private static final Logger logger = LoggerFactory.getLogger(LatLongImpl.class);

	public  String getLatLongPositions(String address) throws Exception {
		int responseCode = 0;
		String latLon = null;
		try {
			String api = "http://maps.googleapis.com/maps/api/geocode/xml?address=" + URLEncoder.encode(address, "UTF-8")
					+ "&sensor=true";
			URL url = new URL(api);
			HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.connect();
			responseCode = httpConnection.getResponseCode();
			if (responseCode == 200) {
				DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document document = builder.parse(httpConnection.getInputStream());
				XPathFactory xPathfactory = XPathFactory.newInstance();
				XPath xpath = xPathfactory.newXPath();
				XPathExpression expr = xpath.compile("/GeocodeResponse/status");
				String status = (String) expr.evaluate(document, XPathConstants.STRING);
				if (status.equals("OK")) {
					expr = xpath.compile("//geometry/location/lat");
					String latitude = (String) expr.evaluate(document, XPathConstants.STRING);
					expr = xpath.compile("//geometry/location/lng");
					String longitude = (String) expr.evaluate(document, XPathConstants.STRING);
					latLon = latitude+ "," +longitude;
				} else {
					throw new Exception("Error from Google API - response status: " + status);
				}
			}
		}
		catch (Exception e) {
			logger.info("Exception occurred while hitting Google Maps API for LatLong values! "+e);
			throw e;
		}
		return latLon;
	}

}
