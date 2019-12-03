package ptash.petr.cognitivemaps.web.protocol.request;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

public class AddFlexibleConceptRequest {

    @NotBlank
    private String mapName;

    @NotBlank
    private String conceptName;

    @Nullable
    private String conceptDescription;

    @NonNull
    @DecimalMax(value = "1.0")
    @DecimalMin(value = "0.0")
    private double outputValue;

    public AddFlexibleConceptRequest() {
    }

    public AddFlexibleConceptRequest(String mapName, String conceptName, String conceptDescription, double outputValue) {
        this.mapName = mapName;
        this.conceptName = conceptName;
        this.conceptDescription = conceptDescription;
        this.outputValue = outputValue;
    }

    public String getMapName() {
        return mapName;
    }

    public String getConceptName() {
        return conceptName;
    }

    public String getConceptDescription() {
        return conceptDescription;
    }

    public double getOutputValue() {
        return outputValue;
    }
}
