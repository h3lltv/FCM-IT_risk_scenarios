package zabudskiy.fcm.view;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
//import com.vaadin.flow.data.value.ValueChangeMode;
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

    private static final double[] scenarioA = {0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0};
    private static final double[] scenarioB = {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0};
    private static final double[] scenarioC = {1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    private TextField mapName = new TextField("Map name");
    private TextField connectionName = new TextField("Name");
    private TextArea connectionDesc = new TextArea("Description");
    private TextField connectionWeight = new TextField("Weight");
    private TextField fromConcept = new TextField("From concept");
    private TextField toConcept = new TextField("To concept");

    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    //    Button addConcepts = new Button("Add concepts", VaadinIcon.PLUS.create());
    Button addConnection = new Button("Add connection", VaadinIcon.ENTER.create());
    private Button newScenario = new Button("Custom scenario", VaadinIcon.ABACUS.create());
    Button setCustomScenario = new Button("Set scenario", VaadinIcon.ASTERISK.create());
    private Button executeMap = new Button("Execute map", VaadinIcon.ABACUS.create());
    private NumberField numberField1;
    private NumberField numberField2;
    private NumberField numberField3;
    private NumberField numberField4;
    private NumberField numberField5;
    private NumberField numberField6;
    private NumberField numberField7;
    private NumberField numberField8;
    private NumberField numberField9;
    private NumberField numberField10;
    private NumberField numberField11;
    private NumberField numberField12;
    private NumberField numberField13;

    ComboBox<String> concepts = new ComboBox<>("Scenario");

    private Grid<ConceptDto> conceptGrid = new Grid<>(ConceptDto.class);

//    private HorizontalLayout connections = new HorizontalLayout(connectionName, fromConcept, toConcept,
//            connectionDesc, connectionWeight);

    private ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    void setChangeHandler(ChangeHandler changeHandler) {
        this.changeHandler = changeHandler;
    }

    @Autowired
    public FCMEditor(FCMServiceImpl c, ConceptServiceImpl csi, ConnectionServiceImpl conService) {
        HorizontalLayout actions = new HorizontalLayout(save, delete, concepts);

        FormLayout columnLayout = new FormLayout();

        numberField1 = new NumberField();
        numberField1.setHasControls(true);
        numberField1.setStep(0.01d);
        numberField1.setMin(0);
        numberField1.setMax(1);
        numberField1.setPlaceholder("c1");

        numberField2 = new NumberField();
        numberField2.setHasControls(true);
        numberField2.setStep(0.01d);
        numberField2.setMin(0);
        numberField2.setMax(1);
        numberField2.setPlaceholder("c2");


        numberField3 = new NumberField();
        numberField3.setHasControls(true);
        numberField3.setStep(0.01d);
        numberField3.setMin(0);
        numberField3.setMax(1);
        numberField3.setPlaceholder("c3");


        numberField4 = new NumberField();
        numberField4.setHasControls(true);
        numberField4.setStep(0.01d);
        numberField4.setMin(0);
        numberField4.setMax(1);
        numberField4.setPlaceholder("c4");


        numberField5 = new NumberField();
        numberField5.setHasControls(true);
        numberField5.setStep(0.01d);
        numberField5.setMin(0);
        numberField5.setMax(1);
        numberField5.setPlaceholder("c5");


        numberField6 = new NumberField();
        numberField6.setHasControls(true);
        numberField6.setStep(0.01d);
        numberField6.setMin(0);
        numberField6.setMax(1);
        numberField6.setPlaceholder("c6");


        numberField7 = new NumberField();
        numberField7.setHasControls(true);
        numberField7.setStep(0.01d);
        numberField7.setMin(0);
        numberField7.setMax(1);
        numberField7.setPlaceholder("c7");


        numberField8 = new NumberField();
        numberField8.setHasControls(true);
        numberField8.setStep(0.01d);
        numberField8.setMin(0);
        numberField8.setMax(1);
        numberField8.setPlaceholder("c8");


        numberField9 = new NumberField();
        numberField9.setHasControls(true);
        numberField9.setStep(0.01d);
        numberField9.setMin(0);
        numberField9.setMax(1);
        numberField9.setPlaceholder("c9");


        numberField10 = new NumberField();
        numberField10.setHasControls(true);
        numberField10.setStep(0.01d);
        numberField10.setMin(0);
        numberField10.setMax(1);
        numberField10.setPlaceholder("c10");

        numberField11 = new NumberField();
        numberField11.setHasControls(true);
        numberField11.setStep(0.01d);
        numberField11.setMin(0);
        numberField11.setMax(1);
        numberField11.setPlaceholder("c11");


        numberField12 = new NumberField();
        numberField12.setHasControls(true);
        numberField12.setStep(0.01d);
        numberField12.setMin(0);
        numberField12.setMax(1);
        numberField12.setPlaceholder("c12");


        numberField13 = new NumberField();
        numberField13.setHasControls(true);
        numberField13.setStep(0.01d);
        numberField13.setMin(0);
        numberField13.setMax(1);
        numberField13.setPlaceholder("c13");
//        columnLayout.setVisible(false);
        columnLayout.add(numberField1, numberField2, numberField2, numberField2, numberField3, numberField4, numberField5,
                numberField6, numberField7, numberField8, numberField9, numberField10, numberField11, numberField12, numberField13);
        columnLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("10em", 3));
        columnLayout.setWidth("50");

        newScenario.setVisible(false);

        columnLayout.setVisible(false);
        this.fcmService = c;
        this.conceptService = csi;
        this.connectionService = conService;

        concepts.setItems("Scenario A", "Scenario B", "Scenario C");
        concepts.setVisible(false);
        concepts.addValueChangeListener(event -> {
            if (event.getValue() != null) {
                if (event.getValue().equalsIgnoreCase("scenario a")) {
                    createConceptsForMap(mapName.getValue(), scenarioA);
                } else if (event.getValue().equalsIgnoreCase("scenario b")) {
                    createConceptsForMap(mapName.getValue(), scenarioB);
                } else if (event.getValue().equalsIgnoreCase("scenario b")) {
                    createConceptsForMap(mapName.getValue(), scenarioC);
                }
            }
        });

//        connections.setVisible(false);
        conceptGrid.setVisible(false);
        conceptGrid.setColumnReorderingAllowed(true);
        conceptGrid.setHeightByRows(true);

        fromConcept.setPlaceholder("c1");
        toConcept.setPlaceholder("c2");
//        addConcepts.setEnabled(false);
        addConnection.setEnabled(false);
        executeMap.setEnabled(false);
        setCustomScenario.setVisible(false);
        add(mapName, actions, concepts, newScenario, columnLayout, setCustomScenario, addConnection, executeMap, conceptGrid);
        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save(mapName.getValue()));

        save.addClickListener(e -> save(mapName.getValue()));
        save.addClickListener(e -> newScenario.setVisible(true));

        delete.addClickListener(e -> delete(mapName.getValue()));
