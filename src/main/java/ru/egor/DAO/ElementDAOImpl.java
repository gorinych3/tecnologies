package ru.egor.DAO;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.egor.entity.Element;
import ru.egor.entity.MyTool;
import ru.egor.entity.Plate;

import java.util.List;
import java.util.Set;

@Repository
@Transactional
public class ElementDAOImpl implements ElementDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public List<Element> showAllElements() {
        List<Element> elements = sessionFactory.getCurrentSession().createQuery("from Element").list();
        System.out.println(elements);
        return elements;
    }

    @SuppressWarnings("unchecked")
    @Transactional
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

    @SuppressWarnings("unchecked")
    @Override
    public List<MyTool> getMyTools() {
        List<MyTool> tools = (List<MyTool>) sessionFactory.getCurrentSession().createQuery("from MyTool").list();
        return tools;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Plate> showPlates() {
        return sessionFactory.getCurrentSession().createQuery("from Plate").list();
    }

    @Override
    public void addPlate(Plate plate) {
        sessionFactory.getCurrentSession().save(plate);
    }
}
