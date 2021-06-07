package schoolink;

/**
 * Base class for text extractor implementations.
 */
public abstract class AbstractTextExtractor implements TextExtractor {

    /**
     * The supported content types by this text extractor.
     */
    private final String[] contentTypes;

    /**
     * @param contentTypes the supported content types by this text extractor.
     */
    public AbstractTextExtractor(String[] contentTypes) {
        this.contentTypes = new String[contentTypes.length];
        System.arraycopy(contentTypes, 0, this.contentTypes, 0, contentTypes.length);
    }

    /**
     * @inheritDoc
     */
    public String[] getContentTypes() {
        return contentTypes;
    }
}
