package ru.egor.DAO;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.egor.entity.Element;
import ru.egor.entity.MyTool;
import ru.egor.entity.MyPath;
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
    public int addPlate(Plate plate) {
       sessionFactory.getCurrentSession().save(plate);
        //System.out.println(plate.getPlateId());
        return plate.getPlateId();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Plate getPlateByModel(String model) {
        Query<Plate> plate = sessionFactory.getCurrentSession().createQuery("from Plate where model = :paramName");
        plate.setParameter("paramName", model);
        return plate.getSingleResult();
    }

    @Override
    public void addPathPlate(MyPath path) {
        //String insert = "insert into Path (plateId, pathName) select path.plateId, path.pathName from Path";
        //sessionFactory.getCurrentSession().createQuery(insert);
        sessionFactory.getCurrentSession().save(path);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MyPath> getMypathForOneElement(int plateId) {
        Query<MyPath> path = sessionFactory.getCurrentSession().createQuery("from MyPath where plateId = :paramName");
        path.setParameter("paramName", plateId);
        return path.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MyPath> getMypathAll() {
        return sessionFactory.getCurrentSession().createQuery("from MyPath").list();
    }
}
