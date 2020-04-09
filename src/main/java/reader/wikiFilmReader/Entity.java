package reader.wikiFilmReader;
import lombok.Getter;
import lombok.Setter;


public class Entity {

    @Getter
    @Setter
    private String name;
    private String url;

    public Entity(String name,String url){
        this.name = name;
        this.url = url;
    }

}
