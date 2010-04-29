package net.refractions.udig.feature.editor;

import java.util.List;

import net.refractions.udig.feature.editor.AbstractPageBookView.PageRec;
import net.refractions.udig.internal.ui.UiPlugin;
import net.refractions.udig.project.ILayer;
import net.refractions.udig.project.IMap;
import net.refractions.udig.project.ui.ApplicationGIS;
import net.refractions.udig.project.ui.IFeatureSite;
import net.refractions.udig.project.ui.feature.FeaturePanelProcessor;
import net.refractions.udig.project.ui.feature.FeatureSiteImpl;
import net.refractions.udig.project.ui.feature.FeaturePanelProcessor.FeaturePanelEntry;
import net.refractions.udig.project.ui.internal.ProjectUIPlugin;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.part.IPageBookViewPage;
import org.eclipse.ui.part.MessagePage;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.PageBookView;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.FeatureType;

/**
 * View allowing direct editing of the currently selected feature.
 * <p>
 * The currently selected feature is handled by the EditManager.
 * 
 * @author jodyg
 * @since 1.2.0
 */
public class FeatureView2 extends PageBookView {

    public static final String ID = "net.refractions.udig.feature.editor.featureView";

    @Override
    protected IPage createDefaultPage( PageBook book ) {
        MessagePage page = new MessagePage();        
        page.setMessage("Default Page");
        
        initPage((IPageBookViewPage) page);        
        page.createControl( getPageBook() );
        
        return page;
    }

    @Override
    protected PageRec doCreatePage( IWorkbenchPart part ) {
        IMap map = (IMap) part.getAdapter( IMap.class );        
        if( map == null ) {
            MessagePage page = new MessagePage();
            page.setMessage( "Please select a Map" );
            initPage( page );
            page.createControl(getPageBook());
            
            PageRec rec = new PageRec( part, page );        
            return rec;
        }
        
        IPage page = (IPage) new FeaturePage(map.getEditManager());
        initPage((IPageBookViewPage) page);
        page.createControl(getPageBook());
        return new PageRec(part, page);    
    }

    @Override
    protected void doDestroyPage( IWorkbenchPart part, PageRec pageRecord ) {
    }

    @Override
    protected IWorkbenchPart getBootstrapPart() {
        IWorkbenchWindow window = UiPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow();
        if( window == null ) return null;
        
        IWorkbenchPage page = window.getActivePage();
        IEditorPart editor = page.getActiveEditor();
        if( editor == null ) return null;
        
        IMap map = (IMap) editor.getAdapter( IMap.class );
        if( map == null ) return null;
        
        return editor;
    }

    @Override
    protected boolean isImportant( IWorkbenchPart part ) {
        IMap map = (IMap) part.getAdapter( IMap.class );
        
        return map != null;
    }



}