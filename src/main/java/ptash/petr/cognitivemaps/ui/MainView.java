package ptash.petr.cognitivemaps.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import ptash.petr.cognitivemaps.service.impl.CognitiveMapServiceImpl;
import ptash.petr.cognitivemaps.web.protocol.response.CognitiveMapDto;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;


@Route("")
public class MainView extends VerticalLayout {

    private Grid<CognitiveMapDto> grid = new Grid<>(CognitiveMapDto.class);
    private final TextField filter = new TextField();
    private final Button createMapButton = new Button("Create map", VaadinIcon.PLUS.create());
    private final HorizontalLayout toolbar = new HorizontalLayout(filter, createMapButton);
    private CognitiveMapServiceImpl repo;
    private final CognitiveMapEditor editor;

    @Autowired
    public MainView(CognitiveMapServiceImpl fcmService, CognitiveMapEditor editor) {
        this.editor = editor;
        this.repo = fcmService;

        setSpacing(true);
        setSizeFull();

        grid.setVerticalScrollingEnabled(true);
        grid.setColumnReorderingAllowed(true);
        grid.setHeightByRows(true);
        // grid.getColumnByKey("name").setFlexGrow(1);
        grid.getColumnByKey("connections").setFlexGrow(1);

        filter.setPlaceholder("Filter by map name");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        add(toolbar, grid, editor);

        editor.save.addClickListener(e -> grid.setItems(fcmService.getAll()));
        editor.delete.addClickListener(e -> grid.setItems(fcmService.getAll()));
        createMapButton.addClickListener(e -> editor.setVisible(true));
        editor.addFLexConcept.addClickListener(e -> grid.setItems(fcmService.getAll()));
        editor.addHardConcept.addClickListener(e -> grid.setItems(fcmService.getAll()));

        editor.setChangeHandler(() -> {
            fillList(filter.getValue());
        });
    }

    private void fillList(String name) {
        if (name.isEmpty()) {
            grid.setItems(this.repo.getAll());
        } else
            grid.setItems(this.repo.getByName(name));
    }
}