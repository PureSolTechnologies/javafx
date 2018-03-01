package com.puresoltechnologies.javafx.perspectives;

import java.io.Serializable;

public class PartDragData implements Serializable {

    private static final long serialVersionUID = 4947909835506827971L;

    private final String partStackId;
    private final String partId;

    public PartDragData(String partStackId, String partId) {
	super();
	this.partStackId = partStackId;
	this.partId = partId;
    }

    public String getPartStackId() {
	return partStackId;
    }

    public String getPartId() {
	return partId;
    }

}
