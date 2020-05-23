package taratoare_web;
import java.util.Set;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebCrawler {
	
	//Hash care contine toate link-urile gasite
	private HashSet<String> links;
	public static final int MAX_PAGES = 100;
	String cale = "D:\\Scoala\\workspace_riw\\WebCrawler";
	
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
}
