package ru.egor.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.egor.entity.MyPath;
import ru.egor.entity.Plate;

import java.util.List;

@Repository
@Transactional
public class PlateDAOImpl implements PlateDAO{

    private SessionFactory sessionFactory;
    private MyPathDAOImpl myPathDAO;

    @Autowired
    public PlateDAOImpl(SessionFactory sessionFactory, MyPathDAOImpl myPathDAO) {
        this.sessionFactory = sessionFactory;
        this.myPathDAO = myPathDAO;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Plate> showPlates() {
        return sessionFactory.getCurrentSession().createQuery("from Plate").list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Plate getPlateByModel(String model) {
        Query<Plate> plate = sessionFactory.getCurrentSession().createQuery("from Plate where model = :paramName");
        plate.setParameter("paramName", model);
        return plate.getSingleResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Plate getPlateById(int id) {
        Query<Plate> plate = sessionFactory.getCurrentSession().createQuery("from Plate where plateId = :paramName");
        plate.setParameter("paramName", id);
        return plate.getSingleResult();
    }

    @Override
    public int addPlate(Plate plate) {
        sessionFactory.getCurrentSession().save(plate);
        return plate.getPlateId();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void deletePlateById(int plateId) {
        Plate plate = getPlateById(plateId);
        sessionFactory.getCurrentSession().delete(plate);
        List <MyPath> pathes = myPathDAO.getMypathForOnePlate(plateId);
        myPathDAO.deletePathes(pathes);
    }
}
