/**********************************************************************
 * $Source: /cvsroot/jameica/jameica.ca/src/de/willuhn/jameica/ca/gui/part/EntryListTable.java,v $
 * $Revision: 1.2 $
 * $Date: 2009/10/07 16:38:59 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn software & services
 * All rights reserved
 *
 **********************************************************************/

package de.willuhn.jameica.ca.gui.part;

import java.rmi.RemoteException;
import java.util.Date;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;

import de.willuhn.jameica.ca.Plugin;
import de.willuhn.jameica.ca.gui.menus.EntryListMenu;
import de.willuhn.jameica.ca.gui.model.EntryListModel;
import de.willuhn.jameica.ca.gui.model.EntryListModel.Line;
import de.willuhn.jameica.ca.store.Entry;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.formatter.DateFormatter;
import de.willuhn.jameica.gui.formatter.TableFormatter;
import de.willuhn.jameica.gui.parts.TablePart;
import de.willuhn.jameica.gui.util.Color;
import de.willuhn.jameica.gui.util.SWTUtil;
import de.willuhn.jameica.messaging.Message;
import de.willuhn.jameica.messaging.MessageConsumer;
import de.willuhn.jameica.messaging.QueryMessage;
import de.willuhn.jameica.system.Application;
import de.willuhn.logging.Logger;
import de.willuhn.util.I18N;

/**
 * Implementiert eine vorkonfigurierte Liste mit den Schluesseln.
 */
public class EntryListTable extends TablePart
{
  private final static I18N i18n = Application.getPluginLoader().getPlugin(Plugin.class).getResources().getI18N();

  /**
   * ct.
   * @param action
   * @throws Exception
   */
  public EntryListTable(Action action) throws Exception
  {
    super(new EntryListModel().getItems(),action);

    this.setContextMenu(new EntryListMenu());
    this.addColumn(i18n.tr("Ausgestellt f�r"),"subject");
    this.addColumn(i18n.tr("Organisation"),"organization");
    this.addColumn(i18n.tr("Aussteller"),"issuer");
    this.addColumn(i18n.tr("G�ltig von"),"validFrom",new DateFormatter());
    this.addColumn(i18n.tr("G�ltig bis"),"validTo",new DateFormatter());
    
    this.setRememberColWidths(true);
    this.setRememberOrder(true);
    this.setRememberState(true);
    this.setSummary(true);
    
    this.setFormatter(new TableFormatter()
    {
      /**
       * @see de.willuhn.jameica.gui.formatter.TableFormatter#format(org.eclipse.swt.widgets.TableItem)
       */
      public void format(TableItem item)
      {
        try
        {
          // Abgelaufene Schluessel zeigen wir rot an.
          Line line = (Line) item.getData();
          Date validTo = line.getValidTo();
          if (validTo != null && validTo.before(new Date()))
            item.setForeground(Color.ERROR.getSWTColor());
          else
            item.setForeground(Color.WIDGET_FG.getSWTColor());
          
          // TODO: CA-Zertifikate zeigen wir fett gedruckt an.

          // Wir zeigen unterschiedliche Icons an, wenn nur Public-Key vorhanden ist oder beides
          if (line.havePrivateKey())
            item.setImage(SWTUtil.getImage("key-pub+priv.png"));
          else
            item.setImage(SWTUtil.getImage("key-pub.png"));
        }
        catch (Exception e)
        {
          Logger.error("unable to format line",e);
        }
      }
    });
  }

  /**
   * @see de.willuhn.jameica.gui.parts.TablePart#paint(org.eclipse.swt.widgets.Composite)
   */
  public synchronized void paint(Composite parent) throws RemoteException
  {
    final MessageConsumer mc = new MyMessageConsumer();
    Application.getMessagingFactory().getMessagingQueue("jameica.ca.entry.import").registerMessageConsumer(mc);
    parent.addDisposeListener(new DisposeListener() {
      public void widgetDisposed(DisposeEvent e)
      {
        Application.getMessagingFactory().unRegisterMessageConsumer(mc);
      }
    });

    super.paint(parent);
    this.sort();
  }

  /**
   * Damit werden wir benachrichtigt, wenn neue Schluessel importiert wurden.
   * Wir koennen sie dann live zur Tabelle hinzufuegen.
   */
  private class MyMessageConsumer implements MessageConsumer
  {
    /**
     * @see de.willuhn.jameica.messaging.MessageConsumer#autoRegister()
     */
    public boolean autoRegister()
    {
      return false;
    }

    /**
     * @see de.willuhn.jameica.messaging.MessageConsumer#getExpectedMessageTypes()
     */
    public Class[] getExpectedMessageTypes()
    {
      return new Class[]{QueryMessage.class};
    }

    /**
     * @see de.willuhn.jameica.messaging.MessageConsumer#handleMessage(de.willuhn.jameica.messaging.Message)
     */
    public void handleMessage(Message message) throws Exception
    {
      QueryMessage msg = (QueryMessage) message;
      final Object data = msg.getData();
      if (data == null || !(data instanceof Entry))
        return;
      
      GUI.getDisplay().asyncExec(new Runnable()
      {
        public void run()
        {
          try
          {
            addItem(new EntryListModel.Line((Entry)data));
            sort();
          }
          catch (Exception e)
          {
            Logger.error("error while adding item",e);
          }
        }
      });
    }
    
  }
  
}


/**********************************************************************
 * $Log: EntryListTable.java,v $
 * Revision 1.2  2009/10/07 16:38:59  willuhn
 * @N GUI-Code zum Anzeigen und Importieren von Schluesseln
 *
 * Revision 1.1  2009/10/07 12:24:04  willuhn
 * @N Erster GUI-Code
 *
 **********************************************************************/
