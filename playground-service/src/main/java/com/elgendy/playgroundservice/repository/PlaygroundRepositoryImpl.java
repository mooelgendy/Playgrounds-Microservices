package com.elgendy.playgroundservice.repository;

import com.elgendy.playgroundservice.model.Playground;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * JPA-backed repository.
 *
 * <p>The previous implementation caught and logged every persistence
 * exception, then returned {@code null}. That hid failures and turned
 * database outages into empty responses. Exceptions now propagate so the
 * service and the API layer can report an accurate status code.</p>
 *
 * <p>Cache annotations were removed from this class: it returns managed JPA
 * entities, which are only valid inside the current persistence context.
 * Caching is handled in the service layer instead.</p>
 */
@Repository
public class PlaygroundRepositoryImpl implements PlaygroundRepository {

    private static final String BASE_QUERY = "select p from Playground p";
    private static final String AREA_CLAUSE = "lower(p.area) like :area";
    private static final String NAME_CLAUSE = "lower(p.name) like :name";

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Playground> findAll() {
        return em.createQuery(BASE_QUERY, Playground.class).getResultList();
    }

    @Override
    public List<Playground> search(String area, String name, int offset, int limit) {
        TypedQuery<Playground> query = em.createQuery(queryFor(area, name), Playground.class);
        bindFilters(query, area, name);
        query.setFirstResult(Math.max(offset, 0));
        query.setMaxResults(Math.max(limit, 1));
        return query.getResultList();
    }

    @Override
    public long count(String area, String name) {
        TypedQuery<Long> query = em.createQuery(countQueryFor(area, name), Long.class);
        bindFilters(query, area, name);
        return query.getSingleResult();
    }

    @Override
    public Playground findById(Integer id) {
        return id == null ? null : em.find(Playground.class, id);
    }

    @Override
    public Playground save(Playground playground) {
        em.persist(playground);
        em.flush();
        return playground;
    }

    @Override
    public Playground update(Playground playground) {
        Playground merged = em.merge(playground);
        em.flush();
        return merged;
    }

    @Override
    public void delete(Playground playground) {
        em.remove(playground);
        em.flush();
    }

    private static String queryFor(String area, String name) {
        StringBuilder jpql = new StringBuilder(BASE_QUERY);
        appendFilters(jpql, area, name);
        return jpql.toString();
    }

    private static String countQueryFor(String area, String name) {
        StringBuilder jpql = new StringBuilder("select count(p) from Playground p");
        appendFilters(jpql, area, name);
        return jpql.toString();
    }

    private static void appendFilters(StringBuilder jpql, String area, String name) {
        List<String> clauses = new ArrayList<>();
        if (StringUtils.hasText(area)) {
            clauses.add(AREA_CLAUSE);
        }
        if (StringUtils.hasText(name)) {
            clauses.add(NAME_CLAUSE);
        }
        if (!clauses.isEmpty()) {
            jpql.append(" where ").append(String.join(" and ", clauses));
        }
    }

    private static void bindFilters(TypedQuery<?> query, String area, String name) {
        if (StringUtils.hasText(area)) {
            query.setParameter("area", likePattern(area));
        }
        if (StringUtils.hasText(name)) {
            query.setParameter("name", likePattern(name));
        }
    }

    private static String likePattern(String value) {
        return "%" + value.trim().toLowerCase() + "%";
    }
}
