package reader.readers.wikiReader.webpagereader;

import lombok.Getter;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import reader.readers.wikiReader.Entity;
import reader.readers.wikiReader.WebPageReader;

import java.io.IOException;

public class AwardReader {
    private static final String MAIN_AWARDS = "main awards";
    private static final String BEST_FILM = "best film";
    private static final String BEST_DIRECTOR = "best director";
    private static final String BEST_ACTOR = "best actor";
    private static final String BEST_ACTRESS = "best actress";
    private static final String BEST_SUPPORTING_ACTOR = "best supporting actor";
    private static final String BEST_SUPPORTING_ACTRESS = "best supporting actress";
    private static final String BEST_COMIC_ACTOR = "best comic actor";
    private static final String BEST_VILLAIN = "best villain";
    private static final String BEST_DEBUT = "best debut";
    private static final String LUX_NEW_FACE_OF_THE_YEAR = "lux new face of the year";
    private static final String BEST_STORY = "best story";
    private static final String EARLY_LIFE = "best screenplay";
    private static final String BEST_DIALOGUE = "best dialogue";
    private static final String BEST_MUSIC = "best music";
    private static final String BEST_LYRICS = "best lyrics";
    private static final String BEST_PLAYBACK_SINGER_MALE = "best playback singer, male";
    private static final String BEST_PLAYBACK_SINGER_FEMALE = "best playback singer, female";
    private static final String BEST_ACTION = "best action";
    private static final String BEST_ART_DIRECTOR = "best art direction";
    private static final String BEST_CHOREOGRAPHY = "best choreography";
    private static final String BEST_CINEMATOGRAPHY = "best cinematography";
    private static final String BEST_EDITING = "best editing";
    private static final String BEST_SOUND = "best sound";
    private static final String LIFETIME_ACHIEVEMENT_AWARD = "lifetime achievement award";

    @Getter
    private Entity bestFilm;
    @Getter
    private Entity bestDirector;
    @Getter
    private Entity bestActor;
    @Getter
    private Entity bestActress;
    @Getter
    private Entity bestSupportingActor;
    @Getter
    private Entity bestSupportingActress;
    @Getter
    private Entity bestComicActor;
    @Getter
    private Entity bestVillain;
    @Getter
    private Entity bestDebut;
    @Getter
    private Entity luxNewFaceOfTheYear;
    @Getter
    private Entity bestStory;
    @Getter
    private Entity earlyLife;
    @Getter
    private Entity bestDialogue;
    @Getter
    private Entity bestMusic;
    @Getter
    private Entity bestLyrics;
    @Getter
    private Entity bestPlaybackSingerMale;
    @Getter
    private Entity bestPlaybackSingerFemale;
    @Getter
    private Entity bestAction;
    @Getter
    private Entity bestArtDirector;
    @Getter
    private Entity bestChoreography;
    @Getter
    private Entity bestCinematography;
    @Getter
    private Entity bestEditing;
    @Getter
    private Entity bestSound;
    @Getter
    private Entity lifetimeAchievementAward;


    protected Document HTMLDocument;

    public AwardReader(String url) throws IOException {
        try {
            HTMLDocument = Jsoup.connect(url).get();
        } catch (HttpStatusException ex) {
            System.out.println(ex);
        }

        readAwards();
    }

    public void readAwards(){

        bestFilm=readAward(BEST_FILM);
        bestDirector=readAward(BEST_DIRECTOR);
        bestDirector=readAward(BEST_ACTOR);
        bestActress=readAward(BEST_ACTRESS);
        bestSupportingActor=readAward(BEST_SUPPORTING_ACTOR);
        bestSupportingActress=readAward(BEST_SUPPORTING_ACTRESS);
        bestComicActor=readAward(BEST_COMIC_ACTOR);
        bestVillain=readAward(BEST_VILLAIN);
        bestDebut=readAward(BEST_DEBUT);
        luxNewFaceOfTheYear=readAward(LUX_NEW_FACE_OF_THE_YEAR);
        bestStory=readAward(BEST_STORY);
        earlyLife=readAward(EARLY_LIFE);
        bestDialogue=readAward(BEST_DIALOGUE);
        bestMusic=readAward(BEST_MUSIC);
        bestLyrics=readAward(BEST_LYRICS);
        bestPlaybackSingerMale=readAward(BEST_PLAYBACK_SINGER_MALE);
        bestPlaybackSingerFemale=readAward(BEST_PLAYBACK_SINGER_FEMALE);
        bestAction=readAward(BEST_ACTION);
        bestArtDirector=readAward(BEST_ART_DIRECTOR);
        bestChoreography=readAward(BEST_CHOREOGRAPHY);
        bestCinematography=readAward(BEST_CINEMATOGRAPHY);
        bestEditing=readAward(BEST_EDITING);
        bestSound=readAward(BEST_SOUND);
        lifetimeAchievementAward=readAward(LIFETIME_ACHIEVEMENT_AWARD);
    }
    public Entity readAward(String awardType) {

        Entity awardee=new Entity();
        String competing;

       /* Elements mainSectionGroup = this.HTMLDocument.select("h2");
        Element correctSectionGroup = null;
        for (Element currentSectionGroup : mainSectionGroup)
        {
            String cleanSectionGroupTitle = currentSectionGroup.text().trim().toLowerCase();
            if (cleanSectionGroupTitle.indexOf(MAIN_AWARDS) >= 0) {
                correctSectionGroup = currentSectionGroup;
                break;
            }
        }*/
        Elements mainSections = this.HTMLDocument.select("h3");

        for (Element currentSection : mainSections) {
            String sectionTitle = currentSection.text();
            Elements spans = currentSection.select("span");
            boolean foundMainFilm = false;
            boolean foundMainActor = false;
            for ( Element currentSpan : spans ){
                String currentAttribute = currentSpan.attr("id");
                String currentAttribute2 = currentSpan.id();
                if (currentAttribute.compareTo("Best_Film")==0){
                    foundMainFilm=true;
                }
                if (currentAttribute.compareTo("Best_Actor")==0){
                    foundMainActor=true;
                }
            }
            //System.out.println("section title:" + sectionTitle);

            String cleanSectionTitle = sectionTitle.trim().toLowerCase();
            boolean canShow = true;

            if (awardType==BEST_FILM){
                if (!foundMainFilm){
                    canShow = false;
                }
            }
            if (awardType==BEST_ACTOR){
                if (!foundMainActor){
                    canShow = false;
                }
            }
            if (cleanSectionTitle.indexOf(awardType) >= 0 && canShow) {

                System.out.println("Found plot chapter:" + sectionTitle);

                String elementTag = "";
                Element nextSibling = currentSection.nextElementSibling();
                String awardeeName = nextSibling.text();
                Elements insideLine = nextSibling.select("a");
                String awardeeUrl = insideLine.attr("href");

                awardee.setName(awardeeName);
                awardee.setUrl(awardeeUrl);

                if (awardeeUrl != "") {
                    System.out.println("{\"value\":\"" + awardee.getName() + "\",\"href\":\"" + awardee.getUrl() + "\"}");
                } else {
                    System.out.println("{\"value\":\"" + awardee.getName() + "\"}");
                }
            }
        }

        return awardee;
    }
}