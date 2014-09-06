/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.imageservice.wicket;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.MultiFileUploadField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.util.lang.Bytes;
import org.imageservice.services.FileService;
import org.imageservice.services.FileServiceImpl;
import org.imageservice.services.ImageEntry;

/**
 *
 * @author aishmanov
 */
public class ImageUploader extends Panel {

    private UploadForm uploadForm;
    private ViewForm viewForm;
    private Label counterText;
    private MultiFileUploadField fileUpload;
    private Long counter = 100L;
    private final FileService service;

    public ImageUploader(String id) {
        super(id);

        service = new FileServiceImpl();

        counterText = new Label("counter", new PropertyModel((AppSession) this.getSession(), "images.size"));
        add(counterText.setOutputMarkupId(true));

        uploadForm = new UploadForm("uploadForm");
        add(uploadForm.setOutputMarkupId(true));

        viewForm = new ViewForm("viewForm");
        add(viewForm.setOutputMarkupId(true));
    }

    //Upload images
    private class UploadForm extends Form {

        private final List<FileUpload> uploads;

        public UploadForm(String id) {
            super(id);

            setMultiPart(true);
            setMaxSize(Bytes.kilobytes(8172));
            
            uploads = new ArrayList<>();
            
            fileUpload = new MultiFileUploadField("fileUpload", new PropertyModel<List<FileUpload>>(this, "uploads"));
            add(fileUpload);

            add(new AjaxLink("uploadButton") {
                @Override
                public void onClick(AjaxRequestTarget art) {
                    fileUpload.updateModel();
                    for (FileUpload item : uploads) {
                        service.savePhotoWithThumbs(counter, item.getBytes(), item.getClientFileName());
                        counter++;
                        getImages().add(new ImageEntry(item.getClientFileName(), item.getContentType(), item.getBytes()));
                    }
                    art.add(counterText);
                    art.add(uploadForm);
                    art.add(viewForm);
                }
            });
        }

//        @Override
//        protected void onSubmit() {
//            for (FileUpload item : uploads) {
//                service.savePhotoWithThumbs(counter, item.getBytes(), item.getClientFileName());
//                counter++;
//                getImages().add(new ImageEntry(item.getClientFileName(), item.getContentType(), item.getBytes()));
//            }
//        }
        public List<FileUpload> getUploads() {
            return uploads;
        }
    }

    //Display images
    private class ViewForm extends Form {

        private ListView imagesView;
        private WebMarkupContainer viewContainer;

        public ViewForm(String id) {
            super(id);
            viewContainer = new WebMarkupContainer("viewContainer");
            imagesView = new ListView("imagesView", new PropertyModel((AppSession) this.getSession(), "images")) {
                @Override
                protected void populateItem(ListItem li) {
                    final ImageEntry ti = (ImageEntry) li.getModelObject();
                    Image image = new Image("image", new DynamicImageResource() {
                        @Override
                        protected byte[] getImageData(IResource.Attributes atrbts) {
                            return ti.getImageBody();
                        }
                    });
                    li.add(image);
                }
            };
            viewContainer.add(imagesView.setOutputMarkupId(true));
            add(viewContainer.setOutputMarkupId(true));
        }

    }

    private List<ImageEntry> getImages() {
        return ((AppSession) this.getSession()).getImages();
    }
}
