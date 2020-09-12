package reader.XMLFileReader;

import java.util.ArrayList;

public class Hierarchy {

    ArrayList<String> hierarchyList;

    public Hierarchy(ArrayList<String> hierarchyList){
        this.hierarchyList = hierarchyList;
    }

    public int getNumberOfLevels(){
        int size = this.hierarchyList.size();
        return size;
    }

    public int getLevel(String value){
        int level = this.hierarchyList.indexOf(value);
        return level;
    }

}
