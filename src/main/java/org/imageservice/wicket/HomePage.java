package org.imageservice.wicket;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;

public class HomePage extends BasePage {
    
//    private MainForm mainForm;

    public HomePage() {
        super();
        add(new ImageUploader("uploaderPanel"));
//        add(mainForm = new MainForm("mainForm"));
    }
    
    public class MainForm extends Form {
        
        private WebMarkupContainer uploaderContainer;
        private WebMarkupContainer galleryContainer;

        public MainForm(String id) {
            super(id);
            
            uploaderContainer = new WebMarkupContainer("uploaderContainer");
            add(uploaderContainer.setOutputMarkupId(true));
            
            uploaderContainer.add(new ImageUploader("uploaderPanel"));
            
            galleryContainer = new WebMarkupContainer("galleryContainer");
            add(galleryContainer.setOutputMarkupId(true));
        }
        
    }
}
