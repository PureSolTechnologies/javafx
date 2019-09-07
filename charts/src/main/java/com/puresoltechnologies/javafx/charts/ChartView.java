package com.puresoltechnologies.javafx.charts;

import com.puresoltechnologies.javafx.charts.plots.Plot;
import com.puresoltechnologies.javafx.charts.plots.PlotCanvas;
import com.puresoltechnologies.javafx.charts.preferences.ChartsProperties;
import com.puresoltechnologies.javafx.extensions.fonts.FontDefinition;
import com.puresoltechnologies.javafx.preferences.Preferences;
import com.puresoltechnologies.javafx.utils.FXNodeUtils;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;

/**
 * This is the component to be used to plot into.
 *
 * @author Rick-Rainer Ludwig
 */
public class ChartView extends GridPane {

    private static final ObjectProperty<FontDefinition> titleFontProperty = Preferences
	    .getProperty(ChartsProperties.TITLE_FONT);
    private static final ObjectProperty<FontDefinition> subtitleFontProperty = Preferences
	    .getProperty(ChartsProperties.SUBTITLE_FONT);

    private final BooleanProperty legendVisible = new SimpleBooleanProperty(true);
    private final StringProperty titleProperty = new SimpleStringProperty();
    private final StringProperty subTitleProperty = new SimpleStringProperty();

    private final PlotCanvas plotCanvas = new PlotCanvas();
    private final Label titleLabel = new Label();
    private final Label subTitleLabel = new Label();
    private final TableView<Plot<?, ?, ?>> legendTable = new TableView<>();

    public ChartView(String title) {
	this();
	titleProperty.setValue(title);
    }

    public ChartView() {
	super();
	titleLabel.textProperty().bind(titleProperty);
	titleLabel.visibleProperty().bind(titleProperty.isNotEmpty());
	titleLabel.managedProperty().bind(titleProperty.isNotEmpty());
	titleLabel.setFont(titleFontProperty.get().toFont());
	titleLabel.setAlignment(Pos.TOP_CENTER);
	FXNodeUtils.setTextColor(titleLabel, titleFontProperty.get().getColor());
	subTitleLabel.textProperty().bind(subTitleProperty);
	subTitleLabel.visibleProperty().bind(subTitleProperty.isNotEmpty());
	subTitleLabel.managedProperty().bind(subTitleProperty.isNotEmpty());
	subTitleLabel.setFont(subtitleFontProperty.get().toFont());
	subTitleLabel.setAlignment(Pos.TOP_CENTER);
	FXNodeUtils.setTextColor(subTitleLabel, subtitleFontProperty.get().getColor());
	plotCanvas.setManaged(true);

	configureLegendTable();

	GridPane.setConstraints(titleLabel, 0, 0, 1, 1, HPos.CENTER, VPos.TOP, Priority.ALWAYS, Priority.NEVER);
	GridPane.setConstraints(subTitleLabel, 0, 1, 1, 1, HPos.CENTER, VPos.TOP, Priority.ALWAYS, Priority.NEVER);
	GridPane.setConstraints(plotCanvas, 0, 2, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
	GridPane.setConstraints(legendTable, 1, 2, 1, 1, HPos.CENTER, VPos.TOP, Priority.SOMETIMES, Priority.ALWAYS);
	getChildren().addAll(titleLabel, subTitleLabel, plotCanvas, legendTable);
	setGridLinesVisible(false);
    }

    private void configureLegendTable() {

	TableColumn<Plot<?, ?, ?>, Plot<?, ?, ?>> markerColumn = new TableColumn<>("Marker");
	markerColumn.setCellValueFactory(plot -> new SimpleObjectProperty<>(plot.getValue()));
	markerColumn.setCellFactory(column -> new TableCell<>() {
	    @Override
	    protected void updateItem(Plot<?, ?, ?> plot, boolean empty) {
		super.updateItem(plot, empty);
		setText(null);
		if ((plot == null) || empty) {
		    setGraphic(null);
		} else {
		    Color color = plot.getColor();
		    Pane pane = new Pane();
		    pane.backgroundProperty()
			    .setValue(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
		    setGraphic(pane);
		}
	    }
	});
	legendTable.getColumns().add(markerColumn);

	TableColumn<Plot<?, ?, ?>, String> nameColumn = new TableColumn<>("Name");
	nameColumn.setCellValueFactory(plot -> new SimpleStringProperty(plot.getValue().getTitle()));
	legendTable.getColumns().add(nameColumn);

	legendTable.visibleProperty().bind(legendVisible);
	legendTable.setItems(plotCanvas.getPlots());
    }

    public ObjectProperty<Insets> plotPaddingProperty() {
	return plotCanvas.paddingProperty();
    }

    public void setPlotPadding(Insets insets) {
	plotCanvas.setPadding(insets);
    }

    public Insets getPlotPadding() {
	return plotCanvas.getPadding();
    }

    public StringProperty titleProperty() {
	return titleProperty;
    }

    public void setTitle(String title) {
	titleProperty.setValue(title);
    }

    public String getTitle() {
	return titleProperty.getValue();
    }

    public StringProperty subTitleProperty() {
	return subTitleProperty;
    }

    public void setSubTitle(String subTitle) {
	subTitleProperty.setValue(subTitle);
    }

    public String getSubTitle() {
	return subTitleProperty.get();
    }

    public BooleanProperty legendVisibleProperty() {
	return legendVisible;
    }

    public void setLegendVisible(boolean visible) {
	legendVisible.setValue(visible);
    }

    public boolean isLegendVisible() {
	return legendVisible.getValue();
    }

    public void addPlot(Plot<?, ?, ?> plot) {
	plotCanvas.addPlot(plot);
    }

}
