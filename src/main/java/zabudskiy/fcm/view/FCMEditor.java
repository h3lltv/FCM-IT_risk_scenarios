package zabudskiy.fcm.view;

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
import zabudskiy.fcm.service.exceptions.CognitiveMapBadRequestException;
import zabudskiy.fcm.service.exceptions.CognitiveMapNotFoundException;
import zabudskiy.fcm.service.impl.FCMServiceImpl;
import zabudskiy.fcm.service.impl.ConceptServiceImpl;
import zabudskiy.fcm.service.impl.ConnectionServiceImpl;
import zabudskiy.fcm.dto.ConceptDto;

@SpringComponent
@UIScope
public class FCMEditor extends VerticalLayout implements KeyNotifier {

    private FCMServiceImpl fcmService;
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

    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    Button addConcepts = new Button("Add concepts", VaadinIcon.PLUS.create());
    private Button addConnection = new Button("Add connection", VaadinIcon.ENTER.create());
    private Button executeMap = new Button("Execute map", VaadinIcon.ABACUS.create());
    private HorizontalLayout actions = new HorizontalLayout(save, delete);

    private Grid<ConceptDto> conceptGrid = new Grid<>(ConceptDto.class);

    private HorizontalLayout connections = new HorizontalLayout(connectionName, fromConcept, toConcept,
            connectionDesc, connectionWeight);
    private HorizontalLayout concepts = new HorizontalLayout(conceptName, conceptDesc);

    private ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    void setChangeHandler(ChangeHandler changeHandler) {
        this.changeHandler = changeHandler;
    }

    @Autowired
    public FCMEditor(FCMServiceImpl c, ConceptServiceImpl csi, ConnectionServiceImpl conService) {
        this.fcmService = c;
        this.conceptService = csi;
        this.connectionService = conService;

        setSpacing(true);

        connections.setVisible(false);
        conceptGrid.setVisible(false);
        conceptGrid.setColumnReorderingAllowed(true);
        conceptGrid.setHeightByRows(true);

        fromConcept.setPlaceholder("c1");
        toConcept.setPlaceholder("c2");
        addConcepts.setEnabled(false);
        addConnection.setEnabled(false);
        executeMap.setEnabled(false);
        add(mapName, actions,concepts, addConcepts, connections, addConnection, executeMap, conceptGrid);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save(mapName.getValue()));

        save.addClickListener(e -> save(mapName.getValue()));
        save.addClickListener(e -> addConcepts.setEnabled(true));
        save.addClickListener(e -> connections.setVisible(true));

        delete.addClickListener(e -> delete(mapName.getValue()));
        //addConcepts.addClickListener(e -> createConceptsForMap(mapName.getValue()));
        addConcepts.addClickListener(e -> createConceptsForBratan(conceptName.getValue(), conceptDesc.getValue(), mapName.getValue()));
//        addConcepts.addClickListener(e -> addConnection.setEnabled(true));
        addConnection.addClickListener(e -> createConnection(connectionName.getValue(), connectionDesc.getValue(),
                Double.parseDouble(connectionWeight.getValue()), mapName.getValue(), fromConcept.getValue(),
                toConcept.getValue()));
//        addConnection.addClickListener(e -> executeMap.setEnabled(true));
        executeMap.addClickListener(e -> execute(mapName.getValue()));
        executeMap.addClickListener(e -> conceptGrid.setItems(c.getByName(mapName.getValue()).getConcepts()));
        //executeMap.addClickListener(e -> conceptGrid.setVisible(true));
        setVisible(false);
    }

    private void createConceptsForBratan(String conceptName, String description, String mapName){
        try {
            conceptService.addFlexConcept(conceptName, description, mapName);
            addConnection.setEnabled(true);
            changeHandler.onChange();
        }
        catch (CognitiveMapBadRequestException e) {
            Notification notification = new Notification(
                    e.getMessage(), 3000);
            notification.open();
        }
    }

    private void createConceptsForMap(String s) {
        try {
            conceptService.addFlexConcept("c1", "Wrong IT tools selection", s);
            conceptService.addFlexConcept("c2", "IT software releases unstability", s);
            conceptService.addFlexConcept("c3", "Complex IT integration within current\n" +
                    "infrastructure", s);
            conceptService.addFlexConcept("c4", "Unrealistic expectations", s);
            conceptService.addFlexConcept("c5", "Low Top management support", s);
            conceptService.addFlexConcept("c6", "Low users’ involvement", s);
            conceptService.addFlexConcept("c7", "Inadequate consulting services", s);
            conceptService.addFlexConcept("c8", "High costs", s);
            conceptService.addFlexConcept("c9", "Wrong IT project management", s);
            conceptService.addFlexConcept("c10", "High reliability requirements", s);
            conceptService.addFlexConcept("c11", "Wrong legacy systems management", s);
            conceptService.addFlexConcept("c12", "IT security issues", s);
            conceptService.addFlexConcept("c13", "Low Performance", s);
            addConnection.setEnabled(true);
            changeHandler.onChange();
        } catch (CognitiveMapBadRequestException e) {
            Notification notification = new Notification(
                    e.getMessage(), 3000);
            notification.open();
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
        if (weight >= 0 && weight <= 1) {
            try {
                connectionService.addConnection(name, desc, weight, mapName, fromC, toC);
                changeHandler.onChange();
                executeMap.setEnabled(true);
            } catch (CognitiveMapNotFoundException e) {
                Notification notification = new Notification(e.getMessage(), 3000);
                notification.open();

            }
        } else {
            Notification notification = new Notification("Please, enter correct weight!", 3000);
            notification.open();
            connectionWeight.focus();
        }
    }
}