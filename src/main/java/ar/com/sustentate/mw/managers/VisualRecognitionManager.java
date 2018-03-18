package ar.com.sustentate.mw.managers;

import ar.com.sustentate.mw.models.VisualRecognitionParameters;
import ar.com.sustentate.mw.models.VisualRecognitionResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

@Component
public class VisualRecognitionManager {

    @Autowired
    private VisualRecognition visualRecognition;

    public VisualRecognitionResult recognize(File image) throws JsonProcessingException, FileNotFoundException {
        ObjectMapper mapper = new ObjectMapper();
        VisualRecognitionParameters parameters = new VisualRecognitionParameters();
        parameters.getOwners().add("me");

        String parametersJson = mapper.writeValueAsString(parameters);

        ClassifyOptions options = new ClassifyOptions.Builder()
                .imagesFile(image)
                .parameters(parametersJson)
                .build();
        ClassifiedImages result = visualRecognition.classify(options).execute();
        VisualRecognitionResult recognitionResult = new VisualRecognitionResult();
        if (result.getImages().size() != 1) {
            recognitionResult.setErrorCode(1);
            recognitionResult.setErrorDesc("no image was recognized");
            return recognitionResult;
        }

        ClassifiedImage classifiedImage = result.getImages().get(0);
        if (classifiedImage.getClassifiers().size() != 1) {
            recognitionResult.setErrorCode(2);
            recognitionResult.setErrorDesc("incorrect amount of classifiers");
            return recognitionResult;
        }
        List<ClassResult> classResults =  classifiedImage.getClassifiers().get(0).getClasses();

        Float recScore = 0f;
        Float noRecScore = 0f;
        for(ClassResult classResult : classResults) {
            if (classResult.getClassName().endsWith("_rec")) {
                if (recScore < classResult.getScore())
                    recScore = classResult.getScore();

            } else {
                if (noRecScore < classResult.getScore()) {
                    noRecScore = classResult.getScore();
                }
            }
        }

        if (recScore > noRecScore)
            recognitionResult.setRecognitionResult(1);
        else
            recognitionResult.setRecognitionResult(0);

        recognitionResult.setErrorDesc("");
        return recognitionResult;
    }
}
