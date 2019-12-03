package ptash.petr.cognitivemaps.web.protocol.request;

import javax.validation.constraints.NotBlank;

public class DeleteConceptRequest {

    @NotBlank
    private String conceptName;

    @NotBlank
    private String mapName;

    public DeleteConceptRequest() {}

    public DeleteConceptRequest(@NotBlank String conceptName, @NotBlank String mapName) {
        this.conceptName = conceptName;
        this.mapName = mapName;
    }

    public String getConceptName() {
        return conceptName;
    }

    public void setConceptName(String conceptName) {
        this.conceptName = conceptName;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }
}
