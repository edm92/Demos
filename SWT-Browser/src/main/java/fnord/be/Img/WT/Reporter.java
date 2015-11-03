package fnord.be.Img.WT;

import org.eclipse.swt.*;
import org.eclipse.swt.browser.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import java.io.*;
import java.net.*;


public class Reporter {

	static Browser browser;
	static String[] urls;
	static String[] titles;
	static int index;

	
	public static void main(String[] args) {
		Display display = new Display();
		
		final Shell shell = new Shell(display);
		shell.layout(true, true);
		shell.setMaximized(true);
		
		
		
		shell.setText("jCad Debtor Overview");
		shell.setLayout(new GridLayout());
		Composite compTools = new Composite(shell, SWT.NONE);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		compTools.setLayoutData(data);
		compTools.setLayout(new GridLayout(2, false));
		
		Composite comp = new Composite(shell, SWT.NONE);
		data = new GridData(GridData.FILL_BOTH);
		comp.setLayoutData(data);
		comp.setLayout(new FillLayout());
		
		try {
			browser = new Browser(comp, SWT.NONE);
			new myCustomFunction (browser, "theJavaFunction");
			browser.addProgressListener(new ProgressListener() {
	            public void completed(ProgressEvent event) {
	                System.out.println("Page loaded");
	                try {
	                    Thread.sleep(2000);
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	                // Execute JavaScript in the browser
	                //browser.execute("alert(\"JavaScript, called from Java\");"); 
	            }
	            public void changed(ProgressEvent event) {
	            }
	        });
		} catch (SWTError e) {
			MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
			messageBox.setMessage("Closing application. The main app could not be initialized.");
			messageBox.setText("Fatal error - application terminated");
			messageBox.open();
			System.exit(-1);
		}
		
		final LocationListener locationListener = new LocationListener() {
			public void changed(LocationEvent event) {
				Browser browser = (Browser)event.widget;
				// Do something when we change pages
			}
			public void changing(LocationEvent event) {
			}
		};
		
		String folder = System.getProperty("user.dir") + "/core";
		File file = new File(folder);
		File[] files = file.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".html") || name.endsWith(".htm");
			}
		});
		if (files.length == 0) return;
		urls = new String[files.length];
		titles = new String[files.length];
		index = 0;
		int toUse = 0;
		for (int i = 0; i < files.length; i++) {
			try {
				
				String url = files[i].toURL().toString();
				System.out.println("found " + url );
				if(url.contains("index.html")) toUse = i;
				urls[i] = url;
			} catch (MalformedURLException ex) {}
		}
		shell.setText("Building index");
		//browser.addTitleListener(tocTitleListener);
		//browser.addProgressListener(tocProgressListener);
		if (urls.length > 0) browser.setUrl(urls[toUse]);
		new myCustomFunction (browser, "theJavaFunction");
		/*
		browser.dispose();
		browser = new Browser(comp, SWT.NONE);
		DocumentationViewer.browser = browser;
		comp.layout(true);
		browser.setUrl(urls[0]);
		browser.addLocationListener(locationListener);
		*/
		shell.setText("JCad Reporter");
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
	}
	

}


//Called by JavaScript
class myCustomFunction extends BrowserFunction {
	Browser pBrowser = null;
    
	myCustomFunction (Browser browser, String name) {
       super (browser, name);
       pBrowser = browser;
   }
   public Object function (Object[] arguments) {
	   String addition = "";
   	   if(arguments.length > 1){
   		   addition += (String)arguments[0].toString();
   		   addition += " -- " + (String)arguments[1].toString();
   	   }
	   
       System.out.println("Terminal from rich canvas " +addition);
       return null;
   }
}
