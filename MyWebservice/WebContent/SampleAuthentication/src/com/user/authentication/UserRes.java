/**
 * UserRes.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.user.authentication;

public class UserRes  implements java.io.Serializable {
    private java.lang.String responseCode;

    private java.lang.String responseMessage;

    private java.lang.String sessionTokenId;

    public UserRes() {
    }

    public UserRes(
           java.lang.String responseCode,
           java.lang.String responseMessage,
           java.lang.String sessionTokenId) {
           this.responseCode = responseCode;
           this.responseMessage = responseMessage;
           this.sessionTokenId = sessionTokenId;
    }


    /**
     * Gets the responseCode value for this UserRes.
     * 
     * @return responseCode
     */
    public java.lang.String getResponseCode() {
        return responseCode;
    }


    /**
     * Sets the responseCode value for this UserRes.
     * 
     * @param responseCode
     */
    public void setResponseCode(java.lang.String responseCode) {
        this.responseCode = responseCode;
    }


    /**
     * Gets the responseMessage value for this UserRes.
     * 
     * @return responseMessage
     */
    public java.lang.String getResponseMessage() {
        return responseMessage;
    }


    /**
     * Sets the responseMessage value for this UserRes.
     * 
     * @param responseMessage
     */
    public void setResponseMessage(java.lang.String responseMessage) {
        this.responseMessage = responseMessage;
    }


    /**
     * Gets the sessionTokenId value for this UserRes.
     * 
     * @return sessionTokenId
     */
    public java.lang.String getSessionTokenId() {
        return sessionTokenId;
    }


    /**
     * Sets the sessionTokenId value for this UserRes.
     * 
     * @param sessionTokenId
     */
    public void setSessionTokenId(java.lang.String sessionTokenId) {
        this.sessionTokenId = sessionTokenId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UserRes)) return false;
        UserRes other = (UserRes) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.responseCode==null && other.getResponseCode()==null) || 
             (this.responseCode!=null &&
              this.responseCode.equals(other.getResponseCode()))) &&
            ((this.responseMessage==null && other.getResponseMessage()==null) || 
             (this.responseMessage!=null &&
              this.responseMessage.equals(other.getResponseMessage()))) &&
            ((this.sessionTokenId==null && other.getSessionTokenId()==null) || 
             (this.sessionTokenId!=null &&
              this.sessionTokenId.equals(other.getSessionTokenId())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getResponseCode() != null) {
            _hashCode += getResponseCode().hashCode();
        }
        if (getResponseMessage() != null) {
            _hashCode += getResponseMessage().hashCode();
        }
        if (getSessionTokenId() != null) {
            _hashCode += getSessionTokenId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UserRes.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://authentication.user.com", ">UserRes"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("responseCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://authentication.user.com", "ResponseCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://authentication.user.com", ">ResponseCode"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("responseMessage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://authentication.user.com", "ResponseMessage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://authentication.user.com", ">ResponseMessage"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sessionTokenId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://authentication.user.com", "SessionTokenId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://authentication.user.com", ">SessionTokenId"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
