package me.paulf.licenseliberty;

import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.core.io.ConfigWriter;
import com.google.common.collect.ImmutableList;
import cpw.mods.modlauncher.api.IEnvironment;
import cpw.mods.modlauncher.api.ITransformationService;
import cpw.mods.modlauncher.api.ITransformer;
import net.minecraftforge.fml.loading.moddiscovery.ModFileParser;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.forgespi.locating.IModLocator;
import net.minecraftforge.forgespi.locating.ModFileFactory;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.file.AccessMode;
import java.nio.file.FileSystem;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.spi.FileSystemProvider;
import java.util.List;
import java.util.Set;

public class LibertyTransformationService implements ITransformationService {
    @Override
    public String name() {
        return "licenseliberty";
    }

    @Override
    public void initialize(final IEnvironment environment) {
        //noinspection UnnecessaryLocalVariable
        final ModFileFactory factory = ModFileFactory.FACTORY;
        final Field f = FieldUtils.getDeclaredField(ModFileFactory.class, "FACTORY", true);
        FieldUtils.removeFinalModifier(f);
        try {
            FieldUtils.writeStaticField(f, new ModFileFactory() {
                @Override
                public IModFile build(final Path path, final IModLocator locator, final ModFileInfoParser parser) {
                    return factory.build(path, new ModsTomlSubstitutor(locator), parser);
                }
            });
        } catch (final IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void beginScanning(final IEnvironment environment) {
    }

    @Override
    public void onLoad(final IEnvironment env, final Set<String> otherServices) {
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<ITransformer> transformers() {
        return ImmutableList.of();
    }

    private static class ModsTomlSubstitutor extends ForwardingModLocator {
        private final IModLocator delegate;

        public ModsTomlSubstitutor(final IModLocator delegate) {
            this.delegate = delegate;
        }

        @Override
        protected IModLocator delegate() {
            return this.delegate;
        }

        static class PrivateSecurityManager extends SecurityManager {
            @Override
            public Class<?>[] getClassContext() {
                return super.getClassContext();
            }
        }

        @Override
        public Path findPath(final IModFile modFile, final String... path) {
            final Path p = super.findPath(modFile, path);
            //noinspection StringEquality
            if (path.length == 2 && path[0] == "META-INF" && path[1] == "mods.toml" && ModFileParser.class.equals(new PrivateSecurityManager().getClassContext()[2])) {
                return new ForwardingPath() {
                    @Override
                    protected Path delegate() {
                        throw new UnsupportedOperationException();
                    }

                    @Override
                    public Path getFileName() {
                        return p.getFileName();
                    }

                    @Override
                    public FileSystem getFileSystem() {
                        final FileSystem fs = p.getFileSystem();
                        return new ForwardingFileSystem() {
                            @Override
                            protected FileSystem delegate() {
                                throw new UnsupportedOperationException();
                            }

                            @Override
                            public FileSystemProvider provider() {
                                final FileSystemProvider provider = fs.provider();
                                return new ForwardingFileSystemProvider() {
                                    @Override
                                    protected FileSystemProvider delegate() {
                                        throw new UnsupportedOperationException();
                                    }

                                    @Override
                                    public void checkAccess(final Path path, final AccessMode... modes) throws IOException {
                                    }

                                    @Override
                                    public InputStream newInputStream(final Path path, final OpenOption... options) throws IOException {
                                        try (final FileConfig fileConfig = FileConfig.of(p)) {
                                            fileConfig.load();
                                            if (fileConfig.get("license") == null) {
                                                fileConfig.set("license", "Hello, World!");
                                                final ConfigWriter writer = fileConfig.configFormat().createWriter();
                                                final byte[] bytes;
                                                try (final ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                                                    writer.write(fileConfig, out);
                                                    bytes = out.toByteArray();
                                                }
                                                return new ByteArrayInputStream(bytes);
                                            }
                                        }
                                        return provider.newInputStream(p, options);
                                    }
                                };
                            }
                        };
                    }
                };
            }
            return p;
        }
    }
}
