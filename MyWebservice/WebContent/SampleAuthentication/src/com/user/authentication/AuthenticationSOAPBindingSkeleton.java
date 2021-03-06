/**
 * AuthenticationSOAPBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.user.authentication;

public class AuthenticationSOAPBindingSkeleton implements com.user.authentication.AuthenticationPort, org.apache.axis.wsdl.Skeleton {
    private com.user.authentication.AuthenticationPort impl;
    private static java.util.Map _myOperations = new java.util.Hashtable();
    private static java.util.Collection _myOperationsList = new java.util.ArrayList();

    /**
    * Returns List of OperationDesc objects with this name
    */
    public static java.util.List getOperationDescByName(java.lang.String methodName) {
        return (java.util.List)_myOperations.get(methodName);
    }

    /**
    * Returns Collection of OperationDescs
    */
    public static java.util.Collection getOperationDescs() {
        return _myOperationsList;
    }

    static {
        org.apache.axis.description.OperationDesc _oper;
        org.apache.axis.description.FaultDesc _fault;
        org.apache.axis.description.ParameterDesc [] _params;
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://authentication.user.com", "UserReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://authentication.user.com", ">UserReq"), com.user.authentication.UserReq.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("authentication", _params, new javax.xml.namespace.QName("http://authentication.user.com", "UserRes"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://authentication.user.com", ">UserRes"));
        _oper.setElementQName(new javax.xml.namespace.QName("", "Authentication"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("authentication") == null) {
            _myOperations.put("authentication", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("authentication")).add(_oper);
    }

    public AuthenticationSOAPBindingSkeleton() {
        this.impl = new com.user.authentication.AuthenticationSOAPBindingImpl();
    }

    public AuthenticationSOAPBindingSkeleton(com.user.authentication.AuthenticationPort impl) {
        this.impl = impl;
    }
    public com.user.authentication.UserRes authentication(com.user.authentication.UserReq userReq) throws java.rmi.RemoteException
    {
        com.user.authentication.UserRes ret = impl.authentication(userReq);
        return ret;
    }

}
