package com.torama.model;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.torama.model.hib.User;
import com.torama.model.i.IUserModel;

public class UserImpl extends BasicModel<User> implements IUserModel {
	@Override
	public final User persistOrMerge(final User user) throws Exception {
		checkAttributes(user);
    	if (isDuplicate(user)) {
    		throw new Exception(
    				"There is already a line with the same properties " +
    				"[Number: " + user.getId() + "]");
    	}
    	return (User) sessionFactory.getCurrentSession().merge(user);
	}
	
	/**
     * check all attributes of the station weather there are NullPointers
     * @param station - the station you want to save
	 * @throws Exception 
     */
    private final void checkAttributes(final User user) throws Exception {
    	final String exceptionStr = "The line has an invalide Attribute."+
    								"[Number: " + user.getId() + "]";
    	if (user.getId() == 0) {
    		throw new Exception(
    				exceptionStr + "Number: NULL ]");
    	}
    }
    
    /**
	 * @param station - the station you want to save
	 * @return weather another station with the same name and location already exists 
	 */
	private final boolean isDuplicate(final User user) {
		if (user.getId() != 0) { return false; }
		List<User> userList = findByCriterion(User.class, 
				Restrictions.eq("number", user.getId()));
		return userList.size() == 1;
	}
}