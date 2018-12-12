package com.mycompany.a3.interfaces;

public interface ICollider {
	// apply appropriate detection algorithm
	public boolean collidesWith(ICollider otherObject);
	
	// apply appropriate response algorithm
	public void handleCollision(ICollider otherObject);


}
