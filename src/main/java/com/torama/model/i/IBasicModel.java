package com.torama.model.i;

import java.util.List;

import org.hibernate.criterion.Criterion;

public interface IBasicModel<T> {
    /**
     * Speichert ein Objekt wenn eine ID gesetzt wurde. Ansonsten wird das Objekt aktualisiert.
     * 
     * @param object zu speicherndes / updatendes Objekt.
     * @return id des Objektes.
     * @throws Exception 
     */
    public T persistOrMerge(T object) throws Exception;
    
    /**
     * Lädt ein Objekt mit der angegebenen ID.
     * 
     * @param id
     * @return
     */
    public T load(Class<T> clazz, long id);
    
    /**
     * Hiermit wird das übergebene Objekt gelöscht
     * 
     * @param object
     */
    public void remove(T object);
    
    /**
     * 
     * @param clazz
     * @param criterions
     * @return
     */
    public T findUniqueByCriterion(Class<T> model, Criterion... criterions);
    
    /**
     * Findet 
     * 
     * @param clazz
     * @param criterion
     * @return
     */
    public List<T> findByCriterion(Class<T> model, Criterion... criterions);
}
