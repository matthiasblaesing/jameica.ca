/**********************************************************************
 * $Source: /cvsroot/jameica/jameica.ca/src/de/willuhn/jameica/ca/gui/dialogs/SelectCreateWizzardDialog.java,v $
 * $Revision: 1.3 $
 * $Date: 2010/08/10 12:14:24 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn software & services
 * All rights reserved
 *
 **********************************************************************/

package de.willuhn.jameica.ca.gui.dialogs;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Composite;

import de.willuhn.jameica.ca.Plugin;
import de.willuhn.jameica.ca.gui.wizzard.CertificateWizzard;
import de.willuhn.jameica.ca.gui.wizzard.WizzardUtil;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.dialogs.AbstractDialog;
import de.willuhn.jameica.gui.input.SelectInput;
import de.willuhn.jameica.gui.internal.buttons.Cancel;
import de.willuhn.jameica.gui.util.ButtonArea;
import de.willuhn.jameica.gui.util.SimpleContainer;
import de.willuhn.jameica.system.Application;
import de.willuhn.jameica.system.OperationCanceledException;
import de.willuhn.jameica.system.Settings;
import de.willuhn.util.ApplicationException;
import de.willuhn.util.I18N;

/**
 * Dialog zur Auswahl des Wizzards fuer die Erstellung eines neuen Zertifikates.
 */
public class SelectCreateWizzardDialog extends AbstractDialog
{
  private final static Settings settings = Application.getPluginLoader().getPlugin(Plugin.class).getResources().getSettings();
  private final static I18N i18n = Application.getPluginLoader().getPlugin(Plugin.class).getResources().getI18N();
  private final static int WINDOW_WIDTH = 400;

  private CertificateWizzard wizzard = null;
  
  /**
   * ct.
   * @param position
   */
  public SelectCreateWizzardDialog(int position)
  {
    super(position);
    this.setSize(WINDOW_WIDTH,SWT.DEFAULT);
    this.setTitle(i18n.tr("Assistent zur Erstellung eines Schl�ssels"));
  }

  /**
   * @see de.willuhn.jameica.gui.dialogs.AbstractDialog#getData()
   */
  protected Object getData() throws Exception
  {
    return this.wizzard;
  }

  /**
   * @see de.willuhn.jameica.gui.dialogs.AbstractDialog#paint(org.eclipse.swt.widgets.Composite)
   */
  protected void paint(Composite parent) throws Exception
  {
    // Dialog bei Druck auf ESC automatisch schliessen
    parent.addKeyListener(new KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        if (e.keyCode == SWT.ESC)
          throw new OperationCanceledException("wizzard cancelled");
      }
    });

    SimpleContainer c = new SimpleContainer(parent);
    c.addText(i18n.tr("Bitte w�hlen Sie den Assistenten aus, den Sie zur Erstellung des " +
    		              "neuen Schl�ssels nutzen m�chten."),true);
    
    String lastUsed = settings.getString("wizzard.last",null);
    List<CertificateWizzard> list = WizzardUtil.getWizzards();
    CertificateWizzard last = null;
    if (lastUsed != null && lastUsed.length() > 0)
    {
      for (CertificateWizzard w:list)
      {
        if (w.getClass().getName().equals(lastUsed))
        {
          last = w;
          break;
        }
      }
    }
    final SelectInput select = new SelectInput(list,last);
    select.setAttribute("name");
    select.setName(i18n.tr("Assistent"));
    select.setPleaseChoose(i18n.tr("Bitte w�hlen..."));
    
    c.addInput(select);
    
    ButtonArea buttons = c.createButtonArea(2);
    buttons.addButton(i18n.tr("�bernehmen"),new Action()
    {
      public void handleAction(Object context) throws ApplicationException
      {
        CertificateWizzard selected = (CertificateWizzard) select.getValue();
        if (selected == null)
          return;
        
        wizzard = selected;
        settings.setAttribute("wizzard.last",wizzard.getClass().getName());
        close();
      }
    },null,true,"ok.png");
    buttons.addButton(new Cancel());
    getShell().setMinimumSize(getShell().computeSize(WINDOW_WIDTH,SWT.DEFAULT));
  }

}


/**********************************************************************
 * $Log: SelectCreateWizzardDialog.java,v $
 * Revision 1.3  2010/08/10 12:14:24  willuhn
 * @B Dialog-Groesse
 * @N Automatische Vorauswahl des letzten Wizzards
 *
 * Revision 1.2  2009/10/15 22:55:29  willuhn
 * @N Wizzard zum Erstellen von Hibiscus Payment-Server Lizenzen
 *
 * Revision 1.1  2009/10/15 11:50:43  willuhn
 * @N Erste Schluessel-Erstellung via GUI und Wizzard funktioniert ;)
 *
 **********************************************************************/
