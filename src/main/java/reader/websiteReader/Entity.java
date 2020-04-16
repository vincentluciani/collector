package reader.websiteReader;

import lombok.Getter;
import lombok.Setter;

public class Entity {

    @Getter
    @Setter
    private String question;
    private String answer;
    private String category;
    private String subcategory;
}
