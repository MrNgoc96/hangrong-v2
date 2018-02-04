package hangrong.model;

import hangrong.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.ArrayList;

@Repository
@Transactional
public class UserDAO {

    @Autowired
    SessionFactory sessionFactory;

    // Mã hóa mật khấu
    public  String encryptPassword(String password)
    {
        String sha1 = null;
        try {
            MessageDigest msdDigest = MessageDigest.getInstance("SHA-1");
            msdDigest.update(password.getBytes("UTF-8"), 0, password.length());
            sha1 = DatatypeConverter.printHexBinary(msdDigest.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sha1.toLowerCase();
    }
    public ArrayList<User> getUsers(int firstResult,int size) {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("From User u");
        query.setFirstResult(firstResult);
        query.setMaxResults(size);
        return (ArrayList<User>) query.list();
    }

    public boolean checkLogin(String username,String password){
        Session session = sessionFactory.getCurrentSession();
        try {
            String hql = "SELECT u FROM User u Where u.username = :username and u.password =:password";
            Query<User> query = session.createQuery(hql);
            query.setParameter("username",username);
            query.setParameter("password",encryptPassword(password));
            return query.list().size() > 0;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public User getUser(String username) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(User.class, username);
    }

    public boolean saveUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        try {
            user.setPassword(encryptPassword(user.getPassword()));
            session.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        try {
            user.setPassword(encryptPassword(user.getPassword()));
            session.update(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(String username) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.delete(username);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



}
