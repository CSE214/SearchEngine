package searchEngine;

import java.util.Comparator;

/**
 * Compares two WebPages by their rank in descending order.
 *
 * @author Sayan Sivakumaran e-mail: sayan.sivakumaran@stonybrook.edu Stony
 *         Brook ID: 110261379
 **/
public class RankComparator implements Comparator<WebPage> {
	@Override
	public int compare(WebPage w1, WebPage w2) {
		if (w1.getRank() == w2.getRank())
			return 0;
		else if (w1.getRank() > w2.getRank())
			return -1;
		else
			return 1;
	}
}
