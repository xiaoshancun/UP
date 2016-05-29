
/*
 * shj-online, Mannheim, Germany: 2009
 *
 * Copyright (c) 2009 shj-online
 * 
 * http://www.shj-online.de/
 * mailto:heiko.jakubzik@shj-online.de
 *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL H. JAKUBZIK SOFTWARE-DEVELOPEMENT
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * AUFBAU
 *
 * @version 7-00-00 (auto coded)
 * change date              change author           change description
 * ===================================================================
 * version 7-00	            
 *
 * Oct-2009		    h. jakubzik             autoclass
 * 
 */

package de.shj.UP.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import javax.naming.NamingException;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import org.w3c.dom.Node; 

/**
 *  Modul: Exam. Here, rules can be stored about the exam. More explicitly in tblSdPruefungRegelDetail. This table just stores a name and description of the rule (German Regel). The detail-table holds records of combinations of modules and credits that must not be used for the specified exam.
 *  
 *  ALLGEMEINE INFORMATION ZU DEN OBJEKTEN DIESES PACKAGES:
 *  Diese Klasse kapselt die Tabelle 'PruefungRegel' in 
 *  der Datenbankversion 7-00-00 (Oktober 2009). 
 *  Jede Tabellenspalte entspricht einem Feld dieses Objekt (mit Gettern und Settern).
 * 
 *  Es gibt Konstruktoren für 
 *  - ein leeres Objekt (Bean)
 *  - ein Objekt mit Inhalt einer Tabellenzeile aus der Datenbank
 *    -- Konstruktion über Indexwerte
 *    -- Konstruktion per Übergabe eines Recordsets (protected initByRst())
 *  - ein Objekt mit Inhalten aus einer Xml-Datei füllen.
 * 
 *  Die Hauptmethoden sind {@link {@link #add()}, {@link {@link #update()} und {@link #delete()}.
 * 
 *  Es gibt eine kanonische Methode {@link #isDirty()} nach folgenden Regeln:
 * 
 *  1) Nach Konstruktion und im Zweifel ist {@link #isDirty()} == false;
 *  2) Nachdem per Setter eine Eigentschaft verändert (!) wurde, ist das Objekt 'dirty'
 *  3) Nach einem fehlgeschlagenen Aufruf von {@link #update()} ist das Objekt 'dirty'
 *  4) Nach einem fehlgeschlagenen Aufruf von {@link #add()} ist das Objekt 'dirty'
 *  5) Nach einem Aufruf von {@link #delete()} (erfolgreich oder nicht) ist das Objekt 'dirty'
 *  6) Nach einem erfolgreichen Aufruf von {@link #update()} ist {@link #isDirty()} == false;
 *  7) Nach einem erfolgreichen Aufruf von {@link #add()} ist {@link #isDirty()} == false;
 *  8) Alle anderen Methoden wirken sich nicht auf {@link #isDirty()} aus, solange sie keine Eigenschaften des Objekts ändern
 *  Beachten Sie bitte, dass {@link #update()} und {@link #add()} nur ausgeführt werden, wenn {@link #isDirty()} == true.
 *  Beachten Sie bitte auch, dass dieses Objekt sowohl unbenutzt als auch ungetestet sein mag;  
 *  es wird automatisch aus dem Datenmodell der Anwendung erzeugt.
 * 
 **/
public class PruefungRegel extends shjCore{

	private static final long serialVersionUID = 7L;

////////////////////////////////////////////////////////////////
// 1.   P R I V A T E   D E K L A R A T I O N E N
////////////////////////////////////////////////////////////////
	
	private boolean m_bIsDirty = false;

	private long m_lSdSeminarID;
	private long m_lPruefungID;
	private long m_lRegelID;
	private String m_sPruefungRegelBezeichnung;
	private String m_sPruefungRegelBeschreibung;
	private String m_sCustom1;
	private String m_sCustom2;
	private String m_sCustom3;

////////////////////////////////////////////////////////////////
// 2.   Ö F F E N T L I C H E  E I G E N S C H A F T E N
////////////////////////////////////////////////////////////////

	/**
 	 * Wahr, wenn sich das Objekt von der Datenbanktabellenzeile unterscheidet.
	 * @return: Urteil, ob das Objekt noch mit der Datenbanktabellenzeile identisch ist.
	 **/
	public boolean isDirty(){
	  return this.m_bIsDirty;
	}


