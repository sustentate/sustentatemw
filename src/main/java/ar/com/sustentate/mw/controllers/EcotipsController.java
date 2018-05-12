package ar.com.sustentate.mw.controllers;

import ar.com.sustentate.mw.managers.CloudantManager;
import ar.com.sustentate.mw.models.EcoTipResponse;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class EcotipsController {
    @Autowired
    private CloudantManager cloudantManager;

    @RequestMapping("/ecotip")
    public @ResponseBody List<EcoTipResponse> getEcoTip(@RequestParam(name="lastId", required = false) String lastId) throws IOException {
        List<EcoTipResponse> tips = cloudantManager.getTips(lastId);
        return tips;
    }
}
