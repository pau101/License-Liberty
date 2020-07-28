package me.paulf.licenseliberty;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.AccessMode;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.spi.FileSystemProvider;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;

public abstract class ForwardingFileSystemProvider extends FileSystemProvider {
    protected abstract FileSystemProvider delegate();

    @Override
    public FileSystem newFileSystem(final Path path, final Map<String, ?> env) throws IOException {
        return this.delegate().newFileSystem(path, env);
    }

    @Override
    public InputStream newInputStream(final Path path, final OpenOption... options) throws IOException {
        return this.delegate().newInputStream(path, options);
    }

    @Override
    public OutputStream newOutputStream(final Path path, final OpenOption... options) throws IOException {
        return this.delegate().newOutputStream(path, options);
    }

    @Override
    public FileChannel newFileChannel(final Path path, final Set<? extends OpenOption> options, final FileAttribute<?>... attrs) throws IOException {
        return this.delegate().newFileChannel(path, options, attrs);
    }

    @Override
    public AsynchronousFileChannel newAsynchronousFileChannel(final Path path, final Set<? extends OpenOption> options, final ExecutorService executor, final FileAttribute<?>... attrs) throws IOException {
        return this.delegate().newAsynchronousFileChannel(path, options, executor, attrs);
    }

    @Override
    public void createSymbolicLink(final Path link, final Path target, final FileAttribute<?>... attrs) throws IOException {
        this.delegate().createSymbolicLink(link, target, attrs);
    }

    @Override
    public void createLink(final Path link, final Path existing) throws IOException {
        this.delegate().createLink(link, existing);
    }

    @Override
    public boolean deleteIfExists(final Path path) throws IOException {
        return this.delegate().deleteIfExists(path);
    }

    @Override
    public Path readSymbolicLink(final Path link) throws IOException {
        return this.delegate().readSymbolicLink(link);
    }

    @Override
    public String getScheme() {
        return this.delegate().getScheme();
    }

    @Override
    public FileSystem newFileSystem(final URI uri, final Map<String, ?> env) throws IOException {
        return this.delegate().newFileSystem(uri, env);
    }

    @Override
    public FileSystem getFileSystem(final URI uri) {
        return this.delegate().getFileSystem(uri);
    }

    @Override
    public Path getPath(final URI uri) {
        return this.delegate().getPath(uri);
    }

    @Override
    public SeekableByteChannel newByteChannel(final Path path, final Set<? extends OpenOption> options, final FileAttribute<?>... attrs) throws IOException {
        return this.delegate().newByteChannel(path, options, attrs);
    }

    @Override
    public DirectoryStream<Path> newDirectoryStream(final Path dir, final DirectoryStream.Filter<? super Path> filter) throws IOException {
        return this.delegate().newDirectoryStream(dir, filter);
    }

    @Override
    public void createDirectory(final Path dir, final FileAttribute<?>... attrs) throws IOException {
        this.delegate().createDirectory(dir, attrs);
    }

    @Override
    public void delete(final Path path) throws IOException {
        this.delegate().delete(path);
    }

    @Override
    public void copy(final Path source, final Path target, final CopyOption... options) throws IOException {
        this.delegate().copy(source, target, options);
    }

    @Override
    public void move(final Path source, final Path target, final CopyOption... options) throws IOException {
        this.delegate().move(source, target, options);
    }

    @Override
    public boolean isSameFile(final Path path, final Path path2) throws IOException {
        return this.delegate().isSameFile(path, path2);
    }

    @Override
    public boolean isHidden(final Path path) throws IOException {
        return this.delegate().isHidden(path);
    }

    @Override
    public FileStore getFileStore(final Path path) throws IOException {
        return this.delegate().getFileStore(path);
    }

    @Override
    public void checkAccess(final Path path, final AccessMode... modes) throws IOException {
        this.delegate().checkAccess(path, modes);
    }

    @Override
    public <V extends FileAttributeView> V getFileAttributeView(final Path path, final Class<V> type, final LinkOption... options) {
        return this.delegate().getFileAttributeView(path, type, options);
    }

    @Override
    public <A extends BasicFileAttributes> A readAttributes(final Path path, final Class<A> type, final LinkOption... options) throws IOException {
        return this.delegate().readAttributes(path, type, options);
    }

    @Override
    public Map<String, Object> readAttributes(final Path path, final String attributes, final LinkOption... options) throws IOException {
        return this.delegate().readAttributes(path, attributes, options);
    }

    @Override
    public void setAttribute(final Path path, final String attribute, final Object value, final LinkOption... options) throws IOException {
        this.delegate().setAttribute(path, attribute, value, options);
    }
}
