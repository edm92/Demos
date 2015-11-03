package fnord.be.Img.WT;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class FullScreenSwtBrowser {

	private JFrame bFrame;
	private JPanel embedPanel;

	

	// SWT Components

	private Display display = new Display();
	private Shell shell;
	private Canvas embedCanvas;
	private Browser swtBrowser;

	public void init(){
		bFrame = new JFrame();
		bFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		bFrame.setSize(800, 600);
		bFrame.setUndecorated(true);
//		newDisplay = new DisplayMode(800, 600, 32, 60);
//		newDisplay = new DisplayMode(800, 600, 16, 30);


		embedCanvas = new Canvas();
		embedCanvas.setPreferredSize(new Dimension(800, 600));

		embedPanel = new JPanel();
		embedPanel.add(embedCanvas);


		JPanel activityPanel = new JPanel();
		activityPanel.setBackground(Color.GREEN);
		bFrame.pack();

	}

	public void initBrowser(final String url) {

		display.syncExec(new Runnable() {

			public void run() {

				shell = SWT_AWT.new_Shell(display, embedCanvas);
				shell.setLayout(new GridLayout());

				swtBrowser = new Browser(shell, SWT.NONE);
				swtBrowser.setLayoutData(new GridData(GridData.FILL_BOTH));
				shell.setSize(embedCanvas.getWidth(), embedCanvas.getHeight());
				shell.setLayoutData(new GridData(GridData.FILL_BOTH));
				swtBrowser.setUrl(url);
				shell.open();
				shell.forceFocus();
			}
		});
	}

	public void start(){
		bFrame.add(embedPanel);
		
	
		
		
		String url = "http://www.google.com";

		initBrowser(url);
		

		embedPanel.setVisible(true);
		bFrame.setVisible(true);
		bFrame.pack();
		bFrame.repaint();
		
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FullScreenSwtBrowser  fssb = new FullScreenSwtBrowser();
		fssb.init();
		fssb.start();
	}

}