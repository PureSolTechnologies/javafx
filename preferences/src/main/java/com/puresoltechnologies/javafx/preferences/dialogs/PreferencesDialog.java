package com.puresoltechnologies.javafx.preferences.dialogs;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import com.puresoltechnologies.javafx.preferences.Preferences;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class PreferencesDialog extends Dialog<Void> {

    private static final Image iconSmall;
    private static final Image iconBig;
    static {
	try {
	    iconSmall = ResourceUtils.getImage(Preferences.class, "icons/FatCow_Icons16x16/setting_tools.png");
	    iconBig = ResourceUtils.getImage(Preferences.class, "icons/FatCow_Icons32x32/setting_tools.png");
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    private final TreeView<PreferencesPageNode> treeView;
    private final BorderPane preferencesPane;
    private final ImageView imageView;
    private final Label name;
    private final Button restoreDefaultsButton;
    private final Button applyButton;
    private PreferencesPage currentPreferencesPage;

    public PreferencesDialog() {
	setTitle("Preferences");
	setHeaderText("Preferences of Trader.");
	setGraphic(new ImageView(iconBig));
	DialogPane dialogPane = getDialogPane();
	Stage stage = (Stage) dialogPane.getScene().getWindow();
	stage.getIcons().addAll(iconSmall, iconBig);
	setResizable(true);

	SplitPane splitPane = new SplitPane();

	treeView = new TreeView<>();
	preferencesPane = new BorderPane();
	preferencesPane.setPadding(new Insets(10, 10, 10, 10));
	splitPane.getItems().addAll(treeView, preferencesPane);
	splitPane.setDividerPosition(0, 0.33);

	HBox headerBox = new HBox();
	headerBox.setAlignment(Pos.CENTER_LEFT);
	headerBox.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, new CornerRadii(0.0),
		new BorderWidths(0, 0, 1.0, 0, false, false, false, false), new Insets(5, 5, 5, 5))));
	headerBox.setPadding(new Insets(0, 0, 10, 0));
	imageView = new ImageView();
	name = new Label();
	name.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, Font.getDefault().getSize() * 1.33));
	headerBox.getChildren().addAll(imageView, name);
	preferencesPane.setTop(headerBox);

	HBox buttonBox = new HBox(10.0);
	buttonBox.setAlignment(Pos.CENTER_RIGHT);
	buttonBox.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, new CornerRadii(0.0),
		new BorderWidths(1.0, 0, 0, 0, false, false, false, false), new Insets(5, 5, 5, 5))));
	buttonBox.setPadding(new Insets(10, 0, 0, 0));
	restoreDefaultsButton = new Button("Restore Defaults");
	restoreDefaultsButton.setDisable(true);
	restoreDefaultsButton.setOnAction(event -> restoreDefaults());
	applyButton = new Button("Apply");
	applyButton.setDisable(true);
	applyButton.setOnAction(event -> applyChanges());
	buttonBox.getChildren().addAll(restoreDefaultsButton, applyButton);
	preferencesPane.setBottom(buttonBox);

	dialogPane.setContent(splitPane);

	ButtonType buttonTypeCancel = new ButtonType("Close", ButtonData.CANCEL_CLOSE);
	ObservableList<ButtonType> buttonTypes = dialogPane.getButtonTypes();
	buttonTypes.addAll(buttonTypeCancel);

	initializeTreeView();
    }

    private void initializeTreeView() {
	TreeItem<PreferencesPageNode> rootItem = new TreeItem<>(new PreferencesPageNode("Root"));
	rootItem.setExpanded(true);

	treeView.setRoot(rootItem);
	treeView.setShowRoot(false);
	treeView.setEditable(false);
	treeView.setCellFactory((TreeView<PreferencesPageNode> p) -> new TreeCell<>() {
	    @Override
	    public void updateItem(PreferencesPageNode item, boolean empty) {
		super.updateItem(item, empty);
		if (empty) {
		    setText(null);
		    setGraphic(null);
		} else {
		    setText(item.getNodeName());
		    PreferencesPage preferencesPage = item.getPreferencesPage();
		    if (preferencesPage != null) {
			setTooltip(new Tooltip(preferencesPage.getName()));
			Image image = preferencesPage.getImage();
			if (image != null) {
			    setGraphic(new ImageView(image));
			}
		    }
		}
	    }
	});
	treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
	    PreferencesPage preferencesPage = newValue.getValue().getPreferencesPage();
	    openPreferencesPage(preferencesPage);
	});

	ServiceLoader<PreferencesPage> preferencesPages = ServiceLoader.load(PreferencesPage.class);
	Iterator<PreferencesPage> iterator = preferencesPages.iterator();
	while (iterator.hasNext()) {
	    PreferencesPage preferencesPage = iterator.next();
	    List<String> path = preferencesPage.getPath();
	    addNode(rootItem, path, 0, preferencesPage);
	}
    }

    private void openPreferencesPage(PreferencesPage preferencesPage) {
	if (preferencesPage != null) {
	    imageView.setImage(preferencesPage.getImage());
	    name.setText(preferencesPage.getName());
	    preferencesPane.setCenter(preferencesPage.createPane(this));
	    preferencesPage.load(Preferences.getInstance());
	    currentPreferencesPage = preferencesPage;
	    restoreDefaultsButton.setDisable(false);
	    applyButton.setDisable(false);
	} else {
	    restoreDefaultsButton.setDisable(true);
	    applyButton.setDisable(true);
	    currentPreferencesPage = null;
	    preferencesPane.setCenter(null);
	    imageView.setImage(null);
	    name.setText("");
	}
    }

    private void addNode(TreeItem<PreferencesPageNode> rootItem, List<String> path, int i,
	    PreferencesPage preferencesPage) {
	ObservableList<TreeItem<PreferencesPageNode>> children = rootItem.getChildren();
	TreeItem<PreferencesPageNode> child = null;
	for (TreeItem<PreferencesPageNode> node : children) {
	    if (node.getValue().getNodeName().equals(path.get(i))) {
		child = node;
		break;
	    }
	}
	if (child == null) {
	    child = new TreeItem<>(new PreferencesPageNode(path.get(i)));
	    children.add(child);
	}
	if (i == (path.size() - 1)) {
	    if (child.getValue().getPreferencesPage() != null) {
		throw new IllegalStateException("Multiple pages on one node are not supported.");
	    }
	    child.getValue().setPreferencesPage(preferencesPage);
	} else {
	    addNode(child, path, i + 1, preferencesPage);
	}
    }

    private void restoreDefaults() {
	if (currentPreferencesPage != null) {
	    currentPreferencesPage.reset();
	}
    }

    private void applyChanges() {
	if (currentPreferencesPage != null) {
	    currentPreferencesPage.save(Preferences.getInstance());
	}
    }
}
