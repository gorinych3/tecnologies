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
    private MyPathDAOImpl myPathDAO;

    @Autowired
    public ElementDAOImpl(SessionFactory sessionFactory, MyPathDAOImpl myPathDAO) {
        this.sessionFactory = sessionFactory;
        this.myPathDAO = myPathDAO;
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
    public Element getElementById(int id) {
        Query<Element> elementQuery = sessionFactory.getCurrentSession().createQuery("from Element where elId = :paramName");
        elementQuery.setParameter("paramName", id);
        return elementQuery.getSingleResult();
    }

    @Override
    public int addElement(Element element) {
        sessionFactory.getCurrentSession().save(element);
        return element.getElId();
    }

    @Override
    public void updateElement(Element element) {
        sessionFactory.getCurrentSession().update(element);
    }

    @Override
    public void deleteElement(int id) {
        Element element = getElementById(id);
        sessionFactory.getCurrentSession().delete(element);
        List <MyPath> pathes = myPathDAO.getMypathForOneElement(id);
        myPathDAO.deletePathes(pathes);
    }

}
