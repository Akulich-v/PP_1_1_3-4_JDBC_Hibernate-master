package jm.task.core.jdbc.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final Util util = new Util();



    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        SessionFactory sessionFactory = util.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            String sql = "CREATE TABLE IF NOT EXISTS mybdtwst " +
                    "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                    "age TINYINT NOT NULL)";

            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();

            session.getTransaction().commit();
        } finally {
            session.close();
        }

    }

    @Override
    public void dropUsersTable() {
        SessionFactory sessionFactory = util.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();

            String sql = "DROP TABLE IF EXISTS mybdtwst";
            Query query = session.createSQLQuery(sql);
            query.executeUpdate();

            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User();
        SessionFactory sessionFactory = util.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();

            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            session.persist(user);

            session.getTransaction().commit();
        } finally {
            System.out.println(user);
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        User user = new User();
        SessionFactory sessionFactory = util.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();

            User userToDelete = session.find(User.class,id);
            session.remove(userToDelete);

            session.getTransaction().commit();

        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        User user = new User();
        SessionFactory sessionFactory = util.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        List<User> usersList = new ArrayList<>();

        try {
            session.beginTransaction();
            usersList = session.createQuery("FROM User", User.class).list();
            session.getTransaction().commit();
        } finally {
            session.close();
        }

        return usersList;
    }

    @Override
    public void cleanUsersTable() {
        User user = new User();
        SessionFactory sessionFactory = util.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

            String hql = "DELETE FROM User";

            Query query = session.createQuery(hql);
            query.executeUpdate();

        session.getTransaction().commit();
        session.close();
    }
}
