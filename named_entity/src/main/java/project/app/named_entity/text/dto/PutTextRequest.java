// package project.app.named_entity.text.dto;

// import java.util.List;
// import java.util.function.BiFunction;
// import java.util.function.Function;

// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.EqualsAndHashCode;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;
// import lombok.ToString;
// import project.app.named_entity.named_entity.NamedEntity;
// import project.app.named_entity.text.Text;


// @Builder
// @ToString
// @Setter
// @EqualsAndHashCode
// @Getter
// @AllArgsConstructor
// @NoArgsConstructor
//TODO: niepotrzebne chyba
// public class PutTextRequest {
//     // private long id;
//     private long[] namedEntities;

//     public static BiFunction<Text,PutTextRequest, Text> dtoToEntityUpdater(Function<long[],List<NamedEntity>> entityGetter){

//         return (text,request) -> {
//             text.setNamedEntities(entityGetter.apply(request.getNamedEntities()));
//             return text;
//         };
//     }
// }
