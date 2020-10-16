package reader.readers.wikiReader;

import lombok.Getter;
import lombok.Setter;


public class Entity {

    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String url;

    public Entity(String name, String url) {
        this.name = name;
        this.url = url;
    }
    public Entity() {
        this.name="";
        this.url="";
    }
}
