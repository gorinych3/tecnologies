package ru.egor.DAO;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.egor.OtherClasses.StorageFileNotFoundException;
import ru.egor.entity.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public class ElementDAOImpl implements ElementDAO {


    @Autowired
    private SessionFactory sessionFactory;




    //блок получения списков entity-------------------------------------------------------------------------------------

    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public List<Element> showAllElements() {
        List<Element> elements = sessionFactory.getCurrentSession().createQuery("from Element").list();
        System.out.println(elements);
        return elements;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Plate> showPlates() {
        return sessionFactory.getCurrentSession().createQuery("from Plate").list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Machine> showMachine() {
        return sessionFactory.getCurrentSession().createQuery("from Machine").list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MyTool> getMyTools() {
        return sessionFactory.getCurrentSession().createQuery("from MyTool").list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MyPath> getMypathAll() {
        return sessionFactory.getCurrentSession().createQuery("from MyPath").list();
    }



    //блок получения entity по заданным параметрам----------------------------------------------------------------------

    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public List<Element> showElementByName(String nameElement) {
        Query <Element> elements = sessionFactory.getCurrentSession().createQuery("from Element where nameElement = :paramName");
        elements.setParameter("paramName", nameElement);
        return elements.getResultList();
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
    public List<MyPath> getMypathForOnePlate(int plateId) {
        Query<MyPath> path = sessionFactory.getCurrentSession().createQuery("from MyPath where plateId = :paramName");
        path.setParameter("paramName", plateId);
        return path.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Plate getPlateById(int id) {
        Query<Plate> plate = sessionFactory.getCurrentSession().createQuery("from Plate where plateId = :paramName");
        plate.setParameter("paramName", id);
        return plate.getSingleResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public MyTool getToolById(int id) {
        Query<MyTool> myTool = sessionFactory.getCurrentSession().createQuery("from MyTool where toolId = :paramName");
        myTool.setParameter("paramName", id);
        return myTool.getSingleResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MyPath> getMypathForOneTool(int toolId) {
        Query<MyPath> path = sessionFactory.getCurrentSession().createQuery("from MyPath where mytoolId = :paramName");
        path.setParameter("paramName", toolId);
        return path.getResultList();
    }


    //блок добавления entity--------------------------------------------------------------------------------------------

    @Override
    public void addElement(Element element) {
    }

    @Override
    public int addPlate(Plate plate) {
        sessionFactory.getCurrentSession().save(plate);
        //System.out.println(plate.getPlateId());
        return plate.getPlateId();
    }

    @Override
    public void addPathPlate(MyPath path) {
        //String insert = "insert into Path (plateId, pathName) select path.plateId, path.pathName from Path";
        //sessionFactory.getCurrentSession().createQuery(insert);
        sessionFactory.getCurrentSession().save(path);
    }

    @Override
    public int addTool(MyTool myTool) {
        sessionFactory.getCurrentSession().save(myTool);
        return myTool.getToolId();
    }

    @Override
    public int addMachine(Machine machine) {
        sessionFactory.getCurrentSession().save(machine);
        return machine.getMachId();
    }

    //блок редактирования-----------------------------------------------------------------------------------------------

    @Override
    public void updateElement(Element element) {

    }


    //блок удаления-----------------------------------------------------------------------------------------------------

    @Override
    public void deleteElement(String name) {

    }

    @SuppressWarnings("unchecked")
    @Override
    public void deletePlateById(int plateId) {
        Plate plate = getPlateById(plateId);
        sessionFactory.getCurrentSession().delete(plate);
        List <MyPath> pathes = getMypathForOnePlate(plateId);
        deletePathes(pathes);
    }

    @Override
    public void deleteToolById(int toolId) {
        MyTool myTool = getToolById(toolId);
        sessionFactory.getCurrentSession().delete(myTool);
        List <MyPath> pathes = getMypathForOneTool(toolId);
        deletePathes(pathes);
    }

    private void deletePathes(List<MyPath> paths){
        for(MyPath path : paths){
            File file = new File(path.getPathName());
            if(file.delete()) {
                sessionFactory.getCurrentSession().delete(path);
            }
        }
    }
}
