package com.gc.chatapp.dao.jpa;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gc.chatapp.dao.BroadcastDao;
import com.gc.chatapp.entities.BroadCastChatMessage;
import com.gc.chatapp.entities.ChatBroadcast;
import com.gc.chatapp.entities.ChatUser;
import com.gc.chatapp.exceptions.BroadcastNotFound;
import com.gc.chatapp.services.UserService;

@Repository
public class BroadcastDaoImpl implements BroadcastDao {
	@Autowired
	private EntityManagerFactory factory;
	@Autowired
	private UserService userservice;
	
	@Override
	public long createBroadcast(ChatBroadcast chatBroadcast) throws SQLException {

		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(chatBroadcast);
		em.getTransaction().commit();
		em.close();

		return chatBroadcast.getChatBroadcastId();
	}

	@Override
	public void addUserToBroadcast(long userId, long broadcastId) throws SQLException {
		EntityManager em = factory.createEntityManager();
		Set<ChatUser> chatUsers = em.find(ChatBroadcast.class,broadcastId).getChatBroadcastMembers();
		ChatUser userToAdd = null;
		try {
			userToAdd = userservice.getChatUserById(userId);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		em.getTransaction().begin();
		if(userToAdd!=null)
		chatUsers.add(userToAdd);
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public void removeUserFromBroadcast(long userId, long broadcastId) throws SQLException {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		Set<ChatUser> chatUsers = em.find(ChatBroadcast.class, broadcastId)
				.getChatBroadcastMembers();
		ChatUser userToRemove = em.find(ChatUser.class, userId);
		chatUsers.remove(userToRemove);
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public void editBroadcastName(String name, long broadcastId) throws SQLException, BroadcastNotFound {

		EntityManager em = factory.createEntityManager();
		ChatBroadcast broadcastObj = em.find(ChatBroadcast.class, broadcastId);
		em.getTransaction().begin();
		if(broadcastObj!=null){
		broadcastObj.setChatBroadcastName(name);}
		else{
			throw new BroadcastNotFound();
		}
		em.getTransaction().commit();
		em.close();

	}

	@Override
	public void deleteBroadcast(long broadcastId) throws SQLException {

		EntityManager em = factory.createEntityManager();
		ChatBroadcast broadcastObj =em.find(ChatBroadcast.class, broadcastId);
		em.getTransaction().begin();
		em.remove(broadcastObj);
		em.getTransaction().commit();
		em.close();

	}

	@Override
	public List<ChatBroadcast> getListOfBroadcast(long userId) throws SQLException {
		EntityManager em = factory.createEntityManager();
		String jpql = "from ChatBroadcast cb where cb.chatBroadcastCreator.userId=:userId";
		TypedQuery<ChatBroadcast> query = em.createQuery(jpql, ChatBroadcast.class);
		query.setParameter("userId", userId);
		List<ChatBroadcast> broadcasts = query.getResultList();
		em.close();
		return broadcasts;
	}

	@Override
	public ChatBroadcast getBroadcastById(long broadcastId) throws SQLException {

		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		String jpql = "from ChatBroadcast b where b.chatBroadcastId=:broadcastId";
		TypedQuery<ChatBroadcast> query = em.createQuery(jpql,
				ChatBroadcast.class);
		query.setParameter("broadcastId", broadcastId);
		ChatBroadcast chatBroadcast = query.getSingleResult();
		em.getTransaction().commit();
		em.close();

		return chatBroadcast;

	}

	@Override
	public List<BroadCastChatMessage> getMessageListForBroadcast(long chatBroadcastId) throws SQLException {
		// TODO Auto-generated method stub
		EntityManager em = factory.createEntityManager();
		String jpql = "from  BroadCastChatMessage cb where chatBroadcast_id=:chatBroadcastId";
		TypedQuery<BroadCastChatMessage> query = em.createQuery(jpql, BroadCastChatMessage.class);
		query.setParameter("chatBroadcastId", chatBroadcastId);
		List<BroadCastChatMessage> messageList = query.getResultList();
		em.close();
		return messageList;
	}

	@Override
	public void createBroadcastMessage(BroadCastChatMessage message) throws SQLException {
		// TODO Auto-generated method stub
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(message);
		em.getTransaction().commit();
		em.close();
	}
}
