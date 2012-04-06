public class XmlKBEntityReader extends LineNumberReader implements Iterator
{
    boolean inEntity = false
    boolean endEntitySeen = false

    String currentLine = ''

    int entityLineNumber = 0
    LineNumberReader reader

    XmlKBEntityReader(LineNumberReader reader)
    {
        super(reader)
        this.reader = reader
    }

    // Read the next line in this entity.
    // Return null if we've reached the end of the section or the end of the file.
    public String readLine()
    {
        if (inEntity) {
            // The currentLine will be null if our previous readLine hit EOF.
            if (!endEntitySeen && (currentLine != null)) {
                String thisLine = currentLine

                endEntitySeen = thisLine.equalsIgnoreCase('</entity>')

                currentLine = reader.readLine()?.trim()

                return thisLine
            }

            inEntity = false
        }

        return null
    }

    /**
     * We only close if the underlying reader is at EOF
     */
    @Override
    int read(char[] cbuf, int off, int len)
    {
        throw new IllegalStateException()
    }

    public void close()
    {
        if (!hasNext()) reader.close()
    }

    @Override
    boolean hasNext()
    {
        if (currentLine != null) {
            entityLineNumber = reader.lineNumber
            while (!(currentLine.startsWith('<entity') || currentLine.startsWith('<ENTITY'))) {

                currentLine = reader.readLine()?.trim()

                if (currentLine == null) break
            }
        }

        currentLine != null
    }

    @Override
    XmlKBEntityReader next()
    {
        if (hasNext()) {
            inEntity = true
            endEntitySeen = false
        }

        this
    }

    @Override
    void remove()
    {
        throw new UnsupportedOperationException()
    }
}
