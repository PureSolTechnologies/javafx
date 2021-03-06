package com.puresoltechnologies.javafx.charts;

import java.util.List;
import java.util.Locale;

import com.puresoltechnologies.javafx.charts.axes.Axis;
import com.puresoltechnologies.javafx.charts.plots.Plot;
import com.puresoltechnologies.javafx.charts.plots.PlotCanvas;
import com.puresoltechnologies.javafx.charts.plots.PlotDatum;
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
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;

/**
 * This is the component to be used to plot into.
 *
 * @author Rick-Rainer Ludwig
 */
public class ChartView extends GridPane {

    /**
     * This enum is used to specify the legend in relationship to the plot.
     *
     * @author Rick-Rainer Ludwig
     */
    public enum LegendPosition {

        LEFT, RIGHT, TOP, BOTTOM;

    }

    private static final ObjectProperty<FontDefinition> titleFontProperty = Preferences
            .getProperty(ChartsProperties.TITLE_FONT);
    private static final ObjectProperty<FontDefinition> subtitleFontProperty = Preferences
            .getProperty(ChartsProperties.SUBTITLE_FONT);

    private final BooleanProperty hasContextMenu = new SimpleBooleanProperty(true);
    private final BooleanProperty legendVisible = new SimpleBooleanProperty(true);
    private final StringProperty titleProperty = new SimpleStringProperty();
    private final StringProperty subTitleProperty = new SimpleStringProperty();
    private final ObjectProperty<Locale> copyLocale = new SimpleObjectProperty<>(Locale.getDefault());
    private final ObjectProperty<LegendPosition> legendPosition = new SimpleObjectProperty<>(LegendPosition.RIGHT);

    private final PlotCanvas plotCanvas = new PlotCanvas();
    private final BorderPane plotBorderPane = new BorderPane();
    private final Label titleLabel = new Label();
    private final Label subTitleLabel = new Label();
    private final LegendPane legendPane = new LegendPane(this, plotCanvas.getPlots());

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
        legendPosition.addListener((observable, oldValue, newValue) -> applyLegend());

        configureLegendTable();
        configureContextMenu();

        plotBorderPane.setCenter(plotCanvas);
        applyLegend();

        GridPane.setConstraints(titleLabel, 0, 0, 1, 1, HPos.CENTER, VPos.TOP, Priority.ALWAYS, Priority.NEVER);
        GridPane.setConstraints(subTitleLabel, 0, 1, 1, 1, HPos.CENTER, VPos.TOP, Priority.ALWAYS, Priority.NEVER);
        GridPane.setConstraints(plotBorderPane, 0, 2, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
        getChildren().addAll(titleLabel, subTitleLabel, plotBorderPane);
        setMaxHeight(Double.MAX_VALUE);
        setMaxWidth(Double.MAX_VALUE);
        setGridLinesVisible(false);
    }

    private void configureLegendTable() {
        legendPane.visibleProperty().bind(legendVisible);
        legendPane.managedProperty().bind(legendVisible);
    }

    private void applyLegend() {
        plotBorderPane.getChildren().remove(legendPane);
        switch (legendPosition.get()) {
        case LEFT:
            plotBorderPane.setLeft(legendPane);
            legendPane.setOrientation(Orientation.VERTICAL);
            break;
        case RIGHT:
            plotBorderPane.setRight(legendPane);
            legendPane.setOrientation(Orientation.VERTICAL);
            break;
        case TOP:
            plotBorderPane.setTop(legendPane);
            legendPane.setOrientation(Orientation.HORIZONTAL);
            break;
        case BOTTOM:
            plotBorderPane.setBottom(legendPane);
            legendPane.setOrientation(Orientation.HORIZONTAL);
            break;
        default:
            // intentionally left empty
        }
    }

    private void configureContextMenu() {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem autoScaleItem = new MenuItem("Autoscale");
        autoScaleItem.setOnAction(event -> {
            plotCanvas.autoScale();
            event.consume();
        });
        MenuItem copyItem = new MenuItem("Copy");
        copyItem.setOnAction(event -> {
            copy();
            event.consume();
        });
//        MenuItem chartPropertiesItem = new MenuItem("Chart Properties...");
//        chartPropertiesItem.setOnAction(event -> {
//            new ChartPropertiesDialog(ChartView.this, plotCanvas.getPlots()).showAndWait();
//            event.consume();
//        });
        contextMenu.getItems().addAll( //
                autoScaleItem, //
                new SeparatorMenuItem(), //
                copyItem // TODO , //
//		new SeparatorMenuItem(), //
//		chartPropertiesItem //
        );

        setOnMouseClicked(event -> {
            if (hasContextMenu.get()) {
                if (event.getButton() == MouseButton.SECONDARY) {
                    contextMenu.show(ChartView.this, event.getScreenX(), event.getScreenY());
                    event.consume();
                } else if (event.getButton() == MouseButton.PRIMARY) {
                    if (contextMenu.isShowing()) {
                        contextMenu.hide();
                    }
                }
                /*
                 * Event is only consumed when context menu is used. Otherwise, the event is
                 * propagated.
                 */
                event.consume();
            }
        });
    }

