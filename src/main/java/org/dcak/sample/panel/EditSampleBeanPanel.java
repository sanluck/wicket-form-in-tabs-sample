/*
 * Copyright 2016 Lukyanov A.A. <sanluck@mail.ru>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dcak.sample.panel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.dcak.sample.bean.SampleBean;

/**
 *
 * @author Lukyanov A.A. <sanluck@mail.ru>
 */
public class EditSampleBeanPanel extends Panel {
    
    public EditSampleBeanPanel(String id, IModel<SampleBean> model) {
        super(id, model);
        setOutputMarkupId(true);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize(); 
        add(new FeedbackPanel("feedback").setOutputMarkupId(true));
        Form form = new Form("form", getDefaultModel());
        form.add(new TextField("nodeId", PropertyModel.of(getDefaultModel(), "id")).setRequired(true));
        form.add(new TextField("nodeValue", PropertyModel.of(getDefaultModel(), "value")).setRequired(true));
        form.add(new AjaxSubmitLink("save", form) {
            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                super.onError(target, form);
                error("It's an error!");
                target.add(EditSampleBeanPanel.this.get("feedback"));
            }

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                super.onSubmit(target, form); 
                target.add(getPage().get("tree"));
            }
            
        });
        form.add(new AjaxLink("cancel") {
            @Override
            public void onClick(AjaxRequestTarget art) {
                getSampleBeanModel().setObject(new SampleBean());
                art.add(EditSampleBeanPanel.this);
                art.add(getPage().get("tree"));
            }
        });
        add(form);
    }
    
    private IModel<SampleBean> getSampleBeanModel() {
        return (IModel<SampleBean>) EditSampleBeanPanel.this.getDefaultModel();
    }
    
 }
