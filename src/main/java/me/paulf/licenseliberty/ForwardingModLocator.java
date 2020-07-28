package me.paulf.licenseliberty;

import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.forgespi.locating.IModLocator;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.jar.Manifest;

public abstract class ForwardingModLocator implements IModLocator {
    protected abstract IModLocator delegate();

    @Override
    public List<IModFile> scanMods() {
        return this.delegate().scanMods();
    }

    @Override
    public String name() {
        return this.delegate().name();
    }

    @Override
    public Path findPath(final IModFile modFile, final String... path) {
        return this.delegate().findPath(modFile, path);
    }

    @Override
    public void scanFile(final IModFile modFile, final Consumer<Path> pathConsumer) {
        this.delegate().scanFile(modFile, pathConsumer);
    }

    @Override
    public Optional<Manifest> findManifest(final Path file) {
        return this.delegate().findManifest(file);
    }

    @Override
    public void initArguments(final Map<String, ?> arguments) {
        this.delegate().initArguments(arguments);
    }

    @Override
    public boolean isValid(final IModFile modFile) {
        return this.delegate().isValid(modFile);
    }
}
