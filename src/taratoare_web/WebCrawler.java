package taratoare_web;
import java.util.Set;
import java.util.StringTokenizer;
import java.awt.List;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.omg.CORBA.portable.InputStream;

public class WebCrawler {
	
	//Hash care contine toate link-urile gasite
	private HashSet<String> links;
	public static final int MAX_PAGES = 100;
	String cale = "D:\\Scoala\\workspace_riw\\WebCrawler";
	
	public static final String DISALLOW = "Disallow:";
	private static final boolean DEBUG = false;
	//private LinkedList<String> linkss = new LinkedList<String>();
	
	//scriitorul catre fisierele locale
	PrintWriter scriitor;
	
	//Initializare HashSet
	public WebCrawler(){
		links = new HashSet<String>();
	}
	// Add urls to frontier from url seed and the rest
	public void LinksExtraction(String url) {
		// check for new url's and max pages to traverse
		if(!links.contains(url) && links.size() < MAX_PAGES) {
			try {
				
				//fetch html source code
				Document document = Jsoup.connect(url).userAgent("RIWEB_CRAWLER").get();
				//parse links from page
				Elements linksOnPage = document.select("a[href]");
				//add to frontier
				if(links.add(url)) {
					System.out.println(url);
				}
				// recursive way to add other links from the initial page
				for (Element page : linksOnPage) {
					if (!(page.attr("abs:href").contains("#")))
						LinksExtraction("http://riweb.tibeica.com/crawl/" + page.attr("href"));
						//LinksExtraction(page.attr("abs:href"));
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("For '" + url + "': " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	//Descarcare pagina (continutul din body)
	public String getContentFromPage(String url) throws IOException {
		Document articol =  Jsoup.connect(url).get();
		return articol.body().text();
	}
	//Creare fisiere care corespund fiecarui articol gasit
	public void writetoFile(String text, String cale) throws FileNotFoundException, UnsupportedEncodingException {
		scriitor = new PrintWriter(cale, "UTF-8");
		scriitor.print(text);
		scriitor.close();
	}
	//dau cheie la senilata
	public void cheielaCrawler(String url) throws IOException {
		int contor = 0;
		//extragere legaturi de la url-ul dat
		LinksExtraction(url);
		//parcurgere fiecare link
		for(String link : links) {
			String textul = getContentFromPage(link);
			writetoFile(textul , cale + "/riweb.tibeica.com/articol_indexat_" + contor + ".txt");
			contor++;
		}
	}
	
	// Verifying robots.txt
		public boolean isSafeRobot(URL url) {

			String strRobot = "https://riweb.tibeica.com/robots.txt";
			URL urlRobot;
			try {
				urlRobot = new URL(strRobot);
			} catch (MalformedURLException e) {

				return false;
			}

			if (DEBUG)
				System.out.println("Checking robot protocol " + urlRobot.toString());
			String strCommands;
			try {
				InputStream urlRobotStream = (InputStream) urlRobot.openStream();

				// read in entire file
				byte b[] = new byte[1000];
				int numRead = urlRobotStream.read(b);
				strCommands = new String(b, 0, numRead);
				while (numRead != -1) {
					numRead = urlRobotStream.read(b);
					if (numRead != -1) {
						String newCommands = new String(b, 0, numRead);
						strCommands += newCommands;
					}
				}
				urlRobotStream.close();
			} catch (IOException e) {
				// if there is no robots.txt file, it is OK to search
				return true;
			}
			if (DEBUG)
				System.out.println(strCommands);

			// assume that this robots.txt refers to us and
			// search for "Disallow:" commands.
			String strURL = urlRobot.getFile();
			int index = 0;
			while ((index = strCommands.indexOf(DISALLOW, index)) != -1) {
				index += DISALLOW.length();
				String strPath = strCommands.substring(index);
				StringTokenizer st = new StringTokenizer(strPath);

				if (!st.hasMoreTokens())
					break;

				String strBadPath = st.nextToken();
				if (strURL.indexOf(strBadPath) == 0)
					return false;
			}

			return true;
		}
}
