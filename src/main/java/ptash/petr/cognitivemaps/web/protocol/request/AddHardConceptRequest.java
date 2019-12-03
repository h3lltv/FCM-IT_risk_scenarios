package ptash.petr.cognitivemaps.web.protocol.request;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

public class AddHardConceptRequest {

    @NotBlank
    private String mapName;

    @NotBlank
    private String conceptName;

    @NonNull
    @DecimalMax(value = "1.0")
    @DecimalMin(value = "0.0")
    private Double outputValue;

    @Nullable
    private String conceptDescription;

    public AddHardConceptRequest() {
    }

    public AddHardConceptRequest(String mapName, String conceptName, String conceptDescription, Double outputValue) {
        this.mapName = mapName;
        this.conceptName = conceptName;
        this.outputValue = outputValue;
        this.conceptDescription = conceptDescription;
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

    public Double getOutputValue() {
        return outputValue;
    }
}
