package managers;

import ar.com.sustentate.mw.managers.VisualRecognitionManager;
import ar.com.sustentate.mw.models.VisualRecognitionResult;
import ar.com.sustentate.mw.models.VisualRecognitionParameters;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.http.ServiceCallback;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.*;
import jersey.repackaged.jsr166e.CompletableFuture;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class VisualRecognitionManagerFixture {

    @Mock
    private VisualRecognition visualRecognition;

    @InjectMocks
    private VisualRecognitionManager visualRecognitionManager;

    @Before
    public void setup() {
        ReflectionTestUtils.setField(visualRecognitionManager, "classifierId", "Clasificador");
    }

    @Test
    public void classifyNoImageRecognized() {
        //TODO: para cuando el errorcode = 1
        throw new NotImplementedException();
    }

    @Test
    public void classifyWrongClassifiers() {
        //TODO: para cuando errocode = 2
        throw new NotImplementedException();
    }

    @Test
    public void classify() throws JsonProcessingException, FileNotFoundException {
        // Arrange
        File image = null;
        Mockito.when(visualRecognition.classify(Mockito.any()))
                .thenReturn(new ServiceCall<ClassifiedImages>() {
                    @Override
                    public ClassifiedImages execute() throws RuntimeException {
                        ClassifiedImages classifiedImages = new ClassifiedImages();
                        List<ClassifiedImage> classifiedImagesList = new ArrayList<>();
                        ClassifiedImage image = new ClassifiedImage();
                        classifiedImagesList.add(image);
                        classifiedImages.setImages(classifiedImagesList);
                        List<ClassifierResult> classifierResults = new ArrayList<>();
                        ClassifierResult classifierResult = new ClassifierResult();
                        List<ClassResult> classResults = new ArrayList<>();
                        classifierResult.setClasses(classResults);
                        ClassResult classResult = new ClassResult();
                        classResult.setScore(1f);
                        classResult.setClassName("bottle_rec");
                        classResults.add(classResult);
                        classifierResults.add(classifierResult);
                        image.setClassifiers(classifierResults);
                        return classifiedImages;
                    }

                    @Override
                    public void enqueue(ServiceCallback<? super ClassifiedImages> serviceCallback) {

                    }

                    @Override
                    public CompletableFuture<ClassifiedImages> rx() {
                        return null;
                    }
                });

        // Act
        VisualRecognitionResult results = visualRecognitionManager.recognize(image);

        // Assert
        // Verify that the image classification was called.
        Mockito.verify(visualRecognition).classify(Mockito.argThat(new Matcher<ClassifyOptions>() {
            @Override
            public boolean matches(Object o) {
                ClassifyOptions options = (ClassifyOptions) o;
                ObjectMapper mapper = new ObjectMapper();
                try {
                    VisualRecognitionParameters parameters = mapper.readValue(options.parameters(), VisualRecognitionParameters.class);
                    Assert.assertEquals(1, parameters.getClassifierIds().size());
                    Assert.assertEquals("Clasificador", parameters.getClassifierIds().get(0));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return true;
            }

            @Override
            public void describeMismatch(Object o, Description description) {

            }

            @Override
            public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {

            }

            @Override
            public void describeTo(Description description) {

            }
        }));
        Mockito.verify(visualRecognition).classify(Mockito.any());
        Assert.assertEquals(0, results.getErrorCode());
        Assert.assertTrue(results.getErrorDesc().isEmpty());
        Assert.assertTrue(results.getRecognitionResult() == 1);
    }

    @Test
    public void todo() {
        //TODO add missing
        throw new NotImplementedException();
    }
}
