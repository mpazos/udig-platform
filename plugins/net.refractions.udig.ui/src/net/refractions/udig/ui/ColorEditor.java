/* uDig - User Friendly Desktop Internet GIS client
 * http://udig.refractions.net
 * (C) 2013, Refractions Research Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * (http://www.eclipse.org/legal/epl-v10.html), and the Refractions BSD
 * License v1.0 (http://udig.refractions.net/files/bsd3-v10.html).
 */
package net.refractions.udig.ui;

import org.eclipse.jface.preference.ColorSelector;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;

/**
 * A "button" of a certain color determined by the color picker.
 * 
 * Bridges from superclass {@link ColorSelector} implementation to notify using
 * selection changed events.
 * 
 * @author Jody Garnett 
 */
public class ColorEditor extends ColorSelector {

    private SelectionListener listener = null;
    
    /**
     * Construct <code>ColorEditor</code>.
     * 
     * @param parent
     */
    public ColorEditor( Composite parent ) {
        super( parent );
    }
    
    @Deprecated
    public void setListener( SelectionListener selectionListener ) {
        if( listener != null ){
            getButton().removeSelectionListener(listener);
        }
        listener = selectionListener;
        if( listener != null ){
            getButton().addSelectionListener(listener);
        }
    }

    public java.awt.Color getColor(){
        RGB rgb = getColorValue();
        return new java.awt.Color( rgb.red, rgb.green, rgb.blue);
    }
    
    public void setColor( java.awt.Color color ){
        if( color == null ){
            setColorValue( null );
        }
        else {
            RGB rgb = new RGB(color.getRed(), color.getGreen(), color.getBlue() );
            setColorValue( rgb );    
        }                
    }
}
