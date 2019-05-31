package ru.egor.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.egor.entity.MyPath;

import java.io.File;
import java.util.List;

@Repository
@Transactional
public class MyPathDAOImpl implements MyPathDAO{

    private SessionFactory sessionFactory;

    @Autowired
    public MyPathDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MyPath> getMypathAll() {
        return sessionFactory.getCurrentSession().createQuery("from MyPath").list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MyPath> getMypathForOneElement(int elId) {
        Query<MyPath> path = sessionFactory.getCurrentSession().createQuery("from MyPath where elementId = :paramName");
        path.setParameter("paramName", elId);
        return path.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MyPath> getMypathForOnePlate(int plateId) {
        Query<MyPath> path = sessionFactory.getCurrentSession().createQuery("from MyPath where plateId = :paramName");
        path.setParameter("paramName", plateId);
        return path.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MyPath> getMypathForOneTool(int toolId) {
        Query<MyPath> path = sessionFactory.getCurrentSession().createQuery("from MyPath where mytoolId = :paramName");
        path.setParameter("paramName", toolId);
        return path.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MyPath> getMypathForOneMachine(int machId) {
        Query<MyPath> path = sessionFactory.getCurrentSession().createQuery("from MyPath where machineid = :paramName");
        path.setParameter("paramName", machId);
        return path.getResultList();
    }

    @Override
    public void addPathPlate(MyPath path) {
        sessionFactory.getCurrentSession().save(path);
    }

    void deletePathes(List<MyPath> paths){
        for(MyPath path : paths){
            File file = new File(path.getPathName());
            if(file.delete()) {
                sessionFactory.getCurrentSession().delete(path);
            }
        }
    }
}