	/**
	 * 
	 * @return 
	 * Assoziiert mit der Datenbanktabellenspalte <Code>tblSdPruefungRegel.lngSdSeminarID</Code>
	 **/
	public long getSdSeminarID(){
		return this.m_lSdSeminarID;
	}

	/**
	 * 
	 * @param value: Assoziiert mit der Datenbanktabellenspalte <Code>tblSdPruefungRegel.lngSdSeminarID</Code>
	 **/	
	public void setSdSeminarID(long value){
		this.m_bIsDirty = (this.m_bIsDirty || (value != this.m_lSdSeminarID));
		this.m_lSdSeminarID=value;
	}
	

	/**
	 * 
	 * @return 
	 * Assoziiert mit der Datenbanktabellenspalte <Code>tblSdPruefungRegel.lngPruefungID</Code>
	 **/
	public long getPruefungID(){
		return this.m_lPruefungID;
	}

	/**
	 * 
	 * @param value: Assoziiert mit der Datenbanktabellenspalte <Code>tblSdPruefungRegel.lngPruefungID</Code>
	 **/	
	public void setPruefungID(long value){
		this.m_bIsDirty = (this.m_bIsDirty || (value != this.m_lPruefungID));
		this.m_lPruefungID=value;
	}
	

	/**
	 * 
	 * @return 
	 * Assoziiert mit der Datenbanktabellenspalte <Code>tblSdPruefungRegel.lngRegelID</Code>
	 **/
	public long getRegelID(){
		return this.m_lRegelID;
	}

	/**
	 * 
	 * @param value: Assoziiert mit der Datenbanktabellenspalte <Code>tblSdPruefungRegel.lngRegelID</Code>
	 **/	
	public void setRegelID(long value){
		this.m_bIsDirty = (this.m_bIsDirty || (value != this.m_lRegelID));
		this.m_lRegelID=value;
	}
	

	/**
	 * 
	 * @return 
	 * Assoziiert mit der Datenbanktabellenspalte <Code>tblSdPruefungRegel.strPruefungRegelBezeichnung</Code>
	 **/
	public String getPruefungRegelBezeichnung(){
		return this.m_sPruefungRegelBezeichnung;
	}

	/**
	 * 
	 * @param value: Assoziiert mit der Datenbanktabellenspalte <Code>tblSdPruefungRegel.strPruefungRegelBezeichnung</Code>
	 **/	
	public void setPruefungRegelBezeichnung(String value){
		this.m_bIsDirty = (this.m_bIsDirty || (!value.equals(this.m_sPruefungRegelBezeichnung)));
		this.m_sPruefungRegelBezeichnung=value;
	}
	

	/**
	 * 
	 * @return 
	 * Assoziiert mit der Datenbanktabellenspalte <Code>tblSdPruefungRegel.strPruefungRegelBeschreibung</Code>
	 **/
	public String getPruefungRegelBeschreibung(){
		return this.m_sPruefungRegelBeschreibung;
	}

	/**
	 * 
	 * @param value: Assoziiert mit der Datenbanktabellenspalte <Code>tblSdPruefungRegel.strPruefungRegelBeschreibung</Code>
	 **/	
	public void setPruefungRegelBeschreibung(String value){
		this.m_bIsDirty = (this.m_bIsDirty || (!value.equals(this.m_sPruefungRegelBeschreibung)));
		this.m_sPruefungRegelBeschreibung=value;
	}
	

	/**
	 * 
	 * @return 
	 * Assoziiert mit der Datenbanktabellenspalte <Code>tblSdPruefungRegel.strCustom1</Code>
	 **/
	public String getCustom1(){
		return this.m_sCustom1;
	}

	/**
	 * 
	 * @param value: Assoziiert mit der Datenbanktabellenspalte <Code>tblSdPruefungRegel.strCustom1</Code>
	 **/	
	public void setCustom1(String value){
		this.m_bIsDirty = (this.m_bIsDirty || (!value.equals(this.m_sCustom1)));
		this.m_sCustom1=value;
	}
	

	/**
	 * 
	 * @return 
	 * Assoziiert mit der Datenbanktabellenspalte <Code>tblSdPruefungRegel.strCustom2</Code>
	 **/
	public String getCustom2(){
		return this.m_sCustom2;
	}

