package ptash.petr.cognitivemaps.web.protocol.request;

import javax.validation.constraints.NotBlank;

public class GetMapRequest {

    @NotBlank
    private String name;

    public GetMapRequest() {
    }

    public GetMapRequest(@NotBlank String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
