package ar.com.sustentate.mw.controllers;

import ar.com.sustentate.mw.managers.ElasticSearchManager;
import ar.com.sustentate.mw.models.ElementosResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ElementosController {
    @Autowired
    private ElasticSearchManager elasticSearchManager;

    @RequestMapping("/elementos")
    public @ResponseBody
    List<ElementosResponse> buscarElementos(@RequestParam(name="criterio", required = true) String criterio) {
        //TODO: Utilizar la clase elementos
       List<ElementosResponse> elementos = new ArrayList<>();
       return elementos;
    }
}
