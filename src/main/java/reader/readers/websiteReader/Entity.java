package reader.readers.websiteReader;

import lombok.Getter;
import lombok.Setter;

public class Entity {

    @Getter
    @Setter
    private String question;
    @Getter
    @Setter
    private String answer;
    @Getter
    @Setter
    private String category;
    @Getter
    @Setter
    private String subcategory;

    public Entity(String category, String subcategory, String question, String answer) {
        this.category = category;
        this.subcategory = subcategory;
        this.question = question;
        this.answer = answer;
    }
}
