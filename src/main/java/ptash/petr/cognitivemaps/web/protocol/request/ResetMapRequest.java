package ptash.petr.cognitivemaps.web.protocol.request;

import javax.validation.constraints.NotBlank;

public class ResetMapRequest {

    @NotBlank
    private String name;

    public ResetMapRequest() {
    }

    public ResetMapRequest(@NotBlank String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
