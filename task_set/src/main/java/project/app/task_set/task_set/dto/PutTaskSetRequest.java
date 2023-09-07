package project.app.task_set.task_set.dto;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.task_set.ne_type.NamedEntityType;
import project.app.task_set.task_set.TaskSet;
import project.app.task_set.text.Text;

@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PutTaskSetRequest {
    private Long[] texts;
    private Long[] namedEntityTypes;    
    /**
     * <p> PUT </p>
     * Static function to map DTO to TaskSet entity for updating purposes
     * @param textGetter function for getting Text entities list from ids list
     * @param typeGetter function for getting NamedEntitiesType entities list from ids list
     * @return TaskSet 
     */
    public static BiFunction<TaskSet,PutTaskSetRequest,TaskSet> dtoToEntityUpdater(
        Function<Long[],List<Text>> textGetter,
        Function<Long[],List<NamedEntityType>> typeGetter
    ){
        return (taskSet, request) -> {
            
            taskSet.setTexts(textGetter.apply(request.getTexts()));
            taskSet.setNamedEntityTypes(typeGetter.apply(request.getNamedEntityTypes()));
            return taskSet;
        };
    }
}
