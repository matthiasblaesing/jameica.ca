/**********************************************************************
 * $Source: /cvsroot/jameica/jameica.ca/src/de/willuhn/jameica/ca/gui/wizzard/WizzardUtil.java,v $
 * $Revision: 1.1 $
 * $Date: 2009/10/15 11:50:42 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn software & services
 * All rights reserved
 *
 **********************************************************************/

package de.willuhn.jameica.ca.gui.wizzard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.willuhn.jameica.ca.Plugin;
import de.willuhn.jameica.system.Application;
import de.willuhn.logging.Logger;
import de.willuhn.util.ClassFinder;

/**
 * Hilfsklasse mit statischen Methoden fuer die Wizzards.
 */
public class WizzardUtil
{
  /**
   * Liefert eine Liste der moeglichen Assistenten zur Erstellung neuer Zertifikate.
   * @return Liste der moeglichen Asstistenten.
   */
  public final static List<CreateCertificateWizzard> getWizzards()
  {
    List<CreateCertificateWizzard> list = new ArrayList<CreateCertificateWizzard>();
      
    try
    {
      ClassFinder finder = Application.getPluginLoader().getPlugin(Plugin.class).getResources().getClassLoader().getClassFinder();
      Class<CreateCertificateWizzard>[] classes = finder.findImplementors(CreateCertificateWizzard.class);
      for (Class<CreateCertificateWizzard> c:classes)
      {
        try
        {
          list.add(c.newInstance());
        }
        catch (Exception e)
        {
          Logger.error("error while loading wizzard " + c.getName() + ", skipping",e);
        }
      }
      
      // Jetzt sortieren wir die Liste noch
      Collections.sort(list);
    }
    catch (ClassNotFoundException ce)
    {
      Logger.error("no implementors found for interface " + CreateCertificateWizzard.class.getName(),ce);
    }
    return list;
  }
}


/**********************************************************************
 * $Log: WizzardUtil.java,v $
 * Revision 1.1  2009/10/15 11:50:42  willuhn
 * @N Erste Schluessel-Erstellung via GUI und Wizzard funktioniert ;)
 *
 **********************************************************************/
