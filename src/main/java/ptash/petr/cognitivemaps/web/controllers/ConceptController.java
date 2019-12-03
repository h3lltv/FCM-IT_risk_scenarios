package ptash.petr.cognitivemaps.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptash.petr.cognitivemaps.service.api.ConceptService;
import ptash.petr.cognitivemaps.web.protocol.request.AddFlexibleConceptRequest;
import ptash.petr.cognitivemaps.web.protocol.request.AddHardConceptRequest;
import ptash.petr.cognitivemaps.web.protocol.request.DeleteConceptRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/concept")
public class ConceptController {

    private final ConceptService conceptService;

    @Autowired
    public ConceptController(ConceptService conceptService) {
        this.conceptService = conceptService;
    }

    @PostMapping("/addFlex")
    public ResponseEntity<Void> addFlexibleConcept(@RequestBody @Valid AddFlexibleConceptRequest request) {
        conceptService.addFlexConcept(request.getConceptName(), request.getConceptDescription(), request.getOutputValue(), request.getMapName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addHard")
    public ResponseEntity<Void> addHardConcept(@RequestBody @Valid AddHardConceptRequest request) {
        conceptService.addHardConcept(request.getConceptName(), request.getConceptDescription(), request.getOutputValue(), request.getMapName());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteConcept(@RequestBody @Valid DeleteConceptRequest request) {
        conceptService.deleteConceptFromMap(request.getConceptName(), request.getMapName());
        return ResponseEntity.ok().build();
    }
}
