<%@ page language="java" pageEncoding="UTF8"%><%if(request.getParameter("signup_expected_backend_version")==null) throw new Exception("{\"error\":\"Es gibt Verständigungsschwierigkeiten zwischen Anwendung und Server; bitte melden Sie sich beim Anbieter Ihrer App.\",\"errorDebug\":\"Der Pflichtparameter >signup-expected-backend-version< wurde nicht übergeben.\",\"errorcode\":2,\"severity\":100}");if(!request.getParameter("signup_expected_backend_version").startsWith("1-0")) throw new Exception("{\"error\":\"Es gibt einen Versionskonflikt: Ihre App ist mit diesem Server nicht kompatibel!\",\"errorDebug\":\"Es wäre eine Versionsnummer mit >1-0...< erwartet worden.\",\"errorcode\":3,\"severity\":100}");%>