//        addConcepts.addClickListener(e -> createConceptsForMap(mapName.getValue()));
        addConnection.addClickListener(e -> setConnections());
        executeMap.addClickListener(e -> execute(mapName.getValue()));
        executeMap.addClickListener(e -> conceptGrid.setItems(c.getByName(mapName.getValue()).getConcepts()));
        executeMap.addClickListener(e -> conceptGrid.setVisible(true));
        newScenario.addClickListener(e -> columnLayout.setVisible(true));
        newScenario.addClickListener(e -> setCustomScenario.setVisible(true));

        setCustomScenario.addClickListener(e -> createCustomConcepts());
        setVisible(false);
    }

    private void createCustomConcepts() {
        double[] arr = {numberField1.getValue(), numberField2.getValue(), numberField3.getValue(), numberField3.getValue(),
                numberField4.getValue(), numberField5.getValue(), numberField6.getValue(), numberField7.getValue(),
                numberField8.getValue(), numberField9.getValue(), numberField10.getValue(), numberField11.getValue(),
                numberField12.getValue(), numberField13.getValue()};
        createConceptsForMap(mapName.getValue(), arr);
    }

    private void createConceptsForMap(String s, double[] w) {
        try {
            conceptService.addFlexConcept("c1", "Wrong IT tools selection", s, w[0]);
            conceptService.addFlexConcept("c2", "IT software releases unstability", s, w[1]);
            conceptService.addFlexConcept("c3", "Complex IT integration within current\n" +
                    "infrastructure", s, w[2]);
            conceptService.addFlexConcept("c4", "Unrealistic expectations", s, w[3]);
            conceptService.addFlexConcept("c5", "Low Top management support", s, w[4]);
            conceptService.addFlexConcept("c6", "Low users’ involvement", s, w[5]);
            conceptService.addFlexConcept("c7", "Inadequate consulting services", s, w[6]);
            conceptService.addFlexConcept("c8", "High costs", s, w[7]);
            conceptService.addFlexConcept("c9", "Wrong IT project management", s, w[8]);
            conceptService.addFlexConcept("c10", "High reliability requirements", s, w[9]);
            conceptService.addFlexConcept("c11", "Wrong legacy systems management", s, w[10]);
            conceptService.addFlexConcept("c12", "IT security issues", s, w[11]);
            conceptService.addFlexConcept("c13", "Low Performance", s, w[12]);
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
            changeHandler.onChange();
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
//            addConcepts.setEnabled(true);
            concepts.setVisible(true);
//            connections.setVisible(true);
            executeMap.setEnabled(true);
            changeHandler.onChange();
        } catch (CognitiveMapBadRequestException e) {
            Notification notification = new Notification(e.getMessage(), 3000);
            notification.open();
        }
    }

