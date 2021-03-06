package searchEngine;

import java.util.Comparator;

/**
 * Compares two WebPages by their URL in alphabetical order.
 *
 * @author Sayan Sivakumaran e-mail: sayan.sivakumaran@stonybrook.edu Stony
 *         Brook ID: 110261379
 **/
public class UrlComparator implements Comparator<WebPage> {
	@Override
	public int compare(WebPage w1, WebPage w2) {
		return w1.getUrl().compareTo(w2.getUrl());
	}
}
