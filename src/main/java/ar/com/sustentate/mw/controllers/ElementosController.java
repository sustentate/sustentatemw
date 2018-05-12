package ar.com.sustentate.mw.controllers;

import ar.com.sustentate.mw.managers.CloudantManager;
import ar.com.sustentate.mw.managers.ElasticSearchManager;
import ar.com.sustentate.mw.models.ElementosResponse;
import ar.com.sustentate.mw.models.OperationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ElementosController {
    @Autowired
    private ElasticSearchManager elasticSearchManager;

    @Autowired
    private CloudantManager cloudantManager;

    @RequestMapping("/elementos")
    public @ResponseBody
    List<ElementosResponse> buscarElementos(@RequestParam(name="criterio", required = true) String criterio) {
        //TODO: Utilizar la clase elementos
       List<ElementosResponse> elementos = new ArrayList<>();
       return elementos;
    }

    @RequestMapping(value = "/elementos/sync", method = RequestMethod.POST)
    public @ResponseBody
    OperationResponse sync() throws IOException {
        OperationResponse response = new OperationResponse();
        elasticSearchManager.savePrueba();
        response.setStatus("OK");
        return response;
    }
}
