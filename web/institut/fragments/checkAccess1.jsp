<%@ page language="java" pageEncoding="UTF8"%><%if(user.getDozentAccessLevel()<1) throw new Exception("{\"error\":\"Ihre Berechtigung reicht leider nicht aus, wenden Sie sich bitte an den Administrator.\",\"errorDebug\":\"Schwerer Fehler, da eine Berechtigun <1 vorliegt, mit der ein Login egentlich nicht möglich sein sollte.\",\"errorcode\":4,\"severity\":100}");%>