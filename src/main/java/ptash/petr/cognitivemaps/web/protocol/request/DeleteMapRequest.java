package ptash.petr.cognitivemaps.web.protocol.request;

import javax.validation.constraints.NotBlank;

public class DeleteMapRequest {

    @NotBlank
    private String name;

    public DeleteMapRequest() {}

    public DeleteMapRequest(@NotBlank String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }
}
