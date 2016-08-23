package com.testtask.dao;

import com.testtask.model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
    private static final int limitResultsPerPage = 5;

    private SessionFactory sessionFactory;


    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        Session session = sessionFactory.getCurrentSession();
        List<User> list = session.createQuery("FROM User").list();
        for(User user : list) {
            logger.info("User list: " + user);
        }
        return list;
    }

    @Override
    public void createUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(user);
        logger.info("User successfully created. User details: " + user);
    }

    @Override
    public User readUser(int id) {
        Session session = sessionFactory.getCurrentSession();
        User user = (User) session.load(User.class, id);
        logger.info("User successfully updated. User details: " + user);
        return user;
    }

    @Override
    public void updateUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.update(user);
        logger.info("User successfully updated. User details: " + user);
    }

    @Override
    public void deleteUser(int id) {
        Session session = sessionFactory.getCurrentSession();
        User user = (User) session.load(User.class, id);
        if (user != null) {
            session.delete(user);
        }
        logger.info("User successfully deleted. User details: " + user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getUsersByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM User WHERE name = :name");
        query.setParameter("name", name);
        List<User> result = query.list();
        for (User user : result) {
            logger.info("User list info: " + user);
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getUsers(Long page) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM User");
        query.setFirstResult((int)(page - 1) * limitResultsPerPage);
        query.setMaxResults(limitResultsPerPage);
        List<User> users = query.list();
        for (User user : users) {
            logger.info("User list info: " + user);
        }
        return users;
    }
}
