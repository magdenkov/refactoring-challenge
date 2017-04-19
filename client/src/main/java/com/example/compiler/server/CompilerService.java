/**
 * CompilerService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.example.compiler.server;

public interface CompilerService extends javax.xml.rpc.Service {
    public java.lang.String getCompilerAddress();

    public com.example.compiler.server.Compiler getCompiler() throws javax.xml.rpc.ServiceException;

    public com.example.compiler.server.Compiler getCompiler(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