    private void copy() {
        String clipboardString = createClipboardDataString();
        Image clipboardImage = createClipboardImage();

        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(clipboardString);
        clipboardContent.putImage(clipboardImage);
        Clipboard.getSystemClipboard().setContent(clipboardContent);
    }

    private String createClipboardDataString() {
        StringBuilder builder = new StringBuilder();

        ObservableList<Plot<?, ?, ?>> plots = plotCanvas.getPlots();
        int maxSize = 0;
        // Determine max length and write chart titles...
        boolean first = true;
        for (Plot<?, ?, ?> plot : plots) {
            maxSize = Math.max(maxSize, plot.getData().size());
            if (first) {
                first = false;
            } else {
                builder.append('\t');
            }
            builder.append(plot.getTitle());
            builder.append('\t');
        }
        builder.append('\n');
        // Write X and Y axes titles...
        first = true;
        for (Plot<?, ?, ?> plot : plots) {
            if (first) {
                first = false;
            } else {
                builder.append('\t');
            }
            Axis<?> xAxis = plot.getXAxis();
            builder.append(xAxis.getTitle() + "(" + plot.getXAxis().getUnit() + ")");
            builder.append('\t');
            Axis<?> yAxis = plot.getYAxis();
            builder.append(yAxis.getTitle() + "(" + plot.getYAxis().getUnit() + ")");
        }
        builder.append('\n');

        for (int pos = 0; pos < maxSize; pos++) {
            first = true;
            for (Plot<?, ?, ?> plot : plots) {
                if (first) {
                    first = false;
                } else {
                    builder.append('\t');
                }
                @SuppressWarnings("unchecked")
                List<PlotDatum<?, ?>> data = (List<PlotDatum<?, ?>>) plot.getData();
                if (data.size() > pos) {
                    PlotDatum<?, ?> datum = data.get(pos);
                    builder.append(datum.getClipboardString(copyLocale.get()));
                }
            }
            builder.append('\n');
        }
        String clipboardString = builder.toString();
        return clipboardString;
    }

    private Image createClipboardImage() {
        WritableImage writableImage = new WritableImage((int) getWidth(), (int) getHeight());
        this.snapshot(null, writableImage);
        return writableImage;
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

    /**
     * This property is the {@link Locale} used to convert the data for the clip
     * board. The default locale is taken from {@link Locale#getDefault()}.
     *
     * @return An {@link ObjectProperty} is returned.
     */
    public ObjectProperty<Locale> copyLocaleProperty() {
        return copyLocale;
    }

    /**
     * Sets the {@link #copyLocaleProperty()}.
     *
     * @param locale is the locale to be set.
     */
    public void setCopyLocale(Locale locale) {
        copyLocale.setValue(locale);
    }

    /**
     * Returns the {@link #copyLocaleProperty()}.
     *
     * @return A {@link Locale} is returned.
     */
    public Locale getCopyLocale() {
        return copyLocale.getValue();
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

    public ObjectProperty<Color> frameColorProperty() {
        return plotCanvas.frameColorProperty();
    }

    public void setFrameColor(Color frameColor) {
        plotCanvas.frameColorProperty().set(frameColor);
    }

    public Color getFrameColor() {
        return plotCanvas.frameColorProperty().get();
    }

    public ObjectProperty<Color> backgroundColorProperty() {
        return plotCanvas.backgroundColorProperty();
    }

    public void setBackgroundColor(Color backgroundColor) {
        plotCanvas.backgroundColorProperty().set(backgroundColor);
    }

    public Color getBackgroundColor() {
        return plotCanvas.backgroundColorProperty().get();
    }

    public ObjectProperty<LegendPosition> legendPositionProperty() {
        return legendPosition;
    }

    public void setLegendPosition(LegendPosition legendPosition) {
        this.legendPosition.set(legendPosition);
    }

    public LegendPosition getLegendPosition() {
        return legendPosition.get();
    }

    public ObservableList<Plot<?, ?, ?>> getPlots() {
        return plotCanvas.getPlots();
    }

    public void addPlot(Plot<?, ?, ?> plot) {
        plotCanvas.addPlot(plot);
    }

    public void removePlot(Plot<?, ?, ?> plot) {
        plotCanvas.removePlot(plot);
    }

    public void clear() {
        plotCanvas.removeAllPlots();
    }
}
