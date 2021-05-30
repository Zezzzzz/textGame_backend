package app.textGame_backend.entities;


import javax.persistence.*;

@Entity
@Table
public class Secrets {
    @Id
    @Column(name="cloud_name")
    private String cloudName;

    @Column(name="api_Key")
    private String apiKey;

    @Column(name="api_Secret")
    private String apiSecret;

    public Secrets() {
    }

    public Secrets(String apiKey, String cloudName, String apiSecret) {
        this.apiKey = apiKey;
        this.cloudName = cloudName;
        this.apiSecret = apiSecret;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getCloudName() {
        return cloudName;
    }

    public void setCloudName(String cloudName) {
        this.cloudName = cloudName;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }
}
