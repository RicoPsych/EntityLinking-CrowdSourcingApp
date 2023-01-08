package project.app.named_entity.named_entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.app.named_entity.text.Text;

@Repository
public interface NamedEntityRepository extends JpaRepository<NamedEntity,Long>{
    public List<NamedEntity> findByText(Text text);
}
