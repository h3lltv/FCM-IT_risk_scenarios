package zabudskiy.fcm.web.protocol.response;

import org.megadix.jfcm.FcmConnection;

public class ConnectionDto {

    private String name;
    private String description;
    private String fromConceptName;
    private String toConceptName;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getFromConceptName() {
        return fromConceptName;
    }

    public String getToConceptName() {
        return toConceptName;
    }

    private ConnectionDto(String name, String description, String fromConceptName, String toConceptName) {
        this.name = name;
        this.description = description;
        this.fromConceptName = fromConceptName;
        this.toConceptName = toConceptName;
    }

    public static ConnectionDto fromConnection(FcmConnection connection) {
        return new ConnectionDto(connection.getName(), connection.getDescription(), connection.getFrom().getName(), connection.getTo().getName());
    }

    @Override
    public String toString(){
        return fromConceptName+ " -> " + toConceptName;
    }
}
