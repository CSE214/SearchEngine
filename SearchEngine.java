package searchEngine;

import java.util.LinkedList;

/**
 * <code>SearchEngine</code> class simulates a search engine that the user can
 * interact with.
 *
 * @author Sayan Sivakumaran e-mail: sayan.sivakumaran@stonybrook.edu Stony
 *         Brook ID: 110261379
 **/
public class SearchEngine {
	public static final String PAGES_FILE = "pages.txt";
	public static final String LINKS_FILE = "links.txt";
	private static WebGraph web;

	public static void main(String[] args) {
		LinkedList<String> keywords = new LinkedList<String>();
		keywords.addLast("cookies");
		keywords.addLast("cupcakes");
		WebPage test = new WebPage("cooking.com", keywords, 0);
		System.out.println(test.toString());
	}
}
