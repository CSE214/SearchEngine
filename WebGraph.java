package searchEngine;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * <code>WebGraph</code> class simulates the web with a directed graph.
 *
 * @author Sayan Sivakumaran e-mail: sayan.sivakumaran@stonybrook.edu Stony
 *         Brook ID: 110261379
 **/
public class WebGraph {
	private static final int MAX_PAGES = 40;
	private int[][] edges;
	private LinkedList<WebPage> pages;
	private int pageCount;

	/**
	 * Constructs a WebGraph object using the indicated files as the source for
	 * pages and edges.
	 * 
	 * @param pagesFile The path to the pages file
	 * @param linksFile The path to the links file
	 * 
	 *                  <dl>
	 *                  <dt>Preconditions:</dt>
	 *                  <dd>Both parameters reference text files which exist. The
	 *                  files follow proper format as outlined in the "Reading Graph
	 *                  from File" section below.</dd>
	 *                  </dl>
	 *                  <dl>
	 *                  <dt>Postconditions:</dt>
	 *                  <dd>A WebGraph has been constructed and initialized based on
	 *                  the text files.</dd>
	 *                  </dl>
	 * 
	 * @return The constructed webgraph
	 * @throws IllegalArgumentException Thrown if either of the files does not
	 *                                  reference a valid text file, or if the files
	 *                                  are not formatted correctly.
	 */
	public static WebGraph buildFromFiles(String pagesFilePath, String linksFilePath) throws IllegalArgumentException {
		try {
			FileInputStream pagesFile = new FileInputStream(pagesFilePath);
			InputStreamReader inStream = new InputStreamReader(pagesFile);
			BufferedReader reader = new BufferedReader(inStream);
			WebGraph webGraph = new WebGraph();
			String line = "";
			while ((line = reader.readLine()) != null) {
				String[] words = line.trim().split(" ");
				String url = words[0];
				LinkedList<String> keywords = new LinkedList(Arrays.asList(Arrays.copyOfRange(words, 1, words.length)));
				webGraph.addPage(url, keywords);
			}
			FileInputStream linksFile = new FileInputStream(linksFilePath);
			inStream = new InputStreamReader(linksFile);
			reader = new BufferedReader(inStream);
			while ((line = reader.readLine()) != null) {
				String[] words = line.trim().split(" ");
				String source = words[0];
				String destination = words[1];
				webGraph.addLink(source, destination);
			}
			reader.close();
			return webGraph;
		} catch (Exception e) {
			System.out.println(e);
			throw new IllegalArgumentException("One of the paths is invalid.");
		}
	}

	public void sortPages(Comparator<WebPage> comparator) {
		Collections.sort(pages, comparator);
	}

	/**
	 * Returns an instance of WebGraph
	 */
	public WebGraph() {
		this.edges = new int[MAX_PAGES][MAX_PAGES];
		this.pages = new LinkedList<WebPage>();
		this.pageCount = 0;
	}

	/**
	 * Obtains the index of a webPage in the pages list specified by URL.
	 * 
	 * @param url The URL to search for
	 * @return The index
	 */
	public int getIndex(String url) {
		int removeIndex = -1;
		ListIterator<WebPage> list = pages.listIterator();
		while (list.hasNext()) {
			WebPage page = list.next();
			if (page.getUrl().equals(url)) {
				removeIndex = pages.indexOf(page);
				break;
			}
		}
		return removeIndex;
	}

	/**
	 * Adds a page to the WebGraph
	 * 
	 * @param url      The URL of the webpage (must not already exist in the
	 *                 WebGraph).
	 * @param keywords The keywords associated with the WebPage.
	 * 
	 *                 <dl>
	 *                 <dt>Preconditions:</dt>
	 *                 <dd>The URL is unique and does not exist in a WebPage already
	 *                 on the graph. Both inputs are not null.</dd>
	 *                 </dl>
	 *                 <dl>
	 *                 <dt>Postconditions:</dt>
	 *                 <dd>The page has been added to pages at index 'i' and links
	 *                 has been logically extended to include the new row and column
	 *                 indexed by 'i'</dd>
	 *                 </dl>
	 * 
	 * @throws IllegalArgumentException If url is not unique and already exists in
	 *                                  the graph, or if either argument is null.
	 * @throws FullGraphException       If the graph is already full
	 */
	public void addPage(String url, LinkedList<String> keywords) throws IllegalArgumentException, FullGraphException {
		if (pageCount == MAX_PAGES) {
			throw new FullGraphException("The graph is already full.");
		}
		if (url == null) {
			throw new IllegalArgumentException("The URL should not be null.");
		} else if (keywords == null) {
			throw new IllegalArgumentException("The collection of keywords should not be null.");
		}
		ListIterator<WebPage> list = pages.listIterator();
		while (list.hasNext()) {
			if (list.next().getUrl().equals(url)) {
				throw new IllegalArgumentException("The URL should be unique.");
			}
		}
		pages.addLast(new WebPage(url, keywords, pageCount));
		pageCount += 1;
		updatePageRanks();
	}

