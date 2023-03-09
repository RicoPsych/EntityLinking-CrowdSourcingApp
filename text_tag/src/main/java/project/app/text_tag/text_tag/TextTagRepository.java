package project.app.text_tag.text_tag;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.app.text_tag.ne_type.NamedEntityType;
import project.app.text_tag.text.Text;

@Repository
public interface TextTagRepository extends JpaRepository<TextTag,Long>{
    Optional<TextTag> findByName(String name);

    List<TextTag> findByTextsContaining(Text text);

    
    List<TextTag> findByNamedEntityTypesContaining(NamedEntityType type);
}
