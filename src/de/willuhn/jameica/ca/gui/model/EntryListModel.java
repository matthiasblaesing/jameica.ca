/**********************************************************************
 * $Source: /cvsroot/jameica/jameica.ca/src/de/willuhn/jameica/ca/gui/model/EntryListModel.java,v $
 * $Revision: 1.4 $
 * $Date: 2009/10/13 00:26:32 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn software & services
 * All rights reserved
 *
 **********************************************************************/

package de.willuhn.jameica.ca.gui.model;

import java.util.ArrayList;
import java.util.List;

import de.willuhn.jameica.ca.Plugin;
import de.willuhn.jameica.ca.service.StoreService;
import de.willuhn.jameica.ca.store.Entry;
import de.willuhn.jameica.ca.store.Store;
import de.willuhn.jameica.system.Application;

/**
 * Implementiert ein Model, welches die Schluessel des Keystore als Liste (Tabelle) liefert.
 */
public class EntryListModel
{
  private List<ListItem> list = null;
  
  /**
   * Liefert eine Liste der Schluessel.
   * @return Liste der Schluessel.
   * @throws Exception
   */
  public synchronized List<ListItem> getItems() throws Exception
  {
    if (this.list != null)
      return this.list;
    
    this.list = new ArrayList<ListItem>();
    StoreService service = (StoreService) Application.getServiceFactory().lookup(Plugin.class,"store");
    Store store = service.getStore();
    List<Entry> entries = store.getEntries();
    for (Entry e:entries)
    {
      this.list.add(new ListItem(e));
    }
    return this.list;
  }
}


/**********************************************************************
 * $Log: EntryListModel.java,v $
 * Revision 1.4  2009/10/13 00:26:32  willuhn
 * @N Tree-View fuer Zertifikate
 *
 * Revision 1.3  2009/10/07 17:09:11  willuhn
 * @N Schluessel loeschen
 *
 * Revision 1.2  2009/10/07 16:38:59  willuhn
 * @N GUI-Code zum Anzeigen und Importieren von Schluesseln
 *
 * Revision 1.1  2009/10/07 12:24:04  willuhn
 * @N Erster GUI-Code
 *
 **********************************************************************/
