package ru.job4j.hql;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;


public class HbnTrackerTest {
    private static final RepoInterface HBN = HbnTracker.getINSTANCE();

    @Test
    public void add() {
        Candidate candidate = Candidate.of("Ann", 2, BigDecimal.valueOf(110));
        assertEquals(0, candidate.getId());
        HBN.add(candidate);
        int id = candidate.getId();
        assertTrue(id > 0);
    }

    @Test
    public void replace() {
        Candidate candidate = Candidate.of("Zed", 2, BigDecimal.valueOf(10));
        HBN.add(candidate);
        int id = candidate.getId();
        HBN.replace(id, Candidate.of("Ozy", 10, BigDecimal.valueOf(1000)));
        candidate = HBN.findById(id);
        assertEquals(0, BigDecimal.valueOf(1000).compareTo(candidate.getSalary()));
        assertEquals("Ozy", candidate.getName());
    }

    @Test
    public void delete() {
        Candidate candidate = Candidate.of("Igor", 2, BigDecimal.valueOf(10));
        HBN.add(candidate);
        int id = candidate.getId();
        HBN.delete(id);
        candidate = HBN.findById(id);
        assertNull(candidate);
    }

    @Test
    public void findAll() {
        HBN.add(Candidate.of("A", 1, BigDecimal.valueOf(10)));
        HBN.add(Candidate.of("B", 2, BigDecimal.valueOf(12)));
        List<Candidate> list = HBN.findAll();
        assertFalse(list.isEmpty());
        assertEquals(2, list.size());
    }

    @Test
    public void findByName() {
        Candidate candidate = Candidate.of("Mike", 2, BigDecimal.valueOf(500));
        HBN.add(candidate);
        candidate.setName("A");
        List<Candidate> list = HBN.findByName("Mike");
        assertEquals("Mike", list.iterator().next().getName());
    }

    @Test
    public void findById() {
        Candidate candidate = Candidate.of("Zed", 2, BigDecimal.valueOf(110));
        HBN.add(candidate);
        int id = candidate.getId();
        candidate.setId(0);
        candidate = HBN.findById(id);
        assertEquals(id, candidate.getId());
    }
}
