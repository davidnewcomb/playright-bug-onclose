package uk.co.bigsoft.playright_bug_onclose;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class Main {

	public static void main(String[] args) {

		log("Test showing BrowserContext.close() is not called when the browser disapears.");
		log("");

		Playwright playwright = Playwright.create();
		LaunchOptions lo = new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50);

		// BrowserType bt = playwright.firefox();
		BrowserType bt = playwright.chromium();

		Browser browser = bt.launch(lo);
		browser.onDisconnected(it -> { //
			log("***************************** Browser onDisconnected: " + it); //
		});

		BrowserContext browserContext = browser.newContext();
		browserContext.onClose(it -> { //
			log("***************************** BrowserContext onDisconnected: " + it); //
		});
		browserContext.onPage(it -> { //
			log("***************************** BrowserContext onPage: " + it); //
		});

		Page page = browserContext.newPage();
		page.onClose(it -> { //
			log("***************************** Page onClose: " + it); //
		});

		for (int i = 10; i != 0; --i) {
			try {
				log("Close window, you have " + i + " seconds to comply! Connected/Closed=" + browser.isConnected()
						+ "/" + page.isClosed());
				Thread.sleep(1_000);
			} catch (InterruptedException e) {
				log("Interrupted!");
			}
		}
		log("I am now calling browserContext.close()");
		browserContext.close();

		log("Close Playright");
		playwright.close();

		log("End");

	}

	private static void log(String m) {
		System.out.println(m);
	}
}
