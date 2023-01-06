package project.app.text_tag.text_tag;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextTagRepository extends JpaRepository<TextTag,Long>{
    Optional<TextTag> findByName(String name);
}
