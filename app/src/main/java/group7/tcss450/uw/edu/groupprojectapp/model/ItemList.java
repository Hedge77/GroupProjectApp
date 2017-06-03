package group7.tcss450.uw.edu.groupprojectapp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Andy on 2017. 6. 2..
 */

public class ItemList implements Serializable {
    private List<Item> list;


    public List<Item> getList() {
        return list;
    }

    public void setList(List<Item> items) {
        list = items;
    }
}
