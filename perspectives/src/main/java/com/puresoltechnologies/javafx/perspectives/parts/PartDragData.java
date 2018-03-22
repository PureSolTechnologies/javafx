package com.puresoltechnologies.javafx.perspectives.parts;

import java.io.Serializable;
import java.util.UUID;

public class PartDragData implements Serializable {

    private static final long serialVersionUID = 4947909835506827971L;

    private final UUID partStackId;
    private final UUID partId;

    public PartDragData(UUID partStackId, UUID partId) {
	super();
	this.partStackId = partStackId;
	this.partId = partId;
    }

    public UUID getPartStackId() {
	return partStackId;
    }

    public UUID getPartId() {
	return partId;
    }

}
