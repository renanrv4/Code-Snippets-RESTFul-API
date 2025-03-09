package dio.lab.restapi.service;

import dio.lab.restapi.domain.model.Snippet;
import dio.lab.restapi.domain.repository.SnippetRepository;
import dio.lab.restapi.exception.BusinessException;
import dio.lab.restapi.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SnippetService {

    private final SnippetRepository snippetRepository;

    public SnippetService(SnippetRepository snippetRepository) {
        this.snippetRepository = snippetRepository;
    }

    @Transactional(readOnly = true)
    public List<Snippet> findAll() {
        return snippetRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Snippet findById(Long id) {
        return snippetRepository.findById(id).orElseThrow(() -> new NotFoundException("O snippet com o ID: " + id + " Não existe"));
    }

    @Transactional
    public Snippet create(Snippet snippetToCreate) {
        validateSnippet(snippetToCreate);
        if (snippetToCreate.getUser() == null || snippetToCreate.getUser().getId() == null) {
            throw new BusinessException("O snippet deve ter um usuário associado.");
        }
        return snippetRepository.save(snippetToCreate);
    }

    @Transactional
    public Snippet update(Long id, Snippet snippetToUpdate) {
        Snippet dbSnippet = findById(id);
        dbSnippet.setTitle(snippetToUpdate.getTitle());
        dbSnippet.setCode(snippetToUpdate.getCode());
        dbSnippet.setLanguage(snippetToUpdate.getLanguage());
        dbSnippet.setUpdatedAt(snippetToUpdate.getUpdatedAt());

        return snippetRepository.save(dbSnippet);
    }

    private void validateSnippet(Snippet snippet) {
        if (snippet == null) {
            throw new BusinessException("O Snippet não pode ser nulo");
        }
        if (snippet.getTitle() == null || snippet.getTitle().isEmpty()) {
            throw new BusinessException("O título do snippet não pode ser vazio");
        }
        if (snippet.getCode() == null || snippet.getCode().isEmpty()) {
            throw new BusinessException("O código do snippet não pode ser vazio");
        }
    }
}
