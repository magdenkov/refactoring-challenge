/**
 * Compiler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.example.compiler.server;

public interface Compiler extends java.rmi.Remote {
    public com.example.compiler.server.Result buildAndRun(java.lang.String code, java.lang.String data) throws java.rmi.RemoteException;
}
