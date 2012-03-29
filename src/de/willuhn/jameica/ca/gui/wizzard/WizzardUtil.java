/**********************************************************************
 * $Source: /cvsroot/jameica/jameica.ca/src/de/willuhn/jameica/ca/gui/wizzard/WizzardUtil.java,v $
 * $Revision: 1.6 $
 * $Date: 2012/03/29 21:14:17 $
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
  public final static List<CertificateWizzard> getWizzards()
  {
    List<CertificateWizzard> list = new ArrayList<CertificateWizzard>();
      
    try
    {
      ClassFinder finder = Application.getPluginLoader().getManifest(Plugin.class).getClassLoader().getClassFinder();
      Class<CertificateWizzard>[] classes = finder.findImplementors(CertificateWizzard.class);
      for (Class<CertificateWizzard> c:classes)
      {
        try
        {
          CertificateWizzard w = c.newInstance();
          if (w.isEnabled())
            list.add(w);
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
      Logger.error("no implementors found for interface " + CertificateWizzard.class.getName(),ce);
    }
    return list;
  }
}


/**********************************************************************
 * $Log: WizzardUtil.java,v $
 * Revision 1.6  2012/03/29 21:14:17  willuhn
 * @C Kompatibilitaet zu Jameica 2.2 leider doch nicht moeglich
 *
 * Revision 1.5  2012/03/29 20:57:15  willuhn
 * @C Kompatibilitaet zu Jameica 2.2 wieder hergestellt
 *
 * Revision 1.4  2012/03/28 22:28:11  willuhn
 * @N Einfuehrung eines neuen Interfaces "Plugin", welches von "AbstractPlugin" implementiert wird. Es dient dazu, kuenftig auch Jameica-Plugins zu unterstuetzen, die selbst gar keinen eigenen Java-Code mitbringen sondern nur ein Manifest ("plugin.xml") und z.Bsp. Jars oder JS-Dateien. Plugin-Autoren muessen lediglich darauf achten, dass die Jameica-Funktionen, die bisher ein Object vom Typ "AbstractPlugin" zuruecklieferten, jetzt eines vom Typ "Plugin" liefern.
 * @C "getClassloader()" verschoben von "plugin.getRessources().getClassloader()" zu "manifest.getClassloader()" - der Zugriffsweg ist kuerzer. Die alte Variante existiert weiterhin, ist jedoch als deprecated markiert.
 *
 * Revision 1.3  2009/10/26 23:48:49  willuhn
 * @N Payment-Server-Wizzard ausblenden, wenn CA nicht vorhanden
 *
 * Revision 1.2  2009/10/15 22:55:29  willuhn
 * @N Wizzard zum Erstellen von Hibiscus Payment-Server Lizenzen
 *
 * Revision 1.1  2009/10/15 11:50:42  willuhn
 * @N Erste Schluessel-Erstellung via GUI und Wizzard funktioniert ;)
 *
 **********************************************************************/
