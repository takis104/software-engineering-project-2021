package schoolink;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class CustomUrlHandler extends URLStreamHandler {
    @Override
    protected URLConnection openConnection(URL u) throws IOException {
      return new CustomHostServicesUrlConnection(u, EditorFx.instance.getHostServices());
    }
}
