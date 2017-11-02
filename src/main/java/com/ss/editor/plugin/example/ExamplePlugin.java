package com.ss.editor.plugin.example;

import com.ss.editor.annotation.FXThread;
import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.file.converter.FileConverterRegistry;
import com.ss.editor.plugin.EditorPlugin;
import com.ss.editor.plugin.example.converter.ExampleFileConverter;
import com.ss.editor.plugin.example.creator.ExampleFileCreator;
import com.ss.editor.plugin.example.editor.ExampleTextFileEditor;
import com.ss.editor.ui.component.asset.tree.AssetTreeContextMenuFillerRegistry;
import com.ss.editor.ui.component.creator.FileCreatorRegistry;
import com.ss.editor.ui.component.editor.EditorRegistry;
import com.ss.editor.ui.control.property.builder.PropertyBuilderRegistry;
import com.ss.editor.ui.control.tree.node.TreeNodeFactoryRegistry;
import com.ss.rlib.plugin.PluginContainer;
import com.ss.rlib.plugin.PluginSystem;
import com.ss.rlib.plugin.annotation.PluginDescription;
import javafx.scene.control.MenuItem;
import org.jetbrains.annotations.NotNull;

/**
 * The implementation of an editor plugin.
 *
 * @author JavaSaBr
 */
@PluginDescription(
        id = "com.ss.editor.plugin.example",
        version = "0.1",
        minAppVersion = "1.3.0",
        name = "Example plugin",
        description = "Example plugin"
)
public class ExamplePlugin extends EditorPlugin {

    public ExamplePlugin(@NotNull final PluginContainer pluginContainer) {
        super(pluginContainer);
    }

    @Override
    @FromAnyThread
    public void register(@NotNull final AssetTreeContextMenuFillerRegistry registry) {
        super.register(registry);

        registry.registerSingle((element, items, actionTester) -> {

            final MenuItem item = new MenuItem("Test single action");
            item.setOnAction(event -> System.out.println("To do something"));

            items.add(item);
        });
        registry.registerMulti((element, items, actionTester) -> {

            final MenuItem item = new MenuItem("Test multi action");
            item.setOnAction(event -> System.out.println("To do something"));

            items.add(item);
        });
    }

    @Override
    @FromAnyThread
    public void register(@NotNull final FileConverterRegistry registry) {
        super.register(registry);
        registry.register(ExampleFileConverter.DESCRIPTION);
    }

    @Override
    @FromAnyThread
    public void register(@NotNull final FileCreatorRegistry registry) {
        super.register(registry);
        registry.register(ExampleFileCreator.DESCRIPTION);
    }

    @Override
    @FromAnyThread
    public void register(@NotNull final EditorRegistry registry) {
        super.register(registry);
        registry.register(ExampleTextFileEditor.DESCRIPTION);
    }

    @Override
    @FromAnyThread
    public void register(@NotNull final TreeNodeFactoryRegistry registry) {
        super.register(registry);
    }

    @Override
    public void register(@NotNull final PropertyBuilderRegistry registry) {
        super.register(registry);
    }

    @Override
    @FXThread
    public void onFinishLoading(@NotNull final PluginSystem pluginSystem) {
        super.onFinishLoading(pluginSystem);
    }
}
