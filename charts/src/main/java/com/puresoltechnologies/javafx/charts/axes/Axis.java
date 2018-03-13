package com.puresoltechnologies.javafx.charts.axes;

/**
 * Interface for a single axis.
 * 
 * @author Rick-Rainer Ludwig
 */
public interface Axis<T> {

    /**
     * For internal use, an identifier is randomly generated to find reused axis for
     * multi plots.
     * 
     * @return A {@link String} from a random UUID is returned.
     */
    public String getId();

    public String getTitle();

    public String getUnit();

    public AxisType getAxisType();

    public Class<T> getValueType();

}
