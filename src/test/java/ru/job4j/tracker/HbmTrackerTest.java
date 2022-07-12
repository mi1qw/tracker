package ru.job4j.tracker;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class HbmTrackerTest {
    private static final Store HBM = new HbmTracker();

    @Test
    public void add() {
        Item item = new Item("Ann", "description_Ann");
        assertNull(item.getId());
        HBM.add(item);
        int id = item.getId();
        assertTrue(id > 0);
    }

    @Test
    public void replace() {
        Item item = new Item("Ann", "description_Ann");
        HBM.add(item);
        int id = item.getId();
        HBM.replace(id, new Item("Zed", "description_Zed"));
        item = HBM.findById(id);
        assertEquals("Zed", item.getName());
    }

    @Test
    public void delete() {
        Item item = new Item("Ann", "description_Ann");
        HBM.add(item);
        int id = item.getId();
        HBM.delete(id);
        item = HBM.findById(id);
        assertNull(item);
    }

    @Test
    public void findAll() {
        HBM.add(new Item("Ann", "description_Ann"));
        HBM.add(new Item("Zed", "description_Zed"));
        List<Item> list = HBM.findAll();
        assertEquals(2, list.size());
    }

    @Test
    public void findByName() {
        Item item = new Item("Ann", "description_Ann");
        HBM.add(item);
        item.setName("A");
        List<Item> list = HBM.findByName("Ann");
        assertEquals("Ann", list.iterator().next().getName());
    }

    @Test
    public void findById() {
        Item item = new Item("Ann", "description_Ann");
        HBM.add(item);
        Integer id = item.getId();
        item.setId(0);
        item = HBM.findById(id);
        assertEquals(id, item.getId());
    }
}
