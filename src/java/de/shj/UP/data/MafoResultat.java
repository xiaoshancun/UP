
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
 *  Todo.
 *  
 *  ALLGEMEINE INFORMATION ZU DEN OBJEKTEN DIESES PACKAGES:
 *  Diese Klasse kapselt die Tabelle 'foResultat' in 
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
public class MafoResultat extends shjCore{

	private static final long serialVersionUID = 7L;

////////////////////////////////////////////////////////////////
// 1.   P R I V A T E   D E K L A R A T I O N E N
////////////////////////////////////////////////////////////////
	
	private boolean m_bIsDirty = false;

	private long m_lMafoID;
	private long m_lMafoResultatID;
	private long m_lMafoResultatFrageID;
	private long m_lMafoResultatFrageOptionID;
	private String m_sMafoResultatFrageAntwort;
	private Date m_dMafoResultatDatum;

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
	 * Assoziiert mit der Datenbanktabellenspalte <Code>tblMafoResultat.lngMafoID</Code>
	 **/
	public long getMafoID(){
		return this.m_lMafoID;
	}

	/**
	 * 
	 * @param value: Assoziiert mit der Datenbanktabellenspalte <Code>tblMafoResultat.lngMafoID</Code>
	 **/	
	public void setMafoID(long value){
		this.m_bIsDirty = (this.m_bIsDirty || (value != this.m_lMafoID));
		this.m_lMafoID=value;
	}
	

	/**
	 * 
	 * @return 
	 * Assoziiert mit der Datenbanktabellenspalte <Code>tblMafoResultat.lngMafoResultatID</Code>
	 **/
	public long getMafoResultatID(){
		return this.m_lMafoResultatID;
	}

	/**
	 * 
	 * @param value: Assoziiert mit der Datenbanktabellenspalte <Code>tblMafoResultat.lngMafoResultatID</Code>
	 **/	
	public void setMafoResultatID(long value){
		this.m_bIsDirty = (this.m_bIsDirty || (value != this.m_lMafoResultatID));
		this.m_lMafoResultatID=value;
	}
	

	/**
	 * 
	 * @return 
	 * Assoziiert mit der Datenbanktabellenspalte <Code>tblMafoResultat.lngMafoResultatFrageID</Code>
	 **/
	public long getMafoResultatFrageID(){
		return this.m_lMafoResultatFrageID;
	}

	/**
	 * 
	 * @param value: Assoziiert mit der Datenbanktabellenspalte <Code>tblMafoResultat.lngMafoResultatFrageID</Code>
	 **/	
	public void setMafoResultatFrageID(long value){
		this.m_bIsDirty = (this.m_bIsDirty || (value != this.m_lMafoResultatFrageID));
		this.m_lMafoResultatFrageID=value;
	}
	

	/**
	 * 
	 * @return 
	 * Assoziiert mit der Datenbanktabellenspalte <Code>tblMafoResultat.lngMafoResultatFrageOptionID</Code>
	 **/
	public long getMafoResultatFrageOptionID(){
		return this.m_lMafoResultatFrageOptionID;
	}

	/**
	 * 
	 * @param value: Assoziiert mit der Datenbanktabellenspalte <Code>tblMafoResultat.lngMafoResultatFrageOptionID</Code>
	 **/	
	public void setMafoResultatFrageOptionID(long value){
		this.m_bIsDirty = (this.m_bIsDirty || (value != this.m_lMafoResultatFrageOptionID));
		this.m_lMafoResultatFrageOptionID=value;
	}
	

	/**
	 * 
	 * @return 
	 * Assoziiert mit der Datenbanktabellenspalte <Code>tblMafoResultat.strMafoResultatFrageAntwort</Code>
	 **/
	public String getMafoResultatFrageAntwort(){
		return this.m_sMafoResultatFrageAntwort;
	}

	/**
	 * 
	 * @param value: Assoziiert mit der Datenbanktabellenspalte <Code>tblMafoResultat.strMafoResultatFrageAntwort</Code>
	 **/	
	public void setMafoResultatFrageAntwort(String value){
		this.m_bIsDirty = (this.m_bIsDirty || (!value.equals(this.m_sMafoResultatFrageAntwort)));
		this.m_sMafoResultatFrageAntwort=value;
	}
	

