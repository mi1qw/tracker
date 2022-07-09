package ru.job4j.hql;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;


public class HbnTrackerTest {
    private static final RepoInterface HBN = HbnTracker.getINSTANCE();
    private static Candidate newCandidate;


    @Test
    public void add() {
        Candidate candidate = Candidate.of("Ann", 2, BigDecimal.valueOf(110));
        assertEquals(0, candidate.getId());
        HBN.add(candidate);
        int id = candidate.getId();
        assertTrue(id > 0);
        HBN.delete(id);
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
        HBN.delete(id);
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
        HBN.add(Candidate.of("A", 2, BigDecimal.valueOf(10)));
        HBN.add(Candidate.of("B", 2, BigDecimal.valueOf(10)));
        List<Candidate> list = HBN.findAll();
        assertFalse(list.isEmpty());
        list.forEach(cnd -> HBN.delete(cnd.getId()));
    }

    @Test
    public void findByName() {
        Candidate candidate = Candidate.of("Mike", 2, BigDecimal.valueOf(500));
        HBN.add(candidate);
        candidate.setName("A");
        List<Candidate> list = HBN.findByName("Mike");
        assertEquals("Mike", list.iterator().next().getName());
        list.forEach(cnd -> HBN.delete(cnd.getId()));
    }

    @Test
    public void findById() {
        Candidate candidate = Candidate.of("Zed", 2, BigDecimal.valueOf(110));
        assertEquals(0, candidate.getId());
        HBN.add(candidate);
        int id = candidate.getId();
        assertTrue(id > 0);
        candidate.setId(0);
        candidate = HBN.findById(id);
        assertEquals(id, candidate.getId());
        HBN.delete(id);
    }
}
