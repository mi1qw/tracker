package ru.job4j.hql;

import java.util.List;

public interface RepoInterface {
    Candidate add(Candidate candidate);

    boolean replace(int id, Candidate candidate);

    boolean delete(int id);

    List<Candidate> findAll();

    List<Candidate> findByName(String name);

    Candidate findById(int id);
}
