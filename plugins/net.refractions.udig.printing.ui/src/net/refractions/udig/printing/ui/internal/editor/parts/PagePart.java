/*
 *    uDig - User Friendly Desktop Internet GIS client
 *    http://udig.refractions.net
 *    (C) 2004, Refractions Research Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * (http://www.eclipse.org/legal/epl-v10.html), and the Refractions BSD
 * License v1.0 (http://udig.refractions.net/files/bsd3-v10.html).
 *
 */
package net.refractions.udig.printing.ui.internal.editor.parts;

import java.util.List;

import net.refractions.udig.printing.model.Box;
import net.refractions.udig.printing.model.Page;
import net.refractions.udig.printing.model.PropertyListener;
import net.refractions.udig.printing.ui.internal.editor.figures.PageFigure;
import net.refractions.udig.printing.ui.internal.editor.policies.CustomXYLayoutEditPolicy;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

/**
 * Controller (Part) for Printing Pages.
 * 
 * @author Richard Gould
 * @since 0.3
 */
public class PagePart extends AbstractGraphicalEditPart {

    private InternalPropertyListener listener = new InternalPropertyListener();

    protected List< ? > getModelChildren() {
        return getModel().getBoxes();
    }

    protected IFigure createFigure() {
        Figure figure = new PageFigure(getModel().getSize());
        figure.setLayoutManager((new FreeformLayout()));
        return figure;
    }

    protected void createEditPolicies() {
        installEditPolicy(EditPolicy.LAYOUT_ROLE, new CustomXYLayoutEditPolicy());
    }

    public void activate() {
        if (isActive()) {
            return;
        }

        super.activate();
        getModel().eAdapters().add(this.listener);
    }

    public void deactivate() {
        if (!isActive()) {
            return;
        }
        super.deactivate();
        getModel().eAdapters().remove(this.listener);
    }

    @Override
    public Page getModel() {
        return (Page) super.getModel();
    }

    protected void refreshVisuals() {
        Page page = getModel();

        Point loc = new Point(0, 0);
        Dimension size = page.getSize();
        Rectangle rectangle = new Rectangle(loc, size);

        // this should trigger all the resize in PageImpl
        IFigure fig = getFigure();
        // Dimension fSize = fig.getSize();
        fig.setSize(size);
        getParent().setLayoutConstraint(this, fig, rectangle);
        //
        // List<Box> boxes = page.getBoxes();
        // for( Box box : boxes ) {
        // Dimension size2 = box.getSize();
        // System.out.println(size2.width + "/" + size2.height);
        // }

    }

    public GraphicalEditPart getParent() {
        return ((GraphicalEditPart) super.getParent());
    }

    protected class InternalPropertyListener extends PropertyListener {

        protected void boxesChanged() {
            refreshChildren();
        }

        protected void sizeChanged() {
            refreshVisuals();
        }
    }
}
