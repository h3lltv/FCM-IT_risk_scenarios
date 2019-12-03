package ptash.petr.cognitivemaps.web.protocol.request;

import javax.validation.constraints.NotBlank;

public class CreateCognitiveMapRequest {

    @NotBlank
    private String name;

    public CreateCognitiveMapRequest() {
    }

    public CreateCognitiveMapRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
