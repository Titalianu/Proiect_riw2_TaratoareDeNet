package taratoare_web;

import java.io.IOException;
import java.net.URL;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		WebCrawler cw = new WebCrawler();
		String urlString = "http://riweb.tibeica.com/crawl/";
		URL ureleu = new URL(urlString);
		Director_manager.createDir(urlString);
		cw.cheielaCrawler(urlString);
		Client_HTTP.getHttpClient(urlString);
	}

}
