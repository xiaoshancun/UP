
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
 *  Protokolltabelle für WebService-Datenaustausch mit HIS-System. Neu in Version 6-23, Dez. 2008. Die Tabelle hängt n:1 an BdStudentXLeistung, kann also zu jedem Schein n Meldungen speichern.
 *  
 *  ALLGEMEINE INFORMATION ZU DEN OBJEKTEN DIESES PACKAGES:
 *  Diese Klasse kapselt die Tabelle 'StudentXLeistungHIS' in 
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
public class StudentXLeistungHIS extends shjCore{

	private static final long serialVersionUID = 7L;

////////////////////////////////////////////////////////////////
// 1.   P R I V A T E   D E K L A R A T I O N E N
////////////////////////////////////////////////////////////////
	
	private boolean m_bIsDirty = false;

	private long m_lSdSeminarID;
	private String m_sMatrikelnummer;
	private long m_lLeistungsID;
	private long m_lStudentLeistungCount;
	private long m_lStudentLeistungHISId;
	private Date m_dStudentLeistungHISKontakt;
	private String m_sStudentLeistungHISTracker;
	private String m_sStudentLeistungHISSignUpMsg;
	private String m_srStudentLeistungHISMsg;

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
	 * Seminar
	 * @return Seminar
	 * Assoziiert mit der Datenbanktabellenspalte <Code>tblBdStudentXLeistungHIS.lngSdSeminarID</Code>
	 **/
	public long getSdSeminarID(){
		return this.m_lSdSeminarID;
	}

	/**
	 * Seminar
	 * @param value: Assoziiert mit der Datenbanktabellenspalte <Code>tblBdStudentXLeistungHIS.lngSdSeminarID</Code>
	 **/	
	public void setSdSeminarID(long value){
		this.m_bIsDirty = (this.m_bIsDirty || (value != this.m_lSdSeminarID));
		this.m_lSdSeminarID=value;
	}
	

	/**
	 * Student"s registration number.
	 * @return Student"s registration number.
	 * Assoziiert mit der Datenbanktabellenspalte <Code>tblBdStudentXLeistungHIS.strMatrikelnummer</Code>
	 **/
	public String getMatrikelnummer(){
		return this.m_sMatrikelnummer;
	}

	/**
	 * Student"s registration number.
	 * @param value: Assoziiert mit der Datenbanktabellenspalte <Code>tblBdStudentXLeistungHIS.strMatrikelnummer</Code>
	 **/	
	public void setMatrikelnummer(String value){
		this.m_bIsDirty = (this.m_bIsDirty || (value != this.m_sMatrikelnummer));
		this.m_sMatrikelnummer=value;
	}
	

	/**
	 * Id of credit.
	 * @return Id of credit.
	 * Assoziiert mit der Datenbanktabellenspalte <Code>tblBdStudentXLeistungHIS.lngLeistungsID</Code>
	 **/
	public long getLeistungsID(){
		return this.m_lLeistungsID;
	}

	/**
	 * Id of credit.
	 * @param value: Assoziiert mit der Datenbanktabellenspalte <Code>tblBdStudentXLeistungHIS.lngLeistungsID</Code>
	 **/	
	public void setLeistungsID(long value){
		this.m_bIsDirty = (this.m_bIsDirty || (value != this.m_lLeistungsID));
		this.m_lLeistungsID=value;
	}
	

	/**
	 * How many credits of this type does this student already have (how often did he fail?)
	 * @return How many credits of this type does this student already have (how often did he fail?)
	 * Assoziiert mit der Datenbanktabellenspalte <Code>tblBdStudentXLeistungHIS.lngStudentLeistungCount</Code>
	 **/
	public long getStudentLeistungCount(){
		return this.m_lStudentLeistungCount;
	}

	/**
	 * How many credits of this type does this student already have (how often did he fail?)
	 * @param value: Assoziiert mit der Datenbanktabellenspalte <Code>tblBdStudentXLeistungHIS.lngStudentLeistungCount</Code>
	 **/	
	public void setStudentLeistungCount(long value){
		this.m_bIsDirty = (this.m_bIsDirty || (value != this.m_lStudentLeistungCount));
		this.m_lStudentLeistungCount=value;
	}
	

	/**
	 * Zaehler.
	 * @return Zaehler.
	 * Assoziiert mit der Datenbanktabellenspalte <Code>tblBdStudentXLeistungHIS.lngStudentLeistungHISId</Code>
	 **/
	public long getStudentLeistungHISId(){
		return this.m_lStudentLeistungHISId;
	}

