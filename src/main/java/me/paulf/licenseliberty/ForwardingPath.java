package me.paulf.licenseliberty;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public abstract class ForwardingPath implements Path {
    protected abstract Path delegate();

    @Override
    public void forEach(final Consumer<? super Path> action) {
        this.delegate().forEach(action);
    }

    @Override
    public Spliterator<Path> spliterator() {
        return this.delegate().spliterator();
    }

    @Override
    public FileSystem getFileSystem() {
        return this.delegate().getFileSystem();
    }

    @Override
    public boolean isAbsolute() {
        return this.delegate().isAbsolute();
    }

    @Override
    public Path getRoot() {
        return this.delegate().getRoot();
    }

    @Override
    public Path getFileName() {
        return this.delegate().getFileName();
    }

    @Override
    public Path getParent() {
        return this.delegate().getParent();
    }

    @Override
    public int getNameCount() {
        return this.delegate().getNameCount();
    }

    @Override
    public Path getName(final int index) {
        return this.delegate().getName(index);
    }

    @Override
    public Path subpath(final int beginIndex, final int endIndex) {
        return this.delegate().subpath(beginIndex, endIndex);
    }

    @Override
    public boolean startsWith(final Path other) {
        return this.delegate().startsWith(other);
    }

    @Override
    public boolean startsWith(final String other) {
        return this.delegate().startsWith(other);
    }

    @Override
    public boolean endsWith(final Path other) {
        return this.delegate().endsWith(other);
    }

    @Override
    public boolean endsWith(final String other) {
        return this.delegate().endsWith(other);
    }

    @Override
    public Path normalize() {
        return this.delegate().normalize();
    }

    @Override
    public Path resolve(final Path other) {
        return this.delegate().resolve(other);
    }

    @Override
    public Path resolve(final String other) {
        return this.delegate().resolve(other);
    }

    @Override
    public Path resolveSibling(final Path other) {
        return this.delegate().resolveSibling(other);
    }

    @Override
    public Path resolveSibling(final String other) {
        return this.delegate().resolveSibling(other);
    }

    @Override
    public Path relativize(final Path other) {
        return this.delegate().relativize(other);
    }

    @Override
    public URI toUri() {
        return this.delegate().toUri();
    }

    @Override
    public Path toAbsolutePath() {
        return this.delegate().toAbsolutePath();
    }

    @Override
    public Path toRealPath(final LinkOption... options) throws IOException {
        return this.delegate().toRealPath();
    }

    @Override
    public File toFile() {
        return this.delegate().toFile();
    }

    @Override
    public WatchKey register(final WatchService watcher, final WatchEvent.Kind<?>[] events, final WatchEvent.Modifier... modifiers) throws IOException {
        return this.delegate().register(watcher, events, modifiers);
    }

    @Override
    public WatchKey register(final WatchService watcher, final WatchEvent.Kind<?>... events) throws IOException {
        return this.delegate().register(watcher, events);
    }

    @Override
    public Iterator<Path> iterator() {
        return this.delegate().iterator();
    }

    @Override
    public int compareTo(final Path other) {
        throw new UnsupportedOperationException();
    }
}
