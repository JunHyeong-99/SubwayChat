package com.cloud.SubwayChat.core.config;

import com.azure.core.management.AzureEnvironment;
import com.azure.core.management.profile.AzureProfile;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.resourcemanager.AzureResourceManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureConfig {

    @Value("${azure.client-id}")
    private String clientId;

    @Value("${azure.tenant-id}")
    private String tenantId;

    @Value("${azure.client-secret}")
    private String clientSecret;

    @Bean
    public ClientSecretCredential clientSecretCredential() {
        return new ClientSecretCredentialBuilder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .tenantId(tenantId)
                .build();
    }

    @Bean
    public AzureResourceManager azureResourceManager(ClientSecretCredential clientSecretCredential) {
        AzureProfile profile = new AzureProfile(AzureEnvironment.AZURE);
        AzureResourceManager.Authenticated authenticated = AzureResourceManager
                .authenticate(clientSecretCredential, profile);

        return authenticated.withDefaultSubscription();
    }
}
