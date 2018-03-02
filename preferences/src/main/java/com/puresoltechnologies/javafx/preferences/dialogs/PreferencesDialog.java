package com.puresoltechnologies.javafx.preferences.dialogs;

import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
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

public class PreferencesDialog extends Dialog<Void> {

    private final TreeView<PreferencesPageNode> treeView;
    private final BorderPane preferencesPane;
    private final ImageView imageView;
    private final Label name;

    public PreferencesDialog() {
	setTitle("Preferences");
	setHeaderText("Preferences of Trader.");
	setResizable(true);
	setHeight(480);
	setWidth(640);

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

	HBox buttonBox = new HBox();
	buttonBox.setAlignment(Pos.CENTER_RIGHT);
	buttonBox.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, new CornerRadii(0.0),
		new BorderWidths(1.0, 0, 0, 0, false, false, false, false), new Insets(5, 5, 5, 5))));
	buttonBox.setPadding(new Insets(10, 0, 0, 0));
	Button restoreDefaultsButton = new Button("Restore Defaults");
	Button applyButton = new Button("Restore Defaults");
	buttonBox.getChildren().addAll(restoreDefaultsButton, applyButton);
	preferencesPane.setBottom(buttonBox);

	getDialogPane().setContent(splitPane);

	ButtonType buttonTypeOk = new ButtonType("Apply & Close", ButtonData.OK_DONE);
	ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
	ObservableList<ButtonType> buttonTypes = getDialogPane().getButtonTypes();
	buttonTypes.addAll(buttonTypeOk, buttonTypeCancel);

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
	    preferencesPane.setCenter(preferencesPage.getPane());
	} else {
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
	    child = new TreeItem<PreferencesPageNode>(new PreferencesPageNode(path.get(i)));
	    children.add(child);
	}
	if (i == path.size() - 1) {
	    if (child.getValue().getPreferencesPage() != null) {
		throw new IllegalStateException("Multiple pages on one node are not supported.");
	    }
	    child.getValue().setPreferencesPage(preferencesPage);
	} else {
	    addNode(child, path, i + 1, preferencesPage);
	}
    }

}