	/**
	 * 
	 * @param value: Assoziiert mit der Datenbanktabellenspalte <Code>tblSdPruefungRegel.strCustom2</Code>
	 **/	
	public void setCustom2(String value){
		this.m_bIsDirty = (this.m_bIsDirty || (!value.equals(this.m_sCustom2)));
		this.m_sCustom2=value;
	}
	

	/**
	 * 
	 * @return 
	 * Assoziiert mit der Datenbanktabellenspalte <Code>tblSdPruefungRegel.strCustom3</Code>
	 **/
	public String getCustom3(){
		return this.m_sCustom3;
	}

	/**
	 * 
	 * @param value: Assoziiert mit der Datenbanktabellenspalte <Code>tblSdPruefungRegel.strCustom3</Code>
	 **/	
	public void setCustom3(String value){
		this.m_bIsDirty = (this.m_bIsDirty || (!value.equals(this.m_sCustom3)));
		this.m_sCustom3=value;
	}
	


////////////////////////////////////////////////////////////////
// 3.   X M L  U T I L I T I E S
////////////////////////////////////////////////////////////////
	
	/**
	 * @return Objekt als XML-String
	 **/
	public String toXMLString(){
		return 	

			"<SdSeminarID>" + m_lSdSeminarID + "</SdSeminarID>"  + 
			"<PruefungID>" + m_lPruefungID + "</PruefungID>"  + 
			"<RegelID>" + m_lRegelID + "</RegelID>"  + 
			"<PruefungRegelBezeichnung>" + m_sPruefungRegelBezeichnung + "</PruefungRegelBezeichnung>"  + 
			"<PruefungRegelBeschreibung>" + m_sPruefungRegelBeschreibung + "</PruefungRegelBeschreibung>"  + 
			"<Custom1>" + m_sCustom1 + "</Custom1>"  + 
			"<Custom2>" + m_sCustom2 + "</Custom2>"  + 
			"<Custom3>" + m_sCustom3 + "</Custom3>" ;
	}

////////////////////////////////////////////////////////////////
// 4.   S Q L  U T I L I T I E S
////////////////////////////////////////////////////////////////


	/**
	 * Where-SQL für eindeutigen Index dieses Objekte in der Datenbank (in Tabelle 'tblSdPruefungRegel.').
	 * @return SQL-String wie z.B. "Id1=? and Id2=?"
	 **/
	private String getSQLWhereClause(){
		return 
			"\"lngSdSeminarID\"=? AND " + 
			"\"lngPruefungID\"=? AND " + 
			"\"lngRegelID\"=?";
	}

	/**
	 * SQL-Befehl zum Löschen des Objekts in der Datenbank (aus Tabelle 'tblSdPruefungRegel.')
	 * @return SQL-String zum Löschen des Objekts, z.B. "delete from T1 where (Id1=?);"
	 **/
	private String toDBDeleteString(){
		return "delete from \"tblSdPruefungRegel\" where ( " + this.getSQLWhereClause() + ");";
	}

	/**
 	 * Setzt die Werte für alle Platzhalter in {@link #getSQLWhereClause()}.
	 * @param ii Offset für die Parameter (inklusive).
	 * @throws SQLException 
	 **/
	private void pokeWhere(int ii, PreparedStatement ps) throws SQLException{
		
		ps.setLong(ii++, m_lSdSeminarID);
		ps.setLong(ii++, m_lPruefungID);
		ps.setLong(ii++, m_lRegelID);
	}

	/**
  	 * Füllt das SQL-Statement mit den aktuellen Werten des Objekts
	 * @throws SQLException
	 **/
	private void pokeStatement(PreparedStatement ps) throws SQLException{
		
		ps.setLong(1, m_lSdSeminarID);
		ps.setLong(2, m_lPruefungID);
		ps.setLong(3, m_lRegelID);
		ps.setString(4, m_sPruefungRegelBezeichnung);
		ps.setString(5, m_sPruefungRegelBeschreibung);
		ps.setString(6, m_sCustom1);
		ps.setString(7, m_sCustom2);
		ps.setString(8, m_sCustom3);

	}

