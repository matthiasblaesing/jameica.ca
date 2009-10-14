/**********************************************************************
 * $Source: /cvsroot/jameica/jameica.ca/src/de/willuhn/jameica/ca/gui/wizzard/Attic/CreateCertificateWizzard.java,v $
 * $Revision: 1.2 $
 * $Date: 2009/10/14 23:58:17 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn software & services
 * All rights reserved
 *
 **********************************************************************/

package de.willuhn.jameica.ca.gui.wizzard;

import de.willuhn.jameica.ca.store.template.Template;
import de.willuhn.jameica.gui.Part;
import de.willuhn.util.ApplicationException;

/**
 * Interface, welches von allen Wizzards zur Erzeugung neuer Zertifikate
 * implementiert werden muss.
 * 
 * Die Methoden der Wizzard-Implementierungen werden wie folgt aufgerufen:
 * 
 * <ol>
 *   <li>
 *     Beim Klick auf "Zertifikat erstellen..." wird dem User eine Auswahlliste
 *     mit den Namen der moeglichen Assistenten angezeigt. Hierfuer wird der
 *     Rueckgabewert der Funktion "getName()" verwendet.
 *   </li>
 *    
 *   <li>
 *     Der ausgewaehlte Wizzard wird gestartet, indem die Methode
 *     paint(Composite) aufgerufen wird. Die Implementierung kann hier
 *     Controls anzeigen, in denen der User die noetigen Benutzereingaben
 *     vornehmen kann. Die Implementierung sollte keine eigenen Buttons zeichnen,
 *     da das Composite bereits in einem modalen Dialog angezeigt wird, der
 *     mit einem "Abbrechen"- und "Uebernehmen"-Button versehen ist.
 *   </li>
 *    
 *   <li>
 *     Klickt der User aus "Uebernehmen", wird die Funktion "create()"
 *     aufgeruden. Die Implementierung soll hier die Benutzereingaben aus den
 *     Controls auswerten, daraus ein Template-Objekt erstellen und dieses
 *     zurueckliefern. Die Implementierung muss keine Widgets oder Controls
 *     disposen, das macht jameica.ca selbst.
 *   </li>
 * </ol>
 */
public interface CreateCertificateWizzard extends Part
{
  /**
   * Liefert einen sprechenden Namen fuer den Wizzard bzw. das Zertifikat-Format.
   * @return sprechender Name fuer den Wizzard.
   */
  public String getName();
  
  /**
   * Wird aufgerufen, wenn der User den "Uebernehmen"-Button geklickt hat.
   * Die Implementierung muss dann das passende Zertifikatstemplate erzeugen
   * und zurueckliefern.
   * @return Das Template, aus dem das Zertifikat erstellt werden soll.
   * @throws ApplicationException
   */
  public Template create() throws ApplicationException;
  
}


/**********************************************************************
 * $Log: CreateCertificateWizzard.java,v $
 * Revision 1.2  2009/10/14 23:58:17  willuhn
 * @N Erster Code fuer die Wizzards zum Erstellen neuer Zertifikate
 *
 * Revision 1.1  2009/10/14 17:20:50  willuhn
 * *** empty log message ***
 *
 **********************************************************************/
