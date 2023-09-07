package project.app.named_entity.text;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TextRepository extends JpaRepository<Text,Long>{

}
