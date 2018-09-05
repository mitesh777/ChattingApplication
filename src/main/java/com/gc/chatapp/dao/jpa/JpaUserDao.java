package com.gc.chatapp.dao.jpa;

import java.util.List;
import java.text.ParseException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
//chatmangement
import org.springframework.stereotype.Repository;

import com.gc.chatapp.dao.UserDao;
import com.gc.chatapp.entities.ChatUser;
import com.gc.chatapp.entities.IndividualChatMessage;
import com.gc.chatapp.entities.User;
import com.gc.chatapp.entities.UserKey;

@Repository
public class JpaUserDao implements UserDao {

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Override
	public List<ChatUser> retrieveAllChatUsers() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		String querystr = "select chatuser from ChatUser chatuser";
		TypedQuery<ChatUser> query = entityManager.createQuery(querystr, ChatUser.class);
		List<ChatUser> retrievedChatUsers = query.getResultList();
		entityManager.close();
		return retrievedChatUsers;
	}

	@Override
	public ChatUser addNewChatUser(ChatUser currentChatUser, ChatUser newChatUser) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		ChatUser tempChatUser = entityManager.find(ChatUser.class, currentChatUser.getEmailId());
		tempChatUser.getIndividualContacts().add(newChatUser);
		entityManager.merge(tempChatUser);
		entityManager.getTransaction().commit();
		entityManager.close();
		return tempChatUser;
	}

	@Override
	public void saveChatUser(ChatUser chatUser, String secretKey) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(chatUser);
		entityManager.flush();	
		UserKey userKey = new UserKey(secretKey, chatUser);
		entityManager.persist(userKey);
		entityManager.flush();
		entityManager.getTransaction().commit();
		entityManager.close();

	}
	
	@Override
	public ChatUser getChatUserById(long userId) throws ParseException {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
        ChatUser user = entityManager.find(ChatUser.class, userId);
        return user;
	}
	
	@Override
	public void updateUser(ChatUser user) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(user);
        entityManager.getTransaction().commit();
        entityManager.close();
	}

	@Override
	public void updateUserStatus(ChatUser user) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(user);
        entityManager.getTransaction().commit();
        entityManager.close();
	}
	
	@Override
	public boolean checkPassword(ChatUser user) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		ChatUser u = entityManager.find(ChatUser.class, user.getUserId());
		return user.getPassword().equals(u.getPassword());
	}
	
	@Override
	public void resetPassword(long userId, String oldPassword, String newPassword) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
		ChatUser user = entityManager.find(ChatUser.class, userId);
		user.setPassword(newPassword);
		entityManager.getTransaction().begin();
        entityManager.merge(user);
        entityManager.getTransaction().commit();
        entityManager.close();	
	}	
	
	@Override
	public void indexDao(User user) {
		EntityManager entityManager = entityManagerFactory
				.createEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entityManager.persist(user);
		tx.commit();
		entityManager.close();
	}

	@Override
	public void indexChatUserDao(ChatUser user) {
		EntityManager entityManager = entityManagerFactory
				.createEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entityManager.persist(user);
		tx.commit();
		entityManager.close();
	}
	@Override
	public void persistIndividualMessage(
			IndividualChatMessage individualChatMessage, ChatUser user) {
		ChatUser me = getChatUserByEmail(user.getEmailId());
		individualChatMessage.setSender(me);
		EntityManager entityManager = entityManagerFactory
				.createEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entityManager.persist(individualChatMessage);
		tx.commit();
		entityManager.close();
		// makes an entry in to
		// the database
	}


	@Override
	public ChatUser getChatUserByEmail(String email) {
		EntityManager entityManager = entityManagerFactory
				.createEntityManager();	
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		String querystr = "select ae from ChatUser ae where ae.emailId=:email";
		TypedQuery<ChatUser> query = entityManager.createQuery(querystr, ChatUser.class);	
		query.setParameter("email", email);
		ChatUser retrievedChatUser = (ChatUser) query.getSingleResult();
		tx.commit();
		entityManager.close();
		System.out.println(retrievedChatUser);
		return retrievedChatUser;
	}

	@Override
	public void updateUser(User user) {
		EntityManager entityManager = entityManagerFactory
				.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.merge(user);
		entityManager.getTransaction().commit();
		entityManager.close();

	}

	@Override
	public void updateUserStatus(User user) {
		EntityManager entityManager = entityManagerFactory
				.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.merge(user);
		entityManager.getTransaction().commit();
		entityManager.close();

	}

	@Override
	public boolean checkPassword(User user) {
		// TODO Auto-generated method stub
		EntityManager entityManager = entityManagerFactory
				.createEntityManager();
		User u = entityManager.find(User.class, user.getEmailId());
		return user.getPassword() == u.getPassword();
	}

	@Override
	public UserKey getSecretKeyForChatUser(ChatUser chatUser) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		String querystr = "select cu from ChatUser cu where cu.emailId=:email";
		TypedQuery<ChatUser> query1 = entityManager.createQuery(querystr, ChatUser.class);
		query1.setParameter("email", chatUser.getEmailId());
		ChatUser user = query1.getSingleResult();
		 querystr = "select cu from UserKey cu where cu.user.userId=:userID";
		TypedQuery<UserKey> query = entityManager.createQuery(querystr, UserKey.class);
		query.setParameter("userID", user.getUserId());
		UserKey userKey = query.getSingleResult();
		return userKey;
	}
	
	public User retrieveUserByEmailId(String emailId) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		String hql = "SELECT c FROM ChatUser c WHERE c.emailId=:eId";
		TypedQuery<ChatUser> query = entityManager.createQuery(hql, ChatUser.class);
		query.setParameter("eId", emailId);
		ChatUser chatUser = query.getSingleResult();
		//System.out.println("User retrieved!");
		entityManager.close();
		return chatUser;
	}
	
	public ChatUser validateUser(String emailId, String password) throws NoResultException {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		String hql = "SELECT c FROM ChatUser c WHERE c.emailId=:eId AND c.password=:pwd";
		TypedQuery<ChatUser> query = entityManager.createQuery(hql, ChatUser.class);
		query.setParameter("eId", emailId);
		query.setParameter("pwd", password);
		ChatUser chatUser = query.getSingleResult();
		System.out.println("User authenticated");
		entityManager.close();
		return chatUser;
	}
	
	@Override
	public int updatePassword(String emailId, String password) throws NoResultException {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		String hql = "UPDATE ChatUser c SET c.password=:pwd WHERE c.emailId=:eId";
		//String hql1 = "UPDATE User u SET u.password=:pwd WHERE u.emailId=:eId";
		
		Query query = entityManager.createQuery(hql);
		//Query query1 = entityManager.createQuery(hql1);
		
		//query1.setParameter("eId", emailId);
		//query1.setParameter("pwd", password);
		
		query.setParameter("eId", emailId);
		query.setParameter("pwd", password);
		
		int rowsUpdated = query.executeUpdate();
		//int rowsUpdated1 = query1.executeUpdate();
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		return rowsUpdated;
		}
		
		@Override
	public ChatUser retrieveChatUserByEmailId(String emailId) throws NoResultException {
		System.out.println("DAO Triggered");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		String hql = "SELECT c FROM ChatUser c WHERE c.emailId=:eId";
		TypedQuery<ChatUser> query = entityManager.createQuery(hql, ChatUser.class);
		query.setParameter("eId", emailId);
		ChatUser chatUser = query.getSingleResult();
		System.out.println("User retrieved!");
		System.out.println(chatUser.getEmailId());
		entityManager.close();
		return chatUser;
	}
	
	@Override
	public String retrieveSecretKey(long userId) throws NoResultException {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		String hql = "SELECT k FROM UserKey k JOIN ChatUser n ON k.chatUser.userId = n.userId AND k.chatUser.userId=:uId";
		TypedQuery<UserKey> query = entityManager.createQuery(hql, UserKey.class);
		query.setParameter("uId", userId);
		UserKey userKey = query.getSingleResult();
		entityManager.close();
		return userKey.getSecretKey();
	}
	
	@Override
	public ChatUser retrieveUserBySecretKey(String secretKey) throws NoResultException{
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		String hql = "SELECT u FROM UserKey u WHERE u.secretKey=:sk";
		TypedQuery<UserKey> query = entityManager.createQuery(hql, UserKey.class);
		query.setParameter("sk", secretKey);
		UserKey userKey = query.getSingleResult();
		entityManager.close();
		return userKey.getChatUser();
	}

}