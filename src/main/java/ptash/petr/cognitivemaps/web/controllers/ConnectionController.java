package ptash.petr.cognitivemaps.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptash.petr.cognitivemaps.service.api.ConnectionService;
import ptash.petr.cognitivemaps.web.protocol.request.AddConnectionRequest;
import ptash.petr.cognitivemaps.web.protocol.request.DeleteConnectionRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/connection")
public class ConnectionController {

    private final ConnectionService connectionService;

    @Autowired
    public ConnectionController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addConnection(@RequestBody @Valid AddConnectionRequest request) {
        connectionService.addConnection(request.getConnectionName(), request.getDescription(), request.getWeight(),
                request.getMapName(), request.getFromConceptName(), request.getToConceptName());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteConnection(@RequestBody @Valid DeleteConnectionRequest request) {
        connectionService.deleteConnectionFromMap(request.getConnectionName(), request.getMapName());
        return ResponseEntity.ok().build();
    }
}
