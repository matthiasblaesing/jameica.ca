/**********************************************************************
 * $Source: /cvsroot/jameica/jameica.ca/src/de/willuhn/jameica/ca/gui/controller/EntryListControl.java,v $
 * $Revision: 1.2 $
 * $Date: 2009/10/13 00:26:32 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn software & services
 * All rights reserved
 *
 **********************************************************************/

package de.willuhn.jameica.ca.gui.controller;

import de.willuhn.jameica.ca.gui.part.EntryListTable;
import de.willuhn.jameica.gui.AbstractControl;
import de.willuhn.jameica.gui.AbstractView;

/**
 * Controller fuer die Liste der Schluessel.
 */
public class EntryListControl extends AbstractControl
{
  private EntryListTable table = null;

  /**
   * ct.
   * @param view
   */
  public EntryListControl(AbstractView view)
  {
    super(view);
  }
  
  /**
   * Liefert die Tabelle mit den Schluesseln.
   * @return Tabelle mit den Schluesseln.
   * @throws Exception
   */
  public EntryListTable getTable() throws Exception
  {
    if (this.table == null)
      this.table = new EntryListTable(null); // TODO Keine Action angegeben
    return this.table;
  }
}


/**********************************************************************
 * $Log: EntryListControl.java,v $
 * Revision 1.2  2009/10/13 00:26:32  willuhn
 * @N Tree-View fuer Zertifikate
 *
 * Revision 1.1  2009/10/07 12:24:04  willuhn
 * @N Erster GUI-Code
 *
 **********************************************************************/