	/**
	 * 
	 * @return 
	 * Assoziiert mit der Datenbanktabellenspalte <Code>tblMafoResultat.dtmMafoResultatDatum</Code>
	 **/
	public Date getMafoResultatDatum(){
		return this.m_dMafoResultatDatum;
	}

	/**
	 * 
	 * @param value: Assoziiert mit der Datenbanktabellenspalte <Code>tblMafoResultat.dtmMafoResultatDatum</Code>
	 **/	
	public void setMafoResultatDatum(Date value){
		this.m_bIsDirty = (this.m_bIsDirty || (value != this.m_dMafoResultatDatum));
		this.m_dMafoResultatDatum=value;
	}
	


////////////////////////////////////////////////////////////////
// 3.   X M L  U T I L I T I E S
////////////////////////////////////////////////////////////////
	
	/**
	 * @return Objekt als XML-String
	 **/
	public String toXMLString(){
		return 	

			"<MafoID>" + m_lMafoID + "</MafoID>"  + 
			"<MafoResultatID>" + m_lMafoResultatID + "</MafoResultatID>"  + 
			"<MafoResultatFrageID>" + m_lMafoResultatFrageID + "</MafoResultatFrageID>"  + 
			"<MafoResultatFrageOptionID>" + m_lMafoResultatFrageOptionID + "</MafoResultatFrageOptionID>"  + 
			"<MafoResultatFrageAntwort>" + m_sMafoResultatFrageAntwort + "</MafoResultatFrageAntwort>"  + 
			"<MafoResultatDatum>" + m_dMafoResultatDatum + "</MafoResultatDatum>" ;
	}

////////////////////////////////////////////////////////////////
// 4.   S Q L  U T I L I T I E S
////////////////////////////////////////////////////////////////


	/**
	 * Where-SQL für eindeutigen Index dieses Objekte in der Datenbank (in Tabelle 'tblMafoResultat.').
	 * @return SQL-String wie z.B. "Id1=? and Id2=?"
	 **/
	private String getSQLWhereClause(){
		return 
			"\"lngMafoID\"=? AND " + 
			"\"lngMafoResultatID\"=? AND " + 
			"\"lngMafoResultatFrageID\"=?";
	}

	/**
	 * SQL-Befehl zum Löschen des Objekts in der Datenbank (aus Tabelle 'tblMafoResultat.')
	 * @return SQL-String zum Löschen des Objekts, z.B. "delete from T1 where (Id1=?);"
	 **/
	private String toDBDeleteString(){
		return "delete from \"tblMafoResultat\" where ( " + this.getSQLWhereClause() + ");";
	}

	/**
 	 * Setzt die Werte für alle Platzhalter in {@link #getSQLWhereClause()}.
	 * @param ii Offset für die Parameter (inklusive).
	 * @throws SQLException 
	 **/
	private void pokeWhere(int ii, PreparedStatement ps) throws SQLException{
		
		ps.setLong(ii++, m_lMafoID);
		ps.setLong(ii++, m_lMafoResultatID);
		ps.setLong(ii++, m_lMafoResultatFrageID);
	}

	/**
  	 * Füllt das SQL-Statement mit den aktuellen Werten des Objekts
	 * @throws SQLException
	 **/
	private void pokeStatement(PreparedStatement ps) throws SQLException{
		
		ps.setLong(1, m_lMafoID);
		ps.setLong(2, m_lMafoResultatID);
		ps.setLong(3, m_lMafoResultatFrageID);
		ps.setLong(4, m_lMafoResultatFrageOptionID);
		ps.setString(5, m_sMafoResultatFrageAntwort);
		ps.setDate(6, m_dMafoResultatDatum);

	}

	/**
	 * SQL-Befehl zum Speichern der Objekteigenschaften in die Datenbank (Tabelle 'tblMafoResultat.')
	 * @return PreparedStatemtent, das nach Aufruf von {@link #pokeStatement(PreparedStatement)} und {@link #execute()} das Update ausführt.
	 **/
	private String toDBUpdateString(){
		return "update \"tblMafoResultat\" set " +
			"\"lngMafoID\"=?, " +
			"\"lngMafoResultatID\"=?, " +
			"\"lngMafoResultatFrageID\"=?, " +
			"\"lngMafoResultatFrageOptionID\"=?, " +
			"\"strMafoResultatFrageAntwort\"=?, " +
			"\"dtmMafoResultatDatum\"=?" +
			" where (" + this.getSQLWhereClause() + ");";
	}

