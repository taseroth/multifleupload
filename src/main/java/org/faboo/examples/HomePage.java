package org.faboo.examples;

import org.apache.wicket.markup.html.GenericWebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.faboo.examples.fileupload.FileUploadPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomePage extends GenericWebPage<HomePage> {
	private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(HomePage.class);

    private String name;

	public HomePage(final PageParameters parameters) {
		super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new FeedbackPanel("feedback"));
        Form outerForm = new Form("outer-form");
        outerForm.setMultiPart(true);
        add(outerForm);

        TextField nameField = new TextField<>("name", new PropertyModel<String>(this, "name"));
        nameField.setRequired(true);
        outerForm.add(nameField);

        FileUploadPanel fileUploadPanel = new FileUploadPanel("fileupload", 4);
        outerForm.add(fileUploadPanel);

        outerForm.add(new SubmitLink("submit-outer"){
            @Override
            public void onSubmit() {
                super.onSubmit();
            }
        });

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
