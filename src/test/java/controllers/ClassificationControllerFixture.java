package controllers;

import ar.com.sustentate.mw.Application;
import ar.com.sustentate.mw.controllers.ClassificationController;
import ar.com.sustentate.mw.managers.VisualRecognitionManager;
import ar.com.sustentate.mw.models.ClassificationRequest;
import ar.com.sustentate.mw.models.VisualRecognitionResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.servlet.annotation.MultipartConfig;
import java.io.IOException;
import java.util.Arrays;

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class ClassificationControllerFixture {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @InjectMocks
    private ClassificationController classificationController;

    @Mock
    private VisualRecognitionManager visualRecognitionManager;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(classificationController).build();
    }

    @Test
    public void classificationOk() throws Exception {
        // Arrange
        ClassificationRequest request = new ClassificationRequest();
        request.setEncodedImage("aG9sYQ==");
        VisualRecognitionResult recognitionResult = new VisualRecognitionResult();
        recognitionResult.setRecognitionResult(1);
        recognitionResult.setErrorCode(0);
        Mockito.when(visualRecognitionManager.recognize(Mockito.any()))
                .thenReturn(recognitionResult);

        // Act
        MvcResult result = mockMvc.perform(post("/classification")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.json(request)))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        VisualRecognitionResult decodedResult = mapper.readValue(content, VisualRecognitionResult.class);

        // Assert
        Mockito.verify(visualRecognitionManager).recognize(Mockito.any());
        Assert.assertTrue(decodedResult.getRecognitionResult() == 1);
        Assert.assertEquals(0, decodedResult.getErrorCode());
    }

    @Test
    public void classificationErrorMissingFile() throws Exception {
        // Arrange
        ClassificationRequest request = new ClassificationRequest();

        // Act
        mockMvc.perform(post("/classification")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.json(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("image missing"));


        // Assert
        throw new NotImplementedException();
    }

    @Test
    public void todo() {
        //TODO: Add missing tests
        throw new NotImplementedException();
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}
