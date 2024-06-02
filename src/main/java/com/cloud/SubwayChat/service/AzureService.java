package com.cloud.SubwayChat.service;
import com.azure.resourcemanager.AzureResourceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AzureService {

    private final AzureResourceManager azureResourceManager;

    public void scaleVirtualMachineSet(String resourceGroupName, String vmssName, int newCapacity) {
        var vmss = azureResourceManager.virtualMachineScaleSets().getByResourceGroup(resourceGroupName, vmssName);
        vmss.update().withCapacity(newCapacity).apply();
    }
}