package app.textGame_backend.controllers;

import app.textGame_backend.entities.Secrets;
import app.textGame_backend.repositories.SecretsRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/secrets")
public class SecretsController {
    Gson gson = new GsonBuilder().create();

    static final String SUCCESS_MSG = "Success";

    @Autowired
    private SecretsRepository secretsRepository;

    @GetMapping(path="/cloudinarySecrets", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getCloudinarySecrets() {
        Secrets secrets = secretsRepository.getCloudinarySecrets();
        if(secrets != null){
            secrets.setApiSecret(""+secrets.getApiSecret().hashCode());
            secrets.setCloudName(""+secrets.getCloudName().hashCode());
            secrets.setApiKey(""+secrets.getApiKey().hashCode());
            return gson.toJson(secrets);
        }
        return "failed";
    }

}
