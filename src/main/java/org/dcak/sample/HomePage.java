package org.dcak.sample;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.repeater.tree.DefaultNestedTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.ITreeProvider;
import org.apache.wicket.extensions.markup.html.repeater.tree.content.Folder;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;
import org.dcak.sample.bean.SampleBean;
import org.dcak.sample.panel.EditSampleBeanPanel;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

        private final IModel<SampleBean> sampleModel = new Model<>();
        
	public HomePage(final PageParameters parameters) {
		super(parameters);

                List<ITab> iTabs = new ArrayList<>();
                iTabs.add(new AbstractTab(Model.of("Edit tab")) {
                    @Override
                    public WebMarkupContainer getPanel(String string) {
                        return new EditSampleBeanPanel(string, sampleModel);
                    }
                });
                
                iTabs.add(new AbstractTab(Model.of("Other tab")) {
                    @Override
                    public WebMarkupContainer getPanel(String string) {
                        return new WebMarkupContainer(string);
                    }
                });
                
		add(new Label("stringModelValue", PropertyModel.of(sampleModel, "value")).setOutputMarkupId(true));
                add(new AjaxTabbedPanel("tabs", iTabs));
                add(new DefaultNestedTree<SampleBean>("tree", new ITreeProvider<SampleBean>() {
                    
                    @Override
                    public Iterator<? extends SampleBean> getRoots() {
                        List l = new ArrayList();
                        l.add(new SampleBean("n1", "Node1"));
                        l.add(new SampleBean("n2", "Node2"));
                        l.add(new SampleBean("n3", "Node3"));
                        return l.iterator();
                    }

                    @Override
                    public boolean hasChildren(SampleBean t) {
                        return false;
                    }

                    @Override
                    public Iterator<? extends SampleBean> getChildren(SampleBean t) {
                        return t != null ? new ArrayList().iterator() : null;
                    }

                    @Override
                    public IModel<SampleBean> model(SampleBean t) {
                        return new Model(t);
                    }

                    @Override
                    public void detach() {
                    }
                }) {
                    @Override
                    protected Component newContentComponent(String id, IModel<SampleBean> node) {
                        return new Folder<SampleBean>(id, this, node) {
                            @Override
                            protected Component newLabelComponent(String id, IModel<SampleBean> model) {
                                return new Label(id, PropertyModel.of(model, "value"));
                            }

                            @Override
                            protected MarkupContainer newLinkComponent(String id, final IModel<SampleBean> model) {
                                return new AjaxLink(id) {
                                    @Override
                                    public void onClick(AjaxRequestTarget art) {
                                        sampleModel.setObject(model.getObject());
                                        art.add(HomePage.this.get("stringModelValue"));
                                        art.add(HomePage.this.get("tabs"));
                                        
                                        clearInput();
                                    }
                                };
                            }
                        };
                    }
                    
                }.setOutputMarkupId(true));

    }
	
	private void clearInput()
	{
		visitChildren(Form.class, new IVisitor<Form, Void>() {
			 public void component(Form form, IVisit<Void> visit) {
				 form.clearInput();
			 }
		});
	}
}