	/**
	 * SQL-Befehl zum Speichern der Objekteigenschaften in die Datenbank (Tabelle 'tblSdPruefungRegel.')
	 * @return PreparedStatemtent, das nach Aufruf von {@link #pokeStatement(PreparedStatement)} und {@link #execute()} das Update ausführt.
	 **/
	private String toDBUpdateString(){
		return "update \"tblSdPruefungRegel\" set " +
			"\"lngSdSeminarID\"=?, " +
			"\"lngPruefungID\"=?, " +
			"\"lngRegelID\"=?, " +
			"\"strPruefungRegelBezeichnung\"=?, " +
			"\"strPruefungRegelBeschreibung\"=?, " +
			"\"strCustom1\"=?, " +
			"\"strCustom2\"=?, " +
			"\"strCustom3\"=?" +
			" where (" + this.getSQLWhereClause() + ");";
	}

	/**
	 * SQL-Befehl zum Hinzufügen des Objekts in die Datenbank (Tabelle 'tblSdPruefungRegel.')
	 * @return SQL-String "insert into...".
	 **/
	private String toDBAddString(){
		return "insert into \"tblSdPruefungRegel\" ( " +
			"\"lngSdSeminarID\", \"lngPruefungID\", \"lngRegelID\", \"strPruefungRegelBezeichnung\", \"strPruefungRegelBeschreibung\", \"strCustom1\", \"strCustom2\", \"strCustom3\" ) VALUES ( ?,?,?,?,?,?,?,?);";
	}
	
////////////////////////////////////////////////////////////////
// 5.   K O N S T R U K T O R   H I L F E 
////////////////////////////////////////////////////////////////
	
	/**
	 * Lade die Objekteigenschaften aus der Datenbank
	 * @param Indexwerte für eindeutige Identifikation der Datenbankdatbellenzeile
	 * @throws NamingException 
	 * @throws SQLException 
	 **/
	private void init(long lngSdSeminarID, long lngPruefungID, long lngRegelID) throws SQLException, NamingException{

		this.m_lSdSeminarID=lngSdSeminarID;

		this.m_lPruefungID=lngPruefungID;

		this.m_lRegelID=lngRegelID;

		
		PreparedStatement pstm = prepareStatement("select * from \"tblSdPruefungRegel\" where (" + this.getSQLWhereClause() + ");");
		pokeWhere(1,pstm);

		ResultSet rst=pstm.executeQuery();
		if(rst.next()) this.initByRst(rst);
		rst.close();
		this.disconnect();
		rst=null;
	}	
	
	/**
	 * Lade die Objekteigenschaften aus einem ResultSet.
	 * param rst ResultSet mit allen Spalten der Tabelle 'tblSdPruefungRegel'
	 * @throws SQLException 
	 **/
	protected void initByRst(ResultSet rst) throws SQLException{
		this.m_lSdSeminarID=rst.getLong("lngSdSeminarID");
		this.m_lPruefungID=rst.getLong("lngPruefungID");
		this.m_lRegelID=rst.getLong("lngRegelID");
		this.m_sPruefungRegelBezeichnung=rst.getString("strPruefungRegelBezeichnung");
		this.m_sPruefungRegelBeschreibung=rst.getString("strPruefungRegelBeschreibung");
		this.m_sCustom1=rst.getString("strCustom1");
		this.m_sCustom2=rst.getString("strCustom2");
		this.m_sCustom3=rst.getString("strCustom3");	
	}	
	
	/**
	 * Lade die Objekteigenschaften aus einer XML-Node.
	 * param node XML-Node mit allen Eigenschaften als Tags.
	 * @throws ParseException (Datum muss im ISO-Format yyyy-MM-dd übergeben werden).
	 **/
	private void initByNode(Node node) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		this.m_lSdSeminarID=Long.parseLong(shjNodeValue(node, "SdSeminarID"));
		this.m_lPruefungID=Long.parseLong(shjNodeValue(node, "PruefungID"));
		this.m_lRegelID=Long.parseLong(shjNodeValue(node, "RegelID"));
		this.m_sPruefungRegelBezeichnung=(shjNodeValue(node, "PruefungRegelBezeichnung"));
		this.m_sPruefungRegelBeschreibung=(shjNodeValue(node, "PruefungRegelBeschreibung"));
		this.m_sCustom1=(shjNodeValue(node, "Custom1"));
		this.m_sCustom2=(shjNodeValue(node, "Custom2"));
		this.m_sCustom3=(shjNodeValue(node, "Custom3"));
	}		
	