	/**
	 * SQL-Befehl zum Hinzufügen des Objekts in die Datenbank (Tabelle 'tblMafoResultat.')
	 * @return SQL-String "insert into...".
	 **/
	private String toDBAddString(){
		return "insert into \"tblMafoResultat\" ( " +
			"\"lngMafoID\", \"lngMafoResultatID\", \"lngMafoResultatFrageID\", \"lngMafoResultatFrageOptionID\", \"strMafoResultatFrageAntwort\", \"dtmMafoResultatDatum\" ) VALUES ( ?,?,?,?,?,?);";
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
	private void init(long lngMafoID, long lngMafoResultatID, long lngMafoResultatFrageID) throws SQLException, NamingException{

		this.m_lMafoID=lngMafoID;

		this.m_lMafoResultatID=lngMafoResultatID;

		this.m_lMafoResultatFrageID=lngMafoResultatFrageID;

		
		PreparedStatement pstm = prepareStatement("select * from \"tblMafoResultat\" where (" + this.getSQLWhereClause() + ");");
		pokeWhere(1,pstm);

		ResultSet rst=pstm.executeQuery();
		if(rst.next()) this.initByRst(rst);
		rst.close();
		this.disconnect();
		rst=null;
	}	
	
	/**
	 * Lade die Objekteigenschaften aus einem ResultSet.
	 * param rst ResultSet mit allen Spalten der Tabelle 'tblMafoResultat'
	 * @throws SQLException 
	 **/
	protected void initByRst(ResultSet rst) throws SQLException{
		this.m_lMafoID=rst.getLong("lngMafoID");
		this.m_lMafoResultatID=rst.getLong("lngMafoResultatID");
		this.m_lMafoResultatFrageID=rst.getLong("lngMafoResultatFrageID");
		this.m_lMafoResultatFrageOptionID=rst.getLong("lngMafoResultatFrageOptionID");
		this.m_sMafoResultatFrageAntwort=rst.getString("strMafoResultatFrageAntwort");
		this.m_dMafoResultatDatum=rst.getDate("dtmMafoResultatDatum");	
	}	
	
	/**
	 * Lade die Objekteigenschaften aus einer XML-Node.
	 * param node XML-Node mit allen Eigenschaften als Tags.
	 * @throws ParseException (Datum muss im ISO-Format yyyy-MM-dd übergeben werden).
	 **/
	private void initByNode(Node node) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		this.m_lMafoID=Long.parseLong(shjNodeValue(node, "MafoID"));
		this.m_lMafoResultatID=Long.parseLong(shjNodeValue(node, "MafoResultatID"));
		this.m_lMafoResultatFrageID=Long.parseLong(shjNodeValue(node, "MafoResultatFrageID"));
		this.m_lMafoResultatFrageOptionID=Long.parseLong(shjNodeValue(node, "MafoResultatFrageOptionID"));
		this.m_sMafoResultatFrageAntwort=(shjNodeValue(node, "MafoResultatFrageAntwort"));
		this.m_dMafoResultatDatum=(Date) (sdf.parse(shjNodeValue(node, "MafoResultatDatum")));
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
		pokeWhere(7,pstm);
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
	public MafoResultat(){}	
	
	/**
	 * Konstruktor mit Indexwerten (eindeutig) der assoziierten Datenbanktabelle
	 * @throws NamingException 
	 * @throws SQLException 
	 **/
	public MafoResultat(long lngMafoID, long lngMafoResultatID, long lngMafoResultatFrageID) throws SQLException, NamingException{
		this.init(lngMafoID, lngMafoResultatID, lngMafoResultatFrageID);
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
	public MafoResultat(ResultSet rst) throws SQLException{
		this.initByRst(rst);
		this.m_bIsDirty = false;
	}

	/**
	 * Konstruktor per XML-Darstellung des Objekts.
	 * @throws ParseException, if a date can't be read.
	 **/
	public MafoResultat(Node node) throws ParseException{
		this.initByNode(node);
		this.m_bIsDirty = false;
	}

  }//Klassenende
