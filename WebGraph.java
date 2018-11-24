package searchEngine;

import java.util.LinkedList;
import java.util.ListIterator;

public class WebGraph {
	private static final int MAX_PAGES = 40;
	private int[][] edges;
	private LinkedList<WebPage> pages;
	private int pageCount;

	/**
	 * Returns an instance of WebGraph
	 */
	public WebGraph(int maxPages) {
		this.edges = new int[MAX_PAGES][MAX_PAGES];
		this.pages = new LinkedList<WebPage>();
		this.pageCount = 0;
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
	 */
	public void addPage(String url, LinkedList<String> keywords) throws IllegalArgumentException {
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
	 * @throws IllegalArgumentException If url does not exist in the graph or is
	 *                                  null
	 */
	public void removePage(String url) throws IllegalArgumentException {
		// Check for null
		if (url == null) {
			throw new IllegalArgumentException("The URL should not be null.");
		}
		// Find index if exists
		int removeIndex = -1;
		ListIterator<WebPage> list = pages.listIterator();
		while (list.hasNext()) {
			WebPage page = list.next();
			if (page.getUrl().equals(url)) {
				removeIndex = pages.indexOf(page);
				break;
			}
		}
		if (removeIndex == -1) {
			throw new IllegalArgumentException("That URL does not exist in the graph.");
		}
		// Remove page
		pages.remove(removeIndex);
		pageCount -= 1;
		// Update indices
		list = pages.listIterator(removeIndex);
		while (list.hasNext()) {
			WebPage page = list.next();
			page.setIndex(page.getIndex() - 1);
		}
	}

}
