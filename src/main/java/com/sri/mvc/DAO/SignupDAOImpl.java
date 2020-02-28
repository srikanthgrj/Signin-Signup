package com.sri.mvc.DAO;

import java.util.Objects;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sri.mvc.Entity.SignuEntity;

@Component
public class SignupDAOImpl implements SignupDAO {
	@Autowired
	private SessionFactory factory;

	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}

	public void save(SignuEntity ent) {
		System.out.println("entered save");

		try {
			// Session session = Factory.openSession();
			/*
			 * System.out.println("dao implement start"); System.out.println(ent);
			 * Configuration cfg = new Configuration(); cfg.configure();
			 * cfg.addAnnotatedClass(SignuEntity.class); SessionFactory fsc =
			 * cfg.buildSessionFactory();
			 */
			Session session = factory.openSession();
			session.beginTransaction();
			System.out.println("tx begin");
			System.out.println("data saving");
			if (Objects.nonNull(ent)) {
				session.save(ent);
			} else {
				System.out.println("cannot save");
			}

			session.getTransaction().commit();
			System.out.println("commited");

			System.out.println("all resource closed");
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public SignuEntity fetch(String email) {
		Session session = factory.openSession();
		try {
			System.out.println("invoked mail " + email);

			Query query = session.getNamedQuery("fetchbyemail");
			query.setParameter("mail", email);
			System.out.println("query " + query);
			Object str =query.uniqueResult();
			if (Objects.nonNull(str)) { 
				System.out.println("email exists");
				SignuEntity ent =(SignuEntity)str;
				return ent;
			}

			else {
				System.out.println("email not exixt");
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();

		}
		return null;
	}

	public SignuEntity fetchbymailandpassword(String mail, String password) {
		Session session = factory.openSession();
		try {
			System.out.println("invoked signin fromIMPL");

			Query query = session.getNamedQuery("fetchbyemailandpassword");
			query.setParameter("mail", mail);
			query.setParameter("pwd", password);
			System.out.println("query " + query);
			Object out =query.uniqueResult();
			if (Objects.nonNull(out)) {
				SignuEntity ent =(SignuEntity)out;
				return ent;
			}

			else {
				System.out.println("not available");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();

		}
		return null;
	}

}