	/**
	 * Zaehler.
	 * @param value: Assoziiert mit der Datenbanktabellenspalte <Code>tblBdStudentXLeistungHIS.lngStudentLeistungHISId</Code>
	 **/	
	public void setStudentLeistungHISId(long value){
		this.m_bIsDirty = (this.m_bIsDirty || (value != this.m_lStudentLeistungHISId));
		this.m_lStudentLeistungHISId=value;
	}
	

	/**
	 * Wann gab es den letzten HIS-Kontakt zu dieser Leistung?.
	 * @return Wann gab es den letzten HIS-Kontakt zu dieser Leistung?.
	 * Assoziiert mit der Datenbanktabellenspalte <Code>tblBdStudentXLeistungHIS.dtmStudentLeistungHISKontakt</Code>
	 **/
	public Date getStudentLeistungHISKontakt(){
		return this.m_dStudentLeistungHISKontakt;
	}

	/**
	 * Wann gab es den letzten HIS-Kontakt zu dieser Leistung?.
	 * @param value: Assoziiert mit der Datenbanktabellenspalte <Code>tblBdStudentXLeistungHIS.dtmStudentLeistungHISKontakt</Code>
	 **/	
	public void setStudentLeistungHISKontakt(Date value){
		this.m_bIsDirty = (this.m_bIsDirty || (!value.equals(this.m_dStudentLeistungHISKontakt)));
		this.m_dStudentLeistungHISKontakt=value;
	}
	

	/**
	 * Eindeutiger Kennzeichner des Datensatzes in HIS
	 * @return Eindeutiger Kennzeichner des Datensatzes in HIS
	 * Assoziiert mit der Datenbanktabellenspalte <Code>tblBdStudentXLeistungHIS.strStudentLeistungHISTracker</Code>
	 **/
	public String getStudentLeistungHISTracker(){
		return this.m_sStudentLeistungHISTracker;
	}

	/**
	 * Eindeutiger Kennzeichner des Datensatzes in HIS
	 * @param value: Assoziiert mit der Datenbanktabellenspalte <Code>tblBdStudentXLeistungHIS.strStudentLeistungHISTracker</Code>
	 **/	
	public void setStudentLeistungHISTracker(String value){
		this.m_bIsDirty = (this.m_bIsDirty || (!value.equals(this.m_sStudentLeistungHISTracker)));
		this.m_sStudentLeistungHISTracker=value;
	}
	

	/**
	 * Wie beschreibt SignUp diese Transaktion?
	 * @return Wie beschreibt SignUp diese Transaktion?
	 * Assoziiert mit der Datenbanktabellenspalte <Code>tblBdStudentXLeistungHIS.strStudentLeistungHISSignUpMsg</Code>
	 **/
	public String getStudentLeistungHISSignUpMsg(){
		return this.m_sStudentLeistungHISSignUpMsg;
	}

	/**
	 * Wie beschreibt SignUp diese Transaktion?
	 * @param value: Assoziiert mit der Datenbanktabellenspalte <Code>tblBdStudentXLeistungHIS.strStudentLeistungHISSignUpMsg</Code>
	 **/	
	public void setStudentLeistungHISSignUpMsg(String value){
		this.m_bIsDirty = (this.m_bIsDirty || (!value.equals(this.m_sStudentLeistungHISSignUpMsg)));
		this.m_sStudentLeistungHISSignUpMsg=value;
	}
	

	/**
	 * Wie beschreibt HIS diese Transaktion?
	 * @return Wie beschreibt HIS diese Transaktion?
	 * Assoziiert mit der Datenbanktabellenspalte <Code>tblBdStudentXLeistungHIS.dstrStudentLeistungHISMsg</Code>
	 **/
	public String getrStudentLeistungHISMsg(){
		return this.m_srStudentLeistungHISMsg;
	}

	/**
	 * Wie beschreibt HIS diese Transaktion?
	 * @param value: Assoziiert mit der Datenbanktabellenspalte <Code>tblBdStudentXLeistungHIS.dstrStudentLeistungHISMsg</Code>
	 **/	
	public void setrStudentLeistungHISMsg(String value){
		this.m_bIsDirty = (this.m_bIsDirty || (!value.equals(this.m_srStudentLeistungHISMsg)));
		this.m_srStudentLeistungHISMsg=value;
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
			"<Matrikelnummer>" + m_sMatrikelnummer + "</Matrikelnummer>"  + 
			"<LeistungsID>" + m_lLeistungsID + "</LeistungsID>"  + 
			"<StudentLeistungCount>" + m_lStudentLeistungCount + "</StudentLeistungCount>"  + 
			"<StudentLeistungHISId>" + m_lStudentLeistungHISId + "</StudentLeistungHISId>"  + 
			"<StudentLeistungHISKontakt>" + m_dStudentLeistungHISKontakt + "</StudentLeistungHISKontakt>"  + 
			"<StudentLeistungHISTracker>" + m_sStudentLeistungHISTracker + "</StudentLeistungHISTracker>"  + 
			"<StudentLeistungHISSignUpMsg>" + m_sStudentLeistungHISSignUpMsg + "</StudentLeistungHISSignUpMsg>"  + 
			"<rStudentLeistungHISMsg>" + m_srStudentLeistungHISMsg + "</rStudentLeistungHISMsg>" ;
	}

////////////////////////////////////////////////////////////////
// 4.   S Q L  U T I L I T I E S
////////////////////////////////////////////////////////////////