//    private void createConnection(String name, String desc, double weight, String mapName, String fromC, String toC) {
//        if (weight >= 0 && weight <= 1) {
//            try {
//                connectionService.addConnection(name, desc, weight, mapName, fromC, toC);
////                executeMap.setEnabled(true);
//                changeHandler.onChange();
//            } catch (CognitiveMapBadRequestException e) {
//                Notification notification = new Notification(e.getMessage(), 3000);
//                notification.open();
//
//            }
//        } else {
//            Notification notification = new Notification("Please, enter correct weight!", 3000);
//            notification.open();
//            connectionWeight.focus();
//        }
//    }

    private void setConnections() {
        connectionService.addConnection("c1>c2", "", 0.4, mapName.getValue(), "c1", "c2");
        connectionService.addConnection("c1>c3", "", 0.8, mapName.getValue(), "c1", "c3");
        connectionService.addConnection("c1>c4", "", 0.4, mapName.getValue(), "c1", "c4");
        connectionService.addConnection("c1>c6", "", 0.6, mapName.getValue(), "c1", "c6");
        connectionService.addConnection("c1>c8", "", 0.2, mapName.getValue(), "c1", "c8");
        connectionService.addConnection("c1>c12", "", 0.7, mapName.getValue(), "c1", "c12");
        connectionService.addConnection("c1>c13", "", 0.6, mapName.getValue(), "c1", "c13");
        connectionService.addConnection("c2>c3", "", 0.7, mapName.getValue(), "c2", "c3");
        connectionService.addConnection("c2>c6", "", 0.5, mapName.getValue(), "c2", "c6");
        connectionService.addConnection("c2>c7", "", 0.2, mapName.getValue(), "c2", "c7");
        connectionService.addConnection("c2>c8", "", 0.3, mapName.getValue(), "c2", "c8");
        connectionService.addConnection("c2>c11", "", 0.6, mapName.getValue(), "c2", "c11");
        connectionService.addConnection("c2>c12", "", 0.7, mapName.getValue(), "c2", "c12");
        connectionService.addConnection("c3>c8", "", 0.8, mapName.getValue(), "c3", "c8");
        connectionService.addConnection("c3>c13", "", 0.3, mapName.getValue(), "c3", "c13");
        connectionService.addConnection("c4>c5", "", 0.6, mapName.getValue(), "c4", "c5");
        connectionService.addConnection("c4>c6", "", 0.4, mapName.getValue(), "c4", "c6");
        connectionService.addConnection("c4>c9", "", 0.1, mapName.getValue(), "c4", "c9");
        connectionService.addConnection("c6>c11", "", 0.2, mapName.getValue(), "c6", "c11");
        connectionService.addConnection("c6>c13", "", 0.6, mapName.getValue(), "c6", "c13");
        connectionService.addConnection("c7>c1", "", 0.8, mapName.getValue(), "c7", "c1");
        connectionService.addConnection("c7>c8", "", 0.9, mapName.getValue(), "c7", "c8");
        connectionService.addConnection("c7>c9", "", 0.6, mapName.getValue(), "c7", "c9");
        connectionService.addConnection("c7>c11", "", 0.7, mapName.getValue(), "c7", "c11");
        connectionService.addConnection("c7>c13", "", 0.8, mapName.getValue(), "c7", "c13");
        connectionService.addConnection("c8>c4", "", 0.7, mapName.getValue(), "c8", "c4");
        connectionService.addConnection("c8>c5", "", 0.8, mapName.getValue(), "c8", "c5");
        connectionService.addConnection("c9>c3", "", 0.3, mapName.getValue(), "c9", "c3");
        connectionService.addConnection("c9>c6", "", 0.4, mapName.getValue(), "c9", "c6");
        connectionService.addConnection("c9>c8", "", 0.4, mapName.getValue(), "c9", "c8");
        connectionService.addConnection("c9>c11", "", 0.3, mapName.getValue(), "c9", "c11");
        connectionService.addConnection("c10>c8", "", 0.4, mapName.getValue(), "c10", "c8");
        connectionService.addConnection("c11>c3", "", 0.7, mapName.getValue(), "c11", "c3");
        connectionService.addConnection("c12>c5", "", 0.2, mapName.getValue(), "c12", "c5");
        connectionService.addConnection("c13>c5", "", 0.3, mapName.getValue(), "c13", "c5");
    }
}