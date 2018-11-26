package searchEngine;

import java.util.LinkedList;
import java.util.Scanner;

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
	private static Scanner in = new Scanner(System.in);

	/**
	 * Initializes the web graph.
	 */
	public static void init() {
		web = new WebGraph();
	}

	/**
	 * Prints a list of commands for the user.
	 * 
	 * <dl>
	 * <dt>Postconditions</dt>
	 * <dd>The list of commands has been printed.</dd>
	 * </dl>
	 * 
	 */
	public static void printMenu() {
		System.out.println("\n(AP) - Add a new page to the graph.\r\n" + "(RP) - Remove a page from the graph.\r\n"
				+ "(AL) - Add a link between  pages in the graph.\r\n"
				+ "(RL) - Remove a link between pages in the graph.\r\n" + "(P)  - Print the graph.\r\n"
				+ "(S)  - Search for pages with a keyword.\r\n" + "(Q)  - Quit." + "\n");
	}

	/**
	 * Delegates which function to run depending on the command entered..
	 * 
	 * <dl>
	 * <dt>Postconditions</dt>
	 * <dd>The command has called the appropriate function.</dd>
	 * </dl>
	 */
	public static void commandManager() {
		printMenu();
		System.out.print("Please select an option: ");
		String command = in.nextLine().trim();
		System.out.print("\n");
		switch (command.toUpperCase()) {
		case ("AP"): {
			addPage();
			break;
		}
		case ("RP"): {
			removePage();
			break;
		}
		case ("AL"): {
			addLink();
			break;
		}
		default: {
			System.out.println("That command is not valid. Please try again.");
		}
		}
		commandManager();
	}

	/**
	 * Allows the user to add a page to the graph.
	 */
	public static void addPage() {
		System.out.print("Enter a URL: ");
		String url = in.nextLine();
		System.out.print("Enter keywords (space-separated): ");
		String keywords = in.nextLine();
		String[] keywordArray = keywords.split(" ");
		LinkedList<String> keywordList = new LinkedList<String>();
		for (int i = 0; i < keywordArray.length; i++) {
			keywordList.addLast(keywordArray[i]);
		}
		System.out.println("");
		try {
			web.addPage(url, keywordList);
			System.out.println(url + " successfully added to the WebGraph!");
		} catch (IllegalArgumentException e) {
			System.out.println("That webpage already exists.");
		} catch (FullGraphException e) {
			System.out.println("The web is already full.");
		}
	}

	/**
	 * Allows the user to remove a page from the graph.
	 */
	public static void removePage() {
		System.out.print("Enter a URL: ");
		String url = in.nextLine();
		web.removePage(url);
		System.out.println("\n" + url + " has been removed from the grpah!");
	}

	public static void addLink() {

	}

	/**
	 * Run the simulation.
	 */
	public static void main(String[] args) {
		init();
		commandManager();
	}
}