	/**
	 * Where-SQL für eindeutigen Index dieses Objekte in der Datenbank (in Tabelle 'tblBdStudentXLeistungHIS.').
	 * @return SQL-String wie z.B. "Id1=? and Id2=?"
	 **/
	private String getSQLWhereClause(){
		return 
			"\"lngSdSeminarID\"=? AND " + 
			"\"strMatrikelnummer\"=? AND " + 
			"\"lngLeistungsID\"=? AND " + 
			"\"lngStudentLeistungCount\"=? AND " + 
			"\"lngStudentLeistungHISId\"=?";
	}

	/**
	 * SQL-Befehl zum Löschen des Objekts in der Datenbank (aus Tabelle 'tblBdStudentXLeistungHIS.')
	 * @return SQL-String zum Löschen des Objekts, z.B. "delete from T1 where (Id1=?);"
	 **/
	private String toDBDeleteString(){
		return "delete from \"tblBdStudentXLeistungHIS\" where ( " + this.getSQLWhereClause() + ");";
	}

	/**
 	 * Setzt die Werte für alle Platzhalter in {@link #getSQLWhereClause()}.
	 * @param ii Offset für die Parameter (inklusive).
	 * @throws SQLException 
	 **/
	private void pokeWhere(int ii, PreparedStatement ps) throws SQLException{
		
		ps.setLong(ii++, m_lSdSeminarID);
		ps.setString(ii++, m_sMatrikelnummer);
		ps.setLong(ii++, m_lLeistungsID);
		ps.setLong(ii++, m_lStudentLeistungCount);
		ps.setLong(ii++, m_lStudentLeistungHISId);
	}

	/**
  	 * Füllt das SQL-Statement mit den aktuellen Werten des Objekts
	 * @throws SQLException
	 **/
	private void pokeStatement(PreparedStatement ps) throws SQLException{
		
		ps.setLong(1, m_lSdSeminarID);
		ps.setString(2, m_sMatrikelnummer);
		ps.setLong(3, m_lLeistungsID);
		ps.setLong(4, m_lStudentLeistungCount);
		ps.setLong(5, m_lStudentLeistungHISId);
		ps.setDate(6, m_dStudentLeistungHISKontakt);
		ps.setString(7, m_sStudentLeistungHISTracker);
		ps.setString(8, m_sStudentLeistungHISSignUpMsg);
		ps.setString(9, m_srStudentLeistungHISMsg);

	}

	/**
	 * SQL-Befehl zum Speichern der Objekteigenschaften in die Datenbank (Tabelle 'tblBdStudentXLeistungHIS.')
	 * @return PreparedStatemtent, das nach Aufruf von {@link #pokeStatement(PreparedStatement)} und {@link #execute()} das Update ausführt.
	 **/
	private String toDBUpdateString(){
		return "update \"tblBdStudentXLeistungHIS\" set " +
			"\"lngSdSeminarID\"=?, " +
			"\"strMatrikelnummer\"=?, " +
			"\"lngLeistungsID\"=?, " +
			"\"lngStudentLeistungCount\"=?, " +
			"\"lngStudentLeistungHISId\"=?, " +
			"\"dtmStudentLeistungHISKontakt\"=?, " +
			"\"strStudentLeistungHISTracker\"=?, " +
			"\"strStudentLeistungHISSignUpMsg\"=?, " +
			"\"dstrStudentLeistungHISMsg\"=?" +
			" where (" + this.getSQLWhereClause() + ");";
	}

