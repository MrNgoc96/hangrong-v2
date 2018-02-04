package hangrong.model;

import hangrong.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Repository
@Transactional
public class CustomerDAO {

    @Autowired
    SessionFactory sessionFactory;

    public ArrayList<Customer> getCustomers(int firstResult,int size) {
        Session session = sessionFactory.getCurrentSession();
        Query<Customer> query = session.createQuery("FROM Customer");
        query.setFirstResult(firstResult);
        query.setMaxResults(size);
        return (ArrayList<Customer>) query.list();
    }


    public Customer getCustomer(int customerId) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Customer.class, customerId);
    }

    public Customer getCustomerByEmail(String email){
        Session session = sessionFactory.getCurrentSession();
        try {
            String hql = "SELECT c FROM Customer c WHERE c.email = :email";
            Query<Customer> query = session.createQuery(hql);
            query.setParameter("email",email);
            return query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean saveCustomer(Customer Customer) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.save(Customer);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCustomer(Customer Customer) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.update(Customer);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCustomer(int CustomerId) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.delete(CustomerId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



}
