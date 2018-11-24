package searchEngine;

import java.util.LinkedList;

/**
 * <code>WebPage</code> class represents a hyperlinked document on the web.
 *
 * @author Sayan Sivakumaran e-mail: sayan.sivakumaran@stonybrook.edu Stony
 *         Brook ID: 110261379
 **/
public class WebPage {
	private String url;
	private int index;
	private int rank;
	private LinkedList<String> keywords;

	/**
	 * @return The url of this instance
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url The new url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return The index of this instance
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index The new index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return The rank of this instance
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * @param rank The new rank to set
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}

	/**
	 * @return The keywords of this instance
	 */
	public LinkedList<String> getKeywords() {
		return keywords;
	}

	/**
	 * @param keywords The new keywords to set
	 */
	public void setKeywords(LinkedList<String> keywords) {
		this.keywords = keywords;
	}

	/**
	 * Returns an instance of WebPage
	 * 
	 * @param url      The url of the page
	 * @param index    The index of the page in WebGraph's list
	 * @param keywords The keywords of the page
	 */
	public WebPage(String url, LinkedList<String> keywords, int index) {
		this.url = url;
		this.index = index;
		this.keywords = keywords;
	}

}
