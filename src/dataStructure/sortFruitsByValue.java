package dataStructure;

import java.util.Comparator;

public class sortFruitsByValue implements Comparator<GraphFruit>{


    @Override
    public int compare(GraphFruit f1, GraphFruit f2) {
        return f1.getValue() - f2.getValue(); 

    }



}