////////////////////////////////////////////////////////////////
// 6.   S Q L  U T I L I T I E S
////////////////////////////////////////////////////////////////

	/**
	 * 
	 * Öffentliche Methode, um das aktuelle Objekt als 
	 * neue Zeile in die Datenbank zu schreiben (insert into...).
	 * Diese Methode enthält keinen ID-Broker, m.a.W.
	 * muss anderweitig (durch SERIAL Ids oder per Programm) 
         * Sorge dafür getragen werden, dass das Objekt keine 
	 * bestehenden Indices verletzt.
	 * 
	 * @return true für Erfolg (oder falls das Objekt nicht 'dirty' wahr, {@link #isDirty()}).
	 * @throws NamingException 
	 * @throws SQLException 
	 **/
	public boolean add() throws SQLException, NamingException{
		if( !(isDirty()) ) return true;
		boolean bReturn = false;
		
		// Lade Statement mit SQL
		PreparedStatement pstm = prepareStatement(toDBAddString());
		
		// Lade Objekteigenschaften in Statement
		// und übermittle das an die Datenbank
		pokeStatement(pstm);
		try {
			pstm.execute();
			bReturn=true;
		} catch (SQLException e) {}
		this.m_bIsDirty = !(bReturn);
		return bReturn;
	}

	/**
	 * 
	 * Schreibe die Eigenschaften dieses Objekts in die 
	 * Datenbank.
	 * 
	 * @return true für Erfolg (oder falls das Objekt nicht 'dirty' wahr, {@link #isDirty()}).
	 * @throws NamingException 
	 * @throws SQLException 
         **/
	public boolean update() throws SQLException, NamingException{
		if( !(isDirty()) ) return true;
		boolean bReturn = false;

		// Lade Statement mit SQL
		PreparedStatement pstm = prepareStatement(toDBUpdateString());

		// Lade Objekteigenschaften in Statement
		pokeStatement(pstm);

		// Identifiziere das Objekt (bzw. die Tabellenzeile) per Where-Clause
		// und übermittle die neuen Werte an die Datenbank
		pokeWhere(9,pstm);
		bReturn	= pstm.execute();
		try {
			pstm.execute();
			bReturn=true;
		} catch (SQLException e) {}
		return bReturn;
	}

	/**
	 * 
	 * Lösche dieses Objekt aus der Datenbank
	 * 
	 * @return true für Erfolg.
	 * @throws NamingException 
	 * @throws SQLException 
         **/
	public boolean delete() throws SQLException, NamingException{
		PreparedStatement pstm = prepareStatement(toDBDeleteString());
		pokeWhere(1,pstm);
		this.m_bIsDirty = true;
		try {
			pstm.execute();
			return true;
		} catch (SQLException e) {}
		return false;
	}	
	
////////////////////////////////////////////////////////////////
// 6.   K O N S T R U K T O R E N
////////////////////////////////////////////////////////////////

	/**
	 * Leerer Konstruktor (als Bean).
	 **/
	public PruefungRegel(){}	
	
	/**
	 * Konstruktor mit Indexwerten (eindeutig) der assoziierten Datenbanktabelle
	 * @throws NamingException 
	 * @throws SQLException 
	 **/
	public PruefungRegel(long lngSdSeminarID, long lngPruefungID, long lngRegelID) throws SQLException, NamingException{
		this.init(lngSdSeminarID, lngPruefungID, lngRegelID);
		this.m_bIsDirty = false;
	}	
	
	/**
	 * 
	 * Konstruktor über ResultSet: das ResultSet muss alle
	 * Spalten der Tabelle enthalten, und rst.next() muss 
	 * vor Aufruf dieser Methode aufgerufen sein.
	 * 
	 * @throws SQLException 
	 **/
	public PruefungRegel(ResultSet rst) throws SQLException{
		this.initByRst(rst);
		this.m_bIsDirty = false;
	}

	/**
	 * Konstruktor per XML-Darstellung des Objekts.
	 * @throws ParseException, if a date can't be read.
	 **/
	public PruefungRegel(Node node) throws ParseException{
		this.initByNode(node);
		this.m_bIsDirty = false;
	}

  }//Klassenende
