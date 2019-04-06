package ar.com.sustentate.mw.controllers;

import ar.com.sustentate.mw.managers.CloudantManager;
import ar.com.sustentate.mw.managers.ObjectStorageManager;
import ar.com.sustentate.mw.managers.VisualRecognitionManager;
import ar.com.sustentate.mw.models.ClassificationRequest;
import ar.com.sustentate.mw.models.VisualRecognitionResult;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@RestController
public class ClassificationController {

    @Autowired
    private VisualRecognitionManager visualRecognitionManager;

    @Autowired
    private ObjectStorageManager objectStorageManager;

    @Autowired
    private CloudantManager cloudantManager;

    @RequestMapping(value = "/classification", method = RequestMethod.POST)
    public @ResponseBody VisualRecognitionResult classification(@RequestBody ClassificationRequest classificationRequest) throws IOException {

        // Converts the image insisde the payload into a InputStream
        byte[] imageContents = Base64.decodeBase64(classificationRequest.getEncodedImage());

        File tempFile = File.createTempFile("watson", ".jpeg");
        OutputStream outputStream = new FileOutputStream(tempFile);
        outputStream.write(imageContents);
        outputStream.close();

        try {
            VisualRecognitionResult results = visualRecognitionManager.recognize(tempFile);
            objectStorageManager.saveImage(tempFile, "recognition");
            cloudantManager.saveRecognitionData(results, tempFile, classificationRequest);
            return results;
        }
        catch (Exception e) {
            return null;
        }
        finally {
            tempFile.delete();
        }
    }
}
