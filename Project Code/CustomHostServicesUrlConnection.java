package schoolink;

import java.awt.Desktop;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;

import javafx.application.HostServices;

public class CustomHostServicesUrlConnection extends HttpURLConnection {

    private URL urlToOpen;
    protected CustomHostServicesUrlConnection(URL u, HostServices hostServices) {
      super(u);
      this.urlToOpen= u;
      System.out.println("HERE");
    }

    @Override
    public void disconnect() {
      // do nothing
    }

    @Override
    public boolean usingProxy() {
      return false;
    }

    @Override
    public void connect() throws IOException {
    	try {
			Desktop.getDesktop().browse(urlToOpen.toURI());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public InputStream getInputStream() throws IOException {
      return new InputStream() {

        @Override
        public int read() throws IOException {
          return 0;
        }
      };
    }

  }

