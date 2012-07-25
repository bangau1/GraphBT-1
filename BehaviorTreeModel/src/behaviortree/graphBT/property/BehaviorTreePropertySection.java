package behaviortree.graphBT.property;

import org.eclipse.graphiti.features.IFeature;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.impl.CustomContext;
import org.eclipse.graphiti.features.impl.AbstractFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.platform.GFPropertySection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import behaviortree.*;

public class BehaviorTreePropertySection extends GFPropertySection 
	implements ITabbedPropertyConstants {
	private Text componentText;
	private Text behaviorText;
	private CCombo operatorCombo;
	private CCombo statusCombo;

    @Override
    public void createControls(Composite parent, 
    		TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);

        TabbedPropertySheetWidgetFactory factory = getWidgetFactory();
        Composite composite = factory.createFlatFormComposite(parent);

        FormData componentData;
        FormData behaviorData;
        FormData operatorData;
        FormData statusData;
        
        componentText = factory.createText(composite, "");
        behaviorText = factory.createText(composite, "");
        operatorCombo = factory.createCCombo(composite);
        statusCombo = factory.createCCombo(composite);
        
        behaviorText.addModifyListener(modifyListenerBehavior());
        componentText.addModifyListener(modifyListenerComponent());
        
        for(TraceabilityStatus ts : TraceabilityStatus.VALUES) {
        	statusCombo.add(ts.getName());
	    }
        
        for(Operator ts : Operator.VALUES) {
        	operatorCombo.add(ts.getName());
	    }

        componentData = new FormData();
        componentData.left = new FormAttachment(0, STANDARD_LABEL_WIDTH*2);
        componentData.right = new FormAttachment(100, 0);
        componentData.top = new FormAttachment(0, VSPACE);
        //componentData.bottom = new FormAttachment(behaviorText, VSPACE);
        componentText.setLayoutData(componentData);

        CLabel valueLabel2 = factory.createCLabel(composite, "Component Name:");
        componentData = new FormData();
        componentData.left = new FormAttachment(0, 0);
        componentData.right = new FormAttachment(componentText, -HSPACE);
        componentData.top = new FormAttachment(componentText, 0, SWT.CENTER);
        valueLabel2.setLayoutData(componentData);
        
        behaviorData = new FormData();
        behaviorData.left = new FormAttachment(0, STANDARD_LABEL_WIDTH * 2);
        behaviorData.right = new FormAttachment(100, 0);
        behaviorData.top = new FormAttachment(componentText, VSPACE);
        behaviorText.setLayoutData(behaviorData);
        
        CLabel valueLabel3 = factory.createCLabel(composite, "Behavior Name:");
        behaviorData = new FormData();
        behaviorData.left = new FormAttachment(0, 0);
        behaviorData.right = new FormAttachment(behaviorText, -HSPACE);
        behaviorData.top = new FormAttachment(behaviorText, 0, SWT.CENTER);
        valueLabel3.setLayoutData(behaviorData);
        
        operatorData = new FormData();
        operatorData.left = new FormAttachment(0, STANDARD_LABEL_WIDTH * 2);
        operatorData.right = new FormAttachment(100, 0);
        operatorData.top = new FormAttachment(behaviorText, VSPACE);
        operatorCombo.setLayoutData(operatorData);
        
        CLabel valueLabel4 = factory.createCLabel(composite, "Operator Name:");
        operatorData = new FormData();
        operatorData.left = new FormAttachment(0, 0);
        operatorData.right = new FormAttachment(operatorCombo, -HSPACE);
        operatorData.top = new FormAttachment(operatorCombo, 0, SWT.CENTER);
        valueLabel4.setLayoutData(operatorData);
        
        statusData = new FormData();
        statusData.left = new FormAttachment(0, STANDARD_LABEL_WIDTH * 2);
        statusData.right = new FormAttachment(100, 0);
        statusData.top = new FormAttachment(operatorCombo, VSPACE);
        statusCombo.setLayoutData(statusData);
        
        CLabel valueLabel5 = factory.createCLabel(composite, "Traceability Status Name");
        statusData = new FormData();
        statusData.left = new FormAttachment(0, 0);
        statusData.right = new FormAttachment(statusCombo, -HSPACE);
        statusData.top = new FormAttachment(statusCombo, 0, SWT.CENTER);
        valueLabel5.setLayoutData(statusData);

    }

    @Override
    public void refresh() {
        PictogramElement pe = getSelectedPictogramElement();

        if (pe != null) {
            Object bo = Graphiti.getLinkService()
                 .getBusinessObjectForLinkedPictogramElement(pe);

            if (bo == null)
                return;
            
            behaviorText.addModifyListener(modifyListenerBehavior());
            componentText.addModifyListener(modifyListenerComponent());
        }
    }
    
    private ModifyListener modifyListenerComponent() {
    	return new ModifyListener(){
	    	@Override
	    	public void modifyText(ModifyEvent e) {
	    		String value = componentText.getText();
	    		if (value == null) {
	    			value = "";
	    		}
	    		PictogramElement pe = getSelectedPictogramElement();
	    		System.out.println("Ini objeknya punya tipenya apa eaaa");
	    		if (pe != null) {
	    			Object bo = Graphiti.getLinkService().
	    					getBusinessObjectForLinkedPictogramElement(pe);
	    			if (bo == null)
	    				return;
	    			String name = ((StandardNode) bo).getComponent().getComponentName();
	    			System.out.println("Component: " + name);
	    			if (value.equals(name))
	    				return;
	    		}
	    		final String typedValue = value;
	    		IFeature feature = new AbstractFeature(
	    				getDiagramTypeProvider().getFeatureProvider()) {
	    			@Override
	    			public void execute(IContext context) {
	    				PictogramElement pe = getSelectedPictogramElement();
	    				if (pe != null) {
	    					Object bo = Graphiti.getLinkService().
	    							getBusinessObjectForLinkedPictogramElement(pe);
	    					
	    					if (bo == null)
	    						return;
	    					
	    					if(bo instanceof StandardNode)
	    					{
	    						StandardNode node = (StandardNode) bo;
	    						System.out.println("StandardNode...");
	    						node.getComponent().setComponentName(typedValue);
	    					}
//	    					if(bo instanceof Component)
//	    					{
//	    						Component eClass = (Component) bo;
//	    						System.out.println("Component...");
//	    					}
//	    					if(bo instanceof Behavior)
//	    					{
//	    						Behavior eClass = (Behavior) bo;
//	    						System.out.println("behavior...");
//	    					}
	    				}
	    			}
	    			
	    			@Override
	    			public boolean canExecute(IContext context) {
	    				return true;
	    			}
	    		};
	    		CustomContext context = new CustomContext();
	    		execute(feature, context);
	    	}
	    };
    }
    
    private ModifyListener modifyListenerBehavior() {
    	return new ModifyListener(){
	    	@Override
	    	public void modifyText(ModifyEvent e) {
	    		String value = behaviorText.getText();
	    		if (value == null) {
	    			value = "";
	    		}
	    		PictogramElement pe = getSelectedPictogramElement();
	    		System.out.println("Ini objeknya punya tipenya apa eaaa");
	    		if (pe != null) {
	    			Object bo = Graphiti.getLinkService().
	    					getBusinessObjectForLinkedPictogramElement(pe);
	    			if (bo == null)
	    				return;
	    			String name = ((StandardNode) bo).getBehavior().toString();
	    			System.out.println("Behavior: " + name);
	    			if (value.equals(name))
	    				return;
	    		}
	    		final String typedValue = value;
	    		IFeature feature = new AbstractFeature(
	    				getDiagramTypeProvider().getFeatureProvider()) {
	    			@Override
	    			public void execute(IContext context) {
	    				PictogramElement pe = getSelectedPictogramElement();
	    				if (pe != null) {
	    					Object bo = Graphiti.getLinkService().
	    							getBusinessObjectForLinkedPictogramElement(pe);
	    					
	    					if (bo == null)
	    						return;
	    					
	    					if(bo instanceof StandardNode)
	    					{
	    						StandardNode node = (StandardNode) bo;
	    						System.out.println("StandardNode...");
	    						node.getBehavior().setBehaviorName(typedValue);
	    						//setBehaviorName(typedValue);
	    					}
//	    					if(bo instanceof Component)
//	    					{
//	    						Component eClass = (Component) bo;
//	    						System.out.println("Component...");
//	    					}
//	    					if(bo instanceof Behavior)
//	    					{
//	    						Behavior eClass = (Behavior) bo;
//	    						System.out.println("behavior...");
//	    					}
	    				}
	    			}
	    			
	    			@Override
	    			public boolean canExecute(IContext context) {
	    				return true;
	    			}
	    		};
	    		CustomContext context = new CustomContext();
	    		execute(feature, context);
	    	}
	    };
    }
}