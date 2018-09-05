package com.gc.chatapp.dao.jpa;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.springframework.beans.factory.annotation.Autowired;
//chatmangement
import org.springframework.stereotype.Repository;

import com.gc.chatapp.dao.ChatMessageDao;
import com.gc.chatapp.entities.ChatUser;
import com.gc.chatapp.entities.IndividualChatMessage;
import com.gc.chatapp.entities.User;

@Repository
public class ChatMessageDaoImpl implements ChatMessageDao {

	
	@Autowired
	private EntityManagerFactory factory;

	public ChatMessageDaoImpl(EntityManagerFactory factory) {
		super();
		this.factory = factory;
	}

	@Override
	public ChatUser getChatUserById(long userId) {
		EntityManager entityManager = factory.createEntityManager();
		entityManager.getTransaction().begin();
		ChatUser user = entityManager.find(ChatUser.class, userId);
		entityManager.getTransaction().commit();
		entityManager.close();
		return user;
	}

	@Override
	public void addIndividualMessage(IndividualChatMessage individualChatMessage) {
		EntityManager entityManager = factory.createEntityManager(); // manages all the operations on the entity
		entityManager.getTransaction().begin(); // object of type entity transaction
		entityManager.persist(individualChatMessage); // makes an entry in to the database
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Override
	public List<IndividualChatMessage> getAllIndividualMessagesOfAChatUser(long currentUserId, long clickedUserId) {
		EntityManager entityManager = factory.createEntityManager();
		TypedQuery<IndividualChatMessage> query = entityManager
				.createQuery("select m from IndividualChatMessage m where (m.sender=?1 "
						+ "and m.receiver=?2) or (m.sender=?2 and m.receiver=?1)", IndividualChatMessage.class);
		query.setParameter(1, getChatUserById(currentUserId));
		query.setParameter(2, getChatUserById(clickedUserId));
		List<IndividualChatMessage> individualChatMessage = query.getResultList();
		entityManager.close();
		return individualChatMessage;
	}

	@Override
	public Set<ChatUser> getAllChatUsers(long currentUserId) {
		EntityManager entityManager = factory.createEntityManager();
		ChatUser user = entityManager.find(ChatUser.class, currentUserId);
		entityManager.getTransaction().begin();
		Set<ChatUser> setOfChats = user.getIndividualContacts();
		entityManager.getTransaction().commit();
		entityManager.close();
		return setOfChats;
	}

	@Override
	public void removeIndividualMessage(String chatMessageId) {
		EntityManager entityManager = factory.createEntityManager();
		IndividualChatMessage indMessage = entityManager.find(IndividualChatMessage.class, chatMessageId);
		entityManager.getTransaction().begin();
		entityManager.remove(indMessage);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

}
