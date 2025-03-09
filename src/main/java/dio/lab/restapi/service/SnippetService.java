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

        return snippetRepository.save(snippetToCreate);
    }

    @Transactional
    public Snippet update(Long id, Snippet snippetToUpdate) {
        Snippet dbSnippet = findById(id);

        if (!dbSnippet.getId().equals(snippetToUpdate.getId())) {
            throw new BusinessException("O ID deve ser o mesmo");
        }

        validateSnippet(snippetToUpdate);

        dbSnippet.setTitle(snippetToUpdate.getTitle());
        dbSnippet.setCode(snippetToUpdate.getCode());
        dbSnippet.setLanguage(snippetToUpdate.getLanguage());
        dbSnippet.setUpdatedAt(snippetToUpdate.getUpdatedAt());

        return snippetRepository.save(dbSnippet);
    }

    @Transactional
    public void delete(Long id) {
        Snippet dbSnippet = findById(id);
        snippetRepository.delete(dbSnippet);
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
