package project.app.task_set.task;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class PostTasksRequest {
    private Task[] tasksRq;

    @ToString
    @Setter
    @EqualsAndHashCode
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static private class Task {
        private long indexStart;
        private long indexEnd;

        private String startDate;
        private String endDate;
        private long textId;
        private long submitionsNum;
        private long taskSet;
    }


    public static Supplier<PostTasksRequest> CreateRequest(long taskSetId, long textId ,long[] indexes, String startDate){
        return () -> {
            List<Task> tasks = new ArrayList<>();
            
            for (int i = 0; i < indexes.length-1; i++) {
                if (i!=0){
                    tasks.add(new Task(indexes[i]+1, indexes[i+1], startDate, startDate, textId, 0, taskSetId));
                }
                else{
                    tasks.add(new Task(indexes[i], indexes[i+1], startDate, startDate, textId, 0, taskSetId));
                }
            }

            return PostTasksRequest.builder().tasksRq(tasks.toArray(new Task[tasks.size()])).build(); 
        };
    }
}
