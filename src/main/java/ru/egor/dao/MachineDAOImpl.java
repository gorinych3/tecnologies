package ru.egor.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.egor.entity.Machine;
import ru.egor.entity.MyPath;

import java.util.List;

@Repository
@Transactional
public class MachineDAOImpl implements MachineDAO{

    private SessionFactory sessionFactory;
    private MyPathDAOImpl myPathDAO;

    @Autowired
    public MachineDAOImpl(SessionFactory sessionFactory, MyPathDAOImpl myPathDAO) {
        this.sessionFactory = sessionFactory;
        this.myPathDAO = myPathDAO;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Machine> showMachine() {
        return sessionFactory.getCurrentSession().createQuery("from Machine").list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Machine getMachineById(int id) {
        Query<Machine> machineQuery = sessionFactory.getCurrentSession().createQuery("from Machine where machId = :paramName");
        machineQuery.setParameter("paramName", id);
        return machineQuery.getSingleResult();
    }

    @Override
    public int addMachine(Machine machine) {
        sessionFactory.getCurrentSession().save(machine);
        return machine.getMachId();
    }

    @Override
    public void deleteMachineById(int machId) {
        Machine machine = getMachineById(machId);
        sessionFactory.getCurrentSession().delete(machine);
        List <MyPath> paths = myPathDAO.getMypathForOneMachine(machId);
        myPathDAO.deletePathes(paths);
    }
}
