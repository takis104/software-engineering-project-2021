package schoolink;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 * Interface for extracting text content from binary streams.
 */
public interface TextExtractor {
    String[] getContentTypes();
    Reader extractText(InputStream stream, String type, String encoding) throws IOException;
}