	/**
	 * SQL-Befehl zum Hinzufügen des Objekts in die Datenbank (Tabelle 'tblBdStudentXLeistungHIS.')
	 * @return SQL-String "insert into...".
	 **/
	private String toDBAddString(){
		return "insert into \"tblBdStudentXLeistungHIS\" ( " +
			"\"lngSdSeminarID\", \"strMatrikelnummer\", \"lngLeistungsID\", \"lngStudentLeistungCount\", \"lngStudentLeistungHISId\", \"dtmStudentLeistungHISKontakt\", \"strStudentLeistungHISTracker\", \"strStudentLeistungHISSignUpMsg\", \"dstrStudentLeistungHISMsg\" ) VALUES ( ?,?,?,?,?,?,?,?,?);";
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
	private void init(long lngSdSeminarID, String strMatrikelnummer, long lngLeistungsID, long lngStudentLeistungCount, long lngStudentLeistungHISId) throws SQLException, NamingException{

		this.m_lSdSeminarID=lngSdSeminarID;

		this.m_sMatrikelnummer=strMatrikelnummer;

		this.m_lLeistungsID=lngLeistungsID;

		this.m_lStudentLeistungCount=lngStudentLeistungCount;

		this.m_lStudentLeistungHISId=lngStudentLeistungHISId;

		
		PreparedStatement pstm = prepareStatement("select * from \"tblBdStudentXLeistungHIS\" where (" + this.getSQLWhereClause() + ");");
		pokeWhere(1,pstm);

		ResultSet rst=pstm.executeQuery();
		if(rst.next()) this.initByRst(rst);
		rst.close();
		this.disconnect();
		rst=null;
	}	
	
	/**
	 * Lade die Objekteigenschaften aus einem ResultSet.
	 * param rst ResultSet mit allen Spalten der Tabelle 'tblBdStudentXLeistungHIS'
	 * @throws SQLException 
	 **/
	protected void initByRst(ResultSet rst) throws SQLException{
		this.m_lSdSeminarID=rst.getLong("lngSdSeminarID");
		this.m_sMatrikelnummer=rst.getString("strMatrikelnummer");
		this.m_lLeistungsID=rst.getLong("lngLeistungsID");
		this.m_lStudentLeistungCount=rst.getLong("lngStudentLeistungCount");
		this.m_lStudentLeistungHISId=rst.getLong("lngStudentLeistungHISId");
		this.m_dStudentLeistungHISKontakt=rst.getDate("dtmStudentLeistungHISKontakt");
		this.m_sStudentLeistungHISTracker=rst.getString("strStudentLeistungHISTracker");
		this.m_sStudentLeistungHISSignUpMsg=rst.getString("strStudentLeistungHISSignUpMsg");
		this.m_srStudentLeistungHISMsg=rst.getString("dstrStudentLeistungHISMsg");	
	}	
	
	/**
	 * Lade die Objekteigenschaften aus einer XML-Node.
	 * param node XML-Node mit allen Eigenschaften als Tags.
	 * @throws ParseException (Datum muss im ISO-Format yyyy-MM-dd übergeben werden).
	 **/
	private void initByNode(Node node) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		this.m_lSdSeminarID=Long.parseLong(shjNodeValue(node, "SdSeminarID"));
		this.m_sMatrikelnummer=(shjNodeValue(node, "Matrikelnummer"));
		this.m_lLeistungsID=Long.parseLong(shjNodeValue(node, "LeistungsID"));
		this.m_lStudentLeistungCount=Long.parseLong(shjNodeValue(node, "StudentLeistungCount"));
		this.m_lStudentLeistungHISId=Long.parseLong(shjNodeValue(node, "StudentLeistungHISId"));
		this.m_dStudentLeistungHISKontakt=(Date) (sdf.parse(shjNodeValue(node, "StudentLeistungHISKontakt")));
		this.m_sStudentLeistungHISTracker=(shjNodeValue(node, "StudentLeistungHISTracker"));
		this.m_sStudentLeistungHISSignUpMsg=(shjNodeValue(node, "StudentLeistungHISSignUpMsg"));
		this.m_srStudentLeistungHISMsg=(shjNodeValue(node, "rStudentLeistungHISMsg"));
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
		pokeWhere(10,pstm);
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
	public StudentXLeistungHIS(){}	
	
	/**
	 * Konstruktor mit Indexwerten (eindeutig) der assoziierten Datenbanktabelle
	 * @throws NamingException 
	 * @throws SQLException 
	 **/
	public StudentXLeistungHIS(long lngSdSeminarID, String strMatrikelnummer, long lngLeistungsID, long lngStudentLeistungCount, long lngStudentLeistungHISId) throws SQLException, NamingException{
		this.init(lngSdSeminarID, strMatrikelnummer, lngLeistungsID, lngStudentLeistungCount, lngStudentLeistungHISId);
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
	public StudentXLeistungHIS(ResultSet rst) throws SQLException{
		this.initByRst(rst);
		this.m_bIsDirty = false;
	}

	/**
	 * Konstruktor per XML-Darstellung des Objekts.
	 * @throws ParseException, if a date can't be read.
	 **/
	public StudentXLeistungHIS(Node node) throws ParseException{
		this.initByNode(node);
		this.m_bIsDirty = false;
	}

  }//Klassenende
