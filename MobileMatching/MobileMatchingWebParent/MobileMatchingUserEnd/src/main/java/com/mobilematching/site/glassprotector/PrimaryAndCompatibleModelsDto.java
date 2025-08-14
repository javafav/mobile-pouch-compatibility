package com.mobilematching.site.glassprotector;

import java.util.List;

import com.mobilematching.common.entity.Mobile;
import com.mobilematching.common.entity.GlassProtector;

public class PrimaryAndCompatibleModelsDto {
    private Mobile primaryModel;
    private List<Mobile> compatibleModels;
    private List<GlassProtector> protectors; // NEW FIELD

    public PrimaryAndCompatibleModelsDto(Mobile primaryModel, List<Mobile> compatibleModels) {
        this.primaryModel = primaryModel;
        this.compatibleModels = compatibleModels;
    }

    public Mobile getPrimaryModel() {
        return primaryModel;
    }

    public void setPrimaryModel(Mobile primaryModel) {
        this.primaryModel = primaryModel;
    }

    public List<Mobile> getCompatibleModels() {
        return compatibleModels;
    }

    public void setCompatibleModels(List<Mobile> compatibleModels) {
        this.compatibleModels = compatibleModels;
    }

    public List<GlassProtector> getProtectors() { // NEW GETTER
        return protectors;
    }

    public void setProtectors(List<GlassProtector> protectors) { // NEW SETTER
        this.protectors = protectors;
    }
}
