package zabudskiy.fcm.web.protocol.response;

import org.megadix.jfcm.Concept;

public class ConceptDto {

    private String name;
    private String description;
    private double outputValue;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getOutputValue() {
        return outputValue;
    }

    private ConceptDto(String name, String description, double outputValue) {
        this.name = name;
        this.description = description;
        this.outputValue = outputValue;
    }

    public static ConceptDto fromConcept(Concept concept) {
        if (concept.getOutput() == null) {
            return new ConceptDto(concept.getName(), concept.getDescription(), 0.0);
        } else {
            return new ConceptDto(concept.getName(), concept.getDescription(), concept.getOutput());
        }
    }

    @Override
    public String toString(){
        return this.name;
    }
}
