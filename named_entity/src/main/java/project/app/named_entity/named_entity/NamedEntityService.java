package project.app.named_entity.named_entity;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import project.app.named_entity.text.Text;

@Service
public class NamedEntityService {
    NamedEntityRepository repository;

    @Autowired
    NamedEntityService(NamedEntityRepository repo){
        this.repository = repo;
    }

    public Optional<NamedEntity> find(Long id){
        return repository.findById(id);
    }

    public List<NamedEntity> findAll(){
        return repository.findAll();
    }

    public List<NamedEntity> findByText(Text text){
        return repository.findByText(text);
    }

    @Transactional
    public NamedEntity add(NamedEntity entity){
        return repository.save(entity);
    }

    @Transactional
    public void delete(NamedEntity entity){
        repository.delete(entity);
    }

    @Transactional
    public void update(NamedEntity new_entity){
        repository.findById(new_entity.getId())
        .ifPresent(entity -> {
            entity.setIndexStart(new_entity.getIndexStart());
            entity.setIndexEnd(new_entity.getIndexEnd());
            entity.setKb_link(new_entity.getKb_link());
            entity.setText(new_entity.getText());
            entity.setType(new_entity.getType());
        });
    }


}
