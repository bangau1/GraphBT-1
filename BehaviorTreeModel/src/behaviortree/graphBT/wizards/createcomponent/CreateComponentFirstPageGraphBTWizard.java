package behaviortree.graphBT.wizards.createcomponent;

import java.util.HashMap;

import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import behaviortree.Component;
import behaviortree.GraphBTUtil;
import behaviortree.Operator;
import behaviortree.TraceabilityStatus;


public class CreateComponentFirstPageGraphBTWizard extends WizardPage {
	
	private Composite container;
	private HashMap<Integer,String> map;
	private Diagram d;

	
	public CreateComponentFirstPageGraphBTWizard(HashMap<Integer,String> map, Diagram d) {
		super("Create Component Wizard");
		setTitle("Create Component Wizard");
		setDescription("Fill in the Behavior Tree node elements below.");
		this.map = map;
		this.d=d;
	}

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;

	    final Label componentLabel = new Label(container, SWT.NULL);
		componentLabel.setText("Component Name");
		
		final Text componentNameText = new Text(container, SWT.BORDER | SWT.SINGLE);
		final Label componentRefLabel = new Label(container, SWT.NULL);
		componentRefLabel.setText("Component Ref");
		//componentRefLabel.setVisible(false);
		
		final Text componentRefText = new Text(container, SWT.BORDER | SWT.SINGLE);
		//componentRefText.setVisible(false);
		
		
		componentNameText.setText("");
		
		componentNameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				Text t= (Text) e.widget;
				map.put(Component.COMPONENT_NAME, t.getText());
			}
	    });
		
		componentRefText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				Text t= (Text) e.widget;
				map.put(Component.COMPONENT_REF, t.getText());
			}
	    });


		//System.out.println("stringCarrier[0] " + stringCarrier[0]);
		System.out.println("stringCarrier[0].getText() " + componentNameText.getText());
		// Required to avoid an error in the system
		setControl(container);
	}
}