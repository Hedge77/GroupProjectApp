/**
 * TCSS 450 Mobile Programming
 * Project PhaseI Group 7
 *
 * @author Jisu Shin, Ryan Roe, Brandon Lo
 * @version 1.0
 */

package group7.tcss450.uw.edu.groupprojectapp.model;

import java.io.Serializable;
import java.util.List;

/**
 * ItemList
 *
 * This serializable class is for a list of items that are from the result.
 *
 * @author Jisu Shin
 * @version 1.0
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
