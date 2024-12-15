package uk.co.bigsoft.playright_bug_onclose;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class Main {

	public static void main(String[] args) {
		mainWithBrowserContext(args);
		mainWithoutBrowserContext(args);
	}

	public static void mainWithBrowserContext(String[] args) {

		log("Test With BrowserContext");
		log("");

		Playwright playwright = Playwright.create();
		LaunchOptions lo = new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50);

		// BrowserType bt = playwright.firefox();
		BrowserType bt = playwright.chromium();

		Browser browser = bt.launch(lo);
		BrowserContext browserContext = browser.newContext();
		Page page = browserContext.newPage();

		browserContext.onClose(it -> { //
			log("***************************** BrowserContext onDisconnected: " + it); //
		});
		browserContext.onPage(it -> { //
			log("***************************** BrowserContext onPage: " + it); //
		});
		browser.onDisconnected(it -> { //
			log("***************************** Browser onDisconnected: " + it); //
		});
		page.onClose(it -> { //
			log("***************************** Page onClose: " + it); //
		});
		page.onCrash(it -> { //
			log("***************************** Page onCrash: " + it); //
		});
		page.onPageError(it -> { //
			log("***************************** Page onPageError: " + it); //
		});

		for (int i = 10; i != 0; --i) {
			try {
				log("Close window, you have " + i + " seconds to comply! Closed=" + page.isClosed());
				Thread.sleep(1_000);
			} catch (InterruptedException e) {
				log("Interrupted!");
			}
		}
		log("Calling page.close()");
//		page.close();
		browserContext.close();
		log("Close Playright");
		playwright.close();
		log("End");

	}

	public static void mainWithoutBrowserContext(String[] args) {

		log("Test With BrowserContext");
		log("");

		Playwright playwright = Playwright.create();
		LaunchOptions lo = new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50);
		BrowserType bt = playwright.firefox();
		Browser browser = bt.launch(lo);
		Page page = browser.newPage();

		browser.onDisconnected(it -> { //
			log("***************************** Browser onDisconnected: " + it); //
		});
		page.onClose(it -> { //
			log("***************************** Page onClose: " + it); //
		});
		page.onCrash(it -> { //
			log("***************************** Page onCrash: " + it); //
		});
		page.onPageError(it -> { //
			log("***************************** Page onPageError: " + it); //
		});

		for (int i = 10; i != 0; --i) {
			try {
				log("Close window, you have " + i + " seconds to comply! Closed=" + page.isClosed());
				Thread.sleep(1_000);
			} catch (InterruptedException e) {
				log("Interrupted!");
			}
		}
		log("Calling page.close()");
		page.close();
		log("Close Playright");
		playwright.close();
		log("End");

	}

	private static void log(String m) {
		System.out.println(m);
	}
}
