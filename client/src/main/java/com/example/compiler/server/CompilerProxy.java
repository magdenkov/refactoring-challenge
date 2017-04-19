package com.example.compiler.server;

public class CompilerProxy implements com.example.compiler.server.Compiler {
  private String _endpoint = null;
  private com.example.compiler.server.Compiler compiler = null;
  
  public CompilerProxy() {
    _initCompilerProxy();
  }
  
  public CompilerProxy(String endpoint) {
    _endpoint = endpoint;
    _initCompilerProxy();
  }
  
  private void _initCompilerProxy() {
    try {
      compiler = (new com.example.compiler.server.CompilerServiceLocator()).getCompiler();
      if (compiler != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)compiler)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)compiler)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (compiler != null)
      ((javax.xml.rpc.Stub)compiler)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.example.compiler.server.Compiler getCompiler() {
    if (compiler == null)
      _initCompilerProxy();
    return compiler;
  }
  
  public com.example.compiler.server.Result buildAndRun(java.lang.String code, java.lang.String data) throws java.rmi.RemoteException{
    if (compiler == null)
      _initCompilerProxy();
    return compiler.buildAndRun(code, data);
  }
  
  
}