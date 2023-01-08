package project.app.text.text_tag;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextTagRepository extends JpaRepository<TextTag,Long>{

}