	/**
	 * Removes the WebPage from the graph with the given URL.
	 * 
	 * @param url The URL of the page to remove from the graph.
	 * 
	 *            <dt>Postconditions:</dt>
	 *            <dd>The WebPage with the indicated URL has been removed from the
	 *            graph, and it's corresponding row and column has been removed from
	 *            the adjacency matrix. All pages that has an index greater than the
	 *            index that was removed should decrease their index value by 1. If
	 *            url is null or could not be found in pages, the method ignores the
	 *            input and does nothing.</dd>
	 *            </dl>
	 */
	public void removePage(String url) {
		// Check for null
		if (url == null) {
			return;
		}
		// Find index if exists
		int removeIndex = getIndex(url);
		if (removeIndex == -1) {
			return;
		}
		// Remove page
		pages.remove(removeIndex);
		pageCount -= 1;
		// Update indices
		ListIterator<WebPage> list = pages.listIterator(removeIndex);
		while (list.hasNext()) {
			WebPage page = list.next();
			page.setIndex(page.getIndex() - 1);
		}
		updatePageRanks();
	}

	/**
	 * Adds a link from the WebPage with the URL indicated by source to the WebPage
	 * with the URL indicated by destination.
	 * 
	 * @param source      The source of the link
	 * @param destination The destination of the link
	 * @throws IllegalArgumentException If either of the URLs are null or could not
	 *                                  be found in pages.
	 */
	public void addLink(String source, String destination) throws IllegalArgumentException {
		if (source == null || destination == null) {
			throw new IllegalArgumentException("A URL should not be null.");
		}

		int sourceIndex = getIndex(source);
		int destinationIndex = getIndex(destination);
		if (sourceIndex == -1) {
			throw new IllegalArgumentException("The source is not in the graph.");
		} else if (destinationIndex == -1) {
			throw new IllegalArgumentException("The destination is not in the graph.");
		} else if (edges[sourceIndex][destinationIndex] == 1) {
			throw new IllegalArgumentException("That link already exists.");
		}

		edges[sourceIndex][destinationIndex] = 1;
		updatePageRanks();
	}

	/**
	 * Removes the specified link from the graph.
	 * 
	 * @param source      The source of the link
	 * @param destination The destination of the link
	 * 
	 *                    <dt>Postconditions:</dt>
	 *                    <dd>The entry in links for the specified hyperlink has
	 *                    been set to 0 (no link). If either of the URLs could not
	 *                    be found, the input is ignored and the method does
	 *                    nothing.</dd>
	 *                    </dl>
	 */
	public void removeLink(String source, String destination) {
		if (source == null || destination == null) {
			return;
		}

		int sourceIndex = getIndex(source);
		int destinationIndex = getIndex(destination);
		if (sourceIndex == -1) {
			return;
		} else if (destinationIndex == -1) {
			return;
		}

		edges[sourceIndex][destinationIndex] = 0;
		updatePageRanks();
	}

	/**
	 * Calculates and assigns the PageRank for the specified page.
	 * 
	 * @param page The page to update the pageRank of
	 */
	public void updatePageRank(WebPage page) {
		ListIterator<WebPage> list = pages.listIterator();
		int pageRank = 0;
		while (list.hasNext()) {
			int sourceIndex = pages.indexOf(list.next());
			if (edges[sourceIndex][page.getIndex()] == 1) {
				pageRank += 1;
			}
		}
		page.setRank(pageRank);
	}

	/**
	 * Calculates and assigns the PageRank for every page in the WebGraph.
	 * 
	 * <dt>Postconditions:</dt>
	 * <dd>All WebPages in the graph have been assigned their proper PageRank.</dd>
	 * </dl>
	 */
	public void updatePageRanks() {
		ListIterator<WebPage> list = pages.listIterator();
		while (list.hasNext()) {
			updatePageRank(list.next());
		}
	}

	/**
	 * @return A list of indices that correspond to the webpages that url links to.
	 */
	public String getLinkString(String url) {
		int index = getIndex(url);
		int[] destinations = edges[index];
		String linkString = "";
		for (int i = 0; i < destinations.length; i++) {
			if (destinations[i] == 1) {
				linkString += (linkString.equals("") ? i : ", " + i);
			}
		}
		return linkString;
	}

	/**
	 * Prints the WebGraph in tabular form
	 */
	public void printTable() {
		String heading = String.format("%-6s | %-25s | %-10s | %-20s | %-30s", "Index", "URL", "PageRank", "Links",
				"Keywords");
		System.out.println("\n" + heading);
		System.out.println(
				"==========================================================================================================================");
		ListIterator<WebPage> list = pages.listIterator();
		while (list.hasNext()) {
			WebPage webPage = list.next();
			System.out.println(webPage.toString(getLinkString(webPage.getUrl())));
		}
	}

	/**
	 * Prints a table of search results.
	 * 
	 * @param keyword keyword to search for
	 */
	public void printSearchResults(String keyword) {
		sortPages(new RankComparator());
		ListIterator<WebPage> list = pages.listIterator();
		int i = 1;
		boolean hasResults = false;
		String table = "";
		String header = String.format("%-6s | %-10s | %-25s", "Rank", "PageRank", "URL");
		table += header;
		table += "\n===========================================================";
		while (list.hasNext()) {
			WebPage webPage = list.next();
			if (webPage.getKeywords().contains(keyword)) {
				table += String.format("\n%-6s | %-10s | %-25s", i, webPage.getRank(), webPage.getUrl());
				i += 1;
				hasResults = true;
			}
		}
		if (!hasResults) {
			System.out.println("\nNo search results found for the keyword " + keyword + ".");
		} else {
			System.out.println(table);
		}
	}

}
