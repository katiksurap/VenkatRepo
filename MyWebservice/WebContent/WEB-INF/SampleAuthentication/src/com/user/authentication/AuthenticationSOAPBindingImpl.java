/**
 * AuthenticationSOAPBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.user.authentication;

public class AuthenticationSOAPBindingImpl implements com.user.authentication.AuthenticationPort {
	
	public com.user.authentication.UserRes authentication(com.user.authentication.UserReq userReq) throws java.rmi.RemoteException {
		
		UserRes userRes = new UserRes();
		
		if (userReq != null) {
			
			if (userReq.getUserName() == null) {
				userRes.setResponseCode("1");
				userRes.setResponseMessage("Invalid Request : User Name is Empty");
				userRes.setSessionTokenId("");
				return userRes;
			}
			
			if (userReq.getPassword() == null) {
				userRes.setResponseCode("2");
				userRes.setResponseMessage("Invalid Request : Passowrd is Empty");
				userRes.setSessionTokenId("");
				return userRes;
			}
			// Do call for data base and if success set session id
			userRes.setResponseCode("0");
			userRes.setResponseMessage("Success");
			userRes.setSessionTokenId("345454");
		}
		return userRes;
	}

}
