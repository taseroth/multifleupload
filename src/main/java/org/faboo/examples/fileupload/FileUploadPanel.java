package org.faboo.examples.fileupload;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.markup.repeater.util.ModelIteratorAdapter;
import org.apache.wicket.model.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Bert.radke <bert.radke@t-systems.com>
 */
public class FileUploadPanel extends Panel {

    private final int maxFilesToUpload;

    private List<UploadData> uploads = new ArrayList<>();

    FileUploadField fileUploadField;

    public FileUploadPanel(String id, int maxFilesToUpload) {
        super(id);
        this.maxFilesToUpload = maxFilesToUpload;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new Label("maxFilesToUpload", new StringResourceModel("maxtext", this, Model.of(this))));

        add(new Label("sum", new AbstractReadOnlyModel<Long>() {
            @Override
            public Long getObject() {
                long sum = 0L;
                for (UploadData upload : uploads) {
                    sum += upload.getFile().length();
                }
                return sum;
            }
        }));
        // ++++++++++ view uploaded files ++++++++++++++++++++++++
        add(new RefreshingView<UploadData>("uploads") {

            @Override
            protected Iterator<IModel<UploadData>> getItemModels() {
                return new ModelIteratorAdapter<UploadData>(uploads) {
                    @Override
                    protected IModel<UploadData> model(UploadData object) {
                        return new CompoundPropertyModel<>(object);
                    }
                };
            }

            @Override
            protected void populateItem(final Item<UploadData> item) {
                item.add(new Label("name"));
                item.add(new Link<Void>("remove") {
                    @Override
                    public void onClick() {
                        UploadData uploadData = item.getModelObject();
                        uploadData.getFile().delete();
                        uploads.remove(uploadData);
                    }
                });
            }
        });

        // +++++++++ add new uploads +++++++
        Form<Void> form = new Form<Void>("form") {
            @Override
            protected void onSubmit() {
                super.onSubmit();
            }
        };
        add(form);
        form.setMultiPart(true);

        form.add(new SubmitLink("submit") {
            @Override
            public void onSubmit() {
                super.onSubmit();
                createUploadData(fileUploadField.getFileUpload());
            }

            @Override
            protected boolean isLinkEnabled() {
                return uploads.size() < maxFilesToUpload;
            }
        });

        fileUploadField = new FileUploadField("upload") {
            @Override
            public boolean isEnabled() {
                return uploads.size() < maxFilesToUpload;
            }
        };
        fileUploadField.setRequired(true);
        form.add(fileUploadField);
    }

    /**
     * Creates a new UploadData and adds it to the uploads.
     */
    private void createUploadData(FileUpload upload) {
        try {
            System.out.println("new file size" + upload.getSize());
            File tempFile = upload.writeToTempFile();
            tempFile.deleteOnExit();
            uploads.add(new UploadData(upload.getClientFileName(), tempFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getMaxFilesToUpload() {
        return maxFilesToUpload;
    }
}
