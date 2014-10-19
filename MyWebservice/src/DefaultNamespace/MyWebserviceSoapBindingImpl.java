/**
 * MyWebserviceSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package DefaultNamespace;

public class MyWebserviceSoapBindingImpl implements DefaultNamespace.MyWebservice{
    public java.lang.String hello(java.lang.String name) throws java.rmi.RemoteException {
    	System.out.println("The Given Input is"+name);
    	
    	
    	
        return name+"The Output is : I am Venkat";
    }

}
