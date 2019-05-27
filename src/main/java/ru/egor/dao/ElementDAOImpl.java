package ru.egor.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.egor.entity.*;
import java.util.List;

@Repository
@Transactional
public class ElementDAOImpl implements ElementDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public ElementDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //блок получения списков entity-------------------------------------------------------------------------------------

    @SuppressWarnings("unchecked")
    @Override
    public List<Element> getElements() {
        List<Element> elements = sessionFactory.getCurrentSession().createQuery("from Element").list();
        return elements;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Element> showElementByName(String nameElement) {
        Query <Element> elements = sessionFactory.getCurrentSession().createQuery("from Element where nameElement = :paramName");
        elements.setParameter("paramName", nameElement);
        return elements.getResultList();
    }

    @Override
    public void addElement(Element element) {
    }

    @Override
    public void updateElement(Element element) {

    }

    @Override
    public void deleteElement(String name) {

    }

}
