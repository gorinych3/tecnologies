package ru.egor.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.egor.entity.MyPath;
import ru.egor.entity.MyTool;

import java.util.List;

@Repository
@Transactional
public class MyToolDAOImpl implements MyToolDAO{

    private SessionFactory sessionFactory;
    private MyPathDAOImpl myPathDAO;

    @Autowired
    public MyToolDAOImpl(SessionFactory sessionFactory, MyPathDAOImpl myPathDAO) {
        this.sessionFactory = sessionFactory;
        this.myPathDAO = myPathDAO;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MyTool> getMyTools() {
        return sessionFactory.getCurrentSession().createQuery("from MyTool").list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public MyTool getToolById(int id) {
        Query<MyTool> myTool = sessionFactory.getCurrentSession().createQuery("from MyTool where toolId = :paramName");
        myTool.setParameter("paramName", id);
        return myTool.getSingleResult();
    }

    @Override
    public int addTool(MyTool myTool) {
        sessionFactory.getCurrentSession().save(myTool);
        return myTool.getToolId();
    }

    @Override
    public void deleteToolById(int toolId) {
        MyTool myTool = getToolById(toolId);
        sessionFactory.getCurrentSession().delete(myTool);
        List <MyPath> pathes = myPathDAO.getMypathForOneTool(toolId);
        myPathDAO.deletePathes(pathes);
    }
}
