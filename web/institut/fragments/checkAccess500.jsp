<%@ page language="java" pageEncoding="UTF8"%><%if(user.getDozentAccessLevel()<500) throw new Exception("{\"error\":\"Ihre Berechtigung reicht leider nicht aus, wenden Sie sich bitte an den Administrator.\",\"errorDebug\":\"Es ist eine Berechtigung der Ebene 500 oder höher erforderlich.\",\"errorcode\":5,\"severity\":80}");%>