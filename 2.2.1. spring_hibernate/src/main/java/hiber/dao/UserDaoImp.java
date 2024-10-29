package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   private SessionFactory sessionFactory;

   @Autowired
   public void setSessionFactory(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUserThisCar(int series, String model) {
      TypedQuery<Car> query=sessionFactory.getCurrentSession().createQuery("from Car " +
              "where series =:series and model =:model", Car.class);
      query.setParameter("series", series);
      query.setParameter("model", model);
      Car car = query.getSingleResult();
      TypedQuery<User> query1=sessionFactory.getCurrentSession().createQuery("from User " +
              "where userCar =:car", User.class);
      query1.setParameter("car", car);
      return query1.getSingleResult();
   }

}
