package dio.lab.restapi.controller;

import dio.lab.restapi.domain.model.Snippet;
import dio.lab.restapi.domain.model.User;
import dio.lab.restapi.domain.repository.UserRepository;
import dio.lab.restapi.service.SnippetService;
import dio.lab.restapi.exception.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/snippets")
public class SnippetController {

    private final SnippetService snippetService;

    @Autowired
    private UserRepository userRepository;

    public SnippetController(SnippetService snippetService) {
        this.snippetService = snippetService;
    }

    @GetMapping
    @Operation(summary = "Get all snippets from a user", description = "Retrieve a list of all registered snippets")
    public ResponseEntity<List<Snippet>> getAllSnippets() {
        List<Snippet> snippets = snippetService.findAll();
        return new ResponseEntity<>(snippets, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a snippet by its ID", description = "Retrieve a specific snippet based on its ID")
    public ResponseEntity<Snippet> getSnippetById(@PathVariable Long id) {
        Snippet snippet = snippetService.findById(id);
        return new ResponseEntity<>(snippet, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create a new snippet")
    public ResponseEntity<Snippet> createSnippet(@RequestBody Snippet snippet) {
        try {
            User user = userRepository.findById(snippet.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            snippet.setUser(user);
            Snippet createdSnippet = snippetService.create(snippet);

            return new ResponseEntity<>(createdSnippet, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update a snippet", description = "Update the data of an existing snippet based on its ID")
    public ResponseEntity<Snippet> updateSnippet(@PathVariable Long id, @RequestBody Snippet snippet) {
        try {
            Snippet updatedSnippet = snippetService.update(id, snippet);
            return new ResponseEntity<>(updatedSnippet, HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a snippet", description = "Delete an existing snippet based on its ID")
    public ResponseEntity<Void> deleteSnippet(@PathVariable Long id) {
        try {
            snippetService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (BusinessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
