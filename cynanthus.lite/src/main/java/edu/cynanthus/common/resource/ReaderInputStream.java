package edu.cynanthus.common.resource;

import java.io.*;

/**
 * El tipo Reader input stream.
 */
public final class ReaderInputStream extends InputStream {

    /**
     * El Reader.
     */
    private final Reader reader;
    /**
     * El Pos.
     */
    private final PipedOutputStream pos;
    /**
     * El Pis.
     */
    private final PipedInputStream pis;
    /**
     * El Osw.
     */
    private final OutputStreamWriter osw;

    /**
     * Instancia un nuevo Reader input stream.
     *
     * @param reader el reader
     * @throws IOException el io exception
     */
    public ReaderInputStream(Reader reader) throws IOException {
        this.reader = reader;
        pos = new PipedOutputStream();
        pis = new PipedInputStream(pos);
        osw = new OutputStreamWriter(pos);
    }

    /**
     * Instancia un nuevo Reader input stream.
     *
     * @param reader   el reader
     * @param encoding el encoding
     * @throws IOException el io exception
     */
    public ReaderInputStream(Reader reader, String encoding)
        throws IOException {
        this.reader = reader;
        pos = new PipedOutputStream();
        pis = new PipedInputStream(pos);
        osw = new OutputStreamWriter(pos, encoding);
    }

    /**
     * Read int.
     *
     * @return el int
     * @throws IOException el io exception
     */
    public int read() throws IOException {
        if (pis.available() > 0) {
            return pis.read();
        }

        int c = reader.read();

        if (c == -1) {
            return c;
        }

        osw.write(c);
        osw.flush();
        pos.flush();

        return pis.read();
    }

    /**
     * Read int.
     *
     * @param b   el b
     * @param off el off
     * @param len el len
     * @return el int
     * @throws IOException el io exception
     */
    public int read(byte[] b, int off, int len) throws IOException {
        if (len == 0) {
            return 0;
        }

        int c = read();

        if (c == -1) {
            return -1;
        }

        b[off] = (byte) c;

        int i = 1;

        for (; (i < len) && reader.ready(); i++) {
            c = read();

            if (c == -1) {
                return i;
            }

            b[off + i] = (byte) c;
        }

        return i;
    }

    /**
     * Available int.
     *
     * @return el int
     * @throws IOException el io exception
     */
    public int available() throws IOException {
        int i = pis.available();

        if (i > 0) {
            return i;
        }

        if (reader.ready()) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Close.
     *
     * @throws IOException el io exception
     */
    public void close() throws IOException {
        reader.close();
        osw.close();
        pis.close();
    }
}
