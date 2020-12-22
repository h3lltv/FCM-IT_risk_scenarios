package ptash.petr.cognitivemaps.ui;

import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import ptash.petr.cognitivemaps.service.exceptions.CognitiveMapBadRequestException;
import ptash.petr.cognitivemaps.service.exceptions.CognitiveMapNotFoundException;
import ptash.petr.cognitivemaps.service.impl.CognitiveMapServiceImpl;
import ptash.petr.cognitivemaps.service.impl.ConceptServiceImpl;
import ptash.petr.cognitivemaps.service.impl.ConnectionServiceImpl;
import ptash.petr.cognitivemaps.web.protocol.response.ConceptDto;

@SpringComponent
@UIScope
public class CognitiveMapEditor extends VerticalLayout implements KeyNotifier {

    private CognitiveMapServiceImpl fcmService;
    private ConceptServiceImpl conceptService;
    private ConnectionServiceImpl connectionService;

    private TextField mapName = new TextField("Map name");
    private TextField connectionName = new TextField("Name");
    private TextField conceptName = new TextField("Concept name");
    private TextArea conceptDesc = new TextArea("Description");
    private TextArea connectionDesc = new TextArea("Description");
    private TextField connectionWeight = new TextField("Weight");
    private TextField fromConcept = new TextField("From concept");
    private TextField toConcept = new TextField("To concept");
    private NumberField conceptValue = new NumberField();

    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    Button addFLexConcept = new Button("Add concepts", VaadinIcon.PLUS.create());
    Button addHardConcept = new Button("Add concepts", VaadinIcon.HAMMER.create());
    private Button addConnection = new Button("Add connection", VaadinIcon.ENTER.create());
    private Button executeMap = new Button("Execute map", VaadinIcon.ABACUS.create());
    private HorizontalLayout actions = new HorizontalLayout(save, delete);

    private Grid<ConceptDto> conceptGrid = new Grid<>(ConceptDto.class);

    private HorizontalLayout connections = new HorizontalLayout(connectionName, fromConcept, toConcept,
            connectionDesc, connectionWeight);
    private HorizontalLayout concepts = new HorizontalLayout(conceptName, conceptValue, conceptDesc);

    private ChangeHandler changeHandler;


    public interface ChangeHandler {
        void onChange();
    }

    void setChangeHandler(ChangeHandler changeHandler) {
        this.changeHandler = changeHandler;
    }

    @Autowired
    public CognitiveMapEditor(CognitiveMapServiceImpl c, ConceptServiceImpl csi, ConnectionServiceImpl conService) {
        this.fcmService = c;
        this.conceptService = csi;
        this.connectionService = conService;
//        conceptValue = new NumberField();
        conceptValue.setHasControls(true);
        conceptValue.setStep(0.01d);
        conceptValue.setMin(0);
        conceptValue.setMax(1);
        conceptValue.setPlaceholder("0-1");

        setSpacing(true);

        connections.setVisible(false);
        conceptGrid.setVisible(false);
        conceptGrid.setColumnReorderingAllowed(true);
        conceptGrid.setHeightByRows(true);

        fromConcept.setPlaceholder("c1");
        toConcept.setPlaceholder("c2");
        addFLexConcept.setEnabled(false);
        addHardConcept.setEnabled(false);
        addConnection.setEnabled(false);
        executeMap.setEnabled(false);
        add(mapName, actions, concepts, addFLexConcept, addHardConcept, connections, addConnection, executeMap, conceptGrid);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save(mapName.getValue()));

        save.addClickListener(e -> save(mapName.getValue()));
        save.addClickListener(e -> addFLexConcept.setEnabled(true));
        save.addClickListener(e -> addHardConcept.setEnabled(true));
        save.addClickListener(e -> connections.setVisible(true));

        delete.addClickListener(e -> delete(mapName.getValue()));
        addFLexConcept.addClickListener(e -> createConceptsForFlexBratan(conceptName.getValue(), conceptDesc.getValue(),
                conceptValue.getValue(), mapName.getValue()));
        addHardConcept.addClickListener(e -> createConceptsForHardBratan(conceptName.getValue(), conceptDesc.getValue(),
                conceptValue.getValue(), mapName.getValue()));
        addConnection.addClickListener(e -> createConnection(connectionName.getValue(), connectionDesc.getValue(),
                Double.parseDouble(connectionWeight.getValue()), mapName.getValue(), fromConcept.getValue(),
                toConcept.getValue()));

        executeMap.addClickListener(e -> execute(mapName.getValue()));
        executeMap.addClickListener(e -> conceptGrid.setItems(c.getByName(mapName.getValue()).getConcepts()));

        setVisible(false);
    }

    private void createConceptsForFlexBratan(String conceptName, String description, double weight, String mapName) {
        if (weight >= 0 && weight <= 1) {
            try {
                conceptService.addFlexConcept(conceptName, description, weight, mapName);
                addConnection.setEnabled(true);
                changeHandler.onChange();
            } catch (CognitiveMapBadRequestException e) {
                Notification notification = new Notification(
                        e.getMessage(), 3000);
                notification.open();
            }
        } else {
            Notification nt = new Notification("Weight must be in range from 0 to 1");
            nt.open();
        }
    }

    private void createConceptsForHardBratan(String conceptName, String description, double weight, String mapName) {
        if (weight >= 0 && weight <= 1) {
            try {
                conceptService.addHardConcept(conceptName, description, weight, mapName);
                addConnection.setEnabled(true);
                changeHandler.onChange();
            } catch (CognitiveMapBadRequestException e) {
                Notification notification = new Notification(
                        e.getMessage(), 3000);
                notification.open();
            }
        } else {
            Notification nt = new Notification("Weight must be in range from 0 to 1");
            nt.open();
        }
    }

    private void execute(String s) {
        try {
            fcmService.execute(s);
            conceptGrid.setVisible(true);
        } catch (CognitiveMapBadRequestException e) {
            Notification notification = new Notification(e.getMessage(), 3000);
            notification.open();
        }
    }

    private void delete(String s) {
        try {
            fcmService.deleteCognitiveMap(s);
            changeHandler.onChange();
        } catch (CognitiveMapNotFoundException e) {
            Notification notification = new Notification(e.getMessage(), 3000);
            notification.open();
        }
    }

    private void save(String s) {
        try {
            fcmService.createNewMap(s);
            changeHandler.onChange();
        } catch (CognitiveMapBadRequestException e) {
            Notification notification = new Notification(e.getMessage(), 3000);
            notification.open();
        }
    }

    private void createConnection(String name, String desc, double weight, String mapName, String fromC, String toC) {
        if (weight >= -1 && weight <= 1) {
            try {
                connectionService.addConnection(name, desc, weight, mapName, fromC, toC);
                changeHandler.onChange();
                executeMap.setEnabled(true);
            } catch (CognitiveMapBadRequestException e) {
                Notification notification = new Notification(e.getMessage(), 3000);
                notification.open();
            }
        } else {
            Notification not = new Notification("Weight must be in range from -1 to 1", 3000);
            not.open();
        }
    }
}