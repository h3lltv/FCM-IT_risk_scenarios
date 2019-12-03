package ptash.petr.cognitivemaps.web.protocol.request;

import org.springframework.lang.Nullable;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AddConnectionRequest {

    @NotBlank
    private String connectionName;

    @NotBlank
    private String mapName;

    @NotBlank
    private String fromConceptName;

    @Nullable
    private String description;

    @NotBlank
    private String toConceptName;

    @NotNull
    @DecimalMax(value = "1.0")
    @DecimalMin(value = "0.0")
    private Double weight;

    public AddConnectionRequest() {
    }

    public AddConnectionRequest(@NotBlank String connectionName, @NotBlank String mapName, @NotBlank String fromConceptName, @Nullable String description,
                                @NotBlank String toConceptName, @NotNull @DecimalMax(value = "1.0") @DecimalMin(value = "-1.0") Double weight) {
        this.connectionName = connectionName;
        this.mapName = mapName;
        this.fromConceptName = fromConceptName;
        this.description = description;
        this.toConceptName = toConceptName;
        this.weight = weight;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    public String getConnectionName() {
        return connectionName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setFromConceptName(String fromConceptName) {
        this.fromConceptName = fromConceptName;
    }

    public void setToConceptName(String toConceptName) {
        this.toConceptName = toConceptName;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getMapName() {
        return mapName;
    }

    public String getFromConceptName() {
        return fromConceptName;
    }

    public String getToConceptName() {
        return toConceptName;
    }

    public Double getWeight() {
        return weight;
    }
}
