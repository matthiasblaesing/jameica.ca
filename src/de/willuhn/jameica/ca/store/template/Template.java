/**********************************************************************
 * $Source: /cvsroot/jameica/jameica.ca/src/de/willuhn/jameica/ca/store/template/Template.java,v $
 * $Revision: 1.4 $
 * $Date: 2009/10/14 23:58:17 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn software & services
 * All rights reserved
 *
 **********************************************************************/

package de.willuhn.jameica.ca.store.template;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.willuhn.jameica.ca.store.Entry;


/**
 * Template fuer ein zu erstellendes Zertifikat.
 */
public class Template implements Serializable
{
  /**
   * Default-Schluessellaenge.
   */
  public final static int KEYSIZE_DEFAULT = 2048;
  
  /**
   * Default-Signatur-Algorithmus.
   */
  public final static String SIGNATUREALG_DEFAULT = "SHA1WithRSAEncryption";
  
  private int keysize                = KEYSIZE_DEFAULT;
  private String signatureAlg        = SIGNATUREALG_DEFAULT;
  
  private List<Attribute> attributes = new ArrayList<Attribute>();
  private List<Extension> extensions = new ArrayList<Extension>();
  private Entry issuer               = null;
  
  private Date validFrom             = new Date();
  private Date validTo               = new Date(System.currentTimeMillis() + (1000l*60*60*24*365*2)); // per Default ~2 Jahre
  
  
  /**
   * Liefert die Schluessel-Laenge in Bytes.
   * @return die Schluessel-Laenge in Bytes.
   * Default: 2048.
   */
  public int getKeySize()
  {
    return this.keysize;
  }
  
  /**
   * Speichert die Schluessel-Laenge in Bytes.
   * @param size Schluessel-Laenge in Bytes.
   */
  public void setKeySize(int size)
  {
    this.keysize = size;
  }
  
  /**
   * Liefert die Attribute auf die das Zertifikat ausgestellt werden soll.
   * @return Attribute des Subjects.
   * Default: Keine.
   */
  public List<Attribute> getAttributes()
  {
    return this.attributes;
  }
  
  /**
   * Liefert die Extensions des Zertifikates.
   * Das sind quasi die moeglichen Verwendungszwecke wie Signierung, Verschluesselung, usw.
   * @return Extensions des Zertifikates.
   * Default: Keine.
   */
  public List<Extension> getExtensions()
  {
    return this.extensions;
  }

  /**
   * Liefert den optionalen Schluessel des Ausstellers.
   * Wenn keiner angegeben wird, wird ein selfsigned Zertifikat erzeugt.
   * @return Schluessel des Ausstellers.
   * Default: Keiner.
   */
  public Entry getIssuer()
  {
    return this.issuer;
  }
  
  /**
   * Speichert den Schluessel des Ausstellers.
   * Wenn keiner angegeben wird, wird ein selfsigned Zertifikat erzeugt.
   * @param entry der Schluessel des Ausstellers.
   */
  public void setIssuer(Entry entry)
  {
    this.issuer = entry;
  }
  
  /**
   * Liefert das Beginn-Datum der Gueligkeit.
   * @return Gueltig ab.
   * Default: Heute.
   */
  public Date getValidFrom()
  {
    return this.validFrom;
  }
  
  /**
   * Liefert das Beginn-Datum der Gueltigkeit.
   * @param from Gueltig ab.
   */
  public void setValidFrom(Date from)
  {
    this.validFrom = from;
  }
  
  /**
   * Liefert das End-Datum der Gueltigkeit.
   * @return Gueltig bis.
   * Default: ~10 Jahre (3650 Tage).
   */
  public Date getValidTo()
  {
    return this.validTo;
  }
  
  /**
   * Speichert das End-Datum der Gueltigkeit.
   * @param to Gueltig bis.
   */
  public void setValidTo(Date to)
  {
    this.validTo = to;
  }
  
  /**
   * Liefert den zu verwendenden Signatur-Algorithmus.
   * @return den zu verwendenden Signatur-Algorithmus.
   * Default: SHA1WithRSAEncryption
   */
  public String getSignatureAlgorithm()
  {
    return this.signatureAlg;
  }
  
  /**
   * Speichert den zu verwendenden Signatur-Algorithmus.
   * @param alg der zu verwendende Signatur-Algorithmus.
   */
  public void setSignatureAlgorithm(String alg)
  {
    this.signatureAlg = alg;
  }
  
  /**
   * Liefert einen sprechenden Namen fuer das Template.
   * @return sprechender Name fuer das Template.
   */
  public String getName()
  {
    return "default";
  }

}


/**********************************************************************
 * $Log: Template.java,v $
 * Revision 1.4  2009/10/14 23:58:17  willuhn
 * @N Erster Code fuer die Wizzards zum Erstellen neuer Zertifikate
 *
 * Revision 1.3  2009/10/06 16:36:00  willuhn
 * @N Extensions
 * @N PEM-Writer
 *
 * Revision 1.2  2009/10/06 00:27:37  willuhn
 * *** empty log message ***
 *
 * Revision 1.1  2009/10/05 16:02:38  willuhn
 * @N Neues Jameica-Plugin: "jameica.ca" - ein Certifcate-Authority-Tool zum Erstellen und Verwalten von SSL-Zertifikaten
 *
 **********************************************************************/
