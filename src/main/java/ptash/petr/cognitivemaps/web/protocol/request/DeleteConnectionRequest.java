package ptash.petr.cognitivemaps.web.protocol.request;

import javax.validation.constraints.NotBlank;

public class DeleteConnectionRequest {

    @NotBlank
    private String connectionName;

    @NotBlank
    private String mapName;

    public DeleteConnectionRequest() {}

    public DeleteConnectionRequest(@NotBlank String connectionName, @NotBlank String mapName) {
        this.connectionName = connectionName;
        this.mapName = mapName;
    }

    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }
}
