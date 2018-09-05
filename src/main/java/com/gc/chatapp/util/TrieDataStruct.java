package com.gc.chatapp.util;
import java.util.*;

import com.gc.chatapp.dto.SearchEntity;

public class TrieDataStruct {

    private final TrieNode root;
    private final int MAX_COUNT = 50;

    private class TrieNode {
        Map<Character, TrieNode> children;
        boolean endOfName;
        List<SearchEntity> searchEntities;
        
        public TrieNode() {
            children = new HashMap<>();
            searchEntities = new ArrayList<>();
            endOfName = false;
        }
    }

    public TrieDataStruct() {
        root = new TrieNode();
    }

    public void insert(String name, SearchEntity searchEntity) {
        TrieNode current = root;

        for (int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);
            
            if(Character.isLetter(ch))
            	ch = Character.toUpperCase(ch);
            
            TrieNode node = current.children.get(ch);
            if (node == null) {
                node = new TrieNode();
                if(Character.isLetter(ch))
                	current.children.put(ch , node);
                else
                	current.children.put(ch, node);
            }
            current = node;
        }
        current.endOfName = true;
        current.searchEntities.add(searchEntity);
    }

    public List<SearchEntity> searchEntitiesByName(String name) {
        TrieNode current = root;
        for (int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);
            TrieNode node = current.children.get(ch);
            if (node == null) {
                return new ArrayList<>();
            }
            current = node;
        }
        return current.searchEntities;
    }

    public List<SearchEntity> searchEntitiesByType(String name, SearchType searchType) {

        List<SearchEntity> entities = new ArrayList<>();

        TrieNode current = root;

        for (int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);
            
            if(Character.isLetter(ch))
            	ch = Character.toUpperCase(ch);
            
            TrieNode node = current.children.get(ch);
        
            if (node == null) {
                return entities;
            }
            current = node;
        }

        entities = addEntitiesToList(current, entities, searchType);

        return entities;
    }

    private List<SearchEntity> addEntitiesToList(TrieNode node , List<SearchEntity> entities, SearchType searchType) {

        if(entities.size() > MAX_COUNT)
            return entities;

        for(SearchEntity searchEntity : node.searchEntities) {
            if(searchType.equals(SearchType.ALL) || searchType.equals(searchEntity.getType()))
                entities.add(searchEntity);
        }

        for(TrieNode current : node.children.values())
        	entities = addEntitiesToList(current, entities, searchType);
        
        return entities;
     }
    
    public List<SearchEntity> getAllEntities() {
    
    	List<SearchEntity> entities = new ArrayList<>();

        TrieNode current = root;

        entities = addAllEntitiesToList(current, entities);

        return entities;
    	
    }
    
    private List<SearchEntity> addAllEntitiesToList(TrieNode node , List<SearchEntity> entities) {

        for(SearchEntity searchEntity : node.searchEntities) {
            entities.add(searchEntity);
        }

        for(TrieNode current : node.children.values())
        	entities = addAllEntitiesToList(current, entities);
        
        return entities;
     }
}
