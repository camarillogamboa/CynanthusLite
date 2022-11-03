package edu.cynanthus.common.resource;

import java.io.*;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

/**
 * El tipo File acces object.
 *
 * @param <R> el parámetro de tipo
 */
public abstract class FileAccesObject<R> implements ResourceAccesObject<File, R> {

    /**
     * La constante INPUT_STREAM_FAO.
     */
    public static final FileAccesObject<InputStream> INPUT_STREAM_FAO = new FileAccesObject<>() {

        @Override
        void createResource(File file, InputStream inputStream) throws ResourceException {
            try (OutputStream out = new FileOutputStream(file)) {
                inputStream.transferTo(out);
            } catch (IOException ex) {
                throw new ResourceException(ex, ResourceAction.OTHER);
            }
        }

        @Override
        InputStream readResource(File file) throws ResourceException {
            try {
                return new FileInputStream(file);
            } catch (FileNotFoundException ex) {
                throw new ResourceException(ex, ResourceAction.READ);
            }
        }

    };

    /**
     * La constante READER_FAO.
     */
    public static final FileAccesObject<Reader> READER_FAO = new FileAccesObject<>() {

        @Override
        void createResource(File file, Reader reader) throws ResourceException {
            try (Writer writer = new FileWriter(file)) {
                reader.transferTo(writer);
            } catch (IOException ex) {
                throw new ResourceException(ex, ResourceAction.OTHER);
            }
        }

        @Override
        Reader readResource(File file) throws ResourceException {
            return new InputStreamReader(INPUT_STREAM_FAO.readResource(file));
        }

    };

    /**
     * La constante BUFFERED_READER_FAO.
     */
    public static final FileAccesObject<BufferedReader> BUFFERED_READER_FAO = new FileAccesObject<>() {

        @Override
        void createResource(File file, BufferedReader bufferedReader) throws ResourceException {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
                for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
            } catch (IOException ex) {
                throw new ResourceException(ex, ResourceAction.OTHER);
            }
        }

        @Override
        BufferedReader readResource(File file) throws ResourceException {
            return new BufferedReader(READER_FAO.readResource(file));
        }

    };

    /**
     * La constante LINES_FAO.
     */
    public static final FileAccesObject<Iterator<String>> LINES_FAO = new FileAccesObject<>() {

        @Override
        void createResource(File file, Iterator<String> lines) throws ResourceException {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                while (lines.hasNext()) writer.write(lines.next());
            } catch (IOException ex) {
                throw new ResourceException(ex, ResourceAction.OTHER);
            }
        }

        @Override
        Iterator<String> readResource(File file) throws ResourceException {
            return BUFFERED_READER_FAO.readResource(file).lines().iterator();
        }

    };

    /**
     * La constante STRING_FAO.
     */
    public static final FileAccesObject<String> STRING_FAO = new FileAccesObject<>() {

        @Override
        void createResource(File file, String string) throws ResourceException {
            try (Writer writer = new FileWriter(file)) {
                writer.append(string);
            } catch (IOException ex) {
                throw new ResourceException(ex, ResourceAction.OTHER);
            }
        }

        @Override
        String readResource(File file) throws ResourceException {
            try {
                return StreamUtil.toString(READER_FAO.readResource(file));
            } catch (IOException ex) {
                throw new ResourceException(ex, ResourceAction.READ);
            }
        }

    };

    /**
     * Copy.
     *
     * @param sourceFile el source file
     * @param file       el file
     * @throws ResourceException el resource exception
     */
    public static void copy(File sourceFile, File file) throws ResourceException {
        try (InputStream inputStream = new FileInputStream(sourceFile)) {
            INPUT_STREAM_FAO.create(file, inputStream);
        } catch (IOException ex) {
            throw new ResourceException(ex, ResourceAction.OTHER);
        }
    }

    /**
     * Count lines long.
     *
     * @param files el files
     * @return el long
     * @throws ResourceException el resource exception
     */
    public static long countLines(File... files) throws ResourceException {
        AtomicReference<Long> countReference = new AtomicReference<>(0L);
        forEachFile(f -> {
            if (f != null && f.isFile()) try (Reader reader = new FileReader(f)) {
                for (int code = reader.read(); code != -1; code = reader.read())
                    if (code == '\n') countReference.set(countReference.get() + 1);
            } catch (IOException ex) {
                throw new ResourceException(ex, ResourceAction.READ);
            }
        }, files);
        return countReference.get();
    }

    /**
     * Count files long.
     *
     * @param files el files
     * @return el long
     * @throws ResourceException el resource exception
     */
    public static long countFiles(File... files) throws ResourceException {
        AtomicReference<Long> countReference = new AtomicReference<>(0L);
        forEachFile(f -> countReference.set(countReference.get() + 1), files);
        return countReference.get();
    }

    /**
     * Create directory if no exists.
     *
     * @param file el file
     */
    public static void createDirectoryIfNoExists(File file) {
        if (!file.exists()) file.mkdirs();
    }

    /**
     * For each file.
     *
     * @param fileConsumer el file consumer
     * @param files        el files
     * @throws ResourceException el resource exception
     */
    public static void forEachFile(ResourceConsumer<File> fileConsumer, File... files) throws ResourceException {
        if (files != null) for (File file : files) {
            fileConsumer.accept(file);
            forEachFile(fileConsumer, file.listFiles());
        }
    }

    /**
     * Create.
     *
     * @param file     el file
     * @param resource el resource
     * @throws ResourceException el resource exception
     */
    @Override
    public final void create(File file, R resource) throws ResourceException {
        if (file.exists()) throw new ResourceException("El archivo " + file + " ya existe.", ResourceAction.CREATE);
        else try {
            if (file.createNewFile()) createResource(file, resource);
        } catch (IOException ex) {
            throw new ResourceException(ex, ResourceAction.CREATE);
        }
    }

    /**
     * Read r.
     *
     * @param file el file
     * @return el r
     * @throws ResourceException el resource exception
     */
    @Override
    public final R read(File file) throws ResourceException {
        if (file.exists()) if (file.isFile()) return readResource(file);
        else
            throw new ResourceException(
                "El archivo " + file + " no puede ser leido ya que se trata de un directorio.", ResourceAction.READ
            );
        else throw new ResourceException("El archivo " + file + " no existe.", ResourceAction.READ);
    }

    /**
     * Update.
     *
     * @param file     el file
     * @param resource el resource
     * @throws ResourceException el resource exception
     */
    @Override
    public final void update(File file, R resource) throws ResourceException {
        if (file.exists()) createResource(file, resource);
        else throw new ResourceException("El archivo a actualizar " + file + " no existe.", ResourceAction.UPDATE);
    }

    /**
     * Delete.
     *
     * @param file el file
     * @throws ResourceException el resource exception
     */
    @Override
    public final void delete(File file) throws ResourceException {
        if (file.exists()) {
            if (!file.delete())
                throw new ResourceException("El archivo " + file + " no pudo ser eliminado.", ResourceAction.DELETE);
        } else throw new ResourceException("El archivo a eliminar " + file + " no existe", ResourceAction.DELETE);
    }

    /**
     * Create resource.
     *
     * @param file     el file
     * @param resource el resource
     * @throws ResourceException el resource exception
     */
    abstract void createResource(File file, R resource) throws ResourceException;

    /**
     * Read resource r.
     *
     * @param file el file
     * @return el r
     * @throws ResourceException el resource exception
     */
    abstract R readResource(File file) throws ResourceException;

}
