package me.paulf.licenseliberty;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.WatchService;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.nio.file.spi.FileSystemProvider;
import java.util.Set;

public abstract class ForwardingFileSystem extends FileSystem {
    protected abstract FileSystem delegate();

    @Override
    public FileSystemProvider provider() {
        return this.delegate().provider();
    }

    @Override
    public void close() throws IOException {
        this.delegate().close();
    }

    @Override
    public boolean isOpen() {
        return this.delegate().isOpen();
    }

    @Override
    public boolean isReadOnly() {
        return this.delegate().isReadOnly();
    }

    @Override
    public String getSeparator() {
        return this.delegate().getSeparator();
    }

    @Override
    public Iterable<Path> getRootDirectories() {
        return this.delegate().getRootDirectories();
    }

    @Override
    public Iterable<FileStore> getFileStores() {
        return this.delegate().getFileStores();
    }

    @Override
    public Set<String> supportedFileAttributeViews() {
        return this.delegate().supportedFileAttributeViews();
    }

    @Override
    public Path getPath(final String first, final String... more) {
        return this.delegate().getPath(first, more);
    }

    @Override
    public PathMatcher getPathMatcher(final String syntaxAndPattern) {
        return this.delegate().getPathMatcher(syntaxAndPattern);
    }

    @Override
    public UserPrincipalLookupService getUserPrincipalLookupService() {
        return this.delegate().getUserPrincipalLookupService();
    }

    @Override
    public WatchService newWatchService() throws IOException {
        return this.delegate().newWatchService();
    }
}
